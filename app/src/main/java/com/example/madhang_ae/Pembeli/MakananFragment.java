package com.example.madhang_ae.Pembeli;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madhang_ae.API.BaseApiService;
import com.example.madhang_ae.API.UtilsApi;
import com.example.madhang_ae.Adapter.AdapterMakanan;
import com.example.madhang_ae.EditProfile;
import com.example.madhang_ae.MainActivity;
import com.example.madhang_ae.Model.ModelMakanan;
import com.example.madhang_ae.Penjual.HomeFragment;
import com.example.madhang_ae.Penjual.NavigationPenjual;
import com.example.madhang_ae.R;
import com.example.madhang_ae.ResponseModel.ResponseModelMakanan;
import com.example.madhang_ae.SessionManager;
import com.example.madhang_ae.otp;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakananFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private BottomSheetBehavior bsMakanan;
    private LinearLayout linearLayoutbs;
    SessionManager sessionManager;
    private TextView nodata;
    private ImageView nodataImage;
    private RecyclerView rvMakanan;
    private RecyclerView.Adapter adMakanan;
    private List<ModelMakanan> modelMakananList = new ArrayList<>();
    private Spinner kecamatanMakanan;
    private long idkecamatanMakanan;
    private int idKecamatan;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_makanan, container, false);
        linearLayoutbs = v.findViewById(R.id.bottomSheetMakanan);
        bsMakanan = BottomSheetBehavior.from(linearLayoutbs);
        bsMakanan.setState(BottomSheetBehavior.STATE_EXPANDED);
        sessionManager = new SessionManager(getContext());
        rvMakanan = v.findViewById(R.id.rv_item_makanan);
        nodata = v.findViewById(R.id.tv_nodata_makanan);
        nodataImage = v.findViewById(R.id.image_nodata_makanan);
        rvMakanan.setLayoutManager(new GridLayoutManager(getContext(),2));
        kecamatanMakanan = v.findViewById(R.id.sp_kecamatanMakanan);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.listDaftarKecamatan,R.layout.custom_spinner2);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        kecamatanMakanan.setAdapter(adapter);
        kecamatanMakanan.setOnItemSelectedListener(this);
        return v;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        idkecamatanMakanan = parent.getItemIdAtPosition(position);
        idKecamatan = (int) idkecamatanMakanan;
        if (idKecamatan == 0){
            BaseApiService mApiService = UtilsApi.getApiService();
            Call<ResponseModelMakanan> showAll = mApiService.getAllMakanan();
            showAll.enqueue(new Callback<ResponseModelMakanan>() {
                @Override
                public void onResponse(Call<ResponseModelMakanan> call, Response<ResponseModelMakanan> response) {
                    modelMakananList = response.body().getData();
                    if (modelMakananList.isEmpty()){
                        rvMakanan.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                        nodataImage.setVisibility(View.VISIBLE);
                    }else {
                        rvMakanan.setVisibility(View.VISIBLE);
                        nodata.setVisibility(View.GONE);
                        nodataImage.setVisibility(View.GONE);
                        adMakanan = new AdapterMakanan(getContext(), modelMakananList);
                        rvMakanan.setAdapter(adMakanan);
                        adMakanan.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModelMakanan> call, Throwable t) {
                    Toast.makeText(getActivity(), "Gagal Menghubungkan Server pesan : "+t, Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            BaseApiService mApiService = UtilsApi.getApiService();
            Call<ResponseModelMakanan> showAll = mApiService.getMakananByKecamatan(idKecamatan);
            showAll.enqueue(new Callback<ResponseModelMakanan>() {
                @Override
                public void onResponse(Call<ResponseModelMakanan> call, Response<ResponseModelMakanan> response) {
                    modelMakananList = response.body().getData();
                    if (modelMakananList.isEmpty()){
                        rvMakanan.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                        nodataImage.setVisibility(View.VISIBLE);
                    }else {
                        rvMakanan.setVisibility(View.VISIBLE);
                        nodata.setVisibility(View.GONE);
                        nodataImage.setVisibility(View.GONE);
                        adMakanan = new AdapterMakanan(getContext(), modelMakananList);
                        rvMakanan.setAdapter(adMakanan);
                        adMakanan.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModelMakanan> call, Throwable t) {
                    Toast.makeText(getActivity(), "Gagal Menghubungkan Server pesan : "+t, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}