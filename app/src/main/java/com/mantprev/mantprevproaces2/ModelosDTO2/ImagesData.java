package com.mantprev.mantprevproaces2.ModelosDTO2;


public class ImagesData {


    private int idOrdTrab;
    private byte[] imageData;


    public ImagesData() {
    }

    public ImagesData(int idOrdTrab, byte[] imageData) {
        this.idOrdTrab = idOrdTrab;
        this.imageData = imageData;
    }

    public int getIdOrdTrab() {
        return idOrdTrab;
    }

    public void setIdOrdTrab(int idOrdTrab) {
        this.idOrdTrab = idOrdTrab;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }



}
