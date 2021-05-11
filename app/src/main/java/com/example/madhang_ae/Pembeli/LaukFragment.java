package com.example.madhang_ae.Pembeli;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.madhang_ae.API.BaseApiService;
import com.example.madhang_ae.API.UtilsApi;
import com.example.madhang_ae.Adapter.AdapterLauk;
import com.example.madhang_ae.EditProfile;
import com.example.madhang_ae.Model.ModelLauk;
import com.example.madhang_ae.Penjual.NavigationPenjual;
import com.example.madhang_ae.R;
import com.example.madhang_ae.ResponseModel.ResponseModelLauk;
import com.example.madhang_ae.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaukFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    SessionManager sessionManager;

    private BottomSheetBehavior bsLauk;
    private LinearLayout linearLayoutbs;
    private RecyclerView rvLauk;
    private RecyclerView.Adapter adLauk;
    private List<ModelLauk> modelLaukList = new ArrayList<>();
    private Spinner kecamatanLauk;
    private long idkecamatanLauk;
    private int idKecamatan;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_lauk, container, false);
        linearLayoutbs = v.findViewById(R.id.bottomSheetLauk);
        bsLauk = BottomSheetBehavior.from(linearLayoutbs);
        bsLauk.setState(BottomSheetBehavior.STATE_EXPANDED);
        sessionManager = new SessionManager(getContext());
        rvLauk = v.findViewById(R.id.rv_item_lauk);
        rvLauk.setLayoutManager(new GridLayoutManager(getContext(),2));
        kecamatanLauk = v.findViewById(R.id.sp_kecamatanLauk);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.listDaftarKecamatan,R.layout.custom_spinner2);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        kecamatanLauk.setAdapter(adapter);
        kecamatanLauk.setOnItemSelectedListener(this);
        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        idkecamatanLauk = parent.getItemIdAtPosition(position);
        idKecamatan = (int) idkecamatanLauk;
        if (idKecamatan == 0){
            BaseApiService mApiService = UtilsApi.getApiService();
            Call<ResponseModelLauk> showAll = mApiService.getAllLauk();
            showAll.enqueue(new Callback<ResponseModelLauk>() {
                @Override
                public void onResponse(Call<ResponseModelLauk> call, Response<ResponseModelLauk> response) {
                    modelLaukList = response.body().getData();
                    adLauk = new AdapterLauk(getContext(),modelLaukList);
                    rvLauk.setAdapter(adLauk);
                    adLauk.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ResponseModelLauk> call, Throwable t) {
                    Toast.makeText(getActivity(), "Gagal Menghubungkan Server pesan : "+t, Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            BaseApiService mApiService = UtilsApi.getApiService();
            Call<ResponseModelLauk> showAll = mApiService.getLaukByKecamatan(idKecamatan);
            showAll.enqueue(new Callback<ResponseModelLauk>() {
                @Override
                public void onResponse(Call<ResponseModelLauk> call, Response<ResponseModelLauk> response) {
                    modelLaukList = response.body().getData();
                    adLauk = new AdapterLauk(getContext(),modelLaukList);
                    rvLauk.setAdapter(adLauk);
                    adLauk.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ResponseModelLauk> call, Throwable t) {
                    Toast.makeText(getActivity(), "Gagal Menghubungkan Server pesan : "+t, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}