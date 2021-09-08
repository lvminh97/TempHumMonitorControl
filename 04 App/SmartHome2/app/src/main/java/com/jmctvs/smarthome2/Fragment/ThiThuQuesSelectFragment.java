package com.jmctvs.smarthome2.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jmctvs.smarthome2.Utilities.Utilities;
import com.jmctvs.smarthome2.Model.ExamAdapter;
import com.jmctvs.thibanglaixe.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ThiThuQuesSelectFragment extends Fragment {

    private Context context;
    private View view;
    private GridView examList;
    private ExamAdapter examAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        context = container.getContext();
        view = inflater.inflate(R.layout.fragment_thithu_ques_select, container, false);
        initUI();
        return view;
    }

    private void initUI(){
        examList = view.findViewById(R.id.grid_thithu_ques);
        ArrayList<String> exam = new ArrayList<String>();
        int numExam = 0;
        if(Utilities.getPack().equals("A1")) numExam = 8;
        else if(Utilities.getPack().equals("A2")) numExam = 18;
        else if(Utilities.getPack().equals("B1")) numExam = 18;
        for(int i = 1; i <= numExam; i++){
            exam.add("" + i);
        }
        examAdapter = new ExamAdapter((Activity) context, exam);
        examList.setAdapter(examAdapter);
        examList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Utilities.setExam("" + (i + 1));
                RequestQueue queue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Utilities.HOST + "/?action=get_exam",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Utilities.setQuesData(response);
                                loadQuesList();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                                Log.d("SVMC", error.getMessage());
                            }
                        })
                {
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("uid", Utilities.getAccountObj().getUid());
                        params.put("exam", Utilities.getExam());
                        params.put("pack", Utilities.getPack());
                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }

    private void loadQuesList(){
        Utilities.setCurrentScreen("ThiThuQues");
        Fragment fragment = new ThiThuQuesFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_framelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
