package com.example.dip_project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaCodec;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.dip_project.Adapter.StatisticMoreAdapter;
import com.example.dip_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MoreStatisticActivity extends AppCompatActivity {

    TextView title;
    TextView description;
    FirebaseAuth email;
    FirebaseFirestore database;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_statistic);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        email = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        rv = findViewById(R.id.statistic_more_recycler);
        ArrayList<String> statistic = new ArrayList<>();
        String statisticName = getIntent().getStringExtra("statisticName").trim();
        title.setText(getIntent().getStringExtra("statisticTitle"));
        database.collection("PddData")
                .document(statisticName)
                .collection("Email")
                .document(email.getCurrentUser().getEmail())
                .get().addOnCompleteListener(task1 -> {
                    description.setText(task1.getResult().get("title").toString());
                });
        Log.d("statit",statisticName);
        if(statisticName.equals("ticketStatistic"))
            for(int i = 1; i < 11;i++){
                int[] pos = new int[1];
                pos[0] = i;
                database.collection("PddData")
                        .document("ticketStatistic")
                        .collection("Email")
                        .document(email.getCurrentUser().getEmail())
                        .get().addOnCompleteListener(task1 -> {
                            statistic.add("билет " + (pos[0])+" " + task1.getResult()
                                    .get("ticket"+(pos[0])).toString());
                            Log.d("statit",task1.getResult()
                                    .get("ticket"+(pos[0])).toString());
                            Log.d("statit",statistic.toString());
                            StatisticMoreAdapter adapter = new StatisticMoreAdapter(statistic);
                            rv.setAdapter(adapter);
                        });
            }



    }
}