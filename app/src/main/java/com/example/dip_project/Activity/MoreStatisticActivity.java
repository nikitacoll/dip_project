package com.example.dip_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaCodec;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.dip_project.Adapter.StatisticMoreAdapter;
import com.example.dip_project.Adapter.StatisticMoreAdapter2;
import com.example.dip_project.Class.UserMoreStatistic;
import com.example.dip_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MoreStatisticActivity extends AppCompatActivity {

    TextView title;
    TextView description;
    FirebaseAuth email;
    FirebaseFirestore database;
    RecyclerView rv;
    String[] date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_statistic);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        email = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        rv = findViewById(R.id.statistic_more_recycler);
        ArrayList<String> statistic1 = new ArrayList<>();
        ArrayList<UserMoreStatistic> statistic2 = new ArrayList<>();
         date = new String[1];
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
        if(statisticName.equals("ticketStatistic")){
            for(int i = 1; i < 11;i++){
                int[] pos = new int[1];
                pos[0] = i;
                database.collection("PddData")
                        .document("ticketStatistic")
                        .collection("Email")
                        .document(email.getCurrentUser().getEmail())
                        .get().addOnCompleteListener(task1 -> {
                            statistic1.add("билет " + (pos[0])+" " + task1.getResult()
                                    .get("ticket"+(pos[0])).toString());
                            Log.d("statit",task1.getResult()
                                    .get("ticket"+(pos[0])).toString());
                            Log.d("statit",statistic1.toString());
                            StatisticMoreAdapter adapter = new StatisticMoreAdapter(statistic1);
                            rv.setAdapter(adapter);
                        });
            }
        }
        if(statisticName.equals("examSuccessPercentage")){
            database.collection("PddData")
                    .document("userRegistrationdate")
                    .collection("Email")
                    .document(email.getCurrentUser().getEmail())
                    .get().addOnCompleteListener(task -> {
                        date[0] = task.getResult().get("registrationdate").toString();
                        for(int i = 0;i < 365;i++){
                            database.collection("PddData")
                                    .document("examSuccessPercentage")
                                    .collection("Email")
                                    .document(email.getCurrentUser().getEmail())
                                    .get().addOnCompleteListener(task1 -> {
                                        try {
                                            statistic2.add(new UserMoreStatistic(
                                                    "Средний балл экзамена на "+date[0] + " - ",task1.getResult()
                                                    .get(FieldPath.of(date[0].trim())).toString()));
                                            date[0] = datePlusOne(date[0]);
                                        }catch (Exception ex){
                                            date[0] = datePlusOne(date[0]);
                                        }
                                        StatisticMoreAdapter2 adapter = new StatisticMoreAdapter2(statistic2);
                                        rv.setAdapter(adapter);
                                    });
//
                        }
                    });
            //task.getResult().get(FieldPath.of("01.05.2023")
//            for(int i = 1; i < 11;i++){
//                int[] pos = new int[1];
//                pos[0] = i;
//                database.collection("PddData")
//                        .document("userRegistrationdate")
//                        .collection("Email")
//                        .document(email.getCurrentUser().getEmail())
//                        .get().addOnCompleteListener(task1 -> {
//                            statistic.add("билет " + (pos[0])+" " + task1.getResult()
//                                    .get("ticket"+(pos[0])).toString());
//                            Log.d("statit",task1.getResult()
//                                    .get("ticket"+(pos[0])).toString());
//                            Log.d("statit",statistic.toString());
//                            StatisticMoreAdapter adapter = new StatisticMoreAdapter(statistic);
//                            rv.setAdapter(adapter);
//                        });
            }
        }


    public static String datePlusOne(String date){
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
        Date date1 = new Date();
        try {
            date1 = ft.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.DATE,1);
            date1 = calendar.getTime();


        }catch (ParseException pe){
            System.out.println(pe);
        }
        return ft.format(date1);
    }


    }
