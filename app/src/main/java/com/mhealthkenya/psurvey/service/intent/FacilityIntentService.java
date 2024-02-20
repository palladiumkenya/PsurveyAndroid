package com.mhealthkenya.psurvey.service.intent;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.mhealthkenya.psurvey.helper.DatabaseHelper;

/**
 * FacilityIntentService is an IntentService responsible for handling background tasks related to
 * facilities in the PSurvey application, such as bulk saving data to the local database.
 */
public class FacilityIntentService extends IntentService {
    private static final String TAG = FacilityIntentService.class.getCanonicalName();
    private DatabaseHelper databaseHelper;

    /**
     * Default constructor for FacilityIntentService.
     */
    public FacilityIntentService() {
        super(TAG);
    }

    /**
     * Called when the service is created. Initializes the DatabaseHelper instance.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        databaseHelper = new DatabaseHelper(this);
    }

    /**
     * Called when the IntentService is started. Handles the background intent processing,
     * specifically bulk saving data to the local database.
     *
     * @param intent The Intent passed to the service.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "--> onHandleIntent: .. Start");
        // Check if DatabaseHelper is initialized
        if (databaseHelper != null) {
            // Perform bulk saving of data to the local database
            databaseHelper.bulkSave(this);
            Log.i("-->Bulk save", "Bulk Location save");
        } else {
            Log.e(TAG, "-->DatabaseHelper is null");
        }
        Log.i(TAG, "-->OnHandleIntent..end");
    }
}
