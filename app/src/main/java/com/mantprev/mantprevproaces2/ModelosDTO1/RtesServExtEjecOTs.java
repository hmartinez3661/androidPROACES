package com.mantprev.mantprevproaces2.ModelosDTO1;

public class RtesServExtEjecOTs {

    private int idReporte;
    private int idOT;
    private String nombreServic;
    private Double costoServic;
    private String fechaServic;


   //CONSTRUCTOR VACIO
    public RtesServExtEjecOTs(){

    }


    //GETTERS AND SETTERS
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

    public String getNombreServic() {
        return nombreServic;
    }

    public void setNombreServic(String nombreServic) {
        this.nombreServic = nombreServic;
    }

    public Double getCostoServic() {
        return costoServic;
    }

    public void setCostoServic(Double costoServic) {
        this.costoServic = costoServic;
    }

    public String getFechaServic() {
        return fechaServic;
    }

    public void setFechaServic(String fechaServic) {
        this.fechaServic = fechaServic;
    }




}
