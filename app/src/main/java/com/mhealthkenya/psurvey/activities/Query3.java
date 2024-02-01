package com.mhealthkenya.psurvey.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mhealthkenya.psurvey.R;

public class Query3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query3);



    }

    private void loadDataFromSharedPreferences() {
       /* preferences = QuetionsOffline.this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int sessionId = preferences.getInt(KEY_SESSION_ID, 0); // Default value if key not found
        String uniqueIdentifier = preferences.getString(KEY_UNIQUE_IDENTIFIER, "");
        int questionnaireId = preferences.getInt(KEY_QUESTIONNAIRE_ID, 0);
        int questionId = preferences.getInt(KEY_QUESTION_ID, 0);
        String option = preferences.getString(KEY_OPTION, "");
        String question = preferences.getString(KEY_QUESTION, "");
        currentQuestionIndex = preferences.getInt(KEY_CURRENT_QUESTION_INDEX, 0);

        // Use the loaded data as needed

        373269*/
    }
}