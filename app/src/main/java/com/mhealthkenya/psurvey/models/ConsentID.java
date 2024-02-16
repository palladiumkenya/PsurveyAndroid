package com.mhealthkenya.psurvey.models;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Table(name = "ConsentID")
public class ConsentID extends SugarRecord {

    long generatedID;

    public ConsentID() {
    }

    public ConsentID(long generatedID) {
        this.generatedID = generatedID;
    }

    public long getGeneratedID() {
        return generatedID;
    }

    public void setGeneratedID(long generatedID) {
        this.generatedID = generatedID;
    }
}
