package com.mhealthkenya.psurvey.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.orm.SugarRecord;

import java.io.Serializable;

@Entity(tableName ="user_responses")
public class UserResponseEntity extends SugarRecord implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int idA;

    private int questionnaireId;
    private int questionId;
    private int answerId;
    private  String option;
    private  String Quetion_A;

    private String uniqueIdentifier;


    public UserResponseEntity() {
    }

    public UserResponseEntity(int questionnaireId, int questionId,  String option, String Quetion_A) {

        this.questionnaireId = questionnaireId;
        this.questionId = questionId;

        this.option = option;
        this.Quetion_A= Quetion_A;
    }

    protected UserResponseEntity(Parcel in) {
        idA = in.readInt();
        questionnaireId = in.readInt();
        questionId = in.readInt();
        answerId = in.readInt();
        option = in.readString();
        Quetion_A = in.readString();
    }

    public static final Creator<UserResponseEntity> CREATOR = new Creator<UserResponseEntity>() {
        @Override
        public UserResponseEntity createFromParcel(Parcel in) {
            return new UserResponseEntity(in);
        }

        @Override
        public UserResponseEntity[] newArray(int size) {
            return new UserResponseEntity[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(idA);
        dest.writeInt(questionnaireId);
        dest.writeInt(questionId);
        dest.writeInt(answerId);
        dest.writeString(option);
        dest.writeString(Quetion_A);
    }
}
