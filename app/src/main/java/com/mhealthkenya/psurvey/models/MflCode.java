package com.mhealthkenya.psurvey.models;

import com.orm.SugarRecord;

public class MflCode extends SugarRecord {
    public int mfl;

    public MflCode() {
    }

    public MflCode(int mfl) {
        this.mfl = mfl;
    }

    public int getMfl() {
        return mfl;
    }

    public void setMfl(int mfl) {
        this.mfl = mfl;
    }
}
