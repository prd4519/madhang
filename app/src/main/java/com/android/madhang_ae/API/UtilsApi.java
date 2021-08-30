package com.android.madhang_ae.API;

public class UtilsApi {

    public static final String BASE_URL = "https://madhangae.000webhostapp.com/Madhang_ae/";
    public static final String IMAGE_URL = "https://madhangae.000webhostapp.com/Madhang_ae/assets/files/image/";
    public static final String WA_URL = "https://wa.me/";
    public static final String MESSAGE = "/?text=";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL).create(BaseApiService.class);
    }
}
