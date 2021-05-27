package com.example.madhang_ae;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madhang_ae.API.BaseApiService;
import com.example.madhang_ae.API.UtilsApi;
import com.example.madhang_ae.Pembeli.NavigationPembeli;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button daftar,login;
    SessionManager sessionManager;
    AlertDialog.Builder dialogBuilder,failBuilder;
    AlertDialog customDialog,failDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.ColorButton));
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.btn_Login);
        etEmail = findViewById(R.id.et_emailLogin);
        etPassword = findViewById(R.id.et_passwordLogin);
        dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        View layoutView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(layoutView);
        customDialog = dialogBuilder.create();
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        failBuilder = new AlertDialog.Builder(MainActivity.this);
        View layoutView1 = getLayoutInflater().inflate(R.layout.custom_dialog_fail, null);
        failBuilder.setView(layoutView1);
        failDialog = failBuilder.create();
        failDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginRequest();
            }
        });
        daftar = (Button) findViewById(R.id.btn_Daftar);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Daftar.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void loginRequest() {
        customDialog.show();
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<ResponseBody> login = mApiService.loginRequest(etEmail.getText().toString(),etPassword.getText().toString());
        login.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonResult = new JSONObject(response.body().string());
                        customDialog.hide();
                        if (jsonResult.getString("error").equals("false")) {
                            String id = jsonResult.getJSONObject("login").getString("id");
                            String idKecamatan = jsonResult.getJSONObject("login").getString("id_kecamatan");
                            String otp = jsonResult.getJSONObject("login").getString("otp");
                            String password = etPassword.getText().toString();
                            Intent intent = new Intent(getApplicationContext(), NavigationPembeli.class);
                            startActivity(intent);
                            finish();
                            sessionManager = new SessionManager(getApplicationContext());
                            sessionManager.createSession(id,password,idKecamatan,otp);

                        } else {
                            String error_msg = jsonResult.getString("error_msg");
                            failDialog.show();
                            Runnable dismissAlert = new Runnable() {
                                @Override
                                public void run() {
                                    if (failDialog != null)
                                        failDialog.dismiss();
                                }
                            }; new Handler().postDelayed(dismissAlert, 3000);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "GAGAL", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug","OnFailure : Error -> "+t.getMessage());
            }
        });
    }

//    private void showAlertDialog(){
//        dialogBuilder = new AlertDialog.Builder(MainActivity.this);
//        View layoutView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
//        dialogBuilder.setView(layoutView);
//        customDialog = dialogBuilder.create();
//        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//    }


}