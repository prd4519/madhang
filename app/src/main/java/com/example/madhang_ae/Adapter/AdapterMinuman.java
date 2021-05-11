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
import com.example.madhang_ae.Model.ModelMinuman;
import com.example.madhang_ae.Model.ModelMinuman;
import com.example.madhang_ae.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class AdapterMinuman extends RecyclerView.Adapter<AdapterMinuman.HolderDataMinuman>{
    private Context ctx;
    private List<ModelMinuman> modelMinumanList;

    public AdapterMinuman(Context ctx, List<ModelMinuman> modelMinumanList) {
        this.ctx = ctx;
        this.modelMinumanList = modelMinumanList;
    }

    @NonNull
    @Override
    public HolderDataMinuman onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_minuman,parent,false);
        HolderDataMinuman holder = new HolderDataMinuman(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderDataMinuman holder, int position) {
        ModelMinuman mm = modelMinumanList.get(position);
        holder.namaMinuman.setText(mm.getNama());
        holder.namaDesa.setText("Desa "+mm.getDesa());
        switch (mm.getShift()){
            case 1:
                holder.waktuMinuman.setText("Tersedia Hingga 12:00");
                break;
            case 2:
                holder.waktuMinuman.setText("Tersedia Hingga 18:00");
                break;
        }
        holder.hargaMinuman.setText("Rp. "+String.valueOf(mm.getHarga()));
        Glide.with(holder.itemView.getContext())
                .load(UtilsApi.IMAGE_URL + mm.getImage())
                .apply(new RequestOptions().override(160,110))
                .into(holder.imageMinuman);
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
        return modelMinumanList.size();
    }

    public class HolderDataMinuman extends RecyclerView.ViewHolder{
        private RoundedImageView imageMinuman;
        private TextView namaMinuman,namaDesa,waktuMinuman,hargaMinuman;
        public HolderDataMinuman(@NonNull View itemView) {
            super(itemView);
            imageMinuman = itemView.findViewById(R.id.imageMinuman);
            namaMinuman = itemView.findViewById(R.id.namaMinuman);
            namaDesa = itemView.findViewById(R.id.namaDesaMinuman);
            waktuMinuman = itemView.findViewById(R.id.waktuMinuman);
            hargaMinuman = itemView.findViewById(R.id.hargaMinuman);
        }
    }
}
