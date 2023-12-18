package com.mhealthkenya.psurvey.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.orm.SugarRecord;

import java.util.Date;

@Entity(tableName = "SessionOffline")
public class SessionOffline{

    @PrimaryKey(autoGenerate = true)
    private int Sessionid;
    private int questionnaireId;

    @ColumnInfo(name = "sessionIdentifier")
    private String sessionIdentifier;

    @ColumnInfo(name = "start_time")
    private long startTime; // Use timestamp to store start time

    @ColumnInfo(name = "end_time")
    private long endTime;   // Use timestamp to store end time

    // Add other fields like user ID, session duration, etc. as needed

    // Constructor


    public SessionOffline() {
    }

    public SessionOffline(String sessionIdentifier, int questionnaireId, long startTime) {
        this.sessionIdentifier = sessionIdentifier;
        this.startTime = startTime;
        this.questionnaireId =questionnaireId;
      //  this.endTime =endTime;
    }

    // Getters and setters


    public int getSessionid() {
        return Sessionid;
    }

    public void setSessionid(int sessionid) {
        Sessionid = sessionid;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getSessionIdentifier() {
        return sessionIdentifier;
    }

    public void setSessionIdentifier(String sessionIdentifier) {
        this.sessionIdentifier = sessionIdentifier;
    }


    // Set endTimestamp when the session ends
    public void endSession(long endTime) {
        this.endTime = endTime;
    }
}
