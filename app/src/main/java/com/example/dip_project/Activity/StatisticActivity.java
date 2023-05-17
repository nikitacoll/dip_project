package com.example.dip_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.dip_project.Adapter.StatisticAdapter;
import com.example.dip_project.Class.UserStatistic;
import com.example.dip_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class StatisticActivity extends AppCompatActivity {
    FirebaseAuth userMail;
    FirebaseFirestore database;
    TextView back;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistick);
        back = findViewById(R.id.back);
        database = FirebaseFirestore.getInstance();
        userMail = FirebaseAuth.getInstance();
        rv = findViewById(R.id.statistic_recycler);
        updateExamPercent();
        ArrayList<UserStatistic> statisticsList = new ArrayList<>();
        statisticsList.add(new UserStatistic("examSuccessPercentage","процент успешности экзамена"));
        statisticsList.add(new UserStatistic("ticketStatistic","статистика билетов"));
        StatisticAdapter adapter = new StatisticAdapter(statisticsList, statistic -> {
            Intent intent = new Intent(getBaseContext(), MoreStatisticActivity.class);
            intent.putExtra("statisticName",statisticsList.get(statistic).getStatisticName());
            intent.putExtra("statisticTitle",statisticsList.get(statistic).getStatisticTitle());
            Log.d("statisticName",statisticsList.get(statistic).getStatisticName());
            startActivity(intent);
        });
        rv.setAdapter(adapter);

    }

    public void updateExamPercent(){
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
        String todayDate = ft.format(date);
        database.collection("PddData")
                .document("examSuccessPercentage")
                .collection("Email")
                .document(userMail.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        HashMap<String,String> map = new HashMap<>();
                        String examsCount = task.getResult().get("ExamsCount").toString();
                        String examsPassedCount =task.getResult().get("ExamsPassedCount").toString();
                        String examPercent =
                                String.valueOf(Integer.parseInt(examsPassedCount)*100
                                        /Integer.parseInt(examsCount));

                        map.put(todayDate,examPercent);
                        database.collection("PddData")
                                .document("examSuccessPercentage")
                                .collection("Email")
                                .document(userMail.getCurrentUser().getEmail()).set(map,SetOptions.merge());
                    }
                });

    }


}