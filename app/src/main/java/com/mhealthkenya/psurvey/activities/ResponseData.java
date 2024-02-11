package com.mhealthkenya.psurvey.activities;

import static com.mhealthkenya.psurvey.depedancies.AppController.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.activities.auth.LoginActivity;
import com.mhealthkenya.psurvey.adapters.UserResponseAdapter;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.models.AnswerEntity;
import com.mhealthkenya.psurvey.models.QuestionEntity;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;
import com.mhealthkenya.psurvey.models.QuetionnaireID;
import com.mhealthkenya.psurvey.models.ResponseIntent;
import com.mhealthkenya.psurvey.models.SessionID;
import com.mhealthkenya.psurvey.models.SessionOffline;
import com.mhealthkenya.psurvey.models.UrlTable;
import com.mhealthkenya.psurvey.models.UserResponseEntity;
import com.mhealthkenya.psurvey.models.auth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseData extends AppCompatActivity {

    public String z;
    private auth loggedInUser;


    Button btnsubmit;
    int IDvalue;

    String SesssionValue;
    AllQuestionDatabase allQuestionDatabase;

    int quer_id;


    UserResponseEntity userResponseEntity;

    ArrayList<UserResponseEntity> answerList = new ArrayList<>();

    public ArrayList<UserResponseEntity> userResponseEntities;

    public List<UserResponseEntity> userResponseEntities1;

    RecyclerView recyclerView1;
    public  UserResponseAdapter adapter;


    List<UserResponseEntity> userResponses;

    List<SessionOffline> sessionOfflines;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_data);
        userResponseEntities1 = new ArrayList<>();
        btnsubmit = (Button) findViewById(R.id.submit_all);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Responses");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Set the toolbar navigation icon click listener
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Handle the back arrow click to navigate to the previous activity
            }
        });
        // Initialize your RecyclerView

        loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN, auth.class);



        allQuestionDatabase = AllQuestionDatabase.getInstance(this);
        //userResponseEntities =allQuestionDatabase.userResponseDao().getUserResponsesForQuestionnaire()

     //    Intent mIntent = getIntent();
     //   IDvalue = mIntent.getIntExtra("Quetionnaire_ID", 0);
        try {

            //from QUETIONNAIRE_ID


            List<QuetionnaireID> _url =QuetionnaireID.findWithQuery(QuetionnaireID.class, "SELECT *from QUETIONNAIRE_ID ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    IDvalue=_url.get(x).getQuetioonareID();

                    Toast.makeText(ResponseData.this, "Your Quetionnaire_ID is" + " " +IDvalue, Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){
            Log.d(" IDvalue", e.getMessage());
        }


        //    SesssionValue=mIntent.getStringExtra("Session_ID");
        try {


            List<SessionID> _url =SessionID.findWithQuery(SessionID.class, "SELECT *from SESSION_ID ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    SesssionValue=_url.get(x).getUniqueIdentifier();

                    Toast.makeText(ResponseData.this, "Your session is" + " " +SesssionValue, Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){
            Log.d("SesssionValue", e.getMessage());
        }


   /*     try {

            List<ResponseIntent> savedID = ResponseIntent.findWithQuery(ResponseIntent.class, "SELECT *from RESPONSE_INTENT ORDER BY id DESC LIMIT 1");
            if (savedID.size()==1){
                for (int x=0; x<savedID.size(); x++) {

                    IDvalue = savedID.get(x).getID_extra();
                 //
                    //   SesssionValue = savedID.get(x).getSession_extra();

                    SesssionValue = savedID.get(x).getUniqueIdentifier_extra();

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }*/


      //  Toast.makeText(ResponseData.this, "ID Is"+IDvalue, Toast.LENGTH_SHORT).show();
        recyclerView1 = findViewById(R.id.recyclerViewResponse);
        userResponseEntities= new ArrayList<>();
       // userResponseEntities1= new ArrayList<>();



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(layoutManager);
        // recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1.setHasFixedSize(true);
        adapter = new UserResponseAdapter(this);
       // recyclerView1.addItemDecoration(new DividerItemDecoration(recyclerView1.getContext()));

        adapter.setOnItemClickListener(new UserResponseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {


                Toast.makeText(ResponseData.this, "Coming Soon", Toast.LENGTH_SHORT).show();

              /*  Intent ii=new Intent(ResponseData.this, QuetionsOffline.class);
                ii.putExtra("ID",  userResponseEntity.getIdA());
                ii.putExtra("Quetion",  userResponseEntity.getQuetion_A());
                ii.putExtra("Answer",  userResponseEntity.getOption());
              //  ii.putExtra("Answer",  userResponseEntity.getQ);

                startActivity(ii);*/

            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView1.getContext(), layoutManager.getOrientation());
               // layoutManager.getOrientation());
        recyclerView1.addItemDecoration(dividerItemDecoration);

        recyclerView1.setAdapter(adapter);
        getResponses2();




        //
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Toast.makeText(ResponseData.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                sendAnswersToServer();

            }
        });

    }


    //get Response

    public void getResponses2(){
        userResponses = allQuestionDatabase.userResponseDao().getUserResponsesForuniqueIdentifier(SesssionValue);
        // userResponseEntities1.add(userResponses);
        Log.d("RESPONSE SIZE", String.valueOf(userResponses.size()));

        //try

        for (UserResponseEntity userResponseEntity:userResponses) {
            Log.d("QuetionnaireID", String.valueOf(userResponseEntity.getQuestionnaireId()));
            Log.d("QuetionID", String.valueOf(userResponseEntity.getQuestionId()));
            Log.d("Option", String.valueOf(userResponseEntity.getOption()));

            Log.d("SESSIONid", String.valueOf(userResponseEntity.getSessionid()));

            int questionnaireId = userResponseEntity.getQuestionnaireId();
            int questionId =  userResponseEntity.getQuestionId();
            String answer = userResponseEntity.getOption();
            int answeiD =userResponseEntity.getAnswerId();
            String uniq = userResponseEntity.getUniqueIdentifier();
            String quetion = userResponseEntity.getQuetion_A();
            int session = userResponseEntity.getSessionid();

            int questionType= userResponseEntity.getQuestionType();
            boolean isRequired =userResponseEntity.isRequired();
            String dateValidation =userResponseEntity.getDateValidation();
            boolean isRepeatable =userResponseEntity.isRepeatable();

            // QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity(questionnaireId,questionnaireName, questionnaireDescription, questionnaireCreatedAt, questionnaireNumberOfQuestions, questionnaireActiveTill, questionnaireTargetApp);
            UserResponseEntity userResponseEntity1 = new UserResponseEntity(session, questionType, isRequired, dateValidation, isRepeatable, answeiD, uniq, questionnaireId, questionId, answer,quetion);
            // UserResponseEntity userResponseEntity1 = new UserResponseEntity(userResponseEntity.getQuestionnaireId(), userResponseEntity.getQuestionId(), userResponseEntity.getOption());
            userResponseEntities.add(userResponseEntity1);

            adapter.setUser2(userResponseEntities);
        }
        if (userResponseEntities.isEmpty()){
            getAlert();
            // Toast.makeText(ResponseData.this, "No Responses for this Quetionnaire", Toast.LENGTH_SHORT).show();
        }
    }


    //Alert
    private void getAlert(){


        AlertDialog.Builder builder1 = new AlertDialog.Builder(ResponseData.this);
        builder1.setIcon(R.drawable.logo);
        builder1.setTitle("No Responses for This Questionnaire");
       // builder1.setMessage( zz);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(ResponseData.this, Query2.class);
                        startActivity(intent);
                        finish();

                        //dialog.cancel();
                    }
                });

        /*builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(Config.this, SelectUrls.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });*/

        AlertDialog alert11 = builder1.create();
        alert11.show();

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

        AndroidNetworking.post(z+ Constants.LOGOUT)
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

                                Intent intent = new Intent(ResponseData.this, LoginActivity.class);
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

                            Intent intent = new Intent(ResponseData.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        }
                        else{

                            Toast.makeText(ResponseData.this, ""+error.getErrorBody(), Toast.LENGTH_SHORT).show();


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
    // submit data

        public  void sendAnswersToServer() {
            String auth_token = loggedInUser.getAuth_token();
            try {
                JSONArray responsesArray = new JSONArray();

                // Iterate over the list of user responses and prepare JSON objects
                for (UserResponseEntity userResponse : userResponses) {
                    quer_id = userResponse.getQuestionnaireId();
                    JSONObject responseObj = new JSONObject();
                    responseObj.put("ccc_number", "12345"); // Example field, replace with actual field names
                    responseObj.put("first_name", "victor");
                    responseObj.put("questionnaire_participant_id", 1);
                    responseObj.put("informed_consent", "True");
                    responseObj.put("privacy_policy", "True");
                    responseObj.put("interviewer_statement", "True");

                    JSONArray questionAnswersArray = new JSONArray();
                    // Iterate over the list of question answers for each response
                    for (UserResponseEntity questionAnswer : userResponses) {
                        JSONObject questionAnswerObj = new JSONObject();
                        questionAnswerObj.put("question", questionAnswer.getSessionid());
                        questionAnswerObj.put("answer", questionAnswer.getAnswerId());
                        questionAnswerObj.put("open_text", questionAnswer.getOption());
                        questionAnswersArray.put(questionAnswerObj);
                    }
                    responseObj.put("question_answers", questionAnswersArray);
                    responsesArray.put(responseObj);
                }

                // Create the main JSON object with the questionnaire ID and responses array
                JSONObject requestBody = new JSONObject();
                requestBody.put("questionnaire_id",  quer_id); // Assuming all responses belong to the same questionnaire
                requestBody.put("responses", responsesArray);

                Log.d("SENT DATA", requestBody.toString());
                Log.d("TOKEN", auth_token);

                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization","Token "+ auth_token);

                // Send the JSON object to the server using Volley
                RequestQueue queue = Volley.newRequestQueue(ResponseData.this);
               String URL ="https://psurveyapitest.kenyahmis.org/api/questions/answers/all/";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, requestBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                // Handle response from the server
                                try {
                                    boolean success = response.getBoolean("success");
                                    if (success) {
                                        // Display success message
                                        String message = response.getString("Message");
                                        Toast.makeText(ResponseData.this, message, Toast.LENGTH_SHORT).show();
                                        // TODO: Display the success message
                                    } else {
                                        // Handle unsuccessful response
                                        // TODO: Handle the error case
                                        Toast.makeText(ResponseData.this, "Not successful", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                           // }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        if (error instanceof ServerError && error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            if (statusCode == 500) {
                                // Handle 500 error
                                // Display an error message to the user or log the error
                                Toast.makeText(ResponseData.this, "Server Error with code"+" "+statusCode+ error.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle other types of errors
                                // Display an error message or retry the request
                            }
                        } else {
                            // Handle other types of errors
                            // Display an error message or retry the request
                        }
                        //Log.d("ERROR", error.getLocalizedMessage());

                        // Handle error
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() {
                        return headers;
                    }
                };
                queue.add(jsonObjectRequest);

            } catch (JSONException e) {
                e.printStackTrace();
            }
       // }
}}