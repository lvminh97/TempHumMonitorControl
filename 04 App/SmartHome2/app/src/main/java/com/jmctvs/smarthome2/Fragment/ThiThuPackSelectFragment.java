package com.jmctvs.smarthome2.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jmctvs.smarthome2.Utilities.Utilities;
import com.jmctvs.thibanglaixe.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ThiThuPackSelectFragment extends Fragment implements View.OnClickListener{

    private View view;
    private Button a1Btn, a2Btn, b1Btn;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_thithu_pack_select, container, false);
        initUI();
        return view;
    }

    private void initUI(){
        a1Btn = view.findViewById(R.id.btn_thithu_A1);
        a2Btn = view.findViewById(R.id.btn_thithu_A2);
        b1Btn = view.findViewById(R.id.btn_thithu_B1);

        a1Btn.setOnClickListener(this);
        a2Btn.setOnClickListener(this);
        b1Btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_thithu_A1:
                Utilities.setPack("A1");
                break;
            case R.id.btn_thithu_A2:
                Utilities.setPack("A2");
                break;
            case R.id.btn_thithu_B1:
                Utilities.setPack("B1");
                break;
        }
        loadThiThuQuesSelect();
    }

    private void loadThiThuQuesSelect(){
        Utilities.setCurrentScreen("ThiThuQuesSelect");
        Fragment fragment = new ThiThuQuesSelectFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_framelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
