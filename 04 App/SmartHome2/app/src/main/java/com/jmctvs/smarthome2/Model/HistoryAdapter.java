package com.jmctvs.smarthome2.Model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jmctvs.thibanglaixe.R;

import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<History> {
    private Activity context;

    private ArrayList<History> histories;

    public HistoryAdapter(Activity context, ArrayList<History> histories) {
        super(context, R.layout.history, histories);
        this.context = context;
        this.histories = histories;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.history, null, true);

        TextView timeTv = rowView.findViewById(R.id.time);
        TextView titleTv = rowView.findViewById(R.id.title);
        TextView trueNumTv = rowView.findViewById(R.id.true_num);
        TextView falseNumTv = rowView.findViewById(R.id.false_num);

        timeTv.setText("Thời gian: " + histories.get(position).getTime());
        titleTv.setText("Đề " + histories.get(position).getExam() + " - Gói " + histories.get(position).getPack());
        trueNumTv.setText("Đúng: " + histories.get(position).getCorrect());
        falseNumTv.setText("Sai: " + histories.get(position).getWrong());

        return rowView;
    }
}
