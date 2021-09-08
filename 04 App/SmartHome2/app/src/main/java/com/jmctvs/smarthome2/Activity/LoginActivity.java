package com.jmctvs.smarthome2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jmctvs.smarthome2.Utilities.Utilities;
import com.jmctvs.smarthome2.Model.Account;
import com.jmctvs.smarthome2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText emailEd, passwordEd;
    private Button loginBtn, signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
    }

    private void initUI(){
        loginBtn = findViewById(R.id.btn_login);
        signupBtn = findViewById(R.id.btn_signup);
        emailEd = findViewById(R.id.ed_email);
        passwordEd = findViewById(R.id.ed_password);

        SharedPreferences pref = getSharedPreferences("SEC_AUTH", MODE_PRIVATE);
        String username = pref.getString("username", null);
        String password = pref.getString("password", null);
        if(username != null && password != null){
            emailEd.setText(username);
            passwordEd.setText(password);
        }
        loginBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_login){
            login();
        }
        else if(view.getId() == R.id.btn_signup){
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        }
    }

    private void login(){
        RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utilities.HOST + "/?action=login",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject json = new JSONObject();
                    try {
                        json = new JSONObject(response);
                        if(json.getString("code").equals("LoginOK")){
                            Utilities.setAccountObj(new Account(json.getString("uid"), json.getString("email"), json.getString("fullname")));
                            SharedPreferences pref = getSharedPreferences("SEC_AUTH", MODE_PRIVATE);
                            pref.edit().putString("username", emailEd.getText().toString()).commit();
                            pref.edit().putString("password", passwordEd.getText().toString()).commit();
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intent);
                        } else{
                            Utilities.setAccountObj(null);
                            SharedPreferences pref = getSharedPreferences("SEC_AUTH", MODE_PRIVATE);
                            pref.edit().putString("username", null).commit();
                            pref.edit().putString("password", null).commit();
                            Toast.makeText(getBaseContext(), "Đăng nhập thất bại. Vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    Log.d("SVMC", error.getMessage());
                }
            })
            {
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("email", emailEd.getText().toString());
                    params.put("password", passwordEd.getText().toString());
                    return params;
                }
            };
        queue.add(stringRequest);
    }

    @Override
    public void onBackPressed(){
        System.exit(0);
    }
}