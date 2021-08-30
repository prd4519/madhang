package com.android.madhang_ae;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Random;

public class Daftar extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner kecamatanDaftar;
    private EditText etEmail,etPassword, etnama, etnoHp;
    private String email,password,nama,kecamatan,noHp;
    private int code,id;
    long idKecamatan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.ColorButton));
        setContentView(R.layout.activity_daftar);
        kecamatanDaftar = findViewById(R.id.sp_kecamatanDaftar);
        etEmail = findViewById(R.id.et_emailDaftar);
        etPassword = findViewById(R.id.et_passwordDaftar);
        etnama = findViewById(R.id.et_namaDaftar);
        etnoHp = findViewById(R.id.et_noHPDaftar);

        FloatingActionButton back = (FloatingActionButton) findViewById(R.id.btn_kembaliDaftar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Daftar.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.listDaftarKecamatan,R.layout.custom_spinner);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        kecamatanDaftar.setAdapter(adapter);
        kecamatanDaftar.setOnItemSelectedListener(this);
        Button Daftar = (Button) findViewById(R.id.btn_verifikasi);

        Daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = (int) idKecamatan;
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                noHp = etnoHp.getText().toString();
                nama = etnama.getText().toString();
                if (email.equals("")){
                    etEmail.setError("Mohon isi data");
                }else if (password.equals("")){
                    etPassword.setError("Mohon isi data");
                }else if (noHp.equals("")){
                    etnoHp.setError("Mohon isi data");
                }else if (nama.equals("")){
                    etnama.setError("Mohon isi data");
                }else if(idKecamatan == 0){
                    Toast.makeText(Daftar.this, "Mohon Pilih Kecamatan", Toast.LENGTH_SHORT).show();
                }else {
                    verifikasiOTP();
                }
            }
        });
    }

    private void verifikasiOTP() {
        int id = (int) idKecamatan;
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        noHp = etnoHp.getText().toString();
        nama = etnama.getText().toString();
        if (email.equals("")|password.equals("")|noHp.equals("")|nama.equals("")){
            Toast.makeText(this, "Harap Masukkan Data dengan benar", Toast.LENGTH_SHORT).show();
        }else {
            Random codeOtp = new Random();
            code = codeOtp.nextInt(8999) + 1000;
            Intent i = new Intent(Daftar.this, otp.class);
                i.putExtra("passwordVerifikasi",password);
                i.putExtra("noHPVerifikasi",noHp);
                i.putExtra("idKecamatanVerifikasi",String.valueOf(id));
                i.putExtra("namaVerifikasi",nama);
                i.putExtra("emailVerifikasi", email);
                i.putExtra("otpVerifikasi", String.valueOf(code));
            startActivity(i);
            /*BaseApiService mApiService = UtilsApi.getApiService();
            Call<ResponseBody> insert = mApiService.insertAkun(email, password, noHp, id, nama, code);
            insert.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Intent i = new Intent(Daftar.this, otp.class);
                        i.putExtra("emailVerifikasi", email);
                        i.putExtra("otpVerifikasi", String.valueOf(code));
                        startActivity(i);

                    } else {
                        Toast.makeText(Daftar.this, "Email Already Exist", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("RETRO", "ON FAILURE : " + t.getMessage());

                }
            });*/
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        kecamatan = parent.getItemAtPosition(position).toString();
        idKecamatan = parent.getItemIdAtPosition(position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}