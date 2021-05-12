package com.example.madhang_ae.API;


import com.example.madhang_ae.Model.ModelPenjual;
import com.example.madhang_ae.ResponseModel.ResponseModelJajanan;
import com.example.madhang_ae.ResponseModel.ResponseModelLauk;
import com.example.madhang_ae.ResponseModel.ResponseModelMakanan;
import com.example.madhang_ae.ResponseModel.ResponseModelMinuman;
import com.example.madhang_ae.ResponseModel.ResponseModelPenjual;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
    @Multipart
    @POST("Akun/updateAkun")
    Call<ResponseBody> updateAkun (
            @Part MultipartBody.Part image
            ,@Part("nama") RequestBody nama
            ,@Part("password") RequestBody password
            ,@Part("id_kecamatan") RequestBody id_kecamatan
            ,@Part("email") RequestBody email
            ,@Part("otp") RequestBody otp
            ,@Part("no_hp") RequestBody no_hp
            ,@Part("id_user") RequestBody id_user);
    @FormUrlEncoded
    @POST("Akun/Verifikasi")
    Call<ResponseBody> verifikasi(@Field("email") String email,@Field("otp")int otp);
    @FormUrlEncoded
    @POST("Akun/updatePassword")
    Call<ResponseBody> updatePassword(@Field("id_user") String idUser,@Field("password")String password);

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
    @FormUrlEncoded
    @POST("Item/getAllItem")
    Call<ResponseModelPenjual> getAllItem(@Field("id_user") int id_user);
    @FormUrlEncoded
    @POST("Item/getAllItemByKategori")
    Call<ResponseModelPenjual> getAllItemByKategori(@Field("id_user") int id_user,@Field("id_kategori") int id_kategori);

    @GET("Item/getMakanan")
    Call<ResponseModelMakanan> getAllMakanan();
    @FormUrlEncoded
    @POST("Item/getMakananByKecamatan")
    Call<ResponseModelMakanan> getMakananByKecamatan(@Field("id_kecamatan") int id_kecamatan);
    @GET("Item/getMinuman")
    Call<ResponseModelMinuman> getAllMinuman();
    @FormUrlEncoded
    @POST("Item/getMinumanByKecamatan")
    Call<ResponseModelMinuman> getMinumanByKecamatan(@Field("id_kecamatan") int id_kecamatan);
    @GET("Item/getLauk")
    Call<ResponseModelLauk> getAllLauk();
    @FormUrlEncoded
    @POST("Item/getLaukByKecamatan")
    Call<ResponseModelLauk> getLaukByKecamatan(@Field("id_kecamatan") int id_kecamatan);
    @GET("Item/getJajanan")
    Call<ResponseModelJajanan> getAllJajanan();
    @FormUrlEncoded
    @POST("Item/getJajananByKecamatan")
    Call<ResponseModelJajanan> getJajananByKecamatan(@Field("id_kecamatan") int id_kecamatan);
}
