package com.example.madhang_ae.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.madhang_ae.API.BaseApiService;
import com.example.madhang_ae.API.UtilsApi;
import com.example.madhang_ae.MainActivity;
import com.example.madhang_ae.Model.ModelPenjual;
import com.example.madhang_ae.Penjual.HomeFragment;
import com.example.madhang_ae.R;
import com.example.madhang_ae.ServicePenjual;
import com.example.madhang_ae.otp;
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
        holder.id.setText(String.valueOf(mp.getId()));
        holder.namaDagangan.setText(mp.getNama());
        holder.namaKategori.setText(mp.getNamaKategori());
        holder.harga.setText("Rp. "+String.valueOf(mp.getHarga()));
        Glide.with(holder.itemView.getContext())
                .load(UtilsApi.IMAGE_URL + mp.getImage())
                .apply(new RequestOptions().override(160,110))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.image.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.image.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(holder.image);
        isTimeExpired(holder.time, mp.getJam_end(),mp.getId());
        isDateExpired(holder.date,mp.getTimestamp(),mp.getId());
        /*Handler handlertime = new Handler(Looper.myLooper());
        handlertime.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },1000);

        Handler handlerdate = new Handler();
        handlerdate.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },1000);*/

    }



    @Override
    public int getItemCount() {
        return modelPenjualList.size();
    }

    public class HolderDataPenjual extends RecyclerView.ViewHolder{
        private RoundedImageView image;
        private TextView namaDagangan, namaKategori, harga,id;
        private ImageButton deleteDagangan;
        final ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.progressPenjual);
        Calendar calendar;
        SimpleDateFormat dateFormat,dateFormat2;
        String time,date;
        AlertDialog.Builder dialogBuilder;
        AlertDialog alertDialog;
        public HolderDataPenjual(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imagePenjual);
            id = itemView.findViewById(R.id.idItem);
            namaDagangan = itemView.findViewById(R.id.namaDagangan);
            namaKategori = itemView.findViewById(R.id.kategoriDagangan);
            harga = itemView.findViewById(R.id.hargaDagangan);
            calendar = Calendar.getInstance();
            deleteDagangan = itemView.findViewById(R.id.btnDeleteDagangan);
            dateFormat = new SimpleDateFormat("HH:mm");
            dateFormat2= new SimpleDateFormat("yyyy-MM-dd");
            time = dateFormat.format(calendar.getTime());
            date = dateFormat2.format(calendar.getTime());
            deleteDagangan = itemView.findViewById(R.id.btnDeleteDagangan);

            deleteDagangan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlertDialog(namaDagangan.getText().toString());

                }
            });
        }
        private void showAlertDialog(String namaDagangan){
            dialogBuilder = new AlertDialog.Builder(ctx);
            View layoutView = LayoutInflater.from(ctx).inflate(R.layout.layout_delete_item,null);
            Button dialogButtonOk = layoutView.findViewById(R.id.btnDialogDelete);
            Button dialogButtonBatal = layoutView.findViewById(R.id.btnDialogBatalDelete);
            TextView message = layoutView.findViewById(R.id.txt_delete_item);
            message.setText("Apakah anda ingin menghapus "+namaDagangan+" dari menu ?");
            dialogBuilder.setView(layoutView);
            alertDialog = dialogBuilder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();
            dialogButtonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteItem(Integer.parseInt(id.getText().toString()));
                    alertDialog.dismiss();

                }
            });
            dialogButtonBatal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
        }
    }

    public boolean isTimeExpired(String startDate, String endDate, int id) {
        SimpleDateFormat dfDate = new SimpleDateFormat("HH:mm");
        boolean b = false;
        Handler handler = new Handler();
        try {
            ctx.startService(new Intent(ctx.getApplicationContext(), ServicePenjual.class));
            if (dfDate.parse(startDate).before(dfDate.parse(endDate))) {
                return true;  // If start date is before end date.
            } else if (dfDate.parse(startDate).equals(dfDate.parse(endDate))) {
                deleteItem1(id);

                return false;  // If two dates are equal.
            } else {
                deleteItem1(id);

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
    private void deleteItem1(int id) {
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<ResponseBody> delete = mApiService.deleteItem(id);
        delete.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(ctx, "Item expired berhasil dihapus", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void deleteItem(int id) {
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<ResponseBody> delete = mApiService.deleteItem(id);
        delete.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(ctx, "Berhasil Hapus Data", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
