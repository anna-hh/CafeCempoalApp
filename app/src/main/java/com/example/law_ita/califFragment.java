package com.example.law_ita;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link califFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class califFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String name, lastname, comentario;
    float rate;
    private View ratingView;
    private Button califBtn;
    private RatingBar rating;
    EditText texto;

    private RecyclerView rcvCalif;
    rateAdapter adapter;
    String userId;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    public califFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static califFragment newInstance(String param1, String param2) {
        califFragment fragment = new califFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ratingView = inflater.inflate(R.layout.fragment_calif, container, false);
        califBtn = (Button)ratingView.findViewById(R.id.califBtn);
        rating = (RatingBar) ratingView.findViewById(R.id.ratingBar);
        texto = (EditText) ratingView.findViewById(R.id.comentario);
        rcvCalif = (RecyclerView) ratingView.findViewById(R.id.rcvCalif);
        rcvCalif.setLayoutManager(new LinearLayoutManager(getContext()));

        FirestoreRecyclerOptions<rating> options =
                new FirestoreRecyclerOptions.Builder<rating>()
                        .setQuery(FirebaseFirestore.getInstance().collection("rating")
                                , rating.class).build();

        adapter = new rateAdapter(options);
        rcvCalif.setAdapter(adapter);

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate = rating;
            }
        });

        califBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comentario = texto.getText().toString();

                mAuth = FirebaseAuth.getInstance();
                db = FirebaseFirestore.getInstance();
                userId = mAuth.getCurrentUser().getUid();

                DocumentReference documentReference = db.collection("users").document(userId);
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.getResult().exists()){
                            lastname = task.getResult().getString("Lastname");
                            name = task.getResult().getString("Name");

                            DocumentReference documentReference = db.collection("rating").document(userId);
                            Map<String, Object> calif=new HashMap<>();
                            calif.put("Name", name);
                            calif.put("Lastname", lastname);
                            calif.put("Rate", rate);
                            calif.put("Comment", comentario);

                            documentReference.set(calif).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("TAG", getString(R.string.c_r) + userId);
                                }
                            });
                            Toast.makeText(getContext(), "Opinión guardada", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return ratingView;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        //FirestoreRecyclerOptions<Chat> options = new FirestoreRecyclerOptions.Builder<Chat>()
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.startListening();
        //FirestoreRecyclerOptions<Chat> options = new FirestoreRecyclerOptions.Builder<Chat>()
    }
}