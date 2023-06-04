package com.example.tfclucia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ComentariosActivity extends AppCompatActivity {

    Button btn_add;
    RecyclerView mRecycler;
    ComentarioAdapter mAdapter;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);
        mFirestore = FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.recycleViewComentarios);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        String idDetalle = getIntent().getStringExtra("idDetalle");

        Query query = mFirestore.collection("films").document(idDetalle).collection("comentarios");

        FirestoreRecyclerOptions<Comentario> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Comentario>().setQuery(query, Comentario.class).build();

        mAdapter = new ComentarioAdapter(firestoreRecyclerOptions);
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);

        btn_add = findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreateComentarioActivity.class);
                i.putExtra("idComentario", idDetalle);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}