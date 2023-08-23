package com.example.wallpaperapi.Api;

import com.example.wallpaperapi.Models.WallpaperResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Query;

public interface Api {
    //ye method kaam karega photo ko get krne me
    @GET("curated")//api konsi h get ki ya post ki vo yha mention krna hoga curated means vo base url ke baad ka jo h mtlb isse humko latest image milegi
    Call<WallpaperResponse> getWallpaper(
            @Header("Authorization") String credentials,
            @Query("page") int pageCount,
            @Query("per_page") int perPage
    );

    @GET("search?")
    Call<WallpaperResponse> getSearch(

            @Header("Authorization") String credentials,
            @Query("query") String queryText
    );
}
