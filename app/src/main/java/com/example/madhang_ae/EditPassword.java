package com.example.madhang_ae;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madhang_ae.API.BaseApiService;
import com.example.madhang_ae.API.UtilsApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPassword extends AppCompatActivity {
    private EditText etPassLama,etPassBaru;
    SessionManager sessionManager;
    String pass,passLama,passBaru,id_user;
    Button btnSimpan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        etPassBaru = findViewById(R.id.et_newPassword);
        etPassLama = findViewById(R.id.et_oldPassword);
        pass = user.get(SessionManager.kunci_pass);
        id_user = user.get(SessionManager.kunci_id);
        FloatingActionButton back = (FloatingActionButton) findViewById(R.id.btn_kembaliPassword);
        btnSimpan = findViewById(R.id.btn_SimpanPassword);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void updatePassword() {
        passBaru = etPassBaru.getText().toString();
        passLama = etPassLama.getText().toString();
        if (passBaru.equals("")|passLama.equals("")){
            Toast.makeText(this, "Mohon lengkapi isian", Toast.LENGTH_SHORT).show();
        }else if (passLama.equals(pass)){
            BaseApiService mApiService = UtilsApi.getApiService();
            Call<ResponseBody> updatePass = mApiService.updatePassword(id_user,passBaru);
            updatePass.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(EditPassword.this, "Password berhasil di Update, Silahkan Login kembali dengan password baru", Toast.LENGTH_SHORT).show();
                    sessionManager.logout();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(EditPassword.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }else {
            Toast.makeText(this, "Password Lama tidak sesuai", Toast.LENGTH_SHORT).show();
        }
    }
}