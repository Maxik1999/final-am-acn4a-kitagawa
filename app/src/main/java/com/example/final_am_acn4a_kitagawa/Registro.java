package com.example.final_am_acn4a_kitagawa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


    Spinner sexoSpinner = findViewById(R.id.sexo);

    // Crear un ArrayAdapter utilizando el arreglo de strings que definimos antes
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
            R.array.sex_options, android.R.layout.simple_spinner_item);

    // Especificar el layout para las opciones del spinner (cómo se verán las opciones)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    
    // Asignar el adapter al Spinner
    sexoSpinner.setAdapter(adapter);
    
    // Establecer el valor predeterminado
    sexoSpinner.setSelection(0);  // Esto seleccionará el primer elemento, que es "Indique su sexo"
    }
}