package com.mhealthkenya.psurvey.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.mhealthkenya.psurvey.activities.AllQuestionDatabase;
import com.mhealthkenya.psurvey.activities.auth.LoginActivity;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.interfaces.UserCredentialsDao;
import com.mhealthkenya.psurvey.models.UrlTable;
import com.mhealthkenya.psurvey.models.UserCredentials;
import com.mhealthkenya.psurvey.models.auth;
import com.mhealthkenya.psurvey.utils.ConnectivityUtils;
import com.mhealthkenya.psurvey.utils.PasswordHasher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LoginService extends IntentService {

    private static final long CHECK_INTERVAL = 30000;
    private UserCredentialsDao userCredentialsDao;
    private AllQuestionDatabase allQuestionDatabase;
    private String z;
    public LoginService() {
        super("LoginService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        allQuestionDatabase = AllQuestionDatabase.getInstance(this);
        userCredentialsDao = allQuestionDatabase.userCredentialsDao();
        try {
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        }catch (Exception e){
            Log.d("No baseURL", e.getMessage());
        }
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while (true) {
            if (ConnectivityUtils.isConnected(LoginService.this)) {
                attemptLogin();
                try {
                    // Sleep for CHECK_INTERVAL milliseconds before checking again
                    Thread.sleep(CHECK_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                // If not connected, wait and check again
                try {
                    // Sleep for CHECK_INTERVAL milliseconds before checking again
                    Thread.sleep(CHECK_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void attemptLogin() {
        UserCredentials storedCredentials = userCredentialsDao.getUserCredentials();
        if (storedCredentials != null) {
            String phoneNumber = storedCredentials.getPhoneNumber();
            String password = PasswordHasher.decryptPassword(storedCredentials.getPassword());

            AndroidNetworking.post(z + Constants.LOGIN)
                    .addHeaders("Content-Type", "application.json")
                    .addHeaders("Connection", "keep-alive")
                    .addHeaders("Accept", "application/json")
                    .addBodyParameter("msisdn", phoneNumber)
                    .addBodyParameter("password", password)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Handle successful login response
                            try {
                                String authToken = response.getString("auth_token");
                                saveUserCredentialsLocally(phoneNumber, password);
                                // Send broadcast with the obtained token
                                Intent broadcastIntent = new Intent();
                                broadcastIntent.setAction("LOGIN_SUCCESS");
                                broadcastIntent.putExtra("authToken", authToken);
                                sendBroadcast(broadcastIntent);
                                Log.i("-->LoginService", authToken);
                                auth newUser = new auth(authToken);

                                Stash.put(Constants.AUTH_TOKEN, newUser);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e("-->LoginService", "Login Error: " + anError.getErrorDetail());
                        }
                    });
        }
    }
    private void saveUserCredentialsLocally(String phoneNumber, String password) {
        UserCredentials userCredentials = new UserCredentials(phoneNumber, PasswordHasher.encryptPassword(password));
        if (allQuestionDatabase.currentUserDao().getCurrentUser() != null) {

            Log.i("-->Delete UserDetails ", allQuestionDatabase.currentUserDao().getCurrentUser().getFirstName());
            allQuestionDatabase.currentUserDao().deleteUserDetails();
        }
        userCredentialsDao.deleteUserCredentials();
        userCredentialsDao.insert(userCredentials);
        Log.i("-->Save User", "Saving User");
    }
}
