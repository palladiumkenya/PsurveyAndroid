package com.mhealthkenya.psurvey.models;

import androidx.room.PrimaryKey;

public class UniqueIdentifierEntity {



    private String uniqueIdentifier;

    public UniqueIdentifierEntity(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }
}
