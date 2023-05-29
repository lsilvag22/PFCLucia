package com.example.tfclucia;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class detailsFilms extends AppCompatActivity {

    TextView movieDetail_pais, textView_title_details, movieDetail_actores, movieDetail_sinopsis, movieDetail_director, movieDetail_fecha, movieDetail_duracion, movieDetail_genero;
    ImageView imageViewDetails;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_films);

        Film film = (Film) getIntent().getSerializableExtra("film");

        movieDetail_pais = (TextView) findViewById(R.id.movieDetail_pais);
        textView_title_details = (TextView) findViewById(R.id.textView_title_details);
        movieDetail_actores = (TextView) findViewById(R.id.movieDetail_actores);
        movieDetail_sinopsis = (TextView) findViewById(R.id.movieDetail_sinopsis);
        movieDetail_director = (TextView) findViewById(R.id.movieDetail_director);
        movieDetail_fecha = (TextView) findViewById(R.id.movieDetail_fecha);
        movieDetail_duracion = (TextView) findViewById(R.id.movieDetail_duracion);
        movieDetail_genero = (TextView) findViewById(R.id.movieDetail_genero);
        imageViewDetails = (ImageView) findViewById(R.id.imageViewDetails);

        String titulo = "titulo no";
        String pais = "pais no";
        String actores = "actores no";
        String sinopsis = "sinopsis no";
        String director = "director no";
        String fecha = "fecha no";
        String duracion = "duracion no";
        String genero = "genero no";
        String foto = "foto no";
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            titulo = extras.getString("titulo");
            pais = extras.getString("pais");
            actores = extras.getString("actores");
            sinopsis = extras.getString("sinopsis");
            director = extras.getString("director");
            fecha = extras.getString("fecha");
            duracion = extras.getString("duracion");
            genero = extras.getString("genero");
            foto = extras.getString("foto");
        }
        textView_title_details.setText(titulo);
        movieDetail_pais.setText(pais);
        movieDetail_actores.setText(actores);
        movieDetail_sinopsis.setText(sinopsis);
        movieDetail_director.setText(director);
        movieDetail_fecha.setText(fecha);
        movieDetail_duracion.setText(duracion);
        movieDetail_genero.setText(genero);
        Glide.with(detailsFilms.this).load(foto).into(imageViewDetails);





    }

}