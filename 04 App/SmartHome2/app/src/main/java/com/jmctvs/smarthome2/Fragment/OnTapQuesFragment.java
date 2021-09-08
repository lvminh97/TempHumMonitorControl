package com.jmctvs.smarthome2.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jmctvs.smarthome2.Utilities.Utilities;
import com.jmctvs.smarthome2.Model.Question;
import com.jmctvs.smarthome2.Model.QuestionAdapter;
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

public class OnTapQuesFragment extends Fragment implements View.OnClickListener{
    private Context context;
    private View view;
    private Button submitBtn;
    private ListView quesList;
    private QuestionAdapter questionOnTapAdapter;

    private ArrayList<Question> questions;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        context = container.getContext();
        view = inflater.inflate(R.layout.fragment_ontap_ques, container, false);
        initUI();
        return view;
    }

    private void initUI(){
        quesList = view.findViewById(R.id.list_ontap_ques);
        loadQues();
        questionOnTapAdapter = new QuestionAdapter((Activity) context, questions);
        quesList.setAdapter(questionOnTapAdapter);
        submitBtn = view.findViewById(R.id.btn_ontap_ques);
        submitBtn.setOnClickListener(this);

    }

    private void loadQues(){
        questions = new ArrayList<Question>();
        Utilities.setAnsData(new HashMap<String, String>());
        try {
            JSONArray jsonArray = new JSONArray(Utilities.getQuesData());
            for(int j = 0; j < jsonArray.length(); j++){
                questions.add(new Question(jsonArray.getJSONObject(j).getString("id"),
                        jsonArray.getJSONObject(j).getString("ques"),
                        jsonArray.getJSONObject(j).getString("ans1"),
                        jsonArray.getJSONObject(j).getString("ans2"),
                        jsonArray.getJSONObject(j).getString("ans3"),
                        jsonArray.getJSONObject(j).getString("ans4"),
                        "",
                        jsonArray.getJSONObject(j).getString("ques_image"),
                        jsonArray.getJSONObject(j).getString("important"),
                        ""));
                Utilities.getAnsData().put(jsonArray.getJSONObject(j).getString("id"), "0");
            }
//            Log.d("SVMC", Utilities.getAnsData().size() + "");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_ontap_ques){
            RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Utilities.HOST + "/?action=submit_answer",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Utilities.setAnswerData(response);
                            loadResultList();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            Log.d("SVMC", error.getMessage());
                        }
                    })
            {
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("uid", Utilities.getAccountObj().getUid());
                    JSONArray jsonArray = new JSONArray();
                    for(int i = 0; i < questions.size(); i++){
                        JSONObject json = new JSONObject();
                        try {
                            String quesId = questions.get(i).getQuesId();
                            json.put("ques_id", quesId);
                            json.put("ans", Utilities.getAnsData().getOrDefault(quesId, "0"));
                            jsonArray.put(json);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    params.put("answer", jsonArray.toString());
                    params.put("type", "ontap");
                    return params;
                }
            };
            queue.add(stringRequest);
        }
    }

    private void loadResultList(){
        Utilities.setCurrentScreen("OnTapResult");
        Fragment fragment = new OnTapResultFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_framelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
