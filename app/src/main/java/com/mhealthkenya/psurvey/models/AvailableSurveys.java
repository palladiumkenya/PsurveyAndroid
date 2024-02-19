package com.mhealthkenya.psurvey.models;


import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Table(name = "AvailableSurveys")
public class AvailableSurveys extends SugarRecord {

    int AvailableSurveys;

    public AvailableSurveys(int availableSurveys) {
        AvailableSurveys = availableSurveys;
    }

    public int getAvailableSurveys() {
        return AvailableSurveys;
    }

    public void setAvailableSurveys(int availableSurveys) {
        AvailableSurveys = availableSurveys;
    }
}
