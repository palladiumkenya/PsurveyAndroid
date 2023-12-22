package com.mhealthkenya.psurvey.models;

import com.orm.SugarRecord;
import com.orm.dsl.Table;


@Table(name = " SurveyUnique")
public class SurveyUnique extends SugarRecord {

    String surveyUnik;

    public SurveyUnique() {
    }

    public SurveyUnique(String surveyUnik) {
        this.surveyUnik = surveyUnik;
    }

    public String getSurveyUnik() {
        return surveyUnik;
    }

    public void setSurveyUnik(String surveyUnik) {
        this.surveyUnik = surveyUnik;
    }
}
