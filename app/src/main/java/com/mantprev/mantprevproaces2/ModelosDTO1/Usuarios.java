package com.mantprev.mantprevproaces2.ModelosDTO1;

import java.util.Date;

public class Usuarios {


    private int idUsuario;
    private String nombreUsuario;
    private String emailUsuario;
    private String userRol;
    private String nombreEmpresa;
    private String paisEmpresa;
    private String idiomaGrupo;
    private String simbMoneda;
    private String codigoPais;
    private String telefonoUser;
    private int  cantMaxUsers;
    private Date fechaFnlSuscrip;
    private int idEmpresa;


    //Constructor vacia
    public Usuarios(){

    }


    //GETTERS AND SETTERS
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getUserRol() {
        return userRol;
    }

    public void setUserRol(String userRol) {
        this.userRol = userRol;
    }

    public String getTelefonoUser() {
        return telefonoUser;
    }

    public void setTelefonoUser(String telefonoUser) {
        this.telefonoUser = telefonoUser;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getIdiomaGrupo() {
        return idiomaGrupo;
    }

    public void setIdiomaGrupo(String idiomaGrupo) {
        this.idiomaGrupo = idiomaGrupo;
    }

    public int getCantMaxUsers() {
        return cantMaxUsers;
    }

    public void setCantMaxUsers(int cantMaxUsers) {
        this.cantMaxUsers = cantMaxUsers;
    }

    public Date getFechaFnlSuscrip() {
        return fechaFnlSuscrip;
    }

    public void setFechaFnlSuscrip(Date fechaFnlSuscrip) {
        this.fechaFnlSuscrip = fechaFnlSuscrip;
    }

    public String getPaisEmpresa() {
        return paisEmpresa;
    }

    public void setPaisEmpresa(String paisEmpresa) {
        this.paisEmpresa = paisEmpresa;
    }

    public String getSimbMoneda() {
        return simbMoneda;
    }

    public void setSimbMoneda(String simbMoneda) {
        this.simbMoneda = simbMoneda;
    }

    public String getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
}
