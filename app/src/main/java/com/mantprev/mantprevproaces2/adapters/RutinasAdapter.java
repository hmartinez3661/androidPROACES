package com.mantprev.mantprevproaces2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mantprev.mantprevproaces2.ModelosDTO1.RustEquiposDTO;
import com.mantprev.mantprevproaces2.R;

import java.util.List;

public class RutinasAdapter extends RecyclerView.Adapter <RutinasAdapter.ViewHolderRuts>  implements View.OnClickListener {

    private final List<RustEquiposDTO> listaRutsEqpips;
    private View.OnClickListener clickListener;

    //Constructor
    public RutinasAdapter (List<RustEquiposDTO> listaRutsEqpips, Context context){
        this.listaRutsEqpips = listaRutsEqpips;
    }


    @Override
    public void onClick(View view) {
        if (clickListener != null){
            clickListener.onClick(view);
        }
    }

    @NonNull
    @Override
    public ViewHolderRuts onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lrf_rutinas, null, false);

        //Para evento onClikListener
        view.setOnClickListener(this);
        return new ViewHolderRuts(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRuts holder, int position) {
        holder.cargarInfoDeRutina(listaRutsEqpips.get(position));
    }

    @Override
    public int getItemCount() {
        return listaRutsEqpips.size();
    }


    //Metodo creado para que escuche el evento clik
    public void setOnClickListener (View.OnClickListener listener){
        this.clickListener = listener;
    }

    public static class ViewHolderRuts extends RecyclerView.ViewHolder{

        public TextView tvNumRut, tvSemanaRut, tvTrabajoRut, tvNombrEquip, tvFrecuencia, tvEjecutor, tvEstatuaAct;

        public ViewHolderRuts (@NonNull View itemView){
            super(itemView);

            tvNumRut = (TextView) itemView.findViewById(R.id.tvNumOTInfo);
            tvSemanaRut = (TextView) itemView.findViewById(R.id.tvSemRut);
            tvTrabajoRut = (TextView) itemView.findViewById(R.id.tvTrabRut);
            tvNombrEquip = (TextView) itemView.findViewById(R.id.tvNombreEquipo);
            tvFrecuencia = (TextView) itemView.findViewById(R.id.tvFrecuencia);
            tvEjecutor = (TextView) itemView.findViewById(R.id.tvEjecutor);
            tvEstatuaAct = (TextView) itemView.findViewById(R.id.tvEstatuaAct);
        }

        private void cargarInfoDeRutina(RustEquiposDTO rutEquipo){
        /********************************************************/
            int idRepteEjec = rutEquipo.getIdRepteEjec(); //Trae el id repte ejecuc. de la Rut
            String status = "";
            if(idRepteEjec > 0){
                status = "Reportada";
            } else {
                status = "Pendiente ejecución";
            }

            String numRut = rutEquipo.getNumeroRut();  //Este dato trae el numero de la turina
            String semRut = rutEquipo.getSemProgr();
            String trabajo = rutEquipo.getTrabajoRut(); //Trae el nombre de la rutina
            String nombEqu = rutEquipo.getNombreEquip();
            String frecuen = rutEquipo.getFrecuAsign();
            String Ejecutor = rutEquipo.getEjecutor();

            tvNumRut.setText(numRut);
            tvSemanaRut.setText(semRut);
            tvTrabajoRut.setText(trabajo);
            tvNombrEquip.setText(nombEqu);
            tvFrecuencia.setText(frecuen);
            tvEjecutor.setText(Ejecutor);
            tvEstatuaAct.setText(status);

            if(idRepteEjec == 0){
                tvEstatuaAct.setTextColor(Color.RED);
            }
        }
    }



}
