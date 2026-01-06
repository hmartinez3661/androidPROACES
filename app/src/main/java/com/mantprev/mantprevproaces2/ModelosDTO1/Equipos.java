package com.mantprev.mantprevproaces2.ModelosDTO1;

public class Equipos {

    //@SerializedName("idEquipo")
    private int idEquipo;
    private int idEquipoPadre;
    private String nombEquipo;
    private String correlativo;
    private String numHijo;
    private String nivelArbol;


    //Contructor
    public Equipos(){
    }


    public int getIdEquipo() {
        return idEquipo;
    }
    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombEquipo() {
        return nombEquipo;
    }
    public void setNombEquipo(String nombEquipo) {
        this.nombEquipo = nombEquipo;
    }

    public String getCorrelativo() {
        return correlativo;
    }
    public void setCorrelativo(String correlativo) {
        this.correlativo = correlativo;
    }

    public int getIdEquipoPadre() {
        return idEquipoPadre;
    }
    public void setIdEquipoPadre(int idEquipoPadre) {
        this.idEquipoPadre = idEquipoPadre;
    }

    public String getNumHijo() {
        return numHijo;
    }
    public void setNumHijo(String numHijo) {
        this.numHijo = numHijo;
    }

    public String getNivelArbol() {
        return nivelArbol;
    }
    public void setNivelArbol(String nivelArbol) {
        this.nivelArbol = nivelArbol;
    }
}
