package com.jmctvs.smarthome2.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmctvs.smarthome2.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class MonitorFragment extends Fragment {

    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_monitor, container, false);
        return view;
    }
}
