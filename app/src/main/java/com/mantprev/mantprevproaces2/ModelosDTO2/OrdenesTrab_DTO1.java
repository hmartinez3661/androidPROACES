package com.mantprev.mantprevproaces2.ModelosDTO2;


public class OrdenesTrab_DTO1 {


    int numOT;
    private String idEquipo;
    private String trabajoSolicit;
    private String persEjecutor;
    private String prioridadOT;
    private String nombreSolict;
    private String statusDeOT;
    private String fechaIngresoOT;
    private String horaAct;
    private String cantFotosAnex;


    public OrdenesTrab_DTO1(){
        //Contructor vacio
    }

    public String getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(String idEquipo) {
        this.idEquipo = idEquipo;
    }

    public int getNumOT() {
        return numOT;
    }

    public void setNumOT(int numOT) {
        this.numOT = numOT;
    }

    public String getTrabajoSolicit() {
        return trabajoSolicit;
    }

    public void setTrabajoSolicit(String trabajoSolicit) {
        this.trabajoSolicit = trabajoSolicit;
    }

    public String getPersEjecutor() {
        return persEjecutor;
    }

    public void setPersEjecutor(String persEjecutor) {
        this.persEjecutor = persEjecutor;
    }

    public String getPrioridadOT() {
        return prioridadOT;
    }

    public void setPrioridadOT(String prioridadOT) {
        this.prioridadOT = prioridadOT;
    }

    public String getNombreSolict() {
        return nombreSolict;
    }

    public void setNombreSolict(String nombreSolict) {
        this.nombreSolict = nombreSolict;
    }

    public String getStatusDeOT() {
        return statusDeOT;
    }

    public void setStatusDeOT(String statusDeOT) {
        this.statusDeOT = statusDeOT;
    }

    public String getFechaIngresoOT() {
        return fechaIngresoOT;
    }

    public void setFechaIngresoOT(String fechaIngresoOT) {
        this.fechaIngresoOT = fechaIngresoOT;
    }

    public String getHoraAct() {
        return horaAct;
    }

    public void setHoraAct(String horaAct) {
        this.horaAct = horaAct;
    }

    public String getCantFotosAnex() {
        return cantFotosAnex;
    }

    public void setCantFotosAnex(String cantFotosAnex) {
        this.cantFotosAnex = cantFotosAnex;
    }



}
