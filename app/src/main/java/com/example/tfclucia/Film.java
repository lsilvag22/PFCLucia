package com.example.tfclucia;

public class Film {

    String titulo,sinopsis;

    public Film(){}

    public Film(String titulo, String sinopsis) {
        this.titulo = titulo;
        this.sinopsis = sinopsis;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
}
