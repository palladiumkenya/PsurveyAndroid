package com.mhealthkenya.psurvey.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.orm.SugarRecord;

import java.util.Date;

@Entity(tableName = "SessionOffline")
public class SessionOffline extends SugarRecord {

    @PrimaryKey(autoGenerate = true)
    private int idA;
    private String uniqueIdentifier;
    private Date startDate;
    private java.sql.Date endDate;

}
