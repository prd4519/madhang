package com.example.madhang_ae.API;

public class UtilsApi {

    public static final String BASE_URL = "https://madhangae.000webhostapp.com/Madhang_ae/";
    public static final String IMAGE_URL = "https://madhangae.000webhostapp.com/Madhang_ae/assets/files/image/";
    public static final String WA_URL = "https://api.whatsapp.com/send?phone=";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL).create(BaseApiService.class);
    }
}
