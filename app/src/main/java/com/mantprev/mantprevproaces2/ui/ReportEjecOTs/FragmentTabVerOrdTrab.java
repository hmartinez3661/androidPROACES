package com.mantprev.mantprevproaces2.ui.ReportEjecOTs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.mantprev.mantprevproaces2.ModelosDTO2.OrdenesTrabDTO_2;
import com.mantprev.mantprevproaces2.R;
import com.mantprev.mantprevproaces2.retrofit.DataServices_Intf;
import com.mantprev.mantprevproaces2.retrofit.Retrofit_Instance;
//import com.mantprev.mantprevproaces2.ui.otrasUI.ActivityCerarSesion;
import com.mantprev.mantprevproaces2.utilities.MetodosStaticos;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;


public class FragmentTabVerOrdTrab extends Fragment {


    private TextView tvNumOT;
    private String numOT, persEjecut, prioridadOT, trabSolicit, nombrSolic, fechaIngrOT, horaIngrOT;
    private String estatusOT, nombrEquip, clasificTrabj, indicPrevias, persReviso, horaRevOT, fechaRevOT;
    private EditText etNombreEquip, etDescripTrab, etNumPersEstim, etNumHrsEstim, etIndicPrev;
    private Spinner spinnerEjecut, spinnerPriorid, spnClasificOT, spnStatusOT;
    private TextView tvIndicFotos, tvPersReviso2, tvDateTime;
    private int cantFotosAnex, persEstim;
    private Double tiempoEstim;
    private ProgressBar progressBar;
    private View root;


    public FragmentTabVerOrdTrab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String idOT = MetodosStaticos.idOT;
        root = inflater.inflate(R.layout.fragment_tab_ver_ord_trab, container, false);

        TextView tvIdOT = (TextView) root.findViewById(R.id.tvIdOT);
        tvIdOT.setText(idOT);  //Text view hidden

        tvNumOT = (TextView) root.findViewById(R.id.tvNumOT);
        etNombreEquip = (EditText) root.findViewById(R.id.etNombreEquip);
        etDescripTrab = (EditText) root.findViewById(R.id.etTrabSolic);
        spinnerEjecut = (Spinner) root.findViewById(R.id.spnEjecutores);
        spinnerPriorid = (Spinner) root.findViewById(R.id.spnPriorids);
        spnClasificOT = (Spinner) root.findViewById(R.id.spnClasificOT);
        spnStatusOT = (Spinner) root.findViewById(R.id.spnStatusOT);
        etNumPersEstim = (EditText) root.findViewById(R.id.etNumPersEstim);
        etNumHrsEstim = (EditText) root.findViewById(R.id.etNumHrsEstim);
        etIndicPrev = (EditText) root.findViewById(R.id.etIndicPrev);
        tvIndicFotos = (TextView) root.findViewById(R.id.tvIndicFotos);
        tvPersReviso2 = (TextView) root.findViewById(R.id.tvPersReviso2);
        tvDateTime = (TextView) root.findViewById(R.id.tvDateTime);
        progressBar = (ProgressBar) root.findViewById(R.id.progresBar);

        tvIndicFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cantFotosAnex > 0){
                    MetodosStaticos.fotosDeCierroOT = "No"; //Para que busque solo fotos anexadas en la generacion OT
                    Navigation.findNavController(root).navigate(R.id.fragmentImgsOT);

                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.msgNoatachedFotos), Toast.LENGTH_SHORT).show();
                }
            }
        });

        getAndShowOrdenTrabajo(idOT);
        return root;
    }


    private void getAndShowOrdenTrabajo(String idOT){
    /***********************************************/
        progressBar.setVisibility(View.VISIBLE);
        int idOrdTrab = Integer.parseInt(idOT);

        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<OrdenesTrabDTO_2> call = service.getOrdenDeTrabById(idOrdTrab);  //ordsTrab/getById/{idOT}

        call.enqueue(new Callback<OrdenesTrabDTO_2>() {

            @Override
            public void onResponse(Call<OrdenesTrabDTO_2> call, retrofit2.Response<OrdenesTrabDTO_2> response) {
                /*
                if (response.code() == 401){  // El token ha expirado
                    ActivityCerarSesion activCerrarSess = new ActivityCerarSesion();
                    activCerrarSess.cleanSesionDeUsuario(getContext());
                    Toast.makeText(getContext(), "The session has ended", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(root).navigate(R.id.fragmentLogin);
                } */

                if(response.isSuccessful() && response.body() != null){
                    OrdenesTrabDTO_2 ordTrab = response.body();

                    Date dateIngrOT = ordTrab.getFechaIngresoOT();
                    String strDateIngrOT = MetodosStaticos.getStrDateFormated(dateIngrOT);

                    Date dateReviOT = ordTrab.getFechaAutorizado();
                    String strDateReviOT = "";
                    if(dateReviOT != null){
                        strDateReviOT = MetodosStaticos.getStrDateFormated(dateReviOT);
                    }

                    numOT = ordTrab.getNumeroOT();
                    persEjecut = ordTrab.getPersEjecutor();
                    prioridadOT = ordTrab.getPrioridadOT();
                    trabSolicit = ordTrab.getTrabajoSolicit();
                    nombrSolic = ordTrab.getNombSolicitante();
                    fechaIngrOT = strDateIngrOT;
                    horaIngrOT = ordTrab.getHoraIngresoOT();
                    estatusOT = ordTrab.getEstatusOT();
                    nombrEquip = ordTrab.getNombEquipo();
                    cantFotosAnex = ordTrab.getCantFotosAnex();
                    clasificTrabj = ordTrab.getClasificTrabajo();
                    persEstim = ordTrab.getPersonalEstim();
                    tiempoEstim = ordTrab.getTiempoEstim();
                    indicPrevias = ordTrab.getIndicacPreviasTrab();
                    persReviso = ordTrab.getNombreAutorizo();
                    fechaRevOT = strDateReviOT;
                    horaRevOT = ordTrab.getHoraAutorizado();

                    //Para ver Reporte de Ejecucion
                    int idRepteEjec = ordTrab.getIdRpteEjecOT();
                    MetodosStaticos.numOT = ordTrab.getNumeroOT() + "";

                    if (idRepteEjec > 0) {
                        MetodosStaticos.idRepteEjecOT = idRepteEjec;
                    } else {
                        MetodosStaticos.idRepteEjecOT = 0;
                    }

                    String dateTimeRev = "(" + strDateReviOT + " -- " + horaRevOT + ")";
                    String persEstimSt = Integer.toString(persEstim);
                    String tmpoEstimSt = Double.toString(tiempoEstim);

                    tvNumOT.setText(numOT);
                    etNombreEquip.setText(nombrEquip);
                    etDescripTrab.setText(trabSolicit);
                    etNumPersEstim.setText(persEstimSt);   //persEstim
                    etNumHrsEstim.setText(tmpoEstimSt);    //tiempoEstim
                    etIndicPrev.setText(indicPrevias);
                    tvPersReviso2.setText(persReviso);
                    tvDateTime.setText(dateTimeRev);

                    String tvIndicFotosStr = tvIndicFotos.getText() + " (" + cantFotosAnex + ")";
                    tvIndicFotos.setText(tvIndicFotosStr);

                    //COLOCA LOS DATOS EN LOS SPINNERS
                    ArrayList<String> ejecutorestArray = new ArrayList<>();
                    ArrayList<String> clasificOTarray = new ArrayList<>();
                    ArrayList<String> prioridadesArray = new ArrayList<>();
                    ArrayList<String> estatusOTsArray = new ArrayList<>();

                    //Si no ha sido revisada no tiene Clasificación todavía
                    if(clasificTrabj == null){
                        clasificTrabj = "---";
                    }

                    ejecutorestArray.add(persEjecut);
                    clasificOTarray.add(clasificTrabj);
                    prioridadesArray.add(prioridadOT);
                    estatusOTsArray.add(estatusOT);

                    //Adapter Spinner Ejecutores
                    ArrayAdapter<CharSequence> adapterEject = new ArrayAdapter(getContext(), R.layout.zspinners_items, ejecutorestArray); //
                    spinnerEjecut.setAdapter(adapterEject);

                    //Adapter Spinner ClasificOTs
                    ArrayAdapter<CharSequence> adapterClasificOTs = new ArrayAdapter(getContext(), R.layout.zspinners_items, clasificOTarray); //
                    spnClasificOT.setAdapter(adapterClasificOTs);

                    //Adapter Spinner Prioridades
                    ArrayAdapter<CharSequence> adapterPrioridades = new ArrayAdapter(getContext(), R.layout.zspinners_items, prioridadesArray); //R.layout.zspinner_text
                    spinnerPriorid.setAdapter(adapterPrioridades);

                    //Adapter Spinner statusOTs
                    ArrayAdapter<CharSequence> adapterStatusOTs = new ArrayAdapter(getContext(), R.layout.zspinners_items, estatusOTsArray); //R.layout.zspinner_text
                    spnStatusOT.setAdapter(adapterStatusOTs);

                } else {
                    Toast.makeText(getContext(), "La Orden de Trabajo es NULL", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OrdenesTrabDTO_2> call, Throwable throwable) {
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());
                Toast.makeText(getContext(), "FALLO EN OBTENER DATOS DE LA OT", Toast.LENGTH_LONG).show();
            }
        });

        progressBar.setVisibility(View.GONE);
    }







}