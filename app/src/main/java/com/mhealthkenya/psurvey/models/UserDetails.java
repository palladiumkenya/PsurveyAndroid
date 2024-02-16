package com.mhealthkenya.psurvey.models;

public class UserDetails {
    private int id;
    private String email;
    private  String firstName;
    private String lastName;
    private int mflCode;
    private String facilityName;

    public UserDetails(int id, String email, String firstName, String lastName, int mflCode, String facilityName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mflCode = mflCode;
        this.facilityName = facilityName;
    }

    public UserDetails() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getMflCode() {
        return mflCode;
    }

    public void setMflCode(int mflCode) {
        this.mflCode = mflCode;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }
}
