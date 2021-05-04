package com.example.madhang_ae;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.madhang_ae.API.BaseApiService;
import com.example.madhang_ae.API.UtilsApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Daftar extends AppCompatActivity {
    private Spinner kecamatanDaftar;
    private EditText etEmail,etPassword, etnama, etnoHp;
    private String email,password,nama,noHp;
    private int kecamatan,code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.ColorButton));
        setContentView(R.layout.activity_daftar);
        kecamatanDaftar = findViewById(R.id.kecamatanSpinner);
        etEmail = findViewById(R.id.et_emailDaftar);
        etPassword = findViewById(R.id.et_passwordDaftar);
        etnama = findViewById(R.id.et_namaDaftar);
        etnoHp = findViewById(R.id.et_noHPDaftar);
        switch (kecamatanDaftar.getSelectedItem().toString()){
            case "Balerejo" :
                kecamatan = 1;
                break;
            case "Dagangan" :
                kecamatan = 2;
                break;
            case "Dolopo" :
                kecamatan = 3;
                break;
            case "Geger" :
                kecamatan = 4;
                break;
            case "Gemarang" :
                kecamatan = 5;
                break;
            case "Jiwan" :
                kecamatan = 6;
                break;
            case "Kare" :
                kecamatan = 7;
                break;
            case "Kebonsari" :
                kecamatan = 8;
                break;
            case "Madiun" :
                kecamatan = 9;
                break;
            case "Mejayan" :
                kecamatan = 10;
                break;
            case "Pilangkenceng" :
                kecamatan = 11;
                break;
            case "Saradan" :
                kecamatan = 12;
                break;
            case "Sawahan" :
                kecamatan = 13;
                break;
            case "Wonoasri" :
                kecamatan = 14;
                break;
            case "Wungu" :
                kecamatan = 15;
                break;
        }
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        noHp = etnoHp.getText().toString();
        nama = etnama.getText().toString();
        FloatingActionButton back = (FloatingActionButton) findViewById(R.id.btn_kembaliDaftar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Daftar.this, MainActivity.class);
                startActivity(i);
            }
        });

        Button Daftar = (Button) findViewById(R.id.btn_verifikasi);
        Daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifikasiOTP();
            }
        });
    }

    private void verifikasiOTP() {
         Random codeOtp = new Random();
         code = codeOtp.nextInt(8999)+1000;
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<ResponseBody> insert = mApiService.insertAkun(email,password,Integer.parseInt(noHp),kecamatan,nama,code,null);
        insert.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Intent i = new Intent(Daftar.this, otp.class);
                i.putExtra("email",email);
                i.putExtra("otp", String.valueOf(code));
                startActivity(i);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());

            }
        });
    }
}