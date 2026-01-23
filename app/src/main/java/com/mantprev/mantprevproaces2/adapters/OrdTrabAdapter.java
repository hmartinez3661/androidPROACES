package com.mantprev.mantprevproaces2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mantprev.mantprevproaces2.ModelosDTO1.OrdenesTrabajo;
import com.mantprev.mantprevproaces2.R;
import com.mantprev.mantprevproaces2.utilities.MetodosStaticos;

import java.util.ArrayList;

public class OrdTrabAdapter extends RecyclerView.Adapter <OrdTrabAdapter.ViewHolderOTs> implements View.OnClickListener {

    private final ArrayList<OrdenesTrabajo> listaOrdenesTrab;
    private View.OnClickListener clickListener;

    //Constructor
    public OrdTrabAdapter(ArrayList<OrdenesTrabajo> listaOrdenesTrab, Context context) {
        this.listaOrdenesTrab = listaOrdenesTrab;
    }


    @NonNull
    @Override
    public ViewHolderOTs onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lrf_ord_trab1, null, false);

        //Para evento onClikListener
        view.setOnClickListener(this);
        return new ViewHolderOTs(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderOTs holder, int position) {
        holder.cargarInfoOrdenTrabajo(listaOrdenesTrab.get(position));
    }


    @Override
    public int getItemCount() {
        return listaOrdenesTrab.size();
    }


    //Metodo creado para que escuche el evento clik
    public void setOnClickListener (View.OnClickListener listener){
        this.clickListener = listener;
    }


    @Override  //Provieve de la implementación de la Interfaz View.OnClickListener
    public void onClick(View view) {
        if (clickListener != null){
            clickListener.onClick(view);
        }
    }


    public class ViewHolderOTs extends RecyclerView.ViewHolder{

        public TextView tvNumOT;
        public TextView tvNombSolic;
        public TextView tvFechaOT;
        public TextView tvTrabSolicDetalle;
        public TextView tvNombreEquipo;
        public TextView tvEjecutor;
        public TextView tvPrioridad;
        public TextView tvEstatuaAct;

        public ViewHolderOTs(@NonNull View itemView) {
            super(itemView);

            tvNumOT = (TextView) itemView.findViewById(R.id.tvNumRut);
            tvNombSolic = (TextView) itemView.findViewById(R.id.tvNombSolic);
            tvFechaOT = (TextView) itemView.findViewById(R.id.tvFechaOT);
            tvTrabSolicDetalle = (TextView) itemView.findViewById(R.id.tvTrabRut);
            tvNombreEquipo = (TextView) itemView.findViewById(R.id.tvNombreEquipo);
            tvEjecutor = (TextView) itemView.findViewById(R.id.tvEjecutor);
            tvPrioridad = (TextView) itemView.findViewById(R.id.tvPrioridad);
            tvEstatuaAct = (TextView) itemView.findViewById(R.id.tvEstatuaAct);
        }

        private void cargarInfoOrdenTrabajo (OrdenesTrabajo ordenTrab){
        /**************************************************************/
            String numeroOT = ordenTrab.getNumeroOT();
            String informOT = "(--> " + ordenTrab.getNombSolicitante() + ")";
            String formtedStrDate = MetodosStaticos.getStrDateFormated(ordenTrab.getFechaIngresoOT());

            tvNumOT.setText(numeroOT); //
            tvNombSolic.setText(informOT);
            tvFechaOT.setText(formtedStrDate);
            tvTrabSolicDetalle.setText(ordenTrab.getTrabajoSolicit());
            tvNombreEquipo.setText(ordenTrab.getNombEquipo()); //nombreEquipo
            tvEjecutor.setText(ordenTrab.getPersEjecutor());
            tvPrioridad.setText(ordenTrab.getPrioridadOT());
            tvEstatuaAct.setText(ordenTrab.getEstatusOT());
        }
    }






}
