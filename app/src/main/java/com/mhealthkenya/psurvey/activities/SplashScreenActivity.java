package com.mhealthkenya.psurvey.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.service.intent.FacilityIntentService;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Intent intent = new Intent(this, FacilityIntentService.class);
        startService(intent);
        Log.i("-->Facility Service", "Start Facility service intent");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);

                    startActivity(new Intent(SplashScreenActivity.this, SelectUrls.class));
                   // startActivity(new Intent(SplashScreenActivity.this, Query2.class));
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
