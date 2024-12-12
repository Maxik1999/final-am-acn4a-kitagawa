package com.example.final_am_acn4a_kitagawa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {

    EditText correologin, contrasenalogin;
    Button Btn_logueo, Btn_registro;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    String correo="", contrasena="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correologin = findViewById(R.id.correologin);
        contrasenalogin = findViewById(R.id.contrasenalogin);
        Btn_logueo = findViewById(R.id.buttonLogin);
        Btn_registro = findViewById(R.id.buttonRegister);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setCanceledOnTouchOutside(false);

        Btn_logueo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidarDatos();
            }
        });
        Btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registro.class));
            }
        });

    }

    private void ValidarDatos() {

        correo = correologin.getText().toString();
        contrasena = contrasenalogin.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            Toast.makeText(this, "Correo invalido", Toast.LENGTH_SHORT).show();
        }
        else  if (TextUtils.isEmpty(contrasena)){
            Toast.makeText(this, "Ingrese contraseña", Toast.LENGTH_SHORT).show();
        }
        else {
            LoginDeUsuario();

        }


    }

    private void LoginDeUsuario() {
        progressDialog.setMessage("Iniciando sesion ...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(correo,contrasena)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            startActivity(new Intent(Login.this, MenuPrincipal.class));
                            Toast.makeText(Login.this, "Bienvenido " +user.getEmail(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "Verifique si el correo y contraseña son correctos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, ""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void openRegisterActivity(View view) {
        // Crear un Intent para abrir la Activity de Registro
        Intent intent = new Intent(Login.this, Registro.class);
        startActivity(intent); // Iniciar la nueva actividad
    }

}