package com.mhealthkenya.psurvey.models;

import com.orm.SugarRecord;

public class SessionID extends SugarRecord {

    private String uniqueIdentifier;

    public SessionID() {
    }

    public SessionID(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }
}
