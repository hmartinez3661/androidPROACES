package com.mantprev.mantprevproaces2.ui.RevAutorizOTs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.mantprev.mantprevproaces2.ModelosDTO1.ConfigSpinners;
import com.mantprev.mantprevproaces2.ModelosDTO2.OrdenTrabRevis;
import com.mantprev.mantprevproaces2.ModelosDTO2.OrdenesTrabDTO_2;
import com.mantprev.mantprevproaces2.R;
import com.mantprev.mantprevproaces2.retrofit.DataServices_Intf;
import com.mantprev.mantprevproaces2.retrofit.Retrofit_Instance;
//import com.mantprev.mantprevproaces2.ui.otrasUI.ActivityCerarSesion;
import com.mantprev.mantprevproaces2.utilities.MetodosStaticos;
import com.mantprev.mantprevproaces2.utilities.StaticConfig;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class FragmentCheckupOT extends Fragment {

    private String idOT;
    private TextView tvNumOT;
    private Button btnSaveRevisOT;
    private String numOT, persEjecut, prioridadOT, trabSolicit, nombrSolic, fechaIngrOT, horaIngrOT, estatusOT, nombrEquip;
    private EditText etNombreEquip, etDescripTrab, etNumPersEstim, etNumHrsEstim, etIndicPrev, etExplicRechazo;
    private Spinner spinnerEjecut, spinnerPriorid, spnClasificOT, spnStatusOT;
    private TextView tvIndicFotos;
    private int cantFotosAnex;
    private View root;
    private ProgressBar progressBar;

    public FragmentCheckupOT() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //idOT = getArguments().getString("idOT");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        idOT = MetodosStaticos.idOT;
        root = inflater.inflate(R.layout.fragment_checkup_ot, container, false);  // Inflate the layout for this fragment

        TextView tvIdOT = (TextView) root.findViewById(R.id.tvIdRepteEjec);
        tvIdOT.setText(idOT);  //Text view hidden

        tvNumOT = (TextView) root.findViewById(R.id.tvNumRut);
        etNombreEquip = (EditText) root.findViewById(R.id.etNombreEquip);
        etDescripTrab = (EditText) root.findViewById(R.id.etTrabajoRut);
        spinnerEjecut = (Spinner) root.findViewById(R.id.tvEjecutoRutDt);
        spinnerPriorid = (Spinner) root.findViewById(R.id.tvFrecucRutDt);
        spnClasificOT = (Spinner) root.findViewById(R.id.spnClasificOT);
        spnStatusOT = (Spinner) root.findViewById(R.id.spnStatusOT);
        etNumPersEstim = (EditText) root.findViewById(R.id.etNumPersEstim);
        etNumHrsEstim = (EditText) root.findViewById(R.id.etNumHrsEstim);
        etIndicPrev = (EditText) root.findViewById(R.id.etMedidadSegur);
        etExplicRechazo = (EditText) root.findViewById(R.id.etExplicRechazo);
        tvIndicFotos = (TextView) root.findViewById(R.id.tvIndicFotos);
        btnSaveRevisOT = (Button) root.findViewById(R.id.btnSaveRevisOT);

        progressBar = (ProgressBar) root.findViewById(R.id.progresBar);

        tvIndicFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cantFotosAnex > 0){
                    Navigation.findNavController(root).navigate(R.id.fragmentImages);
                } else {
                    Toast.makeText(getContext(), "No attached photos to show", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSaveRevisOT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarRevisionOT();
            }
        });

        getAndShowOrdenTrabajo(idOT);
        poblarSpinners();
        return root;
    }


    private void getAndShowOrdenTrabajo(String idOT){
    /***********************************************/
        progressBar.setVisibility(View.VISIBLE);
        int idOrdTrab = Integer.parseInt(idOT);

        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<OrdenesTrabDTO_2> call = service.getOrdenDeTrabById(idOrdTrab);      // "ordsTrab/getById/"

        call.enqueue(new Callback<OrdenesTrabDTO_2>() {

            @Override
            public void onResponse(@NonNull Call<OrdenesTrabDTO_2> call, @NonNull retrofit2.Response<OrdenesTrabDTO_2> response) {
                /*
                if (response.code() == 401){  // El token ha expirado
                    ActivityCerarSesion activCerrarSess = new ActivityCerarSesion();
                    activCerrarSess.cleanSesionDeUsuario(getContext());
                    Toast.makeText(getContext(), "The session has ended", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(root).navigate(R.id.fragmentLogin);
                }  */

                if(response.isSuccessful() && response.body() != null){
                    OrdenesTrabDTO_2 ordenTrab = response.body();

                    numOT = ordenTrab.getNumeroOT();
                    persEjecut  = ordenTrab.getPersEjecutor();
                    prioridadOT = ordenTrab.getPrioridadOT();
                    trabSolicit = ordenTrab.getTrabajoSolicit();
                    nombrSolic  = ordenTrab.getNombSolicitante();
                    fechaIngrOT = ordenTrab.getFechaIngresoOT().toString();
                    horaIngrOT  = ordenTrab.getHoraIngresoOT();
                    estatusOT   = ordenTrab.getEstatusOT();
                    nombrEquip  = ordenTrab.getNombEquipo();
                    cantFotosAnex = ordenTrab.getCantFotosAnex();
                    String persEsti = Integer.toString(ordenTrab.getPersonalEstim());
                    String tmpoEsti = Double.toString(ordenTrab.getTiempoEstim());
                    String indicFts = tvIndicFotos.getText().toString() + " (" + cantFotosAnex + ")";

                    tvNumOT.setText(numOT);
                    etNombreEquip.setText(nombrEquip);
                    etDescripTrab.setText(trabSolicit);
                    etNumPersEstim.setText(persEsti);
                    etNumHrsEstim.setText(tmpoEsti);
                    tvIndicFotos.setText(indicFts);

                } else {
                    Toast.makeText(getContext(), "No se pudo cargar OT para Autorización", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OrdenesTrabDTO_2> call, Throwable throwable) {
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());
                Toast.makeText(getContext(), "FALLO AL CARGAR OT", Toast.LENGTH_LONG).show();
            }
        });

        progressBar.setVisibility(View.GONE);
    }


    private void poblarSpinners(){
    /****************************/
        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<List<ConfigSpinners>> call = service.getConfiguracSpinner();  //"configSpn/getConf"

        call.enqueue(new Callback<List<ConfigSpinners>>() {

            final ArrayList<ConfigSpinners> listConfSpinner = new ArrayList<>();
            final ArrayList<String> ejecutorestArray = new ArrayList<>();
            final ArrayList<String> clasificOTarray = new ArrayList<>();
            final ArrayList<String> prioridadesArray = new ArrayList<>();
            final ArrayList<String> estatusOTsArray = new ArrayList<>();

            @Override
            public void onResponse(Call<List<ConfigSpinners>> call, retrofit2.Response<List<ConfigSpinners>> response) {

                if(response.isSuccessful() && response.body() != null){

                    listConfSpinner.addAll(response.body());
                    int listaConfigSpinnSize = listConfSpinner.size();

                    for (int i=0; i< listaConfigSpinnSize; i++) {

                        ConfigSpinners configSpnn = listConfSpinner.get(i);

                        String ejecutor  = configSpnn.getEjecutoresOTs();
                        String clasifTrb = configSpnn.getClasificTrabOTs();
                        String prioridad = configSpnn.getPrioridTrabOTs();
                        String statusOTs = configSpnn.getEstatusOTs();

                        if (ejecutor != null){
                            if (!ejecutor.isEmpty()){
                                ejecutorestArray.add(ejecutor);
                            }
                        }
                        if (clasifTrb != null){
                            if (!clasifTrb.isEmpty()){
                                clasificOTarray.add(clasifTrb);
                            }
                        }
                        if (prioridad != null){
                            if (!prioridad.isEmpty()){
                                prioridadesArray.add(prioridad);
                            }
                        }
                        if (statusOTs != null){
                            if (!statusOTs.isEmpty()){
                                estatusOTsArray.add(statusOTs);
                            }
                        }
                    }

                    ArrayList<ArrayList> arrayArraysItemsSpiners = new ArrayList<>();
                    arrayArraysItemsSpiners.add(ejecutorestArray);
                    arrayArraysItemsSpiners.add(clasificOTarray);
                    arrayArraysItemsSpiners.add(prioridadesArray);
                    arrayArraysItemsSpiners.add(estatusOTsArray);

                    setItemsDeSpiner(arrayArraysItemsSpiners);
                    progressBar.setVisibility(View.GONE);

                } else{
                    Toast.makeText(getContext(), "Fallo en cargar config. SPINNERS", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ConfigSpinners>> call, Throwable throwable) {
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());
                Toast.makeText(getContext(), "FALLO EN CARGAR CONFIG SPINNERS", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void setItemsDeSpiner(ArrayList<ArrayList> arrayArraysItemsSpiners){
    /**********************************************************************/
        ArrayList<String> listaEjecutArray = arrayArraysItemsSpiners.get(0);
        ArrayList<String> listaClasifOTsArray = arrayArraysItemsSpiners.get(1);
        ArrayList<String> listaPrioridadesArray = arrayArraysItemsSpiners.get(2);
        ArrayList<String> listaStatusOTsArray = arrayArraysItemsSpiners.get(3);

        //Se remueven los items no utilies aqui
        listaEjecutArray.remove(0);      //Se remueve el "---"
        listaPrioridadesArray.remove(0); //Se remueve el "---"
        listaClasifOTsArray.remove(0);   //Se remueve el "---"

        listaStatusOTsArray.remove(0);   //Se remueve el "---"
        listaStatusOTsArray.remove(0);   //Se remueve el "No Autorizada"
        listaStatusOTsArray.remove(2);   //Se remueve el "Cerrada"

        //Adapter Spinner Ejecutores
        ArrayAdapter<CharSequence> adapterEject = new ArrayAdapter(getContext(), R.layout.zspinners_items, listaEjecutArray); //
        adapterEject.setDropDownViewResource(R.layout.zspinners_dropdown_items);
        spinnerEjecut.setAdapter(adapterEject);

        //Adapter Spinner ClasificOTs
        ArrayAdapter<CharSequence> adapterClasificOTs = new ArrayAdapter(getContext(), R.layout.zspinners_items, listaClasifOTsArray); //
        adapterClasificOTs.setDropDownViewResource(R.layout.zspinners_dropdown_items);
        spnClasificOT.setAdapter(adapterClasificOTs);

        //Adapter Spinner Prioridades
        ArrayAdapter<CharSequence> adapterPrioridades = new ArrayAdapter(getContext(), R.layout.zspinners_items, listaPrioridadesArray); //R.layout.zspinner_text
        adapterPrioridades.setDropDownViewResource(R.layout.zspinners_dropdown_items);
        spinnerPriorid.setAdapter(adapterPrioridades);

        //Adapter Spinner statusOTs
        ArrayAdapter<CharSequence> adapterStatusOTs = new ArrayAdapter(getContext(), R.layout.zspinners_items, listaStatusOTsArray); //R.layout.zspinner_text
        adapterStatusOTs.setDropDownViewResource(R.layout.zspinners_dropdown_items);
        spnStatusOT.setAdapter(adapterStatusOTs);

        //Se colocan el ejecutor y la prioridad seleccionadas en la OT
        String ejecutorA = persEjecut;
        String prioridad = prioridadOT;

        //Coloca en el Spinner el ejecutor seleccionado
        String[] ejecutores = new String[listaEjecutArray.size()];

        int listaEjectArraySize = listaEjecutArray.size();
        for (int i=0; i< listaEjectArraySize; i++){
            String ejector = listaEjecutArray.get(i);
            ejecutores[i] = ejector;
        }

        int ejecutoresLength = ejecutores.length;
        for (int i=0; i< ejecutoresLength; i++){
            String ejecutorX = ejecutores[i];
            if(ejecutorX.equals(ejecutorA)){
                spinnerEjecut.setSelection(i);
            }
        }

        //Coloca en el spinner la prioridad seleccionada
        int listaPrioridsArraySize = listaPrioridadesArray.size();
        for (int i=0; i< listaPrioridsArraySize; i++){
            String prioridadLista = listaPrioridadesArray.get(i);
            if(prioridadLista.equals(prioridad)){
                spinnerPriorid.setSelection(i);
            }
        }

        //Se asigna a los spinner el nuevo status y una clasificacion por default
        spnClasificOT.setSelection(1);
        spnStatusOT.setSelection(0);

        //cambiarStatusDeOT(idOT); //Revision automatica de OT
    }


    private void cambiarStatusDeOT(String idOT){  //Revision automatica de OT
    /******************************************/
        String privilegUser = StaticConfig.rolDeUsuario.substring(0, 3);

        //La OT queda Autorizada o Revisada si el que la ve es Admin o Superv de Mannto
        if(privilegUser.equals("ADM") || privilegUser.equals("SDM")){
            progressBar.setVisibility(View.VISIBLE);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String nombreEquipo = etNombreEquip.getText().toString();
            int idOrdTrab = Integer.parseInt(idOT);
            String nombreReviso = StaticConfig.numbRealUser;
            String newClasificOT = spnClasificOT.getSelectedItem().toString();  //"Averia";   //
            String newStatusDeOT = spnStatusOT.getSelectedItem().toString();    //"Revisada"; //
            String fechaRevision = dateFormat.format(new Date());
            String horaRevision = getHoraActual();

            OrdenTrabRevis dtosOrdTrab = new OrdenTrabRevis();
            dtosOrdTrab.setIdOrdTrab(idOrdTrab);
            dtosOrdTrab.setNombPersReviso(nombreReviso);
            dtosOrdTrab.setStatusDeOT(newStatusDeOT);
            dtosOrdTrab.setClasificJOB(newClasificOT);
            dtosOrdTrab.setFechaRevison(fechaRevision);
            dtosOrdTrab.setHoraRevison(horaRevision);

            /* RETROFIT */
            DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
            Call<String> call = service.guardarRevisAutomatOT(dtosOrdTrab);   //"ordsTrab/saveRevAutmOT/"

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {

                    if(response.body().equals("OK")){
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    throwable.printStackTrace();
                    Log.d("ErrorResponse: ", throwable.toString());
                    Toast.makeText(getContext(), "FALLO GUARDAR REVISION DE OT", Toast.LENGTH_LONG).show();
                }
            });

            progressBar.setVisibility(View.GONE);
        }
    }


    private void guardarRevisionOT(){
    /*******************************/
        progressBar.setVisibility(View.VISIBLE);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        int idOrdTrab = Integer.parseInt(idOT);
        String nombRevis = StaticConfig.numbRealUser; //.replaceAll(" ", "%");
        String fechRevis = dateFormat.format(new Date());
        String horaRevis = getHoraActual();
        String trabSolic = etDescripTrab.getText().toString().replaceAll(",", "\\$");
        String persEject = spinnerEjecut.getSelectedItem().toString();
        String prioridOT = spinnerPriorid.getSelectedItem().toString();
        String clasifJOB = spnClasificOT.getSelectedItem().toString();
        String estatusOT = spnStatusOT.getSelectedItem().toString();
        String nEstimTec = etNumPersEstim.getText().toString();
        String nEstimHrs = etNumHrsEstim.getText().toString();
        String indPrevTrab = etIndicPrev.getText().toString().replaceAll(",", "\\$");
        String explRechazo = etExplicRechazo.getText().toString().replaceAll(",", "\\$");

        //Verifica que se hayan ingresado los datos correctamente
        Boolean datosCorrectos = true;

        if (trabSolic.equals("")) {
            Toast.makeText(getContext(), "Please, enter the job required", Toast.LENGTH_LONG).show();
            datosCorrectos = false;
            progressBar.setVisibility(View.GONE);
        }

        int itemPosStatusOT = spnStatusOT.getSelectedItemPosition();
        if(itemPosStatusOT == 2 && explRechazo.equals("")) { //Si ha seleccionado Rechazar OT y no hay explicación
            Toast.makeText(getContext(), getResources().getString(R.string.mensajeRechazoOT), Toast.LENGTH_LONG).show();
            datosCorrectos = false;
            progressBar.setVisibility(View.GONE);
        }

        if (explRechazo.equals("")){
            explRechazo = "--";
        }
        if(indPrevTrab.equals("")){
            indPrevTrab = "--";
        }

        // SI DATOS ESTAN CORRECTOS PROCEDE A GUARDAR LA OT
        if (datosCorrectos == true){

            OrdenTrabRevis dtosOrdTrab = new OrdenTrabRevis();
            dtosOrdTrab.setIdOrdTrab(idOrdTrab);
            dtosOrdTrab.setNombPersReviso(nombRevis);
            dtosOrdTrab.setStatusDeOT(estatusOT);
            dtosOrdTrab.setClasificJOB(clasifJOB);
            dtosOrdTrab.setFechaRevison(fechRevis);
            dtosOrdTrab.setHoraRevison(horaRevis);
            dtosOrdTrab.setTrabajoSolicit(trabSolic);
            dtosOrdTrab.setPersEjecutor(persEject);
            dtosOrdTrab.setPrioridadOT(prioridOT);
            dtosOrdTrab.setNumEstimTecn(nEstimTec);
            dtosOrdTrab.setNumEstimHrs(nEstimHrs);
            dtosOrdTrab.setIndicPrevTrab(indPrevTrab);
            dtosOrdTrab.setExplicRechazo(explRechazo);

            /* RETROFIT */
            DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
            Call<String> call = service.gurdarRevisionOT(dtosOrdTrab);   //"ordsTrab/saveRevOT/"

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {

                    if(response.body().equals("OK")){
                        Toast.makeText(getContext(), getResources().getString(R.string.exitoSaveRevOT), Toast.LENGTH_SHORT).show();
                        //Regresa al listado de nuevas OTs
                        Navigation.findNavController(root).navigate(R.id.fragmentAutorizOTList);

                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string.falloSaveRevOT), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    throwable.printStackTrace();
                    Log.d("ErrorResponse: ", throwable.toString());
                    Toast.makeText(getContext(), "FALLO GUARDAR REVISION DE OT", Toast.LENGTH_LONG).show();
                }
            });

            progressBar.setVisibility(View.GONE);
        }
    }


    public String getHoraActual(){
    /****************************/
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String horaActual = dateFormat.format(date);

        return horaActual;
    }




}