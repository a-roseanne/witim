package com.witim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class activity_login extends AppCompatActivity {


    private EditText et_pass, et_email;
    private Button btn_login;
    private  String email, pass;
    private ProgressBar pgBar;
    private static String url = "https://witim.000webhostapp.com/webservice/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

et_email = findViewById(R.id.edtText_Email);
et_pass = findViewById(R.id.edtText_Password);
btn_login = findViewById(R.id.btn_login);
pgBar = findViewById(R.id.pgBar);

btn_login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        email = et_email.getText().toString().trim();
        pass = et_pass.getText().toString().trim();

        if (!email.isEmpty() && !pass.isEmpty()){
            login(email, pass);
        }else {
            et_email.setError("Please fill this blank");
        }
    }
});
    }
    private  void  login(final String email, String password){
        pgBar.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.INVISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {
                                for (int i = 0; i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String name = object.getString("name").trim();
                                    String email = object.getString("email").trim();

                                    Toast.makeText(activity_login.this, "Welcome Back " + name, Toast.LENGTH_SHORT).show();
                                    Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(mainActivity);
                                }

                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(activity_login.this, "Error", Toast.LENGTH_SHORT).show();
                            pgBar.setVisibility(View.INVISIBLE);
                            btn_login.setVisibility(View.VISIBLE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity_login.this, "Error", Toast.LENGTH_SHORT).show();
                        pgBar.setVisibility(View.INVISIBLE);
                        btn_login.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                params.put("email", email);
                params.put("pass", pass);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
