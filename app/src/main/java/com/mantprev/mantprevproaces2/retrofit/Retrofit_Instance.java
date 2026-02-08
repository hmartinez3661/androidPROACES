package com.mantprev.mantprevproaces2.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mantprev.mantprevproaces2.utilities.StaticConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_Instance {

    private static Retrofit retrofitInstance;
    private static SessionCookieJar cookieJar;
    private static final String BASE_URL = StaticConfig.ipApiRestServic;


    public static Retrofit getRetrofitInstance(){
    /*******************************************/
        if (cookieJar == null) {
            cookieJar = new SessionCookieJar();
        }

        /*
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
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
        */

        if (retrofitInstance == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .cookieJar(cookieJar)
                    .build();

            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

       return retrofitInstance;
    }





}
