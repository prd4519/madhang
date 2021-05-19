package com.example.madhang_ae.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.madhang_ae.API.BaseApiService;
import com.example.madhang_ae.API.UtilsApi;
import com.example.madhang_ae.Model.ModelPenjual;
import com.example.madhang_ae.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterPenjual extends RecyclerView.Adapter<AdapterPenjual.HolderDataPenjual>{
    private Context ctx;
    private List<ModelPenjual> modelPenjualList;

    public AdapterPenjual(Context ctx, List<ModelPenjual> modelPenjualList) {
        this.ctx = ctx;
        this.modelPenjualList = modelPenjualList;
    }

    @NonNull
    @Override
    public HolderDataPenjual onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_penjual,parent,false);
        HolderDataPenjual holder = new HolderDataPenjual(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderDataPenjual holder, int position) {
        ModelPenjual mp = modelPenjualList.get(position);
        holder.namaDagangan.setText(mp.getNama());
        holder.namaKategori.setText(mp.getNamaKategori());
        holder.harga.setText("Rp. "+String.valueOf(mp.getHarga()));
        Glide.with(holder.itemView.getContext())
                .load(UtilsApi.IMAGE_URL + mp.getImage())
                .apply(new RequestOptions().override(160,110))
                .into(holder.image);
        isTimeExpired(holder.date, mp.getJam_end(),mp.getId());
    }



    @Override
    public int getItemCount() {
        return modelPenjualList.size();
    }

    public class HolderDataPenjual extends RecyclerView.ViewHolder{
        private RoundedImageView image;
        private TextView namaDagangan, namaKategori, harga;
        private ImageButton deleteDagangan;
        Calendar calendar;
        SimpleDateFormat dateFormat;
        String date;
        public HolderDataPenjual(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imagePenjual);
            namaDagangan = itemView.findViewById(R.id.namaDagangan);
            namaKategori = itemView.findViewById(R.id.kategoriDagangan);
            harga = itemView.findViewById(R.id.hargaDagangan);
            calendar = Calendar.getInstance();
            deleteDagangan = itemView.findViewById(R.id.btnDeleteDagangan);
            dateFormat = new SimpleDateFormat("HH:mm");
            date = dateFormat.format(calendar.getTime());
        }
    }

    public boolean isTimeExpired(String startDate, String endDate, int id) {
        SimpleDateFormat dfDate = new SimpleDateFormat("HH:mm");
        boolean b = false;
        try {
            if (dfDate.parse(startDate).before(dfDate.parse(endDate))) {
                return true;  // If start date is before end date.
            } else if (dfDate.parse(startDate).equals(dfDate.parse(endDate))) {
                deleteItem(id);
                return false;  // If two dates are equal.
            } else {
                deleteItem(id);
                return false; // If start date is after the end date.
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    private void deleteItem(int id) {
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<ResponseBody> delete = mApiService.deleteItem(id);
        delete.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(ctx, "Terdapat item expired ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
