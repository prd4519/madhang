package com.example.madhang_ae.API;

public class UtilsApi {

   public static final String BASE_URL = "http://172.23.200.42/Madhang_ae/";
    public static final String IMAGE_URL = "http://172.23.200.42/Madhang_ae/assets/files/image/";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL).create(BaseApiService.class);
    }
}
