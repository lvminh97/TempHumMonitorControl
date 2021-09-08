package com.jmctvs.smarthome2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.jmctvs.thibanglaixe.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText emailEd, passwordEd, fullnameEd;
    private Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initUI();
    }

    private void initUI(){
        emailEd = findViewById(R.id.ed_email);
        passwordEd = findViewById(R.id.ed_password);
        fullnameEd = findViewById(R.id.ed_fullname);
        signupBtn = findViewById(R.id.btn_signup);
        signupBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_signup){
            signup();
        }
    }

    private void signup(){
        RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utilities.HOST + "/?action=signup",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            String code = json.getString("code");
                            if(code.equals("EmptyEmail")){
                                Toast.makeText(getBaseContext(), "Email không được để trống!", Toast.LENGTH_SHORT).show();
                            }
                            else if(code.equals("WrongEmail")){
                                Toast.makeText(getBaseContext(), "Email sai định dạng!", Toast.LENGTH_SHORT).show();
                            }
                            else if(code.equals("ExistEmail")){
                                Toast.makeText(getBaseContext(), "Email đã có người sử dụng. Mời chọn email khác!", Toast.LENGTH_SHORT).show();
                            }
                            else if(code.equals("EmptyFullname")){
                                Toast.makeText(getBaseContext(), "Tên không được để trống!", Toast.LENGTH_SHORT).show();
                            }
                            else if(code.equals("ShortPassword")){
                                Toast.makeText(getBaseContext(), "Mật khẩu không được nhỏ hơn 8 ký tự!", Toast.LENGTH_SHORT).show();
                            }
                            else if(code.equals("SignupOK")){
                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Log.d("SVMC", error.getMessage());
                    }
                })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("email", emailEd.getText().toString());
                params.put("password", passwordEd.getText().toString());
                params.put("fullname", fullnameEd.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}