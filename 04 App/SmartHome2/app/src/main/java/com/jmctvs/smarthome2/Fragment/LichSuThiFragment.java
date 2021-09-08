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
import com.jmctvs.smarthome2.Model.History;
import com.jmctvs.smarthome2.Model.HistoryAdapter;
import com.jmctvs.thibanglaixe.R;
import com.jmctvs.smarthome2.Utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class LichSuThiFragment extends Fragment {

    private Context context;
    private View view;
    private ListView historyListView;
    private HistoryAdapter historyAdapter;

    private ArrayList<History> histories;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        context = container.getContext();
        view = inflater.inflate(R.layout.fragment_lichsuthi, container, false);
        loadHistories();
        initUI();
        return view;
    }

    private void initUI() {
        historyListView = view.findViewById(R.id.list_history);

        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                loadHistory(i);
            }
        });
    }

    private void loadHistories() {
        histories = new ArrayList<History>();
        RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utilities.HOST + "/?action=get_history_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i < jsonArray.length(); i++){
                                histories.add(new History(jsonArray.getJSONObject(i).getString("answer_data"),
                                        jsonArray.getJSONObject(i).getString("correct"),
                                        jsonArray.getJSONObject(i).getString("wrong"),
                                        jsonArray.getJSONObject(i).getString("pack"),
                                        jsonArray.getJSONObject(i).getString("exam"),
                                        jsonArray.getJSONObject(i).getString("time")));
                            }
                            historyAdapter = new HistoryAdapter((Activity) context, histories);
                            historyListView.setAdapter(historyAdapter);
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
        Utilities.setPack(histories.get(i).getPack());
        RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utilities.HOST + "/?action=submit_answer",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Utilities.setAnswerData(response);
                        Utilities.setCurrentScreen("ChiTietLichSuThi");
                        Fragment fragment = new ThiThuResultFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_host_framelayout, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
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
                params.put("answer", histories.get(i).getAnswer_data());
                params.put("type", "ontap");
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
