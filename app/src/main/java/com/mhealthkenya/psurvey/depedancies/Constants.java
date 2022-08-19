package com.mhealthkenya.psurvey.depedancies;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.activities.auth.LoginActivity;

public class Constants extends AppCompatActivity {


    //public static int dataID;

    /*ENDPOINT*/
    //public static String ENDPOINT = "https://psurveyapi.kenyahmis.org/";
    //public static String ENDPOINT="https://prod.kenyahmis.org:9100/";
    //Test -https://prod.kenyahmis.org:9000
    //Prod -https://psurvey-api.mhealthkenya.co.ke
    //public static String ENDPOINT = "https://psurvey-api.mhealthkenya.co.ke";

    public static String ENDPOINT= "";

    public static String STAGE_NAME= "";


    //AUTH
    public static String SIGNUP = "/auth/users/";
    public static String CURRENT_USER = "/auth/users/me";
    public static String CURRENT_USER_DETAILED = "/api/current/user";
    public static String UPDATE_USER = "/auth/users/me/";
    public static String LOGIN = "/auth/token/login";
    public static String LOGOUT = "/auth/token/logout/";



    //FACILITIES AND DESIGNATIONS
    public static String ALL_FACILITIES = "/api/facilities";
    public static String DESIGNATION = "/api/designation";



    //QUESTIONNAIRES
    public static String ACTIVE_SURVEYS = "/api/questionnaire/active";
    public static String ALL_QUESTIONNAIRES = "/api/questionnaire/all";
    public static String PATIENT_CONSENT = "/api/questionnaire/start/";
    public static String PROVIDE_ANSWER = "/api/questions/answer/";
    public static String INITIAL_CONFIRMATION = "/api/initial/consent/";
    public static String GET_PARTICIPANTS ="/api/questionnaire/participants/";


    /*MODELS*/
    public static String AUTH_TOKEN = "";



    @Override
    public void onBackPressed() {
        finishAffinity();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constants);

        TextView x =findViewById(R.id.show);
       // Button xx =findViewById(R.id.show1);

        //String z;

        Bundle bundle =getIntent().getExtras();
        ENDPOINT= bundle.getString("url");
        STAGE_NAME =bundle.getString("stage_key");
        getAlert();

        x.setText("You are connected to" + " " +STAGE_NAME + " " + "Server!");
        //Toast.makeText(Config.this, BASE_URL, Toast.LENGTH_LONG).show();
        x.setTextColor(Color.parseColor("#F32013"));

    }

    private void getAlert(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Constants.this);
        builder1.setIcon(android.R.drawable.ic_dialog_alert);
        builder1.setTitle("You are connected to");
        builder1.setMessage( STAGE_NAME + " " + "Server!");
        builder1.setCancelable(false);
       // builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Proceed",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(Constants.this, LoginActivity.class);
                        startActivity(intent);

                        //dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(Constants.this, SelectUrls.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    }

