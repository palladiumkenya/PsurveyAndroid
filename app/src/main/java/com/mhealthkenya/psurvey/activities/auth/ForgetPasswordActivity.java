package com.mhealthkenya.psurvey.activities.auth;

import static com.mhealthkenya.psurvey.depedancies.AppController.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.models.UrlTable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ForgetPasswordActivity extends AppCompatActivity {
    private Button btn_forget_password;
    private TextInputEditText  edtxt_phone_no_reset, edtxt_email_reset, edtxt_pass_conf_reset, edtxt_pass_reset;
    private ProgressDialog progressDialog;
    private String z, zz;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreget_password);
        initialise();
        initToolbar();

            try {
                List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
                if (_url.size()==1){
                    for (int x=0; x<_url.size(); x++){
                        z=_url.get(x).getBase_url1();
                        zz=_url.get(x).getStage_name1();
                        Toast.makeText(ForgetPasswordActivity.this, "You are connected to" + " " +zz, Toast.LENGTH_LONG).show();


                    }
                }

            }catch (Exception e){
                Log.d("No baseURL", e.getMessage());
            }

        Stash.init(this);

        progressDialog = new ProgressDialog(ForgetPasswordActivity.this);
        progressDialog.setTitle("Request Password Reset...");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        btn_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler =  new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String password = edtxt_pass_reset.getText().toString();
                        if (edtxt_email_reset.getText().toString().isEmpty()){
                            Snackbar.make(findViewById(R.id.forget_password_lyt),"Please enter email address", Snackbar.LENGTH_LONG).show();
                        } else if (edtxt_phone_no_reset.getText().toString().isEmpty()){
                            Snackbar.make(findViewById(R.id.forget_password_lyt),"Please enter phone number", Snackbar.LENGTH_LONG).show();
                        } else if (edtxt_pass_conf_reset.getText().toString().isEmpty()){
                            Snackbar.make(findViewById(R.id.forget_password_lyt),"Please confirm your password", Snackbar.LENGTH_LONG).show();
                        } else if (password.isEmpty() || password.length() < 8) {
                            Snackbar.make(findViewById(R.id.forget_password_lyt), "Password should not be less than 8 characters", Snackbar.LENGTH_LONG).show();
                        }
                        else {
                            requestPasswordChange(edtxt_email_reset.getText().toString(),
                                    edtxt_phone_no_reset.getText().toString(), password,edtxt_pass_conf_reset.getText().toString());
                        }
                    }
                });
            }
        });
    }
    private void initialise(){
        edtxt_email_reset = findViewById(R.id.edtxt_email_reset);
        edtxt_phone_no_reset = findViewById(R.id.edtxt_phone_no_reset);
        btn_forget_password = findViewById(R.id.btn_forget_password);
        edtxt_pass_conf_reset = findViewById(R.id.edtxt_pass_conf_reset);
        edtxt_pass_reset = findViewById(R.id.edtxt_pass_reset);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.forget_pass_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Forgot Password");
    }

    private void requestPasswordChange(String email, String phoneNumber, String password, String confirmPassword){
        if (password.equals(confirmPassword)){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("msisdn", phoneNumber);
                jsonObject.put("email", email);
                jsonObject.put("password", password);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try{
                List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
                if (_url.size()==1){
                    for (int x=0; x<_url.size(); x++){
                        z=_url.get(x).getBase_url1();
                    }
                }

            } catch(Exception e){

            }

            // AndroidNetworking.initialize(getApplicationContext(), myUnsafeHttpClient());
            AndroidNetworking.post(z+ Constants.FORGET_PASSWORD)
                    .addHeaders("Content-Type", "application.json")
                    // .addHeaders("Accept", "gzip, deflate, br")
                    .addHeaders("Connection","keep-alive")
                    .addHeaders("Accept", "application/json")
                    .addJSONObjectBody(jsonObject) // posting json
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response

//                        Log.e(TAG, response.toString());

                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.hide();
                                progressDialog.cancel();
                            }
                            try {
                                boolean  status = response.has("success") && response.getBoolean("success");
                                if (status){
                                    requestSuccess();
                                }else if(!response.has("message")){
                                    Toast.makeText(ForgetPasswordActivity.this, "Bad Request, contact Administrator", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    if (progressDialog != null && progressDialog.isShowing()) {
                                        progressDialog.hide();
                                        progressDialog.cancel();
                                    }

                                    Toast.makeText(ForgetPasswordActivity.this, "Bad Request, contact Administrator", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e){
                                Log.d("-->Request Password", e.getMessage());
                            }
                        }

                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.e(TAG, String.valueOf(error.getErrorCode()));

                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.hide();
                                progressDialog.cancel();
                            }

                            if (error.getErrorBody() != null && error.getErrorBody().contains("invalid details")){

                                Snackbar.make(findViewById(R.id.forget_password_lyt), "Invalid phone number or email." , Snackbar.LENGTH_LONG).show();
                            }
                            else {

                                // Snackbar.make(findViewById(R.id.login_lyt), "Error: " + error.getErrorCode(), Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            Snackbar.make(findViewById(R.id.forget_password_lyt), "Your password do not match!", Snackbar.LENGTH_LONG).show();
        }

    }

    private void requestSuccess(){
        Intent requestIntent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
        requestIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(requestIntent);
        Toast.makeText(ForgetPasswordActivity.this, "Request Successful!", Toast.LENGTH_SHORT).show();
    }
}
