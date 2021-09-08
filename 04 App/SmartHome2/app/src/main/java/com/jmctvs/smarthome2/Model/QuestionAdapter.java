package com.jmctvs.smarthome2.Model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jmctvs.smarthome2.Utilities.Utilities;
import com.jmctvs.thibanglaixe.R;

import java.util.ArrayList;

public class QuestionAdapter extends ArrayAdapter<Question>{

    private final Activity context;

    private ArrayList<Question> questions;

    public QuestionAdapter(Activity context, ArrayList<Question> ques) {
        super(context, R.layout.ques, ques);
        this.context = context;
        this.questions = ques;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.ques, null, true);
        TextView quesIdTv = rowView.findViewById(R.id.tv_ques_id);
        TextView quesNumTv = rowView.findViewById(R.id.tv_ques_num);
        ImageView img = rowView.findViewById(R.id.img_ques);
        TextView quesContentTv = rowView.findViewById(R.id.tv_ques_content);
        RadioButton ans1Radio = rowView.findViewById(R.id.radio_ans1);
        RadioButton ans2Radio = rowView.findViewById(R.id.radio_ans2);
        RadioButton ans3Radio = rowView.findViewById(R.id.radio_ans3);
        RadioButton ans4Radio = rowView.findViewById(R.id.radio_ans4);

        if(questions.get(position).getImportant().equals("1")) quesNumTv.setText("Câu " + (position + 1) + " (Điểm liệt)");
        else quesNumTv.setText("Câu " + (position + 1));
        quesContentTv.setText(questions.get(position).getQues());
        if(questions.get(position).getQues_img() != null) img.setImageBitmap(questions.get(position).getQues_img());

        ans1Radio.setText(questions.get(position).getAns1());
        if(Utilities.getAnsData().getOrDefault(questions.get(position).getQuesId(), "").equals("1")) ans1Radio.setChecked(true);
        ans1Radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.getAnsData().put(questions.get(position).getQuesId(), "1");
            }
        });
        //
        ans2Radio.setText(questions.get(position).getAns2());
        if(Utilities.getAnsData().getOrDefault(questions.get(position).getQuesId(), "").equals("2")) ans2Radio.setChecked(true);
        ans2Radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.getAnsData().put(questions.get(position).getQuesId(), "2");
            }
        });
        //
        if(!questions.get(position).getAns3().equals("")) {
            ans3Radio.setText(questions.get(position).getAns3());
            if(Utilities.getAnsData().getOrDefault(questions.get(position).getQuesId(), "").equals("3")) ans3Radio.setChecked(true);
            ans3Radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.getAnsData().put(questions.get(position).getQuesId(), "3");
                }
            });
        }
        else ans3Radio.setVisibility(View.INVISIBLE);
        //
        if(!questions.get(position).getAns4().equals("")) {
            ans4Radio.setText(questions.get(position).getAns4());
            if(Utilities.getAnsData().getOrDefault(questions.get(position).getQuesId(), "").equals("4")) ans4Radio.setChecked(true);
            ans4Radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.getAnsData().put(questions.get(position).getQuesId(), "4");
                }
            });
        }
        else ans4Radio.setVisibility(View.INVISIBLE);

        return rowView;
    }
}
