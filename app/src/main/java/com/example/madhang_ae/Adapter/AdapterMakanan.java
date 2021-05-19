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
import com.example.madhang_ae.Model.ModelMakanan;
import com.example.madhang_ae.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class AdapterMakanan extends RecyclerView.Adapter<AdapterMakanan.HolderDataMakanan>{
    private Context ctx;
    private List<ModelMakanan> modelMakananList;

    public AdapterMakanan(Context ctx, List<ModelMakanan> modelMakananList) {
        this.ctx = ctx;
        this.modelMakananList = modelMakananList;
    }

    @NonNull
    @Override
    public HolderDataMakanan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_makanan,parent,false);
        HolderDataMakanan holder = new HolderDataMakanan(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderDataMakanan holder, int position) {
        ModelMakanan mm = modelMakananList.get(position);
        holder.namaMakanan.setText(mm.getNama());
        holder.namaDesa.setText("Desa "+mm.getDesa());
        holder.waktuMakanan.setText("Tersedia Hingga "+mm.getJam_end());
        holder.hargaMaknan.setText("Rp. "+String.valueOf(mm.getHarga()));
        Glide.with(holder.itemView.getContext())
                .load(UtilsApi.IMAGE_URL + mm.getImage())
                .apply(new RequestOptions().override(160,110))
                .into(holder.imageMakanan);
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
        return modelMakananList.size();
    }

    public class HolderDataMakanan extends RecyclerView.ViewHolder{
        private RoundedImageView imageMakanan;
        private TextView namaMakanan,namaDesa,waktuMakanan,hargaMaknan;
        public HolderDataMakanan(@NonNull View itemView) {
            super(itemView);
            imageMakanan = itemView.findViewById(R.id.imageMakanan);
            namaMakanan = itemView.findViewById(R.id.namaMakanan);
            namaDesa = itemView.findViewById(R.id.namaDesaMakanan);
            waktuMakanan = itemView.findViewById(R.id.waktuMakanan);
            hargaMaknan = itemView.findViewById(R.id.hargaMakanan);
        }
    }
}
