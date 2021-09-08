package com.jmctvs.smarthome2.Fragment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jmctvs.thibanglaixe.R;
import com.jmctvs.smarthome2.Utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class NhanDienBienBaoFragment extends Fragment implements View.OnClickListener{

    final int REQUEST_IMAGE_CAPTURE = 100;

    private Context context;
    private View view;
    private Button captureBtn;
    private ImageView signImg;
    private TextView signNameTv;

    private Uri imageUri;
    private Bitmap bp = null;
    
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        context = container.getContext();
        view = inflater.inflate(R.layout.fragment_nhandienbienbao, container, false);
        initUI();
        ping();
        return view;
    }

    private void initUI() {
        signImg = view.findViewById(R.id.img_sign);
        captureBtn = view.findViewById(R.id.btn_capture);
        captureBtn.setOnClickListener(this);
        signNameTv = view.findViewById(R.id.tv_sign_name);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_capture){
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "Sign picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera");
            imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE){
            if(resultCode == Activity.RESULT_OK){
                bp = null;
                try {
                    bp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                    Matrix matrix = new Matrix();
                    if(bp.getWidth() > bp.getHeight()) {
                        matrix.postRotate(90);
                    }
                    bp = Bitmap.createBitmap(bp, 0, 0, bp.getWidth(), bp.getHeight(), matrix, true);
                    bp = bp.copy(Bitmap.Config.ARGB_8888, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                signImg.setImageBitmap(bp);
                signNameTv.setText("Đang xử lý...");
                sendRequest(bp);
            }
        }
    }

    private void sendRequest(Bitmap bp){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utilities.AI_HOST + "/vgg16-ssd",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SVMC", response);
                        try {
                            JSONArray jsonArr = new JSONArray(response);
                            if(jsonArr.length() == 0) signNameTv.setText("Không nhận diện được");
                            else{
                                JSONObject json = jsonArr.getJSONObject(0);
                                String name = json.getString("sign");
                                int xmin = json.getInt("xmin");
                                int ymin = json.getInt("ymin");
                                int xmax = json.getInt("xmax");
                                int ymax = json.getInt("ymax");
                                // Draw box
                                Utilities.drawBox(bp, xmin, ymin, xmax, ymax);
                                // Display name
                                signNameTv.setText("Biển báo: " + name);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                                Log.d("SVMC", error.getMessage());
                    }
                })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("uid", Utilities.getAccountObj().getUid());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bp.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                byte[] bytes = baos.toByteArray();
                Log.d("SVMC", "" + bytes.length);
                params.put("img_base64", Base64.encodeToString(bytes, Base64.DEFAULT));
                return params;
            }
        };
        queue.getCache().clear();
        queue.add(stringRequest);
    }

    private void ping(){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utilities.AI_HOST + "/ping",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SVMC", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                                Log.d("SVMC", error.getMessage());
                    }
                });
        queue.add(stringRequest);
    }
}
