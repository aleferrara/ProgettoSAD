package com.example.mwng.model;

public class Cat {

    String Id;
    String Nome;
    String eta;
    String sesso;
    String razza;
    String image;
    String chiave;

    public Cat (){

    }

    public Cat(String Id, String nome, String eta, String sesso, String razza, String imageID, String chiave) {
        Nome = nome;
        this.eta = eta;
        this.sesso = sesso;
        this.razza = razza;
        this.image = imageID;
        this.chiave = chiave;
    }

    public String getID() { return Id; }

    public String getNome() {
        return Nome;
    }

    public String getEta() {
        return eta;
    }

    public String getSesso() {
        return sesso;
    }

    public String getRazza() {
        return razza;
    }

    public String getImage() {
        return image;
    }

    public String getChiave() {
        return chiave;
    }

}