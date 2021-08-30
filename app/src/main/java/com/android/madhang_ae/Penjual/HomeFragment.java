package com.android.madhang_ae.Penjual;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.android.madhang_ae.Adapter.AdapterPenjual;
import com.android.madhang_ae.Model.ModelPenjual;
import com.android.madhang_ae.R;
import com.android.madhang_ae.ResponseModel.ResponseModelPenjual;
import com.android.madhang_ae.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    Handler handler;
    private ImageView nodataImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
//        fabPop = v.findViewById(R.id.popupp);

//        getActivity().startService(new Intent (getActivity(),ServicePenjual.class));
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
                    if (modelPenjualList.isEmpty()) {
                        rvPenjual.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                        nodataImage.setVisibility(View.VISIBLE);
                    } else {
                        rvPenjual.setVisibility(View.VISIBLE);
                        nodata.setVisibility(View.GONE);
                        nodataImage.setVisibility(View.GONE);
                        adPenjual = new AdapterPenjual(getContext(), modelPenjualList);
                        rvPenjual.setAdapter(adPenjual);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(20);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        /*adPenjual.notifyDataSetChanged();*/
                                        adPenjual.notifyItemChanged(1,new Object());
                                    }
                                });
                            }
                        }).start();

                        /*handler.removeCallbacks(r);*/

                        /* refreshAll(parent,view,position,id);*/

                    }
                }

                @Override
                public void onFailure(Call<ResponseModelPenjual> call, Throwable t) {
                    Toast.makeText(getContext(), "Gagal Menghubungkan Server pesan : " + t, Toast.LENGTH_SHORT).show();
                }
            });

        }
        else{
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
                        rvPenjual.setVisibility(View.VISIBLE);
                        nodata.setVisibility(View.GONE);
                        nodataImage.setVisibility(View.GONE);
                        adPenjual = new AdapterPenjual(getContext(), modelPenjualList);
                        rvPenjual.setAdapter(adPenjual);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(20);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adPenjual.notifyItemChanged(1,new Object());
                                    }
                                });
                            }
                        }).start();

                        /*handler.removeCallbacks(r);*/

                        /* refreshAll(parent,view,position,id);*/
                    }
                }

                @Override
                public void onFailure(Call<ResponseModelPenjual> call, Throwable t) {
                    Toast.makeText(getContext(), "Gagal Menghubungkan Server pesan : "+t, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void refreshAll(){
        handler = new Handler();
        final Runnable r1 = new Runnable() {
            @Override
            public void run() {

            }
        };
        handler.post(r1);
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        executorService.submit(r);
//
//        executorService.shutdown();

    }
//    private void refreshWithId(int millisecond){
//        handler = new Handler();
//        final Runnable r = new Runnable() {
//            @Override
//            public void run() {
//                BaseApiService mApiService = UtilsApi.getApiService();
//                Call<ResponseModelPenjual> tampil = mApiService.getAllItemByKategori(idUser,idKategoriDagangan);
//                tampil.enqueue(new Callback<ResponseModelPenjual>() {
//                    @Override
//                    public void onResponse(Call<ResponseModelPenjual> call, Response<ResponseModelPenjual> response) {
//                        modelPenjualList = response.body().getData();
//                        if (modelPenjualList.isEmpty()){
//                            rvPenjual.setVisibility(View.GONE);
//                            nodata.setVisibility(View.VISIBLE);
//                            nodataImage.setVisibility(View.VISIBLE);
//                        }else {
//                            rvPenjual.setVisibility(View.VISIBLE);
//                            nodata.setVisibility(View.GONE);
//                            nodataImage.setVisibility(View.GONE);
//                            adPenjual = new AdapterPenjual(getContext(), modelPenjualList);
//                            rvPenjual.setAdapter(adPenjual);
//                            adPenjual.notifyDataSetChanged();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseModelPenjual> call, Throwable t) {
//                        Toast.makeText(getContext(), "Gagal Menghubungkan Server pesan : "+t, Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        };
//        handler.postDelayed(r,millisecond);
//    }
}