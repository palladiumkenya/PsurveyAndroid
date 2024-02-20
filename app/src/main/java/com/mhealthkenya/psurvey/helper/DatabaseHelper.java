package com.mhealthkenya.psurvey.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.mhealthkenya.psurvey.utils.PSurveyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DatabaseHelper class manages the SQLite database operations for the PSurvey application.
 * It includes methods for creating tables, saving facility data, and querying distinct counties,
 * sub-counties, facility names, and facility IDs.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = DatabaseHelper.class.getCanonicalName();

    // Define table and column names
    public static final String TABLE_FACILITIES = "facilities";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MFL_CODE = "mfl_code";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COUNTY = "county";
    public static final String COLUMN_SUB_COUNTY = "sub_county";

    public static final Map<Integer, String> CSV_COLUMN_MAPPING;

    static {
        CSV_COLUMN_MAPPING = new HashMap<>();
        CSV_COLUMN_MAPPING.put(0, COLUMN_ID);
        CSV_COLUMN_MAPPING.put(1, COLUMN_MFL_CODE);
        CSV_COLUMN_MAPPING.put(2, COLUMN_NAME);
        CSV_COLUMN_MAPPING.put(3, COLUMN_COUNTY);
        CSV_COLUMN_MAPPING.put(4, COLUMN_SUB_COUNTY);
    }

    // Define database properties
    private static final String DATABASE_NAME = "pSurvey.db";
    private static final int DATABASE_VERSION = 1;

    // Define table creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_FACILITIES + "(" + COLUMN_ID
            + " integer primary key, " + COLUMN_MFL_CODE
            + " text, " + COLUMN_NAME
            + " text not null, " + COLUMN_COUNTY
            + " text not null, " + COLUMN_SUB_COUNTY
            + " text not null);";

    /**
     * Constructor for DatabaseHelper.
     *
     * @param context Application context.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created. Executes the SQL statement to create the facilities table.
     *
     * @param database SQLiteDatabase instance.
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        // Create the database table
        database.execSQL(DATABASE_CREATE);
        Log.i(TAG, "-->onCreate pSurvey: " + DATABASE_CREATE);
    }

    /**
     * Saves facility data to the local database. Updates existing entries or inserts new ones.
     *
     * @param database   SQLiteDatabase instance.
     * @param facilities List of facility data as maps.
     */
    public void save(@Nullable SQLiteDatabase database, @Nullable List<Map<String, String>> facilities) {
        if (database != null && facilities != null && !facilities.isEmpty()) {
            try {
                database.beginTransaction();
                for (Map<String, String> facility : facilities) {
                    ContentValues cv = new ContentValues();
                    for (String column : facility.keySet()) {
                        String value = facility.get(column);
                        cv.put(column, value);
                    }
                    Long id = checkIfExists(database, cv.getAsString(COLUMN_ID));
                    if (id != null) {
                        database.update(TABLE_FACILITIES, cv, COLUMN_ID + " = ?", new String[]{id.toString()});
                    } else {
                        database.insert(TABLE_FACILITIES, null, cv);
                    }
                }
                database.setTransactionSuccessful();
                Log.i(TAG, "-->save Location: Successful");
            } catch (SQLException e) {
                Log.e(TAG, e.getMessage());
            } finally {
                database.endTransaction();
            }
        }
    }

    /**
     * Checks if a record with the given ID already exists in the database.
     *
     * @param database SQLiteDatabase instance.
     * @param id       ID to check.
     * @return ID if it exists, otherwise null.
     */
    private Long checkIfExists(SQLiteDatabase database, String id) {
        Cursor mCursor = null;
        Long exists = null;

        try {
            String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_FACILITIES + " WHERE " + COLUMN_ID + " = '" + id + "' COLLATE NOCASE ";
            mCursor = database.rawQuery(query, null);
            if (mCursor != null && mCursor.moveToFirst()) {
                exists = mCursor.getLong(0);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (mCursor != null) mCursor.close();
        }
        return exists;
    }

    /**
     * Performs a bulk save by invoking the `save` method with data fetched from the PSurvey API.
     *
     * @param context Application context.
     */
    public void bulkSave(Context context) {
        SQLiteDatabase database = getWritableDatabase();
        PSurveyUtils.saveFacilities(database, context);
        Log.i(TAG, "-->bulkSave: Saving...");
    }

    /**
     * Retrieves a list of distinct counties from the facilities table.
     *
     * @return List of distinct counties.
     */
    public List<String> getDistinctCounties() {
        List<String> countyList = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = database.query(true, TABLE_FACILITIES, new String[]{COLUMN_COUNTY}, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String county = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTY));
                    countyList.add(county);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "-->Error getting distinct counties: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }
        return countyList;
    }

    /**
     * Retrieves a list of sub-counties based on the selected county.
     *
     * @param selectedCounty Selected county.
     * @return List of sub-counties.
     */
    public List<String> getSubCountiesByCounty(String selectedCounty) {
        List<String> subCountyList = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = database.query(true, TABLE_FACILITIES, new String[]{COLUMN_SUB_COUNTY}, COLUMN_COUNTY + " = ?", new String[]{selectedCounty}, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String subCounty = cursor.getString(cursor.getColumnIndex(COLUMN_SUB_COUNTY));
                    subCountyList.add(subCounty);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "-->Error getting sub-counties: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }

        return subCountyList;
    }

    /**
     * Retrieves a list of facility names based on the selected sub-county.
     *
     * @param selectedSubCounty Selected sub-county.
     * @return List of facility names.
     */
    public List<String> getFacilityNamesBySubCounty(String selectedSubCounty) {
        List<String> facilityList = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = database.query(TABLE_FACILITIES, new String[]{COLUMN_NAME}, COLUMN_SUB_COUNTY + " = ?", new String[]{selectedSubCounty}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String facilityName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                    facilityList.add(facilityName);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "-->Error getting facility names: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }

        return facilityList;
    }

    /**
     * Retrieves the facility ID based on the selected facility name.
     *
     * @param selectedFacility Selected facility name.
     * @return Facility ID.
     */
    public int getFacilityId(String selectedFacility) {
        int facilityId = -1;

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = database.query(TABLE_FACILITIES, new String[]{COLUMN_ID}, COLUMN_NAME + " = ?", new String[]{selectedFacility}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                facilityId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            }
        } catch (Exception e) {
            Log.e(TAG, "-->Error getting facility ID: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }

        return facilityId;
    }

    /**
     * Called when the database needs to be upgraded. Handles database upgrades, if needed.
     *
     * @param db         SQLiteDatabase instance.
     * @param oldVersion Old database version.
     * @param newVersion New database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades, if needed
    }
}
