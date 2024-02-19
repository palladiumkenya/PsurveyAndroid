package com.mhealthkenya.psurvey.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.mhealthkenya.psurvey.LastConsent;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.activities.auth.LoginActivity;
import com.mhealthkenya.psurvey.adapters.UploadDataAdapter;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.fragments.HomeFragment;
import com.mhealthkenya.psurvey.fragments.Test;
import com.mhealthkenya.psurvey.models.ActiveSurveys;
import com.mhealthkenya.psurvey.models.UploadModel;
import com.mhealthkenya.psurvey.models.UrlTable;
import com.mhealthkenya.psurvey.models.auth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.Unbinder;

public class UploadedActivity extends Fragment implements UploadDataAdapter.OnItemClickListener {

    boolean isSearchOpened = false;
    private EditText edtSearch;
    private ActionBar actionBar;
    private EditText edtxt_search1;
    JSONArray jsonArray1;

    private Context context;

    RelativeLayout cod;

    ListView listView;

    String passedUname,passedPassword;

     auth loggedInUser;
    String z;

    List<UploadModel> upilist= new ArrayList<>();

    UploadDataAdapter upiErrAdapter1;

    int MFLcode;
   ActiveSurveys activeSurveys;

    View rootView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.activity_uploaded, container, false);

        TextInputLayout textInputLayout = rootView.findViewById(R.id.searchL);
        edtxt_search1 = rootView.findViewById(R.id.edtxt_search);
        textInputLayout.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
        textInputLayout.setEndIconDrawable(R.drawable.search);
        textInputLayout.setEndIconContentDescription("Search");


        setHasOptionsMenu(true); // Important for menu creation

        // Initialize views and action bar
        actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

        FloatingActionButton fab=rootView.findViewById(R.id.fabtodays);

        cod=rootView.findViewById(R.id.coordinate);
        listView= rootView.findViewById(R.id.messages);


        assert getArguments() != null;
        activeSurveys = (ActiveSurveys) getArguments().getSerializable("questionnaire");
        MFLcode = Stash.getInt(String.valueOf(Constants.MFL_CODE));

        Log.d("activeSurveys ", String.valueOf(activeSurveys.getId()));
        Log.d("MFLCODEEE", String.valueOf(MFLcode));
       // Toast.makeText(UploadedActivity.this, "MFL CODE: "+MFLcode, Toast.LENGTH_SHORT).show();

        passedUname="";
        passedPassword="";



        postapi();
        edtxt_search1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                doSearching(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ApiCall();
                postapi();



            }
        });
        return rootView;
    }




    public void doSearching(CharSequence s){
        //refreshSmsInbox();
        try {

            upiErrAdapter1.getFilter().filter(s.toString());

        }
        catch(Exception e){

            Toast.makeText(context, "unable to filter: "+e, Toast.LENGTH_SHORT).show();
        }

    }

    public  void postapi() {
        loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN, auth.class);

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

//     /99/12345/


        AndroidNetworking.get(z+Constants.UPLOADEDDATA+activeSurveys.getId()+"/"+MFLcode)
                .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setPriority(Priority.LOW)

                .build().getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("SERVER RESPONSE", response.toString());

                        String test = response.toString();

                        //Toast.makeText(UPIErrorList.this, "success"+response, Toast.LENGTH_SHORT).show();
                        upilist = new ArrayList<>();

                        try {

                           jsonArray1 = response.getJSONArray("data");
                            if (jsonArray1.length()==0){
                                Toast.makeText(context, "No Data for Clients", Toast.LENGTH_SHORT).show();
                            }

                            for (int i = 0; i < jsonArray1.length(); i++) {
                                JSONObject jsonObject = jsonArray1.getJSONObject(i);

                                int id = jsonObject.getInt("id");
                                String ccc_number = jsonObject.getString("ccc_number");
                                int mfl_code = jsonObject.getInt("mfl_code");
                                boolean has_completed_survey = jsonObject.getBoolean("has_completed_survey");
                                int questionnaire = jsonObject.getInt("questionnaire");


                               upiErrAdapter1 = new UploadDataAdapter(getActivity(), upilist, UploadedActivity.this::onItemClick);


                                UploadModel uploadModel = new UploadModel(id, ccc_number, mfl_code, has_completed_survey, questionnaire);
                                //upilist=new ArrayList<>();
                                upilist.add(uploadModel);

                                listView.setAdapter(upiErrAdapter1);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                    @Override
                    public void onError(ANError anError) {

                        Toast.makeText(getActivity(), anError.getMessage(), Toast.LENGTH_LONG).show();

                        Log.d("Eror", anError.getMessage());

                    }
                });
    }

    @Override
    public void onItemClick(String position) {

        Toast.makeText(getActivity(), position, Toast.LENGTH_LONG).show();
        Bundle bundle = new Bundle();
        bundle.putSerializable("questionnaire", activeSurveys);
        bundle.putString("CCCNUMBER", position);
       // bundle.putSerializable("questionnaire", position);
        Navigation.findNavController(rootView).navigate(R.id.lastConsent2, bundle);


    }
}