package com.example.madhang_ae.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.madhang_ae.API.BaseApiService;
import com.example.madhang_ae.API.UtilsApi;
import com.example.madhang_ae.Model.ModelLauk;
import com.example.madhang_ae.R;
import com.example.madhang_ae.ServicePenjual;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterLauk extends RecyclerView.Adapter<AdapterLauk.HolderDataLauk>{
    private Context ctx;
    private List<ModelLauk> modelLaukList;

    public AdapterLauk(Context ctx, List<ModelLauk> modelLaukList) {
        this.ctx = ctx;
        this.modelLaukList = modelLaukList;
    }

    @NonNull
    @Override
    public HolderDataLauk onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_lauk,parent,false);
        HolderDataLauk holder = new HolderDataLauk(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderDataLauk holder, int position) {
        ModelLauk mm = modelLaukList.get(position);
        holder.namaLauk.setText(mm.getNama());
        holder.namaDesa.setText("Desa "+mm.getDesa());
        holder.waktuLauk.setText("Tersedia Hingga "+mm.getJam_end());

        holder.hargaLauk.setText("Rp. "+String.valueOf(mm.getHarga()));
        Glide.with(holder.itemView.getContext())
                .load(UtilsApi.IMAGE_URL + mm.getImage())
                .apply(new RequestOptions().override(160,110))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.imageLauk.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.imageLauk.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(holder.imageLauk);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(UtilsApi.WA_URL+"62"+mm.getNo_hp()+UtilsApi.MESSAGE
                        +"Halo,\n Saya ingin membeli "+mm.getNama()+" yang anda jual " +
                        "dengan harga Rp."+mm.getHarga()+"\nApakah masih tersedia ?"));
                v.getContext().startActivity(i);
            }
        });
        isTimeExpired(holder.time, mm.getJam_end(),mm.getId());
        isDateExpired(holder.date,mm.getTimestamp(),mm.getId());
    }

    @Override
    public int getItemCount() {
        return modelLaukList.size();
    }

    public class HolderDataLauk extends RecyclerView.ViewHolder{
        private RoundedImageView imageLauk;
        private TextView namaLauk,namaDesa,waktuLauk,hargaLauk;
        private String time,date;
        Calendar calendar;
        SimpleDateFormat sdf,sdf2;
        final ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.progressLauk);
        public HolderDataLauk(@NonNull View itemView) {
            super(itemView);
            calendar = Calendar.getInstance();
            sdf=new SimpleDateFormat("HH:mm");
            sdf2= new SimpleDateFormat("yyyy-MM-dd");
            time = sdf.format(calendar.getTime());
            date = sdf2.format(calendar.getTime());

            imageLauk = itemView.findViewById(R.id.imageLauk);
            namaLauk = itemView.findViewById(R.id.namaLauk);
            namaDesa = itemView.findViewById(R.id.namaDesaLauk);
            waktuLauk = itemView.findViewById(R.id.waktuLauk);
            hargaLauk = itemView.findViewById(R.id.hargaLauk);
        }
    }
    public boolean isTimeExpired(String startDate, String endDate, int id) {
        SimpleDateFormat dfDate = new SimpleDateFormat("HH:mm");
        boolean b = false;
        try {
            ctx.startService(new Intent(ctx.getApplicationContext(), ServicePenjual.class));
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
    public boolean isDateExpired(String startUpload, String endUpload, int id) {
        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
        boolean b = false;
        try {
            ctx.startService(new Intent(ctx.getApplicationContext(), ServicePenjual.class));
            if (dfDate.parse(startUpload).equals(dfDate.parse(endUpload))) {
                return true;  // If two dates are equal.
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

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
