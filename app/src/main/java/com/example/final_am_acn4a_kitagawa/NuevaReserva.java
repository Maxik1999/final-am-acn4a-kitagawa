package com.example.final_am_acn4a_kitagawa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class NuevaReserva extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_reserva);

        ImageView imagen1 = findViewById(R.id.imagen1);
        ImageView imagen2 = findViewById(R.id.imagen2);
        ImageView imagen3 = findViewById(R.id.imagen3);
        ImageView imagen4 = findViewById(R.id.imagen4);

        // Configurar los listeners para cada imagen
        imagen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarRestaurante(
                        "La Parolaccia",
                        "@drawable/la_parolaccia"
                );
            }
        });

        imagen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarRestaurante(
                        "Bistecca",
                        "@drawable/bistecca"
                );
            }
        });

        imagen3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarRestaurante(
                        "Gourmet Porteño",
                        "@drawable/gourmetporteno"
                );
            }
        });

        imagen4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarRestaurante(
                        "Hard Rock",
                        "@drawable/hardrock"
                );
            }
        });
    }

    private void seleccionarRestaurante(String restaurante, String imagen) {
        // Obtener el ID del recurso
        int imagenResId = getResources().getIdentifier(imagen, "drawable", getPackageName());

        Intent intent = new Intent(NuevaReserva.this, NuevaReserva2.class);
        intent.putExtra("restaurante", restaurante);
        intent.putExtra("imagen", imagenResId);
        startActivity(intent);
    }

    public void volverAMain(View view) {
        Intent intent = new Intent(NuevaReserva.this, MenuPrincipal.class);
        startActivity(intent);
        finish();
    }

}
