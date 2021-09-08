package com.jmctvs.smarthome2.Model;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jmctvs.thibanglaixe.R;

import java.util.ArrayList;

public class ResultAdapter extends ArrayAdapter<Question> {

    private final Activity context;

    private ArrayList<Question> questions;
    private ArrayList<String> yourAns;

    public ResultAdapter(Activity context, ArrayList<Question> ques, ArrayList<String> yourAns) {
        super(context, R.layout.ques, ques);
        this.context = context;
        this.questions = ques;
        this.yourAns = yourAns;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.result, null, true);

        TextView quesIdTv = rowView.findViewById(R.id.tv_ques_id);
        TextView quesNumTv = rowView.findViewById(R.id.tv_ques_num);
        ImageView img = rowView.findViewById(R.id.img_ques);
        TextView quesContentTv = rowView.findViewById(R.id.tv_ques_content);
        RadioButton ans1Radio = rowView.findViewById(R.id.radio_ans1);
        RadioButton ans2Radio = rowView.findViewById(R.id.radio_ans2);
        RadioButton ans3Radio = rowView.findViewById(R.id.radio_ans3);
        RadioButton ans4Radio = rowView.findViewById(R.id.radio_ans4);
        TextView detailAnswerTv = rowView.findViewById(R.id.tv_detail_answer);

        if(questions.get(position).getImportant().equals("1")) quesNumTv.setText("Câu " + (position + 1) + " (Điểm liệt)");
        else quesNumTv.setText("Câu " + (position + 1));
        quesContentTv.setText(questions.get(position).getQues());
        if(questions.get(position).getQues_img() != null) img.setImageBitmap(questions.get(position).getQues_img());

        ans1Radio.setText(questions.get(position).getAns1());
        if(yourAns.get(position).equals("1")){
            ans1Radio.setChecked(true);
            ans1Radio.setTextColor(Color.rgb(255, 0, 0));
        }
        if(questions.get(position).getCorrect_ans().equals("1")){
            ans1Radio.setTextColor(Color.rgb(0, 160, 50));
        }
        //
        ans2Radio.setText(questions.get(position).getAns2());
        if(yourAns.get(position).equals("2")){
            ans2Radio.setChecked(true);
            ans2Radio.setTextColor(Color.rgb(255, 0, 0));
        }
        if(questions.get(position).getCorrect_ans().equals("2")){
            ans2Radio.setTextColor(Color.rgb(0, 160, 50));
        }
        //
        if(!questions.get(position).getAns3().equals("")) {
            ans3Radio.setText(questions.get(position).getAns3());
            if(yourAns.get(position).equals("3")){
                ans3Radio.setChecked(true);
                ans3Radio.setTextColor(Color.rgb(255, 0, 0));
            }
            if(questions.get(position).getCorrect_ans().equals("3")){
                ans3Radio.setTextColor(Color.rgb(0, 160, 50));
            }
        }
        else ans3Radio.setVisibility(View.INVISIBLE);
        //
        if(!questions.get(position).getAns4().equals("")) {
            ans4Radio.setText(questions.get(position).getAns4());
            if(yourAns.get(position).equals("4")){
                ans4Radio.setChecked(true);
                ans4Radio.setTextColor(Color.rgb(255, 0, 0));
            }
            if(questions.get(position).getCorrect_ans().equals("4")){
                ans4Radio.setTextColor(Color.rgb(0, 160, 50));
            }
        }
        else ans4Radio.setVisibility(View.INVISIBLE);

        detailAnswerTv.setText("Giải thích: " + questions.get(position).getDetail());

        return rowView;
    }
}
