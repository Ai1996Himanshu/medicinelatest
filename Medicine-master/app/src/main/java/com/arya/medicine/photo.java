package com.arya.medicine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class photo extends AppCompatActivity {

    Button sellBtn;
    RecyclerView items;
    List<med_detail> medList = new ArrayList<>();
    MedAdapter medAdapter;
    ImageButton buttonLogout1;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_photo);

        buttonLogout1=findViewById(R.id.buttonLogout1);
        sellBtn = findViewById(R.id.sell_btn);
        items = findViewById(R.id.item_rv);
        medAdapter = new MedAdapter(medList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        items.setLayoutManager(layoutManager);
        items.setAdapter(medAdapter);
        firebaseAuth=FirebaseAuth.getInstance();
        buttonLogout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(photo.this,loginActivity.class));

            }
        });

        sellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(photo.this, upload.class));
                finish();
            }
        });

        prepareData();

        items.addOnItemTouchListener(
                new RecyclerItemClickListener(this, items ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Intent intent = new Intent(photo.this, full_details.class);
                        intent.putExtra("obj", medList.get(position));
                        Log.d("nuclode", medList.get(position).getBillUrl());
                        Log.d("nuclode", medList.get(position).getMedUrl());
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    private void prepareData() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("med_details");
        ValueEventListener medListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("nuclode:", dataSnapshot.toString());
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    med_detail med = singleSnapshot.getValue(med_detail.class);
                    medList.add(med);

                }

                medAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        db.addValueEventListener(medListener);
    }
}
