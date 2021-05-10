package com.example.madhang_ae.Penjual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.madhang_ae.EditProfile;
import com.example.madhang_ae.MainActivity;
import com.example.madhang_ae.Pembeli.JajananFragment;
import com.example.madhang_ae.Pembeli.LaukFragment;
import com.example.madhang_ae.Pembeli.MakananFragment;
import com.example.madhang_ae.Pembeli.MinumanFragment;
import com.example.madhang_ae.Pembeli.NavigationPembeli;
import com.example.madhang_ae.R;
import com.example.madhang_ae.SessionManager;
import com.example.madhang_ae.otp;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class NavigationPenjual extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener  {
    private BottomNavigationView bottomNavigationViewPenjual;
    private SessionManager sessionManager;
    private String name,id,email,idKecamatan,noHp,avatar,password;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    CircleImageView fabPopPenjual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.ColorButton));
        setContentView(R.layout.activity_navigation_penjual);
        loadFragment(new HomeFragment());
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        name = user.get(SessionManager.kunci_name);
        email = user.get(SessionManager.kunci_mail);
        avatar = user.get(SessionManager.kunci_ava);
        bottomNavigationViewPenjual = findViewById(R.id.navigationPenjual);
        bottomNavigationViewPenjual.setOnNavigationItemSelectedListener(this);
        fabPopPenjual = findViewById(R.id.popupPenjual);
        fabPopPenjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUpp1();
            }
        });
    }
    private void showPopUpp1(){
        dialogBuilder = new AlertDialog.Builder(NavigationPenjual.this);
        View layoutView = getLayoutInflater().inflate(R.layout.popup_penjual, null);
        Button edtProfile = layoutView.findViewById(R.id.edtProfileJual);
        Button dashboardpembeli = layoutView.findViewById(R.id.dshbrPembeli);
        Button logout = layoutView.findViewById(R.id.LogoutJual);
        CircleImageView imageAkun = layoutView.findViewById(R.id.profile_imageJual);
        TextView namaUser = layoutView.findViewById(R.id.txt_namaJual);
        namaUser.setText(name);
        TextView emailUser = layoutView.findViewById(R.id.txt_emailJual);
        emailUser.setText(email);
        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        edtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigationPenjual.this, EditProfile.class);
                startActivity(intent);
                alertDialog.dismiss();
            }
        });
        dashboardpembeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigationPenjual.this, NavigationPembeli.class);
                startActivity(intent);
                finish();
                alertDialog.dismiss();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
                finish();
            }
        });
    }
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerPenjual, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.navigationMenu:
                fragment= new MenuFragment();
                break;
            case R.id.navigationHome:
                fragment= new HomeFragment();
                break;
        }
        return loadFragment(fragment);
    }
    @Override
    public void onBackPressed() {
        dialogBuilder = new AlertDialog.Builder(NavigationPenjual.this);
        View layoutView = getLayoutInflater().inflate(R.layout.layout_exit_aplication, null);
        Button dialogButtonExit = layoutView.findViewById(R.id.btnDialogExit);
        Button dialogButtonBatal = layoutView.findViewById(R.id.btnDialogBatal);
        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        dialogButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                alertDialog.dismiss();
            }
        });
        dialogButtonBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }
}