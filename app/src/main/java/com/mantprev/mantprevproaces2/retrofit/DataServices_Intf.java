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
import com.mantprev.mantprevproaces2.ModelosDTO2.Usuarios_DTO;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
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
    @POST("login")
    Call<Void> userLogin(@Field("username") String username, @Field("password") String password);

    @GET("auth/getUserByEmail/{emailUser}")
    Call<Usuarios_DTO> getUsuarioByEmail(@Path("emailUser") String emailUser);


/*
    @POST("auth/login")
    Call<UserToken> loginRequest(@Body UserCredentials userCredent);

    @POST("auth/loginAndroi")
    Call<UserToken> loginAndroiRequest(@Body UserCredentials userCredent);

    @POST("auth/registNvaEmpresa")
    Call<Integer> registNvaEmpresa(@Body Empresas_DTO nvaEmpresa);

    @POST("auth/registAdmin")
    Call<UserToken> registrarUserAdmin (@Body UserRegister UserRegister);

    @POST("auth/register")
    Call<String> registrarNvoUser(@Body UserRegister UserRegister);

    @GET("auth/userPasswProv/{emailUser}")
    Call<String> getUserPasswProvis(@Path("emailUser") String emailUser);

    @PUT("auth/update")
    Call<String> actualizarDatosNvoUser(@Body Usuarios_DTO Usuarios_DTO);

    @GET("auth/getNewestAppVers")
    Call<Integer> getNewestAppVersion();

    @POST("auth/sendEmailConfCode")
    Call<String> sendEmailConfirmCode(@Body InformacionEmails informEmail);

    @POST("auth/sendEmailNewUserAdmin")
    Call<String> sendEmailNewUserAdminPrincipal(@Body InformacionEmails informEmail);

    @POST("auth/sendEmailRecupPassw")
    Call<String> sendEmailRecuperacPassword (@Body InformacionEmails informEmail);

    @POST("auth/sendEmailNvoUser")
    Call<String> sendEmailNuevoUsuario (@Body InformacionEmails informEmail);

    @POST("auth/sendEmailRenovSuscrip")
    Call<String> sendEmailRenovacSuscrip (@Body InformacionEmails informEmail);

    @POST("auth/emailInformSucrVenc")
    Call<String> sendEmailInforSuscVencida (@Body InformacionEmails informEmail);
*/

    //******* Fin de end points sin necesidad de token *********/


    @GET("api/usuarios/getSuperv")
    Call<List<Usuarios_DTO>> getLstaDeSupervisores();



/*
    @GET("api/v1/usuarios/getAll/{idEmpresa}")
    Call<List<Usuarios>> getLstaDeUsuarios(@Path("idEmpresa") int idEmpresa);

    @GET("api/v1/usuarios/getUserByEmail/{emailUser}")
    Call<Usuarios> getUserByEmail(@Path("emailUser") String emailUser);

    @PUT("api/v1/usuarios/update/{idEmpresa}")
    Call<String> actualizarDatosUsuario1(@Body Usuarios_DTO Usuarios_DTO, @Path("idEmpresa") int idEmpresa);

    @DELETE("api/v1/usuarios/delete/{emailUsuario}")
    Call<String> eliminarUsuarioDeBD(@Path("emailUsuario") String emailUsuario);

    @PUT("api/v1/usuarios/updateDtsEmpr/{idEmpresa}/{nvaCantMaxUsers}/{nvaFechaExpirac}")
    Call<String> actualizarDatosEmpresa(@Path("idEmpresa") int idEmpresa, @Path("nvaCantMaxUsers") int nvaCantMaxUsers,
                                        @Path("nvaFechaExpirac") Date nvaFechaExpirac);
*/


    //REPORTES EJECUCUON OTs ***************************
    @POST("api/reptes/saveRepteEjec2")
    Call<String> guardarReporteEjecOT2(@Body RtesEjecOTs repteEjecOT);

    @POST("api/reptes/saveReptePers")
    Call<String> guardarReptePersEjecOT(@Body RtesPersEjecOTs reptePers);

    @POST("api/reptes/saveRepteRepto")
    Call<String> guardarRepteReptosEjecOT(@Body ReptesReptos_DTO repteRepto);

    @POST("api/reptes/saveRepteSevExt")
    Call<String> guardarRepteServExtEjecOT(@Body RtesServExtEjecOTs repteServExt);

    @GET("api/reptes/getRepteEjec/{idReporte}")
    Call<RtesEjecOTs> getRepteEjecucOT(@Path("idReporte") int idReporte);

    @GET("api/reptes/reptsPers/{idOT}")
    Call<List<Repte2Datos>> getListReptesPersEjecOTByIdOT(@Path("idOT") int idOT);

    @GET("api/reptes/listaReptos/{idOT}")
    Call<List<Repte2Datos>> getListReptesRepuestosEjecOT(@Path("idOT") int idOT);

    @GET("api/reptes/listaSevExt/{idOT}")
    Call<List<Repte2Datos>> getListaReptesServExterEjecOT(@Path("idOT") int idOT);



/*
    @GET("api/v1/reptes/getRtesPers/{fechaInic}/{fechaFinal}/{idEmpresa}")
    Call<List<ReptesPersTecn_DTO>> getListReptesPersEjecOTsByDates(@Path("fechaInic") String fechaInic, @Path("fechaFinal") String fechaFinal,
                                                                   @Path("idEmpresa") int idEmpresa);

    @GET("api/v1/reptes/getRtesReptos/{fechaInic}/{fechaFinal}/{numFormat}/{idEmpresa}")
    Call<List<ReptesReptos_DTO>> getListReptesReptosEjecOTsByDates(@Path("fechaInic") String fechaInic, @Path("fechaFinal") String fechaFinal,
                                                                   @Path("numFormat") String numFormat, @Path("idEmpresa") int idEmpresa);

    @GET("api/v1/reptes/getRtesHrsParo/{fechaInic}/{fechaFinal}/{idEmpresa}")
    Call<List<ReptesParoEquips_DTO>> getListReptesHrsParoEquipos(@Path("fechaInic") String fechaInic, @Path("fechaFinal") String fechaFinal,
                                                                 @Path("idEmpresa") int idEmpresa);

    @GET("api/v1/reptes/getRteRecurrFalla/{nombrFalla}/{fechaInic}/{fechaFinal}/{idEmpresa}")
    Call<List<ReptesRecurFallas_DTO>> getListReptesRecurrFallas(@Path("nombrFalla") String nombrFalla, @Path("fechaInic") String fechaInic,
                                                                @Path("fechaFinal") String fechaFinal, @Path("idEmpresa") int idEmpresa);

    @GET("api/v1/reptes/getListFallas/{fechaInic}/{fechaFinal}/{idEmpresa}")
    Call<List<ReptesRecurFallas_DTO>> getListaFallasByFechas(@Path("fechaInic") String fechaInic, @Path("fechaFinal") String fechaFinal,
                                                             @Path("idEmpresa") int idEmpresa);

    @GET("api/v1/reptes/getRepteAver/{fechaInic}/{fechaFinal}/{idEmpresa}")
    Call<List<ReptesAverias_DTO>> getDtsAveriasTodosEquipos(@Path("fechaInic") String fechaInic, @Path("fechaFinal") String fechaFinal,
                                                            @Path("idEmpresa") int idEmpresa);

    @GET("api/v1/reptes/historEquips/{fechaInic}/{fechaFinal}/{idEmpresa}")
    Call<List<RepteHistMantto_DTO>> getHistorialManttoEquips(@Path("fechaInic") String fechaInic, @Path("fechaFinal") String fechaFinal,
                                                             @Path("idEmpresa") int idEmpresa);

*/


    //PRESONAL TECNICO   ******************************
    @GET("api/persTecn/getAll")
    Call<List<PersonalTecn>> getLstaDePersonalTecn();

/*

    @POST("api/v1/persTecn/saveNew/{nombrTecnico}/{tipoEjecutor}/{idEmpresa}")
    Call<String> registrarNuevoTecnico(@Path("nombrTecnico") String nombrTecnico, @Path("tipoEjecutor") String tipoEjecutor, @Path("idEmpresa") int idEmpresa);

    @PUT("api/v1/persTecn/update/{nombreTecn}/{tipoEjecut}/{nombrOrign}/{idEmpresa}")
    Call<String> actualizarDatosTecnico(@Path("nombreTecn") String nombreTecn, @Path("tipoEjecut") String tipoEjecut,
                                        @Path("nombrOrign") String nombrOrign, @Path("idEmpresa") int idEmpresa);

    @PUT("api/v1/persTecn/delete/{nombreTecn}/{idEmpresa}")
    Call<String> eliminarPersTecnico(@Path("nombreTecn") String nombreTecn, @Path("idEmpresa") int idEmpresa);
*/


    //FALLAS   *****************************************
    @GET("api/fallas/getAll")
    Call<List<Fallas>> getListaDeFallas();

/*
    @PUT("api/v1/fallas/saveFallasInic")
    Call<String> setConfigInicialFallas(@Body List<Fallas> listInicFallas);

    @POST("api/v1/fallas/save/{nombrFalla}/{tipoFalla}/{idEmpresa}")
    Call<String> registrarNuevaFalla(@Path("nombrFalla") String nombrFalla, @Path("tipoFalla") String tipoFalla, @Path("idEmpresa") int idEmpresa);

    @DELETE("api/v1/fallas/delete/{nombreFalla}/{idEmpresa}")
    Call<String> eliminarRegistroFalla(@Path("nombreFalla") String nombreFalla, @Path("idEmpresa") int idEmpresa);
*/


    // EQUIPOS  ***************************************
    @GET("api/equipos/getAll")
    Call<List<Equipos>> getTodosLosEquipos();

/*
    @DELETE("api/v1/equipos/delete/{idEquipo}")
    Call<String> eliminarEquipoById(@Path("idEquipo") int idEquipo);

    @POST("api/v1/equipos/save/{nombreNvoEquip}/{correlatNvoEqu}")
    Call<String> registrarNuevoEquipo(@Path("nombreNvoEquip") String nombreNvoEquip, @Path("correlatNvoEqu") String correlatNvoEqu);

    @PUT("api/v1/equipos/insertNvoEquip")
    Call<String> gurdarNuevoEquipo(@Body Equipos nvoEquipo);

    @PUT("api/v1/equipos/update/{idEquipo}/{nombreEquipo}/{correlatEquip}")
    Call<String> actualizarDatosEquipo(@Path("idEquipo") int idEquipo, @Path("nombreEquipo") String nombreEquipo, @Path("correlatEquip") String correlatEquip);

    @GET("api/v1/equipos/getFichTec/{idEquipo}")
    Call<Equipos_DTO> getEquipoById(@Path("idEquipo") String idEquipo);

    @PUT("api/v1/equipos/saveFichaTec/{idEquipo}/{datosTecn}/{nombrFotogr}")
    Call<String> guardarFichaTecnica(@Path("idEquipo") int idEquipo, @Path("datosTecn") String datosTecn, @Path("nombrFotogr") String nombrFotogr);
*/

/*************************/
    //CONFIGURACIONES SPINNERS  ******************************************
    @GET("auth/getConf")
    Call<List<ConfigSpinners>> getConfiguracSpinner();  //está aqui para crear el primer usuario Admin
/*************************/

    @GET("api/configSpn/getConf")
    Call<List<ConfigSpinners>> getLstaDeEjecutores();




/*
    @POST("api/v1/configSpn/setConfigSpins")
    Call<String> setConfigInicDeSpinns(@Body List<ConfigSpinners> listConfigInicSpinns);

    @PUT("api/v1/configSpn/actualizEjectOTs/{idEmpresa}")
    Call<String> actualizarEjecutorOTs(@Body List<String> listaEjecutores, @Path("idEmpresa") int idEmpresa);

    @PUT("api/v1/configSpn/actualizClasificOTs/{idEmpresa}")
    Call<String> actualizarClasificOTs(@Body List<String> listaClasificOTs, @Path("idEmpresa") int idEmpresa);

    @PUT("api/v1/configSpn/actualizPrioridsOTs/{idEmpresa}")
    Call<String> actualizarPrioridadOTs(@Body List<String> listaPriorid, @Path("idEmpresa") int idEmpresa);

    @PUT("api/v1/configSpn/actualizClasificFallas/{idEmpresa}")
    Call<String> updateClasificFallas(@Body List<String> listaClasifFallas, @Path("idEmpresa") int idEmpresa);

    @PUT("api/v1/configSpn/updateConfigEmails/{configEmails}/{idEmpresa}")
    Call<String> actualizarConfigEmails(@Path("configEmails") String configEmails, @Path("idEmpresa") int idEmpresa);
*/


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
    Call<String> guardarNuevaOrdTrab(@Body OrdenesTrab_DTO1 newOrdTrab);

    @GET("api/ordsTrab/OTsParaCerrar/{status1}/{status2}")
    Call<List<OrdenesTrab_DTO2>> getListOTsParaCerrar(@Path("status1") String status1, @Path("status2") String status2);

    @GET("api/ordsTrab/OTsParaCerrarEjec/{status1}/{status2}/{ejecutor}")
    Call<List<OrdenesTrab_DTO2>> getListOTsParaCerrarEjecut(@Path("status1") String status1, @Path("status2") String status2,
                                                            @Path("ejecutor") String ejecutor);

    @GET("api/ordsTrab/getById/{idOT}")
    Call<OrdenesTrabDTO_2> getOrdenDeTrabById (@Path("idOT") int idOT);

    @GET("api/ordsTrab/listNewOTs")
    Call<List<OrdenesTrabajo>> getListaDeOTsNuevas();

    @GET("api/ordsTrab/listNewOTsEjec/{ejecutor}")
    Call<List<OrdenesTrabajo>> getListaNewOTsByEjecutor (@Path("ejecutor") String ejecutor);

    @PUT("api/ordsTrab/saveRevAutmOT")
    Call<String> guardarRevisAutomatOT(@Body OrdenTrabRevis dtsOrdTrab);

    @PUT("api/ordsTrab/saveRevOT")
    Call<String> gurdarRevisionOT(@Body OrdenTrabRevis dtsOrdTrab);

    @POST("auth/sendEmailNewOT")
    Call<String> sendEmailNuevaOrdenTrab (@Body InformacionEmails informEmail);



    // FOTOS **************************************************
    @Multipart
    @POST("api/fotosOTs/uploadImgsIngr/{idOT}")
    Call<String> uploadImgIngresoOT(@Part MultipartBody.Part multiPart, @Path("idOT") int idOT);

    @Multipart
    @POST("api/fotosOTs/uploadImgCierre/{idOT}")
    Call<String> uploadImgCierreOT(@Part MultipartBody.Part multiPart, @Path("idOT") int idOT);

    @GET("api/fotosOTs/downloadImg/{nombreFoto}")  //Viene de FotosOtAdapter
    Call<ResponseBody> dowLoadFotoFromServer(@Path("nombreFoto") String nombreFoto);

    @GET("api/fotosOTs/fotosIngreso/{idOT}")
    Call<List<Fotos_DTO>> getListaFotosIngresoOT(@Path("idOT") int idOT);

    @GET("api/fotosOTs/fotosCierre/{idOT}")
    Call<List<Fotos_DTO>> getListaFotosCierreOT(@Path("idOT") int idOT);

    @Multipart
    @POST("api/fotosOTs/uploadImgRpteEjecRut/{idRpteEjecRut}")
    Call<String> uploadImgRpteEjecRut(@Part MultipartBody.Part multiPart, @Path("idRpteEjecRut") int idRpteEjecRut);

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
    Call<String> guardarReporteEjecRut(@Body RtesEjecRuts repteEjecRut);

    @POST("api/rutinas/saveRptePersEjecRut")
    Call<String> guardarReptePersEjecRut(@Body RtesPersEjecRuts reptePersEjecRut);

    @POST("api/rutinas/saveRepteReptoRut")
    Call<String> guardarRepteReptosEjecRut(@Body ReptesReptos_DTO repteRepto);

    @POST("api/rutinas/saveRepteSevExtRut")
    Call<String> guardarRepteServExtEjecRut(@Body RtesServExtEjecRuts servExtDts);

    @GET("api/rutinas/getRepteEjecRut/{idRteEjecRut}")
    Call<RtesEjecRuts> getRepteEjecRutina(@Path("idRteEjecRut") int idRteEjecRut);

    @GET("api/rutinas/getListPerEjecRut/{idRteEjecRut}")
    Call<List<RtesPersEjecRuts>> getListPersEjecRutina(@Path("idRteEjecRut") int idRteEjecRut);

    @GET("api/rutinas/getListReptosEjecRut/{idRteEjecRut}")
    Call<List<ReptesReptos_DTO>> getListReptosEjecRutina(@Path("idRteEjecRut") int idRteEjecRut);

    @GET("api/rutinas/getListSevExtEjecRut/{idRteEjecRut}")
    Call<List<RtesServExtEjecRuts>> getListServExtEjecRutina(@Path("idRteEjecRut") int idRteEjecRut);

    @GET("api/rutinas/uploadInfoDocRut/{dtsDocument}")
    Call<String> uploadInfoDocumentRut(@Path("dtsDocument") String dtsDocument);






}
