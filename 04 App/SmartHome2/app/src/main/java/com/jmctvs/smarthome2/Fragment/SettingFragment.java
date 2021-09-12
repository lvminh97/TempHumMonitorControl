package com.jmctvs.smarthome2.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jmctvs.smarthome2.R;
import com.jmctvs.smarthome2.Utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static android.content.Context.MODE_PRIVATE;

public class SettingFragment extends Fragment implements View.OnClickListener{

    private View view;
    private EditText serverEd, portEd;
    private Button saveBtn;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        initUI();
        return view;
    }

    private void initUI(){
        serverEd = view.findViewById(R.id.ed_server);
        portEd = view.findViewById(R.id.ed_port);
        saveBtn = view.findViewById(R.id.btn_save);
        saveBtn.setOnClickListener(this);

        serverEd.setText(Utils.server);
        portEd.setText(Utils.port + "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_save:
                if(serverEd.getText().length() > 0 && portEd.getText().length() > 0){
                    SharedPreferences pref = getActivity().getSharedPreferences("SEC_AUTH", MODE_PRIVATE);
                    pref.edit().putString("server", serverEd.getText().toString()).commit();
                    pref.edit().putInt("port", Integer.parseInt(portEd.getText().toString())).commit();
                    Utils.server = serverEd.getText().toString();
                    Utils.port = Integer.parseInt(portEd.getText().toString());
                    Toast.makeText(getContext(), "Đã lưu cài đặt", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
