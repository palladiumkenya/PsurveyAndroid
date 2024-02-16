package com.mhealthkenya.psurvey.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.models.ActiveSurveys;
import com.mhealthkenya.psurvey.models.ConsentID;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;
import com.mhealthkenya.psurvey.models.SurveyID;
import com.mhealthkenya.psurvey.models.SurveyUnique;
import com.mhealthkenya.psurvey.models.UserResponseEntity2;
import com.mhealthkenya.psurvey.models.auth;
import com.mhealthkenya.psurvey.models.data;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LastConsentData extends AppCompatActivity {
    public String z;

    AllQuestionDatabase allQuestionDatabase;




    ArrayList<String> dataList;
    ArrayList<data>  datas;
    public String dataID;
    String surveyUniqueID;

    private auth loggedInUser;
    private boolean informb, privacyb, stateb;

    int savedquestionnaireId;


    private int questionnaire_participant_id;


    MaterialTextView tv_chosen_survey_title;

    MaterialTextView tv_chosen_survey_introduction;
    Button btn_patient_consent;
    MaterialTextView tv_patient_name;

    MaterialTextView tv_patient_number;
    MaterialTextView patient_id;
    MaterialTextView tv_survey_id;

    TextView informText;
    TextView privacyText;

    CheckBox checkInform;

    CheckBox checkPrivacy;
    CheckBox checkStnt;
    TextInputLayout til_ccc_no;

    TextInputEditText etxt_ccc_no;
    TextInputLayout til_first_name;
    TextInputEditText etxt_first_name;
    Spinner spinner_subjects;
    Button btn_patient_info;





    String[] basic_needs_met = {"Client", "Non-Client"};
    String  basic_needs_met_st_code;
    private String  basic_needs_met_st = "";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_consent_data);

        loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN, auth.class);
        allQuestionDatabase = AllQuestionDatabase.getInstance(this);

         tv_chosen_survey_title =findViewById(R.id.tv_chosen_survey_title);
        tv_chosen_survey_introduction =findViewById(R.id.tv_chosen_survey_introduction);
         btn_patient_consent =findViewById(R.id.btn_patient_consent);
         tv_patient_name=findViewById(R.id.tv_patient_name);
         tv_patient_number=findViewById(R.id.tv_patient_number);
         patient_id=findViewById(R.id.patient_id);
         tv_survey_id=findViewById(R.id.tv_survey_id);



         informText = findViewById(R.id.informText);
         privacyText=findViewById(R.id.privacyText);

          checkInform=findViewById(R.id.checkInform);
          checkPrivacy=findViewById(R.id.checkPrivacy);
          checkStnt=findViewById(R.id.checkStnt);

        //start survey

        til_ccc_no=findViewById(R.id.til_ccc_no);
        etxt_ccc_no=findViewById(R.id.etxt_ccc_no);
        til_first_name=findViewById(R.id.til_f_name);
        etxt_first_name=findViewById(R.id.etxt_first_name);
        spinner_subjects=findViewById(R.id.spinner_subjects);
        btn_patient_info=findViewById(R.id.btn_patient_info);

        try {

            List<SurveyID> savedID = SurveyID.findWithQuery(SurveyID.class, "SELECT *from SurveyID ORDER BY id DESC LIMIT 1");
            if (savedID.size()==1){
                for (int x=0; x<savedID.size(); x++) {
                    savedquestionnaireId = savedID.get(x).getQuetionereID();
                    //  Toast.makeText(QuetionsOffline.this, "surveyID" + " " +savedquestionnaireId, Toast.LENGTH_LONG).show();
                    Log.d("SURVEYIDS", String.valueOf(savedquestionnaireId));

                }


            }

        }catch (Exception e){
            e.printStackTrace();
        }

        try {

            List<SurveyUnique> surveyUniques = SurveyUnique.findWithQuery(SurveyUnique.class, "SELECT *from SurveyUnique ORDER BY id DESC LIMIT 1");
            if (surveyUniques.size()==1){
                for (int x=0; x<surveyUniques.size(); x++) {
                    surveyUniqueID = surveyUniques.get(x).getSurveyUnik();
                    //  Toast.makeText(QuetionsOffline.this, "surveyID" + " " +savedquestionnaireId, Toast.LENGTH_LONG).show();
                    Log.d("SURVEYIDS", String.valueOf(surveyUniqueID));

                }


            }

        }catch (Exception e){
            e.printStackTrace();
        }


        informText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LastConsentData.this, InformedActivity.class);
                startActivity(intent);

            }
        });

        privacyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LastConsentData.this, PrivacyActivity.class);
                startActivity(intent);

            }
        });

        //checkboxes

        if(checkInform.isChecked())
        {
            informb= Boolean.parseBoolean(checkInform.getText().toString());
        }


        if(checkPrivacy.isChecked())
        {
            //description=checkPrivacy.getText().toString();
            privacyb = Boolean.parseBoolean(checkPrivacy.getText().toString());

        }

        if(checkStnt.isChecked())
        {
            stateb = Boolean.parseBoolean(checkStnt.getText().toString());
        }

        //dropdown

        //needsS
        ArrayAdapter<String> needsAdapter = new ArrayAdapter<String>(LastConsentData.this, android.R.layout.simple_spinner_item,  basic_needs_met);
        needsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_subjects.setAdapter(needsAdapter);
        spinner_subjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                basic_needs_met_st = basic_needs_met[position];

                dataID = Integer.toString(position);
              //  dataID=Integer.


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_patient_consent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

         /*       if (!dataID.contentEquals("Client") ||!dataID.contentEquals("Non-Client")){

                    //Snackbar.make(root.findViewById(R.id.frag_patient_consent), "Invalid", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(LastConsentData.this, "Invalid", Toast.LENGTH_LONG).show();

                }*/



                // confirmConsent(activeSurveys.getId(),ccc_no,f_name, Boolean.parseBoolean(checkInform.getText().toString()), Boolean.parseBoolean(checkPrivacy.getText().toString()), Boolean.parseBoolean(checkStnt.getText().toString()));
                 if(!checkInform.isChecked() || !checkPrivacy.isChecked() || !checkStnt.isChecked())
                {
                     Toast.makeText(LastConsentData.this, "Please consent first", Toast.LENGTH_SHORT).show();
                   // Snackbar.make(root.findViewById(R.id.frag_patient_consent), "Please consent first", Snackbar.LENGTH_LONG).show();


                }else {



                     long generatedId1 =save1(savedquestionnaireId, surveyUniqueID,"12345", "Vic", 1, informb, privacyb, stateb);




                     try {
                         ConsentID.deleteAll(ConsentID.class);
                         ConsentID consentID = new ConsentID(generatedId1);
                         consentID.save();


                     }catch (Exception e){
                         e.printStackTrace();
                     }

                    Intent intent = new Intent(LastConsentData.this, QuetionsOffline.class);
                   // intent.putExtra("generatedid", generatedId1);
                    startActivity(intent);

                }

            }
        });





    }
    public long save1( int savedquestionnaireId1, String surveyUniqueID1, String cccNumber, String firstName, int questionnaireParticipantId,
                      boolean informedConsent, boolean privacyPolicy, boolean interviewerStatement){

        UserResponseEntity2 userResponseEntity2 =new UserResponseEntity2();
        userResponseEntity2.setCccNumber(cccNumber);
       // userResponseEntity2.setCccid(ccid);
        userResponseEntity2.setSurveyUniqueID(surveyUniqueID1);
        userResponseEntity2.setSavedquestionnaireId(savedquestionnaireId1);
        userResponseEntity2.setInterviewerStatement(interviewerStatement);
        userResponseEntity2.setInformedConsent(informedConsent);
        userResponseEntity2.setPrivacyPolicy(privacyPolicy);
        userResponseEntity2.setFirstName(firstName);
        userResponseEntity2.setQuestionnaireParticipantId(questionnaireParticipantId);

       //  allQuestionDatabase.userResponseDao().insertResponse2(userResponseEntity2);

        long generatedId = allQuestionDatabase.userResponseDao().insertResponse2(userResponseEntity2);



        return generatedId;
       // Toast.makeText("Data", String.valueOf(generatedId), Toast.LENGTH_LONG).show();




    }
}