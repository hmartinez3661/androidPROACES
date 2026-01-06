package com.mantprev.mantprevproaces2.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mantprev.mantprevproaces2.utilities.StaticConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_Instance {

    private static Retrofit retrofitInstance;
    private static final String BASE_URL = StaticConfig.ipApiRestServic;


    public static Retrofit getRetrofitInstance(){
    /*******************************************/
        Token_Interceptor interceptor = new Token_Interceptor();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofitInstance == null){
            retrofitInstance = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

       return retrofitInstance;
    }





}
