package com.mantprev.mantprevproaces2.ModelosDTO1;

public class RtesReptosEjecOTs {

    private int numOT;
    private int idReporte;
    private int idOT;
    private String nombreRep;
    private Double costoTotal;
    private String fechaConsumo;
    private String nombreMaq;
    private String correlat;
    private String trabSolic;


    //CONSRUCTOR
    public RtesReptosEjecOTs(int numOT, int idReporte, int idOT, String nombreRep, Double costoTotal, String fechaConsumo,
                             String nombreMaq, String correlat, String trabSolic){

        this.numOT = numOT;
        this.idReporte = idReporte;
        this.idOT = idOT;
        this.nombreRep = nombreRep;
        this.costoTotal = costoTotal;
        this.fechaConsumo = fechaConsumo;
        this.nombreMaq = nombreMaq;
        this.correlat = correlat;
        this.trabSolic = trabSolic;

    }


    //CONSTRUCTOR VACIO
    public RtesReptosEjecOTs(){
    }


    //GETTERS AND SETTERS


    public int getNumOT() {
        return numOT;
    }

    public void setNumOT(int numOT) {
        this.numOT = numOT;
    }

    public int getIdReporte() {
        return idReporte;
    }
    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public int getIdOT() {
        return idOT;
    }

    public void setIdOT(int idOT) {
        this.idOT = idOT;
    }

    public String getNombreRep() {
        return nombreRep;
    }

    public void setNombreRep(String nombreRep) {
        this.nombreRep = nombreRep;
    }

    public Double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(Double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public String getFechaConsumo() {
        return fechaConsumo;
    }

    public void setFechaConsumo(String fechaConsumo) {
        this.fechaConsumo = fechaConsumo;
    }

    public String getNombreMaq() {
        return nombreMaq;
    }

    public void setNombreMaq(String nombreMaq) {
        this.nombreMaq = nombreMaq;
    }

    public String getCorrelat() {
        return correlat;
    }

    public void setCorrelat(String correlat) {
        this.correlat = correlat;
    }

    public String getTrabSolic() {
        return trabSolic;
    }

    public void setTrabSolic(String trabSolic) {
        this.trabSolic = trabSolic;
    }
}
