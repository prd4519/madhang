package com.example.madhang_ae.Penjual;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.example.madhang_ae.Adapter.AdapterPenjual;
import com.example.madhang_ae.Adapter.AdapterPenjual;
import com.example.madhang_ae.Adapter.AdapterPenjual;
import com.example.madhang_ae.EditProfile;
import com.example.madhang_ae.Model.ModelPenjual;
import com.example.madhang_ae.Pembeli.NavigationPembeli;
import com.example.madhang_ae.R;
import com.example.madhang_ae.ResponseModel.ResponseModelPenjual;
import com.example.madhang_ae.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    SessionManager sessionManager;
    private BottomSheetBehavior bsHome;
    private LinearLayout linearLayoutbs;
    private RecyclerView rvPenjual;
    private RecyclerView.Adapter adPenjual;
    private RecyclerView.LayoutManager lmPenjual;
    private List<ModelPenjual>modelPenjualList = new ArrayList<>();
    int idUser,idKategoriDagangan;
    long idKategori;
    Spinner kategoriDagangan;
    private TextView nodata;
    private ImageView nodataImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
//        fabPop = v.findViewById(R.id.popupp);
        linearLayoutbs = v.findViewById(R.id.bottomSheetPenjual);
        bsHome = BottomSheetBehavior.from(linearLayoutbs);
        bsHome.setState(BottomSheetBehavior.STATE_EXPANDED);
        sessionManager = new SessionManager(getContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        idUser = Integer.parseInt(user.get(SessionManager.kunci_id));
        rvPenjual = v.findViewById(R.id.rv_item_penjual);
        nodata = v.findViewById(R.id.tv_nodata_penjual);
        nodataImage = v.findViewById(R.id.image_nodata_penjual);
        lmPenjual = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rvPenjual.setLayoutManager(lmPenjual);
        kategoriDagangan = v.findViewById(R.id.sp_kategoriDagangan);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.listDaftarKategori,R.layout.custom_spinner2);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        kategoriDagangan.setAdapter(adapter);
        kategoriDagangan.setOnItemSelectedListener(this);


        return v;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        idKategori = parent.getItemIdAtPosition(position);
        idKategoriDagangan = (int) idKategori;
        if (idKategoriDagangan == 0){
            BaseApiService mApiService = UtilsApi.getApiService();
            Call<ResponseModelPenjual> tampil = mApiService.getAllItem(idUser);
            tampil.enqueue(new Callback<ResponseModelPenjual>() {
                @Override
                public void onResponse(Call<ResponseModelPenjual> call, Response<ResponseModelPenjual> response) {
                    modelPenjualList = response.body().getData();
                    if (modelPenjualList.isEmpty()){
                        rvPenjual.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                        nodataImage.setVisibility(View.VISIBLE);
                    }else {
                        adPenjual = new AdapterPenjual(getContext(), modelPenjualList);
                        rvPenjual.setAdapter(adPenjual);
                        adPenjual.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModelPenjual> call, Throwable t) {
                    Toast.makeText(getActivity(), "Gagal Menghubungkan Server pesan : "+t, Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            BaseApiService mApiService = UtilsApi.getApiService();
            Call<ResponseModelPenjual> tampil = mApiService.getAllItemByKategori(idUser,idKategoriDagangan);
            tampil.enqueue(new Callback<ResponseModelPenjual>() {
                @Override
                public void onResponse(Call<ResponseModelPenjual> call, Response<ResponseModelPenjual> response) {
                    modelPenjualList = response.body().getData();
                    if (modelPenjualList.isEmpty()){
                        rvPenjual.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                        nodataImage.setVisibility(View.VISIBLE);
                    }else {
                        adPenjual = new AdapterPenjual(getContext(), modelPenjualList);
                        rvPenjual.setAdapter(adPenjual);
                        adPenjual.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModelPenjual> call, Throwable t) {
                    Toast.makeText(getActivity(), "Gagal Menghubungkan Server pesan : "+t, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}