package com.mhealthkenya.psurvey.activities;

import static com.mhealthkenya.psurvey.depedancies.AppController.TAG;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.activities.auth.LoginActivity;
import com.mhealthkenya.psurvey.adapters.QuestionnairesAdapterOffline;
import com.mhealthkenya.psurvey.adapters.activeSurveyAdapter;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.fragments.EditProfileFragment;
import com.mhealthkenya.psurvey.interfaces.CurrentUserCallback;
import com.mhealthkenya.psurvey.interfaces.CurrentUserDao;
import com.mhealthkenya.psurvey.models.ActiveSurveys;
import com.mhealthkenya.psurvey.models.AnswerEntity;
import com.mhealthkenya.psurvey.models.Available;
import com.mhealthkenya.psurvey.models.AvailableSurveys;
import com.mhealthkenya.psurvey.models.Completed;
import com.mhealthkenya.psurvey.models.CurrentUser;
import com.mhealthkenya.psurvey.models.QuestionEntity;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;
import com.mhealthkenya.psurvey.models.UrlTable;
import com.mhealthkenya.psurvey.models.UserDetails;
import com.mhealthkenya.psurvey.models.auth;
import com.orm.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

public class offlineHome extends AppCompatActivity implements CurrentUserCallback{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    QuestionEntity questionEntity;
    AnswerEntity answerEntity;

    AllQuestionDatabase allQuestionDatabase;


    JSONObject jsonObject;
    private RequestQueue requestQueue;

    int questionnaireId;

    int active2;
    int availablesurveys1;


    public ArrayList<QuestionnaireEntity> questionnaireEntities;

    QuestionnaireEntity questionnaireEntity2;


    public String z;

    private Unbinder unbinder;
    private View root;
    private Context context;

    private auth loggedInUser;
    private activeSurveyAdapter mAdapter;
    private ArrayList<ActiveSurveys> activeSurveysArrayList;

    TextView txt_name;
    TextView txt_email;
    TextView tv_facility;

    int countS;

    TextView tv_completed_surveys1, tv_active_surveys, editProfile;

    CardView surveysID1, available;

    private ProgressDialog pDialog;
    ProgressDialog progressDialog;

    private CurrentUserDao currentUserDao;
    public QuestionnairesAdapterOffline questionnairesAdapterOffline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_home);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("pSurvey");
        allQuestionDatabase = AllQuestionDatabase.getInstance(this);
        currentUserDao = allQuestionDatabase.currentUserDao();
        questionnairesAdapterOffline = new QuestionnairesAdapterOffline(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            // Handle navigation item clicks here
            switch (item.getItemId()) {
                case R.id.nav_edit_profile:
                    // Handle item 1 click
                    break;
                case R.id.nav_home:
                    // Handle item 2 click
                    break;
                // Add more cases as needed
            }

            drawerLayout.closeDrawers();
            return true;
        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Set to true if you want the dialog to be cancelable
        progressDialog.setIndeterminate(true); // Set to false if you want a progress bar instead of an indeterminate spinner
        progressDialog.show();





        requestQueue = Volley.newRequestQueue(this);
        questionnaireEntities = new ArrayList<>();

        txt_name= findViewById(R.id.tv_name);
        txt_email= findViewById(R.id.tv_email);
        tv_facility = findViewById(R.id.tv_facility);
        editProfile = findViewById(R.id.editProfile);

        try {
            loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN, auth.class);
        } catch (NullPointerException e){
            Log.d("-->User Log", e.getMessage());
        }


        tv_completed_surveys1 = findViewById(R.id.tv_completed_surveys);
        tv_active_surveys = findViewById(R.id.tv_active_surveys);

        if (isConnected(offlineHome.this)){
            try2();
        }else{
            progressDialog.dismiss();

            new RetrieveQuestionnaireTask(allQuestionDatabase, questionnaireEntities, questionnairesAdapterOffline).execute();

        }



        surveysID1 = findViewById(R.id.surveysID);
        available = findViewById(R.id.availableID);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(offlineHome.this, EditProfileActivity.class);
                startActivity(editIntent);
            }
        });

        available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(offlineHome.this, Query2.class);
                startActivity(intent);

                //Toast.makeText(offlineHome.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        loadCurrentUser(this);
      //  try2();


        try {

            List<Completed> _url = Completed.findWithQuery(Completed.class, "SELECT *from Completed ORDER BY id DESC LIMIT 1");
            if (_url.size() == 1) {
                for (int x = 0; x < _url.size(); x++) {
                    countS = _url.get(x).getComplete();

             //       Toast.makeText(offlineHome.this, String.valueOf(countS), Toast.LENGTH_LONG).show();
                    Log.d("COMPLETED SURVEYS", String.valueOf(countS));

                 //   Select.from(Completed.class).orderBy("ID DESC").first();


                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        tv_completed_surveys1.setText(String.valueOf(countS));



    }


//    private void loadCurrentUser(){
//
//        String auth_token = loggedInUser.getAuth_token();
//
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
//
//        AndroidNetworking.get(z+Constants.CURRENT_USER_DETAILED)
//                .addHeaders("Authorization","Token "+ auth_token)
//                .addHeaders("Content-Type", "application.json")
//                .addHeaders("Accept", "*/*")
//                .addHeaders("Accept", "gzip, deflate, br")
//                .addHeaders("Connection","keep-alive")
//                .setPriority(Priority.LOW)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        // do anything with response
////                        Log.e(TAG, response.toString());
//
//                        try {
//
//                            JSONObject user = response.getJSONObject("user");
//
//                            int id = user.has("id") ? user.getInt("id"): 0;
//                            String msisdn = user.has("msisdn") ? user.getString("msisdn") : "";
//                            String email = user.has("email") ? user.getString("email") : "";
//                            String firstName = user.has("f_name") ? user.getString("f_name") : "";
//                            String lastName = user.has("l_name") ? user.getString("l_name") : "";
//                            JSONObject designation = user.getJSONObject("designation");
//
//                            int designationId = designation.has("id") ? designation.getInt("id"): 0;
//                            String designationName = designation.has("name") ? designation.getString("name") : "";
//
//                            JSONObject facility = user.getJSONObject("facility");
//
//                            int facilityId = facility.has("id") ? facility.getInt("id"): 0;
//                            int mflCode = facility.has("mfl_code") ? facility.getInt("mfl_code"): 0;
//                            String facilityName = facility.has("name") ? facility.getString("name") : "";
//                            String county = facility.has("county") ? facility.getString("county") : "";
//                            String subCounty = facility.has("sub_county") ? facility.getString("sub_county") : "";
//
//                            String activeQuestionnaires = response.has("Active_questionnaires") ? response.getString("Active_questionnaires") : "";
//                            String completedSurveys = response.has("Completed_surveys") ? response.getString("Completed_surveys") : "";
//
//
//                            txt_name.setText(firstName + " " + lastName);
//                            txt_email.setText(email);
//                            tv_facility.setText(facilityName);
//                            //tv_active_surveys.setText(activeQuestionnaires);
//                            //tv_completed_surveys.setText(completedSurveys);
//
//                            Stash.put(String.valueOf(Constants.MFL_CODE), mflCode);
//
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                    @Override
//                    public void onError(ANError error) {
//
//                        // handle error
////                        Log.e(TAG, error.getErrorBody());
//
//                        Snackbar.make(root.findViewById(R.id.frag_home), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();
//
//                    }
//                });
//
//    }


    private void try2(){




        String url = "https://psurveyapitest.kenyahmis.org/api/questions/dep/all";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //pDialog.show();
               // pDialog.hide();



                Log.d("ALl", "SUCCESS");
                Log.d("ALl", response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {

                        jsonObject = response.getJSONObject(i);


                        // Parse the JSON data
                        questionnaireId = jsonObject.getInt("id");
                        String questionnaireName = jsonObject.getString("name");
                        String questionnaireDescription = jsonObject.getString("description");
                        boolean questionnaireIsActive = jsonObject.getBoolean("is_active");
                        String questionnaireCreatedAt = jsonObject.getString("created_at");
                        int questionnaireNumberOfQuestions = jsonObject.getInt("number_of_questions");
                        String questionnaireActiveTill = jsonObject.getString("active_till");
                        String questionnaireTargetApp = jsonObject.getString("target_app");
                        //Log.d("ALl", questionnaireName);

                        // Create and insert the QuestionnaireEntity
                        questionnaireEntity2 = new QuestionnaireEntity();
                        questionnaireEntity2.setId(questionnaireId);
                        questionnaireEntity2.setName(questionnaireName);
                        questionnaireEntity2.setDescription(questionnaireDescription);
                        questionnaireEntity2.setActive(questionnaireIsActive);
                        questionnaireEntity2.setCreatedAt(questionnaireCreatedAt);
                        questionnaireEntity2.setNumberOfQuestions(questionnaireNumberOfQuestions);
                        questionnaireEntity2.setActiveTill(questionnaireActiveTill);
                        questionnaireEntity2.setTargetApp(questionnaireTargetApp);
                        questionnaireEntity2.setResponsesTableName(null); // You may set this as needed
                        questionnaireEntity2.setIsPublished(null); // You may set this as needed
                        questionnaireEntity2.setCreatedBy(14); // Y
                        // you may set this as needed

                        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity(questionnaireId,questionnaireName, questionnaireDescription, questionnaireCreatedAt, questionnaireNumberOfQuestions, questionnaireActiveTill, questionnaireTargetApp);
                        questionnaireEntities.add(questionnaireEntity);

                        allQuestionDatabase.questionnaireDao().insert(questionnaireEntity2);

                        Available available1 =new Available(10);
                        available1.save();
                       /* AvailableSurveys.deleteAll(AvailableSurveys.class);
                        AvailableSurveys availableSurveys = new AvailableSurveys(questionnaireEntities.size());
                        availableSurveys.save();*/


                        //questions
                        JSONArray jsonArray = jsonObject.getJSONArray("questions");

                        for (int j = 0; j < jsonArray.length(); j++) {
                            JSONObject questionObject = jsonArray.getJSONObject(j);

                            int questionId = questionObject.getInt("id");
                            String questionText = questionObject.getString("question");
                            String date_validation = questionObject.getString("date_validation");
                            int questionType = questionObject.getInt("question_type");
                            int questionOrder = questionObject.getInt("question_order");
                            boolean isRequired = questionObject.getBoolean("is_required");
                            boolean isRepeatable = questionObject.getBoolean("is_repeatable");

                            // Create and insert the QuestionEntity
                            questionEntity = new QuestionEntity();
                            questionEntity.setId(questionId);
                            questionEntity.setQuestionnaireId(questionnaireId);
                            questionEntity.setQuestion(questionText);
                            questionEntity.setQuestionType(questionType);
                            questionEntity.setQuestionOrder(questionOrder);
                            questionEntity.setRequired(isRequired);
                           // questionEntity.setDateValidation(null); // You may set this as needed
                            questionEntity.setDateValidation(date_validation); // You may set this as needed
                            questionEntity.setRepeatable(isRepeatable); // You may set this as needed
                            questionEntity.setResponseColName(null); // You may set this as needed
                            questionEntity.setCreatedAt(questionObject.getString("created_at"));
                            questionEntity.setCreatedBy(questionObject.getInt("created_by"));

                            // Insert the QuestionEntity into the Room database
                            allQuestionDatabase.questionDao().insert(questionEntity);


                            // Parse and insert answers
                            JSONArray answersArray = questionObject.getJSONArray("answers");
                            // JSONArray answersArray = jsonObject.getJSONArray("answers");
                            for (int k = 0; k < answersArray.length(); k++) {
                                JSONObject answerObject = answersArray.getJSONObject(k);

                                int answerId = answerObject.getInt("id");
                                String answerOption = answerObject.getString("option");

                                Log.d("ANSWER OPTION", answerOption);

                                // Create and insert the AnswerEntity
                                answerEntity = new AnswerEntity();
                                answerEntity.setId(answerId);
                                answerEntity.setQuestionId(questionId);
                                answerEntity.setQuestionnaireId(questionnaireId);
                                answerEntity.setOption(answerOption);
                                answerEntity.setCreatedAt(answerObject.getString("created_at"));
                                answerEntity.setCreatedBy(answerObject.getInt("created_by"));


                                allQuestionDatabase.answerDao().insert(answerEntity);

                                // Insert the AnswerEntity into the Room database

                            }}


                            // RetrieveQuestionnaire();
                        // myAsyncTask1.execute();

                        //RetrieveQuestionnaire();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //   progressBar.setVisibility(View.GONE);
               tv_active_surveys.setText(String.valueOf(questionnaireEntities.size()));




               // Toast.makeText(offlineHome.this, "nOMBERSUrveys"+""+String.valueOf(availablesurveys1), Toast.LENGTH_LONG).show();

                /*Available available1 =new Available(questionnaireEntities.size());
                available1.save();*/
                progressDialog.dismiss();
               // Toast.makeText(offlineHome.this, "nOMBERSUrveys"+""+String.valueOf(questionnaireEntities.size()), Toast.LENGTH_LONG).show();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("SURVEYSSS", error.getMessage());

            }
        });
        requestQueue.add(jsonArrayRequest);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                break;

            case R.id.action_about_app:

                aboutAppDialog();
                break;

            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
    public void logout(){

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

        AndroidNetworking.post(z+Constants.LOGOUT)
                .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        Log.e(TAG, response.toString());



                        try {
                            boolean  status = response.has("success") && response.getBoolean("success");
                            String error = response.has("error") ? response.getString("error") : "";
                            String message = response.has("message") ? response.getString("message") : "";

                            if (status){

                                String endPoint = Stash.getString(Constants.AUTH_TOKEN);
                                Stash.clearAll();
                                Stash.put(Constants.AUTH_TOKEN, endPoint);

                                Intent intent = new Intent(offlineHome.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();


                            }else if (!status){

                                Snackbar.make(findViewById(R.id.drawer_layout), message, Snackbar.LENGTH_LONG).show();

                            }
                            else{

                                Snackbar.make(findViewById(R.id.drawer_layout), error, Snackbar.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, String.valueOf(error.getErrorCode()));


                        if (error.getErrorCode() == 0){

                            String endPoint = Stash.getString(Constants.AUTH_TOKEN);
                            Stash.clearAll();
                            Stash.put(Constants.AUTH_TOKEN, endPoint);

                            Intent intent = new Intent(offlineHome.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        }
                        else{

                            Toast.makeText(offlineHome.this, ""+error.getErrorBody(), Toast.LENGTH_SHORT).show();


                        }

                    }
                });


    }

    public void aboutAppDialog(){

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_about);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;//Version Name
            int verCode = pInfo.versionCode;//Version Code

            ((TextView) dialog.findViewById(R.id.tv_version)).setText("Version: " + version);

            ((TextView) dialog.findViewById(R.id.tv_build)).setText("Build: " + String.valueOf(verCode));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }



        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(offlineHome.this, LoginActivity.class);
        startActivity(intent);
        //        super.onBackPressed();


    }

    private  boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo =connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);

        if((wifiInfo !=null && wifiInfo.isConnected())|| (mobileInfo !=null && mobileInfo.isConnected())){
            return true;
        }
        else{
            return false;
        }

    }

    private void loadCurrentUser(CurrentUserCallback callback) {
        // Check if the current user details are already stored locally
        UserDetails currentUser = currentUserDao.getCurrentUser();
        if (currentUser != null) {
            // User details are already stored locally, return them
            callback.onCurrentUserLoaded(currentUser);
            return;
        }

        String auth_token = loggedInUser.getAuth_token();

        try {
            List<UrlTable> _url = UrlTable.findWithQuery(UrlTable.class, "SELECT * FROM URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size() == 1) {
                for (int x = 0; x < _url.size(); x++) {
                    z = _url.get(x).getBase_url1();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        AndroidNetworking.get(z + Constants.CURRENT_USER_DETAILED)
                .addHeaders("Authorization", "Token " + auth_token)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection", "keep-alive")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the response and create a UserDetails object
                            UserDetails userDetails = parseUserDetails(response);

                            // Save the current user details locally in the Room database
                            saveCurrentUserLocally(userDetails);

                            // Notify the callback with the user details
                            callback.onCurrentUserLoaded(userDetails);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onCurrentUserLoadFailed("Error parsing JSON response");
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        callback.onCurrentUserLoadFailed("Error: " + error.getErrorBody());
                    }
                });
    }

    private void saveCurrentUserLocally(UserDetails userDetails) {

        CurrentUser currentUser = new CurrentUser();
        currentUser.setEmail(userDetails.getEmail());
        currentUser.setFirstName(userDetails.getFirstName());
        currentUser.setLastName(userDetails.getLastName());
        currentUser.setMflCode(userDetails.getMflCode());
        currentUser.setFacilityName(userDetails.getFacilityName());

        // Insert the CurrentUser object into the Room database
        allQuestionDatabase.currentUserDao().insertCurrentUser(currentUser);
        Log.i("-->Save UserDetails ", currentUser.toString());
    }

    @Override
    public void onCurrentUserLoaded(UserDetails currentUser) {
        txt_name.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        txt_email.setText(currentUser.getEmail());
        tv_facility.setText(currentUser.getFacilityName());
    }

    @Override
    public void onCurrentUserLoadFailed(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    private UserDetails parseUserDetails(JSONObject response) throws JSONException {
        JSONObject user = response.getJSONObject("user");

        UserDetails userDetails = new UserDetails();
        userDetails.setId(user.has("id") ? user.getInt("id") : 0);
//        userDetails.setMsisdn(user.has("msisdn") ? user.getString("msisdn") : "");
        userDetails.setEmail(user.has("email") ? user.getString("email") : "");
        userDetails.setFirstName(user.has("f_name") ? user.getString("f_name") : "");
        userDetails.setLastName(user.has("l_name") ? user.getString("l_name") : "");

//        JSONObject designation = user.getJSONObject("designation");
//        userDetails.setDesignationId(designation.has("id") ? designation.getInt("id") : 0);
//        userDetails.setDesignationName(designation.has("name") ? designation.getString("name") : "");

        JSONObject facility = user.getJSONObject("facility");
//        userDetails.setFacilityId(facility.has("id") ? facility.getInt("id") : 0);
        userDetails.setMflCode(facility.has("mfl_code") ? facility.getInt("mfl_code") : 0);
        userDetails.setFacilityName(facility.has("name") ? facility.getString("name") : "");
//        userDetails.setCounty(facility.has("county") ? facility.getString("county") : "");
//        userDetails.setSubCounty(facility.has("sub_county") ? facility.getString("sub_county") : "");
//
//        userDetails.setActiveQuestionnaires(response.has("Active_questionnaires") ? response.getString("Active_questionnaires") : "");
//        userDetails.setCompletedSurveys(response.has("Completed_surveys") ? response.getString("Completed_surveys") : "");

        return userDetails;
    }




    //retrieve background
    public class RetrieveQuestionnaireTask extends AsyncTask<Void, Void, List<QuestionnaireEntity>> {

        private AllQuestionDatabase allQuestionDatabase;
        private List<QuestionnaireEntity> questionnaireEntities;
        private QuestionnairesAdapterOffline questionnairesAdapterOffline;

        public RetrieveQuestionnaireTask(AllQuestionDatabase allQuestionDatabase, List<QuestionnaireEntity> questionnaireEntities, QuestionnairesAdapterOffline questionnairesAdapterOffline) {
            this.allQuestionDatabase = allQuestionDatabase;
            this.questionnaireEntities = questionnaireEntities;
            this.questionnairesAdapterOffline = questionnairesAdapterOffline;
        }

        @Override
        protected List<QuestionnaireEntity> doInBackground(Void... voids) {
            return allQuestionDatabase.questionnaireDao().getAllQuestionnaires();
        }

        @Override
        protected void onPostExecute(List<QuestionnaireEntity> questionnaires) {
            super.onPostExecute(questionnaires);

            for (QuestionnaireEntity retrievedQuestionnaire : questionnaires) {
                Log.d("ALl", retrievedQuestionnaire.getName());
                Log.d("ALl", retrievedQuestionnaire.getDescription());

                int questionnaireId = retrievedQuestionnaire.getId();
                String questionnaireCreatedAt = retrievedQuestionnaire.getCreatedAt();
                String questionnaireDescription = retrievedQuestionnaire.getDescription();
                int questionnaireNumberOfQuestions = retrievedQuestionnaire.getNumberOfQuestions();
                String questionnaireActiveTill = retrievedQuestionnaire.getActiveTill();
                String questionnaireTargetApp = retrievedQuestionnaire.getTargetApp();

                String questionnaireName = retrievedQuestionnaire.getName();

                boolean questionnaireIsActive = retrievedQuestionnaire.isActive();


                QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity(questionnaireId, questionnaireCreatedAt, questionnaireName, questionnaireDescription,  questionnaireNumberOfQuestions, questionnaireActiveTill, questionnaireTargetApp);
                // QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity(questionnaireId, questionnaireCreatedAt,  questionnaireDescription,  questionnaireNumberOfQuestions, questionnaireActiveTill, questionnaireTargetApp);
                //QuestionnaireEntity(int id, String createdAt, String name, String description, int numberOfQuestions, String activeTill, String targetApp)

                questionnaireEntities.add(questionnaireEntity);
                questionnairesAdapterOffline.setUser(questionnaireEntities);

                tv_active_surveys.setText(String.valueOf(questionnaireEntities.size()));



            }






        }
    }

}
