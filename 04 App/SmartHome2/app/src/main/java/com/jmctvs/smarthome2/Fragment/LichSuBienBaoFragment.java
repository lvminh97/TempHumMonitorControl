package com.jmctvs.smarthome2.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jmctvs.smarthome2.Utilities.Utilities;
import com.jmctvs.smarthome2.Model.Sign;
import com.jmctvs.smarthome2.Model.SignHistoryAdapter;
import com.jmctvs.thibanglaixe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class LichSuBienBaoFragment extends Fragment {
    private Context context;
    private View view;
    private ListView historyListView;

    private SignHistoryAdapter signHistoryAdapter;
    private ArrayList<Sign> signs;

    private JSONArray jsonArray;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        context = container.getContext();
        view = inflater.inflate(R.layout.fragment_lichsubienbao, container, false);
        loadHistories();
        initUI();
        return view;
    }

    private void initUI(){
        historyListView = view.findViewById(R.id.list_signhistory);
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                loadHistory(i);
            }
        });
    }

    private void loadHistories() {
        signs = new ArrayList<Sign>();
        RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utilities.HOST + "/?action=get_sign_history_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            jsonArray = new JSONArray(response);
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject json = jsonArray.getJSONObject(i);
                                signs.add(new Sign("", json.getString("datetime"), json.getString("title")));
                            }
                            signHistoryAdapter = new SignHistoryAdapter((Activity) context, signs);
                            historyListView.setAdapter(signHistoryAdapter);
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
                params.put("uid", Utilities.getAccountObj().getUid());
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void loadHistory(int i){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utilities.HOST + "/?action=get_sign_history",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            Utilities.setSign(new Sign(json.getString("img"),
                                    json.getString("datetime"),
                                    json.getString("title")));
                            Utilities.setCurrentScreen("ChiTietLichSuBienBao");
                            Fragment fragment = new ChiTietLichSuBienBaoFragment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.nav_host_framelayout, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
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
                params.put("uid", Utilities.getAccountObj().getUid());
                try {
                    params.put("time", jsonArray.getJSONObject(i).getString("time"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
