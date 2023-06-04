package com.example.tfclucia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateComentarioActivity extends AppCompatActivity {

    Button btn_publicar;
    EditText comentario;
    private FirebaseFirestore mfirestore;
    String idComentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comentario);
        idComentario = getIntent().getStringExtra("idComentario");
        this.setTitle("Crear comentario");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mfirestore = FirebaseFirestore.getInstance();

        comentario = findViewById(R.id.comentarioCrear);
        btn_publicar = findViewById(R.id.btn_publicar);

        btn_publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comentarioString = comentario.getText().toString().trim();

                if (comentarioString.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingresa el comentario", Toast.LENGTH_SHORT).show();
                }else {
                    postComentario(comentarioString);
                }
            }
        });
    }
    private void postComentario(String comentarioString) {
        Map<String, Object> map = new HashMap<>();
        map.put("comentario", comentarioString);

        mfirestore.collection("films").document(idComentario).collection("comentarios").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Publicado exitosamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al publicar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}