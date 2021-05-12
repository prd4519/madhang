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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madhang_ae.API.BaseApiService;
import com.example.madhang_ae.API.UtilsApi;
import com.example.madhang_ae.Adapter.AdapterMinuman;
import com.example.madhang_ae.Adapter.AdapterMinuman;
import com.example.madhang_ae.Adapter.AdapterMinuman;
import com.example.madhang_ae.EditProfile;
import com.example.madhang_ae.Model.ModelMinuman;
import com.example.madhang_ae.Penjual.NavigationPenjual;
import com.example.madhang_ae.R;
import com.example.madhang_ae.ResponseModel.ResponseModelMinuman;
import com.example.madhang_ae.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MinumanFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    SessionManager sessionManager;
    private BottomSheetBehavior bsMinuman;
    private LinearLayout linearLayoutbs;
    private RecyclerView rvMinuman;
    private RecyclerView.Adapter adMinuman;
    private List<ModelMinuman> modelMinumanList = new ArrayList<>();
    private Spinner kecamatanMinuman;
    private long idkecamatanMinuman;
    private int idKecamatan;
    private TextView nodata;
    private ImageView nodataImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_minuman, container, false);
        linearLayoutbs = v.findViewById(R.id.bottomSheetMinuman);
        bsMinuman = BottomSheetBehavior.from(linearLayoutbs);
        bsMinuman.setState(BottomSheetBehavior.STATE_EXPANDED);
        sessionManager = new SessionManager(getContext());
        rvMinuman = v.findViewById(R.id.rv_item_minuman);
        nodata = v.findViewById(R.id.tv_nodata_minuman);
        nodataImage = v.findViewById(R.id.image_nodata_minuman);
        rvMinuman.setLayoutManager(new GridLayoutManager(getContext(),2));
        kecamatanMinuman = v.findViewById(R.id.sp_kecamatanMinuman);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.listDaftarKecamatan,R.layout.custom_spinner2);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        kecamatanMinuman.setAdapter(adapter);
        kecamatanMinuman.setOnItemSelectedListener(this);
        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        idkecamatanMinuman = parent.getItemIdAtPosition(position);
        idKecamatan = (int) idkecamatanMinuman;
        if (idKecamatan == 0){
            BaseApiService mApiService = UtilsApi.getApiService();
            Call<ResponseModelMinuman> showAll = mApiService.getAllMinuman();
            showAll.enqueue(new Callback<ResponseModelMinuman>() {
                @Override
                public void onResponse(Call<ResponseModelMinuman> call, Response<ResponseModelMinuman> response) {
                    modelMinumanList = response.body().getData();
                    if (modelMinumanList.isEmpty()){
                        rvMinuman.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                        nodataImage.setVisibility(View.VISIBLE);
                    }else {
                        rvMinuman.setVisibility(View.VISIBLE);
                        nodata.setVisibility(View.GONE);
                        nodataImage.setVisibility(View.GONE);
                        adMinuman = new AdapterMinuman(getContext(), modelMinumanList);
                        rvMinuman.setAdapter(adMinuman);
                        adMinuman.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModelMinuman> call, Throwable t) {
                    Toast.makeText(getContext(), "Gagal Menghubungkan Server pesan : "+t, Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            BaseApiService mApiService = UtilsApi.getApiService();
            Call<ResponseModelMinuman> showAll = mApiService.getMinumanByKecamatan(idKecamatan);
            showAll.enqueue(new Callback<ResponseModelMinuman>() {
                @Override
                public void onResponse(Call<ResponseModelMinuman> call, Response<ResponseModelMinuman> response) {
                    modelMinumanList = response.body().getData();
                    if (modelMinumanList.isEmpty()){
                        rvMinuman.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                        nodataImage.setVisibility(View.VISIBLE);
                    }else {
                        rvMinuman.setVisibility(View.VISIBLE);
                        nodata.setVisibility(View.GONE);
                        nodataImage.setVisibility(View.GONE);
                        adMinuman = new AdapterMinuman(getContext(), modelMinumanList);
                        rvMinuman.setAdapter(adMinuman);
                        adMinuman.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModelMinuman> call, Throwable t) {
                    Toast.makeText(getContext(), "Gagal Menghubungkan Server pesan : "+t, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}