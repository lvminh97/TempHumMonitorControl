package com.jmctvs.smarthome2.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jmctvs.smarthome2.Activity.MainActivity;
import com.jmctvs.smarthome2.Utilities.Utilities;
import com.jmctvs.thibanglaixe.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class DoiMatKhauFragment extends Fragment {
    private View view;

    private EditText oldPassEd, newPassEd, newPass2Ed;
    private Button updateBtn;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_doimatkhau, container, false);
        initUI();
        return view;
    }

    private void initUI() {
        oldPassEd = view.findViewById(R.id.ed_oldpass);
        newPassEd = view.findViewById(R.id.ed_newpass);
        newPass2Ed = view.findViewById(R.id.ed_newpass2);
        updateBtn = view.findViewById(R.id.btn_updatepass);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Utilities.HOST + "/?action=update_password",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject json = new JSONObject(response);
                                    String code = json.getString("code");
                                    if(code.equals("UpdateOK")){
                                        SharedPreferences pref = getActivity().getSharedPreferences("SEC_AUTH", MODE_PRIVATE);
                                        pref.edit().putString("password", newPassEd.getText().toString()).commit();
                                        Toast.makeText(getActivity().getBaseContext(), "Cập nhật mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                                        Utilities.setCurrentScreen("HomeScreen");
                                        Intent intent = new Intent(getContext(), MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else if(code.equals("WrongOldPass")){
                                        Toast.makeText(getActivity().getBaseContext(), "Sai mật khẩu cũ!", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(code.equals("NewPassMismatch")){
                                        Toast.makeText(getActivity().getBaseContext(), "Mật khẩu mới không khớp!", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(code.equals("ShortPassword")){
                                        Toast.makeText(getActivity().getBaseContext(), "Mật khẩu mới không được ngắn hơn 8 ký tự!", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("SVMC", error.getMessage());
                            }
                        })
                {
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("uid", Utilities.getAccountObj().getUid());
                        params.put("oldpass", oldPassEd.getText().toString());
                        params.put("newpass", newPassEd.getText().toString());
                        params.put("newpass2", newPass2Ed.getText().toString());
                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }
}
