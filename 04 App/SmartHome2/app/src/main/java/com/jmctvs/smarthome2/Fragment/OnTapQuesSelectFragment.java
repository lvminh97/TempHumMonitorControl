package com.jmctvs.smarthome2.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jmctvs.smarthome2.Utilities.Utilities;
import com.jmctvs.thibanglaixe.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class OnTapQuesSelectFragment extends Fragment implements View.OnClickListener{
    private View view;
    private TextView onTapGoiTv;
    private Button luatBtn, bienbaoBtn, sahinhBtn;
//    private ListView a1PackListView;
    private String pack;
    private int numQues;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_ontap_ques_select, container, false);
        initUI();
        return view;
    }

    private void initUI(){
        onTapGoiTv = view.findViewById(R.id.tv_ontap_goi);
        pack = Utilities.getPack();
        onTapGoiTv.setText("Gói ôn tập " + pack);

        luatBtn = view.findViewById(R.id.btn_ontap_luat);
        bienbaoBtn = view.findViewById(R.id.btn_ontap_bienbao);
        sahinhBtn = view.findViewById(R.id.btn_ontap_sahinh);
        luatBtn.setOnClickListener(this);
        bienbaoBtn.setOnClickListener(this);
        sahinhBtn.setOnClickListener(this);
    }

    private void loadQuesList(){
        Utilities.setCurrentScreen("OnTapQues");
        Fragment fragment = new OnTapQuesFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_framelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        String type = "";
        switch (view.getId()){
            case R.id.btn_ontap_luat:
                type = "luat";
                break;
            case R.id.btn_ontap_bienbao:
                type = "bienbao";
                break;
            case R.id.btn_ontap_sahinh:
                type = "sahinh";
                break;
        }
        RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
        String finalType = type;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utilities.HOST + "/?action=get_ques",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArr = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Utilities.setQuesData(response);
                        loadQuesList();
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
                params.put("pack", Utilities.getPack());
                params.put("type", finalType);
//                params.put("start", "" + i);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
