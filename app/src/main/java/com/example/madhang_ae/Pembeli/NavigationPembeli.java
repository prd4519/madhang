package com.example.madhang_ae.Pembeli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.madhang_ae.MainActivity;
import com.example.madhang_ae.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class NavigationPembeli extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationViewPembeli;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.ColorButton));
        setContentView(R.layout.activity_navigation_pembeli);
        loadFragment(new MakananFragment());
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

}