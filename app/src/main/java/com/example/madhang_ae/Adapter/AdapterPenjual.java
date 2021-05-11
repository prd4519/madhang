package com.example.madhang_ae.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.madhang_ae.API.UtilsApi;
import com.example.madhang_ae.Model.ModelPenjual;
import com.example.madhang_ae.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

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
    }

    @Override
    public int getItemCount() {
        return modelPenjualList.size();
    }

    public class HolderDataPenjual extends RecyclerView.ViewHolder{
        private RoundedImageView image;
        private TextView namaDagangan, namaKategori, harga;
        private ImageButton deleteDagangan;
        public HolderDataPenjual(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imagePenjual);
            namaDagangan = itemView.findViewById(R.id.namaDagangan);
            namaKategori = itemView.findViewById(R.id.kategoriDagangan);
            harga = itemView.findViewById(R.id.hargaDagangan);
            deleteDagangan = itemView.findViewById(R.id.btnDeleteDagangan);
        }
    }
}
