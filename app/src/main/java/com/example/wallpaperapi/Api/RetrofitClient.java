package com.example.wallpaperapi.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL="https://api.pexels.com/v1/";
    private static RetrofitClient retrofitClient;
    private static Retrofit retrofit;
    private OkHttpClient.Builder builder = new OkHttpClient.Builder();
    //logging interceptor ka use isliye krte h ki api se response send kr ho ya recieve kr rhe ho apni app ke ander us response ko dekhna h apko logcat ke ander kya result aara h to aap iski help se dekh skte ho
    private HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    public RetrofitClient(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //gson response krti h java object se  java class/ object banaye h unki mapping krti h java object se
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();

    }
    
    public static synchronized RetrofitClient getInstance(){
        if (retrofitClient==null){
            retrofitClient=new RetrofitClient();
        }
        return retrofitClient;
    }
    public Api getApi() {return retrofit.create(Api.class);}
}
