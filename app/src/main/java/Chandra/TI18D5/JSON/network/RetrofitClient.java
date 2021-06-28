package Chandra.TI18D5.JSON.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String BASE_URL = "https://newsapi.org/v2/";

    public static Retrofit setInit(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public static ApiServices getInstance(){
        return setInit().create(ApiServices.class);
    }
}
