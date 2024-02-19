package com.mhealthkenya.psurvey.activities;

import static com.mhealthkenya.psurvey.depedancies.AppController.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import android.os.Build;
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
import com.google.android.material.snackbar.Snackbar;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.activities.auth.LoginActivity;
import com.mhealthkenya.psurvey.adapters.QuestionnairesAdapterOffline;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.interfaces.AnswerDao;
import com.mhealthkenya.psurvey.interfaces.QuestionDao;
import com.mhealthkenya.psurvey.interfaces.QuestionnaireDao;
import com.mhealthkenya.psurvey.models.AnswerEntity;
import com.mhealthkenya.psurvey.models.AvailableSurveys;
import com.mhealthkenya.psurvey.models.QuestionEntity;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;
import com.mhealthkenya.psurvey.models.SessionOffline;
import com.mhealthkenya.psurvey.models.SurveyID;
import com.mhealthkenya.psurvey.models.SurveyUnique;
import com.mhealthkenya.psurvey.models.UrlTable;
import com.mhealthkenya.psurvey.models.auth;
import com.orm.SugarContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Query2 extends AppCompatActivity {
    ProgressBar progressBar;
    int currentProgress=0;

    public String z;

    JSONObject jsonObject;
    Handler mHandler;

    private auth loggedInUser;

    private RequestQueue requestQueue;
    Button button;
    AllQuestionDatabase allQuestionDatabase;

    //adapter
    public QuestionnairesAdapterOffline questionnairesAdapterOffline;
    public QuestionnaireEntity questionnaireEntity;
    public ArrayList<QuestionnaireEntity> questionnaireEntities;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query2);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Facility's Survey");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Set the toolbar navigation icon click listener
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Handle the back arrow click to navigate to the previous activity
            }
        });

        loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN, auth.class);


        progressBar = findViewById(R.id.progress);

        allQuestionDatabase = AllQuestionDatabase.getInstance(this);
        mHandler=new Handler();

        requestQueue = Volley.newRequestQueue(this);
        button =findViewById(R.id.get_all);

        //adapter
        recyclerView=findViewById(R.id.recyclerViewOffline);
        questionnaireEntities = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        questionnairesAdapterOffline = new QuestionnairesAdapterOffline(this);
        recyclerView.setAdapter(questionnairesAdapterOffline);

        questionnairesAdapterOffline.setOnItemClickListener(new QuestionnairesAdapterOffline.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(int position) {
                String sessionIdentifier2 = UUID.randomUUID().toString();


                // Get current date and time
                LocalDateTime now = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    now = LocalDateTime.now();
                }

                // Format date and time
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd_HH:mm:ss");
                String formattedDateTime = now.format(formatter);

                // Format date and time2
                DateTimeFormatter formatter22 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDateTime22 = now.format(formatter22);

                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
                String formattedDateTime2b = now.format(formatter2);



                // Append formatted date and time to UUID
                String sessionIdentifier1 = sessionIdentifier2 + "_" + formattedDateTime;

                String sessionIdentifier22 = "Survey Administered on " + " " + formattedDateTime22 + " " + "at"+ " "+ formattedDateTime2b ;

                QuestionnaireEntity questionnaireEntity =questionnaireEntities.get(position);
                try {
                    SurveyID.deleteAll(SurveyID.class);
                    SurveyID surveyID = new SurveyID( questionnaireEntity.getId());
                    surveyID.save();


                    SurveyUnique.deleteAll(SurveyUnique.class);
                    SurveyUnique surveyUnique = new SurveyUnique(sessionIdentifier22);
                    surveyUnique.save();

                }catch (Exception e){
                    e.printStackTrace();
                }



     /*           Intent ii=new Intent(Query2.this, QuetionsOffline.class);
             //   ii.putExtra("ID",  questionnaireEntity.getId());
                startActivity(ii);*/

                Intent ii=new Intent(Query2.this,LastConsentData.class);
                //   ii.putExtra("ID",  questionnaireEntity.getId());
                startActivity(ii);


            }
        });


       // RetrieveQuestionnaire();
        new RetrieveQuestionnaireTask(allQuestionDatabase, questionnaireEntities, questionnairesAdapterOffline).execute();


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



            }






        }
    }



    //end background


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

                                Intent intent = new Intent(Query2.this, LoginActivity.class);
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

                            Intent intent = new Intent(Query2.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        }
                        else{

                            Toast.makeText(Query2.this, ""+error.getErrorBody(), Toast.LENGTH_SHORT).show();


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

            Intent intent = new Intent(Query2.this, offlineHome.class);
            startActivity(intent);
            //        super.onBackPressed();


    }

}