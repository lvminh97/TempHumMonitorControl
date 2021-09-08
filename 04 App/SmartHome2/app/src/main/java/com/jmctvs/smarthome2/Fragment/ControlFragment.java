package com.jmctvs.smarthome2.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jmctvs.smarthome2.R;
import com.jmctvs.smarthome2.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ControlFragment extends Fragment implements View.OnClickListener{

    private View view;
    private Button fanBtn, mistBtn, servoBtn;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_control, container, false);
        initUI();
        initControlStatus();
        return view;
    }

    private void initUI(){
        fanBtn = view.findViewById(R.id.btn_fan);
        mistBtn = view.findViewById(R.id.btn_mist);
        servoBtn = view.findViewById(R.id.btn_servo);

        fanBtn.setOnClickListener(this);
        mistBtn.setOnClickListener(this);
        servoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_fan:
                setControl("fan", Utils.fanStt == 1 ? "0" : "1");
                break;
            case R.id.btn_mist:
                setControl("mist", Utils.mistStt == 1 ? "0" : "1");
                break;
            case R.id.btn_servo:
                setControl("servo", Utils.servoStt == 1 ? "0" : "1");
                break;
        }

    }

    private void setControl(String param, String value) {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.server + ":" + Utils.port + "/giamsatkhongkhi/setdata.php?" + param + "=" + value,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            if(!json.isNull("fan")){
                                Utils.fanStt = Integer.parseInt(json.getString("fan"));
                                if(Integer.parseInt(json.getString("fan")) == 1){
                                    fanBtn.setBackgroundColor(0xFF2FB36D);
                                    fanBtn.setText("Bật");
                                }
                                else{
                                    fanBtn.setBackgroundColor(0xFFFA0F07);
                                    fanBtn.setText("Tắt");
                                }
                            }
                            if(!json.isNull("mist")){
                                Utils.mistStt = Integer.parseInt(json.getString("mist"));
                                if(Integer.parseInt(json.getString("mist")) == 1){
                                    mistBtn.setBackgroundColor(0xFF2FB36D);
                                    mistBtn.setText("Bật");
                                }
                                else{
                                    mistBtn.setBackgroundColor(0xFFFA0F07);
                                    mistBtn.setText("Tắt");
                                }
                            }
                            if(!json.isNull("servo")){
                                Utils.servoStt = Integer.parseInt(json.getString("servo"));
                                if(Integer.parseInt(json.getString("servo")) == 1){
                                    servoBtn.setBackgroundColor(0xFF2FB36D);
                                    servoBtn.setText("Bật");
                                }
                                else{
                                    servoBtn.setBackgroundColor(0xFFFA0F07);
                                    servoBtn.setText("Tắt");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getContext(),"Đã gửi lệnh", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        queue.add(stringRequest);
    }

    private void initControlStatus(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.server + ":" + Utils.port + "/giamsatkhongkhi/getdata.php?",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            Utils.fanStt = Integer.parseInt(json.getString("fan"));
                            Utils.mistStt = Integer.parseInt(json.getString("mist"));
                            Utils.servoStt = Integer.parseInt(json.getString("servo"));

                            if(Utils.fanStt == 1) {
                                fanBtn.setBackgroundColor(0xFF2FB36D);
                                fanBtn.setText("Bật");
                            }
                            else{
                                fanBtn.setBackgroundColor(0xFFFA0F07);
                                fanBtn.setText("Tắt");
                            }

                            if(Utils.mistStt == 1) {
                                mistBtn.setBackgroundColor(0xFF2FB36D);
                                mistBtn.setText("Bật");
                            }
                            else{
                                mistBtn.setBackgroundColor(0xFFFA0F07);
                                mistBtn.setText("Tắt");
                            }

                            if(Utils.servoStt == 1) {
                                servoBtn.setBackgroundColor(0xFF2FB36D);
                                servoBtn.setText("Bật");
                            }
                            else{
                                servoBtn.setBackgroundColor(0xFFFA0F07);
                                servoBtn.setText("Tắt");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("SVMC2", "Get control fail");
                    }
                });

        queue.add(stringRequest);
    }
}
