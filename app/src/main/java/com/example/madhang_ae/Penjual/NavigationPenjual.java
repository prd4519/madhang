package com.example.madhang_ae.Penjual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.madhang_ae.MainActivity;
import com.example.madhang_ae.R;

public class NavigationPenjual extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.ColorButton));
        setContentView(R.layout.activity_navigation_penjual);
    }
}