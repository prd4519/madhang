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
import com.example.madhang_ae.Adapter.AdapterJajanan;
import com.example.madhang_ae.Adapter.AdapterJajanan;
import com.example.madhang_ae.Adapter.AdapterJajanan;
import com.example.madhang_ae.EditProfile;
import com.example.madhang_ae.Model.ModelJajanan;
import com.example.madhang_ae.Penjual.NavigationPenjual;
import com.example.madhang_ae.R;
import com.example.madhang_ae.ResponseModel.ResponseModelJajanan;
import com.example.madhang_ae.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JajananFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    SessionManager sessionManager;
    private BottomSheetBehavior bsJajanan;
    private LinearLayout linearLayoutbs;
    private RecyclerView rvJajanan;
    private RecyclerView.Adapter adJajanan;
    private List<ModelJajanan> modelJajananList = new ArrayList<>();
    private Spinner kecamatanJajanan;
    private long idkecamatanJajanan;
    private int idKecamatan;
    private TextView nodata;
    private ImageView nodataImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_jajanan, container, false);
        linearLayoutbs = v.findViewById(R.id.bottomSheetJajanan);
        bsJajanan = BottomSheetBehavior.from(linearLayoutbs);
        bsJajanan.setState(BottomSheetBehavior.STATE_EXPANDED);
        sessionManager = new SessionManager(getContext());
        rvJajanan = v.findViewById(R.id.rv_item_jajanan);
        nodata = v.findViewById(R.id.tv_nodata_jajanan);
        nodataImage = v.findViewById(R.id.image_nodata_jajanan);
        rvJajanan.setLayoutManager(new GridLayoutManager(getContext(),2));
        kecamatanJajanan = v.findViewById(R.id.sp_kecamatanJajanan);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.listDaftarKecamatan,R.layout.custom_spinner2);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        kecamatanJajanan.setAdapter(adapter);
        kecamatanJajanan.setOnItemSelectedListener(this);
        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        idkecamatanJajanan = parent.getItemIdAtPosition(position);
        idKecamatan = (int) idkecamatanJajanan;
        if (idKecamatan == 0){
            BaseApiService mApiService = UtilsApi.getApiService();
            Call<ResponseModelJajanan> showAll = mApiService.getAllJajanan();
            showAll.enqueue(new Callback<ResponseModelJajanan>() {
                @Override
                public void onResponse(Call<ResponseModelJajanan> call, Response<ResponseModelJajanan> response) {
                    modelJajananList = response.body().getData();
                    if (modelJajananList.isEmpty()){
                        rvJajanan.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                        nodataImage.setVisibility(View.VISIBLE);
                    }else {
                        adJajanan = new AdapterJajanan(getContext(), modelJajananList);
                        rvJajanan.setAdapter(adJajanan);
                        adJajanan.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModelJajanan> call, Throwable t) {
                    Toast.makeText(getActivity(), "Gagal Menghubungkan Server pesan : "+t, Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            BaseApiService mApiService = UtilsApi.getApiService();
            Call<ResponseModelJajanan> showAll = mApiService.getJajananByKecamatan(idKecamatan);
            showAll.enqueue(new Callback<ResponseModelJajanan>() {
                @Override
                public void onResponse(Call<ResponseModelJajanan> call, Response<ResponseModelJajanan> response) {
                    modelJajananList = response.body().getData();
                    if (modelJajananList.isEmpty()){
                        rvJajanan.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                        nodataImage.setVisibility(View.VISIBLE);
                    }else {
                        adJajanan = new AdapterJajanan(getContext(), modelJajananList);
                        rvJajanan.setAdapter(adJajanan);
                        adJajanan.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModelJajanan> call, Throwable t) {
                    Toast.makeText(getActivity(), "Gagal Menghubungkan Server pesan : "+t, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}