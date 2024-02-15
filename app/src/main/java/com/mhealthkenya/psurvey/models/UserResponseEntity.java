package com.mhealthkenya.psurvey.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.orm.SugarRecord;

import java.io.Serializable;


@Entity(tableName = "question_answers")
       /* foreignKeys = @ForeignKey(entity = UserResponseEntity2.class,
                parentColumns = "id",
                childColumns = "id",
                onDelete = ForeignKey.CASCADE))*/
public class UserResponseEntity  {

    @PrimaryKey(autoGenerate = true)
    private int idA;

    private int cccid;
    private int Sessionid;
    private int questionnaireId;
    private int questionId;
    private int answerId;
    private  String option;
    private  String Quetion_A;
    private String uniqueIdentifier;

    private int questionType;
    private boolean isRequired;
    private String dateValidation;
    private boolean isRepeatable;

    //quetionAnswers


    public UserResponseEntity() {
    }


    public UserResponseEntity(int Sessionid,int cccid, int questionType, boolean isRequired, String dateValidation, boolean isRepeatable,  int answerId, String uniqueIdentifier, int questionnaireId, int questionId, String option, String Quetion_A) {

        this.questionnaireId = questionnaireId;
        this.uniqueIdentifier =uniqueIdentifier;
        this.Sessionid = Sessionid;
        this.answerId =answerId;
        this.questionId = questionId;
        this.option = option;
        this.Quetion_A= Quetion_A;

        this.cccid= cccid;

        this.questionType= questionType;

        this.isRequired=isRequired;
        this.dateValidation=dateValidation;
        this.isRepeatable=isRepeatable;




    }

    public UserResponseEntity(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }




    public int getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }


    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getQuetion_A() {
        return Quetion_A;
    }

    public void setQuetion_A(String quetion_A) {
        Quetion_A = quetion_A;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public int getSessionid() {
        return Sessionid;
    }

    public void setSessionid(int sessionid) {
        Sessionid = sessionid;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public String getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(String dateValidation) {
        this.dateValidation = dateValidation;
    }

    public boolean isRepeatable() {
        return isRepeatable;
    }

    public void setRepeatable(boolean repeatable) {
        isRepeatable = repeatable;
    }

    public int getCccid() {
        return cccid;
    }

    public void setCccid(int cccid) {
        this.cccid = cccid;
    }
}
