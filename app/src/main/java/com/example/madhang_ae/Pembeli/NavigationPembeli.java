package com.example.madhang_ae.Pembeli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.madhang_ae.API.BaseApiService;
import com.example.madhang_ae.API.UtilsApi;
import com.example.madhang_ae.EditProfile;
import com.example.madhang_ae.MainActivity;
import com.example.madhang_ae.Penjual.NavigationPenjual;
import com.example.madhang_ae.R;
import com.example.madhang_ae.ServicePenjual;
import com.example.madhang_ae.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationPembeli extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationViewPembeli;
    private SessionManager sessionManager;
    private String name,id,email,idKecamatan,noHp,avatar,password;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    CircleImageView fabPopPembeli;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.ColorButton));
        setContentView(R.layout.activity_navigation_pembeli);
        loadFragment(new MakananFragment());
        sessionManager = new SessionManager(getApplicationContext());
        fabPopPembeli = findViewById(R.id.popupPembeli);
        HashMap<String, String> user = sessionManager.getUserDetails();
        id = user.get(SessionManager.kunci_id);
        password = user.get(SessionManager.kunci_pass);
        String otp = user.get(SessionManager.kunci_otp);
        bottomNavigationViewPembeli = findViewById(R.id.navigationPembeli);
        bottomNavigationViewPembeli.setOnNavigationItemSelectedListener(this);

       showAva();
        fabPopPembeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp1();
            }
        });
    }

    private void showAva() {
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<ResponseBody> ava = mApiService.getAva(Integer.parseInt(id));
        ava.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonResult = new JSONObject(response.body().string());
                        if (jsonResult.getString("error").equals("false")) {
                            avatar = jsonResult.getJSONObject("profil").getString("avatar");
                            name = jsonResult.getJSONObject("profil").getString("nama");
                            email = jsonResult.getJSONObject("profil").getString("email");

                            Glide.with(NavigationPembeli.this.getApplicationContext())
                                    .load(UtilsApi.IMAGE_URL+avatar)
                                    .centerCrop()
                                    .dontAnimate()
                                    .placeholder(R.drawable.ic_person2)
                                    .into(fabPopPembeli);

                        } else {
                            String error_msg = jsonResult.getString("error_msg");
                            Toast.makeText(NavigationPembeli.this, error_msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(NavigationPembeli.this, "Cannot get image profil", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void showPopUp1(){
        dialogBuilder = new AlertDialog.Builder(NavigationPembeli.this);
        View layoutView = getLayoutInflater().inflate(R.layout.popup_pembeli, null);
        Button edtProfile = layoutView.findViewById(R.id.edtProfile);
        Button dashboardpnjl = layoutView.findViewById(R.id.dshbrPenjual);
        Button logout = layoutView.findViewById(R.id.Logout);
        CircleImageView imageAkun = layoutView.findViewById(R.id.profile_image);
            Glide.with(this.getApplicationContext())
                    .load(UtilsApi.IMAGE_URL+avatar)
                    .apply(new RequestOptions().centerInside())
                    .placeholder(R.drawable.ic_person2)
                    .into(imageAkun);
        TextView namaUser = layoutView.findViewById(R.id.txt_nama);
        namaUser.setText(name);
        TextView emailUser = layoutView.findViewById(R.id.txt_email);
        emailUser.setText(email);
        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        edtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigationPembeli.this, EditProfile.class);
                startActivity(intent);
                alertDialog.dismiss();
            }
        });
        dashboardpnjl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigationPembeli.this, NavigationPenjual.class);
                startActivity(intent);
                finish();
                alertDialog.dismiss();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
                finish();
            }
        });
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
                finishAffinity();
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