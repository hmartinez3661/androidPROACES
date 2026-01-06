package com.mantprev.mantprevproaces2.ModelosDTO1;

public class RtesAprovLabor {

    int numOT;
    private Double cantHrs;
    private Double calidadTrab;
    private String idEmpleado;
    private String nombreEmpl;
    private String tipoEjecutor;
    private String idOrdTrab;
    private String fechaOrdTr;
    private String trabSolict;
    private int cantOTs;   //Contiene la cantidad de OTs que incluye el reporte



    //CONSTRUCTOR VACIO
    public RtesAprovLabor(){
    }


    //GETTERS AND SETTERS

    public int getNumOT() {
        return numOT;
    }

    public void setNumOT(int numOT) {
        this.numOT = numOT;
    }

    public Double getCantHrs() {
        return cantHrs;
    }

    public void setCantHrs(Double cantHrs) {
        this.cantHrs = cantHrs;
    }

    public Double getCalidadTrab() {
        return calidadTrab;
    }

    public void setCalidadTrab(Double calidadTrab) {
        this.calidadTrab = calidadTrab;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpl() {
        return nombreEmpl;
    }

    public void setNombreEmpl(String nombreEmpl) {
        this.nombreEmpl = nombreEmpl;
    }

    public String getTipoEjecutor() {
        return tipoEjecutor;
    }

    public void setTipoEjecutor(String tipoEjecutor) {
        this.tipoEjecutor = tipoEjecutor;
    }

    public String getIdOrdTrab() {
        return idOrdTrab;
    }

    public void setIdOrdTrab(String idOrdTrab) {
        this.idOrdTrab = idOrdTrab;
    }

    public String getFechaOrdTr() {
        return fechaOrdTr;
    }

    public void setFechaOrdTr(String fechaOrdTr) {
        this.fechaOrdTr = fechaOrdTr;
    }

    public String getTrabSolict() {
        return trabSolict;
    }

    public void setTrabSolict(String trabSolict) {
        this.trabSolict = trabSolict;
    }

    public int getCantOTs() {
        return cantOTs;
    }

    public void setCantOTs(int cantOTs) {
        this.cantOTs = cantOTs;
    }


}
