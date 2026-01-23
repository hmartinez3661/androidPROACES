package com.mantprev.mantprevproaces2.ui.listadoOTs;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.mantprev.mantprevproaces2.ModelosDTO1.RtesEjecOTs;
import com.mantprev.mantprevproaces2.ModelosDTO2.Repte2Datos;
import com.mantprev.mantprevproaces2.R;
import com.mantprev.mantprevproaces2.retrofit.DataServices_Intf;
import com.mantprev.mantprevproaces2.retrofit.Retrofit_Instance;
//import com.mantprev.mantprevproaces2.ui.otrasUI.ActivityCerarSesion;
import com.mantprev.mantprevproaces2.utilities.MetodosStaticos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class FragmentTabVerRepEje extends Fragment {

    private String idOT;
    private String numOT;
    private TextView tvNumOT;

    private TextView tvFechaFinaliz, tvNombrFalla, tvNombrSupOT;
    private EditText etHrsParoProduc, etHrsTrabajo, etCalidadTrab, etReportEjec, etNombRecivTrab;
    private TableLayout tblPersTecn,  tblRepuestos, tblServExt;

    private ProgressBar progressBar;
    private TextView tvIndicFotosCierre, tvIndicRepuest, tvIndicServExt;
    private CardView cvRepuestos, cvServExt;
    private View root;

    public FragmentTabVerRepEje() {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        idOT = MetodosStaticos.idOT;
        numOT = "OT-" + idOT;
        root = inflater.inflate(R.layout.fragment_tab_ver_rep_eje, container, false);

        TextView tvIdOT = (TextView) root.findViewById(R.id.tvIdRepteEjec);
        tvIdOT.setText(idOT);  //Text view hidden

        tvNumOT = (TextView) root.findViewById(R.id.tvNumRut);
        tvFechaFinaliz = (TextView) root.findViewById(R.id.etFechaEjecuc);
        etHrsParoProduc = (EditText) root.findViewById(R.id.etHrsProdStop);
        etHrsTrabajo = (EditText) root.findViewById(R.id.etHrsLabor);
        etCalidadTrab = (EditText) root.findViewById(R.id.etCalidadTrab);
        etReportEjec = (EditText) root.findViewById(R.id.etReportEjec);
        tvNombrFalla = (TextView) root.findViewById(R.id.tvNombrFalla);
        tvNombrSupOT = (TextView) root.findViewById(R.id.tvNombSupRut);
        etNombRecivTrab = (EditText) root.findViewById(R.id.etNombRecivTrab);

        tvIndicRepuest = (TextView) root.findViewById(R.id.tvIndicRepuest);
        tvIndicServExt = (TextView) root.findViewById(R.id.tvIndicServExt);

        cvRepuestos = (CardView) root.findViewById(R.id.cvRepuestos);
        cvServExt = (CardView) root.findViewById(R.id.cvServExt);

        tblPersTecn = (TableLayout) root.findViewById(R.id.tblPersTecn);
        tblRepuestos = (TableLayout) root.findViewById(R.id.tblRepuestos);
        tblServExt = (TableLayout) root.findViewById(R.id.tblServExt);

        tvIndicFotosCierre = (TextView) root.findViewById(R.id.tvIndicFotosCierre);
        progressBar = (ProgressBar) root.findViewById(R.id.progresBar);

        tvIndicRepuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarRepuestosUtiliz();
            }
        });

        tvIndicServExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarServExterOT();
            }
        });

        tvIndicFotosCierre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cantFotosCier > 0){
                    MetodosStaticos.fotosDeCierroOT = "Yes"; //Para que busque solo fotos de cierre
                    Navigation.findNavController(root).navigate(R.id.fragmentImages);
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.msgNoatachedFotos), Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvNumOT.setText(numOT);
        cvRepuestos.setVisibility(View.GONE);
        cvServExt.setVisibility(View.GONE);

        mostrarDatosRepteEjecOT();
        mostrarEjecutoresOT();

        return root;
    }


    //Varialbles de clase
    int cantReptsUlit, cantServExter, cantFotosCier;

    private void mostrarDatosRepteEjecOT() {
    /*********************************/
        progressBar.setVisibility(View.VISIBLE);
        int idRpteEjecc = MetodosStaticos.idRepteEjecOT;

        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<RtesEjecOTs> call = service.getRepteEjecucOT(idRpteEjecc);  //"reptes/getRepteEjec/" + idRpteEjec;

        call.enqueue(new Callback<RtesEjecOTs>() {

            @Override
            public void onResponse(Call<RtesEjecOTs> call, retrofit2.Response<RtesEjecOTs> response) {
                /*
                if (response.code() == 401){  // El token ha expirado
                    ActivityCerarSesion activCerrarSess = new ActivityCerarSesion();
                    activCerrarSess.cleanSesionDeUsuario(getContext());
                    Toast.makeText(getContext(), "The session has ended", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(root).navigate(R.id.fragmentLogin);
                }  */

                if(response.isSuccessful() && response.body() != null){
                    RtesEjecOTs repteEjec = response.body();

                    Date fechaEjecuc = repteEjec.getFechaInicio();
                    Double hrsParoProd = repteEjec.getTpoParoProduc();
                    Double hrsTrabajo = repteEjec.getTpoRealReparac();
                    Double calidadTrab = repteEjec.getCalidadTrab();
                    String reportEjec = repteEjec.getReporteHistor();
                    String nombrFalla = repteEjec.getNombreFalla();
                    String supervOT = repteEjec.getNombreSuperv();
                    String recivioTrb = repteEjec.getnPersRecivTrab();
                    cantReptsUlit = repteEjec.getCantRptosUtiliz();
                    cantServExter = repteEjec.getCantServExter();
                    cantFotosCier = repteEjec.getCantFotosCierre();

                    String formatedStrDate = MetodosStaticos.getStrDateFormated(fechaEjecuc);
                    String hrsParoProducc = Double.toString(hrsParoProd);
                    String horasTrabajo   = Double.toString(hrsTrabajo);
                    String calidadTrabajo = Double.toString(calidadTrab);

                    tvNumOT.setText(numOT);
                    tvFechaFinaliz.setText(formatedStrDate);
                    etHrsParoProduc.setText(hrsParoProducc);
                    etHrsTrabajo.setText(horasTrabajo);
                    etCalidadTrab.setText(calidadTrabajo);
                    etReportEjec.setText(reportEjec);
                    tvNombrFalla.setText(nombrFalla);
                    tvNombrSupOT.setText(supervOT);
                    etNombRecivTrab.setText(recivioTrb);

                    String tvIndicRepuestStr = tvIndicRepuest.getText().toString() + " (" + cantReptsUlit + ")";
                    String tvIndicServExtStr = tvIndicServExt.getText().toString() + " (" + cantServExter + ")";
                    String tvIndicFotosStr = tvIndicFotosCierre.getText().toString() + " (" + cantFotosCier + ")";

                    tvIndicRepuest.setText(tvIndicRepuestStr);
                    tvIndicServExt.setText(tvIndicServExtStr);
                    tvIndicFotosCierre.setText(tvIndicFotosStr);

                } else {
                    Toast.makeText(getContext(), "Fallo en cargar reporte de Ejecución Null", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RtesEjecOTs> call, Throwable throwable) {
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());
                Toast.makeText(getContext(), "Fallo en cargar reporte de Ejecución", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void mostrarEjecutoresOT() {
    /**********************************/
        int idOrdTrab = Integer.parseInt(idOT);

        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<List<Repte2Datos>> call = service.getListReptesPersEjecOTByIdOT(idOrdTrab);  // "reptes/reptsPers/" + idOT;

        call.enqueue(new Callback<List<Repte2Datos>>() {
            ArrayList<Repte2Datos> listaReptes = new ArrayList<>();

            @Override
            public void onResponse(Call<List<Repte2Datos>> call, retrofit2.Response<List<Repte2Datos>> response) {
                /*
                if (response.code() == 401){  // El token ha expirado
                    ActivityCerarSesion activCerrarSess = new ActivityCerarSesion();
                    activCerrarSess.cleanSesionDeUsuario(getContext());
                    Toast.makeText(getContext(), "The session has ended", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(root).navigate(R.id.fragmentLogin);
                }  */

                if(response.isSuccessful() && response.body() != null){
                    listaReptes.addAll(response.body());
                    ArrayList<String> listaPersEjecOT = new ArrayList<>();
                    int listaReptesSize = listaReptes.size();

                    for (int i = 0; i < listaReptesSize; i++) {

                        Repte2Datos repte2Datos = listaReptes.get(i);

                        String nombreTecn = repte2Datos.getNombre();
                        Double horasTrab  = repte2Datos.getValor();
                        String persTec_hrsTrab = nombreTecn +"-"+ horasTrab;

                        listaPersEjecOT.add(persTec_hrsTrab);
                    }

                    agregarPersTecnTabla(listaPersEjecOT);
                }else {
                    Toast.makeText(getContext(), "Fallo en cargar lista Ejecutores", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Repte2Datos>> call, Throwable throwable) {
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());
                Toast.makeText(getContext(), "Fallo en cargar datos Ejecutores", Toast.LENGTH_LONG).show();
            }
        });

        progressBar.setVisibility(View.GONE);
    }


    public void agregarPersTecnTabla(ArrayList<String> listaPersEjecOT){
    /******************************************************************/
        int numFila = 0; //Para agregarle un # a la fila
        int listaPersEjectOTsSize = listaPersEjecOT.size();

        for (int i=0; i< listaPersEjectOTsSize; i++){

            String persTecn = listaPersEjecOT.get(i);

            int indOfGuion = persTecn.indexOf("-");
            String nombreDeTecn = persTecn.substring(0, indOfGuion);
            String hrsDeTrabajo = persTecn.substring(indOfGuion + 1);

            TableRow filaTabla = new TableRow(getContext());
            numFila = numFila + 1;

            TextView tvColum0 = new TextView(getContext());
            tvColum0.setBackgroundResource(R.drawable.style_edittex_tbls);
            tvColum0.setWidth(35);
            tvColum0.setGravity(1);
            tvColum0.setText(Integer.toString(numFila));
            tvColum0.setTextColor(Color.BLACK);

            TextView tvColum1 = new TextView(getContext());
            tvColum1.setBackgroundResource(R.drawable.style_edittex_tbls);
            tvColum1.setWidth(170);
            tvColum1.setText(nombreDeTecn);
            tvColum1.setTextColor(Color.BLACK);

            TextView tvColum2 = new TextView(getContext());
            tvColum2.setBackgroundResource(R.drawable.style_edittex_tbls);
            tvColum2.setWidth(60);
            tvColum2.setGravity(1);
            tvColum2.setText(hrsDeTrabajo);
            tvColum2.setTextColor(Color.BLACK);

            filaTabla.addView(tvColum0);
            filaTabla.addView(tvColum1);
            filaTabla.addView(tvColum2);

            tblPersTecn.addView(filaTabla);
        }
    }


    private void mostrarRepuestosUtiliz(){
    /************************************/
        if (cantReptsUlit > 0){
            int isVisible = cvRepuestos.getVisibility();

            if (isVisible == View.VISIBLE){
                cvRepuestos.setVisibility(View.GONE);
            } else {
                cvRepuestos.setVisibility(View.VISIBLE);
            }
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.msgNoReúestos), Toast.LENGTH_LONG).show();
        }

        if (cantReptsUlit > 0 && tblRepuestos.getChildCount() <= 1){

            int isVisible = cvRepuestos.getVisibility();

            if (isVisible == View.VISIBLE){
                cvRepuestos.setVisibility(View.GONE);
            } else {
                cvRepuestos.setVisibility(View.VISIBLE);
            }

            progressBar.setVisibility(View.VISIBLE);
            int idOrdTrab = Integer.parseInt(idOT);

            /* RETROFIT */
            DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
            Call<List<Repte2Datos>> call = service.getListReptesRepuestosEjecOT(idOrdTrab);  // "reptes/listaReptos/" + idOT;

            call.enqueue(new Callback<List<Repte2Datos>>() {
                final ArrayList<Repte2Datos> listaReptes = new ArrayList<>();

                @Override
                public void onResponse(Call<List<Repte2Datos>> call, retrofit2.Response<List<Repte2Datos>> response) {

                    if(response.isSuccessful() && response.body() != null){
                        listaReptes.addAll(response.body());
                        ArrayList<String> listaReptosUtilz = new ArrayList<>();
                        int listaReptesSize = listaReptes.size();

                        for (int i = 0; i < listaReptesSize; i++) {

                            Repte2Datos repte2Datos = listaReptes.get(i);

                            String nombreRepto = repte2Datos.getNombre();
                            Double costoTotRepto = repte2Datos.getValor();
                            String nRepsto_costoTotal = nombreRepto +"-"+ costoTotRepto;

                            listaReptosUtilz.add(nRepsto_costoTotal);
                        }

                        agregarListaReptosTabla(listaReptosUtilz);
                    } else {
                        Toast.makeText(getContext(), "Fallo en cargas lista de repuestos", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Repte2Datos>> call, Throwable throwable) {
                    throwable.printStackTrace();
                    Log.d("ErrorResponse: ", throwable.toString());
                    Toast.makeText(getContext(), "Fallo en cargas datos de repuestos", Toast.LENGTH_LONG).show();
                }
            });

            progressBar.setVisibility(View.GONE);
        }
    }


    private void agregarListaReptosTabla(ArrayList<String> listaReptosUtilz){
    /***********************************************************************/
        int numFila = 0;
        int listaReptosUlizSize = listaReptosUtilz.size();
        for (int i=0; i< listaReptosUlizSize; i++){

            String nombRepto_costo = listaReptosUtilz.get(i);

            int indOfGuion = nombRepto_costo.indexOf("-");
            String nombrRepto = nombRepto_costo.substring(0, indOfGuion);
            String costoTotal = nombRepto_costo.substring(indOfGuion + 1);

            TableRow filaTbl = new TableRow(getContext());
            numFila = numFila + 1;

            TextView tvColum0 = new TextView(getContext());
            tvColum0.setBackgroundResource(R.drawable.style_edittex_tbls);
            tvColum0.setWidth(40);
            tvColum0.setGravity(1);
            tvColum0.setText(Integer.toString(numFila));
            tvColum0.setTextColor(Color.BLACK);

            TextView tvColum1 = new TextView(getContext());
            tvColum1.setBackgroundResource(R.drawable.style_edittex_tbls);
            tvColum1.setWidth(195);
            tvColum1.setText(nombrRepto);
            tvColum1.setTextColor(Color.BLACK);

            TextView tvColum2 = new TextView(getContext());
            tvColum2.setBackgroundResource(R.drawable.style_edittex_tbls);
            tvColum2.setWidth(70);
            tvColum2.setText(costoTotal);
            tvColum2.setGravity(1);
            tvColum2.setTextColor(Color.BLACK);

            filaTbl.addView(tvColum0);
            filaTbl.addView(tvColum1);
            filaTbl.addView(tvColum2);

            tblRepuestos.addView(filaTbl);  //
        }
    }

    private void mostrarServExterOT() {
    /*********************************/
        if (cantServExter > 0){
            int isVisible = cvServExt.getVisibility();

            if (isVisible == View.VISIBLE){
                cvServExt.setVisibility(View.GONE);
            } else {
                cvServExt.setVisibility(View.VISIBLE);
            }
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.msgNoServExt), Toast.LENGTH_LONG).show();
        }

        if (cantServExter > 0 && tblServExt.getChildCount() <= 1){

            progressBar.setVisibility(View.VISIBLE);
            int idOrdTrab = Integer.parseInt(idOT);

            /* RETROFIT */
            DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
            Call<List<Repte2Datos>> call = service.getListaReptesServExterEjecOT(idOrdTrab);   //"reptes/listaSevExt/"

            call.enqueue(new Callback<List<Repte2Datos>>() {
                final ArrayList<Repte2Datos> listReptes2Datos = new ArrayList<>();

                @Override
                public void onResponse(Call<List<Repte2Datos>> call, retrofit2.Response<List<Repte2Datos>> response) {

                    if(response.isSuccessful() && response.body() != null){
                        listReptes2Datos.addAll(response.body());
                        ArrayList<String> listaServExt = new ArrayList<>();
                        int lisaReptes2DatosSize = listReptes2Datos.size();

                        for (int i = 0; i < lisaReptes2DatosSize; i++) {
                            Repte2Datos repte2Dts = listReptes2Datos.get(i);

                            String nombreServic = repte2Dts.getNombre();
                            Double costoServic = repte2Dts.getValor();
                            String nServExt_costo = nombreServic +"-"+ costoServic;

                            listaServExt.add(nServExt_costo);
                        }

                        agregarServExternosTbl(listaServExt);
                    } else {
                        Toast.makeText(getContext(), "Fallo en cargar lista serv. externos", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Repte2Datos>> call, Throwable throwable) {
                    throwable.printStackTrace();
                    Log.d("ErrorResponse: ", throwable.toString());
                    Toast.makeText(getContext(), "Fallo en cargar datos servicios externos", Toast.LENGTH_LONG).show();
                }
            });

            progressBar.setVisibility(View.GONE);
        }
    }

    private void agregarServExternosTbl(ArrayList<String> listaServExt){
    /*******************************************************************/
        int numFila = 0; //Para agregarle un # a la fila
        int listaServExtSize = listaServExt.size();
        String numFilaStr = "";

        for (int i=0; i< listaServExtSize; i++){

            String nombServ_costo = listaServExt.get(i);

            int indOfGuion = nombServ_costo.indexOf("-");
            String nombrServ = nombServ_costo.substring(0, indOfGuion);
            String costoServ = nombServ_costo.substring(indOfGuion + 1);

            TableRow filaTbl = new TableRow(getContext());
            numFila = numFila + 1;
            numFilaStr = numFila + "";

            TextView tvColum0 = new TextView(getContext());
            tvColum0.setBackgroundResource(R.drawable.style_edittex_tbls);
            tvColum0.setWidth(40);
            tvColum0.setGravity(1);
            tvColum0.setText(numFilaStr);
            tvColum0.setTextColor(Color.BLACK);

            TextView tvColum1 = new TextView(getContext());
            tvColum1.setBackgroundResource(R.drawable.style_edittex_tbls);
            tvColum1.setWidth(195);
            tvColum1.setText(nombrServ);
            tvColum1.setTextColor(Color.BLACK);

            TextView tvColum2 = new TextView(getContext());
            tvColum2.setBackgroundResource(R.drawable.style_edittex_tbls);
            tvColum2.setWidth(70);
            tvColum2.setText(costoServ);
            tvColum2.setGravity(1);
            tvColum2.setTextColor(Color.BLACK);

            filaTbl.addView(tvColum0);
            filaTbl.addView(tvColum1);
            filaTbl.addView(tvColum2);

            tblServExt.addView(filaTbl);   //
        }
    }







}