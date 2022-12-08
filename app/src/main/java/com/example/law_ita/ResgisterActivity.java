package com.example.law_ita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.law_ita.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ResgisterActivity extends AppCompatActivity {

    private EditText name;
    private EditText lastname;
    private EditText email;
    private EditText phone;
    private EditText password;
    private EditText confPassword;

    private String userId;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    //Create object of DatabaseReference class to access firebase's Realtime Database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://law-ita-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgister);

        name = findViewById(R.id.name);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        confPassword = findViewById(R.id.confPassword);
        Button registrarseBtn = findViewById(R.id.registrarseBtn);
        TextView cancelarRegistroBtn = findViewById(R.id.cancelarRegistroBtn);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        registrarseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        cancelarRegistroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Back to LoginActivity
                finish();
            }
        });
    }

    public void createUser(){
        String nameTxt = name.getText().toString();
        String lastnameTxt = lastname.getText().toString();
        String emailTxt = email.getText().toString();
        String phoneTxt = phone.getText().toString();
        String passwordTxt = password.getText().toString();
        String confPasswordTxt = confPassword.getText().toString();

        if(nameTxt.isEmpty() || lastnameTxt.isEmpty() || emailTxt.isEmpty() || phoneTxt.isEmpty() ||
                passwordTxt.isEmpty() || confPasswordTxt.isEmpty()){
            Toast.makeText(ResgisterActivity.this, R.string.llenar_campos, Toast.LENGTH_SHORT).show();

        } //Check if passwords are matching with each other
        else if(!passwordTxt.equals(confPasswordTxt)){
            Toast.makeText(ResgisterActivity.this, R.string.no_match, Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.createUserWithEmailAndPassword(emailTxt, passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        userId = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = db.collection("users").document(userId);

                        Map<String, Object> user=new HashMap<>();
                        user.put("Name", nameTxt);
                        user.put("Lastname", lastnameTxt);
                        user.put("Email", emailTxt);
                        user.put("Phone", phoneTxt);
                        user.put("Password", passwordTxt);
                        user.put("confPassword", confPasswordTxt);

                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG", getString(R.string.registroExitoso) + userId);
                            }
                        });
                        Toast.makeText(ResgisterActivity.this, R.string.usuarioR, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ResgisterActivity.this, MainActivity.class));
                    }
                    else{
                        Toast.makeText(ResgisterActivity.this, getString(R.string.usuarioNR) +task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}