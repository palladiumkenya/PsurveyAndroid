package com.mhealthkenya.psurvey.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
@Entity(tableName = "user_responses")
public class UserResponseEntity2 {
    //UserResponseEntity

    @PrimaryKey(autoGenerate = true)
    private int cccid;

    private String cccNumber;
    private String firstName;
    private int savedquestionnaireId;
    int questionnaireParticipantId;
    String surveyUniqueID;
    private boolean informedConsent;
    private boolean privacyPolicy;
    private boolean interviewerStatement;

    //int
    private List<UserResponseEntity>questionAnswers;

    public UserResponseEntity2() {
    }

    // Constructor
    public UserResponseEntity2(int savedquestionnaireId, String surveyUniqueID, String cccNumber, String firstName, int questionnaireParticipantId,
                              boolean informedConsent, boolean privacyPolicy, boolean interviewerStatement
                              ) {
        this.cccNumber = cccNumber;
        this.surveyUniqueID = surveyUniqueID;
        this.savedquestionnaireId = savedquestionnaireId;
        this.firstName = firstName;
        this.questionnaireParticipantId = questionnaireParticipantId;
        this.informedConsent = informedConsent;
        this.privacyPolicy = privacyPolicy;
        this.interviewerStatement = interviewerStatement;
       // this.questionAnswers = questionAnswers;
    }

    // Getters and setters


    public int getCccid() {
        return cccid;
    }

    public void setCccid(int cccid) {
        this.cccid = cccid;
    }

    public String getCccNumber() {
        return cccNumber;
    }

    public void setCccNumber(String cccNumber) {
        this.cccNumber = cccNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getQuestionnaireParticipantId() {
        return questionnaireParticipantId;
    }

    public void setQuestionnaireParticipantId(int questionnaireParticipantId) {
        this.questionnaireParticipantId = questionnaireParticipantId;
    }

    public boolean isInformedConsent() {
        return informedConsent;
    }

    public void setInformedConsent(boolean informedConsent) {
        this.informedConsent = informedConsent;
    }

    public boolean isPrivacyPolicy() {
        return privacyPolicy;
    }

    public void setPrivacyPolicy(boolean privacyPolicy) {
        this.privacyPolicy = privacyPolicy;
    }

    public boolean isInterviewerStatement() {
        return interviewerStatement;
    }

    public void setInterviewerStatement(boolean interviewerStatement) {
        this.interviewerStatement = interviewerStatement;
    }

    public List<UserResponseEntity> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(List<UserResponseEntity> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    public String getSurveyUniqueID() {
        return surveyUniqueID;
    }

    public void setSurveyUniqueID(String surveyUniqueID) {
        this.surveyUniqueID = surveyUniqueID;
    }

    public int getSavedquestionnaireId() {
        return savedquestionnaireId;
    }

    public void setSavedquestionnaireId(int savedquestionnaireId) {
        this.savedquestionnaireId = savedquestionnaireId;
    }
}
