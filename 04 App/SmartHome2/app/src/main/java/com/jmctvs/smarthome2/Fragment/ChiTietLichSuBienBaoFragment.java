package com.jmctvs.smarthome2.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmctvs.smarthome2.Utilities.Utilities;
import com.jmctvs.smarthome2.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ChiTietLichSuBienBaoFragment extends Fragment {
    private Context context;
    private View view;
    private ImageView signImg;
    private TextView signNameTv;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        context = container.getContext();
        view = inflater.inflate(R.layout.fragment_chitietlichsubienbao, container, false);
        initUI();
        return view;
    }

    private void initUI() {
        signImg = view.findViewById(R.id.img_sign);
        signNameTv = view.findViewById(R.id.tv_sign_name);

        Bitmap bp = Utilities.getSign().getSign();
        signImg.setImageBitmap(bp);
        signNameTv.setText("Biển báo: " + Utilities.getSign().getTitle());
    }
}
