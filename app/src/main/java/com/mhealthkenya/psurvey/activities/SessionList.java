package com.mhealthkenya.psurvey.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.adapters.QuestionnairesAdapterOffline;
import com.mhealthkenya.psurvey.adapters.SessionListAdapter;
import com.mhealthkenya.psurvey.models.AnswerEntity;
import com.mhealthkenya.psurvey.models.QuestionEntity;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;
import com.mhealthkenya.psurvey.models.UniqueIdentifierEntity;
import com.mhealthkenya.psurvey.models.UserResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class SessionList extends AppCompatActivity {

    AllQuestionDatabase allQuestionDatabase;


    public SessionListAdapter sessionListAdapter;
    public UserResponseEntity userResponseEntity;
    public ArrayList<UniqueIdentifierEntity> userResponseEntities;

    RecyclerView recyclerView;
    UserResponseEntity userResponseEntity2;

    List<UniqueIdentifierEntity> userResponses;

    int IDvalue;


    int questionnaireId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_list);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Session List");


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

        Intent mIntent = getIntent();
        IDvalue = mIntent.getIntExtra("Quetionnaire_ID", 0);

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
     //       getAlert();
            // Toast.makeText(ResponseData.this, "No Responses for this Quetionnaire", Toast.LENGTH_SHORT).show();
        }
    }
}