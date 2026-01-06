package com.mantprev.mantprevproaces2.ModelosDTO1;


public class UserRegister {


    private String nombreUsuario;
    private String emailUsuario;
    private String password;
    private String userRol;
    private String nombreEmpresa;
    private int    idEmpresa;
    private String idiomaGrupo;
    private int cantMaxUsers;
    private String fechaSuscrip;
    private String fechaFnlSuscrip;
    private String paisEmpresa;
    private String codigoPais;  //codigoPais
    private String simbMoneda;


    public UserRegister() {
    }

    public UserRegister (String nombreUsuario, String emailUsuario, String password, String userRol, String nombreEmpresa,
                         int idEmpresa, String idiomaGrupo, int cantMaxUsers,  String fechaSuscrip, String fechaFnlSuscrip,
                         String paisEmpresa, String codigoPais, String simbMoneda) {

        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
        this.password = password;
        this.userRol = userRol;
        this.nombreEmpresa = nombreEmpresa;
        this.idEmpresa = idEmpresa;
        this.idiomaGrupo = idiomaGrupo;
        this.cantMaxUsers = cantMaxUsers;
        this.fechaSuscrip = fechaSuscrip;
        this.fechaFnlSuscrip = fechaFnlSuscrip;
        this.paisEmpresa = paisEmpresa;
        this.codigoPais = codigoPais;
        this.simbMoneda = simbMoneda;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRol() {
        return userRol;
    }

    public void setUserRol(String userRol) {
        this.userRol = userRol;
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

    public String getFechaSuscrip() {
        return fechaSuscrip;
    }

    public void setFechaSuscrip(String fechaSuscrip) {
        this.fechaSuscrip = fechaSuscrip;
    }

    public String getFechaFnlSuscrip() {
        return fechaFnlSuscrip;
    }

    public void setFechaFnlSuscrip(String fechaFnlSuscrip) {
        this.fechaFnlSuscrip = fechaFnlSuscrip;
    }

    public String getPaisEmpresa() {
        return paisEmpresa;
    }

    public void setPaisEmpresa(String paisEmpresa) {
        this.paisEmpresa = paisEmpresa;
    }

    public String getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    public String getSimbMoneda() {
        return simbMoneda;
    }

    public void setSimbMoneda(String simbMoneda) {
        this.simbMoneda = simbMoneda;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
}
