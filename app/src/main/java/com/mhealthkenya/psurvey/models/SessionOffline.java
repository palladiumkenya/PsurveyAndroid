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

    @ColumnInfo(name = "start_time")
    private long startTime; // Use timestamp to store start time

    @ColumnInfo(name = "end_time")
    private long endTime;   // Use timestamp to store end time

    // Add other fields like user ID, session duration, etc. as needed

    // Constructor
    public SessionOffline(long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime =endTime;
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
}
