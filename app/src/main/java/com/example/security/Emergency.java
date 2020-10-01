package com.example.security;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Emergency extends AppCompatActivity {
    static String URL_register = "https://localhost/securityserver/notificationpath.php";

    TextView username;
    TextView phone;
    Button emergencyButton;
    String fn;
    String pn;
    Spinner casesSpinner;
    ArrayList<String> caseList = new ArrayList<>();
    ArrayAdapter<String> caseListAdapter;
    RequestQueue r;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        r = Volley.newRequestQueue(this);
        username = findViewById(R.id.username);
        phone = findViewById(R.id.userphone);
        emergencyButton = findViewById(R.id.emergency_button);
        fn = getIntent().getExtras().getString("name");
        pn = getIntent().getExtras().getString("phone");
        casesSpinner = findViewById(R.id.sos_case_spinner);
        String Url_case = "https://localhost/securityserver/case.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url_case, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("cases");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String casename = jsonObject.optString("casename");
                        caseList.add(casename);
                        caseListAdapter = new ArrayAdapter<>(Emergency.this, android.R.layout.simple_spinner_dropdown_item);
                        caseListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        casesSpinner.setAdapter(caseListAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });
        r.add(jsonObjectRequest);
        emergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emergency();
            }
        });
    }

    private void emergency() {

    }
}
