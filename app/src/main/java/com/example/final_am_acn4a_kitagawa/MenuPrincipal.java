package com.example.final_am_acn4a_kitagawa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuPrincipal extends AppCompatActivity {

    Button CerrarSesion;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    TextView nombreprincipal;
    ProgressBar progressBarDatos;
    DatabaseReference Usuarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);


        nombreprincipal = findViewById(R.id.nombreprincipal);
        progressBarDatos = findViewById(R.id.progressBarDatos);

        Usuarios = FirebaseDatabase.getInstance().getReference("Usuarios");


        CerrarSesion = findViewById(R.id.CerrarSesion);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        CerrarSesion.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View v) {
                SalirAplicacion();
            }
        });
    }

    @Override
    protected void onStart() {
        Comprabariniciosesion();
        super.onStart();
    }

    private void Comprabariniciosesion() {
        if (user!=null){
            CargaDeDatos();
        } else {
            startActivity(new Intent(MenuPrincipal.this, MainActivity.class));
            finish();
        }
    }

    private void SalirAplicacion() {
        firebaseAuth.signOut();
        startActivity(new Intent(MenuPrincipal.this, MainActivity.class));
        Toast.makeText(this, "Cerraste sesion exitosamente", Toast.LENGTH_SHORT).show();
    }

    private void CargaDeDatos(){
        Usuarios.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    progressBarDatos.setVisibility(View.GONE);
                    nombreprincipal.setVisibility(View.VISIBLE);

                    String nombre = snapshot.child("nombre").getValue(String.class);

                    nombreprincipal.setText(nombre);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void OpenReservaActivity(View v){
        Intent intent = new Intent(this, NuevaReserva.class);
        startActivity(intent);
    }

    public void OpenMisReservas(View v){
        Intent intent = new Intent(this, MisReservas.class);
        startActivity(intent);
    }
}