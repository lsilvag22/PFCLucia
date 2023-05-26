package com.example.tfclucia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

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

        db = FirebaseFirestore.getInstance();
        filmArrayList = new ArrayList<Film>();
        myAdapter = new MyAdapter(FilmsRecyclerview.this,filmArrayList);

        recyclerView.setAdapter(myAdapter);

        EventChangeListener();


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