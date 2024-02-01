package com.mhealthkenya.psurvey.models;

import com.orm.SugarRecord;

public class Available extends SugarRecord {

    public  int available;

    public Available(int available) {
        this.available = available;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}
