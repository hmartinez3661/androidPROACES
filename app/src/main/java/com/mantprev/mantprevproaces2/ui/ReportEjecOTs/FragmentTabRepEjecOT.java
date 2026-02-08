package com.mantprev.mantprevproaces2.ui.ReportEjecOTs;

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

//import com.mantprev.mantprevproaces2.BuildConfig;
import com.mantprev.mantprevproaces2.ModelosDTO1.Fallas;
import com.mantprev.mantprevproaces2.ModelosDTO1.PersonalTecn;
import com.mantprev.mantprevproaces2.ModelosDTO1.RtesEjecOTs;
import com.mantprev.mantprevproaces2.ModelosDTO1.RtesPersEjecOTs;
import com.mantprev.mantprevproaces2.ModelosDTO1.RtesReptosEjecOTs;
import com.mantprev.mantprevproaces2.ModelosDTO1.RtesServExtEjecOTs;
import com.mantprev.mantprevproaces2.ModelosDTO2.ReptesReptos_DTO;
import com.mantprev.mantprevproaces2.ModelosDTO2.Usuarios_DTO;
import com.mantprev.mantprevproaces2.R;
import com.mantprev.mantprevproaces2.adapters.ExpandListFallasAdapter;
import com.mantprev.mantprevproaces2.retrofit.DataServices_Intf;
import com.mantprev.mantprevproaces2.retrofit.Retrofit_Instance;
//import com.mantprev.mantprevproaces2.ui.otrasUI.ActivityCerarSesion;
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


public class FragmentTabRepEjecOT extends Fragment {

    private java.lang.String idOT, numOT;
    private TextView tvFechaFinaliz, tvNombrFalla, tvNombrSupOT, tvNombrePers;
    private EditText etHrsParoProduc, etHrsTrabajo, etCalidadTrab, etReportEjec, etNombRecivTrab, etHrsTrabajo2;
    private EditText etCostoRepsto, etNombreRepsto, etNombreServExt, etCostoServExt;
    private TableLayout tblPersTecn;
    private TableLayout tblRepuestos, tblServExt;
    private ProgressBar progressBar;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private AlertDialog.Builder dialogBuilder;

    private AlertDialog alertDialog;
    private AlertDialog alertDialog2;

    private TextView tvAgregarFotosCierre;
    private CardView cvRepuestos, cvServExt;
    private Button btnGuardaRepEjec;
    private View root;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        idOT  = MetodosStaticos.idOT;
        numOT = MetodosStaticos.numOT;
        root = inflater.inflate(R.layout.fragment_tab_rep_ejec_ot, container, false);

        TextView tvIdOT = (TextView) root.findViewById(R.id.tvIdRepteEjec);
        tvIdOT.setText(idOT);  //Text view hidden

        TextView tvNumOT = (TextView) root.findViewById(R.id.tvNumRut);
        tvFechaFinaliz = (TextView) root.findViewById(R.id.etFechaEjecuc);
        etHrsParoProduc = (EditText) root.findViewById(R.id.etHrsProdStop);
        etHrsTrabajo = (EditText) root.findViewById(R.id.etHrsLabor);
        etCalidadTrab = (EditText) root.findViewById(R.id.etCalidadTrab);
        etReportEjec = (EditText) root.findViewById(R.id.etReportEjec);
        tvNombrFalla = (TextView) root.findViewById(R.id.tvNombrFalla);
        tvNombrSupOT = (TextView) root.findViewById(R.id.tvNombSupRut);
        etNombRecivTrab = (EditText) root.findViewById(R.id.etNombRecivTrab);
        tvNombrePers = (TextView) root.findViewById(R.id.tvNombrePers);
        etHrsTrabajo2 = (EditText) root.findViewById(R.id.etHrsTrabajo2);
        ImageButton ibAgregPerTecTbl = (ImageButton) root.findViewById(R.id.ibAgregarTecn);
        tblPersTecn = (TableLayout) root.findViewById(R.id.tblPersTecn);

        etNombreRepsto = (EditText) root.findViewById(R.id.etNombreRepsto);
        ImageButton ibAgregReptoTbl = (ImageButton) root.findViewById(R.id.ibAgregReptoTbl);
        etCostoRepsto = (EditText) root.findViewById(R.id.etCostoRepsto);
        tblRepuestos = (TableLayout) root.findViewById(R.id.tblRepuestos);

        etNombreServExt = (EditText) root.findViewById(R.id.etNombreServExt);
        etCostoServExt = (EditText) root.findViewById(R.id.etCostoServExt);
        ImageButton ibAgregServExtTbl = (ImageButton) root.findViewById(R.id.ibAgregServExtTbl);
        tblServExt = (TableLayout) root.findViewById(R.id.tblServExt);
        TextView tvIndicRepuest = (TextView) root.findViewById(R.id.tvIndicRepuest);
        TextView tvIndicServExt = (TextView) root.findViewById(R.id.tvIndicServExt);
        cvRepuestos = (CardView) root.findViewById(R.id.cvRepuestos);
        cvServExt = (CardView) root.findViewById(R.id.cvServExt);
        tvAgregarFotosCierre = (TextView) root.findViewById(R.id.tvAgregarFotosRepte);
        btnGuardaRepEjec = (Button) root.findViewById(R.id.btnGuardaRepEjec);
        progressBar = (ProgressBar) root.findViewById(R.id.progresBar);

        tvNumOT.setText(numOT);

        tvNombrFalla.setOnClickListener(new View.OnClickListener() {   //SELECCIONAR FALLA
            @Override
            public void onClick(View view) {
                getArbolDeFallas(view);
            }
        });

        tvFechaFinaliz.setOnClickListener(new View.OnClickListener() {  //SELECCIONAR FECHA
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });

        tvNombrSupOT.setOnClickListener(new View.OnClickListener() {   //SELECCIONAR NOMBRE SUPERVISOR
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

        tvAgregarFotosCierre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarOpcionesFotos();
            }
        });

        btnGuardaRepEjec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarReporteEjecucionOT();
            }
        });

        progressBar.setVisibility(View.GONE);
        cvRepuestos.setVisibility(View.GONE);
        cvServExt.setVisibility(View.GONE);
        java.lang.String tvTexto = getResources().getString(R.string.tvAgregarFotosCierre) + " (0)";
        tvAgregarFotosCierre.setText(tvTexto);
        //etHrsParoProduc.requestFocus();

        setFechaDeHoy();
        activityResult();
        return root;

        //etHrsTrabajo.addTextChangedListener(TextWatcher);
        //etHrsTrabajo.setKeyListener(DigitsKeyListener.getInstance("123456789,."));
    }


    private static final int PERMISO_FOTOS_CODE = 99;
    private final java.lang.String manifestPermiso = Manifest.permission.READ_MEDIA_IMAGES;


    private void mostrarOpcionesFotos() {
    /**********************************/
        //TRAE LOS TITULOS DE LA VENTANA DESDE String.xml
        java.lang.String titleOpc = getResources().getString(R.string.titleOpc);
        java.lang.String opcTakePict = getResources().getString(R.string.opcTakePict);
        java.lang.String opcChoseGall = getResources().getString(R.string.opcChoseGall);
        java.lang.String opcCancel = getResources().getString(R.string.opcCancel);

        final CharSequence[] opciones = {opcTakePict, opcChoseGall, opcCancel};
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setTitle(titleOpc);

        dialogBuilder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(opciones[which].equals(opcTakePict)){
                    camaraPermiso.launch(Manifest.permission.CAMERA);
                }
                if(opciones[which].equals(opcChoseGall)){

                    if(Build.VERSION.SDK_INT >= 33){
                        ActivityCompat.requestPermissions(requireActivity(), new java.lang.String[]{manifestPermiso}, PERMISO_FOTOS_CODE);
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
    ActivityResultLauncher<java.lang.String> camaraPermiso = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
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
    java.lang.String currentPhotoPath;


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
                java.lang.String authority = "com.mantprev.mantprevproaces2" + ".provider";    //BuildConfig.APPLICATION_ID
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
                        java.lang.String tvTexto = getResources().getString(R.string.tvAgregarFotosCierre);
                        tvAgregarFotosCierre.setText(tvTexto + " (" + cantFotos + ")");
                    }
                });



    public Uri getImageUri(Context inContext, Bitmap inImage) {
    /*********************************************************/
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        java.lang.String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, getNombreDeFoto(), null);
        return Uri.parse(path);
    }


    private File createImageFile() throws IOException {
    /************************************************/
        java.lang.String imgFileName = getNombreDeFoto();
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
                    java.lang.String tvTexto = getResources().getString(R.string.tvAgregarFotosCierre);
                    tvAgregarFotosCierre.setText(tvTexto + " (" + cantFotos + ")");
                }
            }
        });
    }

    java.lang.String calidTrab, fechaEjec;

    public void guardarReporteEjecucionOT() {
    /**************************************/
        btnGuardaRepEjec.setEnabled(true);

        //CAPTURA LOS DATOS DE EJECUCION
        fechaEjec = datePickerFecha;   //Se utiliza el String en fromato de fecha sql
        calidTrab = etCalidadTrab.getText().toString();
        java.lang.String idOrdTrab = idOT;
        java.lang.String hrsParoProd = etHrsParoProduc.getText().toString();
        java.lang.String hrsTrabajo  = etHrsTrabajo.getText().toString();

        java.lang.String reportHist = etReportEjec.getText().toString();
        java.lang.String nombrFalla = tvNombrFalla.getText().toString();
        java.lang.String supervOT  = tvNombrSupOT.getText().toString();
        java.lang.String recibTrab = etNombRecivTrab.getText().toString();
        java.lang.String cantFotos = Integer.toString(listaImgsUri.size());
        java.lang.String cantRptos = Integer.toString(tblRepuestos.getChildCount() - 1); //1 solo tiene el table head
        java.lang.String cantSrvEx = Integer.toString(tblServExt.getChildCount() - 1);   //1 solo tiene el table head

        if (recibTrab.isEmpty()) {
            recibTrab = "--";
        }

        //Verifica que se hayan ingresado todos los datos
        boolean datosCorrectos = true;

        java.lang.String nombPers = tvNombrePers.getText().toString();
        java.lang.String nombRpto = etNombreRepsto.getText().toString();
        java.lang.String nombServ = etNombreServExt.getText().toString();

        if(!nombPers.isEmpty() || !nombRpto.isEmpty() || !nombServ.isEmpty()){
            Toast.makeText(getContext(), getResources().getString(R.string.msjInformTabl), Toast.LENGTH_LONG).show();
            datosCorrectos = false;
        }

        if (reportHist.equals("")) {
            datosCorrectos = false;
        }
        if (nombrFalla.equals("")) {
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

            RtesEjecOTs repteEjecOT = new RtesEjecOTs();
            //Date fechaEjecucion = MetodosStaticos.getDateFromString(fechaEjec);
            java.lang.String fechaTrabajo = fechaEjec;

            repteEjecOT.setIdOT(Integer.parseInt(idOrdTrab));
            //repteEjecOT.setFechaInicio(fechaEjecucion);
            repteEjecOT.setFechaTrabajo(fechaTrabajo);
            repteEjecOT.setCalidadTrab(Double.parseDouble(calidTrab));
            repteEjecOT.setTpoParoProduc(Double.parseDouble(hrsParoProd));
            repteEjecOT.setTpoRealReparac(Double.parseDouble(hrsTrabajo));
            repteEjecOT.setNombreSuperv(supervOT);
            repteEjecOT.setNombreFalla(nombrFalla);
            repteEjecOT.setnPersRecivTrab(recibTrab);
            repteEjecOT.setReporteHistor(reportHist);
            repteEjecOT.setCantFotosCierre(Integer.parseInt(cantFotos));
            repteEjecOT.setCantRptosUtiliz(Integer.parseInt(cantRptos));
            repteEjecOT.setCantServExter(Integer.parseInt(cantSrvEx));

            /* RETROFIT */
            DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
            Call<java.lang.String> call = service.guardarReporteEjecOT2(repteEjecOT);   //"reptes/saveRepteEjec/"

            call.enqueue(new Callback<java.lang.String>() {
                @Override
                public void onResponse(Call<java.lang.String> call, retrofit2.Response<java.lang.String> response) {
                    /*
                    if (response.code() == 401){  // El token ha expirado
                        ActivityCerarSesion activCerrarSess = new ActivityCerarSesion();
                        activCerrarSess.cleanSesionDeUsuario(getContext());
                        Toast.makeText(getContext(), "The session has ended", Toast.LENGTH_LONG).show();
                        Navigation.findNavController(root).navigate(R.id.fragmentLogin);
                    }  */

                    if(response.isSuccessful() && response.body() != null){
                        if(response.body().equals("EXITO")){
                            Toast.makeText(getContext(), getResources().getString(R.string.btnGuardaRepEjecOK), Toast.LENGTH_SHORT).show();
                            guardarReportPersTecn();  //GUARDA LOS REPORTES DEL PERSONAL TECNICO

                        } else {
                            Toast.makeText(getContext(), getResources().getString(R.string.btnGuardaRepEjecNot), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    } else {
                        Toast.makeText(getContext(), "No se pudo guardar el reporte de Ejecución OT ...", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<java.lang.String> call, Throwable throwable) {
                    throwable.printStackTrace();
                    Log.d("ErrorResponse: ", throwable.toString());
                    Toast.makeText(getContext(), getResources().getString(R.string.btnGuardaRepEjecNot), Toast.LENGTH_LONG).show();
                }
            });

        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.enterDataMsg), Toast.LENGTH_LONG).show();
        }
    }


    public void guardarReportPersTecn() {
    /***********************************/
        ArrayList<RtesPersEjecOTs> arrayReptesPersn = new ArrayList<>();

        //SE CAPTURA EL LISTADO DE EJECUTORES DE LA OT PARA GUARDARLOS
        RtesPersEjecOTs reptePersn;
        int listaTblPersTecnSze = tblPersTecn.getChildCount();

        for (int i=1; i < listaTblPersTecnSze; i++){

            TableRow filaTbl = (TableRow) tblPersTecn.getChildAt(i);

            TextView textView0 = (TextView) filaTbl.getChildAt(0);
            TextView textView1 = (TextView) filaTbl.getChildAt(1);
            TextView textView2 = (TextView) filaTbl.getChildAt(2);

            java.lang.String idPersTecn = textView0.getText().toString();
            java.lang.String nombPerTec = textView1.getText().toString();  //No se guarda en la Base de Datos
            java.lang.String hrsPersTec = textView2.getText().toString();

            reptePersn = new RtesPersEjecOTs();
            reptePersn.setCantHrs(Double.parseDouble(hrsPersTec));
            reptePersn.setCalidadTrab(Double.parseDouble(calidTrab));
            reptePersn.setFechaEjec(fechaEjec);
            reptePersn.setIdOT(Integer.parseInt(idOT));
            reptePersn.setIdEmpleado(Integer.parseInt(idPersTecn));

            arrayReptesPersn.add(reptePersn);
        }

        int arrayReptesPersSize = arrayReptesPersn.size();
        for (int i = 0; i < arrayReptesPersSize; i++) {

            java.lang.String cantHrs = Double.toString(arrayReptesPersn.get(i).getCantHrs());
            java.lang.String cddTrab = Double.toString(arrayReptesPersn.get(i).getCalidadTrab());  //Calidad de Trabajo
            java.lang.String fchaEje = arrayReptesPersn.get(i).getFechaEjec();
            java.lang.String idOrdTr = Integer.toString(arrayReptesPersn.get(i).getIdOT());
            java.lang.String idEmple = Integer.toString(arrayReptesPersn.get(i).getIdEmpleado());

            RtesPersEjecOTs reptePers = new RtesPersEjecOTs();
            reptePers.setCantHrs(Double.parseDouble(cantHrs));
            reptePers.setCalidadTrab(Double.parseDouble(cddTrab));
            reptePers.setFechaEjec(fchaEje);
            reptePers.setIdOT(Integer.parseInt(idOrdTr));
            reptePers.setIdEmpleado(Integer.parseInt(idEmple));

            /* RETROFIT */
            DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
            Call<java.lang.String> call = service.guardarReptePersEjecOT(reptePers);       //"reptes/saveReptePers/"

            call.enqueue(new Callback<java.lang.String>() {

                @Override
                public void onResponse(Call<java.lang.String> call, retrofit2.Response<java.lang.String> response) {
                    /*
                    if (response.code() == 401){  // El token ha expirado
                        ActivityCerarSesion activCerrarSess = new ActivityCerarSesion();
                        activCerrarSess.cleanSesionDeUsuario(getContext());
                        Toast.makeText(getContext(), "The session has ended", Toast.LENGTH_LONG).show();
                        Navigation.findNavController(root).navigate(R.id.fragmentLogin);
                    }   */

                    if(response.isSuccessful() && response.body() != null){
                        if(!response.body().equals("OK")){
                            Toast.makeText(getContext(), "No se pudo guardar Personal Tecnico .....", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<java.lang.String> call, Throwable throwable) {
                    throwable.printStackTrace();
                    Log.d("ErrorResponse: ", throwable.toString());
                    Toast.makeText(getContext(), "No pudo guardar Personal Técnico", Toast.LENGTH_LONG).show();
                }
            });
        }

        guardarReportesReptos();  //Procede a guardar reporte de repuestos
    }


    public void guardarReportesReptos(){
    /***********************************/
        ArrayList<RtesReptosEjecOTs> arrayReptesReptos = new ArrayList<>();

        //SE CAPTURA EL LISTADO DE REPUESTOS SI SE INGRESO PARA GUARDARLOS
        if (tblRepuestos.getChildCount() > 1){

            RtesReptosEjecOTs repteRepuesto;
            int tablaRepuestosSize = tblRepuestos.getChildCount();

            for (int i=1; i< tablaRepuestosSize; i++){

                TableRow filaTbl = (TableRow) tblRepuestos.getChildAt(i);

                TextView textView0 = (TextView) filaTbl.getChildAt(0);
                TextView textView1 = (TextView) filaTbl.getChildAt(1);

                java.lang.String nombrRepto = textView0.getText().toString();
                java.lang.String costoTotal = textView1.getText().toString();

                repteRepuesto = new RtesReptosEjecOTs();
                repteRepuesto.setNombreRep(nombrRepto);
                repteRepuesto.setCostoTotal(Double.parseDouble(costoTotal));
                repteRepuesto.setFechaConsumo(fechaEjec);
                repteRepuesto.setIdOT(Integer.parseInt(idOT));

                arrayReptesReptos.add(repteRepuesto);
            }
        }

        int arrayReptesReptosSize = arrayReptesReptos.size();
        for(int i=0; i< arrayReptesReptosSize; i++) {
            java.lang.String nombRepto = arrayReptesReptos.get(i).getNombreRep();
            java.lang.String costTotal = Double.toString(arrayReptesReptos.get(i).getCostoTotal());
            java.lang.String fechConsu = arrayReptesReptos.get(i).getFechaConsumo();
            java.lang.String idOrdTrab = Integer.toString(arrayReptesReptos.get(i).getIdOT());

            ReptesReptos_DTO repteRepto = new ReptesReptos_DTO();
            repteRepto.setIdOrdTrab(idOrdTrab);
            repteRepto.setNombreRep(nombRepto);
            repteRepto.setCostTotal(costTotal);
            repteRepto.setDateConsu(fechConsu);

            /* RETROFIT */
            DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
            Call<java.lang.String> call = service.guardarRepteReptosEjecOT(repteRepto);   //"reptes/saveRepteRepto/"

            call.enqueue(new Callback<java.lang.String>() {

                @Override
                public void onResponse(Call<java.lang.String> call, retrofit2.Response<java.lang.String> response) {
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
                    }
                }

                @Override
                public void onFailure(Call<java.lang.String> call, Throwable throwable) {
                    throwable.printStackTrace();
                    Log.d("ErrorResponse: ", throwable.toString());
                    Toast.makeText(getContext(), "No puedo guardar Repuestos", Toast.LENGTH_LONG).show();
                }
            });
        }

        guardarReptesSevExter();   //Procede a guardar reporte de repuestos
    }


    public void guardarReptesSevExter(){
    /**********************************/
        ArrayList<RtesServExtEjecOTs> arrayReptesServExt = new ArrayList<>();

        //CAPTURA EL LISTADO DE SERV. EXTERNOS SI SE INGRESO PARA GUARDARLOS
        if (tblServExt.getChildCount() > 1){

            RtesServExtEjecOTs repteServExt;
            int tablaServExtSize = tblServExt.getChildCount();

            for (int i=1; i< tablaServExtSize; i++){

                TableRow filaTbl = (TableRow) tblServExt.getChildAt(i);

                TextView textView0 = (TextView) filaTbl.getChildAt(0);
                TextView textView1 = (TextView) filaTbl.getChildAt(1);

                java.lang.String nombreServ = textView0.getText().toString();
                java.lang.String costoTotal = textView1.getText().toString();

                repteServExt = new RtesServExtEjecOTs();
                repteServExt.setNombreServic(nombreServ);
                repteServExt.setCostoServic(Double.parseDouble(costoTotal));
                repteServExt.setFechaServic(fechaEjec);
                repteServExt.setIdOT(Integer.parseInt(idOT));

                arrayReptesServExt.add(repteServExt);
            }
        }

        int arrayReptesServExtSize = arrayReptesServExt.size();
        for(int i=0; i< arrayReptesServExtSize; i++) {
            java.lang.String nombrServ = arrayReptesServExt.get(i).getNombreServic();
            java.lang.String costoServ  = Double.toString(arrayReptesServExt.get(i).getCostoServic());
            java.lang.String fechaServ  = arrayReptesServExt.get(i).getFechaServic();
            java.lang.String idOrdTrab = Integer.toString(arrayReptesServExt.get(i).getIdOT());

            RtesServExtEjecOTs repteServExt = new RtesServExtEjecOTs();
            repteServExt.setIdOT(Integer.parseInt(idOrdTrab));
            repteServExt.setNombreServic(nombrServ);
            repteServExt.setCostoServic(Double.parseDouble(costoServ));
            repteServExt.setFechaServic(fechaServ);

            /* RETROFIT */
            DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
            Call<java.lang.String> call = service.guardarRepteServExtEjecOT(repteServExt);   // "reptes/saveRepteSevExt/"

            call.enqueue(new Callback<java.lang.String>() {
                @Override
                public void onResponse(Call<java.lang.String> call, retrofit2.Response<java.lang.String> response) {
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
                    }
                }

                @Override
                public void onFailure(Call<java.lang.String> call, Throwable throwable) {
                    throwable.printStackTrace();
                    Log.d("ErrorResponse: ", throwable.toString());
                    Toast.makeText(getContext(), "No se pudo guardar Serv. Ext.", Toast.LENGTH_LONG).show();
                }
            });
        }

        //Vuelve a la lista de OTs para Cerar
        progressBar.setVisibility(View.GONE);
        if (!listaImgsUri.isEmpty()){
            int idOrdTrab = Integer.parseInt(idOT);
            uploadImagensToServer(idOrdTrab);
        }

        Navigation.findNavController(root).navigate(R.id.fragmentReportEjecList);
    }

    public void uploadImagensToServer(int idOTrab){  //Fotos de Cierre de OT
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
                Call<java.lang.String> call = service.uploadImgCierreOT(fotoFilePart, idOTrab);

                call.enqueue(new Callback<java.lang.String>() {
                    @Override
                    public void onResponse(Call<java.lang.String> call, retrofit2.Response<java.lang.String> response) {
                        if(response.isSuccessful()){
                            //Toast.makeText(getContext(), response.body(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Failure to send photos !!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<java.lang.String> call, Throwable throwable) {
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
    public java.lang.String getNombreDeFoto() {
    /*******************************/
        int count = 10;

        java.lang.String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * CARACTERES.length());
            builder.append(CARACTERES.charAt(character));
        }

        java.lang.String nombreImagen = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        java.lang.String imageFileName = nombreImagen + ".jpg";

        java.lang.String nombreFoto = imageFileName;
        nombreFoto = builder + "_" + nombreFoto;

        return nombreFoto;
    }


    //**************************************


    public void agregarServExtTabla(){
    /*********************************/
        java.lang.String nombreServExt = etNombreServExt.getText().toString();
        java.lang.String costoServExt  = etCostoServExt.getText().toString();

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
        java.lang.String nombreDeRepto = etNombreRepsto.getText().toString();
        java.lang.String costoDeRepto  = etCostoRepsto.getText().toString();

        if (costoDeRepto.length() > 0 && !costoDeRepto.contains(".")){
            costoDeRepto = costoDeRepto + ".00";
        }

        TableRow filaTabla = new TableRow(getContext());

        if (nombreDeRepto.length() > 0 && costoDeRepto.length() > 0){ //

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
            Map<java.lang.String, List<java.lang.String>> mapaPersTecn;
            ArrayList<java.lang.String> listaTiposDeEjecut;    //Lista de Padres de Ejecutores (Electricos, mecánicos, ete)
            ArrayList<java.lang.String> grupoDeEjecutores;     //Hijos de ejecutores  (Ejecutores agrupados)

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
                    mapaPersTecn = new HashMap<java.lang.String, List<java.lang.String>>();
                    listaTiposDeEjecut = new ArrayList<>();  //Lista de Padres (electrico, mecánico, ete)

                    int listaPersTecnSize = listPersTecn.size();
                    for (int i=0; i< listaPersTecnSize; i++){

                        java.lang.String tipoEjecutor  = listPersTecn.get(i).getTipoEjecutor();

                        if (!listaTiposDeEjecut.contains(tipoEjecutor)){
                            listaTiposDeEjecut.add(tipoEjecutor);
                        }
                    }

                    //SE CREAN LOS GRUPOS DE PERSONAL EJECUTOR SEGUN EL TIPO (MECACNICAS, ELECTRICAS, ETC.)
                    int listaTiposEjectSize = listaTiposDeEjecut.size();
                    for (int i=0; i< listaTiposEjectSize; i++){

                        java.lang.String tipoDeEject = listaTiposDeEjecut.get(i);
                        grupoDeEjecutores = new ArrayList<>();

                        for (int j=0; j< listaPersTecnSize; j++){

                            int idEmpleado = listPersTecn.get(j).getIdEmpleado();
                            java.lang.String tipoEjecut = listPersTecn.get(j).getTipoEjecutor();
                            java.lang.String nombrePers = listPersTecn.get(j).getNombre() + " (" +idEmpleado+ ")";

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


    public void mostrarVentanaPersTecn(ArrayList<java.lang.String> parentsList, Map<java.lang.String, List<java.lang.String>> persTecnMap){
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

                java.lang.String nombrePersTecn = expandableListAdapter.getChild(i, i1).toString();
                tvNombrePers.setText(nombrePersTecn);
                alertDialog.dismiss();
                return true;
            }
        });

        dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setView(windowArbolPersTecn);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }


    int idFila = 0; //Para agregarle un id a la fila
    public void agregarPersTecnTabla(){
    /*********************************/
        java.lang.String nombreDeTecn = tvNombrePers.getText().toString();
        java.lang.String hrsDeTrabajo = etHrsTrabajo2.getText().toString();

        //int numFila = tblPersTecn.getChildCount() + 1;
        TableRow filaTabla = new TableRow(getContext());
        idFila = idFila + 1;
        filaTabla.setId(idFila);

        if (nombreDeTecn != ""){
            int indOfCorchete = nombreDeTecn.indexOf("(");
            java.lang.String idPersTecn = nombreDeTecn.substring(indOfCorchete);
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
            final ArrayList<java.lang.String> listaDeSupervis = new ArrayList<>();

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
                        java.lang.String nombreUsuario = usuario.getNombreUsuario();
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

    public void mostrarVentanaListaSupev(ArrayList<java.lang.String> listaDeSupervis){
    /***********************************************************************/
        final View windowListaSuperv = getLayoutInflater().inflate(R.layout.window_lista_superv, null);
        ListView listViewSuperv = windowListaSuperv.findViewById(R.id.listViewSuperv);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listaDeSupervis);  //
        listViewSuperv.setAdapter(arrayAdapter);

        listViewSuperv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                java.lang.String nombreSuperv = listaDeSupervis.get(position);
                tvNombrSupOT.setText(nombreSuperv);
                alertDialog2.dismiss();
            }
        });

        final AlertDialog.Builder dialogBuilder2 = new AlertDialog.Builder(getContext());
        dialogBuilder2.setView(windowListaSuperv);

        alertDialog2 = dialogBuilder2.create();
        alertDialog2.show();
    }


    private void getArbolDeFallas(View view) {
    /*****************************************/
        progressBar.setVisibility(View.VISIBLE);

        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<List<Fallas>> call = service.getListaDeFallas();   // "fallas/getAll";

        call.enqueue(new Callback<List<Fallas>>() {
            final ArrayList<Fallas> listaDeFallas = new ArrayList<>();
            Map<java.lang.String, List<java.lang.String>> mapaDeFallas;

            ArrayList<java.lang.String> listaTiposDeFallas;
            ArrayList<java.lang.String> grupoDeFallasArray;  //Grupo de fallas segun el tipo (mecanicas, electricas, etc)

            @Override
            public void onResponse(Call<List<Fallas>> call, retrofit2.Response<List<Fallas>> response) {

                /*
                if (response.code() == 401){  // El token ha expirado
                    ActivityCerarSesion activCerrarSess = new ActivityCerarSesion();
                    activCerrarSess.cleanSesionDeUsuario(getContext());
                    Toast.makeText(getContext(), "The session has ended", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(root).navigate(R.id.fragmentLogin);
                }  */

                if(response.isSuccessful() && response.body() != null){
                    listaDeFallas.addAll(response.body());

                    //SE CREA LA LISTA DE TIPOS DE FALLAS
                    mapaDeFallas = new HashMap<java.lang.String, List<java.lang.String>>();
                    listaTiposDeFallas = new ArrayList<>();
                    int listaFallasSize = listaDeFallas.size();

                    for (int i=0; i< listaFallasSize; i++){

                        java.lang.String nombreFalla = listaDeFallas.get(i).getNombreFalla();
                        java.lang.String tipoDeFalla = listaDeFallas.get(i).getTipoFalla();

                        if (!listaTiposDeFallas.contains(tipoDeFalla)){
                            listaTiposDeFallas.add(tipoDeFalla);
                        }
                    }

                    //SE CREAN LA LISTA DE GRUPOS DE FALLAS SEGUN EL TIPO (MECACNICAS, ELECTRICAS, ETC.)
                    int listaTiposFallasSize = listaTiposDeFallas.size();
                    for (int i=0; i< listaTiposFallasSize; i++){

                        java.lang.String tipoDeFalla = listaTiposDeFallas.get(i);
                        grupoDeFallasArray = new ArrayList<>();

                        for (int j=0; j<listaDeFallas.size(); j++){

                            java.lang.String tipoFalla  = listaDeFallas.get(j).getTipoFalla();
                            java.lang.String nombrFalla = listaDeFallas.get(j).getNombreFalla();

                            if (tipoFalla.equals(tipoDeFalla)){
                                grupoDeFallasArray.add(nombrFalla);
                            }
                        }
                        mapaDeFallas.put(tipoDeFalla, grupoDeFallasArray);
                    }

                    progressBar.setVisibility(View.GONE);
                    mostrarVentanaArbolFallas(listaTiposDeFallas, mapaDeFallas);

                } else {
                    Toast.makeText(getContext(), "Fallo al cargar el arbol de fallas", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Fallas>> call, Throwable throwable) {
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());

            }
        });
    }


    public void mostrarVentanaArbolFallas(ArrayList<java.lang.String> parentsList, Map<java.lang.String, List<java.lang.String>> fallasMap){
    /*********************************************************************************************************/
        final View windowArbolFallas = getLayoutInflater().inflate(R.layout.window_arbol_fallas, null);

        expandableListView = windowArbolFallas.findViewById(R.id.expListArbFallas);
        expandableListAdapter = new ExpandListFallasAdapter(getContext(), parentsList, fallasMap); //
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
                java.lang.String fallaSelected = expandableListAdapter.getChild(i, i1).toString();

                tvNombrFalla.setText(fallaSelected);
                alertDialog.dismiss();
                return true;
            }
        });

        dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setView(windowArbolFallas);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }


    //******************************************* VARIABLES DEL DATE PICKER
    int dayInt, monthInt, yearInt;
    java.lang.String datePickerFecha;

    private void setFechaDeHoy(){  //Se asume que la fecha de finalización es la fecha de hoy
    /***************************/
        Date fechaHoy = new Date();
        java.lang.String strDate = dateFormat.format(fechaHoy);

        java.lang.String[] dateSplited = strDate.split("/");
        dayInt = Integer.parseInt(dateSplited[0]);
        monthInt = Integer.parseInt(dateSplited[1]);
        yearInt = Integer.parseInt(dateSplited[2]);

        //Guardamos la fecha en formato consultas BD
        datePickerFecha = yearInt +"-"+ monthInt +"-"+ dayInt;

        DateFormat dateFormat2 = DateFormat.getDateInstance(DateFormat.DEFAULT);
        java.lang.String strDateFormated = dateFormat2.format(fechaHoy);
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

                java.lang.String strDateFormated = MetodosStaticos.getFechaStrFormated(datePickerFecha);
                tvFechaFinaliz.setText(strDateFormated);
            }
        }, yearInt, monthInt, dayInt);

        datePickerDialog.show();
    }





}