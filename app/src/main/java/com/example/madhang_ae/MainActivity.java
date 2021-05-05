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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.ColorButton));
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.btn_Login);
        etEmail = findViewById(R.id.et_emailLogin);
        etPassword = findViewById(R.id.et_passwordLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginRequest();
                finish();
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
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<ResponseBody> login = mApiService.loginRequest(etEmail.getText().toString(),etPassword.getText().toString());
        login.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonResult = new JSONObject(response.body().string());
                        if (jsonResult.getString("error").equals("false")) {
                            String name = jsonResult.getJSONObject("login").getString("nama");
                            String id = jsonResult.getJSONObject("login").getString("id");
                            String email = jsonResult.getJSONObject("login").getString("email");
                            String idKecamatan = jsonResult.getJSONObject("login").getString("id_kecamatan");
                            String noHp = jsonResult.getJSONObject("login").getString("no_hp");
                            String avatar = jsonResult.getJSONObject("login").getString("avatar");

                            Intent intent = new Intent(getApplicationContext(), NavigationPembeli.class);
                            intent.putExtra("namaAkun", name);
                            intent.putExtra("idAkun", id);
                            intent.putExtra("emailAkun", email);
                            intent.putExtra("id_kecamatanAkun", idKecamatan);
                            intent.putExtra("noHpAkun", noHp);
                            intent.putExtra("avatarAkun", avatar);
                            startActivity(intent);
                            finish();
                            sessionManager = new SessionManager(getApplicationContext());
                            sessionManager.createSession(email);

                        } else {
                            String error_msg = jsonResult.getString("error_msg");
                            Toast.makeText(MainActivity.this, error_msg, Toast.LENGTH_SHORT).show();
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


}