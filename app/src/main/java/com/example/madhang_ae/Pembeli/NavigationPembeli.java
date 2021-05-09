package com.example.madhang_ae.Pembeli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.madhang_ae.MainActivity;
import com.example.madhang_ae.Penjual.NavigationPenjual;
import com.example.madhang_ae.R;
import com.example.madhang_ae.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

public class NavigationPembeli extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationViewPembeli;
    private SessionManager sessionManager;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.ColorButton));
        setContentView(R.layout.activity_navigation_pembeli);
        loadFragment(new MakananFragment());
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        String name = user.get(SessionManager.kunci_mail);
        bottomNavigationViewPembeli = findViewById(R.id.navigationPembeli);
        bottomNavigationViewPembeli.setOnNavigationItemSelectedListener(this);

    }
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerPembeli, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.navigationMakan:
                fragment= new MakananFragment();
                break;
            case R.id.navigationMinum:
                fragment= new MinumanFragment();
                break;
            case R.id.navigationLauk:
                fragment= new LaukFragment();
                break;
            case R.id.navigationJajan:
                fragment= new JajananFragment();
                break;
        }
        return loadFragment(fragment);
    }
    @Override
    public void onBackPressed() {
        dialogBuilder = new AlertDialog.Builder(NavigationPembeli.this);
        View layoutView = getLayoutInflater().inflate(R.layout.layout_exit_aplication, null);
        Button dialogButtonExit = layoutView.findViewById(R.id.btnDialogExit);
        Button dialogButtonBatal = layoutView.findViewById(R.id.btnDialogBatal);

        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
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