package com.mantprev.mantprevproaces2.ui.RevAutorizOTs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mantprev.mantprevproaces2.ModelosDTO2.Fotos_DTO;
import com.mantprev.mantprevproaces2.R;
import com.mantprev.mantprevproaces2.adapters.FotosOtAdapter;
import com.mantprev.mantprevproaces2.interfases.VerFotosListener;
import com.mantprev.mantprevproaces2.retrofit.DataServices_Intf;
import com.mantprev.mantprevproaces2.retrofit.Retrofit_Instance;
import com.mantprev.mantprevproaces2.utilities.MetodosStaticos;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class FragmentImages extends Fragment implements VerFotosListener{

    String idOT = MetodosStaticos.idOT;
    String numOT = MetodosStaticos.numOT;
    TextView tvEncabesado;
    RecyclerView recyclerViewFotosOT;
    //ProgressBar progressBar;
    View root;

    public FragmentImages() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_images, container, false);

        tvEncabesado = (TextView) root.findViewById(R.id.tvEncabesado);
        recyclerViewFotosOT = (RecyclerView) root.findViewById(R.id.rvFotosOT);
        recyclerViewFotosOT.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));


        getListaNombresFotos();

        return root;
    }

    private final ArrayList<String> listaNamesFotosOT = new ArrayList<>();
    private void getListaNombresFotos(){
    /***********************************/

        VerFotosListener verFotosListener = this;

        /* RETROFIT */
        DataServices_Intf service = Retrofit_Instance.getRetrofitInstance().create(DataServices_Intf.class);
        Call<List<Fotos_DTO>> call = null;

        if (MetodosStaticos.fotosDeCierroOT.equals("Yes")){
            //Muestra las fotos del ciere de OT
            String tituloVent = "Pictures of WO#: " + numOT;
            tvEncabesado.setText(tituloVent);
            String idOT = MetodosStaticos.idOT;
            int idOrdTrab = Integer.parseInt(idOT);
            call = service.getListaFotosCierreOT(idOrdTrab);    //"fotosOTs/fotosCierre/" + idOT;

        } else if (MetodosStaticos.fotosDeCierroOT.equals("No")){
            //Muestra las Fotos que se anexaron en al generar la OT
            String tituloVent = "Pictures of WO#: " + numOT;
            tvEncabesado.setText(tituloVent);
            String idOT = MetodosStaticos.idOT;
            int idOrdTrab = Integer.parseInt(idOT);
            call = service.getListaFotosIngresoOT(idOrdTrab);   //"fotosOTs/fotosIngreso/" + idOT;

        } else if (MetodosStaticos.fotosDeCierroOT.equals("Rut_repteEjec")) {
            //Muestra las Fotos que se anexaron en el repte ejecución de Rut
            String tituloVent = "Fotos ejecucuón Rutina";
            tvEncabesado.setText(tituloVent);
            int idRepteEjecRut = MetodosStaticos.idRepteEjecRut;
            call = service.getListaFotosRepteEjecRut(idRepteEjecRut);   //"fotosOTs/fotosIngreso/" + idOT;
        }

        call.enqueue(new Callback<List<Fotos_DTO>>() {
            final ArrayList<Fotos_DTO> listaDeFotos = new ArrayList<>();

            @Override
            public void onResponse(Call<List<Fotos_DTO>> call, retrofit2.Response<List<Fotos_DTO>> response) {

                listaDeFotos.addAll(response.body());
                int listaFotosSize = listaDeFotos.size();

                for (int i=0; i< listaFotosSize; i++){
                    Fotos_DTO dtsFoto = listaDeFotos.get(i);
                    String nombreFoto = dtsFoto.getNombreFoto();

                    if(!listaNamesFotosOT.contains(nombreFoto)){  //Para evitar la duplicidad de fotos
                        listaNamesFotosOT.add(nombreFoto);
                    }
                }

                //SE OBTIENE UNA INSTANCIA DEL ADAPTADOR
                FotosOtAdapter fotosOtAdapter = new FotosOtAdapter(listaNamesFotosOT, getContext(), verFotosListener);
                recyclerViewFotosOT.setAdapter(fotosOtAdapter);
            }

            @Override
            public void onFailure(Call<List<Fotos_DTO>> call, Throwable throwable) {
                throwable.printStackTrace();
                Log.d("ErrorResponse: ", throwable.toString());
                Toast.makeText(getContext(), "FALLO EN CARGAR LISTA FOTOS", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onFotoClick(int fotoPos) {
    /************************************/
        String nombreFoto = listaNamesFotosOT.get(fotoPos);
        MetodosStaticos.nombreFotoMostrar = nombreFoto;
        Navigation.findNavController(root).navigate(R.id.fragmentVerImages);
    }






}