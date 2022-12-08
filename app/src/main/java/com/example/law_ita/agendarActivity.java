package com.example.law_ita;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.example.law_ita.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class agendarActivity extends AppCompatActivity implements View.OnClickListener {

    Button dateBtn, timeBtn, guardarCitaBtn;
    EditText editTextDate, editTextTime, editTextDescripcion;
    String name, lastname, phone, email;
    TextView textView6;
    private int dia, mes, anio, hora, minutos;

    String userId;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar);
        //Configure ActionBar
        this.setTitle(getString(R.string.agendar_cita_act));
        Toolbar toolbar = (Toolbar) findViewById(R.id.agendarActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Declare views
        dateBtn = (Button) findViewById(R.id.dateBtn);
        timeBtn = (Button) findViewById(R.id.timeBtn);
        guardarCitaBtn = (Button) findViewById(R.id.guardarCitaBtn);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextTime = (EditText) findViewById(R.id.editTextTime);
        editTextDescripcion = (EditText) findViewById(R.id.editTextDescripcion);

        //CLick
        dateBtn.setOnClickListener(this);
        timeBtn.setOnClickListener(this);
        guardarCitaBtn.setOnClickListener(this);


    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    @Override
    public void onClick(View v) {
        if(v==dateBtn){
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            anio = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    editTextDate.setText(dayOfMonth + "/" + (month+1)+"/"+year);

                }
            }, dia, mes, anio);
            datePickerDialog.show();
        }
        if(v==timeBtn){
            final Calendar c = Calendar.getInstance();
            hora = c.get(Calendar.HOUR_OF_DAY);
            minutos = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    editTextTime.setText(hourOfDay + ":" + minute);
                }
            },hora, minutos,false);
            timePickerDialog.show();
        }
        if(v==guardarCitaBtn){
            if(editTextDate.getText().toString().isEmpty()| editTextTime.getText().toString().isEmpty()){
                Toast.makeText(agendarActivity.this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();

            }else{
                crearCita();

            }
        }
    }

    public void crearCita() {
        String fecha = editTextDate.getText().toString();
        String hora = editTextTime.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("users").document(userId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    email =  task.getResult().getString("Email");
                    lastname = task.getResult().getString("Lastname");
                    name = task.getResult().getString("Name");
                    phone =  task.getResult().getString("Phone");

                    DocumentReference documentReference = db.collection("citas").document(userId);
                    Map<String, Object> cita=new HashMap<>();
                    cita.put("Name", name);
                    cita.put("Last name", lastname);
                    cita.put("Phone", phone);
                    cita.put("Email", email);
                    cita.put("Date", fecha);
                    cita.put("Time", hora);
                    cita.put("Description", descripcion);

                    documentReference.set(cita).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("TAG", getString(R.string.citaExitosa) + userId);
                        }
                    });
                    Toast.makeText(agendarActivity.this, "Cita agendada correctamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(agendarActivity.this, MainActivity.class));
                }else{
                    Toast.makeText(agendarActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}