package com.example.tfclucia;

import android.media.Image;

import java.io.Serializable;

public class Film implements Serializable {

    String titulo,sinopsis,genero,pais,actores,director,fecha,duracion;
    String foto;

    public Film(){}

    public Film(String titulo, String sinopsis, String genero, String pais, String actores, String director, String fecha, String duracion, String foto) {
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.genero = genero;
        this.pais = pais;
        this.actores = actores;
        this.director = director;
        this.fecha = fecha;
        this.duracion = duracion;
        this.foto = foto;
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
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}
