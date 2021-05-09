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

import com.example.madhang_ae.MainActivity;
import com.example.madhang_ae.Pembeli.JajananFragment;
import com.example.madhang_ae.Pembeli.LaukFragment;
import com.example.madhang_ae.Pembeli.MakananFragment;
import com.example.madhang_ae.Pembeli.MinumanFragment;
import com.example.madhang_ae.R;
import com.example.madhang_ae.otp;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class NavigationPenjual extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener  {
    private BottomNavigationView bottomNavigationViewPenjual;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.ColorButton));
        setContentView(R.layout.activity_navigation_penjual);
        loadFragment(new HomeFragment());
        bottomNavigationViewPenjual = findViewById(R.id.navigationPenjual);
        bottomNavigationViewPenjual.setOnNavigationItemSelectedListener(this);
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