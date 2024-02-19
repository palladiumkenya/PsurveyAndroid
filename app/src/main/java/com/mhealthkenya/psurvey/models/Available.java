package com.mhealthkenya.psurvey.models;

import com.orm.SugarRecord;
import com.orm.dsl.Table;


@Table(name = "Available")
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
