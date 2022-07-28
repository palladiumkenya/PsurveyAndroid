package com.mhealthkenya.psurvey.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.internal.ANRequestQueue;
import com.mhealthkenya.psurvey.R;

import org.json.JSONArray;
import org.json.JSONException;

public class QuestionsActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        requestQueue = Volley.newRequestQueue(this);
        button =    findViewById(R.id.get_all);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAll();
            }
        });


        //getAll();


    }

    public void getAll(){
        String url = "https://psurvey-api.mhealthkenya.co.ke/api/questions/dep/all";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("ALl", response.toString());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}