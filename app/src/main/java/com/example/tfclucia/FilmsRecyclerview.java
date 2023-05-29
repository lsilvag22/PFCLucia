package com.example.tfclucia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FilmsRecyclerview extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Film> filmArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    MyAdapter.RecyclerViewClickListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_films_recyclerview);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Cargando datos...");
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setOnClickListner();
        db = FirebaseFirestore.getInstance();
        filmArrayList = new ArrayList<Film>();
        myAdapter = new MyAdapter(FilmsRecyclerview.this,filmArrayList, listener);

        recyclerView.setAdapter(myAdapter);

        EventChangeListener();


    }

    private void setOnClickListner() {
        listener = new MyAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), detailsFilms.class);
                intent.putExtra("titulo", filmArrayList.get(position).getTitulo());
                intent.putExtra("pais",filmArrayList.get(position).getPais());
                intent.putExtra("actores",filmArrayList.get(position).getActores());
                intent.putExtra("sinopsis",filmArrayList.get(position).getSinopsis());
                intent.putExtra("director",filmArrayList.get(position).getDirector());
                intent.putExtra("fecha",filmArrayList.get(position).getFecha());
                intent.putExtra("duracion",filmArrayList.get(position).getDuracion());
                intent.putExtra("genero",filmArrayList.get(position).getGenero());
                intent.putExtra("foto",filmArrayList.get(position).getFoto());
                startActivity(intent);
            }
        };
    }

    private void EventChangeListener() {

        db.collection("films").orderBy("titulo", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){

                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){

                            if (dc.getType() == DocumentChange.Type.ADDED){

                                filmArrayList.add(dc.getDocument().toObject(Film.class));

                            }

                            myAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();

                        }

                    }
                });
    }
}