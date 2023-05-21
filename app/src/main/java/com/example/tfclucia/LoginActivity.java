package com.example.tfclucia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

import kotlin.Pair;

public class LoginActivity extends AppCompatActivity {

    TextView bienvenidoLabel, continuarLabel, nuevoUsuario;
    ImageView loginImageView;
    EditText usuarioTextField, contrasenaTextField;
    Button inicioSesion;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginImageView = findViewById(R.id.loginImageView);
        bienvenidoLabel = findViewById(R.id.bienvenidoLabel);
        continuarLabel = findViewById(R.id.continuarLabel);
        usuarioTextField = findViewById(R.id.usuarioTextField);
        contrasenaTextField = findViewById(R.id.contrasenaTextField);
        inicioSesion = findViewById(R.id.inicioSesion);
        nuevoUsuario = findViewById(R.id.nuevoUsuario);

        mAuth = FirebaseAuth.getInstance();

        inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        nuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(loginImageView, "logoImageTrans");
                pairs[1] = new Pair<View, String>(bienvenidoLabel, "textTrans");
                pairs[2] = new Pair<View, String>(continuarLabel, "iniciaSesionTextTrans");
                pairs[3] = new Pair<View, String>(usuarioTextField, "emailInputTextTrans");
                pairs[4] = new Pair<View, String>(contrasenaTextField, "passwordInputTextTrans");
                pairs[5] = new Pair<View, String>(inicioSesion, "buttonRegisterTrans");
                pairs[6] = new Pair<View, String>(nuevoUsuario, "newUserTrans");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void userLogin(){
        String emailString = usuarioTextField.getText().toString();
        String contrasenaString = contrasenaTextField.getText().toString();

        if (TextUtils.isEmpty(emailString)){
            usuarioTextField.setText("Ingrese un correo");
            usuarioTextField.requestFocus();
        } else if (TextUtils.isEmpty(contrasenaString)) {
            Toast.makeText(LoginActivity.this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show();
            contrasenaTextField.requestFocus();
        }else {

            mAuth.signInWithEmailAndPassword(emailString,contrasenaString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Bienvenid@", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                    }else {
                        Log.w("TAG","Error:",task.getException());
                    }
                }
            });
        }
    }

}