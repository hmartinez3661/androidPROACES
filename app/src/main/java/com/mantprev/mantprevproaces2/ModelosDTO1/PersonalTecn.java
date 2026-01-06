package com.mantprev.mantprevproaces2.ModelosDTO1;

import java.util.Date;

public class PersonalTecn {
/*************************/

    private int idEmpleado;
    private String nombre;
    private Date fechaNacim;
    private Date fechaIngreso;
    private String telefonos;
    private String tipoEjecutor;
    private String email;
    private String fotoEmpleado;
    private String funciones;



    //Constructor vacio
    public PersonalTecn(){
    }


    //GETTERS AND SETTERS
    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Date getFechaNacim() {
        return fechaNacim;
    }

    public void setFechaNacim(Date fechaNacim) {
        this.fechaNacim = fechaNacim;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getTipoEjecutor() {
        return tipoEjecutor;
    }

    public void setTipoEjecutor(String tipoEjecutor) {
        this.tipoEjecutor = tipoEjecutor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public String getFotoEmpleado() {
        return fotoEmpleado;
    }

    public void setFotoEmpleado(String fotoEmpleado) {
        this.fotoEmpleado = fotoEmpleado;
    }

    public String getFunciones() {
        return funciones;
    }

    public void setFunciones(String funciones) {
        this.funciones = funciones;
    }
}
