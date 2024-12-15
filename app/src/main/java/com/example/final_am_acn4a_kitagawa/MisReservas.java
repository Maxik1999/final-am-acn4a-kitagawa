package com.example.final_am_acn4a_kitagawa;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MisReservas extends AppCompatActivity {

    private LinearLayout linearLayoutReservas;  // LinearLayout para contener las reservas
    private TextView textViewDatos;  // TextView para mostrar el mensaje de "Cargando reservas..."

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_reservas);

        linearLayoutReservas = findViewById(R.id.linearLayoutReservas);  // LinearLayout donde se agregarán las reservas
        textViewDatos = findViewById(R.id.textViewDatos);  // TextView para mostrar el estado

        // Llamar a la función para obtener las reservas
        obtenerReservas();
    }

    private void obtenerReservas() {
        DatabaseReference reservasRef = FirebaseDatabase.getInstance().getReference("Reservas");

        // Obtener el ID del usuario logueado
        String usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Log.d("MisReservas", "Usuario ID: " + usuarioId);  // Verifica que el usuarioId es correcto

        // Crear una lista para almacenar todas las reservas
        ArrayList<Reserva> reservasList = new ArrayList<>();

        // Obtener todas las reservas desde Firebase
        reservasRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                Log.d("MisReservas", "Consulta exitosa, obteniendo todas las reservas...");

                // Iterar por todas las reservas y agregarlas a la lista
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    Reserva reserva = snapshot.getValue(Reserva.class);
                    if (reserva != null && reserva.getUsuarioId().equals(usuarioId)) {
                        reservasList.add(reserva);  // Agregar reserva a la lista
                        Log.d("MisReservas", "Reserva encontrada: " + reserva.toString());
                    }
                }

                // Llamar a la función para mostrar las reservas en el LinearLayout
                mostrarReservas(reservasList);

            } else {
                Log.e("MisReservas", "Error al obtener las reservas: " + task.getException().getMessage());
                textViewDatos.setText("No se pudieron cargar las reservas.");
            }
        });
    }

    private void mostrarReservas(ArrayList<Reserva> reservasList) {
        linearLayoutReservas.removeAllViews();  // Limpiar el layout antes de agregar nuevas vistas

        if (reservasList.isEmpty()) {
            textViewDatos.setText("No tienes reservas.");
        } else {
            textViewDatos.setText("");  // Limpiar el mensaje de "Cargando reservas..."

            // Iterar sobre la lista de reservas y agregar cada una al LinearLayout
            for (Reserva reserva : reservasList) {
                // Inflar el layout para cada reserva
                View reservaView = LayoutInflater.from(MisReservas.this)
                        .inflate(R.layout.item_reserva, linearLayoutReservas, false);

                // Asignar los valores de la reserva a los TextViews correspondientes
                TextView restauranteTextView = reservaView.findViewById(R.id.textViewRestaurante);
                TextView fechaTextView = reservaView.findViewById(R.id.textViewFecha);
                TextView horaTextView = reservaView.findViewById(R.id.textViewHora);
                TextView comensalesTextView = reservaView.findViewById(R.id.textViewComensales);

                restauranteTextView.setText(reserva.getRestaurante());
                fechaTextView.setText(reserva.getFecha());
                horaTextView.setText(reserva.getHora());
                comensalesTextView.setText(String.valueOf(reserva.getComensales()));

                // Agregar la vista de la reserva al LinearLayout
                linearLayoutReservas.addView(reservaView);
            }
        }
    }
}

