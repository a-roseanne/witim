package com.witim;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private ActionBar bar;
    private EditText edt_name,edt_email, edt_pass, edt_cpass;
    private String name="",email="", pass="", cpass="";
    private Button btn_reg;
    private ProgressBar pgBar;
    private static String urlReg = "https://witim.000webhostapp.com/webservice/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bar = getSupportActionBar();
        bar.setTitle("Register");
        bar.setDisplayHomeAsUpEnabled(true);

        btn_reg = findViewById(R.id.btn_reg);
        pgBar = findViewById(R.id.pgBar);
        edt_name = findViewById(R.id.edtText_name);
        edt_email = findViewById(R.id.edtText_Email);
        edt_pass = findViewById(R.id.edtText_Password);
        edt_cpass = findViewById(R.id.edtText_ConfirmPass);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgBar.setVisibility(View.VISIBLE);
                btn_reg.setVisibility(View.INVISIBLE);

                name = edt_name.getText().toString().trim();
                email= edt_email.getText().toString().trim();
                pass = edt_pass.getText().toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
                    Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                }else{
                    reg();
                }
            }
        });
    }

    private void reg(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlReg,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if(success.equals("1")){
                            Toast.makeText(RegisterActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                            Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(mainActivity);
                        }

                    }catch (JSONException e){
                        e.printStackTrace();
                        Log.e("anyText",response);
                        Toast.makeText(RegisterActivity.this, "Register Failed! " + e.toString(), Toast.LENGTH_SHORT).show();
                        pgBar.setVisibility(View.INVISIBLE);
                        btn_reg.setVisibility(View.VISIBLE);
                    }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(RegisterActivity.this, "Register Failed! " + error.toString(), Toast.LENGTH_SHORT).show();
                        pgBar.setVisibility(View.INVISIBLE);
                        btn_reg.setVisibility(View.VISIBLE);

                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("pass", pass);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

}
