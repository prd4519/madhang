package com.android.madhang_ae.Penjual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.android.madhang_ae.API.BaseApiService;
import com.android.madhang_ae.API.UtilsApi;
import com.android.madhang_ae.EditProfile;
import com.android.madhang_ae.Pembeli.NavigationPembeli;
import com.android.madhang_ae.R;
import com.android.madhang_ae.SessionManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
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

public class NavigationPenjual extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener  {
    private BottomNavigationView bottomNavigationViewPenjual;
    private SessionManager sessionManager;
    private String name,email,avatar,id;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    CircleImageView fabPopPenjual;
    AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.ColorButton));
        setContentView(R.layout.activity_navigation_penjual);
        loadFragment(new HomeFragment());
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        id = user.get(SessionManager.kunci_id);
        bottomNavigationViewPenjual = findViewById(R.id.navigationPenjual);
        bottomNavigationViewPenjual.setOnNavigationItemSelectedListener(this);
        fabPopPenjual = findViewById(R.id.popupPenjual);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        showAva();
        fabPopPenjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUpp1();
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
                            Glide.with(NavigationPenjual.this.getApplicationContext())
                                    .load(UtilsApi.IMAGE_URL+avatar)
                                    .centerCrop()
                                    .dontAnimate()
                                    .placeholder(R.drawable.ic_person2)
                                    .into(fabPopPenjual);

                        } else {
                            String error_msg = jsonResult.getString("error_msg");
                            Toast.makeText(NavigationPenjual.this, error_msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(NavigationPenjual.this, "Cannot get image profil", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void showPopUpp1(){
        dialogBuilder = new AlertDialog.Builder(NavigationPenjual.this);
        View layoutView = getLayoutInflater().inflate(R.layout.popup_penjual, null);
        Button edtProfile = layoutView.findViewById(R.id.edtProfileJual);
        Button dashboardpembeli = layoutView.findViewById(R.id.dshbrPembeli);
        Button logout = layoutView.findViewById(R.id.LogoutJual);
        CircleImageView imageAkun = layoutView.findViewById(R.id.profile_imageJual);
        Glide.with(this.getApplicationContext())
                .load(UtilsApi.IMAGE_URL+avatar)
                .apply(new RequestOptions().centerInside())
                .placeholder(R.drawable.ic_person2)
                .into(imageAkun);
        TextView namaUser = layoutView.findViewById(R.id.txt_namaJual);
        namaUser.setText(name);
        TextView emailUser = layoutView.findViewById(R.id.txt_emailJual);
        emailUser.setText(email);
        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        edtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigationPenjual.this, EditProfile.class);
                startActivity(intent);
                alertDialog.dismiss();
            }
        });
        dashboardpembeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigationPenjual.this, NavigationPembeli.class);
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
                    .replace(R.id.containerPenjual, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.navigationMenu:
                fragment= new MenuFragment();
                break;
            case R.id.navigationHome:
                fragment= new HomeFragment();
                break;
        }
        return loadFragment(fragment);
    }
    @Override
    public void onBackPressed() {
        dialogBuilder = new AlertDialog.Builder(NavigationPenjual.this);
        View layoutView = getLayoutInflater().inflate(R.layout.layout_exit_aplication, null);
        Button dialogButtonExit = layoutView.findViewById(R.id.btnDialogExit);
        Button dialogButtonBatal = layoutView.findViewById(R.id.btnDialogBatal);
        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
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