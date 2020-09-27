package com.example.security;import android.os.Bundle;import android.view.View;import android.widget.ArrayAdapter;import android.widget.Button;import android.widget.EditText;import android.widget.ProgressBar;import android.widget.Spinner;import android.widget.Toast;import androidx.appcompat.app.AppCompatActivity;import com.android.volley.Request;import com.android.volley.RequestQueue;import com.android.volley.Response;import com.android.volley.VolleyError;import com.android.volley.toolbox.JsonObjectRequest;import com.android.volley.toolbox.StringRequest;import com.android.volley.toolbox.Volley;import org.json.JSONArray;import org.json.JSONException;import org.json.JSONObject;import java.util.ArrayList;import java.util.HashMap;import java.util.Map;public class Register extends AppCompatActivity {    static String URL_register = "https://localhost/security_db/register.php";    EditText full_name1;    EditText phone1;    ProgressBar loading;    Button register1;    EditText password1;    Spinner villages;    ArrayList<String> villageList = new ArrayList<>();    ArrayAdapter<String> villageAdapter;    RequestQueue requestQueue;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_register);        full_name1 = findViewById(R.id.full_name1);        phone1 = findViewById(R.id.phone1);        loading = findViewById(R.id.loading);        register1 = findViewById(R.id.register);        password1 = findViewById(R.id.password1);        villages = findViewById(R.id.villages);        String Url_village = "https://localhost/security_db/village.php";        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url_village, null, new Response.Listener<JSONObject>() {            @Override            public void onResponse(JSONObject response) {                JSONArray jsonArray = null;                try {                    jsonArray = response.getJSONArray("village");                    for (int i = 0; i < jsonArray.length(); i++) {                        JSONObject jsonObject = jsonArray.getJSONObject(i);                        String casename = jsonObject.optString("villagename");                        villageList.add(casename);                        villageAdapter = new ArrayAdapter<>(Register.this, android.R.layout.simple_spinner_dropdown_item);                        villageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);                        villages.setAdapter(villageAdapter);                    }                } catch (JSONException e) {                    e.printStackTrace();                }            }        }, new Response.ErrorListener() {            @Override            public void onErrorResponse(VolleyError error) {            }        });        requestQueue.add(jsonObjectRequest);        register1.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                register();            }        });    }    public void register() {        loading.setVisibility(View.VISIBLE);        register1.setVisibility(View.GONE);        final String name = this.full_name1.getText().toString().trim();        final String phone = this.phone1.getText().toString().trim();        final String village = this.villages.toString().trim();        final String password = this.password1.getText().toString().trim();        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_register,                new Response.Listener<String>() {                    @Override                    public void onResponse(String response) {                        try {                            JSONObject jsonObject = new JSONObject(response);                            String success = jsonObject.getString("success");                            if (success.equals("1")) {                                Toast.makeText(Register.this, "register success", Toast.LENGTH_SHORT).show();                            }                        } catch (Exception e) {                            e.printStackTrace();                            Toast.makeText(Register.this, "register failed" + e.toString(), Toast.LENGTH_SHORT).show();                            loading.setVisibility(View.GONE);                            register1.setVisibility(View.VISIBLE);                        }                    }                },                new Response.ErrorListener() {                    @Override                    public void onErrorResponse(VolleyError error) {                        Toast.makeText(Register.this, "register failed" + error.toString(), Toast.LENGTH_SHORT).show();                        loading.setVisibility(View.GONE);                        register1.setVisibility(View.VISIBLE);                    }                }) {            @Override            protected Map<String, String> getParams() //throwsAuthFailureError            {                Map<String, String> params = new HashMap<>();                params.put("fullname", full_name1.getText().toString());                params.put("phone", phone1.getText().toString());                params.put("village", villages.toString());                params.put("password", password1.getText().toString());                return params;            }        };        RequestQueue requestQueue = Volley.newRequestQueue(this);        requestQueue.add(stringRequest);    }}