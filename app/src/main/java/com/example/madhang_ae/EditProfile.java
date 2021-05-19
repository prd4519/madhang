package com.example.madhang_ae;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.madhang_ae.API.BaseApiService;
import com.example.madhang_ae.API.UtilsApi;
import com.example.madhang_ae.Pembeli.NavigationPembeli;
import com.example.madhang_ae.Penjual.NavigationPenjual;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {
    SessionManager sessionManager ;
    private CircleImageView imageProfil;
    private EditText etEmail,etNama,etNoHp;
    private String id_user,id_kecamatan,nama,email,password,no_hp,ava,otp,mediaPath,postPath;
    private Button editProfil,editpassword;
    private static final int REQUEST_PICK_PHOTO = 2;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            inputItem();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.ColorButton));
        setContentView(R.layout.activity_edit_profile);

        FloatingActionButton back = (FloatingActionButton) findViewById(R.id.btn_kembaliEdit);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sessionManager = new SessionManager(this);
        imageProfil = findViewById(R.id.img_EditProfile);
        etEmail = findViewById(R.id.et_emailEdit);
        etNama = findViewById(R.id.et_namaEdit);
        etNoHp = findViewById(R.id.et_noHPEdit);
        editProfil = findViewById(R.id.btn_Edit);
        HashMap<String, String> user = sessionManager.getUserDetails();
        id_user = user.get(SessionManager.kunci_id);
        id_kecamatan = user.get(SessionManager.kunci_idKec);
        otp = user.get(SessionManager.kunci_otp);
        password = user.get(SessionManager.kunci_pass);


        getProfil();
        imageProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galery, REQUEST_PICK_PHOTO );
            }
        });
        editProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });
        editpassword = findViewById(R.id.btn_EditPassword);
        editpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditProfile.this,EditPassword.class);
                startActivity(i);

            }
        });
    }

    private void getProfil() {
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<ResponseBody> ava = mApiService.getAva(Integer.parseInt(id_user));
        ava.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonResult = new JSONObject(response.body().string());
                        if (jsonResult.getString("error").equals("false")) {
                            String avatar = jsonResult.getJSONObject("profil").getString("avatar");
                            String name = jsonResult.getJSONObject("profil").getString("nama");
                            String mail = jsonResult.getJSONObject("profil").getString("email");
                            String noHp = jsonResult.getJSONObject("profil").getString("no_hp");
                            Glide.with(EditProfile.this.getApplicationContext())
                                    .load(UtilsApi.IMAGE_URL + avatar)
                                    .apply(new RequestOptions().centerInside())
                                    .placeholder(R.drawable.ic_person)
                                    .into(imageProfil);
                            etEmail.setText(mail);
                            etNama.setText(name);
                            etNoHp.setText(noHp);

                        } else {
                            String error_msg = jsonResult.getString("error_msg");
                            Toast.makeText(EditProfile.this, error_msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(EditProfile.this, "Cannot get image profil", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void inputItem() {
        nama = etNama.getText().toString();
        email = etEmail.getText().toString();
        no_hp = etNoHp.getText().toString();
        if (nama.equals("")|email.equals("")|no_hp.equals("")|mediaPath == null){
            Toast.makeText(this, "Data diatas tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }else{
            File imagefile = new File(mediaPath);
            RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
            MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", imagefile.getName(), reqBody);
            BaseApiService mApiService = UtilsApi.getApiService();
            Call<ResponseBody> update = mApiService.updateAkun(partImage
                    ,RequestBody.create(MediaType.parse("text/plain"), nama)
                    ,RequestBody.create(MediaType.parse("text/plain"), password)
                    ,RequestBody.create(MediaType.parse("text/plain"), id_kecamatan)
                    ,RequestBody.create(MediaType.parse("text/plain"), email)
                    ,RequestBody.create(MediaType.parse("text/plain"), otp)
                    ,RequestBody.create(MediaType.parse("text/plain"), no_hp)
                    ,RequestBody.create(MediaType.parse("text/plain"), id_user));
            update.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(EditProfile.this, "Berhasil Update Data", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(EditProfile.this,NavigationPembeli.class);
                    startActivity(i);
                    finish();

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("debug","OnFailure : Error -> "+t.getMessage());
                    Toast.makeText(EditProfile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        }else {
            inputItem();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_PICK_PHOTO){
                if (data != null){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,null,null,null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex= cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    imageProfil.setImageURI(data.getData());
                    cursor.close();

                    postPath = mediaPath;
                }
            }
        }
    }
}