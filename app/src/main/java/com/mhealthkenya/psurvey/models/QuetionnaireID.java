package com.mhealthkenya.psurvey.models;

import com.orm.SugarRecord;

public class QuetionnaireID extends SugarRecord {

    private int quetioonareID;

    public QuetionnaireID() {
    }

    public QuetionnaireID(int quetioonareID) {
        this.quetioonareID = quetioonareID;
    }

    public int getQuetioonareID() {
        return quetioonareID;
    }

    public void setQuetioonareID(int quetioonareID) {
        this.quetioonareID = quetioonareID;
    }
}
