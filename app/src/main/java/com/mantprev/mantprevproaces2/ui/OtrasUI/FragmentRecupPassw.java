package com.mantprev.mantprevproaces2.ui.OtrasUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mantprev.mantprevproaces2.ModelosDTO2.InformacionEmails;
import com.mantprev.mantprevproaces2.R;
import com.mantprev.mantprevproaces2.retrofit.DataServices_Intf;
import com.mantprev.mantprevproaces2.retrofit.Retrofit_Instance;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentRecupPassw extends Fragment {

    public FragmentRecupPassw() {
        // Required empty public constructor
    }

    public static FragmentRecupPassw newInstance(String param1, String param2) {
        FragmentRecupPassw fragment = new FragmentRecupPassw();
        return fragment;
    }

    private EditText etUserEmail;
    private ProgressBar progresBar;
    private View root;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_recup_passw, container, false);

        progresBar = (ProgressBar) root.findViewById(R.id.progresBar);
        etUserEmail = (EditText) root.findViewById(R.id.btnEquipos);
        Button btnNewPassw = (Button) root.findViewById(R.id.btnLogin);

        btnNewPassw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarNvoPassword();
            }
        });

        progresBar.setVisibility(View.GONE);
        return root;
    }

    private void generarNvoPassword() {
        /*********************************/
        progresBar.setVisibility(View.VISIBLE);
        String emailUser = etUserEmail.getText().toString();

        if (!emailUser.isEmpty() && validacionDeEmail(emailUser)){

            /* RETROFIT */
            DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
            Call<String> call = service.getUserPasswProvis(emailUser);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {

                    if(response.isSuccessful() && response.body() != null){

                        Toast.makeText(getContext(), getResources().getString(R.string.msgRecupPassw), Toast.LENGTH_LONG).show();
                        String passwProv = response.body();
                        enviarEmail(emailUser, passwProv);
                        cerrarSesionUsuario();
                        progresBar.setVisibility(View.GONE);

                        //Abre fragment recuperacion passw
                        Navigation.findNavController(root).navigate(R.id.fragmentLogin);

                    } else {
                        progresBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), getResources().getString(R.string.errorEmail), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    progresBar.setVisibility(View.GONE);
                    throwable.printStackTrace();
                    Log.d("ErrorResponse: ", throwable.toString());
                    Toast.makeText(getContext(), getResources().getString(R.string.msgUser03), Toast.LENGTH_LONG).show();
                }
            });

        } else {
            progresBar.setVisibility(View.GONE);
            Toast.makeText(getContext(), getResources().getString(R.string.msgUser03), Toast.LENGTH_LONG).show();
        }
    }

    private void enviarEmail (String emailUser, String passwProv){
        /************************************************************/
        String asunto = "Password Mantprev";

        String saludo = getResources().getString(R.string.saludo);
        String content01 = getResources().getString(R.string.content11); // +" <b>"+ passwProv +"</b>";
        String content09 = getResources().getString(R.string.content09);
        String content10 = getResources().getString(R.string.content10);

        String mensaje = saludo + "<br>" +
                content01 + "<br><br>" +
                content09 + "<br><b>" +
                content10 + "</b> ";

        //SendEmailsService.sendEmail(emailUser, asunto, mensaje);
        InformacionEmails informEmail = new InformacionEmails();
        informEmail.setEmailDestin(emailUser);
        informEmail.setAsunto(asunto);
        informEmail.setSaludo(saludo);
        informEmail.setTxtMsg01(content01);
        informEmail.setTxtMsg09(content09);
        informEmail.setTxtMsg10(content10);
        informEmail.setTxtMsg15(passwProv);

        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<String> call = service.sendEmailRecuperacPassword(informEmail);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && response.body() != null){
                    Toast.makeText(getContext(), "Un correo electrónico le fue enviado.", Toast.LENGTH_SHORT).show();
                    progresBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());
                Toast.makeText(getContext(), "Fail to sent enail", Toast.LENGTH_LONG).show();
                progresBar.setVisibility(View.GONE);
            }
        });
    }


    private void cerrarSesionUsuario(){
        /*********************************/
        //Cierra cualquier sesion abierta y manda al Login
        SharedPreferences preferencesFile = getContext().getSharedPreferences("datosUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorFile = preferencesFile.edit();

        editorFile.remove("nombreUsuario").commit();
        editorFile.remove("emailUsuario").commit();
        editorFile.remove("rolDeUsuario").commit();

        editorFile.putString("nombreUsuario", "sesionCerrada");
        editorFile.putString("emailUsuario",  "sesionCerrada");
        editorFile.putString("rolDeUsuario",  "sesionCerrada");
        editorFile.apply();
    }


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

        return emailValido;
    }



}