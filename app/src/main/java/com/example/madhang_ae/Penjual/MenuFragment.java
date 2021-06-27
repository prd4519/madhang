package com.example.madhang_ae.Penjual;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.madhang_ae.API.BaseApiService;
import com.example.madhang_ae.API.UtilsApi;
import com.example.madhang_ae.EditProfile;
import com.example.madhang_ae.MainActivity;
import com.example.madhang_ae.Pembeli.NavigationPembeli;
import com.example.madhang_ae.R;
import com.example.madhang_ae.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class MenuFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    SessionManager sessionManager;
    private BottomSheetBehavior bsMenu;
    private LinearLayout linearLayoutbs;
    RoundedImageView imageItem;
    EditText etNama,etHarga,etDesa;
    long idKategori;
    int idKategories;
    Spinner kategori;
    String namaDagangan,harga,desa,idUser,noHp,idKecamatan,mediaPath,postPath,date,timeEnd;
    Button simpanItem;
    Calendar calendar;
    SimpleDateFormat dateFormat;
    TimePicker picker;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog customDialog;
    private static final int REQUEST_PICK_PHOTO = 2;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            inputItem();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        sessionManager = new SessionManager(getContext());
        kategori = v.findViewById(R.id.sp_kategoriInput);
        calendar = Calendar.getInstance();
        picker = v.findViewById(R.id.tp_item);
        picker.setIs24HourView(true);
        dateFormat = new SimpleDateFormat("HH:mm");
        date = dateFormat.format(calendar.getTime());
        linearLayoutbs = v.findViewById(R.id.bottomSheetMenu);
        bsMenu = BottomSheetBehavior.from(linearLayoutbs);
        bsMenu.setState(BottomSheetBehavior.STATE_EXPANDED);
        HashMap<String, String> user = sessionManager.getUserDetails();
        idUser = user.get(SessionManager.kunci_id);
        idKecamatan = user.get(SessionManager.kunci_idKec);
//        noHp = user.get(SessionManager.kunci_noHp);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.listDaftarKategori,R.layout.custom_spinner3);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        kategori.setAdapter(adapter);
        getnohp();
        etNama = v.findViewById(R.id.et_namaDagangan);
        etHarga = v.findViewById(R.id.et_hargaDagangan);
        etDesa = v.findViewById(R.id.et_namaDesaDagangan);
        kategori.setOnItemSelectedListener(this);
        simpanItem = v.findViewById(R.id.btn_SimpanItem);
        imageItem = v.findViewById(R.id.prevImage);
        dialogBuilder = new AlertDialog.Builder(getContext());
        View layoutView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(layoutView);
        customDialog = dialogBuilder.create();
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        imageItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galery, REQUEST_PICK_PHOTO );
            }
        });
        simpanItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {int h,m;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    h = picker.getHour();
                    m = picker.getMinute();
                }else{
                    h = picker.getCurrentHour();
                    m = picker.getCurrentMinute();
                }
                timeEnd = (String.format("%02d:%02d",h,m));
                requestPermission();
            }
        });
        return v;
    }

    private void getnohp() {
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<ResponseBody> ava = mApiService.getAva(Integer.parseInt(idUser));
        ava.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonResult = new JSONObject(response.body().string());
                        if (jsonResult.getString("error").equals("false")) {

                            String no_Hp = jsonResult.getJSONObject("profil").getString("no_hp");
                            noHp = no_Hp;

                        } else {
                            String error_msg = jsonResult.getString("error_msg");
                            Toast.makeText(getContext(), error_msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Cannot get image profil", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inputItem() {
        idKategories = (int) idKategori;
        if (idKategories == 0 ){
            Toast.makeText(getContext(), "Mohon Pilih Kategori dan Shift", Toast.LENGTH_SHORT).show();
        }else{
            namaDagangan = etNama.getText().toString();
            harga = etHarga.getText().toString();
            desa = etDesa.getText().toString();

            if (namaDagangan.equals("")|harga.equals("")|desa.equals("")|mediaPath == null){
                Toast.makeText(getContext(), "Mohon Lengkapi Data diatas", Toast.LENGTH_SHORT).show();
            }else{
                customDialog.show();
                File imagefile = new File(mediaPath);
                RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
                MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", imagefile.getName(), reqBody);
                long length = imagefile.length();
                int l = (int) length;
                if (l <= 2048000) {
                    BaseApiService mApiService = UtilsApi.getApiService();
                    Call<ResponseBody> inputItem = mApiService.inputItem(partImage
                            , RequestBody.create(MediaType.parse("text/plain"), namaDagangan)
                            , RequestBody.create(MediaType.parse("text/plain"), String.valueOf(idKategories))
                            , RequestBody.create(MediaType.parse("text/plain"), idKecamatan)
                            , RequestBody.create(MediaType.parse("text/plain"), desa)
                            , RequestBody.create(MediaType.parse("text/plain"), timeEnd)
                            , RequestBody.create(MediaType.parse("text/plain"), noHp)
                            , RequestBody.create(MediaType.parse("text/plain"), harga)
                            , RequestBody.create(MediaType.parse("text/plain"), idUser));
                    inputItem.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Berhasil Input Data", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getContext(), NavigationPenjual.class);
                                startActivity(i);
                                customDialog.dismiss();
                                getActivity().finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    customDialog.dismiss();
                    Toast.makeText(getContext(), "Gambar lebih dari 2mb", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        idKategori = parent.getItemIdAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn,null,null,null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex= cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    imageItem.setImageURI(data.getData());
                    cursor.close();

                    postPath = mediaPath;
                }
            }
        }
    }
}