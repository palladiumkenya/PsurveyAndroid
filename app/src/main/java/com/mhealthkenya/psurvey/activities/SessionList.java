package com.mhealthkenya.psurvey.activities;

import static com.mhealthkenya.psurvey.depedancies.AppController.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import com.mhealthkenya.psurvey.adapters.QuestionnairesAdapterOffline;
import com.mhealthkenya.psurvey.adapters.SessionListAdapter;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.models.AnswerEntity;
import com.mhealthkenya.psurvey.models.QuestionEntity;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;
import com.mhealthkenya.psurvey.models.QuetionnaireID;
import com.mhealthkenya.psurvey.models.ResponseIntent;
import com.mhealthkenya.psurvey.models.UniqueIdentifierEntity;
import com.mhealthkenya.psurvey.models.UrlTable;
import com.mhealthkenya.psurvey.models.UserResponseEntity;
import com.mhealthkenya.psurvey.models.UserResponseEntity2;
import com.mhealthkenya.psurvey.models.auth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionList extends AppCompatActivity {

    AllQuestionDatabase allQuestionDatabase;


    public SessionListAdapter sessionListAdapter;
    public UserResponseEntity userResponseEntity;
    public ArrayList<UniqueIdentifierEntity> userResponseEntities;

    RecyclerView recyclerView;
    UserResponseEntity userResponseEntity2;

    List<UniqueIdentifierEntity> userResponses;

    int IDvalue;

    public String z;
    private auth loggedInUser;




    int questionnaireId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_list);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Response List");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Set the toolbar navigation icon click listener
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Handle the back arrow click to navigate to the previous activity
            }
        });


        allQuestionDatabase = AllQuestionDatabase.getInstance(this);
        loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN, auth.class);

       // Intent mIntent = getIntent();
        //IDvalue = mIntent.getIntExtra("Quetionnaire_ID", 0);

        try {


            List<QuetionnaireID> _url =QuetionnaireID.findWithQuery(QuetionnaireID.class, "SELECT *from QUETIONNAIRE_ID ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    IDvalue=_url.get(x).getQuetioonareID();

                    Toast.makeText(SessionList.this, "Your Quetionnaire_ID is" + " " +IDvalue, Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){
            Log.d(" IDvalue", e.getMessage());
        }

     /*   try {

            List<ResponseIntent> savedID = ResponseIntent.findWithQuery(ResponseIntent.class, "SELECT *from RESPONSE_INTENT ORDER BY id DESC LIMIT 1");
            if (savedID.size()==1) {
                for (int x = 0; x < savedID.size(); x++) {

                    IDvalue = savedID.get(x).getID_extra();

                    Toast.makeText(SessionList.this, "No Responses for this Quetionnaire"+IDvalue, Toast.LENGTH_SHORT).show();


                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/

        //adapter
        recyclerView=findViewById(R.id.recyclerViewSession);
        userResponseEntities = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        sessionListAdapter = new SessionListAdapter(this);
        recyclerView.setAdapter(sessionListAdapter);

        sessionListAdapter.setOnItemClickListener(new SessionListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {


                Intent ii=new Intent(SessionList.this, ResponseData.class);

                startActivity(ii);

            }
        });


 RetrieveQuestionnaire1();
    }

    public void RetrieveQuestionnaire1(){

        userResponses = allQuestionDatabase.userResponseDao().getSessions(IDvalue);
        // userResponseEntities1.add(userResponses);
        Log.d("RESPONSE SIZE", String.valueOf(userResponses.size()));

        //try

        for (UniqueIdentifierEntity userResponseEntity:userResponses) {
            Log.d("QuetionnaireID", String.valueOf(userResponseEntity.getUniqueIdentifier()));

            String uniq = userResponseEntity.getUniqueIdentifier();


            // QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity(questionnaireId,questionnaireName, questionnaireDescription, questionnaireCreatedAt, questionnaireNumberOfQuestions, questionnaireActiveTill, questionnaireTargetApp);
            UniqueIdentifierEntity userResponseEntity1 = new UniqueIdentifierEntity(uniq);
            // UserResponseEntity userResponseEntity1 = new UserResponseEntity(userResponseEntity.getQuestionnaireId(), userResponseEntity.getQuestionId(), userResponseEntity.getOption());
            userResponseEntities.add(userResponseEntity1);

            sessionListAdapter.setUser(userResponseEntities);
        }
        if (userResponseEntities.isEmpty()){
            getAlert();
            // Toast.makeText(ResponseData.this, "No Responses for this Quetionnaire", Toast.LENGTH_SHORT).show();
        }
    }

    //Alert
    private void getAlert(){


        AlertDialog.Builder builder1 = new AlertDialog.Builder(SessionList.this);
        builder1.setIcon(R.drawable.logo);
        builder1.setTitle("No Responses for This Questionnaire"+IDvalue);
        // builder1.setMessage( zz);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(SessionList.this, Query2.class);
                        startActivity(intent);
                 //       finish();

                        //dialog.cancel();
                    }
                });

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

                                Intent intent = new Intent(SessionList.this, LoginActivity.class);
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

                            Intent intent = new Intent(SessionList.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        }
                        else{

                            Toast.makeText(SessionList.this, ""+error.getErrorBody(), Toast.LENGTH_SHORT).show();


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


}