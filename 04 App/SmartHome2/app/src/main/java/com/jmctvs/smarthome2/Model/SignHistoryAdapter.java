package com.jmctvs.smarthome2.Model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jmctvs.thibanglaixe.R;

import java.util.ArrayList;

public class SignHistoryAdapter extends ArrayAdapter<Sign> {
    private Activity context;

    private ArrayList<Sign> signHistories;

    public SignHistoryAdapter(Activity context, ArrayList<Sign> signHistories) {
        super(context, R.layout.sign_history, signHistories);
        this.context = context;
        this.signHistories = signHistories;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.sign_history, null, true);

        TextView tv_time = rowView.findViewById(R.id.tv_time);
        TextView tv_title = rowView.findViewById(R.id.tv_sign_title);

        tv_time.setText(signHistories.get(position).getTime());
        tv_title.setText(signHistories.get(position).getTitle());

        return rowView;
    }
}
