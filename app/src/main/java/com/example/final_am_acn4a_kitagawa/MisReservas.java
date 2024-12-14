package com.example.final_am_acn4a_kitagawa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import com.google.firebase.auth.FirebaseAuth;

public class MisReservas extends AppCompatActivity {

    private TextView textViewDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_reservas);

        textViewDatos = findViewById(R.id.textViewDatos);

        mostrarMisReservas();
    }


    private void mostrarMisReservas() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reservasRef = database.getReference("reservas");

        String usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Log.d("MisReservas", "Usuario ID: " + usuarioId);

        // Filtrar las reservas del usuario actual
        reservasRef.orderByChild("usuarioId").equalTo(usuarioId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("MisReservas", "Consulta exitosa");
                        if (task.getResult() != null && task.getResult().getChildrenCount() > 0) {
                            Log.d("MisReservas", "Resultados encontrados: " + task.getResult().getChildrenCount());
                            StringBuilder reservas = new StringBuilder();
                            for (DataSnapshot snapshot : task.getResult().getChildren()) {
                                String restaurante = snapshot.child("restaurante").getValue(String.class);
                                String fecha = snapshot.child("fecha").getValue(String.class);
                                String hora = snapshot.child("hora").getValue(String.class);
                                String comensales = snapshot.child("comensales").getValue(String.class);

                                // Agregar la información de la reserva al StringBuilder
                                reservas.append("Restaurante: ").append(restaurante)
                                        .append("\nFecha: ").append(fecha)
                                        .append("\nHora: ").append(hora)
                                        .append("\nComensales: ").append(comensales)
                                        .append("\n\n");
                            }

                            // Mostrar las reservas en el TextView
                            textViewDatos.setText(reservas.toString());
                        } else {
                            Log.d("MisReservas", "No se encontraron reservas");
                            textViewDatos.setText("No tienes reservas.");
                        }
                    } else {
                        Log.d("MisReservas", "Error en la consulta: " + task.getException().getMessage());
                        textViewDatos.setText("Error al obtener las reservas");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d("MisReservas", "Error al obtener las reservas: " + e.getMessage());
                    textViewDatos.setText("Error al obtener las reservas");
                });


    }


    public void volverAMenuPrincipal(View view) {
        // Regresar a la actividad principal (MainActivity)
        Intent intent = new Intent(this, MenuPrincipal.class);
        startActivity(intent);
    }
}
