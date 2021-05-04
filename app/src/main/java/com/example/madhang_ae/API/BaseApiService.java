package com.example.madhang_ae.API;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
}
