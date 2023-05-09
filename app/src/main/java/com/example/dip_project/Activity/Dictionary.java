package com.example.dip_project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.dip_project.Class.DictionaryThem;
import com.example.dip_project.Adapter.DictionaryThemAdapter;
import com.example.dip_project.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Dictionary extends AppCompatActivity {

    FirebaseFirestore database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        ArrayList<DictionaryThem> dictionaryThems = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.dictionary_recycler);
        String themName = getIntent().getStringExtra("themName");
        final int[] theamSize = {0};
        database = FirebaseFirestore.getInstance();
        database.collection("PddData")
                .document("RulesGuide")
                .collection("theam"+themName).get().addOnCompleteListener(task1 -> {
                    if(task1.isSuccessful()){
                        theamSize[0] = task1.getResult().size();
                        Log.d("conter",String.valueOf(theamSize[0]));
                        for(int i = 0; i < theamSize[0];i++){
                            database = FirebaseFirestore.getInstance();
                            database.collection("PddData")
                                    .document("RulesGuide")
                                    .collection("theam"+themName)
                                    .document(String.valueOf(i+1))
                                    .get()
                                    .addOnCompleteListener(task2 -> {

                                        if(task2.isSuccessful()){
                                            dictionaryThems.add(new DictionaryThem(
                                                    task2.getResult().get("URL").toString().trim(),
                                                    task2.getResult().get("text").toString()));
                                            DictionaryThemAdapter adapter = new DictionaryThemAdapter(getApplicationContext(),dictionaryThems);
                                            recyclerView.setAdapter(adapter);
                                        }
                                    });
                        }

                    }
                });




    }
}