package com.android.madhang_ae.Pembeli;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.madhang_ae.API.BaseApiService;
import com.android.madhang_ae.API.UtilsApi;
import com.android.madhang_ae.Adapter.AdapterLauk;
import com.android.madhang_ae.Model.ModelLauk;
import com.android.madhang_ae.R;
import com.android.madhang_ae.ResponseModel.ResponseModelLauk;
import com.android.madhang_ae.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

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
    private TextView nodata;
    private ImageView nodataImage;
    Handler handler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_lauk, container, false);
        linearLayoutbs = v.findViewById(R.id.bottomSheetLauk);
        bsLauk = BottomSheetBehavior.from(linearLayoutbs);
        bsLauk.setState(BottomSheetBehavior.STATE_EXPANDED);
        sessionManager = new SessionManager(getContext());
        rvLauk = v.findViewById(R.id.rv_item_lauk);
        nodata = v.findViewById(R.id.tv_nodata_lauk);
        nodataImage = v.findViewById(R.id.image_nodata_lauk);
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
                    if (modelLaukList.isEmpty()){
                        rvLauk.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                        nodataImage.setVisibility(View.VISIBLE);
                    }else {
                        rvLauk.setVisibility(View.VISIBLE);
                        nodata.setVisibility(View.GONE);
                        nodataImage.setVisibility(View.GONE);
                        adLauk = new AdapterLauk(getContext(), modelLaukList);
                        rvLauk.setAdapter(adLauk);
                        adLauk.notifyDataSetChanged();

                    }
                }

                @Override
                public void onFailure(Call<ResponseModelLauk> call, Throwable t) {
                    Toast.makeText(getContext(), "Gagal Menghubungkan Server pesan : "+t, Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            BaseApiService mApiService = UtilsApi.getApiService();
            Call<ResponseModelLauk> showAll = mApiService.getLaukByKecamatan(idKecamatan);
            showAll.enqueue(new Callback<ResponseModelLauk>() {
                @Override
                public void onResponse(Call<ResponseModelLauk> call, Response<ResponseModelLauk> response) {
                    modelLaukList = response.body().getData();
                    if (modelLaukList.isEmpty()){
                        rvLauk.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                        nodataImage.setVisibility(View.VISIBLE);
                    }else {
                        rvLauk.setVisibility(View.VISIBLE);
                        nodata.setVisibility(View.GONE);
                        nodataImage.setVisibility(View.GONE);
                        adLauk = new AdapterLauk(getContext(), modelLaukList);
                        rvLauk.setAdapter(adLauk);
                        adLauk.notifyDataSetChanged();

                    }
                }

                @Override
                public void onFailure(Call<ResponseModelLauk> call, Throwable t) {
                    Toast.makeText(getContext(), "Gagal Menghubungkan Server pesan : "+t, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
//    private void refreshAll(AdapterView<?> parent, View view, int position, long id){
//        handler = new Handler();
//        final Runnable r = new Runnable() {
//            @Override
//            public void run() {
//                onItemSelected(parent,view,position,id);
//            }
//        };
//        handler.postDelayed(r,1000);
////        ExecutorService executorService = Executors.newCachedThreadPool();
////        executorService.submit(r);
////
////        executorService.shutdown();
//
//    }
}