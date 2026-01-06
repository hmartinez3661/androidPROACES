package com.mantprev.mantprevproaces2.ui.newOrdTrab;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

//import com.mantprev.mantprevproaces2.BuildConfig;
import com.mantprev.mantprevproaces2.ModelosDTO1.ConfigSpinners;
import com.mantprev.mantprevproaces2.ModelosDTO1.Equipos;
import com.mantprev.mantprevproaces2.ModelosDTO2.InformacionEmails;
import com.mantprev.mantprevproaces2.ModelosDTO2.OrdenesTrab_DTO1;
import com.mantprev.mantprevproaces2.ModelosDTO2.Usuarios_DTO;
import com.mantprev.mantprevproaces2.R;
import com.mantprev.mantprevproaces2.adapters.ExpandListEquipsAdapter;
import com.mantprev.mantprevproaces2.adapters.GridViewFotosAdapter;
import com.mantprev.mantprevproaces2.databinding.FragmentNewOtBinding;
import com.mantprev.mantprevproaces2.retrofit.DataServices_Intf;
import com.mantprev.mantprevproaces2.retrofit.Retrofit_Instance;
//import com.mantprev.mantprevproaces2.ui.otrasUI.ActivityCerarSesion;
import com.mantprev.mantprevproaces2.utilities.StaticConfig;
import com.ortiz.touchview.BuildConfig;

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
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class FragmentNewOrdTrab<listaImgsUri> extends Fragment {  // implements Response.ErrorListener, Response.Listener<JSONObject>

    private FragmentNewOtBinding binding;
    private String nombreFoto;
    private Button btnSeleccEquip, btnEnviarOt;
    private EditText etNombreEquip, etTrabSolic;
    private TextView tvIdEquipo, tvOpcionesFotos;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;
    private Spinner spinnerEjecut, spinnerPriorid;
    private ProgressBar progressBar;
    private GridView gvImagenes;
    private GridViewFotosAdapter gridViewAdapter;
    private String enviarEmails = "";
    private View root;


    public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNewOtBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        btnSeleccEquip = (Button) root.findViewById(R.id.btnSeleccEquip);
        btnEnviarOt = (Button) root.findViewById(R.id.btnEnviarOt);

        etNombreEquip = (EditText) root.findViewById(R.id.etNombreEquip);
        etTrabSolic = (EditText) root.findViewById(R.id.etTrabSolic);
        tvIdEquipo = (TextView) root.findViewById(R.id.tvIdEquipo);  //TextView hidden
        tvOpcionesFotos = (TextView) root.findViewById(R.id.tvIndicFotos);

        spinnerEjecut = (Spinner) root.findViewById(R.id.spnEjecutores);
        spinnerPriorid = (Spinner) root.findViewById(R.id.spnPriorids);
        progressBar = root.findViewById(R.id.progresBar);
        gvImagenes = root.findViewById(R.id.gvImagenes);
        progressBar.setVisibility(View.VISIBLE);

        //Seleccionar Equipo
        btnSeleccEquip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getArbolDeEquipos(view);
            }
        });

        //Enviar-Guardar OT
        btnEnviarOt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarGuardarNuevaOT();
            }
        });

        tvOpcionesFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarOpcionesFotos(view);
            }
        });

        activityResult();
        poblarSpinners();  //Los spinner se llenar con los datos
        etTrabSolic.requestFocus();

        return root;
    }

    private void poblarSpinners(){
    /****************************/
        progressBar.setVisibility(View.VISIBLE);
        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<List<ConfigSpinners>> call = service.getConfiguracSpinner();  //"configSpn/getConf";

        call.enqueue(new Callback<List<ConfigSpinners>>() {

            final ArrayList<ConfigSpinners> listaConfSpns = new ArrayList<>();
            final ArrayList<String> ejecutorestArray = new ArrayList<>();
            final ArrayList<String> clasificOTrabArray = new ArrayList<>();
            final ArrayList<String> prioridadesArray = new ArrayList<>();

            @Override
            public void onResponse(Call<List<ConfigSpinners>> call, Response<List<ConfigSpinners>> response) {

                /*
                if (response.code() == 401){  // El token ha expirado
                    ActivityCerarSesion activCerrarSess = new ActivityCerarSesion();
                    activCerrarSess.cleanSesionDeUsuario(getContext());
                    Toast.makeText(getContext(), "The session has ended", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(root).navigate(R.id.fragmentLogin);
                } */

                if(response.isSuccessful() && response.body() != null){
                    listaConfSpns.addAll(response.body());
                    if (listaConfSpns.size() > 1){
                        //Se actualiza el status de Nueva OT en el idioa del usuario
                        String statusNvaOT = listaConfSpns.get(1).getEstatusOTs();
                        StaticConfig.statusNvaOT = statusNvaOT;

                        int listaConfigSpnnSize = listaConfSpns.size();
                        for (int i = 0; i < listaConfigSpnnSize; i++) {

                            ConfigSpinners configSpnn = listaConfSpns.get(i);

                            String ejecutor  = configSpnn.getEjecutoresOTs();
                            String clasifTrb = configSpnn.getClasificTrabOTs();
                            String prioridad = configSpnn.getPrioridTrabOTs();
                            String confEmail = configSpnn.getConfigCorreos();

                            if (ejecutor != null){
                                if (!ejecutor.isEmpty()){
                                    ejecutorestArray.add(ejecutor);
                                }
                            }
                            if (clasifTrb != null){
                                if (!clasifTrb.isEmpty()){
                                    clasificOTrabArray.add(clasifTrb);
                                }
                            }
                            if (prioridad != null){
                                if (!prioridad.isEmpty()){
                                    prioridadesArray.add(prioridad);
                                }
                            }
                            if(i==0){
                                enviarEmails = confEmail;  //Obtiene la config. Emails (enviar Si o No)
                            }
                        }

                        ArrayList<ArrayList> arraysDeSpiners = new ArrayList<>();
                        arraysDeSpiners.add(ejecutorestArray);
                        arraysDeSpiners.add(clasificOTrabArray);
                        arraysDeSpiners.add(prioridadesArray);

                        setSpinnersToAdapters(arraysDeSpiners);
                    }
                    progressBar.setVisibility(View.GONE);

                } else {
                    Toast.makeText(getContext(), "Fallo en cargar cofiguracion Spinner", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<ConfigSpinners>> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());
                Toast.makeText(getContext(), "FALLO EN CARGAR DATOS", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void setSpinnersToAdapters(ArrayList<ArrayList> arraysDeSpiners){
    /**********************************************************************/
        ArrayList<String> listaEjecutArray = arraysDeSpiners.get(0);
        ArrayList<String> listaClasifTrabArray = arraysDeSpiners.get(1);
        ArrayList<String> listaPrioridadesArray = arraysDeSpiners.get(2);

        //Adapter Spinner Ejecutores
        ArrayAdapter<CharSequence> adapterEject = new ArrayAdapter(requireContext(), R.layout.zspinners_items, listaEjecutArray); //
        adapterEject.setDropDownViewResource(R.layout.zspinners_dropdown_items);
        spinnerEjecut.setAdapter(adapterEject);

        //Adapter Spinner Prioridades
        ArrayAdapter<CharSequence> adapterPrioridades = new ArrayAdapter(requireContext(), R.layout.zspinners_items, listaPrioridadesArray); //R.layout.zspinner_text
        adapterPrioridades.setDropDownViewResource(R.layout.zspinners_dropdown_items);
        spinnerPriorid.setAdapter(adapterPrioridades);
    }

    private void getArbolDeEquipos(View view) {
    /*****************************************/
        progressBar.setVisibility(View.VISIBLE);

        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<List<Equipos>> call = service.getTodosLosEquipos();  //"equipos/getAll";

        call.enqueue(new Callback<List<Equipos>>() {
            ArrayList<Equipos> listaEquipos = new ArrayList<>();
            ArrayList<String> parentsList = new ArrayList<>();
            Map<String, List<String>> equipsMap;
            ArrayList<String> childList;

            @Override
            public void onResponse(Call<List<Equipos>> call, Response<List<Equipos>> response) {

                if(response.isSuccessful() && response.body() != null){
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

                        if (correlat.length() == 2){  //4
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
                            String nombreEquipo = equipoHijo.getNombEquipo() + " (" + idEquipoHijo +")";

                            if(correlatHijo.startsWith(correlatPadre)){
                                childList.add(nombreEquipo);
                            }
                        }
                        equipsMap.put(nombrEquipPrd, childList);
                    }

                    progressBar.setVisibility(View.GONE);
                    mostrarVentanaEquipos(parentsList, equipsMap);

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "La LISTA EQUIPOS es nula", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Equipos>> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());
                Toast.makeText(getContext(), "FALLO EN CARGAR LISTA EQUIPOS", Toast.LENGTH_LONG).show();
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
                String idEquipSt = equipSelected.substring(posCorchete + 1).replace(")", "");
                equipSelected = equipSelected.substring(0, posCorchete);

                etNombreEquip.setText(equipSelected);
                tvIdEquipo.setText(idEquipSt);
                etNombreEquip.setEnabled(false);  //Para que no se pueda editar
                alertDialog.dismiss();

                return true;
            }
        });

        dialogBuilder = new AlertDialog.Builder(requireContext());
        dialogBuilder.setView(windowEquipos);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private static final int PERMISO_FOTOS_CODE = 99;
    private final String manifestPermiso = Manifest.permission.READ_MEDIA_IMAGES;

    private void mostrarOpcionesFotos(View view){
    /*****************************************/
        //TRAE LOS TITULOS DE LA VENTANA DESDE String.xml
        String titleOpc = getResources().getString(R.string.titleOpc);
        String opcTakePict = getResources().getString(R.string.opcTakePict);
        String opcChoseGall = getResources().getString(R.string.opcChoseGall);
        String opcCancel = getResources().getString(R.string.opcCancel);

        final CharSequence[] opciones = {opcTakePict, opcChoseGall, opcCancel};
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());  //()

        dialogBuilder.setTitle(titleOpc)
                     .setItems(opciones, new DialogInterface.OnClickListener() {

             public void onClick(DialogInterface dialog, int opcSelected) {

                 if(opciones[opcSelected].equals(opcTakePict)){
                     camaraPermiso.launch(Manifest.permission.CAMERA);
                 }
                 if(opciones[opcSelected].equals(opcChoseGall)){

                     ActivityCompat.requestPermissions(requireActivity(), new String[]{manifestPermiso}, PERMISO_FOTOS_CODE);
                     openGallery();
                 }
                 if(opciones[opcSelected].equals(opcCancel)){
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
    private Uri imageUri = null;
    private ArrayList<Uri> listaImgsUri = new ArrayList<>();
    private String currentPhotoPath;

    private void activarCamara(){
    /****************************/
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(requireContext().getPackageManager()) != null){
            File photoTmpFile = null;

            try {
                photoTmpFile = createImageTmpFile();  // Create the File where the photo should go

            } catch (IOException ex){ex.getMessage();}

            if (photoTmpFile != null){
                String authority = "com.mantprev.mantprevproaces2" + ".provider";
                Uri photoUri = FileProvider.getUriForFile(Objects.requireNonNull(requireContext()), authority, photoTmpFile);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                camaraLauncher.launch(intent);
            }
        }
    }

    //ACTIVA LA CAMARA PARA TOMAR FOTO
    private ActivityResultLauncher<Intent> camaraLauncher =
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

                            if (bitmap != null){
                                imageUri = getImageUri(getContext(), bitmap);
                                listaImgsUri.add(imageUri);

                                gridViewAdapter = new GridViewFotosAdapter(getContext(), listaImgsUri);
                                gvImagenes.setAdapter(gridViewAdapter);
                            }
                        }
                    });


    public Uri getImageUri(Context inContext, Bitmap inImage) {
    /*********************************************************/
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, getNombreDeFoto(), null);
        return Uri.parse(path);
    }


    private File createImageTmpFile() throws IOException {
    /************************************************/
        String imgFileName = getNombreDeFoto();
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imgTmpFile = File.createTempFile(imgFileName, ".jpg", storageDir);
        currentPhotoPath = imgTmpFile.getAbsolutePath();

        return imgTmpFile;
    }

    private ActivityResultLauncher<Intent> intentLauncher;
    private void openGallery(){
    //**************************
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);

        intentLauncher.launch(Intent.createChooser(intent, getResources().getString(R.string.indicacion)));
    }


    private void activityResult(){
    //****************************
        intentLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            if (result.getData() != null){
                ClipData clipData = result.getData().getClipData();

                if (clipData != null){  // ..getItemCount() > 0

                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        listaImgsUri.add(clipData.getItemAt(i).getUri());
                    }

                    gridViewAdapter = new GridViewFotosAdapter(getContext(), listaImgsUri);
                    gvImagenes.setAdapter(gridViewAdapter);
                }
            }
        });
    }


    private String nombreEquipo;    //Se utiliza para mandar los emials a los superv.
    private String trabajoSolicit;  //Se utiliza para mandar los emails a los superv.

    public void enviarGuardarNuevaOT() {
    //*********************************/
        progressBar.setVisibility(View.VISIBLE);

        nombreEquipo = etNombreEquip.getText().toString();
        String idEquipo = tvIdEquipo.getText().toString();
        trabajoSolicit = etTrabSolic.getText().toString();
        String persEjecutor = spinnerEjecut.getSelectedItem().toString();
        String prioridadOT = spinnerPriorid.getSelectedItem().toString();
        String nombreSolict = StaticConfig.numbRealUser;
        String statusDeOT = "No Autorizada";
        String horaAct = getHoraActual();
        String cantFotosAnex = Integer.toString(listaImgsUri.size());

        //Verifica que se hayan ingresado todos los datos
        Boolean datosCorrectos = true;
        if (nombreEquipo.equals("")) { datosCorrectos = false;}
        if (trabajoSolicit.equals("")) { datosCorrectos = false;}
        if (persEjecutor.equals("---")) { datosCorrectos = false;}
        if (prioridadOT.equals("---")) { datosCorrectos = false;}

        // SI DATOS ESTAN CORRECTOS PROCEDE A GUARDAR LA OT
        if (datosCorrectos){

            OrdenesTrab_DTO1 newOrdTrab = new OrdenesTrab_DTO1();
            newOrdTrab.setIdEquipo(idEquipo);
            newOrdTrab.setTrabajoSolicit(trabajoSolicit);
            newOrdTrab.setPersEjecutor(persEjecutor);
            newOrdTrab.setPrioridadOT(prioridadOT);
            newOrdTrab.setNombreSolict(nombreSolict);
            newOrdTrab.setStatusDeOT(statusDeOT);
            newOrdTrab.setFechaIngresoOT(null);  //Se carga la fecha en la API
            newOrdTrab.setHoraAct(horaAct);
            newOrdTrab.setCantFotosAnex(cantFotosAnex);

            /* RETROFIT */
            DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
            Call<String> call = service.guardarNuevaOrdTrab(newOrdTrab);

            call.enqueue(new Callback<String>() {

                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    int idOrdTrab = 0; int canFotos = 0;

                    if(response.isSuccessful()){
                        Toast.makeText(getContext(), getResources().getString(R.string.exitoOT), Toast.LENGTH_SHORT).show();
                        idOrdTrab = Integer.parseInt(response.body());
                        canFotos  = listaImgsUri.size();
                        etNombreEquip.setText(""); etTrabSolic.setText("");  //Limpia los EditText
                        gvImagenes.setAdapter(null);                         //Limpia el gridView de todas las imagenes
                    }

                    if(canFotos > 0){
                        uploadImagensToServer(idOrdTrab);   //GUARDAR LA FOTOS DE LA OT en el Server
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    throwable.printStackTrace();
                    Log.d("ErrorResponse: ", throwable.toString());
                    Toast.makeText(getContext(), getResources().getString(R.string.failSentOT), Toast.LENGTH_LONG).show();
                }
            });

            if (enviarEmails.equals("Yes")){    //Manda correo de nueva OT a supev. si está config. la opción
                enviarEmailsSupervMantto();     //Obtiene lista emails supervisores y luego manda los emails
            }

            progressBar.setVisibility(View.GONE);

        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.enterDataMsg), Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }


    public void uploadImagensToServer(int idOrdTrab) {  //Fotos ingreso de nueva OT
    /************************************************/
        int listaImgsUriSize = listaImgsUri.size();
        for(int i=0; i< listaImgsUriSize; i++) {

            try {
                Uri uriFoto = listaImgsUri.get(i);
                InputStream inputStream = requireContext().getContentResolver().openInputStream(uriFoto);
                Bitmap bitmapImg = BitmapFactory.decodeStream(inputStream);
                Bitmap bitmapImgReduced = getResisedBitmap(bitmapImg, 800); //foto max 800 pixeles

                //Se genera la Foto en String base64
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmapImgReduced.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();

                RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
                MultipartBody.Part fotoFilePart = MultipartBody.Part.createFormData("file", "img.jpg", requestBody);

                //* RETROFIT
                DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
                Call<String> call = service.uploadImgIngresoOT(fotoFilePart, idOrdTrab);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
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
                //bitmapImg.recycle();

            } catch (Exception ex){ }
        }
        listaImgsUri.clear(); //Limpia la lista de Uris
    }

    private Bitmap getResisedBitmap(Bitmap bitmap, int maxSize) { //Cambia el tamano de la foto
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

        nombreFoto = imageFileName;
        nombreFoto = builder + "_" + nombreFoto;

        return nombreFoto;
    }


    public String getHoraActual(){
    //****************************
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String horaActual = dateFormat.format(date);

        return horaActual;
    }


    private void enviarEmailsSupervMantto(){
    /*************************************/
        progressBar.setVisibility(View.VISIBLE);

        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<List<Usuarios_DTO>> call = service.getLstaDeSupervisores();   //"usuarios/getSuperv";

        call.enqueue(new Callback<List<Usuarios_DTO>>() {
            ArrayList<Usuarios_DTO> listaSuperv = new ArrayList<>();
            String listaEmails = "";

            @Override
            public void onResponse(Call<List<Usuarios_DTO>> call, Response<List<Usuarios_DTO>> response) {

                listaSuperv.addAll(response.body());
                int listaSupervSize = listaSuperv.size();

                for (int i = 0; i < listaSupervSize; i++) {

                    Usuarios_DTO usuario = listaSuperv.get(i);
                    String emailUsuario  = usuario.getEmailUsuario();
                    String rolDelUsuario = usuario.getUserRol();

                    if (rolDelUsuario.substring(0, 3).equals("SDM") || rolDelUsuario.substring(0, 3).equals("ADM")){
                        if (listaEmails.isEmpty()){
                            listaEmails = listaEmails + emailUsuario;
                        } else {
                            listaEmails = listaEmails + ","+ emailUsuario;
                        }
                    }
                }

                emviarEmailsSuperv(listaEmails);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Usuarios_DTO>> call, Throwable throwable) {
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());
                Toast.makeText(getContext(), "FALLO EN CARGAR LISTA", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void emviarEmailsSuperv(String emailsDeSuperv) {
    /******************************************************/
        String nombrEquip = nombreEquipo;
        String trabSolicit = trabajoSolicit;
        String persEjecutor = spinnerEjecut.getSelectedItem().toString();
        String prioridadOT = spinnerPriorid.getSelectedItem().toString();
        String nombreSolict = StaticConfig.numbRealUser;
        String nombreEmpres = StaticConfig.nombrEmpresa;

        String asunto = getResources().getString(R.string.emailAsunto);

        String saludo = getResources().getString(R.string.saludo);
        String content01 = getResources().getString(R.string.content01);
        String content02 = getResources().getString(R.string.content02) + ":";
        String content03 = getResources().getString(R.string.content03);
        String content04 = getResources().getString(R.string.content04);
        String content05 = getResources().getString(R.string.content05);
        String content07 = getResources().getString(R.string.content07);
        //String content08 = getResources().getString(R.string.content08) + ".";
        String content09 = getResources().getString(R.string.content09);
        String content10 = getResources().getString(R.string.content10);

        String mensaje = saludo + "<br>" +
                content01 + " " + nombreSolict +" (" + nombreEmpres +") "+ content02 + "<br><br>" +

                "<b>" + content03 + "</b> " + nombrEquip + "<br>" +
                "<b>" + content04 + "</b> " +  trabSolicit + " <br><br>" +

                "<b>" + content05 + "</b> " + persEjecutor + "<br>" +
                "<b>" + content07 + "</b> " + prioridadOT + "<br><br>" +

                content09 + "<br>" +
                "<b>" + content10 + "</b>" ;

        //Creating SendEmailsService
        //SendEmailsService.sendEmailOTs(emailsDeSuperv, asunto, mensaje);
        InformacionEmails informEmail = new InformacionEmails();
        informEmail.setEmailDestin(emailsDeSuperv);  //Lista de emals separados por ","
        informEmail.setAsunto(asunto);
        informEmail.setSaludo(saludo);

        informEmail.setTxtMsg01(content01);
        informEmail.setTxtMsg02(content02);
        informEmail.setTxtMsg03(content03);
        informEmail.setTxtMsg04(content04);
        informEmail.setTxtMsg05(content05);
        informEmail.setTxtMsg07(content07);
        informEmail.setTxtMsg09(content09);
        informEmail.setTxtMsg10(content10);
        informEmail.setTxtMsg11(nombreSolict);
        informEmail.setTxtMsg12(nombreEmpres);
        informEmail.setTxtMsg13(nombrEquip);
        informEmail.setTxtMsg14(trabSolicit);
        informEmail.setTxtMsg15(persEjecutor);
        informEmail.setTxtMsg16(prioridadOT);

        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<String> call = service.sendEmailNuevaOrdenTrab(informEmail);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && response.body() != null){
                    Toast.makeText(getContext(), "An email was sent to maintenance staff", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());
                Toast.makeText(getContext(), "Fail to sent enail", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        //PONE EN BLANCO LOS CAMPOS PRINCIPALES  *********************************
        etNombreEquip.setText("");
        etTrabSolic.setText("");
        spinnerEjecut.setSelection(0);
        spinnerPriorid.setSelection(0);
        gvImagenes.setAdapter(null);
    }






}