package com.example.final_am_acn4a_kitagawa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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