package com.example.madhang_ae.API;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface BaseApiService {
    @FormUrlEncoded
    @POST("Ceklogin/insertLogin")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                                    @Field("password") String password
    );
    @FormUrlEncoded
    @POST("Akun/insertAkun")
    Call<ResponseBody> insertAkun(@Field("email") String email,
                                  @Field("password") String password,
                                  @Field("no_hp") String noHP,
                                  @Field("id_kecamatan") int idKecamatan,
                                  @Field("nama") String nama,
                                  @Field("otp") int otp

    );
    @FormUrlEncoded
    @POST("Akun/Verifikasi")
    Call<ResponseBody> verifikasi(@Field("email") String email,@Field("otp")int otp);
    @Multipart
    @POST("Item/insertItem")
    Call<ResponseBody> inputItem (
             @Part MultipartBody.Part image
            ,@Part("nama") RequestBody nama
            ,@Part("id_kategori") RequestBody id_kategori
            ,@Part("id_kecamatan") RequestBody id_kecamatan
            ,@Part("desa") RequestBody desa
            ,@Part("shift") RequestBody shift
            ,@Part("no_hp") RequestBody no_hp
            ,@Part("harga") RequestBody harga
            ,@Part("id_user") RequestBody id_user);
}
