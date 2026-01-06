package com.mantprev.mantprevproaces2.utilities;

public class zCodigoUtil {

    /*

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    STRING REQUEST
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, urlElimEquip, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR: ", error.getMessage());
            }
        });

        // Añade la peticion a la cola
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(stringRequest2);





    Locale locale = Locale.getDefault();mat.getIntegerInstance(locale);
    tvHrsLabPer.setText(numberFormat.format(hrsLabPeriodo));

    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
    tvHrsLabPer.setText(numberFormat.format(hrsLabPeriodo));


    String porcAprovLab = String.format(Locale.getDefault(),"%.1f", aprovLabor) + " %";

    //etColumn0.setBackgroundResource(android.R.color.transparent);

    public static String getFechaStrFormated(String fechaStr) {
    //**********************************************************
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        Date fecha = null;
            try {
            fecha = formato.parse(fechaStr);

        } catch (
            ParseException e) {
            e.printStackTrace();
        }

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
        String strDateFormated = dateFormat.format(fecha);

        return strDateFormated;
    }
    */

    /*
    private void filtrarTabalOTsPorEjecutor(String ejectSelected){
    //************************************************************
        for(int i = 0; i < tableLayout.getChildCount(); i++){

            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            TextView textView = (TextView) tableRow.getChildAt(3);
            String tipoEject = textView.getText().toString();
            int numFila = tableLayout.indexOfChild(tableRow);

            if(!ejectSelected.equals("---")){
                if (!tipoEject.equals(ejectSelected)){
                    if(numFila > 0){
                        tableRow.setVisibility(View.GONE);
                    }
                } else {
                    tableRow.setVisibility(View.VISIBLE);
                }
            } else{
                tableRow.setVisibility(View.VISIBLE);
            }
        }
    }

    */


    /*
    private void getListaEjecutoresSpinner(){
    /7***************************************
     progressBar.setVisibility(View.VISIBLE);

    //SE CONSULTAN Y SE AGREGA LOS DATOS A LOS SPINNERS
    JsonArrayRequest consultaDtsSpiners = new JsonArrayRequest(Request.Method.GET, urlListaEjec, null, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {

            ArrayList<String> listaEjecutArray = new ArrayList<>();

            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject dtsSpinersJSON = new JSONObject(response.get(i).toString());
                    String ejecutor  = dtsSpinersJSON.optString("ejecutoresOTs");

                    if (!ejecutor.isEmpty()){
                        listaEjecutArray.add(ejecutor);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No se pudo obtene datos de Spinners " + response, Toast.LENGTH_SHORT).show();
                    //progressDialog.hide();
                }
            }

            //Adapter Spinner Ejecutores
            ArrayAdapter<CharSequence> adapterEject = new ArrayAdapter(getContext(), R.layout.zspinners_items, listaEjecutArray); //
            spinnerEjecut.setAdapter(adapterEject);

            progressBar.setVisibility(View.GONE);
        }
    },      new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            progressBar.setVisibility(View.GONE);
            error.printStackTrace();
            Log.d("ErrorResponse: ", error.toString());
        }
    });

        //SE REALIZA CONSULTA A BD
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(consultaDtsSpiners);
    }
    */



    /*
    // TABLE LAYAOT EN EL ARCHIVO XML
    <ScrollView
        android:id="@+id/scrollVertTbl"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_marginTop="10dp"
        android:layout_marginStart="10dp"
        android:layout_marginEnd="10dp"
        android:layout_marginBottom="10dp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@id/divider2"
        app:layout_constraintBottom_toBottomOf="parent">

        <HorizontalScrollView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toBottomOf="@id/imgBtnFechaInic">

            <TableLayout
                android:id="@+id/tblListaOTs"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintTop_toBottomOf="@id/imgBtnFechaInic"/>

        </HorizontalScrollView>

    </ScrollView>
    */

    /*
    private void filtrarTabalOTsPorEjecutor(String ejectSelected){
    //************************************************************
        for(int i = 0; i < tableLayout.getChildCount(); i++){

            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            TextView textView = (TextView) tableRow.getChildAt(3);
            String tipoEject = textView.getText().toString();
            int numFila = tableLayout.indexOfChild(tableRow);

            if(!ejectSelected.equals("---")){
                if (!tipoEject.equals(ejectSelected)){
                    if(numFila > 0){
                        tableRow.setVisibility(View.GONE);
                    }
                } else {
                    tableRow.setVisibility(View.VISIBLE);
                }
            } else{
                tableRow.setVisibility(View.VISIBLE);
            }
        }
    }
    */


    // AGREGAR INFORMACION A UNA TABLA
    /*
    for (int i=0; i<jsonArray.length(); i++) {
        try {
            JSONObject objFila = jsonArray.getJSONObject(i);

            String nombreFalla = objFila.getString("nombreFalla");
            String tmpParoProd = objFila.getString("tmpParoProd");

            TextView tvColumn0 = new TextView(getContext());
            tvColumn0.setText(nombreFalla);
            tvColumn0.setBackgroundResource(R.drawable.style_edittex_tbls);

            TextView tvColumn1 = new TextView(getContext());
            tvColumn1.setText("1");
            tvColumn1.setGravity(Gravity.CENTER);
            tvColumn1.setBackgroundResource(R.drawable.style_edittex_tbls);

            TextView tvColumn2 = new TextView(getContext());
            tvColumn2.setText(tmpParoProd);
            tvColumn2.setGravity(Gravity.CENTER);
            tvColumn2.setBackgroundResource(R.drawable.style_edittex_tbls);

            TextView tvColumn3 = new TextView(getContext());
            tvColumn3.setText("Ver Detalle");
            tvColumn3.setTextColor(Color.parseColor("#0052cc"));
            tvColumn3.setGravity(Gravity.CENTER);
            tvColumn3.setBackgroundResource(R.drawable.style_edittex_tbls);
            tvColumn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verDetalleDeFalla(nombreFalla);
                }
            });

            TextView tvColumn4 = new TextView(getContext());
            tvColumn4.setText("Ver Grafica");
            tvColumn4.setTextColor(Color.parseColor("#0052cc"));
            tvColumn4.setGravity(Gravity.CENTER);
            tvColumn4.setBackgroundResource(R.drawable.style_edittex_tbls);
            tvColumn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verGraficaDeFalla(nombreFalla);
                }
            });

            TableRow filaTabla = new TableRow(getContext());
            filaTabla.addView(tvColumn0);
            filaTabla.addView(tvColumn1);
            filaTabla.addView(tvColumn2);
            filaTabla.addView(tvColumn3);
            filaTabla.addView(tvColumn4);

            tblListaFallas.addView(filaTabla);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    } */



    /* METODO QUE MUESTRA LISTADO DE OTs EN UNA TABLA
    *************************************************
    private void setListaDeOrdenesTrabajo(){
    //**************************************
        progressBar.setVisibility(View.VISIBLE);
        String urlListaOTs = "";

        if (getOTs30days.equals("Si")){
             urlListaOTs = urlListaOTs30days;

        }else {  // Se esta llamando a una nueva lista entre dos fechas
            urlListaOTs = urlListOTs2Fechas;
            tableLayout.removeAllViews();
        }

        String fechaInic  = datePickerFechaInic;
        String fechaFinal = datePickerFechafinal;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlListaOTs, new Response.Listener<String>() {

        //TRAE LOS TITULOS DE LA TABLA EN EL IDIOMA DE CONFUGURACION
        String tblHeadNumOT = getResources().getString(R.string.tblHeadNumOT);
        String tblHeadTrabSolic = getResources().getString(R.string.tblHeadTrabSolic);
        String tblHeadNombEquip = getResources().getString(R.string.tblHeadNombEquip);
        String tbHeadEjecutor = getResources().getString(R.string.tbHeadEjecutor);
        String tblHeadPriorid = getResources().getString(R.string.tblHeadPriorid);
        String tblHeadStatusOT = getResources().getString(R.string.tblHeadStatusOT);
        String tblHeadFecha = getResources().getString(R.string.tblHeadFecha);
        String tblHeadVerOT = getResources().getString(R.string.tblHeadVerOT);

        @Override
        public void onResponse(String response) {
            try{
                JSONArray jsonArray = new JSONArray(response);

                //Header de la tabla
                TextView columnH0 = new TextView(getContext()); columnH0.setText(tblHeadNumOT);
                columnH0.setBackgroundResource(R.drawable.style_tbls_header);

                TextView columnH1 = new TextView(getContext()); columnH1.setText(tblHeadTrabSolic);
                columnH1.setBackgroundResource(R.drawable.style_tbls_header);

                TextView columnN1 = new TextView(getContext()); columnN1.setText(tblHeadNombEquip);
                columnN1.setBackgroundResource(R.drawable.style_tbls_header);

                TextView columnH2 = new TextView(getContext()); columnH2.setText(tbHeadEjecutor);
                columnH2.setBackgroundResource(R.drawable.style_tbls_header);

                TextView columnH3 = new TextView(getContext()); columnH3.setText(tblHeadPriorid);
                columnH3.setBackgroundResource(R.drawable.style_tbls_header);

                TextView columnHJ = new TextView(getContext()); columnHJ.setText(tblHeadStatusOT);
                columnHJ.setBackgroundResource(R.drawable.style_tbls_header);

                TextView columnH4 = new TextView(getContext()); columnH4.setText(tblHeadFecha);
                columnH4.setBackgroundResource(R.drawable.style_tbls_header);

                TextView columnHK = new TextView(getContext()); columnHK.setText(tblHeadVerOT);
                columnHK.setBackgroundResource(R.drawable.style_tbls_header);

                TableRow filaTablaHd = new TableRow(getContext());
                filaTablaHd.addView(columnH0);
                filaTablaHd.addView(columnH1);
                filaTablaHd.addView(columnN1);
                filaTablaHd.addView(columnH2);
                filaTablaHd.addView(columnH3);
                filaTablaHd.addView(columnHJ);
                filaTablaHd.addView(columnH4);
                filaTablaHd.addView(columnHK);

                tableLayout.addView(filaTablaHd);

                for (int i=0; i<jsonArray.length(); i++) {
                    try {
                        JSONObject objFila = jsonArray.getJSONObject(i);

                        String idOT = objFila.getString("idOT");
                        String trabSolicit = objFila.getString("trabajoSolicit");
                        String nombrEquip = objFila.getString("nombEquipo");
                        String persEjecut = objFila.getString("persEjecutor");
                        String prioridadOT = objFila.getString("prioridadOT");
                        String estatusOT = objFila.getString("estatusOT");
                        String fechaIngrOT = objFila.getString("fechaIngresoOT");

                        if(trabSolicit.length() > 40){
                            trabSolicit = trabSolicit.substring(0, 39) + " ...";
                        }

                        TextView column0 = new TextView(getContext()); column0.setText(idOT);
                        column0.setBackgroundResource(R.drawable.style_edittex_tbls);

                        TextView column1 = new TextView(getContext()); column1.setText(trabSolicit);
                        column1.setBackgroundResource(R.drawable.style_edittex_tbls);

                        TextView columnI = new TextView(getContext()); columnI.setText(nombrEquip);
                        columnI.setBackgroundResource(R.drawable.style_edittex_tbls);

                        TextView column2 = new TextView(getContext()); column2.setText(persEjecut);
                        column2.setBackgroundResource(R.drawable.style_edittex_tbls);

                        TextView column3 = new TextView(getContext()); column3.setText(prioridadOT);
                        column3.setBackgroundResource(R.drawable.style_edittex_tbls);

                        TextView columnJ = new TextView(getContext()); columnJ.setText(estatusOT);
                        columnJ.setBackgroundResource(R.drawable.style_edittex_tbls);

                        TextView column4 = new TextView(getContext()); column4.setText(fechaIngrOT);
                        column4.setBackgroundResource(R.drawable.style_edittex_tbls);

                        ImageButton btnVerOT = new ImageButton(getContext());
                        btnVerOT.setImageResource(R.drawable.eyes_icon);
                        //AGREGAMOS UN LISTENER PARA CADA BOTON
                        View.OnClickListener vetOtListener = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mostraOrdenDeTrabajo(idOT);
                            }
                        };
                        btnVerOT.setOnClickListener(vetOtListener);

                        btnVerOT.setMaxWidth(10);


                        TableRow filaTabla = new TableRow(getContext());
                        filaTabla.addView(column0);
                        filaTabla.addView(column1);
                        filaTabla.addView(columnI);
                        filaTabla.addView(column2);
                        filaTabla.addView(column3);
                        filaTabla.addView(columnJ);
                        filaTabla.addView(column4);
                        filaTabla.addView(btnVerOT);

                        tableLayout.addView(filaTabla);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                progressBar.setVisibility(View.GONE);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR: ", error.getMessage());

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                String nombSolictOT = StaticConfig.numbRealUser;
                Map<String, String> params = new HashMap<String, String>();

                params.put("fechaInic", fechaInic);
                params.put("fechaFinal", fechaFinal);
                params.put("nombSolictOT", nombSolictOT);
                return params;
            }
        };

        // Añade la peticion a la cola
           VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }
    */


    /*

    private void getListaEjecutoresSpinner(){
    //***************************************
        progressBar.setVisibility(View.VISIBLE);

    String urlSpinners = "";
    String idiomaSpinners = StaticConfig.idiomaSpinners;

        switch(idiomaSpinners) {
        case "español":
            urlSpinners = urlSpinnersEsp;
            break;

        case "portugues":
            urlSpinners = urlSpinnersPort;
            break;

        default:  //es idioma Ingles
            urlSpinners = urlSpinnersIngl;
    }

    //SE CONSULTAN Y SE AGREGA LOS DATOS A LOS SPINNERS
    JsonArrayRequest consultaDtsSpiners = new JsonArrayRequest(Request.Method.GET, urlSpinners, null, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {

            ArrayList<String> listaEjecutArray = new ArrayList<>();

            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject dtsSpinersJSON = new JSONObject(response.get(i).toString());
                    String ejecutor  = dtsSpinersJSON.optString("ejecutoresOTs");

                    if (!ejecutor.isEmpty()){
                        listaEjecutArray.add(ejecutor);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No se pudo obtene datos de Spinners " + response, Toast.LENGTH_SHORT).show();
                    //progressDialog.hide();
                }
            }

            //Adapter Spinner Ejecutores
            ArrayAdapter<CharSequence> adapterEject = new ArrayAdapter(getContext(), R.layout.zspinners_items, listaEjecutArray); //
            spinnerEjecut.setAdapter(adapterEject);

            progressBar.setVisibility(View.GONE);
        }
    },      new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            progressBar.setVisibility(View.GONE);
            error.printStackTrace();
            Log.d("ErrorResponse: ", error.toString());
        }
    });

    //SE REALIZA CONSULTA A BD
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(consultaDtsSpiners);
}
    */



}
