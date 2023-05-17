package com.example.tfclucia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

import kotlin.Pair;

public class RegisterActivity extends AppCompatActivity {

    TextView nuevoUsuario, bienvenidoLabel, continuarLabel;
    ImageView registerImageView;
    EditText nameTextField, usuarioRegisterTextField, contrasenaTextField;
    MaterialButton registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                insertarDatos();
            }
        });


    }

    private void insertarDatos() {
        final String nombre = nameTextField.getText().toString().trim();
        final String email = usuarioRegisterTextField.getText().toString().trim();
        final String contrasena = contrasenaTextField.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");

        if (nombre.isEmpty()){
            Toast.makeText(this, "Ingresa un nombre", Toast.LENGTH_SHORT).show();
            return;
        } else if (email.isEmpty()){
            Toast.makeText(this, "Ingresa un correo", Toast.LENGTH_SHORT).show();
            return;
        } else if (contrasena.isEmpty()){
            Toast.makeText(this, "Ingresa una contraseña", Toast.LENGTH_SHORT).show();
            return;
        } else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "http://169.254.104.165/ws1/index.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("datos insertados")) {
                        Toast.makeText(RegisterActivity.this, "datos insertados", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                    params.put("nombre", nombre);
                    params.put("email", email);
                    params.put("contrasena", contrasena);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
            requestQueue.add(request);
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