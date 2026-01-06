package com.mantprev.mantprevproaces2.ui.misOrdTrab;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mantprev.mantprevproaces2.ModelosDTO1.OrdenesTrabajo;
import com.mantprev.mantprevproaces2.R;
import com.mantprev.mantprevproaces2.adapters.OrdTrabAdapter;
import com.mantprev.mantprevproaces2.retrofit.DataServices_Intf;
import com.mantprev.mantprevproaces2.retrofit.Retrofit_Instance;
//import com.mantprev.mantprevproaces2.ui.otrasUI.ActivityCerarSesion;
import com.mantprev.mantprevproaces2.utilities.MetodosStaticos;
import com.mantprev.mantprevproaces2.utilities.StaticConfig;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class FragmentMisOTs extends Fragment {

    public FragmentMisOTs(){
        //Constructor vacios
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvFechaInic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                getAndShowOrdsTrabDeUsuario();
            }
        });

        tvFechaFinal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                getAndShowOrdsTrabDeUsuario();
            }
        });
    }

    private TextView tvFechaInic, tvFechaFinal, tvIndicaciones;
    private int dayInic, monthInic, yearInic, dayFinal, monthFinal, yearFinal;
    private String datePickerFechaInic, datePickerFechafinal;

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_mis_ots, container, false);

        tvFechaInic = (TextView) root.findViewById(R.id.tvMesListado);
        tvFechaFinal = (TextView) root.findViewById(R.id.tvFechaFinal);
        tvIndicaciones = (TextView) root.findViewById(R.id.tvTituloConfig);
        progressBar = (ProgressBar) root.findViewById(R.id.progresBar);

        recyclerView = (RecyclerView) root.findViewById(R.id.reciclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        tvFechaInic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogFechaInicial();
            }
        });

        tvFechaFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogFechaFinal();
            }
        });

        // METODOS DE ARRANQUE
        getFechaInicial();
        getFechaFinal();
        getAndShowOrdsTrabDeUsuario();

        return root;
    }


    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private void getFechaInicial(){
    /****************************/
        Date fechaInicial = MetodosStaticos.getFechaUltimos30Dias();
        String strDate = dateFormat.format(fechaInicial);

        String[] dateSplited = strDate.split("/");
        dayInic = Integer.parseInt(dateSplited[0]);
        monthInic = Integer.parseInt(dateSplited[1]);
        yearInic = Integer.parseInt(dateSplited[2]);

        //Guardamos la fecha en formato consultas BD
        datePickerFechaInic = yearInic +"-"+ monthInic +"-"+ dayInic;

        DateFormat dateFormat2 = DateFormat.getDateInstance(DateFormat.DEFAULT);
        String strDateFormated = dateFormat2.format(fechaInicial);
        tvFechaInic.setText(strDateFormated);

        monthInic = monthInic -1;
    }


    private void getFechaFinal(){
    /***************************/
        Date fechaHoy = MetodosStaticos.getFechaDeMananaDt();  //Le suma 24 horas a la fecha de hoy
        String strDate = dateFormat.format(fechaHoy);

        String[] dateSplited = strDate.split("/");
        dayFinal = Integer.parseInt(dateSplited[0]);
        monthFinal = Integer.parseInt(dateSplited[1]);
        yearFinal = Integer.parseInt(dateSplited[2]);

        //Guardamos la fecha en formato consultas BD
        datePickerFechafinal = yearFinal +"-"+ monthFinal +"-"+ dayFinal;

        DateFormat dateFormat2 = DateFormat.getDateInstance(DateFormat.DEFAULT);
        String strDateFormated = dateFormat2.format(fechaHoy);
        tvFechaFinal.setText(strDateFormated);

        monthFinal = monthFinal -1;
    }


    private void openDialogFechaInicial(){
    /*********************************/
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mesSelect = month + 1;
                dayInic = dayOfMonth;
                monthInic = month;
                yearInic = year;

                //Guardamos la fecha en formato consultas BD
                datePickerFechaInic = year +"-"+ mesSelect +"-"+ dayOfMonth;

                String strDateFormated = MetodosStaticos.getFechaStrFormated(datePickerFechaInic);
                tvFechaInic.setText(strDateFormated);
            }
        }, yearInic, monthInic, dayInic);

        datePickerDialog.show();
    }


    private void openDialogFechaFinal(){
    /*********************************/
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mesSelect = month + 1;
                dayFinal = dayOfMonth;
                monthFinal = month;
                yearFinal = year;

                //Guardamos la fecha en formato consultas BD
                datePickerFechafinal = year +"-"+ mesSelect +"-"+ dayOfMonth;

                String strDateFormated = MetodosStaticos.getFechaStrFormated(datePickerFechafinal);
                tvFechaFinal.setText(strDateFormated);
            }
        }, yearFinal, monthFinal, dayFinal);

        datePickerDialog.show();
    }


    private void getAndShowOrdsTrabDeUsuario(){
    /**************************************/
        progressBar.setVisibility(View.VISIBLE);
        String fechaInic  = datePickerFechaInic;
        String fechaFinal = datePickerFechafinal;
        String nombSolictOT = StaticConfig.numbRealUser;

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        Date date2 = null;

        try {
            date1 = sdFormat.parse(fechaInic);
            date2 = sdFormat.parse(fechaFinal);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(date1.before(date2)) {  //Date1 occurs before Date2   date1.compareTo(date2
            /* RETROFIT */
            DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
            Call<List<OrdenesTrabajo>> call = service.getListOTsByFechasUser(fechaInic, fechaFinal, nombSolictOT);

            call.enqueue(new Callback<List<OrdenesTrabajo>>() {
                ArrayList<OrdenesTrabajo> listOrdnsTrab = new ArrayList<>();

                @Override
                public void onResponse(Call<List<OrdenesTrabajo>> call, retrofit2.Response<List<OrdenesTrabajo>> response) {

                    /*
                    if (response.code() == 401){  // El token ha expirado
                        ActivityCerarSesion activCerrarSess = new ActivityCerarSesion();
                        activCerrarSess.cleanSesionDeUsuario(getContext());
                        Toast.makeText(getContext(), "The session has ended", Toast.LENGTH_LONG).show();
                        Navigation.findNavController(root).navigate(R.id.fragmentLogin);
                    }  */

                    if (response.isSuccessful() && response.body() != null) {
                        listOrdnsTrab.addAll(response.body());

                        if(!listOrdnsTrab.isEmpty()){
                            //Coloca el cantidad de OTs de la lista
                            int cantOts = listOrdnsTrab.size();
                            String tvTexto = getResources().getString(R.string.headMisOTs1) + " (" + cantOts + ")";
                            tvIndicaciones.setText(tvTexto);

                            //SE ENVIA LA INFORMACION AL ADAPTADOR
                            OrdTrabAdapter ordTrabAdapter = new OrdTrabAdapter(listOrdnsTrab, getContext());
                            ordTrabAdapter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    int idOTSelecc = listOrdnsTrab.get(recyclerView.getChildAdapterPosition(view)).getIdOT();
                                    int idRpteEjec = listOrdnsTrab.get(recyclerView.getChildAdapterPosition(view)).getIdReporteEjecuc();
                                    mostraOrdenDeTrabajo(idOTSelecc, idRpteEjec);
                                }
                            });

                            recyclerView.setAdapter(ordTrabAdapter);
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<List<OrdenesTrabajo>> call, Throwable throwable) {
                    throwable.printStackTrace();
                    Log.d("ErrorResponse: ", throwable.toString());
                    Toast.makeText(getContext(), "FALLO EN CARGAR DATOS", Toast.LENGTH_LONG).show();
                }
            });

        } else {
            Toast.makeText(getContext(), R.string.msgErrorFechas, Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }


    private void mostraOrdenDeTrabajo(int idOT, int idRpteEjec){
    /*********************************************************/
        MetodosStaticos.idOT = Integer.toString(idOT);
        MetodosStaticos.idRepteEjecOT = idRpteEjec;

        Navigation.findNavController(root).navigate(R.id.fragmentVerOtTabs);
    }








}