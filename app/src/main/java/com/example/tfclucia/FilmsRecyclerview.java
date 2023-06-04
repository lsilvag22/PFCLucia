package com.example.tfclucia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class FilmsRecyclerview extends AppCompatActivity {
    private static FilmsRecyclerview myContext;

    RecyclerView recyclerView;
    ArrayList<Film> filmArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    MyAdapter.RecyclerViewClickListener listener;
    public FilmsRecyclerview(){
        myContext = this;
    }
    public static FilmsRecyclerview getInstance(){
        return myContext;
    }


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
                intent.putExtra("valoracion",filmArrayList.get(position).getValoracion());
                intent.putExtra("id",filmArrayList.get(position).getId());
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

                                Film film = dc.getDocument().toObject(Film.class);
                                filmArrayList.add(film);

                            }
                            myAdapter = new MyAdapter(FilmsRecyclerview.this,filmArrayList, listener);
                            recyclerView.setAdapter(myAdapter);
                            myAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();

                        }

                    }
                });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem menuItem = menu.findItem(R.id.search_film);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Busca por titulo!");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                myAdapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }




}