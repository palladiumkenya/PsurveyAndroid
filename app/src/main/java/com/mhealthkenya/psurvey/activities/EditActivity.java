package com.mhealthkenya.psurvey.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.models.Answer;
import com.mhealthkenya.psurvey.models.AnswerEntity;
import com.mhealthkenya.psurvey.models.QuestionEntity;
import com.mhealthkenya.psurvey.models.ResponseIntent;
import com.mhealthkenya.psurvey.models.SessionOffline;
import com.mhealthkenya.psurvey.models.SurveyID;
import com.mhealthkenya.psurvey.models.SurveyUnique;
import com.mhealthkenya.psurvey.models.UserResponseEntity;
import com.mhealthkenya.psurvey.models.repeat_count;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditActivity extends AppCompatActivity {
    // Assume draftAnswers is a List<UserResponseEntity> for storing draft answers
    private List<UserResponseEntity> draftAnswers;
    String surveyUniqueID;
    // public  int index =0;
    String optionR;
    String answerR;
    int IDvalue;
    int idt1;
    AllQuestionDatabase allQuestionDatabase;
    Button btnNext;

    MaterialTextView surveyQuestion;
    Button nextButton;
    List<QuestionEntity> questions;
    int currentQuestionIndex;
    int questionnaireId;
    AnswerEntity answerEntity;


    DatePickerDialog datePickerDialog;
    String openText = "";

    int repeat_count;
    CheckBox checkBox;

    com.mhealthkenya.psurvey.models.repeat_count repeat_count1;
    com.mhealthkenya.psurvey.models.repeat_count _repeat_count;
    Answer answers;
    ArrayList<AnswerEntity> answerList = new ArrayList<>();

    List<Integer> multiAnswerList = new ArrayList<>();

    int mYear, mMonth, mDay;
    int savedquestionnaireId;


    //openText1
    TextInputLayout openTextTil;
    TextInputEditText openTextEtxt;

    //openText2
    TextInputLayout openTextTil2;
    TextInputEditText openTextEtxt2;

    //openText3
    TextInputLayout openTextTil3;
    TextInputEditText openTextEtxt3;

    //openText4
    TextInputLayout openTextTil4;
    TextInputEditText openTextEtxt4;

    //openText5
    TextInputLayout openTextTil5;
    TextInputEditText openTextEtxt5;

    //openText6
    TextInputLayout openTextTil6;
    TextInputEditText openTextEtxt6;


    //dateNone1
    TextInputLayout dateTextTil;
    TextInputEditText dobEditText;
    //dateNone2
    TextInputLayout dateTextTil2;
    TextInputEditText dobEditText2;
    //dateNone3
    TextInputLayout dateTextTil3;
    TextInputEditText dobEditText3;
    //dateNone4
    TextInputLayout dateTextTil4;
    TextInputEditText dobEditText4;
    //dateNone5
    TextInputLayout dateTextTil5;
    TextInputEditText dobEditText5;
    //dateNone6
    TextInputLayout dateTextTil6;
    TextInputEditText dobEditText6;


    //datefuture1
    TextInputLayout dateTextTilfuture;
    TextInputEditText dobEditTextfuture;

    //datefuture2
    TextInputLayout dateTextTilfuture2;
    TextInputEditText dobEditTextfuture2;

    //datefuture3
    TextInputLayout dateTextTilfuture3;
    TextInputEditText dobEditTextfuture3;

    //datefuture4
    TextInputLayout dateTextTilfuture4;
    TextInputEditText dobEditTextfuture4;

    //datefuture5
    TextInputLayout dateTextTilfuture5;
    TextInputEditText dobEditTextfuture5;

    //datefuture6
    TextInputLayout dateTextTilfuture6;
    TextInputEditText dobEditTextfuture6;


    //datepast1
    TextInputLayout dateTextTilpast;
    TextInputEditText dobEditTextpast;

    //datepast2
    TextInputLayout dateTextTilpast2;
    TextInputEditText dobEditTextpast2;

    //datepast3
    TextInputLayout dateTextTilpast3;
    TextInputEditText dobEditTextpast3;

    //datepast4
    TextInputLayout dateTextTilpast4;
    TextInputEditText dobEditTextpast4;

    //datepast5
    TextInputLayout dateTextTilpast5;
    TextInputEditText dobEditTextpast5;

    //datepast6
    TextInputLayout dateTextTilpast6;
    TextInputEditText dobEditTextpast6;


    TextInputLayout numericText;
    TextInputEditText numericEditText;


    RadioGroup singleChoiceRadioGroup;
    LinearLayout multipleChoiceAns;

    CoordinatorLayout coordinatorLyt;
    ShimmerFrameLayout shimmer_my_container;

    RecyclerView recyclerView;
    LinearLayout no_active_survey_lyt;

    LinearLayout error_lyt;

    int ID_extra, session_extra, answID_extra, quetnID_extra, qtype_extra,QuestionnaireId_extra;
    String Quetion_extra, Option_extra, UniqueIdentifier_extra, dateValidation_extra;
    boolean isRequired_extra, isRepeatable_extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        try {

            List<ResponseIntent> savedID = ResponseIntent.findWithQuery(ResponseIntent.class, "SELECT *from RESPONSE_INTENT ORDER BY id DESC LIMIT 1");
            if (savedID.size()==1){
                for (int x=0; x<savedID.size(); x++) {
                    ID_extra = savedID.get(x).getID_extra();
                    session_extra = savedID.get(x).getSession_extra();
                    answID_extra = savedID.get(x).getAnswID_extra();
                    quetnID_extra = savedID.get(x).getQuetnID_extra();
                    qtype_extra = savedID.get(x).getQtype_extra();
                    QuestionnaireId_extra = savedID.get(x).getQuestionnaireId_extra();
                    Log.d("SURVEYIDS", String.valueOf(QuestionnaireId_extra));

                    Quetion_extra = savedID.get(x).getQuetion_extra();
                    Option_extra = savedID.get(x).getOption_extra();
                    UniqueIdentifier_extra = savedID.get(x).getUniqueIdentifier_extra();
                    dateValidation_extra = savedID.get(x).getDateValidation_extra();
                    isRequired_extra = savedID.get(x).isRequired_extra();;
                    isRepeatable_extra = savedID.get(x).isRepeatable_extra();

                }


            }

        }catch (Exception e){
            e.printStackTrace();
        }

        answerEntity = new AnswerEntity();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Response");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Set the toolbar navigation icon click listener
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Handle the back arrow click to navigate to the previous activity
            }
        });
        initialize();

        //set EditText type4 to accept numeric only
        numericEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

        //DatePickerNone1
        dobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                //show dialog
                datePickerDialog = new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        dobEditText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        //DatePickerNone2
        dobEditText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                //show dialog
                datePickerDialog = new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        dobEditText2.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        //DatePickerNone3
        dobEditText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                //show dialog
                datePickerDialog = new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        dobEditText3.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        //DatePickerNone4
        dobEditText4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                //show dialog
                datePickerDialog = new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        dobEditText4.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        //DatePickerNone5
        dobEditText5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                //show dialog
                datePickerDialog = new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        dobEditText5.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        //DatePickerNone6
        dobEditText6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                //show dialog
                datePickerDialog = new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        dobEditText6.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        //  answerList = new ArrayList<>();
        // surveyQuestion = (MaterialTextView) findViewById(R.id.tv_survey_question);
        btnNext = (Button) findViewById(R.id.btn_next);
        allQuestionDatabase = AllQuestionDatabase.getInstance(this);





        //Retrieve survey unique identifiers

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

        // Retrieve questions for the specified questionnaire

        questions = allQuestionDatabase.questionDao().getQuestionsByQuestionnaireId(QuestionnaireId_extra);



        // Display the question based on the index
        displayQuestion();



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                QuestionEntity question = questions.get(currentQuestionIndex);



                if ( qtype_extra == 1 && isRequired_extra) {

                    if (openTextEtxt.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    } else {

                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex,  openTextEtxt.getText().toString(), question.getQuestion());
                      //  SaveAnswers(question.getQuestionOrder(),question.getQuestionType(),question.isRequired(), question.getDateValidation(), question.isRepeatable(), answerEntity.getId(), surveyUniqueID, savedquestionnaireId, currentQuestionIndex,  openTextEtxt.getText().toString(), question.getQuestion());
                        //saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex,  openTextEtxt.getText().toString(), question.getQuestion());



                    }
                } else if (qtype_extra == 1) {
                   // SaveAnswers(question.getQuestionOrder(),answerEntity.getId(), surveyUniqueID, savedquestionnaireId, currentQuestionIndex, openTextEtxt.getText().toString(), question.getQuestion());
                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex,  openTextEtxt.getText().toString(), question.getQuestion());

                } else if (qtype_extra == 4 && isRequired_extra) {
                    if (numericEditText.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        // }else{
                    } else {

                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, numericEditText.getText().toString(), question.getQuestion());

                    }
                } else if (qtype_extra == 4) {

                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, numericEditText.getText().toString(),question.getQuestion());

                }
                //datepicker none & not repeatable
                else if (qtype_extra == 5 && isRequired_extra && dateValidation_extra.equals("none") && !isRepeatable_extra) {
                    if (dobEditText.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        //&& questions.getDate_validation().contentEquals("none")
                    }
                    {

                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditText.getText().toString(), question.getQuestion());

                    }

                } else if (qtype_extra == 5 && dateValidation_extra.equals("none") && !isRepeatable_extra) {
                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditText.getText().toString(), question.getQuestion());


                }

                //datepicker none & not repeatable
                else if (qtype_extra== 5 && isRequired_extra && dateValidation_extra.equals("none") && isRepeatable_extra && repeat_count == 1) {
                    if (dobEditText.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        //&& questions.getDate_validation().contentEquals("none")
                    }
                    {
                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditText.getText().toString(), question.getQuestion());
                        //saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString(), question.getQuestion());
                    }

                } else if (qtype_extra == 5 && dateValidation_extra.equals("none") && isRepeatable_extra && repeat_count == 1) {
                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditText.getText().toString(), question.getQuestion());
                    //saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString(), question.getQuestion());

                }
                //datepicker none & repeatable count2
                else if (qtype_extra == 5 && isRequired_extra && dateValidation_extra.equals("none") && isRepeatable_extra && repeat_count == 2) {
                    if (dobEditText.getText().toString().equals("") || dobEditText2.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        //&& questions.getDate_validation().contentEquals("none")
                    }
                    {
                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString(), question.getQuestion());
                        //saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString(), question.getQuestion());
                    }

                } else if (qtype_extra == 5 && dateValidation_extra.equals("none") && isRepeatable_extra && repeat_count == 2) {
                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString(), question.getQuestion());
                    //saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString(), question.getQuestion());

                }

                // datepicker none & repeatable count3
                else if (qtype_extra == 5 && isRequired_extra && dateValidation_extra.equals("none") && isRepeatable_extra && repeat_count == 3) {
                    if (dobEditText.getText().toString().equals("") || dobEditText2.getText().toString().equals("") || dobEditText3.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        //&& questions.getDate_validation().contentEquals("none")
                    }
                    {
                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString() + " ," + dobEditText3.getText().toString(), question.getQuestion());
                        //saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString() + " ," + dobEditText3.getText().toString(), question.getQuestion());

                    }

                } else if (qtype_extra == 5 && dateValidation_extra.equals("none") && isRepeatable_extra && repeat_count == 3) {
                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString() + " ," + dobEditText3.getText().toString(), question.getQuestion());
                    // saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString() + " ," + dobEditText3.getText().toString(), question.getQuestion());


                }
                // datepicker none & repeatable count4
                else if (qtype_extra == 5 && isRequired_extra && dateValidation_extra.equals("none") && isRequired_extra && repeat_count == 4) {
                    if (dobEditText.getText().toString().equals("") || dobEditText2.getText().toString().equals("") || dobEditText3.getText().toString().equals("") || dobEditText4.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        //&& questions.getDate_validation().contentEquals("none")
                    }
                    {
                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString().equals("") + " ," + dobEditText3.getText().toString().equals("") + " ," + dobEditText4.getText().toString().equals(""), question.getQuestion());
                        // saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString().equals("") + " ," + dobEditText3.getText().toString().equals("") + " ," + dobEditText4.getText().toString().equals(""), question.getQuestion());

                    }

                } else if (qtype_extra == 5 && dateValidation_extra.equals("none") && isRepeatable_extra && repeat_count1.getRepeat_count() == 4) {
                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString().equals("") + " ," + dobEditText3.getText().toString().equals("") + " ," + dobEditText4.getText().toString().equals(""), question.getQuestion());
                    //saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString().equals("") + " ," + dobEditText3.getText().toString().equals("") + " ," + dobEditText4.getText().toString().equals(""), question.getQuestion());
                }
                // datepicker none & repeatable count5
                else if (qtype_extra == 5 && isRequired_extra && dateValidation_extra.equals("none") && isRepeatable_extra && repeat_count == 5) {
                    if (dobEditText.getText().toString().equals("") || dobEditText2.getText().toString().equals("") || dobEditText3.getText().toString().equals("") || dobEditText4.getText().toString().equals("") || dobEditText5.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        //&& questions.getDate_validation().contentEquals("none")
                    }
                    {
                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString().equals("") + " ," + dobEditText3.getText().toString().equals("") + " ," + dobEditText4.getText().toString().equals("") + " ," + dobEditText5.getText().toString().equals(""), question.getQuestion());
                        // saveAnswersToDraft(savedquestionnaireI d, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString().equals("") + " ," + dobEditText3.getText().toString().equals("") + " ," + dobEditText4.getText().toString().equals("") + " ," + dobEditText5.getText().toString().equals(""), question.getQuestion());
                    }

                } else if (qtype_extra == 5 && dateValidation_extra.equals("none") && isRequired_extra && repeat_count == 5) {
                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString().equals("") + " ," + dobEditText3.getText().toString().equals("") + " ," + dobEditText4.getText().toString().equals("") + " ," + dobEditText5.getText().toString().equals(""), question.getQuestion());
                    //saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString().equals("") + " ," + dobEditText3.getText().toString().equals("") + " ," + dobEditText4.getText().toString().equals("") + " ," + dobEditText5.getText().toString().equals(""), question.getQuestion());



                }

                // datepicker none & repeatable count6
                else if (qtype_extra == 5 && isRequired_extra && dateValidation_extra.equals("none") && isRepeatable_extra && repeat_count == 6) {
                    if (dobEditText.getText().toString().equals("") || dobEditText2.getText().toString().equals("") || dobEditText3.getText().toString().equals("") || dobEditText4.getText().toString().equals("") || dobEditText4.getText().toString().equals("") || dobEditText5.getText().toString().equals("") || dobEditText6.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        //&& questions.getDate_validation().contentEquals("none")
                    }
                    {
                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString().equals("") + " ," + dobEditText3.getText().toString().equals("") + " ," + dobEditText4.getText().toString().equals("") + " ," + dobEditText5.getText().toString().equals("") + " ," + dobEditText6.getText().toString().equals(""), question.getQuestion());
                        //saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString().equals("") + " ," + dobEditText3.getText().toString().equals("") + " ," + dobEditText4.getText().toString().equals("") + " ," + dobEditText5.getText().toString().equals("") + " ," + dobEditText6.getText().toString().equals(""), question.getQuestion());
                    }

                } else if (qtype_extra == 5 && dateValidation_extra.equals("none") && isRepeatable_extra && repeat_count == 6) {
                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString().equals("") + " ," + dobEditText3.getText().toString().equals("") + " ," + dobEditText4.getText().toString().equals("") + " ," + dobEditText5.getText().toString().equals("") + " ," + dobEditText6.getText().toString().equals(""), question.getQuestion());
                    //saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString().equals("") + " ," + dobEditText3.getText().toString().equals("") + " ," + dobEditText4.getText().toString().equals("") + " ," + dobEditText5.getText().toString().equals("") + " ," + dobEditText6.getText().toString().equals(""), question.getQuestion());

                }

                //datepicker restrict future notrrepeat

                else if (qtype_extra == 5 && isRequired_extra && dateValidation_extra.equals("restrict_future") && !isRepeatable_extra) {
                    if (dobEditTextfuture.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    } else {
                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextfuture.getText().toString(), question.getQuestion());
                        // saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString(), question.getQuestion());
                    }

                } else if (qtype_extra == 5 && !isRequired_extra && dateValidation_extra.equals("restrict_future") && !isRepeatable_extra) {
                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextfuture.getText().toString(), question.getQuestion());
                    // saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString(), question.getQuestion());

                }


                //datepicker restrict future repeat count one
                else if (qtype_extra == 5 && isRequired_extra && dateValidation_extra.equals("restrict_future") && isRepeatable_extra && repeat_count == 1) {
                    if (dobEditTextfuture.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    } else {
                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextfuture.getText().toString(), question.getQuestion());
                        // saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString(), question.getQuestion());
                    }

                } else if (qtype_extra == 5 && !isRequired_extra && dateValidation_extra.equals("restrict_future") && isRepeatable_extra && repeat_count == 1) {
                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString(), question.getQuestion());

                }

                //datepicker restrict future repeat count 2
                else if (qtype_extra == 5 && isRequired_extra && dateValidation_extra.equals("restrict_future") && isRepeatable_extra && repeat_count == 2) {
                    if (dobEditTextfuture.getText().toString().equals("") || dobEditTextfuture2.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    } else {
                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString(), question.getQuestion());
                        // saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString(), question.getQuestion());
                    }

                } else if (qtype_extra == 5 && !isRequired_extra && dateValidation_extra.equals("restrict_future") && isRepeatable_extra && repeat_count == 2) {
                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString(), question.getQuestion());
                    // saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString(), question.getQuestion());

                }
                //datepicker restrict future repeat count 3
                else if (qtype_extra == 5 && isRequired_extra && dateValidation_extra.equals("restrict_future") && isRepeatable_extra && repeat_count == 3) {
                    if (dobEditTextfuture.getText().toString().equals("") || dobEditTextfuture2.getText().toString().equals("") || dobEditTextfuture3.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    } else {
                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString(), question.getQuestion());
                        // saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString(), question.getQuestion());
                    }

                } else if (qtype_extra == 5 && !isRequired_extra && dateValidation_extra.equals("restrict_future") && isRepeatable_extra && repeat_count == 3) {
                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString(),question.getQuestion());
                    // saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString(),question.getQuestion());


                }


                //datepicker restrict future repeat count 4
                else if (qtype_extra == 5 && isRequired_extra && dateValidation_extra.equals("restrict_future") && isRepeatable_extra && repeat_count == 4) {
                    if (dobEditTextfuture.getText().toString().equals("") || dobEditTextfuture2.getText().toString().equals("") || dobEditTextfuture3.getText().toString().equals("") || dobEditTextfuture4.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    } else {
                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString() + " ," + dobEditTextfuture4.getText().toString(), question.getQuestion());
                        //saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString() + " ," + dobEditTextfuture4.getText().toString(), question.getQuestion());
                    }

                } else if (qtype_extra == 5 && !isRequired_extra && dateValidation_extra.equals("restrict_future") && isRepeatable_extra && repeat_count == 4) {
                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString() + " ," + dobEditTextfuture4.getText().toString(), question.getQuestion());
                    // saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString() + " ," + dobEditTextfuture4.getText().toString(), question.getQuestion());

                }

                //datepicker restrict future repeat count 5
                else if (qtype_extra == 5 && isRequired_extra && dateValidation_extra.equals("restrict_future") && isRepeatable_extra && repeat_count == 5) {
                    if (dobEditTextfuture.getText().toString().equals("") || dobEditTextfuture2.getText().toString().equals("") || dobEditTextfuture3.getText().toString().equals("") || dobEditTextfuture4.getText().toString().equals("") || dobEditTextfuture5.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    } else {
                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString() + " ," + dobEditTextfuture4.getText().toString() + " ," + dobEditTextfuture5.getText().toString(), question.getQuestion());
                        // saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString() + " ," + dobEditTextfuture4.getText().toString() + " ," + dobEditTextfuture5.getText().toString(), question.getQuestion());
                    }

                } else if (qtype_extra == 5 && !isRequired_extra && dateValidation_extra.equals("restrict_future") && isRepeatable_extra && repeat_count == 5) {
                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString() + " ," + dobEditTextfuture4.getText().toString() + " ," + dobEditTextfuture5.getText().toString(), question.getQuestion());
                    // saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString() + " ," + dobEditTextfuture4.getText().toString() + " ," + dobEditTextfuture5.getText().toString(), question.getQuestion());

                }

                //datepicker restrict future repeat count 6
                else if (qtype_extra == 5 && !isRequired_extra && dateValidation_extra.equals("restrict_future") && isRepeatable_extra && repeat_count == 6) {
                    if (dobEditTextfuture.getText().toString().equals("") || dobEditTextfuture2.getText().toString().equals("") || dobEditTextfuture3.getText().toString().equals("") || dobEditTextfuture4.getText().toString().equals("") || dobEditTextfuture5.getText().toString().equals("") || dobEditTextfuture6.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    } else {
                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString() + " ," + dobEditTextfuture4.getText().toString() + " ," + dobEditTextfuture5.getText().toString() + " ," + dobEditTextfuture6.getText().toString(), question.getQuestion());
                        //saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString() + " ," + dobEditTextfuture4.getText().toString() + " ," + dobEditTextfuture5.getText().toString() + " ," + dobEditTextfuture6.getText().toString(), question.getQuestion());
                    }

                } else if (qtype_extra == 5 && !isRequired_extra && dateValidation_extra.equals("restrict_future") && isRepeatable_extra && repeat_count == 6) {
                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString() + " ," + dobEditTextfuture4.getText().toString() + " ," + dobEditTextfuture5.getText().toString() + " ," + dobEditTextfuture6.getText().toString(), question.getQuestion());
                    //saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString() + " ," + dobEditTextfuture4.getText().toString() + " ," + dobEditTextfuture5.getText().toString() + " ," + dobEditTextfuture6.getText().toString(), question.getQuestion());


                }


                //restrict past date non repeat
                else if (qtype_extra == 5 && isRequired_extra && dateValidation_extra.equals("restrict_past") && !isRepeatable_extra) {

                    if (dobEditTextpast.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    }

                    {

                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextpast.getText().toString(), question.getQuestion());
                        //saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextpast.getText().toString(), question.getQuestion());

                    }

                } else if (qtype_extra == 5 && dateValidation_extra.equals("restrict_past") && !isRepeatable_extra) {
                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextpast.getText().toString(), question.getQuestion());

                    //saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextpast.getText().toString(), question.getQuestion());


                }
                //restrict past date  repeat count 2
                else if (qtype_extra == 5 && isRepeatable_extra && dateValidation_extra.equals("restrict_past") && isRepeatable_extra && repeat_count == 2) {

                    if (dobEditTextpast.getText().toString().equals("") || dobEditTextpast2.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    }

                    {

                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString(), question.getQuestion());
                        //saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString(), question.getQuestion());

                    }

                } else if (qtype_extra == 5 && dateValidation_extra.equals("restrict_past") && isRepeatable_extra && repeat_count == 2) {
                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString(), question.getQuestion());
                    //  saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString(), question.getQuestion());


                }

                //restrict past date  repeat count 3
                else if (qtype_extra == 5 && isRequired_extra && dateValidation_extra.equals("restrict_past") && isRequired_extra && repeat_count == 3) {

                    if (dobEditTextpast.getText().toString().equals("") || dobEditTextpast2.getText().toString().equals("") || dobEditTextpast3.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    }

                    {

                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString(), question.getQuestion());
                        // saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString(), question.getQuestion());

                    }

                } else if (qtype_extra == 5 && dateValidation_extra.equals("restrict_past") && isRepeatable_extra && repeat_count == 3) {
                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex,dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString(), question.getQuestion());
                    // saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex,dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString(), question.getQuestion());


                }
                //restrict past date  repeat count 4
                else if (qtype_extra == 5 && isRequired_extra && dateValidation_extra.equals("restrict_past") && isRepeatable_extra && repeat_count == 4) {

                    if (dobEditTextpast.getText().toString().equals("") || dobEditTextpast2.getText().toString().equals("") || dobEditTextpast3.getText().toString().equals("") || dobEditTextpast4.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    }

                    {

                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString() + " ," + dobEditTextpast4.getText().toString(), question.getQuestion());
                        //  saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString() + " ," + dobEditTextpast4.getText().toString(), question.getQuestion());
                    }

                } else if (qtype_extra == 5 && dateValidation_extra.equals("restrict_past") && isRepeatable_extra && repeat_count1.getRepeat_count() == 4) {
                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString() + " ," + dobEditTextpast4.getText().toString(), question.getQuestion());
                    // saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString() + " ," + dobEditTextpast4.getText().toString(), question.getQuestion());

                }
                //restrict past date  repeat count 5
                else if (qtype_extra == 5 && isRequired_extra && dateValidation_extra.equals("restrict_past") && isRepeatable_extra && repeat_count == 5) {

                    if (dobEditTextpast.getText().toString().equals("") || dobEditTextpast2.getText().toString().equals("") || dobEditTextpast3.getText().toString().equals("") || dobEditTextpast4.getText().toString().equals("") || dobEditTextpast5.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    }

                    {

                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString() + " ," + dobEditTextpast4.getText().toString() + " ," + dobEditTextpast5.getText().toString(), question.getQuestion());
                        //  saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString() + " ," + dobEditTextpast4.getText().toString() + " ," + dobEditTextpast5.getText().toString(), question.getQuestion());

                    }

                } else if (qtype_extra == 5 && dateValidation_extra.equals("restrict_past") && isRepeatable_extra && repeat_count1.getRepeat_count() == 5) {
                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString() + " ," + dobEditTextpast4.getText().toString() + " ," + dobEditTextpast5.getText().toString(), question.getQuestion());
                    // saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString() + " ," + dobEditTextpast4.getText().toString() + " ," + dobEditTextpast5.getText().toString(), question.getQuestion());


                }

                //restrict past date  repeat count 6
                else if (qtype_extra == 5 && isRequired_extra && dateValidation_extra.equals("restrict_past") && isRepeatable_extra && repeat_count == 6) {

                    if (dobEditTextpast.getText().toString().equals("") || dobEditTextpast2.getText().toString().equals("") || dobEditTextpast3.getText().toString().equals("") || dobEditTextpast4.getText().toString().equals("") || dobEditTextpast5.getText().toString().equals("") || dobEditTextpast6.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    }

                    {

                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString() + " ," + dobEditTextpast4.getText().toString() + " ," + dobEditTextpast5.getText().toString() + " ," + dobEditTextpast6.getText().toString(), question.getQuestion());
                        //saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString() + " ," + dobEditTextpast4.getText().toString() + " ," + dobEditTextpast5.getText().toString() + " ," + dobEditTextpast6.getText().toString(), question.getQuestion());

                    }
                } else if (qtype_extra == 5 && dateValidation_extra.equals("restrict_past") && isRepeatable_extra && repeat_count == 6) {
                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString() + " ," + dobEditTextpast4.getText().toString() + " ," + dobEditTextpast5.getText().toString() + " ," + dobEditTextpast6.getText().toString(), question.getQuestion());
                    // saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString() + " ," + dobEditTextpast4.getText().toString() + " ," + dobEditTextpast5.getText().toString() + " ," + dobEditTextpast6.getText().toString(), question.getQuestion());


                } else if (qtype_extra == 2) {

                    int radioButtonID = singleChoiceRadioGroup.getCheckedRadioButtonId();


                    if (radioButtonID == -1) {

                        Toast.makeText(EditActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();

                    } else {
                        View radioButton = singleChoiceRadioGroup.findViewById(radioButtonID);
                        int idx = singleChoiceRadioGroup.indexOfChild(radioButton);


                        //   SaveAnswers(savedquestionnaireId, currentQuestionIndex, openText);
                        SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, String.valueOf(answerList.get(idx).getId()), question.getQuestion());
                        //  saveAnswersToDraft(savedquestionnaireId, currentQuestionIndex, String.valueOf(answerList.get(idx).getId()), question.getQuestion());

                    }

                } else if (qtype_extra == 3) {


                    for (int i = 0; i < multipleChoiceAns.getChildCount(); i++) {
                        View nextChild = multipleChoiceAns.getChildAt(i);

                        if (nextChild instanceof CheckBox) {
                            checkBox = (CheckBox) nextChild;
                            if (checkBox.isChecked()) {
                                multiAnswerList.add(checkBox.getId());
                            }
                        }

                    }

                    //aNSER TYPE 3

                    //SaveAnswers( questionnaireId, currentQuestionIndex, Integer.parseInt(String.valueOf(multiAnswerList)), openText);
                    SaveAnswers(session_extra, qtype_extra, isRequired_extra, dateValidation_extra, isRepeatable_extra,  answID_extra, UniqueIdentifier_extra, QuestionnaireId_extra, currentQuestionIndex, openText, question.getQuestion());
                    // saveAnswersToDraft(questionnaireId, currentQuestionIndex, openText, question.getQuestion());
                    //startActivity(new Intent(QuetionsOffline.this, QuetionsOffline.class));

//                    Toast.makeText(context, String.valueOf(multiAnswerList), Toast.LENGTH_SHORT).show();

                }
                else {
                    btnNext.setEnabled(false);
                }


                // }


                //List/Recyclerview

                        /*List<UserResponseEntity> response= allQuestionDatabase.userResponseDao().getUserResponsesForQuestionnaire(questionnaireId);

                        for (UserResponseEntity userResponseEntity: response){
                             answerR =userResponseEntity.getOption();

                            // UserResponseEntity userResponseEntity1 =new UserResponseEntity();


                        }
                        Toast.makeText(QuetionsOffline.this, answerR, Toast.LENGTH_LONG).show();
                        Log.d("UserResponse", answerR.toString());*/

                //  Log.d("UserResponse", an)


                // }

                //}
                   /* else if (question.getQuestionType() == 1 )
                    {
                        List<AnswerEntity> answersA = allQuestionDatabase.answerDao().getAnswersForQuestion(question.getId());
                        for (AnswerEntity answers2: answersA ){
                            idt1 = answers2.getId();
                            String option=answers2.getOption();
                            String createdAt=answers2.getCreatedAt();
                            int questionI=answers2.getQuestionId();
                            int createdBy=answers2.getCreatedBy();
                        }

                            SaveAnswers( questionnaireId, currentQuestionIndex, idt1 ,openTextEtxt.getText().toString());
                       // provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), openTextEtxt.getText().toString());

                    }*/

                //TYpe 4
                   /* else if (question.getQuestionType()==4 && question.isRequired()){
                        if (numericEditText.getText().toString().equals("")){
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                            // }else{
                        }else{SaveAnswers(questionnaireId, currentQuestionIndex, 1, numericEditText.getText().toString());}
                    }

                    else if (question.getQuestionType() == 4){

                        SaveAnswers(questionnaireId, currentQuestionIndex, 1, numericEditText.getText().toString());
                    }*/


    /*            }
                else {
                    btnNext.setEnabled(false);
                }*/
            }

        });
    }
       /* else {
            surveyQuestion.setText("No questions found for this questionnaire.");
            btnNext.setEnabled(false);
        }*/

    //}

    //same as load question
    private void displayQuestion() {





            surveyQuestion.setText(Quetion_extra);

            //end


            if (qtype_extra == 1) {
                openTextTil.setVisibility(View.VISIBLE);
                openTextEtxt.setText(Option_extra);

            } else if (qtype_extra == 2) {

                singleChoiceRadioGroup.setVisibility(View.VISIBLE);

             //   Toast.makeText(EditActivity.this, "quetnID_extra"+session_extra, Toast.LENGTH_SHORT).show();


                List<AnswerEntity> answersA = allQuestionDatabase.answerDao().getAnswersForQuestion2(savedquestionnaireId, session_extra);
                // Log.d("Answer List", answersA.);


                for (AnswerEntity answers2 : answersA) {
                    // Log.d("Answer List", answers2.toString());

                    int id = answers2.getId();
                    optionR = answers2.getOption();
                    String createdAt = answers2.getCreatedAt();
                    int questionI = answers2.getQuestionId();
                    int questionnaireI = answers2.getQuestionnaireId();
                    int createdBy = answers2.getCreatedBy();

                    answerEntity = new AnswerEntity(id, optionR, createdAt, questionI, questionnaireI, createdBy);
                    answerList.add(answerEntity);

                    RadioButton rbn = new RadioButton(EditActivity.this);
                    rbn.setId(View.generateViewId());
                    rbn.setText(optionR);
                    Log.d("Answer Options", optionR);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                    rbn.setLayoutParams(params);
                    singleChoiceRadioGroup.addView(rbn);


                }


            } else if (qtype_extra == 3) {
                openTextTil.setVisibility(View.GONE);
                singleChoiceRadioGroup.setVisibility(View.GONE);
                multipleChoiceAns.setVisibility(View.VISIBLE);
                // List<AnswerEntity> answersB = allQuestionDatabase.answerDao().getAnswersForQuestion(question.getId());
                List<AnswerEntity> answersB = allQuestionDatabase.answerDao().getAnswersForQuestion2(savedquestionnaireId, session_extra);
                List<Integer> answersB1 = allQuestionDatabase.answerDao().getAnswerIdsForQuestion(session_extra);
                for (AnswerEntity answers2 : answersB) {
                    int id = answers2.getId();
                    String option = answers2.getOption();
                    String createdAt = answers2.getCreatedAt();
                    int questionI = answers2.getQuestionId();
                    int questionnaire2 = answers2.getQuestionnaireId();
                    int createdBy = answers2.getCreatedBy();

                    answerEntity = new AnswerEntity(id, option, createdAt, questionI, questionnaire2, createdBy);
                    answerList.add(answerEntity);

                }


                checkBox = new CheckBox(this);
                checkBox.setId(answerEntity.getId());
                checkBox.setText(answerEntity.getOption());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                checkBox.setLayoutParams(params);
                multipleChoiceAns.addView(checkBox);

                // }

            } else if (qtype_extra == 4) {

                numericText.setVisibility(View.VISIBLE);
                numericEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            }
            //none
            else if (qtype_extra == 5 && dateValidation_extra.equals("none") && isRepeatable_extra && repeat_count == 1) {
                dateTextTil.setVisibility(View.VISIBLE);
            }
            //none not repeat
            else if (qtype_extra == 5 && dateValidation_extra.equals("none") && !isRepeatable_extra) {
                dateTextTil.setVisibility(View.VISIBLE);
            }

            //try
            // restrict future1
            else if (qtype_extra == 5 && dateValidation_extra.equals("restrict_future") && !isRepeatable_extra) {
                // dateTextTil.setVisibility(View.VISIBLE);
                dateTextTilfuture.setVisibility(View.VISIBLE);

            }

            // restrict future1
            else if (qtype_extra == 5 && dateValidation_extra.equals("restrict_future") && isRepeatable_extra && repeat_count == 1) {
                dateTextTilfuture.setVisibility(View.VISIBLE);

            }

            // restrict future2
            else if (qtype_extra == 5 && dateValidation_extra.equals("restrict_future") && isRepeatable_extra && repeat_count == 2) {
                dateTextTilfuture.setVisibility(View.VISIBLE);
                dateTextTilfuture2.setVisibility(View.VISIBLE);

            }


            // restrict future3
            else if (qtype_extra == 5 && dateValidation_extra.equals("restrict_future") && isRepeatable_extra && repeat_count == 3) {
                dateTextTilfuture.setVisibility(View.VISIBLE);
                dateTextTilfuture2.setVisibility(View.VISIBLE);
                dateTextTilfuture3.setVisibility(View.VISIBLE);

            }

            // restrict future4
            else if (qtype_extra == 5 && dateValidation_extra.equals("restrict_future") && isRepeatable_extra && repeat_count == 4) {
                dateTextTilfuture.setVisibility(View.VISIBLE);
                dateTextTilfuture2.setVisibility(View.VISIBLE);
                dateTextTilfuture3.setVisibility(View.VISIBLE);
                dateTextTilfuture4.setVisibility(View.VISIBLE);

            }
            // restrict future5
            else if (qtype_extra == 5 && dateValidation_extra.equals("restrict_future") && isRepeatable_extra && repeat_count == 5) {
                dateTextTilfuture.setVisibility(View.VISIBLE);
                dateTextTilfuture2.setVisibility(View.VISIBLE);
                dateTextTilfuture3.setVisibility(View.VISIBLE);
                dateTextTilfuture4.setVisibility(View.VISIBLE);
                dateTextTilfuture5.setVisibility(View.VISIBLE);

            }

            // restrict future6
            else if (qtype_extra == 5 && dateValidation_extra.equals("restrict_future") && isRepeatable_extra && repeat_count == 6) {
                dateTextTilfuture.setVisibility(View.VISIBLE);
                dateTextTilfuture2.setVisibility(View.VISIBLE);
                dateTextTilfuture3.setVisibility(View.VISIBLE);
                dateTextTilfuture4.setVisibility(View.VISIBLE);
                dateTextTilfuture5.setVisibility(View.VISIBLE);
                dateTextTilfuture6.setVisibility(View.VISIBLE);

            }


            //restrict past repeat none
            else if (qtype_extra == 5 && dateValidation_extra.equals("restrict_past") && !isRepeatable_extra) {
                dateTextTilpast.setVisibility(View.VISIBLE);
            }
            //restrict past2
            else if (qtype_extra == 5 && dateValidation_extra.equals("restrict_past") && isRepeatable_extra && repeat_count1.getRepeat_count() == 2) {
                dateTextTilpast.setVisibility(View.VISIBLE);
                dateTextTilpast2.setVisibility(View.VISIBLE);
            }

            //restrict past3
            else if (qtype_extra == 5 && dateValidation_extra.equals("restrict_past") && isRequired_extra && repeat_count1.getRepeat_count() == 3) {
                dateTextTilpast.setVisibility(View.VISIBLE);
                dateTextTilpast2.setVisibility(View.VISIBLE);
                dateTextTilpast3.setVisibility(View.VISIBLE);
            }


            //restrict past4
            else if (qtype_extra== 5 && dateValidation_extra.equals("restrict_past") && isRepeatable_extra && repeat_count1.getRepeat_count() == 4) {
                dateTextTilpast.setVisibility(View.VISIBLE);
                dateTextTilpast2.setVisibility(View.VISIBLE);
                dateTextTilpast3.setVisibility(View.VISIBLE);
                dateTextTilpast4.setVisibility(View.VISIBLE);
            }
            //restrict past5
            else if (qtype_extra == 5 && dateValidation_extra.equals("restrict_past") && isRepeatable_extra && repeat_count1.getRepeat_count() == 5) {
                dateTextTilpast.setVisibility(View.VISIBLE);
                dateTextTilpast2.setVisibility(View.VISIBLE);
                dateTextTilpast3.setVisibility(View.VISIBLE);
                dateTextTilpast4.setVisibility(View.VISIBLE);
                dateTextTilpast5.setVisibility(View.VISIBLE);
            }

            //restrict past6
            else if (qtype_extra == 5 && dateValidation_extra.equals("restrict_past") && isRepeatable_extra && repeat_count1.getRepeat_count() == 6) {
                dateTextTilpast.setVisibility(View.VISIBLE);
                dateTextTilpast2.setVisibility(View.VISIBLE);
                dateTextTilpast3.setVisibility(View.VISIBLE);
                dateTextTilpast4.setVisibility(View.VISIBLE);
                dateTextTilpast5.setVisibility(View.VISIBLE);
                dateTextTilpast6.setVisibility(View.VISIBLE);
            }

        // btnNext.setEnabled(true);
    }

    private void initialize(){
        surveyQuestion = (MaterialTextView) findViewById(R.id.tv_survey_question);
        //openText1
        openTextTil= (TextInputLayout) findViewById(R.id.til_open_text);
        openTextEtxt =(TextInputEditText) findViewById(R.id.etxt_open_text);

        //openText2
        openTextTil2 = (TextInputLayout) findViewById(R.id.til_open_text2);
        openTextEtxt2= (TextInputEditText) findViewById(R.id.etxt_open_text2);

        //openText3
        openTextTil3=(TextInputLayout) findViewById(R.id.til_open_text3);
        openTextEtxt3= (TextInputEditText) findViewById(R.id.etxt_open_text3);

        //openText4
        openTextTil4=(TextInputLayout) findViewById(R.id.til_open_text4);
        openTextEtxt4= (TextInputEditText) findViewById(R.id.etxt_open_text4);

        //openText5
        openTextTil5=(TextInputLayout) findViewById(R.id.til_open_text5);
        openTextEtxt5= (TextInputEditText) findViewById(R.id.etxt_open_text5);

        //openText6
        openTextTil6=(TextInputLayout) findViewById(R.id.til_open_text6);
        openTextEtxt6= (TextInputEditText) findViewById(R.id.etxt_open_text6);


        //dateNone1
        dateTextTil=(TextInputLayout) findViewById(R.id.dateLayout);
        dobEditText= (TextInputEditText) findViewById(R.id.dob);
        //dateNone2
        dateTextTil2=(TextInputLayout) findViewById(R.id.dateLayout2);
        dobEditText2= (TextInputEditText) findViewById(R.id.dob2);
        //dateNone3
        dateTextTil3=(TextInputLayout) findViewById(R.id.dateLayout3);
        dobEditText3= (TextInputEditText) findViewById(R.id.dob3);
        //dateNone4
        dateTextTil4=(TextInputLayout) findViewById(R.id.dateLayout4);
        dobEditText4= (TextInputEditText) findViewById(R.id.dob4);
        //dateNone5
        dateTextTil5=(TextInputLayout) findViewById(R.id.dateLayout5);
        dobEditText5= (TextInputEditText) findViewById(R.id.dob5);
        //dateNone6
        dateTextTil6=(TextInputLayout) findViewById(R.id.dateLayout6);
        dobEditText6= (TextInputEditText) findViewById(R.id.dob6);


        //datefuture1
        dateTextTilfuture=(TextInputLayout) findViewById(R.id.dateLayoutfuture);
        dobEditTextfuture= (TextInputEditText) findViewById(R.id.dobfuture);

        //datefuture2
        dateTextTilfuture2=(TextInputLayout) findViewById(R.id.dateLayoutfuture2);
        dobEditTextfuture2= (TextInputEditText) findViewById(R.id.dobfuture2);

        //datefuture3
        dateTextTilfuture3=(TextInputLayout) findViewById(R.id.dateLayoutfuture3);
        dobEditTextfuture3= (TextInputEditText) findViewById(R.id.dobfuture3);

        //datefuture4
        dateTextTilfuture4=(TextInputLayout) findViewById(R.id.dateLayoutfuture4);
        dobEditTextfuture4= (TextInputEditText) findViewById(R.id.dobfuture4);

        //datefuture5
        dateTextTilfuture5=(TextInputLayout) findViewById(R.id.dateLayoutfuture5);
        dobEditTextfuture5= (TextInputEditText) findViewById(R.id.dobfuture5);

        //datefuture6
        dateTextTilfuture6=(TextInputLayout) findViewById(R.id.dateLayoutfuture6);
        dobEditTextfuture6= (TextInputEditText) findViewById(R.id.dobfuture6);


        //datepast1
        dateTextTilpast=(TextInputLayout) findViewById(R.id.dateLayoutpast);
        dobEditTextpast=(TextInputEditText) findViewById(R.id.dobpast);

        //datepast2
        dateTextTilpast2=(TextInputLayout) findViewById(R.id.dateLayoutpast2);
        dobEditTextpast2=(TextInputEditText) findViewById(R.id.dobpast2);

        //datepast3
        dateTextTilpast3=(TextInputLayout) findViewById(R.id.dateLayoutpast3);
        dobEditTextpast3=(TextInputEditText) findViewById(R.id.dobpast3);

        //datepast4
        dateTextTilpast4=(TextInputLayout) findViewById(R.id.dateLayoutpast4);
        dobEditTextpast4=(TextInputEditText) findViewById(R.id.dobpast4);

        //datepast5
        dateTextTilpast5=(TextInputLayout) findViewById(R.id.dateLayoutpast5);
        dobEditTextpast5=(TextInputEditText) findViewById(R.id.dobpast5);

        //datepast6
        dateTextTilpast6=(TextInputLayout) findViewById(R.id.dateLayoutpast6);
        dobEditTextpast6=(TextInputEditText) findViewById(R.id.dobpast6);


        numericText=(TextInputLayout) findViewById(R.id.til_numeric_layout);
        numericEditText=(TextInputEditText) findViewById(R.id.etxt_numeric_text);




        singleChoiceRadioGroup=(RadioGroup) findViewById(R.id.radio_group);
        multipleChoiceAns=(LinearLayout) findViewById(R.id.multiselect_lyt);

        coordinatorLyt=(CoordinatorLayout) findViewById(R.id.coordinator_lyt);
        shimmer_my_container=(ShimmerFrameLayout) findViewById(R.id.shimmer_my_container);

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        no_active_survey_lyt=(LinearLayout) findViewById(R.id.no_active_survey_lyt);
        error_lyt=(LinearLayout) findViewById(R.id.error_lyt);



    }



    public void SaveAnswers(int Sessionid, int questionType, boolean isRequired, String dateValidation, boolean isRepeatable, int answ_id, String uniqueIdentifier, int questionnaireId1, int questionId1, String option1, String quetion1){
       // int Sessionid, int questionType, boolean isRequired, String dateValidation, boolean isRepeatable, int answ_id, String uniqueIdentifier, int questionnaireId1, int questionId1, String option1, String quetion1

        // List<AnswerEntity> answers = allQuestionDatabase.answerDao().getAnswersForQuestion(surveyQuestion.getId());

        UserResponseEntity userResponseEntity = allQuestionDatabase.userResponseDao().getUserResponseById(session_extra);

      //  UserResponseEntity userResponseEntity = new UserResponseEntity();

        if (userResponseEntity==null){
            Toast.makeText(EditActivity.this, "Null to update", Toast.LENGTH_SHORT).show();

        }else{


        userResponseEntity.setSessionid(Sessionid);
        userResponseEntity.setQuestionType(questionType);
        userResponseEntity.setRequired(isRequired);
        userResponseEntity.setDateValidation(dateValidation);
        userResponseEntity.setRepeatable(isRepeatable);

        userResponseEntity.setAnswerId(answ_id);
        userResponseEntity.setUniqueIdentifier(uniqueIdentifier);
        userResponseEntity.setQuestionnaireId(questionnaireId1);
        userResponseEntity.setQuestionId(questionId1);
        // userResponseEntity.setAnswerId(answeid1);
        userResponseEntity.setOption(option1);
        userResponseEntity.setQuetion_A(quetion1);

  //      allQuestionDatabase.userResponseDao().insertResponse(userResponseEntity);

        allQuestionDatabase.userResponseDao().updateResponse(userResponseEntity);

        Intent intent = new Intent(EditActivity.this, SessionList.class);
        startActivity(intent);

        }



    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentQuestionIndex", currentQuestionIndex);
        // Save other necessary data here
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentQuestionIndex = savedInstanceState.getInt("currentQuestionIndex");
        // Restore other necessary data here
    }


}