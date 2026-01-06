package com.mantprev.mantprevproaces2.ModelosDTO1;

public class RtesPersEjecOTs {

    private int idReporte;
    private Double cantHrs;
    private Double calidadTrab;
    private String fechaEjec;
    private int idOT;
    private int idEmpleado;
    private String comdNombrEmpl;
    private String comdTipoEjec;


    //CONSTRUCTORE VACIO
    public RtesPersEjecOTs(){
    }


    //GETTERS AND SETTERS
    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
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

    public String getFechaEjec() {
        return fechaEjec;
    }

    public void setFechaEjec(String fechaEjec) {
        this.fechaEjec = fechaEjec;
    }

    public int getIdOT() {
        return idOT;
    }

    public void setIdOT(int idOT) {
        this.idOT = idOT;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getComdNombrEmpl() {
        return comdNombrEmpl;
    }

    public void setComdNombrEmpl(String comdNombrEmpl) {
        this.comdNombrEmpl = comdNombrEmpl;
    }

    public String getComdTipoEjec() {
        return comdTipoEjec;
    }

    public void setComdTipoEjec(String comdTipoEjec) {
        this.comdTipoEjec = comdTipoEjec;
    }






}
