package com.example.final_am_acn4a_kitagawa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registro extends AppCompatActivity {

    EditText nombreET, apellidoET, correoET, nacimientoET, contrasenaET;
    Button registrar;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    String nombre = "", apellido = "", correo = "", nacimiento = "", contrasena = "", sexo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);



        nombreET = findViewById(R.id.nombreET);
        apellidoET = findViewById(R.id.apellidoET);
        correoET = findViewById(R.id.correoET);
        nacimientoET = findViewById(R.id.nacimientoET);
        contrasenaET = findViewById(R.id.contrasenaET);
        registrar = findViewById(R.id.registrar);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(Registro.this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setCanceledOnTouchOutside(false);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidarDatos();
            }
        });

    }

    private void ValidarDatos() {
        nombre = nombreET.getText().toString();
        apellido = apellidoET.getText().toString();
        correo = correoET.getText().toString();
        nacimiento = nacimientoET.getText().toString();
        contrasena = contrasenaET.getText().toString();

        String fechaformat = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/([12][0-9]{3})$";


        if (TextUtils.isEmpty(nombre)) {
            Toast.makeText(this, "Ingrese nombre", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(apellido)) {
            Toast.makeText(this, "Ingrese el apellido", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "Ingrese un correo válido", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(nacimiento)) {
            Toast.makeText(this, "Ingrese la fecha de nacimiento", Toast.LENGTH_SHORT).show();
        } else if (!nacimiento.matches(fechaformat)) { // Validación de formato de fecha
            Toast.makeText(this, "La fecha de nacimiento debe tener el formato DD/MM/YYYY", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(contrasena)) {
            Toast.makeText(this, "Ingrese la contraseña", Toast.LENGTH_SHORT).show();
        } else {
            CrearCuenta();
        }
    }

    private void CrearCuenta() {
        progressDialog.setMessage("Creando su cuenta...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(correo, contrasena)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        GuardarInformacion();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Registro.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void GuardarInformacion() {
        progressDialog.setMessage("Guardando su información");
        progressDialog.dismiss();

        String uid = firebaseAuth.getUid();

        HashMap<String, String> Datos = new HashMap<>();
        Datos.put("uid", uid);
        Datos.put("apellido", apellido);
        Datos.put("correo", correo);
        Datos.put("nacimiento", nacimiento);
        Datos.put("contrasena", contrasena);


        // Guardar datos en Firebase (Ejemplo)
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(
                "Usuarios");
        databaseReference.child(uid)
                .setValue(Datos)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressDialog.dismiss();
                                Toast.makeText(Registro.this, "Cuenta creada con exito", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Registro.this, MenuPrincipal.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Registro.this ,""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
