package com.mantprev.mantprevproaces2.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.mantprev.mantprevproaces2.R;

import java.util.ArrayList;

public class GridViewFotosAdapter extends BaseAdapter {

    Context context;
    ArrayList<Uri> listaImgsUri;

    public GridViewFotosAdapter(Context context, ArrayList<Uri> listaImgsUri) {
        this.context = context;
        this.listaImgsUri = listaImgsUri;
    }

    @Override
    public int getCount() {
        return listaImgsUri.size();
    }

    @Override
    public Object getItem(int position) {
        return listaImgsUri.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_gv_imagenes, null);
        }

        ImageView ivImagen = convertView.findViewById(R.id.ivImagen);
        ImageButton ibtnEliminar = convertView.findViewById(R.id.imgBtnEliminar);

        ivImagen.setImageURI(listaImgsUri.get(position));
        ivImagen.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ivImagen.setLayoutParams(new ViewGroup.LayoutParams(650,350));

        ibtnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaImgsUri.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

}
