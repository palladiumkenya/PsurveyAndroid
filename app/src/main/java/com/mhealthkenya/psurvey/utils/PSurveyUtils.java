package com.mhealthkenya.psurvey.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mhealthkenya.psurvey.helper.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for PSurvey application containing methods to interact with the API
 * and save fetched facility data to the local database.
 */
public class PSurveyUtils {

    // Tag for logging purposes
    private static final String TAG = PSurveyUtils.class.getCanonicalName();

    // API URL for fetching facility data
    private static final String API_URL = "https://psurveyapi.kenyahmis.org/api/facilities/";

    /**
     * Fetches facility data from the API and returns a list of maps containing facility details.
     *
     * @return List of maps containing facility details.
     */
    public static List<Map<String, String>> fetchDataFromAPI() {
        List<Map<String, String>> result = new ArrayList<>();

        try {
            // Establish connection to the API URL
            URL url = new URL(API_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                // Get input stream from the connection
                InputStream is = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                try {
                    // Read response from the API
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Parse JSON response
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    JSONArray dataArray = jsonResponse.getJSONArray("data");

                    // Extract facility data and populate the result list
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject facilityJson = dataArray.getJSONObject(i);
                        Map<String, String> facilityData = new HashMap<>();

                        facilityData.put("id", String.valueOf(facilityJson.getInt("id")));
                        facilityData.put("mfl_code", String.valueOf(facilityJson.getInt("mfl_code")));
                        facilityData.put("name", facilityJson.getString("name"));
                        facilityData.put("county", facilityJson.getString("county"));
                        facilityData.put("sub_county", facilityJson.getString("sub_county"));

                        result.add(facilityData);
                        Log.i("-->Facility Locations", facilityData.get("county"));
                    }
                } catch (IOException | JSONException e) {
                    Log.e(TAG, "-->Error reading data from API: " + e.getMessage());
                } finally {
                    try {
                        // Close input stream and reader
                        is.close();
                        reader.close();
                    } catch (Exception e) {
                        Log.e(TAG, "-->Error closing streams: " + e.getMessage());
                    }
                }
            } finally {
                // Disconnect the URL connection
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            Log.e(TAG, "-->Error connecting to API: " + e.getMessage());
        }
        return result;
    }

    /**
     * Fetches facility data from the API and saves it to the local database.
     *
     * @param db      SQLiteDatabase instance for database operations.
     * @param context Application context for accessing resources.
     */
    public static void saveFacilities(SQLiteDatabase db, Context context) {
        // Fetch facility data from the API
        List<Map<String, String>> apiData = fetchDataFromAPI();

        // Initialize DatabaseHelper for database operations
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        // Save fetched data to the local database
        databaseHelper.save(db, apiData);
    }
}
