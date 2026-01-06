package com.mantprev.mantprevproaces2.ModelosDTO1;

import java.util.Date;

public class RtesEjecOTs {

    private int idOT;
    private Date fechaInicio;
    private Date fechaFinaliz;
    private String fechaTrabajo;
    private Double calidadTrab;
    private Double tpoParoProduc;
    private Double tpoRealReparac;
    private String nombreSuperv;
    private String nombreFalla;
    private String nPersRecivTrab;
    private String reporteHistor;
    private int cantFotosCierre;
    private int cantRptosUtiliz;
    private int cantServExter;


    //CONSTRUCTOR VACIO
    public RtesEjecOTs (){
    }


    //GETTERS AND SETTERS
    public int getIdOT() {
        return idOT;
    }

    public void setIdOT(int idOT) {
        this.idOT = idOT;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Double getCalidadTrab() {
        return calidadTrab;
    }

    public void setCalidadTrab(Double calidadTrab) {
        this.calidadTrab = calidadTrab;
    }

    public Double getTpoParoProduc() {
        return tpoParoProduc;
    }

    public void setTpoParoProduc(Double tpoParoProduc) {
        this.tpoParoProduc = tpoParoProduc;
    }

    public Double getTpoRealReparac() {
        return tpoRealReparac;
    }

    public void setTpoRealReparac(Double tpoRealReparac) {
        this.tpoRealReparac = tpoRealReparac;
    }

    public String getNombreSuperv() {
        return nombreSuperv;
    }

    public void setNombreSuperv(String nombreSuperv) {
        this.nombreSuperv = nombreSuperv;
    }

    public String getNombreFalla() {
        return nombreFalla;
    }

    public void setNombreFalla(String nombreFalla) {
        this.nombreFalla = nombreFalla;
    }

    public String getnPersRecivTrab() {
        return nPersRecivTrab;
    }

    public void setnPersRecivTrab(String nPersRecivTrab) {
        this.nPersRecivTrab = nPersRecivTrab;
    }

    public String getReporteHistor() {
        return reporteHistor;
    }

    public void setReporteHistor(String reporteHistor) {
        this.reporteHistor = reporteHistor;
    }

    public int getCantFotosCierre() {
        return cantFotosCierre;
    }

    public void setCantFotosCierre(int cantFotosCierre) {
        this.cantFotosCierre = cantFotosCierre;
    }

    public int getCantRptosUtiliz() {
        return cantRptosUtiliz;
    }

    public void setCantRptosUtiliz(int cantRptosUtiliz) {
        this.cantRptosUtiliz = cantRptosUtiliz;
    }

    public int getCantServExter() {
        return cantServExter;
    }

    public void setCantServExter(int cantServExter) {
        this.cantServExter = cantServExter;
    }

    public Date getFechaFinaliz() {
        return fechaFinaliz;
    }

    public void setFechaFinaliz(Date fechaFinaliz) {
        this.fechaFinaliz = fechaFinaliz;
    }

    public String getFechaTrabajo() {
        return fechaTrabajo;
    }

    public void setFechaTrabajo(String fechaTrabajo) {
        this.fechaTrabajo = fechaTrabajo;
    }
}
