package com.mantprev.mantprevproaces2.retrofit;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.mantprev.mantprevproaces2.MainActivity;
import com.mantprev.mantprevproaces2.R;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class SessionInterceptor implements Interceptor {

    private final Context context;


    public SessionInterceptor(Context context) {         // El constructor recibe el contexto
        this.context = context.getApplicationContext();  // Usamos .getApplicationContext() para evitar fugas de memoria
    }


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response response = chain.proceed(request);

        if (response.code() == 401) {  // Si el servidor responde 401 Unauthorized, la sesión en Spring expiró

            new Handler(Looper.getMainLooper()).post(() -> {
                Toast.makeText(context, "The session was reopened", Toast.LENGTH_LONG).show();

                /*
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
                */
            });

        }

        return response;
    }



}
