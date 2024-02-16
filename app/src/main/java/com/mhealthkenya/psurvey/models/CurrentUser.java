package com.mhealthkenya.psurvey.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "current_user")
public class CurrentUser {
    @PrimaryKey
    private int id = 1;

    private String firstName;
    private String lastName;

    private String facilityName;
    private String email;
    private int mflCode;

    public CurrentUser(String firstName, String lastName, String facilityName, String email,
                       int mflCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.facilityName = facilityName;
        this.email = email;
        this.mflCode = mflCode;
    }

    public CurrentUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMflCode() {
        return mflCode;
    }

    public void setMflCode(int mflCode) {
        this.mflCode = mflCode;
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "id=" + id +
                '}';
    }
}
