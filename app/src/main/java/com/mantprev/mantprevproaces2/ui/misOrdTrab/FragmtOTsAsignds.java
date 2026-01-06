package com.mantprev.mantprevproaces2.ui.misOrdTrab;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mantprev.mantprevproaces2.ModelosDTO1.Equipos;
import com.mantprev.mantprevproaces2.ModelosDTO1.OrdenesTrabajo;
import com.mantprev.mantprevproaces2.R;
import com.mantprev.mantprevproaces2.adapters.ExpandListEquipsAdapter;
import com.mantprev.mantprevproaces2.adapters.OrdTrabAdapter;
import com.mantprev.mantprevproaces2.retrofit.DataServices_Intf;
import com.mantprev.mantprevproaces2.retrofit.Retrofit_Instance;
import com.mantprev.mantprevproaces2.ui.listadoOTs.FragmentVerOtTabs;
import com.mantprev.mantprevproaces2.utilities.MetodosStaticos;
import com.mantprev.mantprevproaces2.utilities.StaticConfig;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmtOTsAsignds extends Fragment {

    public FragmtOTsAsignds() {
        // Required empty public constructor
    }

    TextView tvIndicaciones, tvEquipArea, tvCorrelat, tvMesListado;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    private View root;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragmt_ots_asignds, container, false);  // Inflate the layout for this fragment

        tvMesListado = (TextView) root.findViewById(R.id.tvMesListado);
        tvIndicaciones = (TextView) root.findViewById(R.id.tvTituloConfig);
        tvEquipArea = (TextView) root.findViewById(R.id.tvEquipArea);
        tvCorrelat = (TextView) root.findViewById(R.id.tvCorrelat);
        progressBar = (ProgressBar) root.findViewById(R.id.progresBar);

        recyclerView = (RecyclerView) root.findViewById(R.id.reciclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        tvEquipArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getArbolDeEquipos(view);
            }
        });

        tvMesListado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoChangeMes();
            }
        });

        // METODOS DE ARRANQUE
        setMesAndAnioInicial();  //Establece datos del mes actual y luego muestra listado de OTs

        return root;
    }

    private void mostrarDialogoChangeMes() {
    /************************************/
        View view = LayoutInflater.from(getContext()).inflate(R.layout.lrf_selecc_mes_dialog, null);

        NumberPicker npAnioSelecc = view.findViewById(R.id.npAnio);
        NumberPicker npMesSelecc  = view.findViewById(R.id.npMes);

        String[] anios = StaticConfig.yearPicker;
        String[] meses = StaticConfig.monthPicker;

        configurarMesPicker(npAnioSelecc, anios);
        configurarMesPicker(npMesSelecc , meses);

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(view)
                .setPositiveButton("OK", (d, which) -> {
                    String yearSelected  = anios[npAnioSelecc.getValue()];
                    String monthSelected = meses[npMesSelecc.getValue()];

                    setMesAndAnioSelected(yearSelected, monthSelected);
                    //Toast.makeText(getContext(), yearSelected + " - " + monthSelected, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .create();

        dialog.show();
    }

    private void configurarMesPicker(NumberPicker picker, String[] valores) {
        picker.setMinValue(0);
        picker.setMaxValue(valores.length - 1);
        picker.setDisplayedValues(valores);
        picker.setWrapSelectorWheel(false);
    }


    private String fecha01;
    private String fecha02;
    private String mesMostradoTv = "";  //Mes que se muestra en el TextView
    private void setMesAndAnioInicial(){
        /****************************/
        Calendar calendar = Calendar.getInstance();
        int anoActual = calendar.get(Calendar.YEAR);
        int mesActual = calendar.get(Calendar.MONTH) + 1;

        String dtsMesActual = anoActual +"-"+ mesActual;
        String mesActFormat = MetodosStaticos.getMesFormater(mesActual);
        String mesMostrarTv = mesActFormat +"/"+ anoActual;
        tvMesListado.setText(mesMostrarTv);

        List<String> fechasDelMes = MetodosStaticos.getFechaInicioAndFinalMesSelecc(dtsMesActual);
        String fechaInic = fechasDelMes.get(0);
        String fechaFinal = fechasDelMes.get(1);
        fecha01 = fechaInic;
        fecha02 = fechaFinal;

        showOTsAsignadasEjecut(fechaInic, fechaFinal);
    }

    private void setMesAndAnioSelected(String anoSelected, String mesSelected){
        /************************************************************************/
        mesMostradoTv = tvMesListado.getText().toString();
        int mesSelectedInt  = MetodosStaticos.getNumeroDeMes(mesSelected);
        String dtsMesSelecc = anoSelected +"-"+ mesSelectedInt;

        String mesMostrarTv = mesSelected +"/"+ anoSelected;
        tvMesListado.setText(mesMostrarTv);

        List<String> fechasDelMes = MetodosStaticos.getFechaInicioAndFinalMesSelecc(dtsMesSelecc);
        String fechaInic = fechasDelMes.get(0);
        String fechaFinal = fechasDelMes.get(1);
        fecha01 = fechaInic;
        fecha02 = fechaFinal;

        showOTsAsignadasEjecut(fechaInic, fechaFinal);
    }

    private ArrayList<OrdenesTrabajo> listaOrdenesTrab;

    private void showOTsAsignadasEjecut(String fechaInic, String fechaFinal){
    /********************************************************************/
        progressBar.setVisibility(View.VISIBLE);
        String nombEjecut = StaticConfig.numbRealUser;

        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<List<OrdenesTrabajo>> listaOTs = service.getOTsAsigndsEjecutor(fechaInic, fechaFinal, nombEjecut);
        recyclerView.removeAllViews();

        listaOTs.enqueue(new Callback<List<OrdenesTrabajo>>() {
            @Override
            public void onResponse(Call<List<OrdenesTrabajo>> call, Response<List<OrdenesTrabajo>> response) {
                /*
                if (response.code() == 401){  // El token ha expirado
                    ActivityCerarSesion activCerrarSess = new ActivityCerarSesion();
                    activCerrarSess.cleanSesionDeUsuario(getContext());
                    Toast.makeText(getContext(), "The session has ended", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(root).navigate(R.id.fragmentLogin);
                } */

                if(response.isSuccessful() && response.body() != null){
                    listaOrdenesTrab = new ArrayList<>();
                    assert response.body() != null;
                    listaOrdenesTrab.addAll(response.body());

                    //Coloca el cantidad de OTs de la lista
                    int cantOts = listaOrdenesTrab.size();
                    String tvTexto = getResources().getString(R.string.headListaOTs1);
                    String tentoInf = tvTexto + " (" + cantOts + ")";
                    tvIndicaciones.setText(tentoInf);
                    tvEquipArea.setText(getResources().getString(R.string.tvNombreEquipo));  //tvNombreEquipo

                    if (!listaOrdenesTrab.isEmpty()){

                        //SE ENVIA LA INFORMACION AL ADAPTADOR
                        OrdTrabAdapter ordTrabAdapter = new OrdTrabAdapter(listaOrdenesTrab, getContext());
                        ordTrabAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int idOTSelecc = listaOrdenesTrab.get(recyclerView.getChildAdapterPosition(view)).getIdOT();
                                int idRpteEjec = listaOrdenesTrab.get(recyclerView.getChildAdapterPosition(view)).getIdReporteEjecuc();

                                mostraOrdenDeTrabajo(idOTSelecc, idRpteEjec);
                            }
                        });
                        recyclerView.setAdapter(ordTrabAdapter);

                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string.msgNoHayFallas), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.msgNoHayFallas), Toast.LENGTH_LONG).show();
                    tvMesListado.setText(mesMostradoTv);
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<OrdenesTrabajo>> call, Throwable throwable) {
                Toast.makeText(getContext(), "No data to show", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void mostraOrdenDeTrabajo(int idOTSelecc, int idRpteEjec){
        /****************************************************************/
        MetodosStaticos.idOT = Integer.toString(idOTSelecc);
        MetodosStaticos.idRepteEjecOT = idRpteEjec;

        FragmentVerOtTabs.fechaInicListOTs  = fecha01;  //"yyyy-MM-dd"
        FragmentVerOtTabs.fechaFinalListOTs = fecha02;  //"yyyy-MM-dd"

        Navigation.findNavController(root).navigate(R.id.fragmentVerOtTabs);
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
                ArrayList<Equipos> listaEquiposPradre = new ArrayList<>();
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

                        listaEquiposPradre.add(equipoPadre);
                    }
                }

                //SE CREAN LA LISTA DE NOMBRES DE PADRES E HIJOS
                int listaEquiposPadreSize = listaEquiposPradre.size();
                for (int i=0; i< listaEquiposPadreSize; i++){

                    String correlatPadre = listaEquiposPradre.get(i).getCorrelativo();
                    String nombrEquipPrd = listaEquiposPradre.get(i).getNombEquipo();

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
                int posCorchete  = equipSelected.lastIndexOf("(");
                String correlatEqu = equipSelected.substring(posCorchete + 1).replace(")", "");
                equipSelected = equipSelected.substring(0, posCorchete);

                tvEquipArea.setText(equipSelected);
                tvCorrelat.setText(correlatEqu);
                alertDialog.dismiss();

                filtrarEquipsPorCorrelat();

                return true;
            }
        });

        dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setView(windowEquipos);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void filtrarEquipsPorCorrelat(){
        /***************************************/
        String correlatPdr = tvCorrelat.getText().toString();
        ArrayList<OrdenesTrabajo> listaOTsFiltradas = new ArrayList<>();
        int listaOrdTrabajoSize = listaOrdenesTrab.size();

        for (int i=0; i< listaOrdTrabajoSize; i++){

            OrdenesTrabajo ordenTrab = listaOrdenesTrab.get(i);
            String correlatEquip = ordenTrab.getCorrelativo();

            if(correlatEquip.startsWith(correlatPdr)){
                listaOTsFiltradas.add(ordenTrab);
            }
        }

        int cantOts = listaOTsFiltradas.size();
        String tvTexto = getResources().getString(R.string.headListaOTs1);
        String tvIndic = tvTexto + " (" + cantOts + ")";
        tvIndicaciones.setText(tvIndic);

        //SE ENVIA LA INFORMACION AL ADAPTADOR
        OrdTrabAdapter ordTrabAdapter = new OrdTrabAdapter(listaOTsFiltradas, getContext());
        ordTrabAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idOTSelecc = listaOTsFiltradas.get(recyclerView.getChildAdapterPosition(view)).getIdOT();
                MetodosStaticos.idOT = Integer.toString(idOTSelecc); //Guarda IdOT para utilizarse para ver la OT
                //Navigation.findNavController(root).navigate(R.id.fragmentAutorizOTs2);
            }
        });

        recyclerView.setAdapter(ordTrabAdapter);
    }










}