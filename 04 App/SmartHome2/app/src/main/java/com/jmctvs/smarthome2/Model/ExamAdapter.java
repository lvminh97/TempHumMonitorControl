package com.jmctvs.smarthome2.Model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jmctvs.thibanglaixe.R;

import java.util.ArrayList;

public class ExamAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<String> exam;

    public ExamAdapter(Activity context, ArrayList<String> exam) {
        this.context = context;
        this.exam = exam;
    }

    @Override
    public int getCount() {
        return exam.size();
    }

    @Override
    public Object getItem(int i) {
        return exam.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = context.getLayoutInflater();
        View gridView = inflater.inflate(R.layout.exam, null, true);

        TextView examNum = gridView.findViewById(R.id.tv_exam_num);
        examNum.setText("Đề " + exam.get(i));

        return gridView;
    }

}
