package com.example.madhang_ae;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Html;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Properties;

public class otp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.ColorButton));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

    }

}