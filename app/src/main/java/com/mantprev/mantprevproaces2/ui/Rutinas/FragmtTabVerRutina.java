package com.mantprev.mantprevproaces2.ui.Rutinas;

import static android.view.View.GONE;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mantprev.mantprevproaces2.ModelosDTO1.RustEquiposDTO;
import com.mantprev.mantprevproaces2.R;
import com.mantprev.mantprevproaces2.retrofit.DataServices_Intf;
import com.mantprev.mantprevproaces2.retrofit.Retrofit_Instance;
import com.mantprev.mantprevproaces2.utilities.MetodosStaticos;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmtTabVerRutina extends Fragment {


    private EditText etNombreEquip, etTrabajoRut, etMedidadSegur, etCoodinPrev, etPreparPrev;
    private TextView tvNumRut, tvEjecutoRutDt, tvFrecucRutDt, tvtPersEstimDt, tvTpoEstimDt, tvEstEquip;
    private ProgressBar progressBar;
    private View root;


    public FragmtTabVerRutina() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragmt_tab_ver_rutina, container, false);

        tvNumRut = (TextView) root.findViewById(R.id.tvNumRut);
        etNombreEquip = (EditText) root.findViewById(R.id.etNombreEquip);
        etTrabajoRut = (EditText) root.findViewById(R.id.etTrabajoRut);
        tvEjecutoRutDt = (TextView) root.findViewById(R.id.tvEjecutoRutDt);
        tvFrecucRutDt = (TextView) root.findViewById(R.id.tvFrecucRutDt);
        tvtPersEstimDt = (TextView) root.findViewById(R.id.tvtPersEstimDt);
        tvTpoEstimDt = (TextView) root.findViewById(R.id.tvTpoEstimDt);
        tvEstEquip = (TextView) root.findViewById(R.id.tvEstEquip);
        progressBar = (ProgressBar) root.findViewById(R.id.progresBar);
        etMedidadSegur = (EditText) root.findViewById(R.id.etMedidadSegur);
        etCoodinPrev = (EditText) root.findViewById(R.id.etCoodinPrev);
        etPreparPrev = (EditText) root.findViewById(R.id.etPrepPrev);

        progressBar.setVisibility(GONE);
        mostrarRutinaSelectec();

        return root;
    }

    private void mostrarRutinaSelectec() {
    /************************************/
        int idRutEquip = MetodosStaticos.idRutEquip;
        progressBar.setVisibility(View.VISIBLE);

        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<RustEquiposDTO> call = service.getRutinaSelected(idRutEquip);

        call.enqueue(new Callback<RustEquiposDTO>() {
            @Override
            public void onResponse(Call<RustEquiposDTO> call, Response<RustEquiposDTO> response) {

                if(response.isSuccessful() && response.body() != null){
                    RustEquiposDTO rutsEquipo = response.body();

                    String numeroRutin = rutsEquipo.getNumeroRut();
                    String nombreEquip = rutsEquipo.getNombreEquip();
                    String trabajoRut  = rutsEquipo.getTrabajoRut();
                    String ejecutorRut = rutsEquipo.getEjecutor();
                    String frecuencia  = rutsEquipo.getFrecuAsign();
                    String persEstimad = rutsEquipo.getPersonEstim() + " ténico(s) mantto.";
                    String tiempoEstim = rutsEquipo.getTiempoEstim() + " hrs.";
                    String estadoEquip = rutsEquipo.getEstadoEquip();
                    String medidasSegu = rutsEquipo.getMedidasSegur();
                    String coordinacPr = rutsEquipo.getCoordinPrev();
                    String preparacRpv = rutsEquipo.getPreparacPrev();

                    tvNumRut.setText(numeroRutin);
                    etNombreEquip.setText(nombreEquip);
                    etTrabajoRut.setText(trabajoRut);
                    tvEjecutoRutDt.setText(ejecutorRut);
                    tvFrecucRutDt.setText(frecuencia);
                    tvtPersEstimDt.setText(persEstimad);
                    tvTpoEstimDt.setText(tiempoEstim);
                    tvEstEquip.setText(estadoEquip);
                    etMedidadSegur.setText(medidasSegu);
                    etCoodinPrev.setText(coordinacPr);
                    etPreparPrev.setText(preparacRpv);

                    MetodosStaticos.nombreEquipo = nombreEquip;

                } else {
                    Toast.makeText(getContext(), "No hay rutina que mostrar....", Toast.LENGTH_LONG).show();
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<RustEquiposDTO> call, Throwable throwable) {
                Toast.makeText(getContext(), "No hay rutina que mostrar !!!", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }









}