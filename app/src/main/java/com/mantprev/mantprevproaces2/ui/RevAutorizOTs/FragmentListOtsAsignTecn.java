package com.mantprev.mantprevproaces2.ui.RevAutorizOTs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mantprev.mantprevproaces2.ModelosDTO1.OrdenesTrabajo;
import com.mantprev.mantprevproaces2.R;
import com.mantprev.mantprevproaces2.adapters.OrdTrabAdapter;
import com.mantprev.mantprevproaces2.retrofit.DataServices_Intf;
import com.mantprev.mantprevproaces2.retrofit.Retrofit_Instance;
import com.mantprev.mantprevproaces2.utilities.MetodosStaticos;
import com.mantprev.mantprevproaces2.utilities.StaticConfig;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentListOtsAsignTecn extends Fragment {

    private boolean inicioFragment = true; // Para que no llene el spinner dos veces

    private TextView tvTituloConfig, tvMesListado;
    private Spinner spinnerPerTecn;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private View root;

    public FragmentListOtsAsignTecn() {
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

        root = inflater.inflate(R.layout.fragment_list_ots_asign_tecn, container, false);

        tvTituloConfig = (TextView) root.findViewById(R.id.tvTituloConfig);
        spinnerPerTecn = (Spinner) root.findViewById(R.id.spnPersTecn);
        progressBar = (ProgressBar) root.findViewById(R.id.progresBar);
        tvMesListado = (TextView) root.findViewById(R.id.tvMesListado);

        recyclerView = (RecyclerView) root.findViewById(R.id.reciclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        tvMesListado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoChangeMes();
            }
        });

        spinnerPerTecn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(!inicioFragment) {
                    String nombreDeTecn = spinnerPerTecn.getSelectedItem().toString();
                    filtrarOTsPorNombreTecnicos(nombreDeTecn);

                } else  {
                    inicioFragment = false;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { /*****/ }
        });

        // METODOS DE ARRANQUE
        getNamesPersTecnSpinner();
        setMesAndAnioInicial();  //Establece datos del mes actual y luego muestra listado de OTs

        return root;
    }

    private void getNamesPersTecnSpinner(){
    /***************************************/
        progressBar.setVisibility(View.VISIBLE);

        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);

        Call<List<String>> call2 = service.getNamesPersTecnAct();
        call2.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {

                if (response.code() == 401){  // La sesion ha expirado
                    //Toast.makeText(getContext(), "The session has ended", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(root).navigate(R.id.fragmentLogin);
                }

                if(response.isSuccessful() && response.body() != null){
                    List<String> listNamesTecnic = response.body();

                    //Adapter Spinner personal técnico
                    ArrayAdapter<CharSequence> adapterPersTecn = new ArrayAdapter(requireContext(), R.layout.zspinners_items, listNamesTecnic); //R.layout.zspinner_text
                    adapterPersTecn.setDropDownViewResource(R.layout.zspinners_dropdown_items);
                    spinnerPerTecn.setAdapter(adapterPersTecn);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable throwable) {
                Toast.makeText(getContext(), "Fallo en cargar lista de Técnicos", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void mostraOrdenDeTrabajo(String idOT, int idRpteEjec){
        /*********************************************/
        MetodosStaticos.idOT = idOT; //Métod para pasar datos a otro Fragment dandole valor a una variable estatica
        MetodosStaticos.idRepteEjecOT = idRpteEjec;

        Navigation.findNavController(root).navigate(R.id.fragmentCheckupOT);

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

        mostrarListadoOTs(fechaInic, fechaFinal);
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

        mostrarListadoOTs(fechaInic, fechaFinal);
    }

    private ArrayList<OrdenesTrabajo> listaOrdenesTrab;
    private void mostrarListadoOTs(String fechaInic, String fechaFinal){
    /********************************************************************/
        progressBar.setVisibility(View.VISIBLE);

        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<List<OrdenesTrabajo>> listaOTs = service.getListOTsByFechas(fechaInic, fechaFinal);

        recyclerView.removeAllViews();

        listaOTs.enqueue(new Callback<List<OrdenesTrabajo>>() {
            @Override
            public void onResponse(Call<List<OrdenesTrabajo>> call, Response<List<OrdenesTrabajo>> response) {

                if (response.code() == 401){  // La sesion ha expirado
                    //Toast.makeText(getContext(), "The session has ended", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(root).navigate(R.id.fragmentLogin);
                }

                if(response.isSuccessful() && response.body() != null){
                    listaOrdenesTrab = new ArrayList<>();
                    listaOrdenesTrab.addAll(response.body());

                    //Coloca el cantidad de OTs de la lista
                    int cantOts = listaOrdenesTrab.size();
                    String tvTexto = getResources().getString(R.string.titOTsAsigTecn);
                    String tentoInf = tvTexto + " (" + cantOts + ")";
                    tvTituloConfig.setText(tentoInf);

                    if (!listaOrdenesTrab.isEmpty()){

                        //SE ENVIA LA INFORMACION AL ADAPTADOR
                        OrdTrabAdapter ordTrabAdapter = new OrdTrabAdapter(listaOrdenesTrab, getContext());
                        ordTrabAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int idOTSelecc = listaOrdenesTrab.get(recyclerView.getChildAdapterPosition(view)).getIdOT();
                                int idRpteEjec = listaOrdenesTrab.get(recyclerView.getChildAdapterPosition(view)).getIdReporteEjecuc();

                                mostraOrdenDeTrabajo(Integer.toString(idOTSelecc), idRpteEjec);
                            }
                        });
                        recyclerView.setAdapter(ordTrabAdapter);

                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string.msgNoHayFallas), Toast.LENGTH_LONG).show();
                    }
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

    private void filtrarOTsPorNombreTecnicos(String nombreDeTecn){
    /*******************************************************/
        ArrayList<OrdenesTrabajo> listaOTsFiltradas = new ArrayList<>();
        int listaOrdTrabajoSize = listaOrdenesTrab.size();

        for (int i=0; i< listaOrdTrabajoSize; i++){

            OrdenesTrabajo ordenTrab = listaOrdenesTrab.get(i);
            String nombDeTecn = ordenTrab.getTecnAsignado();
            if(nombDeTecn == null){
                nombDeTecn = "";
            }

            if(nombDeTecn.equals(nombreDeTecn)){
                listaOTsFiltradas.add(ordenTrab);
            }
        }

        int cantOts = listaOTsFiltradas.size();
        String tvTexto = getResources().getString(R.string.titOTsAsigTecn);
        String tvIndic = tvTexto + " (" + cantOts + ")";
        tvTituloConfig.setText(tvIndic);

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