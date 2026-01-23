package com.mantprev.mantprevproaces2.ui.ReportEjecOTs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mantprev.mantprevproaces2.ModelosDTO1.ConfigSpinners;
import com.mantprev.mantprevproaces2.ModelosDTO1.OrdenesTrabajo;
import com.mantprev.mantprevproaces2.ModelosDTO2.OrdenesTrab_DTO2;
import com.mantprev.mantprevproaces2.R;
import com.mantprev.mantprevproaces2.adapters.OrdTrabAdapter;
import com.mantprev.mantprevproaces2.retrofit.DataServices_Intf;
import com.mantprev.mantprevproaces2.retrofit.Retrofit_Instance;
//import com.mantprev.mantprevproaces2.ui.otrasUI.ActivityCerarSesion;
import com.mantprev.mantprevproaces2.utilities.MetodosStaticos;
import com.mantprev.mantprevproaces2.utilities.StaticConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class FragmentReportEjecList extends Fragment {

    private boolean inicioFragment = true; // Para que no llene el spinner dos veces

    private TextView tvIndicaciones2;
    private Spinner spinnerEjecut;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private View root;

    public FragmentReportEjecList() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    /**********************************************************************************************************/
        root = inflater.inflate(R.layout.fragment_report_ejec_list, container, false);

        tvIndicaciones2 = (TextView) root.findViewById(R.id.tvIndicaciones2);
        spinnerEjecut = (Spinner) root.findViewById(R.id.tvEjecutoRutDt);
        progressBar = (ProgressBar) root.findViewById(R.id.progresBar);

        recyclerView = (RecyclerView) root.findViewById(R.id.reciclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));


        spinnerEjecut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(!inicioFragment) {
                    String ejectSelected = spinnerEjecut.getSelectedItem().toString();
                    cargarRecilerOTsPorEjecutor(ejectSelected);

                } else  {
                    inicioFragment = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { /*****/ }
        });

        // METODOS DE ARRANQUE
        getListaEjecutoresSpinner();
        getStatusOTsToClose();

        return root;
    }


    private void getListaEjecutoresSpinner(){
    /***************************************/
        progressBar.setVisibility(View.VISIBLE);

        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<List<ConfigSpinners>> call = service.getConfiguracSpinner();  //"configSpn/getConf"

        call.enqueue(new Callback<List<ConfigSpinners>>() {

            final ArrayList<ConfigSpinners> listConfSpinner = new ArrayList<>();
            final ArrayList<String> listaEjecOTs = new ArrayList<>();

            @Override
            public void onResponse(Call<List<ConfigSpinners>> call, retrofit2.Response<List<ConfigSpinners>> response) {

                /*
                if (response.code() == 401){  // El token ha expirado
                    ActivityCerarSesion activCerrarSess = new ActivityCerarSesion();
                    activCerrarSess.cleanSesionDeUsuario(getContext());
                    Toast.makeText(getContext(), "The session has ended", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(root).navigate(R.id.fragmentLogin);
                }  */

                if(response.isSuccessful() && response.body() != null){
                    listConfSpinner.addAll(response.body());
                    int listaConfigSpnnSize = listConfSpinner.size();

                    for (int i=0; i< listaConfigSpnnSize; i++) {

                        ConfigSpinners configSpnn = listConfSpinner.get(i);
                        String ejecutor  = configSpnn.getEjecutoresOTs();

                        if (ejecutor != null){
                            if (!ejecutor.isEmpty()){
                                listaEjecOTs.add(ejecutor);
                            }
                        }
                    }

                    //Adapter Spinner Ejecutores
                    ArrayAdapter<CharSequence> adapterEject = new ArrayAdapter(getContext(), R.layout.zspinners_items, listaEjecOTs);
                    adapterEject.setDropDownViewResource(R.layout.zspinners_dropdown_items);
                    spinnerEjecut.setAdapter(adapterEject);

                    progressBar.setVisibility(View.GONE);

                } else {
                    Toast.makeText(getContext(), "Fallo en cargar ejecutores a spinner", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
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


    private void getStatusOTsToClose(){
    /**********************************/
        progressBar.setVisibility(View.VISIBLE);

        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<List<ConfigSpinners>> call = service.getConfiguracSpinner();  //"configSpn/getConf"

        call.enqueue(new Callback<List<ConfigSpinners>>() {

            final ArrayList<ConfigSpinners> listConfSpinner = new ArrayList<>();

            @Override
            public void onResponse(Call<List<ConfigSpinners>> call, retrofit2.Response<List<ConfigSpinners>> response) {

                if(response.isSuccessful() && response.body() != null){
                    listConfSpinner.addAll(response.body());

                    ConfigSpinners configLin2 = listConfSpinner.get(2);
                    ConfigSpinners configLin3 = listConfSpinner.get(3);

                    String statusRevisd = configLin2.getEstatusOTs();
                    String statusEnProc = configLin3.getEstatusOTs();

                    //Coloca las OTs en el Adapter
                    listarOTsReadyToCloseOnAdapter(statusRevisd, statusEnProc);

                } else {
                    Toast.makeText(getContext(), "Fallo en cargar status spinner", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
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


    private String estatusRevisd;
    private String estatusEnProc;

    private void listarOTsReadyToCloseOnAdapter(String statusRevisd, String statusEnProc){
    /*********************************************************************************/
        estatusRevisd = statusRevisd;
        estatusEnProc = statusEnProc;
        String privilegUser = StaticConfig.rolDeUsuario;

        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<List<OrdenesTrab_DTO2>> call = service.getListOTsParaCerrar(statusRevisd, statusEnProc);   //"ordsTrab/OTsParaCerrar/"

        call.enqueue(new Callback<List<OrdenesTrab_DTO2>>() {

            ArrayList<OrdenesTrab_DTO2> listOTsDto = new ArrayList<>();

            @Override
            public void onResponse(Call<List<OrdenesTrab_DTO2>> call, retrofit2.Response<List<OrdenesTrab_DTO2>> response) {

                if(response.isSuccessful() && response.body() != null){
                    listOTsDto.addAll(response.body());

                    ArrayList<OrdenesTrabajo> listaOrdenesTrab = new ArrayList<>();
                    int listaOTsDtoSize = listOTsDto.size();

                    for (int i=0; i< listaOTsDtoSize; i++) {

                        OrdenesTrab_DTO2 ordTrabDto = listOTsDto.get(i);

                        int idOT = ordTrabDto.getIdOT();
                        String numOT = ordTrabDto.getNumeroOT();
                        String trabSolicit = ordTrabDto.getTrabajoSolicit();
                        String nombrEquip = ordTrabDto.getNombEquipo();
                        String persEjecut = ordTrabDto.getPersEjecutor();
                        String prioridadOT = ordTrabDto.getPrioridadOT();
                        String estatusOT = ordTrabDto.getEstatusOT();
                        String fechaIngrOT = ordTrabDto.getFechaIngresoOT();
                        String correlatEqu = ordTrabDto.getCorrelativo();
                        String persSolicito = ordTrabDto.getNombSolicitante();

                        OrdenesTrabajo ordTrabajo = new OrdenesTrabajo();
                        ordTrabajo.setIdOT(idOT);
                        ordTrabajo.setNumeroOT(numOT);
                        ordTrabajo.setTrabajoSolicit(trabSolicit);
                        ordTrabajo.setNombEquipo(nombrEquip);
                        ordTrabajo.setPersEjecutor(persEjecut);
                        ordTrabajo.setPrioridadOT(prioridadOT);
                        ordTrabajo.setEstatusOT(estatusOT);
                        ordTrabajo.setFechaIngresoOT(fechaIngrOT);
                        ordTrabajo.setCorrelativo(correlatEqu);
                        ordTrabajo.setNombSolicitante(persSolicito);

                        listaOrdenesTrab.add(ordTrabajo);
                    }

                    //Coloca el cantidad de OTs de la lista
                    int cantOts = listaOrdenesTrab.size();
                    String tvTexto = getResources().getString(R.string.reportEjecOTsIndic2) + " (" + cantOts + ")";
                    tvIndicaciones2.setText(tvTexto);

                    //SE ENVIA LA INFORMACION AL ADAPTADOR
                    OrdTrabAdapter ordTrabAdapter = new OrdTrabAdapter(listaOrdenesTrab, getContext());
                    ordTrabAdapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(privilegUser.equals("ROLE_ADMIN") || privilegUser.equals("ROLE_MANTOEDIC")){
                                int idOTSelecc = listaOrdenesTrab.get(recyclerView.getChildAdapterPosition(view)).getIdOT();
                                int idRpteEjec = listaOrdenesTrab.get(recyclerView.getChildAdapterPosition(view)).getIdReporteEjecuc();
                                String numOrdTrab = listaOrdenesTrab.get(recyclerView.getChildAdapterPosition(view)).getNumeroOT();

                                mostraOrdenDeTrabajo(Integer.toString(idOTSelecc), idRpteEjec, numOrdTrab);
                            } else {
                                Toast.makeText(getContext(), getResources().getString(R.string.msgAccRestr), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    recyclerView.setAdapter(ordTrabAdapter);
                    progressBar.setVisibility(View.GONE);

                } else {
                    Toast.makeText(getContext(), "Fallo en cargar OTs para cerrar", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<OrdenesTrab_DTO2>> call, Throwable throwable) {
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());
                Toast.makeText(getContext(), "FALLO EN CARGAR DATOS", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void cargarRecilerOTsPorEjecutor(String ejectSelected){
    /************************************************************/
        String ejecutor = ejectSelected; //.replace(" ", "-");

        if(ejectSelected.equals("---")){
            getStatusOTsToClose(); //Carga el listado de OTs sin filtro de ejecutor

        } else {
            progressBar.setVisibility(View.VISIBLE);

            /* RETROFIT */
            DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
            Call<List<OrdenesTrab_DTO2>> call = service.getListOTsParaCerrarEjecut(estatusRevisd, estatusEnProc, ejecutor);

            call.enqueue(new Callback<List<OrdenesTrab_DTO2>>() {
                final ArrayList<OrdenesTrab_DTO2> listaOrdTrabDto = new ArrayList<>();

                @Override
                public void onResponse(Call<List<OrdenesTrab_DTO2>> call, retrofit2.Response<List<OrdenesTrab_DTO2>> response) {

                    if(response.isSuccessful() && response.body() != null){
                        listaOrdTrabDto.addAll(response.body());
                        ArrayList<OrdenesTrabajo> listaOrdenesTrab = new ArrayList<>();

                        if (listaOrdTrabDto.isEmpty()) {
                            Toast.makeText(getContext(), getResources().getString(R.string.tblHeadPriorid), Toast.LENGTH_LONG).show();
                        }

                        int listaOrdTrabDtoSize = listaOrdTrabDto.size();
                        for (int i = 0; i < listaOrdTrabDtoSize; i++) {

                            OrdenesTrab_DTO2 ordTrabDto = listaOrdTrabDto.get(i);

                            int idOT = ordTrabDto.getIdOT();
                            String numOT = ordTrabDto.getNumeroOT();
                            String trabSolicit = ordTrabDto.getTrabajoSolicit();
                            String nombrEquip = ordTrabDto.getNombEquipo();
                            String persEjecut = ordTrabDto.getPersEjecutor();
                            String prioridadOT = ordTrabDto.getPrioridadOT();
                            String estatusOT  = ordTrabDto.getEstatusOT();
                            String fechaIngrOT = ordTrabDto.getFechaIngresoOT();
                            String correlatEqu = ordTrabDto.getCorrelativo();
                            String persSolicito = ordTrabDto.getNombSolicitante();

                            OrdenesTrabajo ordTrabajo = new OrdenesTrabajo();
                            ordTrabajo.setIdOT(idOT);
                            ordTrabajo.setNumeroOT(numOT);
                            ordTrabajo.setTrabajoSolicit(trabSolicit);
                            ordTrabajo.setNombEquipo(nombrEquip);
                            ordTrabajo.setPersEjecutor(persEjecut);
                            ordTrabajo.setPrioridadOT(prioridadOT);
                            ordTrabajo.setEstatusOT(estatusOT);
                            ordTrabajo.setFechaIngresoOT(fechaIngrOT);  //fechaIngrOT
                            ordTrabajo.setCorrelativo(correlatEqu);
                            ordTrabajo.setNombSolicitante(persSolicito);

                            if (persEjecut.equals(ejectSelected)) {
                                listaOrdenesTrab.add(ordTrabajo);
                            }
                        }

                        //Coloca el cantidad de OTs de la lista
                        int cantOts = listaOrdenesTrab.size();
                        String tvTexto = getResources().getString(R.string.reportEjecOTsIndic2)  + " (" + cantOts + ")";
                        tvIndicaciones2.setText(tvTexto);

                        //SE ENVIA LA INFORMACION AL ADAPTADOR
                        OrdTrabAdapter ordTrabAdapter = new OrdTrabAdapter(listaOrdenesTrab, getContext());
                        ordTrabAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int idOTSelecc = listaOrdenesTrab.get(recyclerView.getChildAdapterPosition(view)).getIdOT();
                                int idRpteEjec = listaOrdenesTrab.get(recyclerView.getChildAdapterPosition(view)).getIdReporteEjecuc();
                                String numOrdTrab = listaOrdenesTrab.get(recyclerView.getChildAdapterPosition(view)).getNumeroOT();

                                mostraOrdenDeTrabajo(Integer.toString(idOTSelecc), idRpteEjec, numOrdTrab);
                            }
                        });

                        recyclerView.setAdapter(ordTrabAdapter);
                        progressBar.setVisibility(View.GONE);

                    } else {
                        Toast.makeText(getContext(), "Fallo en cargar OTs para Cerrar - ejecutores", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<List<OrdenesTrab_DTO2>> call, Throwable throwable) {
                    throwable.printStackTrace();
                    Log.d("ErrorResponse: ", throwable.toString());
                    Toast.makeText(getContext(), "FALLO EN CARGAR LISTA", Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    private void mostraOrdenDeTrabajo(String idOT, int idRpteEjec, String numOT){
    /*************************************************************/
        MetodosStaticos.numOT = numOT;
        MetodosStaticos.idOT  = idOT;
        MetodosStaticos.idRepteEjecOT = idRpteEjec;

        Navigation.findNavController(root).navigate(R.id.fragmentReportEjecTabs);

/*
        //Metodo para pasar datos a otro Fragmente por Bundles
        FragmentCheckupOT fragmentCheckupOT = new FragmentCheckupOT();

        Bundle bundle = new Bundle();
        bundle.putString("idOT", idOT);
        fragmentCheckupOT.setArguments(bundle);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content_main, fragmentCheckupOT, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
*/

    }







}