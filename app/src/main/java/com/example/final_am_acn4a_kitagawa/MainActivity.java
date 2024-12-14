package com.example.final_am_acn4a_kitagawa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        // Comprobamos si el usuario está logueado
        if (mAuth.getCurrentUser() != null) {
            // Si ya está logueado, lo enviamos al menu principal
            Intent intent = new Intent(MainActivity.this, MenuPrincipal.class);
            startActivity(intent);
            finish(); // Para que no regrese a MainActivity al presionar el botón "Atrás"
        }

    }
    public void openLoginActivity(View view) {
        // Crear un Intent para ir a LoginActivity
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    public void openRegisterActivity(View view) {
        // Crear un Intent para abrir la Activity de Registro
        Intent intent = new Intent(MainActivity.this, Registro.class);
        startActivity(intent); // Iniciar la nueva actividad
    }

}