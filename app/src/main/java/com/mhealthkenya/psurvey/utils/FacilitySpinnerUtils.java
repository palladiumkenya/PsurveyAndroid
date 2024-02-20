package com.mhealthkenya.psurvey.utils;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.mhealthkenya.psurvey.helper.DatabaseHelper;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.List;

/**
 * Utility class for setting up and managing Spinner widgets related to facility selection
 * in the PSurvey application.
 */
public class FacilitySpinnerUtils {

    /**
     * Sets up the County Spinner and its associated sub-components.
     *
     * @param countyAdapter       ArrayAdapter for County Spinner.
     * @param countySpinner       SearchableSpinner for County selection.
     * @param subCountyAdapter    ArrayAdapter for Sub-County Spinner.
     * @param subCountySpinner    SearchableSpinner for Sub-County selection.
     * @param facilityAdapter     ArrayAdapter for Facility Spinner.
     * @param facilitySpinner     SearchableSpinner for Facility selection.
     * @param context             Application context.
     * @return Facility ID associated with the selected facility.
     */
    public int setupCountySpinner(ArrayAdapter<String> countyAdapter, SearchableSpinner countySpinner,
                                  ArrayAdapter<String> subCountyAdapter, SearchableSpinner subCountySpinner,
                                  ArrayAdapter<String> facilityAdapter, SearchableSpinner facilitySpinner,
                                  Context context) {
        final int[] facilityID = {0};

        // Initialize DatabaseHelper for database operations
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        // Retrieve distinct counties from the database
        List<String> countyList = databaseHelper.getDistinctCounties();
        if (countyList != null && !countyList.isEmpty()) {
            countyList.remove(0);
            countyList.add(0, "Select Your County");
            countyAdapter.clear();
            countyAdapter.addAll(countyList);

            // Set up County Spinner item selection listener
            countySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    String selectedCounty = countyList.get(position);
                    facilityID[0] = populateSubCountySpinner(selectedCounty, subCountyAdapter,
                            subCountySpinner, facilityAdapter, facilitySpinner, context);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    // Handle nothing selected
                }
            });
        }
        return facilityID[0];
    }

    private int populateSubCountySpinner(String selectedCounty, ArrayAdapter<String> subCountyAdapter,
                                         SearchableSpinner subCountySpinner, ArrayAdapter<String> facilityAdapter,
                                         SearchableSpinner facilitySpinner, Context context) {
        final int[] facilityID = {0};

        // Initialize DatabaseHelper for database operations
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        // Retrieve sub-counties based on the selected county
        List<String> subCountyList = databaseHelper.getSubCountiesByCounty(selectedCounty);
        if (subCountyList != null && !subCountyList.isEmpty()) {
            subCountyList.remove(0);
            subCountyList.add(0, "Select your Sub County");
            subCountyAdapter.clear();
            subCountyAdapter.addAll(subCountyList);

            // Set up Sub-County Spinner item selection listener
            subCountySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    String selectedSubCounty = subCountyList.get(position);
                    facilityID[0] = populateFacilitySpinner(selectedSubCounty, facilityAdapter,
                            facilitySpinner, context);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    // Handle nothing selected
                }
            });
        }
        return facilityID[0];
    }

    private int populateFacilitySpinner(String selectedSubCounty, ArrayAdapter<String> facilityAdapter,
                                        SearchableSpinner facilitySpinner, Context context) {
        final int[] facilityID = {0};

        // Initialize DatabaseHelper for database operations
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        // Retrieve facility names based on the selected sub-county
        List<String> facilitiesList = databaseHelper.getFacilityNamesBySubCounty(selectedSubCounty);

        if (facilitiesList != null && !facilitiesList.isEmpty()) {
            facilitiesList.remove(0);
            facilitiesList.add(0, "Select your Facility");
            facilityAdapter.clear();
            facilityAdapter.addAll(facilitiesList);

            // Set up Facility Spinner item selection listener
            facilitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    String selectedFacility = facilitiesList.get(position);
                    facilityID[0] = databaseHelper.getFacilityId(selectedFacility);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    // Handle nothing selected
                }
            });
        }
        return facilityID[0];
    }
}
