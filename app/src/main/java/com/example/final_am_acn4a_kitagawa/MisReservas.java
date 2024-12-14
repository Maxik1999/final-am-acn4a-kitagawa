package com.example.final_am_acn4a_kitagawa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MisReservas extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_reservas);
/*
        restaurantesListView = findViewById(R.id.restaurantesListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, restaurantes);
        restaurantesListView.setAdapter(adapter);

        restaurantesListView.setOnItemClickListener((parent, view, position, id) -> {
            restauranteSeleccionado = restaurantes[position];

            // Pasar el restaurante seleccionado a la siguiente actividad
            Intent intent = new Intent(SeleccionarRestaurante.this, DatosReserva.class);
            intent.putExtra("restaurante", restauranteSeleccionado);
            startActivity(intent);
        });*/
    }

}

