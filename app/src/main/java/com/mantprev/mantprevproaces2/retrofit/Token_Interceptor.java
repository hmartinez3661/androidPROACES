package com.mantprev.mantprevproaces2.retrofit;

import com.mantprev.mantprevproaces2.utilities.StaticConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class Token_Interceptor implements Interceptor {
/***************************************************/

    public Token_Interceptor(){
        //void
    }


    @Override
    public Response intercept (Chain chain) throws IOException {
    /*********************************************************/

        Request newRequest = chain.request().newBuilder()
                .header("Authorization","Bearer " + "StaticConfig.bearerToken")
                .build();

        return chain.proceed(newRequest);
    }



}
