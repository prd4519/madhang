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
import android.widget.Toast;

import com.example.madhang_ae.API.BaseApiService;
import com.example.madhang_ae.API.UtilsApi;
import com.example.madhang_ae.EditProfile;
import com.example.madhang_ae.Pembeli.NavigationPembeli;
import com.example.madhang_ae.R;
import com.example.madhang_ae.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
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
    long idKategori,shift;
    int idKategories,Shift;
    Spinner kategori,spShift;
    String namaDagangan,harga,desa,idUser,noHp,idKecamatan,mediaPath,postPath;
    Button simpanItem;
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
        linearLayoutbs = v.findViewById(R.id.bottomSheetMenu);
        bsMenu = BottomSheetBehavior.from(linearLayoutbs);
        bsMenu.setState(BottomSheetBehavior.STATE_EXPANDED);
        HashMap<String, String> user = sessionManager.getUserDetails();
        idUser = user.get(SessionManager.kunci_id);
        idKecamatan = user.get(SessionManager.kunci_idKec);
        noHp = user.get(SessionManager.kunci_noHp);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.listDaftarKategori,R.layout.custom_spinner2);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        kategori.setAdapter(adapter);
        etNama = v.findViewById(R.id.et_namaDagangan);
        etHarga = v.findViewById(R.id.et_hargaDagangan);
        etDesa = v.findViewById(R.id.et_namaDesaDagangan);
        spShift = v.findViewById(R.id.sp_shiftInput);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),R.array.listDaftarShift,R.layout.custom_spinner2);
        adapter2.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spShift.setAdapter(adapter2);
        spShift.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shift = parent.getItemIdAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        kategori.setOnItemSelectedListener(this);
        simpanItem = v.findViewById(R.id.btn_SimpanItem);
        imageItem = v.findViewById(R.id.prevImage);
        imageItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galery, REQUEST_PICK_PHOTO );
            }
        });
        simpanItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });
        return v;
    }

    private void inputItem() {
        idKategories = (int) idKategori;
        Shift = (int) shift;
        if (idKategories == 0 && Shift == 0){
            Toast.makeText(getContext(), "Mohon Pilih Kategori dan Shift", Toast.LENGTH_SHORT).show();
        }else{
            namaDagangan = etNama.getText().toString();
            harga = etHarga.getText().toString();
            desa = etDesa.getText().toString();
            File imagefile = new File(mediaPath);
            RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
            MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", imagefile.getName(), reqBody);
            BaseApiService mApiService = UtilsApi.getApiService();
            Call<ResponseBody> inputItem = mApiService.inputItem(partImage,RequestBody.create(MediaType.parse("text/plain"), namaDagangan)
                    ,RequestBody.create(MediaType.parse("text/plain"), String.valueOf(idKategories))
                    ,RequestBody.create(MediaType.parse("text/plain"), idKecamatan)
                    ,RequestBody.create(MediaType.parse("text/plain"), desa)
                    ,RequestBody.create(MediaType.parse("text/plain"), String.valueOf(Shift))
                    ,RequestBody.create(MediaType.parse("text/plain"), noHp)
                    ,RequestBody.create(MediaType.parse("text/plain"), harga)
                    ,RequestBody.create(MediaType.parse("text/plain"), idUser));
            inputItem.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(getContext(), "Berhasil Input Data", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getContext(),NavigationPenjual.class);
                    startActivity(i);
                    getActivity().finish();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
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