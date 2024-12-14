package com.example.final_am_acn4a_kitagawa;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class NuevaReserva2 extends AppCompatActivity {

    EditText fechaET, comensalesET, horaET;
    TextView restauranteNombre;
    ImageView restauranteImagen;
    Button confirmarReservaBtn;
    String restauranteSeleccionado, restauranteNombreStr, restauranteImagenUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_reserva2);

        // Recuperar el restaurante seleccionado
        restauranteSeleccionado = getIntent().getStringExtra("restaurante");
        restauranteNombreStr = getIntent().getStringExtra("nombreRestaurante");
        restauranteImagenUrl = getIntent().getStringExtra("imagenRestaurante");

        restauranteNombre = findViewById(R.id.restauranteNombre);
        restauranteImagen = findViewById(R.id.restauranteImagen);
        fechaET = findViewById(R.id.fechaET);
        comensalesET = findViewById(R.id.comensalesET);
        horaET = findViewById(R.id.horaET);
        confirmarReservaBtn = findViewById(R.id.confirmarReservaBtn);

        // Configurar nombre del restaurante
        restauranteNombre.setText(restauranteNombreStr);

        ImageView restauranteImagen = findViewById(R.id.restauranteImagen);
        int imagenResId = getIntent().getIntExtra("imagen", 0);

        if (imagenResId != 0) {
            Glide.with(this)
                    .load(imagenResId)  // Cargar la imagen desde el ID del recurso
                    .into(restauranteImagen);  // Mostrarla en el ImageView con el ID correcto
        }


        fechaET = findViewById(R.id.fechaET);
        comensalesET = findViewById(R.id.comensalesET);
        horaET = findViewById(R.id.horaET);
        confirmarReservaBtn = findViewById(R.id.confirmarReservaBtn);

        confirmarReservaBtn.setOnClickListener(v -> guardarReserva());

        Button volverBtn = findViewById(R.id.volverBtn);

        // Configurar el Listener del botón
        volverBtn.setOnClickListener(v -> {
            // Cuando el botón es presionado, se llama al método onBackPressed()
            onBackPressed();
        });
    }

    private void guardarReserva() {
        String fecha = fechaET.getText().toString().trim();
        String comensales = comensalesET.getText().toString().trim();
        String hora = horaET.getText().toString().trim();

        // Validar campos vacíos
        if (fecha.isEmpty() || comensales.isEmpty() || hora.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar formato de la fecha (DD/MM)
        if (!fecha.matches("\\d{2}/\\d{2}")) {
            Toast.makeText(this, "La fecha debe estar en formato DD/MM", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar formato de la hora (HH:MM)
        if (!hora.matches("\\d{2}:\\d{2}")) {
            Toast.makeText(this, "La hora debe estar en formato HH:MM", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar número de comensales (máximo 10)
        int numComensales;
        try {
            numComensales = Integer.parseInt(comensales);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "La cantidad de comensales debe ser un número", Toast.LENGTH_SHORT).show();
            return;
        }

        if (numComensales < 1 || numComensales > 10) {
            Toast.makeText(this, "La cantidad de comensales debe estar entre 1 y 10", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar usuario autenticado
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) {
            Toast.makeText(this, "Error: usuario no autenticado", Toast.LENGTH_SHORT).show();
            return;
        }

        // Guardar en Firebase
        DatabaseReference reservasRef = FirebaseDatabase.getInstance().getReference("Reservas");
        String reservaId = reservasRef.push().getKey();

        HashMap<String, Object> reservaData = new HashMap<>();
        reservaData.put("usuarioId", uid);
        reservaData.put("restaurante", restauranteSeleccionado);
        reservaData.put("fecha", fecha);
        reservaData.put("hora", hora);
        reservaData.put("comensales", numComensales);

        reservasRef.child(reservaId).setValue(reservaData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(NuevaReserva2.this, "Reserva realizada con éxito", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NuevaReserva2.this, "Error al guardar la reserva: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
