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
}
