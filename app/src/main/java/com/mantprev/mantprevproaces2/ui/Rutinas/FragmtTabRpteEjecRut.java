package com.mantprev.mantprevproaces2.ui.Rutinas;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.mantprev.mantprevproaces2.ModelosDTO1.PersonalTecn;
import com.mantprev.mantprevproaces2.ModelosDTO1.RtesEjecRuts;
import com.mantprev.mantprevproaces2.ModelosDTO1.RtesPersEjecRuts;
import com.mantprev.mantprevproaces2.ModelosDTO1.RtesServExtEjecRuts;
import com.mantprev.mantprevproaces2.ModelosDTO2.ReptesReptos_DTO;
import com.mantprev.mantprevproaces2.ModelosDTO2.Usuarios_DTO;
import com.mantprev.mantprevproaces2.R;
import com.mantprev.mantprevproaces2.adapters.ExpandListFallasAdapter;
import com.mantprev.mantprevproaces2.retrofit.DataServices_Intf;
import com.mantprev.mantprevproaces2.retrofit.Retrofit_Instance;
import com.mantprev.mantprevproaces2.utilities.MetodosStaticos;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmtTabRpteEjecRut extends Fragment {

    public FragmtTabRpteEjecRut() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState); }

    private int idRpteEjec;
    private String numRutina;
    private TextView tvFechaFinaliz, tvNombrSupRut, tvNombrePers, tvIndicPersonal, tvEncabesadoPers, tvEncabesadoHrs,
            tvIndicRepuest, tvEncabesReptos, tvEncabesCosto, tvIndicServExt, tvEncabesSevExt, tvEncabesCostoServExt;
    private EditText etHrsParoProduc, etHrsTrabajo, etCalidadTrab, etReportEjec, etNombRecivTrab;
    private EditText etHrsTrabajo2, etCostoRepsto, etNombreRepsto, etNombreServExt, etCostoServExt;
    private ImageButton ibAgregPerTecTbl, ibAgregReptoTbl, ibAgregServExtTbl;
    private TableLayout tblPersTecn, tblRepuestos, tblServExt;;
    private ProgressBar progressBar;
    private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;
    private AlertDialog alertDialog2;
    private TextView tvAgregarFotosRepte;
    private CardView cvRepuestos, cvServExt;
    private Button btnGuardaRepEjec;
    private View root;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragmt_tab_rpte_ejec_rut, container, false);

        idRpteEjec = MetodosStaticos.idRepteEjecRut;
        numRutina = MetodosStaticos.numRutina;
        
        tvFechaFinaliz = (TextView) root.findViewById(R.id.etFechaEjecuc);
        etHrsParoProduc = (EditText) root.findViewById(R.id.etHrsProdStop);
        etHrsTrabajo = (EditText) root.findViewById(R.id.etHrsLabor);
        etCalidadTrab = (EditText) root.findViewById(R.id.etCalidadTrab);
        etReportEjec = (EditText) root.findViewById(R.id.etReportEjec);
        tvNombrSupRut = (TextView) root.findViewById(R.id.tvNombSupRut);
        etNombRecivTrab = (EditText) root.findViewById(R.id.etNombRecivTrab);
        tvNombrePers = (TextView) root.findViewById(R.id.tvNombrePers);
        etHrsTrabajo2 = (EditText) root.findViewById(R.id.etHrsTrabajo2);
        tblPersTecn = (TableLayout) root.findViewById(R.id.tblPersTecn);
        tvIndicPersonal = (TextView) root.findViewById(R.id.tvIndicPersonal);
        tvEncabesadoPers = (TextView) root.findViewById(R.id.tvEncabesadoPers);
        tvEncabesadoHrs = (TextView) root.findViewById(R.id.tvEncabesadoHrs);
        tvIndicRepuest = (TextView) root.findViewById(R.id.tvIndicRepuest); //,
        tvEncabesReptos = (TextView) root.findViewById(R.id.tvEncabesReptos);
        tvEncabesCosto = (TextView) root.findViewById(R.id.tvEncabesCosto);
        tvIndicServExt = (TextView) root.findViewById(R.id.tvIndicServExt);
        tvEncabesSevExt = (TextView) root.findViewById(R.id.tvEncabesSevExt);
        tvEncabesCostoServExt = (TextView) root.findViewById(R.id.tvEncabesCostoServExt);

        etNombreRepsto = (EditText) root.findViewById(R.id.etNombreRepsto);
        etCostoRepsto = (EditText) root.findViewById(R.id.etCostoRepsto);
        tblRepuestos = (TableLayout) root.findViewById(R.id.tblRepuestos);

        etNombreServExt = (EditText) root.findViewById(R.id.etNombreServExt);
        etCostoServExt = (EditText) root.findViewById(R.id.etCostoServExt);
        tblServExt = (TableLayout) root.findViewById(R.id.tblServExt);
        cvRepuestos = (CardView) root.findViewById(R.id.cvRepuestos);
        cvServExt = (CardView) root.findViewById(R.id.cvServExt);
        tvAgregarFotosRepte = (TextView) root.findViewById(R.id.tvAgregarFotosRepte);
        btnGuardaRepEjec = (Button) root.findViewById(R.id.btnGuardaRepEjec);
        progressBar = (ProgressBar) root.findViewById(R.id.progresBar);

        ibAgregPerTecTbl = (ImageButton) root.findViewById(R.id.ibAgregarTecn);
        ibAgregReptoTbl = (ImageButton) root.findViewById(R.id.ibAgregReptoTbl);
        ibAgregServExtTbl = (ImageButton) root.findViewById(R.id.ibAgregServExtTbl);
        TextView tvIndicRepuest = (TextView) root.findViewById(R.id.tvIndicRepuest);
        TextView tvIndicServExt = (TextView) root.findViewById(R.id.tvIndicServExt);
        TextView tvRepteEjec = (TextView) root.findViewById(R.id.tvIdRepteEjec);
        TextView tvNumRut = (TextView) root.findViewById(R.id.tvNumRut);

        String idRteEjecSt = Integer.toString(idRpteEjec);
        tvRepteEjec.setText(idRteEjecSt);  //tv hidden
        tvNumRut.setText(numRutina);

        tvFechaFinaliz.setOnClickListener(new View.OnClickListener() {  //SELECCIONAR FECHA
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });

        tvNombrSupRut.setOnClickListener(new View.OnClickListener() {   //SELECCIONAR NOMBRE SUPERVISOR
            @Override
            public void onClick(View v) {
                getListaDeSupervisores();
            }
        });

        tvNombrePers.setOnClickListener(new View.OnClickListener() {   //SELECCIONAR NOMBRE PERSOANL TECTNICO
            @Override
            public void onClick(View v) {
                getArbolPersTecnico();
            }
        });

        ibAgregPerTecTbl.setOnClickListener(new View.OnClickListener() {  //AGREGAR PERSONAL TECN. A TABLA
            @Override
            public void onClick(View v) {
                agregarPersTecnTabla();
            }
        });

        ibAgregReptoTbl.setOnClickListener(new View.OnClickListener() {  //AGREGA REPUESTO A LA TABLA
            @Override
            public void onClick(View v) {
                agregarRepuestosTabla();
            }
        });

        ibAgregServExtTbl.setOnClickListener(new View.OnClickListener() {  //AGREGA SERV EXT. A LA TABLA
            @Override
            public void onClick(View v) {
                agregarServExtTabla();
            }
        });

        tvIndicRepuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int isVisible = cvRepuestos.getVisibility();

                if (isVisible == View.VISIBLE){
                    cvRepuestos.setVisibility(View.GONE);
                } else {
                    cvRepuestos.setVisibility(View.VISIBLE);
                }
            }
        });

        tvIndicServExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int isVisible = cvServExt.getVisibility();

                if (isVisible == View.VISIBLE){
                    cvServExt.setVisibility(View.GONE);
                } else {
                    cvServExt.setVisibility(View.VISIBLE);
                }
            }
        });

        tvAgregarFotosRepte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idRpteEjec == 0){
                    mostrarOpcionesFotos();

                } else if(idRpteEjec > 0){
                    MetodosStaticos.idRepteEjecRut = idRpteEjec;
                    MetodosStaticos.fotosDeCierroOT = "Rut_repteEjec"; //Busca las fotos anexadas en el Repte Ejec Rut
                    Navigation.findNavController(root).navigate(R.id.fragmentImages);
                }
            }
        });

        btnGuardaRepEjec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarReporteEjecucionRut();
            }
        });

        progressBar.setVisibility(View.GONE);
        cvRepuestos.setVisibility(View.GONE);
        cvServExt.setVisibility(View.GONE);
        String tvTexto = getResources().getString(R.string.tvAgregarFotosCierre) + " (0)";
        tvAgregarFotosRepte.setText(tvTexto);

        setFechaDeHoy();
        activityResult();
        cargarRepteEjecucPrevio(idRpteEjec);

        return root;
    }

    private void cargarRepteEjecucPrevio(int idRpteEjec){
    /*************************************/
        if (idRpteEjec > 0){
            btnGuardaRepEjec.setEnabled(false); //Para evitar edicion de repte de ejecucuón
            progressBar.setVisibility(View.VISIBLE);

            DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
            Call<RtesEjecRuts> call = service.getRepteEjecRutina(idRpteEjec);

            call.enqueue(new Callback<RtesEjecRuts>() {
                @Override
                public void onResponse(Call<RtesEjecRuts> call, Response<RtesEjecRuts> response) {
                    if(response.isSuccessful() && response.body() != null){
                        RtesEjecRuts rteEjecRut = response.body();

                        String fechaEjec = rteEjecRut.getFechaTrabajo();
                        String hrsParoPr = rteEjecRut.getTpoParoProduc() + "";
                        String hrsTrabaj = rteEjecRut.getTmpoRealTrab() + "";
                        String caliddTra = rteEjecRut.getCalidadTrab() + "";
                        String reportHis = rteEjecRut.getReporteHistor();
                        String nombreSpv = rteEjecRut.getNombSuperv();
                        String recibTrab = rteEjecRut.getPersRecivTrab();
                        int cantRptosUtz = rteEjecRut.getCantRptosUtiliz();
                        int cantServExtr = rteEjecRut.getCantServExter();
                        int cantFtsRepte = rteEjecRut.getCantFotosCierre();

                        etNombRecivTrab.setText(fechaEjec);
                        etHrsParoProduc.setText(hrsParoPr);
                        etHrsTrabajo.setText(hrsTrabaj);
                        etCalidadTrab.setText(caliddTra);
                        etReportEjec.setText(reportHis);
                        tvNombrSupRut.setText(nombreSpv);
                        etNombRecivTrab.setText(recibTrab);

                        String tvTexto = "Ver fotos del reporte de ejecución (" + cantFtsRepte + ")" ;
                        tvAgregarFotosRepte.setText(tvTexto);

                        mostrarPersEjectorRutina(idRpteEjec);
                        mostrarRepuestoutilizados(idRpteEjec, cantRptosUtz);
                        mostrarServExtUtilizados(idRpteEjec, cantServExtr);

                        progressBar.setVisibility(View.GONE);

                    } else {
                        Toast.makeText(getContext(), "No se pudo cargar Reporte ejecucion previo...", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<RtesEjecRuts> call, Throwable throwable) {
                    Toast.makeText(getContext(), "No se pudo cargar Reporte ejec. previo.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    private void mostrarPersEjectorRutina(int idRpteEjec){
    /***************************************************/
        progressBar.setVisibility(View.VISIBLE);
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<List<RtesPersEjecRuts>> call = service.getListPersEjecRutina(idRpteEjec);
        call.enqueue(new Callback<List<RtesPersEjecRuts>>() {
            @Override
            public void onResponse(Call<List<RtesPersEjecRuts>> call, Response<List<RtesPersEjecRuts>> response) {
                if(response.isSuccessful() && response.body() != null){

                    String indicasiones = "Personal que realizo el trabajo";
                    tvIndicPersonal.setText(indicasiones);
                    tvEncabesadoPers.setVisibility(View.GONE);
                    tvEncabesadoHrs.setVisibility(View.GONE);
                    tvNombrePers.setVisibility(View.GONE);
                    etHrsTrabajo2.setVisibility(View.GONE);
                    ibAgregPerTecTbl.setVisibility(View.GONE);

                    List<RtesPersEjecRuts> listReptsPer = response.body();

                    for(int i=0; i<listReptsPer.size(); i++){
                        RtesPersEjecRuts reptePers = listReptsPer.get(i);
                        String nombrPers = reptePers.getComdNombrEmpl();
                        String hrsTrabaj = reptePers.getCantHrsNorm() + "";

                        TableRow filaTabla = new TableRow(getContext());

                        TextView tvColum0 = new TextView(getContext());
                        tvColum0.setBackgroundResource(R.drawable.style_edittex_tbls);
                        tvColum0.setGravity(1);
                        tvColum0.setText("--");

                        TextView tvColum1 = new TextView(getContext());
                        tvColum1.setBackgroundResource(R.drawable.style_edittex_tbls);
                        tvColum1.setText(nombrPers);
                        tvColum1.setTextColor(Color.BLACK);

                        TextView tvColum2 = new TextView(getContext());
                        tvColum2.setBackgroundResource(R.drawable.style_edittex_tbls);
                        tvColum2.setGravity(1);
                        tvColum2.setText(hrsTrabaj);
                        tvColum2.setTextColor(Color.BLACK);

                        TextView tvColum3 = new TextView(getContext());
                        tvColum3.setBackgroundResource(R.drawable.style_edittex_tbls);
                        tvColum3.setGravity(1);
                        tvColum3.setText("---");

                        filaTabla.addView(tvColum0);
                        filaTabla.addView(tvColum1);
                        filaTabla.addView(tvColum2);
                        filaTabla.addView(tvColum3);
                        tblPersTecn.addView(filaTabla);
                    }
                    progressBar.setVisibility(View.GONE);

                } else {
                    Toast.makeText(getContext(), "No se pudo cargar ejecutores Rut ...", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<RtesPersEjecRuts>> call, Throwable throwable) {
                Toast.makeText(getContext(), "No se pudo cargar ejecutores RUT", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void mostrarRepuestoutilizados(int idRpteEjec, int canRptos){
    /******************************************************************/
        String indicasiones = "Toque para ver Repuestos Utilizados" +" ("+ canRptos +")";
        tvIndicRepuest.setText(indicasiones);
        etNombreRepsto.setVisibility(View.GONE);
        etCostoRepsto.setVisibility(View.GONE);
        ibAgregReptoTbl.setVisibility(View.GONE);
        tvEncabesReptos.setVisibility(View.GONE);
        tvEncabesCosto.setVisibility(View.GONE);

        if(canRptos > 0){
            progressBar.setVisibility(View.VISIBLE);
            DataServices_Intf service2 = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
            Call<List<ReptesReptos_DTO>> call2 = service2.getListReptosEjecRutina(idRpteEjec);
            call2.enqueue(new Callback<List<ReptesReptos_DTO>>() {
                @Override
                public void onResponse(Call<List<ReptesReptos_DTO>> call, Response<List<ReptesReptos_DTO>> response) {
                    if(response.isSuccessful() && response.body() != null){

                        List<ReptesReptos_DTO> listaReptos = response.body();

                        for(int i=0; i<listaReptos.size(); i++){
                            ReptesReptos_DTO repteRepto = listaReptos.get(i);
                            String nombreRpto = repteRepto.getNombreRep();
                            String costoRpsto = repteRepto.getCostTotal();

                            TableRow filaTabla = new TableRow(getContext());

                            TextView tvColum0 = new TextView(getContext());
                            tvColum0.setBackgroundResource(R.drawable.style_edittex_tbls);
                            tvColum0.setText(nombreRpto);
                            tvColum0.setTextColor(Color.BLACK);

                            TextView tvColum1 = new TextView(getContext());
                            tvColum1.setBackgroundResource(R.drawable.style_edittex_tbls);
                            tvColum1.setGravity(1);
                            tvColum1.setText(costoRpsto);
                            tvColum1.setTextColor(Color.BLACK);

                            TextView tvColum2 = new TextView(getContext());
                            tvColum2.setBackgroundResource(R.drawable.style_edittex_tbls);
                            tvColum2.setGravity(1);
                            tvColum2.setText("---");
                            tvColum2.setTextColor(Color.BLACK);

                            filaTabla.addView(tvColum0);
                            filaTabla.addView(tvColum1);
                            filaTabla.addView(tvColum2);

                            tblRepuestos.addView(filaTabla);
                        }
                        progressBar.setVisibility(View.GONE);

                    } else {
                        Toast.makeText(getContext(), "No se pudo cargar repuestos Rut ...", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<List<ReptesReptos_DTO>> call, Throwable throwable) {
                    Toast.makeText(getContext(), "No se pudo cargar Repuestos RUT", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    private void mostrarServExtUtilizados(int idRpteEjec, int canServExt){
    /******************************************************************/
        String indicasiones = "Toque para ver servicios externes" +" ("+ canServExt +")";
        tvIndicServExt.setText(indicasiones);
        tvEncabesSevExt.setVisibility(View.GONE);
        tvEncabesCostoServExt.setVisibility(View.GONE);
        etNombreServExt.setVisibility(View.GONE);
        etCostoServExt.setVisibility(View.GONE);
        ibAgregServExtTbl.setVisibility(View.GONE);

        if(canServExt > 0){
            progressBar.setVisibility(View.VISIBLE);
            DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
            Call<List<RtesServExtEjecRuts>> call = service.getListServExtEjecRutina(idRpteEjec);
            call.enqueue(new Callback<List<RtesServExtEjecRuts>>() {
                @Override
                public void onResponse(Call<List<RtesServExtEjecRuts>> call, Response<List<RtesServExtEjecRuts>> response) {
                    if(response.isSuccessful() && response.body() != null){

                        List<RtesServExtEjecRuts> listaServExt = response.body();

                        for(int i=0; i<listaServExt.size(); i++){
                            RtesServExtEjecRuts servExt = listaServExt.get(i);
                            String nombreServExt = servExt.getNombreServic();
                            String costoServExtr = servExt.getCostoServic() + "";

                            TableRow filaTabla = new TableRow(getContext());

                            TextView tvColum0 = new TextView(getContext());
                            tvColum0.setBackgroundResource(R.drawable.style_edittex_tbls);
                            tvColum0.setText(nombreServExt);
                            tvColum0.setTextColor(Color.BLACK);

                            TextView tvColum1 = new TextView(getContext());
                            tvColum1.setBackgroundResource(R.drawable.style_edittex_tbls);
                            tvColum1.setGravity(1);
                            tvColum1.setText(costoServExtr);
                            tvColum1.setTextColor(Color.BLACK);

                            TextView tvColum2 = new TextView(getContext());
                            tvColum2.setBackgroundResource(R.drawable.style_edittex_tbls);
                            tvColum2.setGravity(1);
                            tvColum2.setText("---");
                            tvColum2.setTextColor(Color.BLACK);

                            filaTabla.addView(tvColum0);
                            filaTabla.addView(tvColum1);
                            filaTabla.addView(tvColum2);

                            tblServExt.addView(filaTabla);
                        }
                        progressBar.setVisibility(View.GONE);

                    } else {
                        Toast.makeText(getContext(), "No se pudo cargar Serv.Ext Rut ...", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<List<RtesServExtEjecRuts>> call, Throwable throwable) {
                    Toast.makeText(getContext(), "No se pudo cargar Serv.Ext RUT", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    private static final int PERMISO_FOTOS_CODE = 99;
    private final String manifestPermiso = Manifest.permission.READ_MEDIA_IMAGES;
    private void mostrarOpcionesFotos() {
    /**********************************/
        //TRAE LOS TITULOS DE LA VENTANA DESDE String.xml
        String titleOpc = getResources().getString(R.string.titleOpc);
        String opcTakePict = getResources().getString(R.string.opcTakePict);
        String opcChoseGall = getResources().getString(R.string.opcChoseGall);
        String opcCancel = getResources().getString(R.string.opcCancel);

        final CharSequence[] opciones = {opcTakePict, opcChoseGall, opcCancel};
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
        dialogBuilder.setTitle(titleOpc);

        dialogBuilder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(opciones[which].equals(opcTakePict)){
                    camaraPermiso.launch(Manifest.permission.CAMERA);
                }
                if(opciones[which].equals(opcChoseGall)){

                    if(Build.VERSION.SDK_INT >= 33){
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{manifestPermiso}, PERMISO_FOTOS_CODE);
                        openGallery();
                    } else {
                        openGallery();
                    }
                }
                if(opciones[which].equals(opcCancel)){
                    dialog.dismiss();
                }
            }
        });

        dialogBuilder.show();
    }


    //Verifica permisos para activar la camara o abrir galeria de fotos
    ActivityResultLauncher<String> camaraPermiso = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if(result){
                activarCamara();
            }
        }
    });


    //Para trabajar con Fotos
    static final int REQUEST_IMAGE_CAPTURE = 100;
    static final int REQUEST_TAKE_PHOTO = 101;
    private Uri imageUri = null;
    Bitmap imageBitmap;
    ArrayList<Uri> listaImgsUri = new ArrayList<>();
    String currentPhotoPath;


    //ACTIVA LA CAMARA PARA TOMAR FOTO
    private void activarCamara(){
    //****************************
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getContext().getPackageManager()) != null){
            File photoFile = null;

            try {
                photoFile = createImageFile();  // Create the File where the photo should go

            } catch (IOException ex){ex.getMessage();}

            if (photoFile != null){
                String authority = "com.mantprev.mantprevproaces2" + ".provider";    //BuildConfig.APPLICATION_ID
                Uri photoUri = FileProvider.getUriForFile(requireContext(), authority, photoFile);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                camaraLauncher.launch(intent);
            }
        }
    }


    //MANEJA LA FOTO Y AGREGA A LA LISTA DE FOTOS
    private final ActivityResultLauncher<Intent> camaraLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {

                        @Override
                        public void onActivityResult(ActivityResult result) {

                            File file = new File(currentPhotoPath);
                            Uri uri = Uri.fromFile(file);
                            Bitmap bitmap = null;

                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                            imageUri = getImageUri(getContext(), bitmap);
                            listaImgsUri.add(imageUri);

                            int cantFotos = listaImgsUri.size();
                            String tvTexto = getResources().getString(R.string.tvAgregarFotosCierre) + " (" + cantFotos + ")";
                            tvAgregarFotosRepte.setText(tvTexto);
                        }
                    });

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        /*********************************************************/
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, getNombreDeFoto(), null);
        return Uri.parse(path);
    }


    private File createImageFile() throws IOException {
        /************************************************/
        String imgFileName = getNombreDeFoto();
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imgFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();

        return image;
    }

    private Bitmap getResisedBitmap(Bitmap bitmap, int maxSize) {
        /***********************************************************/
        int width  = bitmap.getWidth();
        int height = bitmap.getHeight();

        if (width<=maxSize && height<=maxSize){
            return bitmap;
        }

        float bitMapRatio = (float) width / (float) height;
        if (bitMapRatio > 1){
            width = maxSize;
            height = (int) (width/bitMapRatio);

        } else {
            height = maxSize;
            width = (int) (height * bitMapRatio);
        }

        Bitmap bitmapResized = Bitmap.createScaledBitmap(bitmap, width, height, true);

        return bitmapResized;
    }


    private ActivityResultLauncher<Intent> intentLauncher;
    private void openGallery(){
        /**************************/
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);

        intentLauncher.launch(Intent.createChooser(intent, getResources().getString(R.string.indicacion)));
    }


    private void activityResult(){  //MANEJA LAS FOTOS QUE SE ANEXARON DE LA GALERIA
        /****************************/
        intentLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            if (result.getData() != null){
                ClipData clipData = result.getData().getClipData();

                if (clipData.getItemCount() > 0){

                    for (int i=0; i<clipData.getItemCount(); i++) {
                        listaImgsUri.add(clipData.getItemAt(i).getUri());
                    }

                    int cantFotos = listaImgsUri.size();
                    String tvTexto = getResources().getString(R.string.tvAgregarFotosCierre) + " (" + cantFotos + ")";
                    tvAgregarFotosRepte.setText(tvTexto);
                }
            }
        });
    }

    String calidTrab, fechaEjec;
    public void guardarReporteEjecucionRut() {
    /**************************************/
        btnGuardaRepEjec.setEnabled(true);

        //CAPTURA LOS DATOS DE EJECUCION
        fechaEjec = datePickerFecha;   //Se utiliza el String en fromato de fecha sql
        calidTrab = etCalidadTrab.getText().toString();
        int idRutEquip = MetodosStaticos.idRutEquip;
        int idEquipo = MetodosStaticos.idEquipo;
        String hrsParoProd = etHrsParoProduc.getText().toString();
        String hrsTrabajo  = etHrsTrabajo.getText().toString();
        String reportHist = etReportEjec.getText().toString();
        String supervOT  = tvNombrSupRut.getText().toString();
        String recibTrab = etNombRecivTrab.getText().toString();
        String semanaRut = MetodosStaticos.semanaRuts;
        String cantFotos = Integer.toString(listaImgsUri.size());
        String cantRptos = Integer.toString(tblRepuestos.getChildCount() - 1); //1 solo tiene el table head
        String cantSrvEx = Integer.toString(tblServExt.getChildCount() - 1);   //1 solo tiene el table head

        if (recibTrab.isEmpty()) {
            recibTrab = "--";
        }

        //Verifica que se hayan ingresado todos los datos
        boolean datosCorrectos = true;

        String nombPers = tvNombrePers.getText().toString();
        String nombRpto = etNombreRepsto.getText().toString();
        String nombServ = etNombreServExt.getText().toString();

        if(!nombPers.isEmpty() || !nombRpto.isEmpty() || !nombServ.isEmpty()){
            Toast.makeText(getContext(), getResources().getString(R.string.msjInformTabl), Toast.LENGTH_LONG).show();
            datosCorrectos = false;
        }

        if (reportHist.equals("")) {
            datosCorrectos = false;
        }
        if (supervOT.equals("---")) {
            datosCorrectos = false;
        }
        if (tblPersTecn.getChildCount() == 0) {
            datosCorrectos = false;
        }

        if (Double.parseDouble(calidTrab) > 10.0) {
            Toast.makeText(getContext(), getResources().getString(R.string.mensajeCalidad), Toast.LENGTH_LONG).show();
            datosCorrectos = false;
        }

        // SI DATOS ESTAN CORRECTOS PROCEDE A GUARDAR EL REPORTE DE EJECUCION
        if (datosCorrectos) {
            progressBar.setVisibility(View.VISIBLE);

            RtesEjecRuts repteEjecRut = new RtesEjecRuts();
            String fechaTrabajo = fechaEjec;

            repteEjecRut.setIdRutEquipo(idRutEquip);
            repteEjecRut.setIdEquipo(idEquipo);
            repteEjecRut.setFechaTrabajo(fechaTrabajo);
            repteEjecRut.setCalidadTrab(Double.parseDouble(calidTrab));
            repteEjecRut.setTpoParoProduc(Double.parseDouble(hrsParoProd));
            repteEjecRut.setTmpoRealTrab(Double.parseDouble(hrsTrabajo));
            repteEjecRut.setNombSuperv(supervOT);
            repteEjecRut.setPersRecivTrab(recibTrab);
            repteEjecRut.setReporteHistor(reportHist);
            repteEjecRut.setSemanaRut(semanaRut);
            repteEjecRut.setCantFotosCierre(Integer.parseInt(cantFotos));
            repteEjecRut.setCantRptosUtiliz(Integer.parseInt(cantRptos));
            repteEjecRut.setCantServExter(Integer.parseInt(cantSrvEx));

            //* RETROFIT
            DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
            Call<String> call = service.guardarReporteEjecRut(repteEjecRut);   //"reptes/saveRepteEjec/"

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {

                    if(response.isSuccessful() && response.body() != null){
                        Toast.makeText(getContext(), getResources().getString(R.string.btnGuardaRepEjecOK), Toast.LENGTH_SHORT).show();
                        int idRpteEjecRut = Integer.parseInt(response.body()); // Trae el id del repte de ejecuc Rut
                        guardarReportPersTecn(idRpteEjecRut);  //GUARDA LOS REPORTES DEL PERSONAL TECNICO

                    } else {
                        Toast.makeText(getContext(), "No se pudo guardar el reporte de Ejecución Rut ...", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    progressBar.setVisibility(View.GONE);
                    throwable.printStackTrace();
                    Log.d("ErrorResponse: ", throwable.toString());
                    Toast.makeText(getContext(), "No se pudo guardar el reporte de Ejecución RUT", Toast.LENGTH_LONG).show();
                }
            });

        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.enterDataMsg), Toast.LENGTH_LONG).show();
        }
    }

    public void guardarReportPersTecn(int idRpteEjecRut) {
    /**************************************************/
        ArrayList<RtesPersEjecRuts> arrayReptesPersn = new ArrayList<>();

        //SE CAPTURA EL LISTADO DE EJECUTORES DE LA OT PARA GUARDARLOS
        RtesPersEjecRuts reptePersn;
        int listaTblPersTecnSze = tblPersTecn.getChildCount();

        for (int i=1; i < listaTblPersTecnSze; i++){

            TableRow filaTbl = (TableRow) tblPersTecn.getChildAt(i);

            TextView textView0 = (TextView) filaTbl.getChildAt(0);
            TextView textView1 = (TextView) filaTbl.getChildAt(1);
            TextView textView2 = (TextView) filaTbl.getChildAt(2);

            String idPersTecn = textView0.getText().toString();
            String nombPerTec = textView1.getText().toString();  //No se guarda en la Base de Datos
            String cantHrsTra = textView2.getText().toString();

            reptePersn = new RtesPersEjecRuts();
            reptePersn.setIdRepteEjecRut(idRpteEjecRut);
            reptePersn.setIdEmpleado(Integer.parseInt(idPersTecn));
            reptePersn.setCantHrsNorm(Double.parseDouble(cantHrsTra));
            reptePersn.setCalidTrabaj(Double.parseDouble(calidTrab));
            reptePersn.setFechaEjec(fechaEjec);

            arrayReptesPersn.add(reptePersn);
        }

        int arrayReptesPersSize = arrayReptesPersn.size();
        for (int i = 0; i < arrayReptesPersSize; i++) {

            RtesPersEjecRuts reptePers = arrayReptesPersn.get(i);

            DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
            Call<String> call = service.guardarReptePersEjecRut(reptePers);       //"reptes/saveReptePers/"
            call.enqueue(new Callback<String>() {

                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    /*
                    if (response.code() == 401){  // El token ha expirado
                        ActivityCerarSesion activCerrarSess = new ActivityCerarSesion();
                        activCerrarSess.cleanSesionDeUsuario(getContext());
                        Toast.makeText(getContext(), "The session has ended", Toast.LENGTH_LONG).show();
                        Navigation.findNavController(root).navigate(R.id.fragmentLogin);
                    }   */

                    if(response.isSuccessful() && response.body() != null){
                        if(!response.body().equals("OK")){
                            Toast.makeText(getContext(), "No se pudo guardar Personal Técnico .....", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(getContext(), "No se pudo guardar Personal Técnico", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    throwable.printStackTrace();
                    Log.d("ErrorResponse: ", throwable.toString());
                    Toast.makeText(getContext(), "No pudo guardar Personal Técnico", Toast.LENGTH_LONG).show();
                }
            });
        }

        guardarReportesReptos(idRpteEjecRut);  //Procede a guardar reporte de repuestos
    }

    public void guardarReportesReptos(int idRpteEjecRut){
    /***************************************************/
        ArrayList<ReptesReptos_DTO> arrayReptesReptos = new ArrayList<>();

        //SE CAPTURA EL LISTADO DE REPUESTOS SI SE INGRESO PARA GUARDARLOS
        if (tblRepuestos.getChildCount() > 1){

            ReptesReptos_DTO repteRepuesto;
            int tablaRepuestosSize = tblRepuestos.getChildCount();

            for (int i=1; i< tablaRepuestosSize; i++){

                TableRow filaTbl = (TableRow) tblRepuestos.getChildAt(i);

                TextView textView0 = (TextView) filaTbl.getChildAt(0);
                TextView textView1 = (TextView) filaTbl.getChildAt(1);

                String nombrRepto = textView0.getText().toString();
                String costoTotal = textView1.getText().toString();

                repteRepuesto = new ReptesReptos_DTO();
                repteRepuesto.setNombreRep(nombrRepto);
                repteRepuesto.setCostTotal(costoTotal);
                repteRepuesto.setDateConsu(fechaEjec);
                repteRepuesto.setIdOrdTrab(Integer.toString(idRpteEjecRut)); //Se envia aqui para no generar nuevo DTO

                arrayReptesReptos.add(repteRepuesto);
            }

            int arrayReptesReptosSize = arrayReptesReptos.size();
            for(int i=0; i< arrayReptesReptosSize; i++) {

                ReptesReptos_DTO repteRepto = arrayReptesReptos.get(i);

                /* RETROFIT */
                DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
                Call<String> call = service.guardarRepteReptosEjecRut(repteRepto);   //"reptes/saveRepteRepto/"

                call.enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                        /*
                        if (response.code() == 401){  // El token ha expirado
                            ActivityCerarSesion activCerrarSess = new ActivityCerarSesion();
                            activCerrarSess.cleanSesionDeUsuario(getContext());
                            Toast.makeText(getContext(), "The session has ended", Toast.LENGTH_LONG).show();
                            Navigation.findNavController(root).navigate(R.id.fragmentLogin);
                        }   */

                        if(response.isSuccessful() && response.body() != null){
                            if(!response.body().equals("OK")){
                                Toast.makeText(getContext(), "No se pudo guardar Repuestos ..... ", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "No se pudo guardar reporte Repuesto", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                        throwable.printStackTrace();
                        Log.d("ErrorResponse: ", throwable.toString());
                        Toast.makeText(getContext(), "No puedo guardar Repuestos", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

        guardarReptesSevExter(idRpteEjecRut);   //Procede a guardar reporte de repuestos
    }


    public void guardarReptesSevExter(int idRpteEjecRut){
    /***********************************************/
        ArrayList<RtesServExtEjecRuts> arrayReptesServExt = new ArrayList<>();

        //CAPTURA EL LISTADO DE SERV. EXTERNOS SI SE INGRESO PARA GUARDARLOS
        if (tblServExt.getChildCount() > 1){

            RtesServExtEjecRuts repteServExt;
            int tablaServExtSize = tblServExt.getChildCount();

            for (int i=1; i< tablaServExtSize; i++){
                TableRow filaTbl = (TableRow) tblServExt.getChildAt(i);
                TextView textView0 = (TextView) filaTbl.getChildAt(0);
                TextView textView1 = (TextView) filaTbl.getChildAt(1);
                String nombreServ = textView0.getText().toString();
                String costoTotal = textView1.getText().toString();

                repteServExt = new RtesServExtEjecRuts();
                repteServExt.setNombreServic(nombreServ);
                repteServExt.setCostoServic(Double.parseDouble(costoTotal));
                repteServExt.setIdRepteEjecRut(idRpteEjecRut);
                repteServExt.setFechaServ(fechaEjec);
                repteServExt.setIdProveedor(1);

                arrayReptesServExt.add(repteServExt);
            }
        }

        int arrayReptesServExtSize = arrayReptesServExt.size();
        for(int i=0; i< arrayReptesServExtSize; i++) {

            RtesServExtEjecRuts repteServExt = arrayReptesServExt.get(i);

            /* RETROFIT */
            DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
            Call<String> call = service.guardarRepteServExtEjecRut(repteServExt);   // "reptes/saveRepteSevExt/"

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    /*
                    if (response.code() == 401){  // El token ha expirado
                        ActivityCerarSesion activCerrarSess = new ActivityCerarSesion();
                        activCerrarSess.cleanSesionDeUsuario(getContext());
                        Toast.makeText(getContext(), "The session has ended", Toast.LENGTH_LONG).show();
                        Navigation.findNavController(root).navigate(R.id.fragmentLogin);
                    }  */

                    if(response.isSuccessful() && response.body() != null){
                        if(!response.body().equals("OK")){
                            Toast.makeText(getContext(), "No se pudo guardar Serv. Ext......", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "No se pudo guardar Servicio Ext", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    throwable.printStackTrace();
                    Log.d("ErrorResponse: ", throwable.toString());
                    Toast.makeText(getContext(), "No se guardo Serv. Ext.", Toast.LENGTH_LONG).show();
                }
            });
        }

        //Vuelve a la lista de Ruts para Reportar Ejec
        progressBar.setVisibility(View.GONE);
        if (!listaImgsUri.isEmpty()){
            uploadImagensToServer(idRpteEjecRut);
        }

        Navigation.findNavController(root).navigate(R.id.fragmentListaRuts);
    }

    public void uploadImagensToServer(int idRpteEjecRut){  //Fotos repte eject Rut
    /*********************************************/
        progressBar.setVisibility(View.VISIBLE);
        int listaImgsUriSize = listaImgsUri.size();

        for(int i=0; i< listaImgsUriSize; i++) {
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(listaImgsUri.get(i));
                Bitmap bitmapImg = BitmapFactory.decodeStream(inputStream);
                Bitmap bitmapResized = getResisedBitmap(bitmapImg, 800);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmapResized.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();

                RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
                MultipartBody.Part fotoFilePart = MultipartBody.Part.createFormData("file", "img.jpg", requestBody);

                //* RETROFIT
                DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
                Call<String> call = service.uploadImgRpteEjecRut(fotoFilePart, idRpteEjecRut);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                        if(response.isSuccessful()){
                            //Toast.makeText(getContext(), response.body(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Failure to send photos !!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                        throwable.printStackTrace();
                        Log.d("ErrorResponse: ", throwable.toString());
                        Toast.makeText(getContext(), "FALLO EN GUARDAR FOTO", Toast.LENGTH_LONG).show();
                    }
                });

                // bitmapImg.recycle();
            } catch (IOException e) { }
        }
        listaImgsUri.clear();  //Limpiar la lista de Uris
    }


    //Metodo para generar una cadena aleatoria de longitud N
    public String getNombreDeFoto() {
        /*******************************/
        int count = 10;

        String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * CARACTERES.length());
            builder.append(CARACTERES.charAt(character));
        }

        String nombreImagen = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        String imageFileName = nombreImagen + ".jpg";

        String nombreFoto = imageFileName;
        nombreFoto = builder + "_" + nombreFoto;

        return nombreFoto;
    }


    //**************************************


    public void agregarServExtTabla(){
        /*********************************/
        String nombreServExt = etNombreServExt.getText().toString();
        String costoServExt  = etCostoServExt.getText().toString();

        if (!costoServExt.isEmpty() && !costoServExt.contains(".")){
            costoServExt = costoServExt + ".00";
        }

        TableRow filaTabla = new TableRow(getContext());

        if (!nombreServExt.isEmpty() && !costoServExt.isEmpty()){ //

            TextView tvColum0 = new TextView(getContext());
            tvColum0.setBackgroundResource(R.drawable.style_edittex_tbls);
            tvColum0.setText(nombreServExt);
            tvColum0.setTextColor(Color.BLACK);

            TextView tvColum1 = new TextView(getContext());
            tvColum1.setBackgroundResource(R.drawable.style_edittex_tbls);
            tvColum1.setGravity(1);
            tvColum1.setText(costoServExt);
            tvColum1.setTextColor(Color.BLACK);

            TextView tvColum2 = new TextView(getContext());
            tvColum2.setBackgroundResource(R.drawable.style_edittex_tbls);
            tvColum2.setGravity(1);
            tvColum2.setText(getResources().getString(R.string.quitarLinea));
            tvColum2.setTextColor(Color.BLACK);

            //AGREGAMOS UN LISTENER PARA CADA texto col2
            View.OnClickListener quitarLineaListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removerServExtDeTabla(filaTabla);
                }
            };
            tvColum2.setOnClickListener(quitarLineaListener);

            filaTabla.addView(tvColum0);
            filaTabla.addView(tvColum1);
            filaTabla.addView(tvColum2);

            tblServExt.addView(filaTabla);
            etNombreServExt.setText("");
            etNombreServExt.requestFocus();

        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.avisoToast03), Toast.LENGTH_SHORT).show();  //avisoToast01
        }
    }


    public void removerServExtDeTabla(TableRow filaTabla){
        /**************************************************/
        tblServExt.removeView(filaTabla);
    }


    public void agregarRepuestosTabla(){
        /*********************************/
        String nombreDeRepto = etNombreRepsto.getText().toString();
        String costoDeRepto  = etCostoRepsto.getText().toString();

        if (!costoDeRepto.isEmpty() && !costoDeRepto.contains(".")){
            costoDeRepto = costoDeRepto + ".00";
        }

        TableRow filaTabla = new TableRow(getContext());

        if (!nombreDeRepto.isEmpty() && !costoDeRepto.isEmpty()){ //

            TextView tvColum0 = new TextView(getContext());
            tvColum0.setBackgroundResource(R.drawable.style_edittex_tbls);
            tvColum0.setText(nombreDeRepto);
            tvColum0.setTextColor(Color.BLACK);

            TextView tvColum1 = new TextView(getContext());
            tvColum1.setBackgroundResource(R.drawable.style_edittex_tbls);
            tvColum1.setGravity(1);
            tvColum1.setText(costoDeRepto);
            tvColum1.setTextColor(Color.BLACK);

            TextView tvColum2 = new TextView(getContext());
            tvColum2.setBackgroundResource(R.drawable.style_edittex_tbls);
            tvColum2.setGravity(1);
            tvColum2.setText(getResources().getString(R.string.quitarLinea));
            tvColum2.setTextColor(Color.BLACK);

            //AGREGAMOS UN LISTENER PARA CADA texto col2
            View.OnClickListener quitarLineaListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removerRptoaDeTabla(filaTabla);
                }
            };
            tvColum2.setOnClickListener(quitarLineaListener);

            filaTabla.addView(tvColum0);
            filaTabla.addView(tvColum1);
            filaTabla.addView(tvColum2);

            tblRepuestos.addView(filaTabla);
            etNombreRepsto.setText("");
            etNombreRepsto.requestFocus();

        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.avisoToast02), Toast.LENGTH_SHORT).show();  //avisoToast01
        }
    }


    public void removerRptoaDeTabla(TableRow filaTabla){
        /**************************************************/
        tblRepuestos.removeView(filaTabla);
    }


    public void getArbolPersTecnico(){
        /*******************************/
        progressBar.setVisibility(View.VISIBLE);

        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<List<PersonalTecn>> call = service.getLstaDePersonalTecn();   //"persTecn/getAll";

        call.enqueue(new Callback<List<PersonalTecn>>() {

            final ArrayList<PersonalTecn> listPersTecn = new ArrayList<>();
            Map<String, List<String>> mapaPersTecn;
            ArrayList<String> listaTiposDeEjecut;    //Lista de Padres de Ejecutores (Electricos, mecánicos, ete)
            ArrayList<String> grupoDeEjecutores;     //Hijos de ejecutores  (Ejecutores agrupados)

            @Override
            public void onResponse(Call<List<PersonalTecn>> call, retrofit2.Response<List<PersonalTecn>> response) {

                /*
                if (response.code() == 401){  // El token ha expirado
                    ActivityCerarSesion activCerrarSess = new ActivityCerarSesion();
                    activCerrarSess.cleanSesionDeUsuario(getContext());
                    Toast.makeText(getContext(), "The session has ended", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(root).navigate(R.id.fragmentLogin);
                }  */

                if(response.isSuccessful() && response.body() != null){
                    listPersTecn.addAll(response.body());

                    //SE CREA LA LISTA DE TIPOS DE EJECUTORES (lista de Padres)
                    mapaPersTecn = new HashMap<String, List<String>>();
                    listaTiposDeEjecut = new ArrayList<>();  //Lista de Padres (electrico, mecánico, ete)

                    int listaPersTecnSize = listPersTecn.size();
                    for (int i=0; i< listaPersTecnSize; i++){

                        String tipoEjecutor  = listPersTecn.get(i).getTipoEjecutor();

                        if (!listaTiposDeEjecut.contains(tipoEjecutor)){
                            listaTiposDeEjecut.add(tipoEjecutor);
                        }
                    }

                    //SE CREAN LOS GRUPOS DE PERSONAL EJECUTOR SEGUN EL TIPO (MECACNICAS, ELECTRICAS, ETC.)
                    int listaTiposEjectSize = listaTiposDeEjecut.size();
                    for (int i=0; i< listaTiposEjectSize; i++){

                        String tipoDeEject = listaTiposDeEjecut.get(i);
                        grupoDeEjecutores = new ArrayList<>();

                        for (int j=0; j< listaPersTecnSize; j++){

                            int idEmpleado = listPersTecn.get(j).getIdEmpleado();
                            String tipoEjecut = listPersTecn.get(j).getTipoEjecutor();
                            String nombrePers = listPersTecn.get(j).getNombre() + " (" +idEmpleado+ ")";

                            if (tipoEjecut.equals(tipoDeEject)){
                                grupoDeEjecutores.add(nombrePers);
                            }
                        }
                        mapaPersTecn.put(tipoDeEject, grupoDeEjecutores);
                    }

                    progressBar.setVisibility(View.GONE);
                    mostrarVentanaPersTecn(listaTiposDeEjecut, mapaPersTecn);

                } else {
                    Toast.makeText(getContext(), "Fallo en cargar lista personal Técnico", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<PersonalTecn>> call, Throwable throwable) {
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());
                Toast.makeText(getContext(), "FALLO EN CARGAR PERS. TECNICO", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void mostrarVentanaPersTecn(ArrayList<String> parentsList, Map<String, List<String>> persTecnMap){
        /******************************************************************************************************/
        final View windowArbolPersTecn = getLayoutInflater().inflate(R.layout.window_lista_perstecn, null);

        expandableListView = windowArbolPersTecn.findViewById(R.id.expandListViewPersTecn);
        expandableListAdapter = new ExpandListFallasAdapter(getContext(), parentsList, persTecnMap); //SE UTILIZA EL MISMO ADAPTADOR DE FALLAS ADAPTER
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;

            @Override
            public void onGroupExpand(int i) {
                if (lastExpandedPosition != -1 && i != lastExpandedPosition){
                    expandableListView.collapseGroup(lastExpandedPosition);  //expandableListView.collapseGroup(lastExpandedPosition);
                }

                lastExpandedPosition = i;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                String nombrePersTecn = expandableListAdapter.getChild(i, i1).toString();
                tvNombrePers.setText(nombrePersTecn);
                alertDialog.dismiss();
                return true;
            }
        });

        dialogBuilder = new AlertDialog.Builder(requireContext());
        dialogBuilder.setView(windowArbolPersTecn);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }


    int idFila = 0; //Para agregarle un id a la fila
    public void agregarPersTecnTabla(){
        /*********************************/
        String nombreDeTecn = tvNombrePers.getText().toString();
        String hrsDeTrabajo = etHrsTrabajo2.getText().toString();

        //int numFila = tblPersTecn.getChildCount() + 1;
        TableRow filaTabla = new TableRow(getContext());
        idFila = idFila + 1;
        filaTabla.setId(idFila);

        if (nombreDeTecn != ""){
            int indOfCorchete = nombreDeTecn.indexOf("(");
            String idPersTecn = nombreDeTecn.substring(indOfCorchete);
            idPersTecn = idPersTecn.replace("(", "").replace(")", "");
            nombreDeTecn = nombreDeTecn.substring(0, indOfCorchete-1);

            TextView tvColum0 = new TextView(getContext());
            tvColum0.setBackgroundResource(R.drawable.style_edittex_tbls);
            tvColum0.setGravity(1);
            tvColum0.setText(idPersTecn);
            tvColum0.setTextColor(Color.BLACK);

            TextView tvColum1 = new TextView(getContext());
            tvColum1.setBackgroundResource(R.drawable.style_edittex_tbls);
            tvColum1.setText(nombreDeTecn);
            tvColum1.setTextColor(Color.BLACK);

            TextView tvColum2 = new TextView(getContext());
            tvColum2.setBackgroundResource(R.drawable.style_edittex_tbls);
            tvColum2.setGravity(1);
            tvColum2.setText(hrsDeTrabajo);
            tvColum2.setTextColor(Color.BLACK);

            TextView tvColum3 = new TextView(getContext());
            tvColum3.setBackgroundResource(R.drawable.style_edittex_tbls);
            tvColum3.setGravity(1);
            tvColum3.setText(getResources().getString(R.string.quitarLinea));
            tvColum3.setTextColor(Color.BLACK);

            //AGREGAMOS UN LISTENER PARA CADA BOTON
            View.OnClickListener quitarLineaListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removerLineaDeTabla(filaTabla);
                }
            };
            tvColum3.setOnClickListener(quitarLineaListener);

            filaTabla.addView(tvColum0);
            filaTabla.addView(tvColum1);
            filaTabla.addView(tvColum2);
            filaTabla.addView(tvColum3);

            tblPersTecn.addView(filaTabla);

        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.avisoToast01), Toast.LENGTH_SHORT).show();  //avisoToast01
        }

        tvNombrePers.setText("");
        tvNombrePers.requestFocus();
    }


    public void removerLineaDeTabla(TableRow filaTabla){ //Del personal técnico
        /**************************************************/
        tblPersTecn.removeView(filaTabla);
    }


    public void getListaDeSupervisores(){
        /***********************************/
        progressBar.setVisibility(View.VISIBLE);

        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<List<Usuarios_DTO>> call = service.getLstaDeSupervisores();   // "usuarios/getSuperv";

        call.enqueue(new Callback<List<Usuarios_DTO>>() {

            final ArrayList<Usuarios_DTO> listaUsuarios = new ArrayList<>();
            final ArrayList<String> listaDeSupervis = new ArrayList<>();

            @Override
            public void onResponse(Call<List<Usuarios_DTO>> call, retrofit2.Response<List<Usuarios_DTO>> response) {
                /*
                if (response.code() == 401){  // El token ha expirado
                    ActivityCerarSesion activCerrarSess = new ActivityCerarSesion();
                    activCerrarSess.cleanSesionDeUsuario(getContext());
                    Toast.makeText(getContext(), "The session has ended", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(root).navigate(R.id.fragmentLogin);
                }  */

                if(response.isSuccessful() && response.body() != null){
                    listaUsuarios.addAll(response.body());
                    int listaUsuariosSize = listaUsuarios.size();

                    for (int i = 0; i < listaUsuariosSize; i++) {
                        Usuarios_DTO usuario = listaUsuarios.get(i);
                        String nombreUsuario = usuario.getNombreUsuario();
                        listaDeSupervis.add(nombreUsuario);
                    }

                    progressBar.setVisibility(View.GONE);
                    mostrarVentanaListaSupev(listaDeSupervis);

                } else{
                    Toast.makeText(getContext(), "Fallo al cargar lista supervisores", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Usuarios_DTO>> call, Throwable throwable) {
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());
                Toast.makeText(getContext(), "FALLO EN CARGAR DATOS", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void mostrarVentanaListaSupev(ArrayList<String> listaDeSupervis){
        /***********************************************************************/
        final View windowListaSuperv = getLayoutInflater().inflate(R.layout.window_lista_superv, null);
        ListView listViewSuperv = windowListaSuperv.findViewById(R.id.listViewSuperv);

        ArrayAdapter arrayAdapter = new ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listaDeSupervis);  //
        listViewSuperv.setAdapter(arrayAdapter);

        listViewSuperv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nombreSuperv = listaDeSupervis.get(position);
                tvNombrSupRut.setText(nombreSuperv);
                alertDialog2.dismiss();
            }
        });

        final AlertDialog.Builder dialogBuilder2 = new AlertDialog.Builder(requireContext());
        dialogBuilder2.setView(windowListaSuperv);

        alertDialog2 = dialogBuilder2.create();
        alertDialog2.show();
    }



    //******************************************* VARIABLES DEL DATE PICKER
    int dayInt, monthInt, yearInt;
    String datePickerFecha;

    private void setFechaDeHoy(){  //Se asume que la fecha de finalización es la fecha de hoy
        /***************************/
        Date fechaHoy = new Date();
        String strDate = dateFormat.format(fechaHoy);

        String[] dateSplited = strDate.split("/");
        dayInt = Integer.parseInt(dateSplited[0]);
        monthInt = Integer.parseInt(dateSplited[1]);
        yearInt = Integer.parseInt(dateSplited[2]);

        //Guardamos la fecha en formato consultas BD
        datePickerFecha = yearInt +"-"+ monthInt +"-"+ dayInt;

        DateFormat dateFormat2 = DateFormat.getDateInstance(DateFormat.DEFAULT);
        String strDateFormated = dateFormat2.format(fechaHoy);
        tvFechaFinaliz.setText(strDateFormated);

        monthInt = monthInt -1;
    }


    private void openDatePicker(){
        //****************************
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mesSelect = month + 1;
                dayInt = dayOfMonth;
                monthInt = month;
                yearInt = year;

                //Guardamos la fecha en formato consultas BD
                datePickerFecha = year +"-"+ mesSelect +"-"+ dayOfMonth;

                String strDateFormated = MetodosStaticos.getFechaStrFormated(datePickerFecha);
                tvFechaFinaliz.setText(strDateFormated);
            }
        }, yearInt, monthInt, dayInt);

        datePickerDialog.show();
    }
















}