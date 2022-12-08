package com.example.law_ita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class verCitaActivity extends AppCompatActivity implements View.OnClickListener {

    Button dateCBtn, timeCBtn, guardarCitaCBtn;
    EditText editTextDateC, editTextTimeC, editTextDescripcionC;
    String name, lastname, phone, email, fecha, horario, descripcion;
    private int dia, mes, anio, hora, minutos;

    String userId;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_cita);
        //Configure ActionBar
        this.setTitle(getString(R.string.vercita));
        Toolbar toolbar = (Toolbar) findViewById(R.id.verCitaActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Declare views
        dateCBtn = (Button) findViewById(R.id.dateCBtn);
        timeCBtn = (Button) findViewById(R.id.timeCBtn);
        guardarCitaCBtn = (Button) findViewById(R.id.guardarCitaCBtn);
        editTextDateC = (EditText) findViewById(R.id.editTextDateC);
        editTextTimeC = (EditText) findViewById(R.id.editTextTimeC);
        editTextDescripcionC = (EditText) findViewById(R.id.editTextDescripcionC);
        //CLick


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("citas").document(userId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    email =  task.getResult().getString("Email");
                    lastname = task.getResult().getString("Lastname");
                    name = task.getResult().getString("Name");
                    phone =  task.getResult().getString("Phone");
                    fecha =  task.getResult().getString("Date");
                    horario = task.getResult().getString("Time");
                    descripcion = task.getResult().getString("Description");

                    editTextDateC.setText(fecha);
                    editTextTimeC.setText(horario);
                    editTextDescripcionC.setText(descripcion);
                }else{
                    Toast.makeText(verCitaActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dateCBtn.setOnClickListener(this);
        timeCBtn.setOnClickListener(this);
        guardarCitaCBtn.setOnClickListener(this);
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    @Override
    public void onClick(View v) {
        if(v==dateCBtn){
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            anio = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    editTextDateC.setText(dayOfMonth + "/" + (month+1)+"/"+year);

                }
            }, dia, mes, anio);
            datePickerDialog.show();
        }
        if(v==timeCBtn){
            final Calendar c = Calendar.getInstance();
            hora = c.get(Calendar.HOUR_OF_DAY);
            minutos = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    editTextTimeC.setText(hourOfDay + ":" + minute);
                }
            },hora, minutos,false);
            timePickerDialog.show();
        }
        if(v==guardarCitaCBtn){
            if(editTextDateC.getText().toString().isEmpty()| editTextTimeC.getText().toString().isEmpty()){
                Toast.makeText(verCitaActivity.this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();

            }else{
                updateCita();

            }
        }
    }

    public void updateCita() {

        String fechaC = editTextDateC.getText().toString();
        String horaC = editTextTimeC.getText().toString();
        String descripcionC = editTextDescripcionC.getText().toString();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("citas").document(userId);
        Map<String, Object> cita=new HashMap<>();
        cita.put("Name", name);
        cita.put("Last name", lastname);
        cita.put("Phone", phone);
        cita.put("Email", email);
        cita.put("Date", fechaC);
        cita.put("Time", horaC);
        cita.put("Description", descripcionC);

        documentReference.set(cita).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("TAG", getString(R.string.citaExitosa) + userId);
            }
        });
        Toast.makeText(verCitaActivity.this, "Cita actualizada correctamente", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(verCitaActivity.this, MainActivity.class));
    }
}