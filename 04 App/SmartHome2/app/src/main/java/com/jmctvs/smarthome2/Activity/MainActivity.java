package com.jmctvs.smarthome2.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.jmctvs.smarthome2.Fragment.NhanDienBienBaoFragment;
import com.jmctvs.smarthome2.Fragment.HomeFragment;
import com.jmctvs.smarthome2.Fragment.LichSuBienBaoFragment;
import com.jmctvs.smarthome2.Fragment.LichSuThiFragment;
import com.jmctvs.smarthome2.Fragment.OnTapPackSelectFragment;
import com.jmctvs.smarthome2.Fragment.OnTapQuesSelectFragment;
import com.jmctvs.smarthome2.Fragment.QuanLyTaiKhoanFragment;
import com.jmctvs.smarthome2.Fragment.ThiThuPackSelectFragment;
import com.jmctvs.smarthome2.Fragment.ThiThuQuesSelectFragment;
import com.jmctvs.smarthome2.R;
import com.jmctvs.smarthome2.Utilities.Utilities;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(Color.rgb(0, 0, 0));
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        loadHome();

        View hView = navigationView.getHeaderView(0);
        TextView fullnameTv = hView.findViewById(R.id.tv_nav_fullname);
        fullnameTv.setText(Utilities.getAccountObj().getFullname());
        Log.d("SVMC2", Utilities.getAccountObj().getFullname());
        TextView emailTv = hView.findViewById(R.id.tv_nav_email);
        emailTv.setText(Utilities.getAccountObj().getEmail());

        getSupportActionBar().setTitle("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentTransaction transaction;
        switch (item.getItemId()){
            case R.id.nav_home:
                loadHome();
                break;
            case R.id.nav_ontap:
                loadOnTapPackSelect();
                break;
            case R.id.nav_thithu:
                loadThiThuPackSelect();
                break;
            case R.id.nav_nhandienbienbao:
                loadNhanDienBienBao();
                break;
            case R.id.nav_quanlytaikhoan:
                loadQuanLyTaiKhoan();
                break;
            case R.id.nav_dangxuat:
                Utilities.setAccountObj(null);
                Intent intent = new Intent(getApplication(), LoginActivity.class);
                startActivity(intent);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadHome(){
        Utilities.setCurrentScreen("HomeScreen");
        Fragment fragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_framelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadOnTapPackSelect(){
        Utilities.setCurrentScreen("OnTapPackSelect");
        Fragment fragment = new OnTapPackSelectFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_framelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void loadThiThuPackSelect(){
        Utilities.setCurrentScreen("ThiThuPackSelect");
        Fragment fragment = new ThiThuPackSelectFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_framelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void loadNhanDienBienBao() {
        Utilities.setCurrentScreen("NhanDienBienBao");
        Fragment fragment = new NhanDienBienBaoFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_framelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void loadQuanLyTaiKhoan(){
        Utilities.setCurrentScreen("QuanLyTaiKhoan");
        Fragment fragment = new QuanLyTaiKhoanFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_framelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed(){
        String currentScreen = Utilities.getCurrentScreen();
        if(currentScreen.equals("HomeScreen")){
            return;
        }
        Fragment fragment = null;
        if(currentScreen.equals("OnTapPackSelect")
                || currentScreen.equals("ThiThuPackSelect")
                || currentScreen.equals("NhanDienBienBao")
                || currentScreen.equals("QuanLyTaiKhoan")) {
            Utilities.setCurrentScreen("HomeScreen");
            fragment = new HomeFragment();
        }
        else if(currentScreen.equals("OnTapQuesSelect")){
            Utilities.setCurrentScreen("OnTapPackSelect");
            fragment = new OnTapPackSelectFragment();
        }
        else if(currentScreen.equals("OnTapQues") || currentScreen.equals("OnTapResult")){
            Utilities.setCurrentScreen("OnTapQuesSelect");
            fragment = new OnTapQuesSelectFragment();
        }
        else if(currentScreen.equals("ThiThuQuesSelect")){
            Utilities.setCurrentScreen("ThiThuPackSelect");
            fragment = new ThiThuPackSelectFragment();
        }
        else if(currentScreen.equals("ThiThuQues") || currentScreen.equals("ThiThuResult")){
            Utilities.setCurrentScreen("ThiThuQuesSelect");
            fragment = new ThiThuQuesSelectFragment();
        }
        else if(currentScreen.equals("ThongTinCaNhan") || currentScreen.equals("LichSuThi") || currentScreen.equals("DoiMatKhau") || currentScreen.equals("LichSuBienBao")){
            Utilities.setCurrentScreen("QuanLyTaiKhoan");
            fragment = new QuanLyTaiKhoanFragment();
        }
        else if(currentScreen.equals("ChiTietLichSuThi")){
            Utilities.setCurrentScreen("LichSuThi");
            fragment = new LichSuThiFragment();
        }
        else if(currentScreen.equals("ChiTietLichSuBienBao")){
            Utilities.setCurrentScreen("LichSuBienBao");
            fragment = new LichSuBienBaoFragment();
        }
        if(fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_framelayout, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}