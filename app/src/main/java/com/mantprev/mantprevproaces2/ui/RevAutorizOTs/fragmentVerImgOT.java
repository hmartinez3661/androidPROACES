package com.mantprev.mantprevproaces2.ui.RevAutorizOTs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.mantprev.mantprevproaces2.R;
import com.mantprev.mantprevproaces2.retrofit.DataServices_Intf;
import com.mantprev.mantprevproaces2.retrofit.Retrofit_Instance;
//import com.mantprev.mantprevproaces2.ui.otrasUI.ActivityCerarSesion;
import com.mantprev.mantprevproaces2.utilities.MetodosStaticos;
import com.ortiz.touchview.TouchImageView;

import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class fragmentVerImgOT extends Fragment {

    public fragmentVerImgOT() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private TouchImageView tivVerFoto;
    private View root;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_ver_img_ot, container, false);
        tivVerFoto = (TouchImageView) root.findViewById(R.id.tivVerFoto);

        mostrarFotoSelected();
        return root;
    }


    private void mostrarFotoSelected() {
    /**********************************/
        String nombreFoto = MetodosStaticos.nombreFotoMostrar;

        //* RETROFIT
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<ResponseBody> call = service.dowLoadFotoFromServer(nombreFoto);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                /*
                if (response.code() == 401){  // El token ha expirado
                    ActivityCerarSesion activCerrarSess = new ActivityCerarSesion();
                    activCerrarSess.cleanSesionDeUsuario(getContext());
                    Toast.makeText(getContext(), "The session has ended", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(root).navigate(R.id.fragmentLogin);
                }  */

                if(response.isSuccessful()){
                    InputStream impStream = response.body().byteStream();
                    Bitmap bitmapImg = BitmapFactory.decodeStream(impStream);
                    tivVerFoto.setImageBitmap(bitmapImg);

                } else {
                    Log.d("Connection: ", "Fail to get photo...");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());
            }
        });
    }


}