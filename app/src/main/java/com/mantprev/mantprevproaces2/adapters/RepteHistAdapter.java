package com.mantprev.mantprevproaces2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mantprev.mantprevproaces2.ModelosDTO1.OrdenesTrabajo;
import com.mantprev.mantprevproaces2.R;

import java.util.ArrayList;

public class RepteHistAdapter extends RecyclerView.Adapter<RepteHistAdapter.ViewHolderRH> implements View.OnClickListener{


    private final ArrayList<OrdenesTrabajo> listaOrdenesTrab;
    private View.OnClickListener clickListener;

    public RepteHistAdapter(ArrayList<OrdenesTrabajo> listaOrdenesTrab) {
        this.listaOrdenesTrab = listaOrdenesTrab;
    }

    @NonNull
    @Override
    public ViewHolderRH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lrf_repte_hist, null, false);
        view.setOnClickListener(this);   //Para evento onClikListener

        return new ViewHolderRH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRH holder, int position) {
        holder.cargarReporteHistorico(listaOrdenesTrab.get(position));
    }

    @Override
    public int getItemCount() {
        return listaOrdenesTrab.size();
    }

    //Metodo creado para que escuche el evento clik
    public void setOnClickListener (View.OnClickListener listener){
        this.clickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (clickListener != null){
            clickListener.onClick(view);
        }
    }

    public class ViewHolderRH extends RecyclerView.ViewHolder {

        public TextView tvNumOT, tvTrabSolicit, tvNombrEquip, tvFechaIngrOT, tvPersSolicit, tvFechaEjecuc, tvRepteHistor;

        public ViewHolderRH(@NonNull View itemView) {
            super(itemView);

            tvNumOT = (TextView) itemView.findViewById(R.id.tvNumOT);
            tvTrabSolicit = (TextView) itemView.findViewById(R.id.tvTrabSolic);
            tvNombrEquip  = (TextView) itemView.findViewById(R.id.tvNombreEquipo);
            tvFechaIngrOT = (TextView) itemView.findViewById(R.id.tvFechaOT);
            tvPersSolicit = (TextView) itemView.findViewById(R.id.tvPersSolicito);
            tvFechaEjecuc = (TextView) itemView.findViewById(R.id.tvFechaEjeuc);
            tvRepteHistor = (TextView) itemView.findViewById(R.id.tvRepteHistor);
        }


        private void cargarReporteHistorico (OrdenesTrabajo ordenTrab){
        /**************************************************************/
            String solicitant = ordenTrab.getNombSolicitante();
            String informOT = ordenTrab.getNumeroOT() + " ( --> " + solicitant +")";

            tvNumOT.setText(informOT); //
            tvTrabSolicit.setText(ordenTrab.getTrabajoSolicit());
            tvNombrEquip.setText(ordenTrab.getNombEquipo());
            tvFechaIngrOT.setText(ordenTrab.getFechaIngresoOT());
            tvPersSolicit.setText(ordenTrab.getNombSolicitante());
            tvFechaEjecuc.setText(ordenTrab.getFechaEjecuc());
            tvRepteHistor.setText(ordenTrab.getRepteHistor());
        }
    }





}
