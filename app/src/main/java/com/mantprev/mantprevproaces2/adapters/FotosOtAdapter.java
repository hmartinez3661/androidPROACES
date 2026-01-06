package com.mantprev.mantprevproaces2.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mantprev.mantprevproaces2.R;
import com.mantprev.mantprevproaces2.interfases.VerFotosListener;
import com.mantprev.mantprevproaces2.retrofit.DataServices_Intf;
import com.mantprev.mantprevproaces2.retrofit.Retrofit_Instance;

import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class FotosOtAdapter extends RecyclerView.Adapter <FotosOtAdapter.ViewHolderFotosOT>{

    ArrayList <String> listaNombresFotosOT;
    Context context;
    private final VerFotosListener verFotosListener;


    //CONSTRUCTOR
    public FotosOtAdapter(ArrayList<String> listaNombresFotosOT, Context context, VerFotosListener verFotosListener) { //
        this.listaNombresFotosOT = listaNombresFotosOT;
        this.context = context;
        this.verFotosListener = verFotosListener;
    }


    @NonNull
    @Override
    public ViewHolderFotosOT onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lrf_ivfoto_ot, null, false);
        return new ViewHolderFotosOT(view, verFotosListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderFotosOT holder, int position) {
        holder.descargarImagenWebService(listaNombresFotosOT.get(position));
    }


    @Override
    public int getItemCount() {
        return listaNombresFotosOT.size();
    }


    public static class ViewHolderFotosOT extends RecyclerView.ViewHolder{
        ImageView ivFotoOT;

        public ViewHolderFotosOT(@NonNull View itemView, VerFotosListener verFotosListener) {
            super(itemView);

            ivFotoOT = (ImageView) itemView.findViewById(R.id.ivFotoOT);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (verFotosListener != null){

                        int pos = getBindingAdapterPosition(); // getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            verFotosListener.onFotoClick(pos);
                        }
                    }
                }
            });
        }


        private void descargarImagenWebService(String nombreFoto){
        /*********************************************************/
            //* RETROFIT
            DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
            Call<ResponseBody> call = service.dowLoadFotoFromServer(nombreFoto);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    if(response.isSuccessful()){

                        InputStream impStream = response.body().byteStream();
                        Bitmap bitmapImg = BitmapFactory.decodeStream(impStream);
                        ivFotoOT.setImageBitmap(bitmapImg);

                    } else {
                        Log.d("Connection: ", "Fail to get photos...");
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





}
