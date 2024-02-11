package com.mhealthkenya.psurvey.models;

import com.orm.SugarRecord;

public class ResponseIntent extends SugarRecord {

    private int ID_extra,  session_extra,  answID_extra, quetnID_extra, qtype_extra, QuestionnaireId_extra;
     private String Quetion_extra, Option_extra, UniqueIdentifier_extra, dateValidation_extra;
     boolean isRequired_extra, isRepeatable_extra;


   /* ID_extra = intent2.getIntExtra("ID", 0); // Default to 0
    session_extra = intent2.getIntExtra("Session", 0); // Default to 0
    answID_extra = intent2.getIntExtra("AnswerID", 0); // Default to 0
    quetnID_extra = intent2.getIntExtra("quetionID", 0); // Default to 0
    qtype_extra = intent2.getIntExtra("QuetionType", 0); // Default to 0
    QuestionnaireId_extra = intent2.getIntExtra("quetionnaireID", 0); // Default to 0

    Quetion_extra = intent2.getStringExtra(  "Quetion");
    Option_extra = intent2.getStringExtra(  "Option");
    UniqueIdentifier_extra = intent2.getStringExtra(  "UniqueIdentifier");
    dateValidation_extra = intent2.getStringExtra(  "datevalidation");
    isRequired_extra = intent2.getBooleanExtra(  "isRequired", true);
    isRepeatable_extra = intent2.getBooleanExtra(  "isRepeatable", true);*/


    public ResponseIntent() {
    }

    public ResponseIntent(int ID_extra, int session_extra, int answID_extra, int quetnID_extra, int qtype_extra, int questionnaireId_extra, String quetion_extra, String option_extra, String uniqueIdentifier_extra, String dateValidation_extra, boolean isRequired_extra, boolean isRepeatable_extra) {
        this.ID_extra = ID_extra;
        this.session_extra = session_extra;
        this.answID_extra = answID_extra;
        this.quetnID_extra = quetnID_extra;
        this.qtype_extra = qtype_extra;
        QuestionnaireId_extra = questionnaireId_extra;
        Quetion_extra = quetion_extra;
        Option_extra = option_extra;
        UniqueIdentifier_extra = uniqueIdentifier_extra;
        this.dateValidation_extra = dateValidation_extra;
        this.isRequired_extra = isRequired_extra;
        this.isRepeatable_extra = isRepeatable_extra;
    }

    public ResponseIntent(String uniqueIdentifier_extra) {
        UniqueIdentifier_extra = uniqueIdentifier_extra;
    }

    public ResponseIntent(int session_extra) {
        this.session_extra = session_extra;
    }

    public int getID_extra() {
        return ID_extra;
    }

    public void setID_extra(int ID_extra) {
        this.ID_extra = ID_extra;
    }

    public int getSession_extra() {
        return session_extra;
    }

    public void setSession_extra(int session_extra) {
        this.session_extra = session_extra;
    }

    public int getAnswID_extra() {
        return answID_extra;
    }

    public void setAnswID_extra(int answID_extra) {
        this.answID_extra = answID_extra;
    }

    public int getQuetnID_extra() {
        return quetnID_extra;
    }

    public void setQuetnID_extra(int quetnID_extra) {
        this.quetnID_extra = quetnID_extra;
    }

    public int getQtype_extra() {
        return qtype_extra;
    }

    public void setQtype_extra(int qtype_extra) {
        this.qtype_extra = qtype_extra;
    }

    public int getQuestionnaireId_extra() {
        return QuestionnaireId_extra;
    }

    public void setQuestionnaireId_extra(int questionnaireId_extra) {
        QuestionnaireId_extra = questionnaireId_extra;
    }

    public String getQuetion_extra() {
        return Quetion_extra;
    }

    public void setQuetion_extra(String quetion_extra) {
        Quetion_extra = quetion_extra;
    }

    public String getOption_extra() {
        return Option_extra;
    }

    public void setOption_extra(String option_extra) {
        Option_extra = option_extra;
    }

    public String getUniqueIdentifier_extra() {
        return UniqueIdentifier_extra;
    }

    public void setUniqueIdentifier_extra(String uniqueIdentifier_extra) {
        UniqueIdentifier_extra = uniqueIdentifier_extra;
    }

    public String getDateValidation_extra() {
        return dateValidation_extra;
    }

    public void setDateValidation_extra(String dateValidation_extra) {
        this.dateValidation_extra = dateValidation_extra;
    }

    public boolean isRequired_extra() {
        return isRequired_extra;
    }

    public void setRequired_extra(boolean required_extra) {
        isRequired_extra = required_extra;
    }

    public boolean isRepeatable_extra() {
        return isRepeatable_extra;
    }

    public void setRepeatable_extra(boolean repeatable_extra) {
        isRepeatable_extra = repeatable_extra;
    }
}
