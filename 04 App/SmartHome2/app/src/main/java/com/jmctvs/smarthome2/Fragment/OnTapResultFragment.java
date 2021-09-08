package com.jmctvs.smarthome2.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.jmctvs.smarthome2.Model.Question;
import com.jmctvs.smarthome2.Model.ResultAdapter;
import com.jmctvs.smarthome2.Utilities.Utilities;
import com.jmctvs.thibanglaixe.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class OnTapResultFragment extends Fragment {

    private Context context;
    private View view;
    private TextView pointTv;
    private ListView resultList;
    private ResultAdapter resultAdapter;

    private ArrayList<Question> questions;
    private ArrayList<String> yourAns;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        context = container.getContext();
        view = inflater.inflate(R.layout.fragment_ontap_result, container, false);
        initUI();
        return view;
    }

    private void initUI(){
        pointTv = view.findViewById(R.id.tv_point);
        resultList = view.findViewById(R.id.list_ontap_result);
        loadData();
        resultAdapter = new ResultAdapter((Activity) context, questions, yourAns);
        resultList.setAdapter(resultAdapter);
        int point = 0, total = yourAns.size();
        Log.d("SVMC", yourAns.size() + "  " + Utilities.getAnsData().size());
        for(int i = 0; i < total; i++){
            if(yourAns.get(i).equals(questions.get(i).getCorrect_ans())) point++;
        }
        pointTv.setText("Kết quả: " + point + "/" + total);
    }

    private void loadData(){
        questions = new ArrayList<Question>();
        yourAns = new ArrayList<String>();
        try {
            JSONArray jsonArray = new JSONArray(Utilities.getAnswerData());
            for(int j = 0; j < jsonArray.length(); j++){
                questions.add(new Question(jsonArray.getJSONObject(j).getString("id"),
                        jsonArray.getJSONObject(j).getString("ques"),
                        jsonArray.getJSONObject(j).getString("ans1"),
                        jsonArray.getJSONObject(j).getString("ans2"),
                        jsonArray.getJSONObject(j).getString("ans3"),
                        jsonArray.getJSONObject(j).getString("ans4"),
                        jsonArray.getJSONObject(j).getString("correct_ans"),
                        jsonArray.getJSONObject(j).getString("ques_image"),
                        jsonArray.getJSONObject(j).getString("important"),
                        jsonArray.getJSONObject(j).getString("detail")));
                yourAns.add(jsonArray.getJSONObject(j).getString("your_answer"));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
