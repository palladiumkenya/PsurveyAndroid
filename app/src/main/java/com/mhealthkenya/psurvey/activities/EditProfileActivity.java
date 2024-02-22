package com.mhealthkenya.psurvey.activities;


import static com.mhealthkenya.psurvey.depedancies.AppController.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView;
import android.view.View;
import android.content.Context;
import android.widget.AdapterView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.snackbar.Snackbar;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.activities.auth.LoginActivity;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.fragments.EditProfileFragment;
import com.mhealthkenya.psurvey.helper.DatabaseHelper;
import com.mhealthkenya.psurvey.models.Designation;
import com.mhealthkenya.psurvey.models.Facility;
import com.mhealthkenya.psurvey.models.UrlTable;
import com.mhealthkenya.psurvey.models.User;
import com.mhealthkenya.psurvey.models.auth;
import com.mhealthkenya.psurvey.service.LoginService;
import com.mhealthkenya.psurvey.utils.FacilitySpinnerUtils;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EditProfileActivity extends AppCompatActivity {

    private Unbinder unbinder;
    private View root;
    private Context context;

    private auth loggedInUser;
    private User user;
    public String z;

    private int facilityID = 0;
    private int designationID = 0;

    ArrayList<String> facilitiesList;
    ArrayList<Facility> facilities;

    ArrayList<String> designationList;
    ArrayList<Designation> designations;
    DatabaseHelper databaseHelper;

    TextView card_name,card_phone;

    TextInputEditText etxt_first_name, etxt_lastname, etxt_phone_number, etxt_email, etxt_facility;

    SearchableSpinner  designation_Spinner;

    MaterialButton btn_update_profile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_profile);
//        unbinder = ButterKnife.bind(this);
        databaseHelper = new DatabaseHelper(EditProfileActivity.this);
        initialise();


        // Initialize other variables and views
        loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN, auth.class);

//        facility_Spinner.setTitle("Select the facility ");
//        facility_Spinner.setPositiveButton("OK");

        designation_Spinner.setTitle("Select your designation ");
        designation_Spinner.setPositiveButton("OK");



        // Load user details
        loadCurrentUser();
        getFacilities();
        getDesignation();
//        startLoginService();

        // Set onClickListener for update profile button
        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserDetails();
            }
        });
    }

    private void initialise(){
        card_name = (TextView) findViewById(R.id.card_name);
        card_phone = (TextView) findViewById(R.id.card_phone);
        etxt_first_name = (TextInputEditText) findViewById(R.id.etxt_first_name);
        etxt_lastname = (TextInputEditText) findViewById(R.id.etxt_lastname);
        etxt_phone_number = (TextInputEditText) findViewById(R.id.etxt_phone_number);
        etxt_email = (TextInputEditText) findViewById(R.id.etxt_email);
        etxt_facility = (TextInputEditText) findViewById(R.id.etxt_facility);
        designation_Spinner = (SearchableSpinner) findViewById(R.id.designation_Spinner);
        btn_update_profile = (MaterialButton) findViewById(R.id.btn_update_profile);

    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unbinder.unbind();
//    }

    private void loadCurrentUser(){

        String auth_token = loggedInUser.getAuth_token();
        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }


        AndroidNetworking.get(z+Constants.CURRENT_USER)
                .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
//                        Log.e(TAG, response.toString());

                        try {

                            int id = response.has("id") ? response.getInt("id") : 0;
                            String first_name = response.has("f_name") ? response.getString("f_name") : "";
                            String last_name = response.has("l_name") ? response.getString("l_name") : "";
                            String email = response.has("email") ? response.getString("email") : "";
                            String phone_no = response.has("msisdn") ? response.getString("msisdn") : "";
                            int designation = response.has("designation") ? response.getInt("designation") : 0;
                            int facility = response.has("facility") ? response.getInt("facility") : 0;

                            User newUser = new User(id,first_name,last_name,email,phone_no,facility,designation);

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user", newUser);

                            card_name.setText(first_name+" "+last_name);
                            card_phone.setText(phone_no);

                            etxt_first_name.setText(first_name);
                            etxt_lastname.setText(last_name);
                            etxt_email.setText(email);
                            etxt_phone_number.setText(phone_no);


                            etxt_facility.setText(databaseHelper.getFacilityName(facility));


//                            facilityID = facility;
                            designationID = designation;


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
//                        Log.e(TAG, error.getErrorBody());

                        Snackbar.make(root.findViewById(com.mhealthkenya.psurvey.R.id.frag_update_user), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });

    }
    private void getFacilities() {

//        try{
//            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
//            if (_url.size()==1){
//                for (int x=0; x<_url.size(); x++){
//                    z=_url.get(x).getBase_url1();
//                }
//            }
//
//        } catch(Exception e){
//
//        }
//
//        AndroidNetworking.get(z+Constants.ALL_FACILITIES)
//                .addHeaders("Content-Type", "application.json")
//                .addHeaders("Accept", "*/*")
//                .addHeaders("Accept", "gzip, deflate, br")
//                .addHeaders("Connection","keep-alive")
//                .setPriority(Priority.LOW)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // do anything with response
//                        Log.e(TAG, response.toString());
//
//                        try {
//
//                            boolean  status = response.has("success") && response.getBoolean("success");
//                            String  message = response.has("message") ? response.getString("message") : "" ;
//                            String  errors = response.has("errors") ? response.getString("errors") : "" ;
//
//
//                            facilities = new ArrayList<Facility>();
//                            facilitiesList = new ArrayList<String>();
//
//                            facilities.clear();
//                            facilitiesList.clear();
//
//                            JSONArray jsonArray = response.getJSONArray("data");
//
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject facility = (JSONObject) jsonArray.get(i);
//
//                                int id = facility.has("id") ? facility.getInt("id") : 0;
//                                String mfl_code = facility.has("mfl_code") ? facility.getString("mfl_code") : "";
//                                String name = facility.has("name") ? facility.getString("name") : "";
//                                String county = facility.has("county") ? facility.getString("county") : "";
//                                String sub_county = facility.has("sub_county") ? facility.getString("sub_county") : "";
//
//                                Facility newFacility = new Facility(id,mfl_code,name,county,sub_county);
//
//                                facilities.add(newFacility);
//                                facilitiesList.add(newFacility.getName());
//                            }
//
//
//                            facilities.add(new Facility(0,"Select your facility.","Select your facility.","--select--","--select--"));
//                            facilitiesList.add("Select your facility.");
//
//                            ArrayAdapter<String> aa=new ArrayAdapter<String>(context,
//                                    android.R.layout.simple_spinner_dropdown_item,
//                                    facilitiesList){
//                                @Override
//                                public int getCount() {
//                                    return super.getCount(); // you don't display last item. It is used as hint.
//                                }
//                            };
//
//                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//                            if (facility_Spinner != null){
//                                facility_Spinner.setAdapter(aa);
//                                facility_Spinner.setSelection(facilityID-1);
//
//                                facilityID = facilities.get(aa.getCount()-1).getId();
//
//                                facility_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                    @Override
//                                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//
//                                        facilityID = facilities.get(position).getId();
//
//
//                                    }
//
//                                    @Override
//                                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                                    }
//                                });
//
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//
//                            Snackbar.make(root.findViewById(R.id.frag_update_user), e.getMessage(), Snackbar.LENGTH_LONG).show();
//                        }
//
//
//                    }
//                    @Override
//                    public void onError(ANError error) {
//                        // handle error
//
//                        Log.e(TAG, String.valueOf(error.getErrorCode()));
//
//                    }
//                });
    }

    private void getDesignation() {

        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }


        AndroidNetworking.get(z+Constants.DESIGNATION)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.e(TAG, response.toString());

                        try {

                            boolean  status = response.has("success") && response.getBoolean("success");
                            String  message = response.has("message") ? response.getString("message") : "" ;
                            String  errors = response.has("errors") ? response.getString("errors") : "" ;


                            designations = new ArrayList<Designation>();
                            designationList = new ArrayList<String>();

                            designations.clear();
                            designationList.clear();

                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject designation = (JSONObject) jsonArray.get(i);

                                int id = designation.has("id") ? designation.getInt("id") : 0;
                                String name = designation.has("name") ? designation.getString("name") : "";


                                Designation newDesignation = new Designation(id,name);

                                designations.add(newDesignation);
                                designationList.add(newDesignation.getName());


                            }

                            designations.add(new Designation(0,"Select your designation."));
                            designationList.add("Select your designation.");




                            ArrayAdapter<String> aa=new ArrayAdapter<String>(EditProfileActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    designationList){
                                @Override
                                public int getCount() {
                                    return super.getCount(); // you don't display last item. It is used as hint.
                                }
                            };

                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            if (designation_Spinner != null){
                                designation_Spinner.setAdapter(aa);
                                designation_Spinner.setSelection(designationID-1);

                                designationID = designations.get(aa.getCount()-1).getId();

                                designation_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                        designationID = designations.get(position).getId();

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Snackbar.make(root.findViewById(com.mhealthkenya.psurvey.R.id.frag_update_user), e.getMessage(), Snackbar.LENGTH_LONG).show();
                        }


                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error

                        Log.e(TAG, String.valueOf(error.getErrorCode()));

                    }
                });
    }

    private void updateUserDetails() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("designation", designationID != 0 ? designationID : "");
            jsonObject.put("facility", databaseHelper.getFacilityId(etxt_facility.getText().toString()));
            jsonObject.put("email", etxt_email.getText().toString());
            jsonObject.put("f_name", etxt_first_name.getText().toString());
            jsonObject.put("l_name", etxt_lastname.getText().toString());
            jsonObject.put("msisdn", etxt_phone_number.getText().toString());



        } catch (JSONException e) {
            e.printStackTrace();
        }

        String auth_token = loggedInUser.getAuth_token();
        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }

        AndroidNetworking.patch(z+Constants.UPDATE_USER)
                .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setContentType("application.json")
                .addJSONObjectBody(jsonObject) // posting json
                .build()
                .getAsJSONObject(new JSONObjectRequestListener(){
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, response.toString());

                        try {

                            String  errors = response.has("error") ? response.getString("error") : "" ;


                            if (response.has("id")){
                                Intent mint = new Intent(EditProfileActivity.this, offlineHome.class);
                                mint.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mint);
                                Toast.makeText(EditProfileActivity.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();

//                                NavHostFragment.findNavController(EditProfileActivity.this).navigate(com.mhealthkenya.psurvey.R.id.nav_home);
//                                Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show();

                            }
                            else{

                                Toast.makeText(context, errors, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, error.getErrorBody());

                        Snackbar.make(root.findViewById(com.mhealthkenya.psurvey.R.id.frag_update_user), "Error: "+error.getErrorBody(), Snackbar.LENGTH_LONG).show();
                    }
                });

    }
}
