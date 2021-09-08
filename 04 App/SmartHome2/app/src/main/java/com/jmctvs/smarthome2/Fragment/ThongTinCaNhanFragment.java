package com.jmctvs.smarthome2.Fragment;

import android.content.Intent;
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

public class ThongTinCaNhanFragment extends Fragment {

    private View view;
    private EditText uidEd, emailEd, fullnameEd;
    private Button updateBtn;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_thongtincanhan, container, false);
        initUI();
        return view;
    }

    private void initUI() {
        uidEd = view.findViewById(R.id.ed_uid);
        uidEd.setEnabled(false);
        emailEd = view.findViewById(R.id.ed_email);
        emailEd.setEnabled(false);
        fullnameEd = view.findViewById(R.id.ed_fullname);
        updateBtn = view.findViewById(R.id.btn_update_info);
        loadInfor();
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInfor();
            }
        });
    }

    private void loadInfor() {
        uidEd.setText(Utilities.getAccountObj().getUid());
        emailEd.setText(Utilities.getAccountObj().getEmail());
        fullnameEd.setText(Utilities.getAccountObj().getFullname());
    }

    private void updateInfor() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utilities.HOST + "/?action=update_info",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            if(json.getString("code").equals("UpdateInfoOK")){
                                Utilities.getAccountObj().setFullname(json.getString("fullname"));
                                Toast.makeText(getActivity().getBaseContext(), "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                                Utilities.setCurrentScreen("HomeScreen");
                                Intent intent = new Intent(getContext(), MainActivity.class);
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
                        Log.d("SVMC", error.getMessage());
                    }
                })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("uid", Utilities.getAccountObj().getUid());
                params.put("fullname", fullnameEd.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
