package com.example.madhang_ae.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.madhang_ae.API.UtilsApi;
import com.example.madhang_ae.Model.ModelLauk;
import com.example.madhang_ae.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

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
                .into(holder.imageLauk);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(UtilsApi.WA_URL+"62"+mm.getNo_hp()));
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelLaukList.size();
    }

    public class HolderDataLauk extends RecyclerView.ViewHolder{
        private RoundedImageView imageLauk;
        private TextView namaLauk,namaDesa,waktuLauk,hargaLauk;
        public HolderDataLauk(@NonNull View itemView) {
            super(itemView);
            imageLauk = itemView.findViewById(R.id.imageLauk);
            namaLauk = itemView.findViewById(R.id.namaLauk);
            namaDesa = itemView.findViewById(R.id.namaDesaLauk);
            waktuLauk = itemView.findViewById(R.id.waktuLauk);
            hargaLauk = itemView.findViewById(R.id.hargaLauk);
        }
    }
}
