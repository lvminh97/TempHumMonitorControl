package com.jmctvs.smarthome2.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jmctvs.smarthome2.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class SettingFragment extends Fragment implements View.OnClickListener{

    private View view;
    private Button a1Btn, a2Btn, b1Btn;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        initUI();
        return view;
    }

    private void initUI(){


        a1Btn.setOnClickListener(this);
        a2Btn.setOnClickListener(this);
        b1Btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }
}
