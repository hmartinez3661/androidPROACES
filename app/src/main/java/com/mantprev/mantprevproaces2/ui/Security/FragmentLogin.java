package com.mantprev.mantprevproaces2.ui.Security;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mantprev.mantprevproaces2.ModelosDTO1.UserCredentials;
import com.mantprev.mantprevproaces2.ModelosDTO1.UserToken;
import com.mantprev.mantprevproaces2.ModelosDTO1.Usuarios;
import com.mantprev.mantprevproaces2.ModelosDTO2.Usuarios_DTO;
import com.mantprev.mantprevproaces2.R;
import com.mantprev.mantprevproaces2.retrofit.DataServices_Intf;
import com.mantprev.mantprevproaces2.retrofit.Retrofit_Instance;
import com.mantprev.mantprevproaces2.utilities.StaticConfig;

import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentLogin extends Fragment {

    public FragmentLogin() {
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

    //***********************************************************

    private TextView tvLogin;
    private LinearLayout linearLayout1;
    private EditText etEmailUser, etPasswordUser;
    private CheckBox chBoxOpenSession;
    private ProgressBar progresBar;
    private View root;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_login, container, false);
        //requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        progresBar = (ProgressBar) root.findViewById(R.id.progresBar);
        tvLogin = (TextView) root.findViewById(R.id.tvInformac2);

        linearLayout1 = (LinearLayout) root.findViewById(R.id.linearLayout1);
        etEmailUser = (EditText) root.findViewById(R.id.etUserEmail);
        etPasswordUser = (EditText) root.findViewById(R.id.etPassword);
        chBoxOpenSession = (CheckBox) root.findViewById(R.id.chBoxOpenSession);

        Button btnLogin = (Button) root.findViewById(R.id.btnLogin);
        TextView tvRecupPassw = (TextView) root.findViewById(R.id.tvRecupPassw);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginRequest();
            }
        });

        tvRecupPassw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //recuperarPassword();
            }
        });

        progresBar.setVisibility(View.GONE);
        chBoxOpenSession.setChecked(true);  //Para mantener la sesion abierta
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Verifica si sesion está abierta y evita el login
        verificarSesionAbierta();

        // BUSCA NUEVA VERISION DE LA APP
        //verificarNvaVersionDispon();
    }

    private void verificarSesionAbierta() {
    /*************************************/
        SharedPreferences preferencesFile = getContext().getSharedPreferences("datosUsuario", Context.MODE_PRIVATE);

        //Verifica sesion abierta; Lee los datos del usuario  en el archivo interno del Movil
        String nombreUsuario = preferencesFile.getString("nombreUsuario", "sesionCerrada");
        String emailUsuario  = preferencesFile.getString("emailUsuario",  "sesionCerrada");
        String passwUsuario  = preferencesFile.getString("passwUsuario",  "sesionCerrada");
        String rolDeUsuario  = preferencesFile.getString("rolDeUsuario",  "sesionCerrada");

        if (!nombreUsuario.equals("sesionCerrada")){  //La sesion está abierta por lo tanto hace un login automático

            StaticConfig.numbRealUser = nombreUsuario;
            StaticConfig.emailUsuario = emailUsuario;
            StaticConfig.rolDeUsuario = rolDeUsuario;
            loginAutomatico(emailUsuario, passwUsuario);  //Realiza un loginAutomatico

        }/* else { //Si no existe sesion abierta: Entra a la pantalla de Login
            chBoxOpenSession.setChecked(true);  //Para mantener la sesion abierta

            //Visible e Invisible de acuerdo al proceso
            linearLayout1.setVisibility(View.VISIBLE);  //Segmento del login
            progresBar.setVisibility(View.GONE);
        }  */
    }

    private void loginAutomatico(String emailUser, String passwordUser) {  //Si existe sesion abierta
    //*******************************************************************
        progresBar.setVisibility(View.VISIBLE);

        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<Void> call = service.userLogin(emailUser, passwordUser);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    progresBar.setVisibility(View.GONE);
                    ingresarAlSisitema(emailUser, passwordUser);

                } else {
                    Toast.makeText(getContext(), "Email o password incorrectos ...", Toast.LENGTH_LONG).show();
                    progresBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Toast.makeText(getContext(), "Error de intermet ... ", Toast.LENGTH_LONG).show();
                progresBar.setVisibility(View.GONE);

                //Cierra la session
                SharedPreferences preferencesFile = requireContext().getSharedPreferences("datosUsuario", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorFile = preferencesFile.edit();
                editorFile.putString("nombreUsuario", "sesionCerrada");
                verificarSesionAbierta();
            }
        });
    }

    private void loginRequest() {
    /****************************/
        String emailUsario = etEmailUser.getText().toString().trim();
        String passUsuario = etPasswordUser.getText().toString().trim();

        boolean isDatosOK = true;
        if(emailUsario.isEmpty() || passUsuario.isEmpty()){
            isDatosOK = false;
        }
        if (!validarEmail(emailUsario)){
            isDatosOK = false;
            Toast.makeText(getContext(), getResources().getString(R.string.msgUser03), Toast.LENGTH_LONG).show();
        }
        if (passUsuario.startsWith("mantprev")){
            //crearPasswordFinal(emailUsario);
        }

        if(isDatosOK && !passUsuario.startsWith("mantprev")){ //datos correcto y password no empieza con "mantprev"
            progresBar.setVisibility(View.VISIBLE);

            DataServices_Intf service2 = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
            Call<Void> call2 = service2.userLogin(emailUsario, passUsuario);

            call2.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 200) {
                        progresBar.setVisibility(View.GONE);
                        ingresarAlSisitema(emailUsario, passUsuario);

                    } else {
                        Toast.makeText(getContext(), "Email o password incorrectos ...", Toast.LENGTH_LONG).show();
                        progresBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable throwable) {
                    Toast.makeText(getContext(), "Error de intermet ... ", Toast.LENGTH_LONG).show();
                    progresBar.setVisibility(View.GONE);
                }
            });

        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.msgIngrDts), Toast.LENGTH_LONG).show();
        }
    }

    private void ingresarAlSisitema(String emailUsario, String passUsuario){
    //*********************************************************************
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<Usuarios_DTO> call = service.getUsuarioByEmail(emailUsario);

        call.enqueue(new Callback<Usuarios_DTO>() {
            @Override
            public void onResponse(Call<Usuarios_DTO> call, Response<Usuarios_DTO> response) {

                if(response.isSuccessful() && response.body() != null){
                    Usuarios_DTO usuario = response.body();

                    String idUsuario = Integer.toString(usuario.getIdUsuario());
                    String nombrUsuario = usuario.getNombreUsuario();
                    String emailUsuario = usuario.getEmailUsuario();
                    String rolDeUsuario = "ROL_" + usuario.getUserRol();

                    //Ingresa los datos a las variables estaticas
                    StaticConfig.emailUsuario = emailUsuario;
                    StaticConfig.numbRealUser = nombrUsuario;
                    StaticConfig.rolDeUsuario = rolDeUsuario;

                    //Usuario ya registrado (ya tiene su password personal definitivo
                    if (!passUsuario.startsWith("mantprev")){

                        if (chBoxOpenSession.isChecked()){  //si hay checke mantiene la sesion abierta

                            //Guarda los datos del usuario en archivo creado por SharePreferences
                            SharedPreferences preferencesFile = getContext().getSharedPreferences("datosUsuario", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editorFile = preferencesFile.edit();

                            editorFile.putString("nombreUsuario", nombrUsuario);
                            editorFile.putString("emailUsuario", emailUsuario);
                            editorFile.putString("passwUsuario", passUsuario);
                            editorFile.putString("rolDeUsuario", rolDeUsuario);
                            editorFile.apply();
                        }

                        //Abre la pagina de inicio
                        Navigation.findNavController(root).navigate(R.id.nav_home);
                    }

                } else {
                    chBoxOpenSession.setChecked(true);  //Para mantener la sesion abierta

                    //Visible e Invisible de acuerdo al proceso
                    linearLayout1.setVisibility(View.VISIBLE);
                    progresBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Fallo en cargar datos de Usuario", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuarios_DTO> call, Throwable throwable) {
                progresBar.setVisibility(View.GONE);
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());
                Toast.makeText(getContext(), "FAIL TO LOAD USER", Toast.LENGTH_LONG).show();

                /*
                Log.e("HTTP", "Code: " + response.code());
                assert response.body() != null;
                response.body().toString();
                */
            }
        });

        progresBar.setVisibility(View.GONE);
    }

    private boolean validarEmail(String email) {
        /*****************************************/
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

/*
    private void crearPasswordFinal(String emailUser){
    //*******************************************  //el usuario tiene password provisional: mantprevXXXXXX).
        progresBar.setVisibility(View.VISIBLE);    //por tanto se le solcita ingresar su password final.

        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<Usuarios_DTO> call = service.getUsuarioByEmail(emailUser);

        call.enqueue(new Callback<Usuarios_DTO>() {

            @Override
            public void onResponse(Call<Usuarios_DTO> call, Response<Usuarios_DTO> response) {

                if(response.isSuccessful() && response.body() != null){
                    Usuarios_DTO usuario = response.body();
                    idUsuarioStr = usuario.getIdUsuario() + "";

                    //Soliicta paswword final
                    linearLayout1.setVisibility(View.GONE);
                    linearLayout2.setVisibility(View.VISIBLE);
                    etNombreApellido.setText(usuario.getNombreUsuario());
                    etEmail.setText(usuario.getEmailUsuario());
                    tvLogin.setText(getResources().getString(R.string.tvLogin2));
                    progresBar.setVisibility(View.GONE);

                } else {
                    progresBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), getResources().getString(R.string.errorDtsUser), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuarios_DTO> call, Throwable throwable) {
                progresBar.setVisibility(View.GONE);
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());

                progresBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), getResources().getString(R.string.errorDtsUser), Toast.LENGTH_LONG).show();
            }
        });
    }
*/

    /*
    private String idUsuarioStr = "0";  //Se actualiza  cuando el usuario ingresa al sistema
    private void guardarPasswordFinaleUser() {  //Usuario que solo tenía paswword provisional (Mantprev)
    //************************************
        String nombreUser = etNombreApellido.getText().toString().trim();
        String emailUser = etEmail.getText().toString();
        String password1 = etPassword1.getText().toString().trim();
        String password2 = etPassword2.getText().toString().trim();

        int numPalabras = nombreUser.split(" ").length;  //numero palabras del nombre

        //VALIDACIONES
        boolean datosCorrectos = true;
        String mensajeDeErr = "";

        if (nombreUser.isEmpty() || emailUser.isEmpty() || password1.isEmpty() || password2.isEmpty()){
            datosCorrectos = false;
            mensajeDeErr = getResources().getString(R.string.msgUser01);
        }

        if(numPalabras > 2){
            datosCorrectos = false;
            mensajeDeErr = getResources().getString(R.string.msgUser08);
        }

        if (!password1.equals(password2)){
            datosCorrectos = false;
            mensajeDeErr = getResources().getString(R.string.msgUser02);
        }

        if(validacionDeEmail(emailUser)){
            datosCorrectos = false;
            mensajeDeErr = getResources().getString(R.string.msgUser03);
        }

        if (datosCorrectos){  //Si los datosCorrectos==true
            progresBar.setVisibility(View.VISIBLE);

            Usuarios_DTO userDTO = new Usuarios_DTO();
            userDTO.setIdUsuario(Integer.parseInt(idUsuarioStr));
            userDTO.setNombreUsuario(nombreUser);
            userDTO.setEmailUsuario(emailUser);
            userDTO.setPassword(password1);
            userDTO.setIdEmpresa(StaticConfig.idEmpresa);

            DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
            Call<String> call = service.actualizarDatosNvoUser(userDTO);  //actualizarNvoUsuario.php";

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if(response.isSuccessful() && response.body() != null){
                        Toast.makeText(getContext(), getResources().getString(R.string.msgActualUser), Toast.LENGTH_SHORT).show();

                        //Limpia los editText
                        etNombreApellido.setText("");
                        etEmail.setText("");
                        etPassword1.setText("");
                        etPassword2.setText("");

                        etEmailUser.setText("");
                        etPasswordUser.setText("");

                        //Ingresa la login por primera vez
                        //Visible e Invisible de acuerdo al proveso
                        tvLogin.setText(getResources().getString(R.string.tvLogin1));
                        linearLayout1.setVisibility(View.VISIBLE);
                        linearLayout2.setVisibility(View.GONE);

                        progresBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    throwable.printStackTrace();
                    Log.d("ErrorResponse: ", throwable.toString());
                    Toast.makeText(getContext(), "FALLO EN ACTUALIZAR EL NUEVO USUARIO", Toast.LENGTH_LONG).show();
                }
            });

        } else {
            Toast.makeText(getContext(), mensajeDeErr, Toast.LENGTH_LONG).show();
        }
    }
    */

/*
    private void recuperarPassword() {
    //*********************************
        //Abre fragment recuperacion passw
        Navigation.findNavController(root).navigate(R.id.fragmentRecupPassw);
    }
    */


    private boolean validacionDeEmail(String emailAdress){
        /****************************************************/
        boolean emailValido;

        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(emailAdress);

        if (mather.find()) {
            emailValido = true;
        } else {
            emailValido = false;
        }

        return !emailValido;
    }


/*
    private void verificarNvaVersionDispon() {
     //*********************************
        Context context = requireContext();  // getContext();
        int curVersion = 0;

        try {
            curVersion = (int) context.getPackageManager().getPackageInfo(context.getPackageName(), 0).getLongVersionCode(); //.versionName;

        } catch (Exception e) {
            e.printStackTrace();
        }
        final int versOnCell = curVersion;

        //* RETROFIT
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<Integer> call = service.getNewestAppVersion();

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {

                if (response.isSuccessful()) {
                    int versOnDB = response.body().intValue();

                    //Si versOnDB > versOnCell es porque se actualizo en DB para obligar actualizacion
                    if (versOnDB > versOnCell) {
                        String titleOptions = getResources().getString(R.string.msgExpicac);
                        String opcDescargarNow = getResources().getString(R.string.msgOpcion1);
                        String opcRecordarLater = getResources().getString(R.string.msgOpcion2);
                        String packageName = requireContext().getPackageName();

                        final CharSequence[] opciones = {opcDescargarNow, opcRecordarLater};
                        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());  //()

                        dialogBuilder.setTitle(titleOptions).setItems(opciones, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int opcSelected) {

                                if (opciones[opcSelected].equals(opcDescargarNow)) {

                                    try {
                                        // Intent para abrir Google Play Store directamente
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));

                                    } catch (ActivityNotFoundException anfe) {
                                        // Si el usuario no tiene Play Store, abre en navegador web
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
                                    }
                                    dialog.dismiss();
                                }

                                if (opciones[opcSelected].equals(opcRecordarLater)) {
                                    dialog.dismiss();
                                }
                            }
                        });

                        dialogBuilder.show();
                    }

                } else {
                    Toast.makeText(getContext(), "Failure to get the app version !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());
                Toast.makeText(getContext(), "Failure to get the newest aap version", Toast.LENGTH_LONG).show();
            }
        });
    }
*/





}