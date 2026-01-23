package com.mantprev.mantprevproaces2.ui.RevAutorizOTs;

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

public class FragmentCheckOTsList extends Fragment {

    private boolean inicioFragment = true; // Para que no llene el spinner dos veces

    private TextView tvIndicaciones;
    private Spinner spinnerEjecut;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private View root;

    public FragmentCheckOTsList() {
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

        root = inflater.inflate(R.layout.fragment_check_ots_list, container, false);

        tvIndicaciones = (TextView) root.findViewById(R.id.tvTituloConfig);
        spinnerEjecut = (Spinner) root.findViewById(R.id.tvEjecutoRutDt);
        progressBar = (ProgressBar) root.findViewById(R.id.progresBar);

        recyclerView = (RecyclerView) root.findViewById(R.id.reciclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        spinnerEjecut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(!inicioFragment) {
                    String ejectSelected = spinnerEjecut.getSelectedItem().toString();
                    getAndShowOTsRivisarPorEjecutor(ejectSelected);

                } else  {
                    inicioFragment = false;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { /*****/ }
        });

        // METODOS DE ARRANQUE
        getListaEjecutoresSpinner();
        getAndShowOTsParaAutorizar();

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
            final ArrayList<String> listaEjecut = new ArrayList<>();

            @Override
            public void onResponse(Call<List<ConfigSpinners>> call, retrofit2.Response<List<ConfigSpinners>> response) {
                /*
                if (response.code() == 401){  // El token ha expirado
                    ActivityCerarSesion activCerrarSess = new ActivityCerarSesion();
                    activCerrarSess.cleanSesionDeUsuario(getContext());
                    Toast.makeText(getContext(), "The session has ended", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(root).navigate(R.id.fragmentLogin);
                }  */

                listConfSpinner.addAll(response.body());
                int listaConfigSpinnSize = listConfSpinner.size();

                for (int i=0; i< listaConfigSpinnSize; i++) {

                    ConfigSpinners configSpn = listConfSpinner.get(i);
                    String ejecutor  = configSpn.getEjecutoresOTs();

                    if (ejecutor != null){
                        if (!ejecutor.isEmpty()){
                            listaEjecut.add(ejecutor);
                        }
                    }
                }

                //Adapter Spinner Ejecutores
                ArrayAdapter<CharSequence> adapterEject = new ArrayAdapter(getContext(), R.layout.zspinners_items, listaEjecut);
                adapterEject.setDropDownViewResource(R.layout.zspinners_dropdown_items);
                spinnerEjecut.setAdapter(adapterEject);

                progressBar.setVisibility(View.GONE);
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


    private void getAndShowOTsParaAutorizar(){
    /***************************************/
        progressBar.setVisibility(View.VISIBLE);
        String privilegUser = StaticConfig.rolDeUsuario;

        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<List<OrdenesTrabajo>> call = service.getListaDeOTsNuevas();   // "ordsTrab/listNewOTs";

        call.enqueue(new Callback<List<OrdenesTrabajo>>() {

            final ArrayList<OrdenesTrabajo> listaOrdenesTrab = new ArrayList<>();

            @Override
            public void onResponse(Call<List<OrdenesTrabajo>> call, retrofit2.Response<List<OrdenesTrabajo>> response) {

                if(response.isSuccessful() && response.body() != null){
                    listaOrdenesTrab.addAll(response.body());

                    //Coloca el cantidad de OTs de la lista
                    int cantOts = listaOrdenesTrab.size();
                    String tvTexto = getResources().getString(R.string.headCkeckOTs1) + " (" + cantOts + ")";
                    tvIndicaciones.setText(tvTexto);

                    //SE ENVIA LA INFORMACION AL ADAPTADOR
                    OrdTrabAdapter ordTrabAdapter = new OrdTrabAdapter(listaOrdenesTrab, getContext());
                    ordTrabAdapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(privilegUser.equals("ROLE_ADMIN") || privilegUser.equals("ROLE_MANTOEDIC")){
                                int idOTSelecc = listaOrdenesTrab.get(recyclerView.getChildAdapterPosition(view)).getIdOT();
                                int idRpteEjec = listaOrdenesTrab.get(recyclerView.getChildAdapterPosition(view)).getIdReporteEjecuc();

                                mostraOrdenDeTrabajo(Integer.toString(idOTSelecc), idRpteEjec);
                            } else {
                                Toast.makeText(getContext(), getResources().getString(R.string.msgAccRestr), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    recyclerView.setAdapter(ordTrabAdapter);
                    progressBar.setVisibility(View.GONE);

                } else {
                    Toast.makeText(getContext(), "No hay OTs para Autorizar", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<OrdenesTrabajo>> call, Throwable throwable) {
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());
                Toast.makeText(getContext(), "FALLO EN CARGAR LISTA OTs", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getAndShowOTsRivisarPorEjecutor(String ejectSelected){
    /************************************************************/
        String privilegUser = StaticConfig.rolDeUsuario;

        if(ejectSelected.equals("---")){
            getAndShowOTsParaAutorizar(); //Carga el listado de OTs sin filtro de ejecutor

        } else {
            progressBar.setVisibility(View.VISIBLE);

            /* RETROFIT */
            DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
            Call<List<OrdenesTrabajo>> call = service.getListaNewOTsByEjecutor(ejectSelected);   // "ordsTrab/listNewOTsEjec/"

            call.enqueue(new Callback<List<OrdenesTrabajo>>() {

                final ArrayList<OrdenesTrabajo> listaOrdenesTrab = new ArrayList<>();

                @Override
                public void onResponse(Call<List<OrdenesTrabajo>> call, retrofit2.Response<List<OrdenesTrabajo>> response) {

                    if(response.isSuccessful() && response.body() != null){
                        listaOrdenesTrab.addAll(response.body());

                        //Coloca el cantidad de OTs de la lista
                        int cantOts = listaOrdenesTrab.size();
                        String tvTexto = getResources().getString(R.string.headCkeckOTs1) + " (" + cantOts + ")";
                        tvIndicaciones.setText(tvTexto);

                        //SE ENVIA LA INFORMACION AL ADAPTADOR
                        OrdTrabAdapter ordTrabAdapter = new OrdTrabAdapter(listaOrdenesTrab, getContext());
                        ordTrabAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(privilegUser.equals("ROLE_ADMIN") || privilegUser.equals("ROLE_MANTOEDIC")){
                                    int idOTSelecc = listaOrdenesTrab.get(recyclerView.getChildAdapterPosition(view)).getIdOT();
                                    int idRpteEjec = listaOrdenesTrab.get(recyclerView.getChildAdapterPosition(view)).getIdReporteEjecuc();
                                    mostraOrdenDeTrabajo(Integer.toString(idOTSelecc), idRpteEjec);

                                } else {
                                    Toast.makeText(getContext(), getResources().getString(R.string.msgAccRestr), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        recyclerView.setAdapter(ordTrabAdapter);
                        progressBar.setVisibility(View.GONE);

                    } else {
                        Toast.makeText(getContext(), "No hay OTs con el ejecutor seleccionado", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<List<OrdenesTrabajo>> call, Throwable throwable) {
                    throwable.printStackTrace();
                    Log.d("ErrorResponse: ", throwable.toString());
                    Toast.makeText(getContext(), "FALLO EN CARGAR LISTA OTs", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void mostraOrdenDeTrabajo(String idOT, int idRpteEjec){
    /*********************************************/
        MetodosStaticos.idOT = idOT; //Métod para pasar datos a otro Fragment dandole valor a una variable estatica
        MetodosStaticos.idRepteEjecOT = idRpteEjec;

        Navigation.findNavController(root).navigate(R.id.fragmentCheckupOT);

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