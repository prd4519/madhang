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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.madhang_ae.API.UtilsApi;
import com.example.madhang_ae.Model.ModelJajanan;
import com.example.madhang_ae.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class AdapterJajanan extends RecyclerView.Adapter<AdapterJajanan.HolderDataJajanan>{
    private Context ctx;
    private List<ModelJajanan> modelJajananList;

    public AdapterJajanan(Context ctx, List<ModelJajanan> modelJajananList) {
        this.ctx = ctx;
        this.modelJajananList = modelJajananList;
    }

    @NonNull
    @Override
    public HolderDataJajanan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_jajanan,parent,false);
        HolderDataJajanan holder = new HolderDataJajanan(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderDataJajanan holder, int position) {
        ModelJajanan mm = modelJajananList.get(position);
        holder.namaJajanan.setText(mm.getNama());
        holder.namaDesa.setText("Desa "+mm.getDesa());
        holder.waktuJajanan.setText("Tersedia Hingga "+mm.getJam_end());
        holder.hargaMaknan.setText("Rp. "+String.valueOf(mm.getHarga()));
        Glide.with(holder.itemView.getContext())
                .load(UtilsApi.IMAGE_URL + mm.getImage())
                .apply(new RequestOptions().override(160,110))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.imageJajanan.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.imageJajanan.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(holder.imageJajanan);
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
    }

    @Override
    public int getItemCount() {
        return modelJajananList.size();
    }

    public class HolderDataJajanan extends RecyclerView.ViewHolder{
        private RoundedImageView imageJajanan;
        private TextView namaJajanan,namaDesa,waktuJajanan,hargaMaknan;
        final ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.progressJajan);
        public HolderDataJajanan(@NonNull View itemView) {
            super(itemView);
            imageJajanan = itemView.findViewById(R.id.imageJajanan);
            namaJajanan = itemView.findViewById(R.id.namaJajanan);
            namaDesa = itemView.findViewById(R.id.namaDesaJajanan);
            waktuJajanan = itemView.findViewById(R.id.waktuJajanan);
            hargaMaknan = itemView.findViewById(R.id.hargaJajanan);
        }
    }
}
