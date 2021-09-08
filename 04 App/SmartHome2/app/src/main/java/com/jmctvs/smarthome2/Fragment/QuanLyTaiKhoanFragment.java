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

public class QuanLyTaiKhoanFragment extends Fragment implements View.OnClickListener{

    private View view;
    private Button inforBtn, passwordBtn, historyBtn, signHistoryBtn;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_quanlytaikhoan, container, false);
        initUI();
        return view;
    }

    private void initUI(){
        inforBtn = view.findViewById(R.id.btn_thongtincanhan);
        passwordBtn = view.findViewById(R.id.btn_doimatkhau);
        historyBtn = view.findViewById(R.id.btn_lichsuthi);
        signHistoryBtn = view.findViewById(R.id.btn_lichsubienbao);

        inforBtn.setOnClickListener(this);
        passwordBtn.setOnClickListener(this);
        historyBtn.setOnClickListener(this);
        signHistoryBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_thongtincanhan){
            loadThongTinCaNhan();
        }
        else if(view.getId() == R.id.btn_doimatkhau){
            loadDoiMatKhau();
        }
        else if(view.getId() == R.id.btn_lichsuthi){
            loadLichSuThi();
        }
        else if(view.getId() == R.id.btn_lichsubienbao){
            loadLichSuBienBao();
        }
    }

    private void loadLichSuThi() {
        Utilities.setCurrentScreen("LichSuThi");
        Fragment fragment = new LichSuThiFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_framelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadDoiMatKhau() {
        Utilities.setCurrentScreen("DoiMatKhau");
        Fragment fragment = new DoiMatKhauFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_framelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadThongTinCaNhan() {
        Utilities.setCurrentScreen("ThongTinCaNhan");
        Fragment fragment = new ThongTinCaNhanFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_framelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadLichSuBienBao() {
        Utilities.setCurrentScreen("LichSuBienBao");
        Fragment fragment = new LichSuBienBaoFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_framelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
