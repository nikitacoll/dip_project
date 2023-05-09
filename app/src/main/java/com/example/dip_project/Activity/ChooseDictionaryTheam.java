package com.example.dip_project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.dip_project.R;
import com.example.dip_project.Adapter.TheamAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ChooseDictionaryTheam extends AppCompatActivity {

    FirebaseFirestore database;
    List<String> theamCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_dictionary_theam);
        theamCollection = new ArrayList<>();
        database = FirebaseFirestore.getInstance();
        for (int i = 0; i < 8; i++) {
            theamCollection.add("тема  " + (i+1));
        }
        RecyclerView ticketRV = findViewById(R.id.theam_recycler);
        TheamAdapter adapter = new TheamAdapter(theamCollection, theam -> {
            Intent intent = new Intent(getBaseContext(), Dictionary.class);
            intent.putExtra("themName",theam.replace("тема","").trim());
            startActivity(intent);

        });
        ticketRV.setAdapter(adapter);
    }
}