package com.mantprev.mantprevproaces2.retrofit;

import com.mantprev.mantprevproaces2.ModelosDTO1.ConfigSpinners;
import com.mantprev.mantprevproaces2.ModelosDTO1.Equipos;
import com.mantprev.mantprevproaces2.ModelosDTO1.Fallas;
import com.mantprev.mantprevproaces2.ModelosDTO1.OrdenesTrabajo;
import com.mantprev.mantprevproaces2.ModelosDTO1.PersonalTecn;
import com.mantprev.mantprevproaces2.ModelosDTO1.RtesEjecOTs;
import com.mantprev.mantprevproaces2.ModelosDTO1.RtesEjecRuts;
import com.mantprev.mantprevproaces2.ModelosDTO1.RtesPersEjecOTs;
import com.mantprev.mantprevproaces2.ModelosDTO1.RtesPersEjecRuts;
import com.mantprev.mantprevproaces2.ModelosDTO1.RtesServExtEjecOTs;
import com.mantprev.mantprevproaces2.ModelosDTO1.RtesServExtEjecRuts;
import com.mantprev.mantprevproaces2.ModelosDTO1.RustEquiposDTO;
import com.mantprev.mantprevproaces2.ModelosDTO2.Fotos_DTO;
import com.mantprev.mantprevproaces2.ModelosDTO2.InformacionEmails;
import com.mantprev.mantprevproaces2.ModelosDTO2.OrdenTrabRevis;
import com.mantprev.mantprevproaces2.ModelosDTO2.OrdenesTrabDTO_2;
import com.mantprev.mantprevproaces2.ModelosDTO2.OrdenesTrab_DTO1;
import com.mantprev.mantprevproaces2.ModelosDTO2.OrdenesTrab_DTO2;
import com.mantprev.mantprevproaces2.ModelosDTO2.Repte2Datos;
import com.mantprev.mantprevproaces2.ModelosDTO2.ReptesReptos_DTO;
import com.mantprev.mantprevproaces2.ModelosDTO2.ResponseString;
import com.mantprev.mantprevproaces2.ModelosDTO2.Usuarios_DTO;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface DataServices_Intf {
/********************************/
    @FormUrlEncoded
    @POST("login")  //No existe el Controlador con la ruta "login" esta ruta entá en la memoria de la IP por Spring Security
    Call<Void> userLogin(@Field("username") String username, @Field("password") String password);

    @GET("auth/getUserByEmail/{emailUser}")
    Call<Usuarios_DTO> getUsuarioByEmail(@Path("emailUser") String emailUser);

    @POST("auth/update")  //PUT
    Call<ResponseString> actualizarDatosNvoUser(@Body Usuarios_DTO Usuarios_DTO);

    @GET("auth/userPasswProv/{emailUser}")
    Call<ResponseString> getUserPasswProvis(@Path("emailUser") String emailUser);

    @POST("auth/sendEmailRecupPassw")
    Call<ResponseString> sendEmailRecuperacPassword (@Body InformacionEmails informEmail);

    //******* Fin de end points sin necesidad de token *********/

    @GET("api/usuarios/getSuperv")
    Call<List<Usuarios_DTO>> getLstaDeSupervisores();


    //REPORTES EJECUCUON OTs ***************************
    @POST("api/reptes/saveRepteEjec2")
    Call<ResponseString> guardarReporteEjecOT2(@Body RtesEjecOTs repteEjecOT);

    @POST("api/reptes/saveReptePers")
    Call<ResponseString> guardarReptePersEjecOT(@Body RtesPersEjecOTs reptePers);

    @POST("api/reptes/saveRepteRepto")
    Call<ResponseString> guardarRepteReptosEjecOT(@Body ReptesReptos_DTO repteRepto);

    @POST("api/reptes/saveRepteSevExt")
    Call<ResponseString> guardarRepteServExtEjecOT(@Body RtesServExtEjecOTs repteServExt);

    @GET("api/reptes/getRepteEjec/{idReporte}")
    Call<RtesEjecOTs> getRepteEjecucOT(@Path("idReporte") int idReporte);

    @GET("api/reptes/reptsPers/{idOT}")
    Call<List<Repte2Datos>> getListReptesPersEjecOTByIdOT(@Path("idOT") int idOT);

    @GET("api/reptes/listaReptos/{idOT}")
    Call<List<Repte2Datos>> getListReptesRepuestosEjecOT(@Path("idOT") int idOT);

    @GET("api/reptes/listaSevExt/{idOT}")
    Call<List<Repte2Datos>> getListaReptesServExterEjecOT(@Path("idOT") int idOT);


    //PRESONAL TECNICO   ******************************
    @GET("api/persTecn/getAll")
    Call<List<PersonalTecn>> getLstaDePersonalTecn();

    @GET("api/persTecn/namesPersTecn")
    Call<List<String>> getNamesPersTecnAct();


    //FALLAS   *****************************************
    @GET("api/fallas/getAll")
    Call<List<Fallas>> getListaDeFallas();


    // EQUIPOS  ***************************************
    @GET("api/equipos/getAll")
    Call<List<Equipos>> getTodosLosEquipos();

/*************************/
    //CONFIGURACIONES SPINNERS  ******************************************
    @GET("auth/getConf")
    Call<List<ConfigSpinners>> getConfiguracSpinner();  //está aqui para crear el primer usuario Admin
/*************************/

    @GET("api/configSpn/getConf")
    Call<List<ConfigSpinners>> getLstaDeEjecutores();


    //ORDENES DE TRABAJO   ********************************************
    @PUT("api/v1/ordsTrab/update/{idOrdTrab}/{newStatusOT}")
    Call<String> actualizarStatusDeOT(@Path("idOrdTrab") int idOrdTrab, @Path("newStatusOT") String newStatusOT);

    @GET("api/v1/ordsTrab/OTsLast30days/{fechaInic}/{fechaFinal}/{idEmpresa}")
    Call<List<OrdenesTrabajo>> getOTsLast30days(@Path("fechaInic") String fechaInic, @Path("fechaFinal") String fechaFinal,
                                                @Path("idEmpresa") int idEmpresa);

    @GET("api/ordsTrab/getOTsByFechas/{fechaInic}/{fechaFinal}")
    Call<List<OrdenesTrabajo>> getListOTsByFechas(@Path("fechaInic") String fechaInic, @Path("fechaFinal") String fechaFinal);
/***************************/
    @GET("api/ordsTrab/getInfHome/{fechaInic}/{fechaFinal}")
    Call<List<OrdenesTrabajo>> getInformHomeDeOTs(@Path("fechaInic") String fechaInic, @Path("fechaFinal") String fechaFinal);
/***************************/
    @GET("api/ordsTrab/OTsByFechasUser/{fechaInic}/{fechaFinal}/{nombrUser}")
    Call<List<OrdenesTrabajo>> getListOTsByFechasUser(@Path("fechaInic") String fechaInic,
                                                      @Path("fechaFinal") String fechaFinal,
                                                      @Path("nombrUser") String nombrUser);

    @GET("api/ordsTrab/getOTsAsignadasEjecut/{fechaInic}/{fechaFinal}/{nombrEjecut}")
    Call<List<OrdenesTrabajo>> getOTsAsigndsEjecutor(@Path("fechaInic") String fechaInic,
                                                     @Path("fechaFinal") String fechaFinal,
                                                     @Path("nombrEjecut") String nombrEjecut);

    @POST("api/ordsTrab/saveNewOrdTrab")
    Call<ResponseString> guardarNuevaOrdTrab(@Body OrdenesTrab_DTO1 newOrdTrab);

    @GET("api/ordsTrab/OTsParaCerrar/{status1}/{status2}")
    Call<List<OrdenesTrab_DTO2>> getListOTsParaCerrar(@Path("status1") String status1, @Path("status2") String status2);

    @GET("api/ordsTrab/OTsParaCerrarEjec/{status1}/{status2}/{ejecutor}")
    Call<List<OrdenesTrab_DTO2>> getListOTsParaCerrarEjecut(@Path("status1") String status1, @Path("status2") String status2,
                                                            @Path("ejecutor") String ejecutor);

    @GET("api/ordsTrab/getById/{idOT}")
    Call<OrdenesTrabDTO_2> getOrdenDeTrabById(@Path("idOT") int idOT);

    @GET("api/ordsTrab/listNewOTs")
    Call<List<OrdenesTrabajo>> getListaDeOTsNuevas();

    @GET("api/ordsTrab/listNewOTsEjec/{ejecutor}")
    Call<List<OrdenesTrabajo>> getListaNewOTsByEjecutor(@Path("ejecutor") String ejecutor);

    @PUT("api/ordsTrab/saveRevAutmOT")
    Call<ResponseString> guardarRevisAutomatOT(@Body OrdenTrabRevis dtsOrdTrab);

    @POST("api/ordsTrab/saveRevOT")
    Call<OrdenTrabRevis> gurdarRevisionOT(@Body OrdenTrabRevis dtsOrdTrab);

    @POST("auth/sendEmailNewOT")
    Call<ResponseString> sendEmailNuevaOrdenTrab(@Body InformacionEmails informEmail);



    // FOTOS **************************************************
    @Multipart
    @POST("api/fotosOTs/uploadImgsIngr/{idOT}")
    Call<ResponseString> uploadImgIngresoOT(@Part MultipartBody.Part multiPart, @Path("idOT") int idOT);

    @Multipart
    @POST("api/fotosOTs/uploadImgCierre/{idOT}")
    Call<ResponseString> uploadImgCierreOT(@Part MultipartBody.Part multiPart, @Path("idOT") int idOT);

    @GET("api/fotosOTs/downloadImg/{nombreFoto}")  //Viene de FotosOtAdapter
    Call<ResponseBody> dowLoadFotoFromServer(@Path("nombreFoto") String nombreFoto);

    @GET("api/fotosOTs/fotosIngreso/{idOT}")
    Call<List<Fotos_DTO>> getListaFotosIngresoOT(@Path("idOT") int idOT);

    @GET("api/fotosOTs/fotosCierre/{idOT}")
    Call<List<Fotos_DTO>> getListaFotosCierreOT(@Path("idOT") int idOT);

    @Multipart
    @POST("api/fotosOTs/uploadImgRpteEjecRut/{idRpteEjecRut}")
    Call<ResponseString> uploadImgRpteEjecRut(@Part MultipartBody.Part multiPart, @Path("idRpteEjecRut") int idRpteEjecRut);

    @GET("api/fotosOTs/fotosRepteEjecRut/{idRepteEjecRut}")
    Call<List<Fotos_DTO>> getListaFotosRepteEjecRut(@Path("idRepteEjecRut") int idRepteEjecRut);



    // RUTINAS ***************************************************

    @GET("api/rutinas/getRutinas/{semanaRuts}")
    Call<List<RustEquiposDTO>> getRutinasSemSelecc(@Path("semanaRuts") String semanaRuts);   //07-2025

    @GET("api/rutinas/renewListRuts/{semanaRuts}/{correlat}/{ejecutor}")
    Call<List<RustEquiposDTO>> getListadoRutinas(@Path("semanaRuts") String semanaRuts,
                                                 @Path("correlat") String correlat, @Path("ejecutor") String ejecutor);

    @GET("api/rutinas/getRutEquip/{idRutEquip}")
    Call<RustEquiposDTO> getRutinaSelected(@Path("idRutEquip") int idRutEquip);

    @POST("api/rutinas/saveRpteEjecRut")
    Call<ResponseString> guardarReporteEjecRut(@Body RtesEjecRuts repteEjecRut);

    @POST("api/rutinas/saveRptePersEjecRut")
    Call<ResponseString> guardarReptePersEjecRut(@Body RtesPersEjecRuts reptePersEjecRut);

    @POST("api/rutinas/saveRepteReptoRut")
    Call<ResponseString> guardarRepteReptosEjecRut(@Body ReptesReptos_DTO repteRepto);

    @POST("api/rutinas/saveRepteSevExtRut")
    Call<ResponseString> guardarRepteServExtEjecRut(@Body RtesServExtEjecRuts servExtDts);

    @GET("api/rutinas/getRepteEjecRut/{idRteEjecRut}")
    Call<RtesEjecRuts> getRepteEjecRutina(@Path("idRteEjecRut") int idRteEjecRut);

    @GET("api/rutinas/getListPerEjecRut/{idRteEjecRut}")
    Call<List<RtesPersEjecRuts>> getListPersEjecRutina(@Path("idRteEjecRut") int idRteEjecRut);

    @GET("api/rutinas/getListReptosEjecRut/{idRteEjecRut}")
    Call<List<ReptesReptos_DTO>> getListReptosEjecRutina(@Path("idRteEjecRut") int idRteEjecRut);

    @GET("api/rutinas/getListSevExtEjecRut/{idRteEjecRut}")
    Call<List<RtesServExtEjecRuts>> getListServExtEjecRutina(@Path("idRteEjecRut") int idRteEjecRut);

    @GET("api/rutinas/uploadInfoDocRut/{dtsDocument}")
    Call<ResponseString> uploadInfoDocumentRut(@Path("dtsDocument") String dtsDocument);






}
