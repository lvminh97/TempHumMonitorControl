package com.jmctvs.smarthome2.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jmctvs.smarthome2.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ControlFragment extends Fragment implements View.OnClickListener{

    private View view;
    private Button fanBtn, mistBtn, servoBtn;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_control, container, false);
        initUI();
        return view;
    }

    private void initUI(){
        fanBtn.setOnClickListener(this);
        mistBtn.setOnClickListener(this);
        servoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_fan:

                break;
            case R.id.btn_mist:

                break;
            case R.id.btn_servo:

                break;
        }

    }
}
