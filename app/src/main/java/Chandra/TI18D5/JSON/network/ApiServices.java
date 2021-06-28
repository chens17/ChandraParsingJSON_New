package Chandra.TI18D5.JSON.network;

import Chandra.TI18D5.JSON.model.topheadline.ResponseTopHeadlines;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("top-headlines")
    Call<ResponseTopHeadlines> getListNews(
            @Query("apiKey") String apiKey,
            @Query("country") String country,
//            @Query("category") String category,
            @Query("q") String keyword
    );
}
