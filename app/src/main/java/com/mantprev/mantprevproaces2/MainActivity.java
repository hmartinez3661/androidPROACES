package com.mantprev.mantprevproaces2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.mantprev.mantprevproaces2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //Obliga a la aplicacion a estar clara
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        /* BOTON FLOTANTE
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });
        */

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setItemIconTintList(null);  //Para que muestre los iconos con sus colores

        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_newOT, R.id.fragmentMisOTs, R.id.nav_listadoOTs, R.id.fragmentAutorizOTList,
                R.id.fragmentReportEjecList, R.id.fragmentListaRuts)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {    // contFragment
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int idItemSelected = item.getItemId();

        if (idItemSelected == R.id.cerrarSesion){

            SharedPreferences preferencesFile = getSharedPreferences("datosUsuario", Context.MODE_PRIVATE);
            SharedPreferences.Editor editorFile = preferencesFile.edit();

            editorFile.remove("nombreUsuario").commit();
            editorFile.remove("emailUsuario").commit();
            editorFile.remove("passwUsuario").commit();
            editorFile.remove("rolDeUsuario").commit();

            editorFile.putString("nombreUsuario", "sesionCerrada");
            editorFile.putString("emailUsuario",  "sesionCerrada");
            editorFile.putString("passwUsuario",  "sesionCerrada");
            editorFile.putString("rolDeUsuario",  "sesionCerrada");

            editorFile.apply();

            Toast.makeText(this, "Closing APP, thanks for using Mantprev", Toast.LENGTH_LONG).show();
            finish();
            finishAffinity();
            System.exit(0);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}