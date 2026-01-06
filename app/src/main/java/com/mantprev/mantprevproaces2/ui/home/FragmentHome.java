package com.mantprev.mantprevproaces2.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.mantprev.mantprevproaces2.ModelosDTO1.ConfigSpinners;
import com.mantprev.mantprevproaces2.ModelosDTO1.OrdenesTrabajo;
import com.mantprev.mantprevproaces2.R;
import com.mantprev.mantprevproaces2.databinding.FragmentHomeBinding;
import com.mantprev.mantprevproaces2.retrofit.DataServices_Intf;
import com.mantprev.mantprevproaces2.retrofit.Retrofit_Instance;
import com.mantprev.mantprevproaces2.utilities.MetodosStaticos;
import com.mantprev.mantprevproaces2.utilities.StaticConfig;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class FragmentHome extends Fragment {

    private FragmentHomeBinding binding;
    private ProgressBar progresBar;
    private String fechaInic, fechaFinal;
    private String otNueva, otRevisada, otEnProceso, otFinalizada, otRechazada;
    private TextView tvOtsGenerad, tvOtsRevisadas, tvOtsSinRevisar, tvOtsEnProceso,
            tvOtsRechazadas, tvOtsFinaliz, tvWellcomeUser;
    private View root;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        Button btnNewOrdTrab = (Button) root.findViewById(R.id.btnNewOT);
        //Button btnMisOrdTrab = (Button) root.findViewById(R.id.btnVerMisOTs);
        Button btnOTsAsignds = (Button) root.findViewById(R.id.btnOTsAsignds);
        tvOtsGenerad = (TextView) root.findViewById(R.id.tvOtsGenerad);
        tvOtsRevisadas = (TextView) root.findViewById(R.id.tvOtsRevisadas);
        tvOtsSinRevisar = (TextView) root.findViewById(R.id.tvOtsSinRevisar);
        tvOtsEnProceso = (TextView) root.findViewById(R.id.tvOtsEnProceso);
        tvOtsRechazadas = (TextView) root.findViewById(R.id.tvOtsRechazadas);
        tvOtsFinaliz = (TextView) root.findViewById(R.id.tvOtsFinaliz);
        tvWellcomeUser = (TextView) root.findViewById(R.id.tvWellcomeUser);
        progresBar = (ProgressBar) root.findViewById(R.id.progresBar);

        btnNewOrdTrab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearNvaOrdenDeTrabajo();
            }
        });

        /*
        btnMisOrdTrab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMisOrdenesDeTrabajo();
            }
        }); */

        btnOTsAsignds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rolUsuario = StaticConfig.rolDeUsuario;

                if (rolUsuario.equals("ROLE_SUPERVISOR") || rolUsuario.equals("ROLE_RTE_EJEC-RUTS-OTS")) {
                    getOTsAsignadasTecnicos();
                } else{
                    Toast.makeText(getContext(), "Su rol no le permite ver este reporte", Toast.LENGTH_LONG).show();
                }
            }
        });

        getFechaInicial();
        getFechaFinal();
        getNombresStatusDeOTs();
        return root;
    }

    private void getNombresStatusDeOTs() {
    /************************************/
        //Coloca el nombre del usuario
        String nombreUsuario = StaticConfig.numbRealUser;
        int pos = nombreUsuario.indexOf("(");
        nombreUsuario = nombreUsuario.substring(0, pos);

        String nombreEmpresa = " (" + StaticConfig.nombrEmpresa + ")";
        String textoSuperior = nombreUsuario + nombreEmpresa;
        tvWellcomeUser.setText(textoSuperior);

        progresBar.setVisibility(View.VISIBLE);
        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<List<ConfigSpinners>> call = service.getConfiguracSpinner();  //"auth/getConf"

        call.enqueue(new Callback<List<ConfigSpinners>>() {
            final ArrayList<ConfigSpinners> listConfSpinner = new ArrayList<>();

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
                    listConfSpinner.addAll(response.body());

                    if (listConfSpinner.size() > 1) {
                        ConfigSpinners configLin1 = listConfSpinner.get(1);
                        ConfigSpinners configLin2 = listConfSpinner.get(2);
                        ConfigSpinners configLin3 = listConfSpinner.get(3);
                        ConfigSpinners configLin4 = listConfSpinner.get(4);
                        ConfigSpinners configLin5 = listConfSpinner.get(5);

                        otNueva = configLin1.getEstatusOTs();
                        otRevisada = configLin2.getEstatusOTs();
                        otEnProceso = configLin3.getEstatusOTs();
                        otFinalizada = configLin4.getEstatusOTs();
                        otRechazada  = configLin5.getEstatusOTs();

                        progresBar.setVisibility(View.GONE);
                        //Corre el métod que calvula las OTs
                        getInfoOTsFragmentHome();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ConfigSpinners>> call, Throwable throwable) {
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());
                Toast.makeText(getContext(), "FALLO EN CARGAR CONFIG SPINNERS", Toast.LENGTH_LONG).show();
                progresBar.setVisibility(View.GONE);
            }
        });
    }

    private void getInfoOTsFragmentHome(){
    /************************************/
        progresBar.setVisibility(View.VISIBLE);
        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<List<OrdenesTrabajo>> call = service.getInformHomeDeOTs(fechaInic, fechaFinal);   //"ordsTrab/getInfHome/" + fechaInic +"/"+ fechaFinal;

        call.enqueue(new Callback<List<OrdenesTrabajo>>() {
            final ArrayList<OrdenesTrabajo> listOrdTrab = new ArrayList<>();

            @Override
            public void onResponse(Call<List<OrdenesTrabajo>> call, retrofit2.Response<List<OrdenesTrabajo>> response) {

                if(response.isSuccessful() && response.body() != null){

                    listOrdTrab.addAll(response.body());

                    int OTsSinRevisar = 0;  //OT con el estatus de nuevaOT
                    int OTsRevisadas = 0;
                    int OTsEnProceso = 0;
                    int OTsRechazadas = 0;
                    int OTsFinalizadas = 0;
                    int listaOrdTrabSize = listOrdTrab.size();

                    for (int i=0; i< listaOrdTrabSize; i++) {

                        OrdenesTrabajo ordenTrab = listOrdTrab.get(i);        // JSONObject objFila = jsonArray.getJSONObject(i);
                        String statusOT = ordenTrab.getEstatusOT();

                        if(statusOT.equals(otNueva)){
                            OTsSinRevisar = OTsSinRevisar + 1;

                        } else if(statusOT.equals(otRevisada)){
                            OTsRevisadas = OTsRevisadas + 1;

                        } else if(statusOT.equals(otEnProceso)){
                            OTsEnProceso = OTsEnProceso + 1;

                        } else if(statusOT.equals(otRechazada)){
                            OTsRechazadas = OTsRechazadas + 1;

                        } else if(statusOT.equals(otFinalizada)){
                            OTsFinalizadas = OTsFinalizadas + 1;
                        }
                    }

                    int totalOTs = OTsSinRevisar + OTsRevisadas + OTsEnProceso + OTsFinalizadas;
                    String totOTs = Integer.toString(totalOTs);
                    String OtsRev = Integer.toString(OTsRevisadas);
                    String otsSinRev = Integer.toString(OTsSinRevisar);
                    String otsEnProc = Integer.toString(OTsEnProceso);
                    String otsRechaz = Integer.toString(OTsRechazadas);
                    String otsFinalz = Integer.toString(OTsFinalizadas);

                    //Se llevan los TextView con los datos
                    tvOtsGenerad.setText(totOTs);
                    tvOtsRevisadas.setText(OtsRev);
                    tvOtsSinRevisar.setText(otsSinRev);
                    tvOtsEnProceso.setText(otsEnProc);
                    tvOtsRechazadas.setText(otsRechaz);
                    tvOtsFinaliz.setText(otsFinalz);
                    progresBar.setVisibility(View.GONE);

                    if (listOrdTrab.isEmpty()){
                        Toast.makeText(getContext(), getResources().getString(R.string.msgNoHayFallas), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<OrdenesTrabajo>> call, Throwable throwable) {
                progresBar.setVisibility(View.GONE);
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());
                Toast.makeText(getContext(), "FALLO EN CARGAR LISTA OTs", Toast.LENGTH_LONG).show();
            }
        });
    }

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private void getFechaInicial(){
        /*****************************/
        Date fechaInicial = MetodosStaticos.getFechaUltimos30Dias();
        fechaInic = dateFormat.format(fechaInicial);
    }

    private void getFechaFinal(){
        /***************************/
        Date fechaFinalDt = MetodosStaticos.getFechaDeMananaDt(); //Un dia mas de hoy
        fechaFinal = dateFormat.format(fechaFinalDt);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void crearNvaOrdenDeTrabajo(){
    /*********************************/
        Navigation.findNavController(root).navigate(R.id.nav_newOT);
    }

    /*
    private void getMisOrdenesDeTrabajo(){
    //***********************************
        Navigation.findNavController(root).navigate(R.id.fragmentMisOTs);
    } */

    private void getOTsAsignadasTecnicos(){
        /***********************************/
        Navigation.findNavController(root).navigate(R.id.fragmtOTsAsignds);
    }







}