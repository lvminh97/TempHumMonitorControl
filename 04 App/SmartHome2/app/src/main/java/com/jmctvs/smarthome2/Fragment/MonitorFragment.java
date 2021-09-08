package com.jmctvs.smarthome2.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MonitorFragment extends Fragment {

    private View view;

    private TextView tempTv, humiTv, luxTv, timeUpdatedTv;

    private Timer timer;
    private MyTimerTask myTimerTask;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_monitor, container, false);
        initUI();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);

        updateData();

        timer = new Timer();
        myTimerTask = new MyTimerTask();
        timer.schedule(myTimerTask, 0, 5000);
    }

    private void initUI(){
        tempTv = view.findViewById(R.id.tv_temp);
        humiTv = view.findViewById(R.id.tv_humi);
        luxTv = view.findViewById(R.id.tv_lux);
        timeUpdatedTv = view.findViewById(R.id.tv_timeupdated);
    }

    private void updateData(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.server + ":" + Utils.port + "/giamsatkhongkhi/getdata.php?",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            tempTv.setText(json.getString("temp") + " oC");
                            humiTv.setText(json.getString("humi") + " %");
                            luxTv.setText(json.getString("lux") + " lux");
                            timeUpdatedTv.setText("Cập nhật: " + json.getString("time"));

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

    private class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            updateData();
        }
    }
}
