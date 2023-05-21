package com.example.tfclucia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import kotlin.Pair;

public class RegisterActivity extends AppCompatActivity {

    TextView nuevoUsuario, bienvenidoLabel, continuarLabel;
    ImageView registerImageView;
    EditText nameTextField, usuarioRegisterTextField, contrasenaTextField;
    Button registro;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        registerImageView = findViewById(R.id.registerImageView);
        bienvenidoLabel = findViewById(R.id.bienvenidoLabel);
        continuarLabel = findViewById(R.id.continuarLabel);
        usuarioRegisterTextField = findViewById(R.id.usuarioRegisterTextField);
        contrasenaTextField = findViewById(R.id.contrasenaTextField);
        nameTextField = findViewById(R.id.nameTextField);
        registro = findViewById(R.id.registro);
        nuevoUsuario = findViewById(R.id.nuevoUsuario);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                transitionBack();
                createUser();
            }
        });


    }
    public void createUser(){
        String nombre = nameTextField.getText().toString().trim();
        String email = usuarioRegisterTextField.getText().toString().trim();
        String contrasena = contrasenaTextField.getText().toString().trim();

        if (TextUtils.isEmpty(nombre)){
            Toast.makeText(this, "Ingresa un nombre", Toast.LENGTH_SHORT).show();
            nameTextField.requestFocus();
        } else if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Ingresa un correo", Toast.LENGTH_SHORT).show();
            usuarioRegisterTextField.requestFocus();
        } else if (TextUtils.isEmpty(contrasena)){
            Toast.makeText(this, "Ingresa una contraseña", Toast.LENGTH_SHORT).show();
            contrasenaTextField.requestFocus();
        } else {

            mAuth.createUserWithEmailAndPassword(email,contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        userId = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = mFirestore.collection("users").document(userId);

                        Map<String, Object> params = new HashMap<>();
                        params.put("id", userId);
                        params.put("nombre", nombre);
                        params.put("email", email);
                        params.put("contrasena", contrasena);

                        documentReference.set(params).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG", "onSuccess: Datos registrados" + userId);
                            }
                        });
                        Toast.makeText(RegisterActivity.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(RegisterActivity.this, "Usuario no registrado" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed(){
        transitionBack();
        super.onBackPressed();
        finish();
    }


    public void transitionBack() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(registerImageView, "logoImageTrans");
        pairs[1] = new Pair<View, String>(bienvenidoLabel, "textTrans");
        pairs[2] = new Pair<View, String>(continuarLabel, "iniciaSesionTextTrans");
        pairs[3] = new Pair<View, String>(usuarioRegisterTextField, "emailInputTextTrans");
        pairs[4] = new Pair<View, String>(contrasenaTextField, "passwordInputTextTrans");
        pairs[5] = new Pair<View, String>(registro, "buttonRegisterTrans");
        pairs[6] = new Pair<View, String>(nuevoUsuario, "newUserTrans");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
            finish();
        }
    }


}