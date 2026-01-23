package com.mantprev.mantprevproaces2.ui.Rutinas;

import static android.view.View.GONE;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mantprev.mantprevproaces2.ModelosDTO1.ConfigSpinners;
import com.mantprev.mantprevproaces2.ModelosDTO1.Equipos;
import com.mantprev.mantprevproaces2.ModelosDTO1.RustEquiposDTO;
import com.mantprev.mantprevproaces2.R;
import com.mantprev.mantprevproaces2.adapters.ExpandListEquipsAdapter;
import com.mantprev.mantprevproaces2.adapters.RutinasAdapter;
import com.mantprev.mantprevproaces2.retrofit.DataServices_Intf;
import com.mantprev.mantprevproaces2.retrofit.Retrofit_Instance;
import com.mantprev.mantprevproaces2.utilities.MetodosStaticos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentListaRuts extends Fragment {

    public FragmentListaRuts() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvSemRuts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                renovarListadoDeRuts();
            }
        });

        tvEquipArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                renovarListadoDeRuts();
            }
        });

        tvEjecutor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                renovarListadoDeRuts();
            }
        });
    }

    private TextView tvSemRuts, tvEquipArea, tvCorrelat, tvEjecutor, tvTituloListaRuts;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private AlertDialog alertDialog;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private View root;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_lista_ruts, container, false);

        tvSemRuts = (TextView) root.findViewById(R.id.tvSemRuts);
        tvEquipArea = (TextView) root.findViewById(R.id.tvEquipArea);
        tvEjecutor = (TextView) root.findViewById(R.id.tvEjector);
        tvCorrelat = (TextView) root.findViewById(R.id.tvCorrelat);
        tvTituloListaRuts = (TextView) root.findViewById(R.id.tvTituloListaRuts);
        progressBar = (ProgressBar) root.findViewById(R.id.progresBar);

        recyclerView = (RecyclerView) root.findViewById(R.id.reciclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        tvEquipArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getArbolDeEquipos(view);
            }
        });

        tvSemRuts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogoChangeWeek();
            }
        });

        tvEjecutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListaDeEjecutores();
            }
        });

        // METODOS DE ARRANQUE
        progressBar.setVisibility(GONE);
        setVariablesDeInicio();
        getAndShowAllRutinasSemAct();

        return root;
    }

    private String semanaRuts = "";   //07-2025
    private void setVariablesDeInicio() {   //Semana Actual
    /***********************************/
        Calendar calendario = Calendar.getInstance();
        int numWeek = calendario.get(Calendar.WEEK_OF_YEAR);
        int numYear = calendario.get(Calendar.YEAR);

        String semAnio = "";
        String semApi  = "";

        if (numWeek < 10){
            semAnio = "0" + numWeek +"/"+ numYear;
            semApi  = numYear +"-0"+ numWeek;
        } else {
            semAnio = numWeek +"/"+ numYear;
            semApi  = numYear +"-"+ numWeek;    //  fecha01  fecha02
        }
        semanaRuts = semAnio.replace("/", "-");  //07-2025
        correlatEqu = "0";
        ejecutorRut = "Todos los ejecutores";

        String diainicioWeek = MetodosStaticos.getDiaInicioSemSelecc(semApi);
        String fiaFinalWeek  = MetodosStaticos.getDiaFinalSemSelecc(semApi);
        String infSemanaRuts = semAnio +" ("+ diainicioWeek +" - " + fiaFinalWeek +")";

        String equipoArea = "Planta General";
        String ejecutor   = "Todos los ejecutores";

        tvSemRuts.setText(infSemanaRuts);
        tvEquipArea.setText(equipoArea);
        tvEjecutor.setText(ejecutor);
    }

    private void getAndShowAllRutinasSemAct() {
    /****************************************/
        recyclerView.removeAllViews();
        progressBar.setVisibility(View.VISIBLE);

        DataServices_Intf service2 = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<List<RustEquiposDTO>> call2 = service2.getRutinasSemSelecc(semanaRuts);   //07-2025

        call2.enqueue(new Callback<List<RustEquiposDTO>>() {
            @Override
            public void onResponse(Call<List<RustEquiposDTO>> call, Response<List<RustEquiposDTO>> response) {

                if(response.isSuccessful() && response.body() != null){
                    List<RustEquiposDTO> listaRutsEquips = new ArrayList<>(response.body());
                    int cantRorts= MetodosStaticos.getCantidadRutsConRepteEjec(listaRutsEquips);

                    //Coloca el cantidad de OTs de la lista
                    int cantOts = listaRutsEquips.size();
                    String tvTexto = getResources().getString(R.string.tvRutinasLista);
                    String textoInf = tvTexto +" ("+ cantRorts +"/"+ cantOts +")";
                    tvTituloListaRuts.setText(textoInf);
                    //tvEquipArea.setText(getResources().getString(R.string.tvNombreEquipo));  //tvNombreEquipo

                    if (!listaRutsEquips.isEmpty()){
                        //SE ENVIA LA INFORMACION AL ADAPTADOR
                        RutinasAdapter ordTrabAdapter = new RutinasAdapter(listaRutsEquips, getContext());
                        ordTrabAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int idRutEquip = listaRutsEquips.get(recyclerView.getChildAdapterPosition(view)).getIdRutEquipo();
                                int idRpteEjec = listaRutsEquips.get(recyclerView.getChildAdapterPosition(view)).getIdRepteEjec();
                                int idEquipo = listaRutsEquips.get(recyclerView.getChildAdapterPosition(view)).getIdEquipo();
                                String numRutina = listaRutsEquips.get(recyclerView.getChildAdapterPosition(view)).getNumeroRut();

                                mostraRutinaSelected(idRutEquip, idRpteEjec, idEquipo, numRutina);
                            }
                        });
                        recyclerView.setAdapter(ordTrabAdapter);

                    } else {
                        Toast.makeText(getContext(), "No datos para mostrar ...", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.msgNoHayFallas), Toast.LENGTH_LONG).show();
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<RustEquiposDTO>> call, Throwable throwable) {
                Toast.makeText(getContext(), "No data to show", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void renovarListadoDeRuts() {
    /*********************************/
        recyclerView.removeAllViews();
        String semSelected = semanaRuts;
        String correlatEquSel = correlatEqu;
        String ejecutorSel = ejecutorRut;

        progressBar.setVisibility(View.VISIBLE);

        DataServices_Intf service2 = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<List<RustEquiposDTO>> call2 = service2.renovarListaRutinas(semSelected, correlatEquSel, ejecutorSel);

        call2.enqueue(new Callback<List<RustEquiposDTO>>() {
            @Override
            public void onResponse(Call<List<RustEquiposDTO>> call, Response<List<RustEquiposDTO>> response) {

                if(response.isSuccessful() && response.body() != null){
                    List<RustEquiposDTO> listaRutsEquips = new ArrayList<>(response.body());
                    int cantRorts= MetodosStaticos.getCantidadRutsConRepteEjec(listaRutsEquips);

                    //Coloca el cantidad de OTs de la lista
                    int cantOts = listaRutsEquips.size();
                    String tvTexto = getResources().getString(R.string.tvRutinasLista);
                    String textoInf = tvTexto +" ("+ cantRorts +"/"+ cantOts +")";
                    tvTituloListaRuts.setText(textoInf);
                    //tvEquipArea.setText(getResources().getString(R.string.tvNombreEquipo));  //tvNombreEquipo

                    if (!listaRutsEquips.isEmpty()){
                        //SE ENVIA LA INFORMACION AL ADAPTADOR
                        RutinasAdapter ordTrabAdapter = new RutinasAdapter(listaRutsEquips, getContext());
                        ordTrabAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int idRutEquip = listaRutsEquips.get(recyclerView.getChildAdapterPosition(view)).getIdRutEquipo();
                                int idRpteEjec = listaRutsEquips.get(recyclerView.getChildAdapterPosition(view)).getIdRepteEjec();
                                int idEquipo = listaRutsEquips.get(recyclerView.getChildAdapterPosition(view)).getIdEquipo();
                                String numRutina = listaRutsEquips.get(recyclerView.getChildAdapterPosition(view)).getNumeroRut();

                                mostraRutinaSelected(idRutEquip, idRpteEjec, idEquipo, numRutina);
                            }
                        });
                        recyclerView.setAdapter(ordTrabAdapter);

                    } else {
                        Toast.makeText(getContext(), "No datos para mostrar ...", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.msgNoHayFallas), Toast.LENGTH_LONG).show();
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<RustEquiposDTO>> call, Throwable throwable) {
                Toast.makeText(getContext(), "No data to show", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void showDialogoChangeWeek() {
    /************************************/
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog (requireContext(),
                (view, year, month, dayOfMonth) -> {

                    Calendar selected = Calendar.getInstance();
                    selected.set(Calendar.YEAR, year);
                    selected.set(Calendar.MONTH, month);
                    selected.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    // Configuración ISO (igual que HTML week)
                    selected.setFirstDayOfWeek(Calendar.MONDAY);
                    selected.setMinimalDaysInFirstWeek(4);

                    int numWeek = selected.get(Calendar.WEEK_OF_YEAR);
                    int numYear = selected.getWeekYear();
                    String semAnio = "";
                    String semApi  = "";

                    if (numWeek < 10){
                        semAnio = "0" + numWeek +"/"+ numYear;
                        semApi  = numYear +"-0"+ numWeek;
                    } else {
                        semAnio = numWeek +"/"+ numYear;
                        semApi  = numYear +"-"+ numWeek;    //  fecha01  fecha02
                    }
                    semanaRuts = semAnio.replace("/", "-");  //07-2025

                    String diaInicioWeek = MetodosStaticos.getDiaInicioSemSelecc(semApi);
                    String diaFinalWeek  = MetodosStaticos.getDiaFinalSemSelecc(semApi);
                    String infSemanaRuts = semAnio +" ("+ diaInicioWeek +" - " + diaFinalWeek +")";

                    tvSemRuts.setText(infSemanaRuts);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }

    private void getArbolDeEquipos(View view) {
    /*****************************************/
        progressBar.setVisibility(View.VISIBLE);

        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<List<Equipos>> call = service.getTodosLosEquipos();

        call.enqueue(new Callback<List<Equipos>>() {

            final ArrayList<Equipos> listaEquipos = new ArrayList<>();
            final ArrayList<String> parentsList = new ArrayList<>();
            Map<String, List<String>> equipsMap;
            ArrayList<String> childList;

            @Override
            public void onResponse(Call<List<Equipos>> call, Response<List<Equipos>> response) {

                listaEquipos.addAll(response.body());
                listaEquipos.remove(0); //Remueve el nodo raiz (Planta General)

                //SE CREA LA LISTA DE EQUIPOS PADRES
                equipsMap = new HashMap<String, List<String>>();
                ArrayList<Equipos> listaEquiposPadres = new ArrayList<>();
                int listaEquiposSize = listaEquipos.size();

                for (int i=0; i< listaEquiposSize; i++){

                    int idEquipo = listaEquipos.get(i).getIdEquipo();
                    String correlat = listaEquipos.get(i).getCorrelativo();
                    String nombreEquip = listaEquipos.get(i).getNombEquipo();

                    if (correlat.length() == 2){

                        Equipos equipoPadre = new Equipos();
                        equipoPadre.setIdEquipo(idEquipo);
                        equipoPadre.setNombEquipo(nombreEquip);
                        equipoPadre.setCorrelativo(correlat);

                        listaEquiposPadres.add(equipoPadre);
                    }
                }

                //SE CREAN LA LISTA DE NOMBRES DE PADRES E HIJOS
                int listaEquiposPadreSize = listaEquiposPadres.size();
                for (int i=0; i< listaEquiposPadreSize; i++){

                    String correlatPadre = listaEquiposPadres.get(i).getCorrelativo();
                    String nombrEquipPrd = listaEquiposPadres.get(i).getNombEquipo();

                    parentsList.add(nombrEquipPrd);
                    childList = new ArrayList<>();

                    for (int j=0; j<listaEquipos.size(); j++){

                        Equipos equipoHijo = listaEquipos.get(j);
                        int idEquipoHijo = equipoHijo.getIdEquipo();
                        String correlatHijo = equipoHijo.getCorrelativo();
                        String nombreEquipo = equipoHijo.getNombEquipo() + " (" + correlatHijo +")";

                        if(correlatHijo.startsWith(correlatPadre)){
                            childList.add(nombreEquipo);
                        }
                    }
                    equipsMap.put(nombrEquipPrd, childList);
                }

                mostrarVentanaEquipos(parentsList, equipsMap);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Equipos>> call, Throwable throwable) {
                //error.printStackTrace();
                //Log.d("ErrorResponse: ", error.toString());
            }
        });
    }

    private String correlatEqu = "";
    public void mostrarVentanaEquipos(ArrayList<String> parentsList, Map<String, List<String>> equipsMap){
        /****************************************************************************/
        final View windowEquipos = getLayoutInflater().inflate(R.layout.window_arbol_equipos, null);

        expandableListView = windowEquipos.findViewById(R.id.expListEquips);
        expandableListAdapter = new ExpandListEquipsAdapter(getContext(), parentsList, equipsMap);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;

            @Override
            public void onGroupExpand(int i) {
                if (lastExpandedPosition != -1 && i != lastExpandedPosition){
                    expandableListView.collapseGroup(lastExpandedPosition);
                }

                lastExpandedPosition = i;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String equipSelected = expandableListAdapter.getChild(i, i1).toString();

                //Separa el nombre y el id del equipo que viene con el nombre desde el ExpandableListView
                int posCorchete = equipSelected.lastIndexOf("(");
                String correlat = equipSelected.substring(posCorchete + 1).replace(")", "");
                equipSelected = equipSelected.substring(0, posCorchete);
                correlatEqu = correlat;

                tvEquipArea.setText(equipSelected);
                tvCorrelat.setText(correlat);
                alertDialog.dismiss();

                return true;
            }
        });

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
        dialogBuilder.setView(windowEquipos);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    public void getListaDeEjecutores(){
    /*********************************/
        progressBar.setVisibility(View.VISIBLE);

        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<List<ConfigSpinners>> call = service.getLstaDeEjecutores();

        call.enqueue(new Callback<List<ConfigSpinners>>() {
            final List<ConfigSpinners> listaConfigSpnn = new ArrayList<>();
            final List<String> listaEjcutRuts = new ArrayList<String>();

            @Override
            public void onResponse(Call<List<ConfigSpinners>> call, retrofit2.Response<List<ConfigSpinners>> response) {
                /*
                if (response.code() == 401){  // El token ha expirado
                    ActivityCerarSesion activCerrarSess = new ActivityCerarSesion();
                    activCerrarSess.cleanSesionDeUsuario(getContext());
                    Toast.makeText(getContext(), "The session has ended", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(root).navigate(R.id.fragmentLogin);
                } */

                if(response.isSuccessful() && response.body() != null){
                    listaConfigSpnn.addAll(response.body());
                    int listaConfigSpinnSize = listaConfigSpnn.size();

                    for (int i=0; i< listaConfigSpinnSize; i++) {

                        ConfigSpinners configSpnn = listaConfigSpnn.get(i);
                        String ejecutor  = configSpnn.getEjecutoresOTs();

                        if (ejecutor != null){
                            if (!ejecutor.isEmpty()){
                                listaEjcutRuts.add(ejecutor);
                            }
                        }
                    }

                    listaEjcutRuts.remove(0);
                    listaEjcutRuts.add(0, "Todos los ejecutores");

                    progressBar.setVisibility(View.GONE);
                    mostrarVentanaEjecutores(listaEjcutRuts);

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Fallo en cargar ejecutores ....", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ConfigSpinners>> call, Throwable throwable) {
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "FALLO EN CARGAR DATOS", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String ejecutorRut = "";
    public void mostrarVentanaEjecutores(List<String> listaEjecuts){
    /*************************************************************/
        final View windowListaSuperv = getLayoutInflater().inflate(R.layout.window_lista_ejecut, null);
        ListView listViewEjecut = windowListaSuperv.findViewById(R.id.listViewEject);

        ArrayAdapter arrayAdapter = new ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listaEjecuts);  //
        listViewEjecut.setAdapter(arrayAdapter);

        listViewEjecut.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nombreEject = listaEjecuts.get(position);
                ejecutorRut = nombreEject;
                alertDialog.dismiss();
                tvEjecutor.setText((CharSequence) nombreEject);
            }
        });

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
        dialogBuilder.setView(windowListaSuperv);

        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void mostraRutinaSelected(int idRutEquip, int idRpteEjec, int idEquipo, String numRutina){
    /**********************************************************************************/
        MetodosStaticos.idRutEquip = idRutEquip;
        MetodosStaticos.idRepteEjecRut = idRpteEjec;
        MetodosStaticos.numRutina = numRutina;
        MetodosStaticos.idEquipo = idEquipo;
        MetodosStaticos.semanaRuts = semanaRuts;

        Navigation.findNavController(root).navigate(R.id.fragmentRutinasTabs);
    }





}