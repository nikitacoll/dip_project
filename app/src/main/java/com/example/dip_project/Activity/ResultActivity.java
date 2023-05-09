package com.example.dip_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.dip_project.Class.UserData;
import com.example.dip_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {
    TextView resulttv;
    TextView back;
    FirebaseAuth userdata;
    FirebaseFirestore database;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        resulttv = findViewById(R.id.result);
        back = findViewById(R.id.back);
        userdata = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        String ticketNumber = getIntent().getStringExtra("ticketNumber");
        String resExam = getIntent().getStringExtra("resultExam");
        String resTicket = getIntent().getStringExtra("result");
        email = userdata.getCurrentUser().getEmail();
        Map<String,String> userResult = new HashMap<>();
        if(getIntent().getStringExtra("resultExam") != null){
            if(Integer.parseInt(resExam) < 19){
                resulttv.setText("экзамен не пройден"+ "\n" + resExam+"/20");

            }
            else{
                resulttv.setText("экзамен пройден"+ "\n" + resExam+"/20");
                updatePassedExamCount();
            }

        }
        if(getIntent().getStringExtra("result") != null){
            if(Integer.parseInt(resTicket) < 0){
                resulttv.setText("билет не пройден"+ "\n" + resTicket+"/20");
            }
            else{
                resulttv.setText("билет пройден"+ "\n" + resTicket+"/20");
                userResult.put("ticket"+ticketNumber,"пройден");
                database.collection("PddData")
                        .document("ticketStatisik")
                        .collection("Email")
                        .document(email).set(userResult, SetOptions.merge());
                userResult.clear();
                Date date = new Date();
                String todayDate = "0"+date.getDay()+".0"+(date.getMonth()+1)+"."+(date.getYear()+1900);
                userResult.put("ticket"+ticketNumber,"пройден: "+ todayDate +" на "+ resTicket +" балл.");
                database.collection("PddData")
                        .document("ticketStatistic")
                        .collection("Email")
                        .document(email).set(userResult, SetOptions.merge());
            }

        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this,ModSelection.class);
                startActivity(intent);
            }
        });

    }

    public void updatePassedExamCount(){
        database.collection("PddData")
                .document("examSuccessPercentage")
                .collection("Email")
                .document(email)
                .get().addOnCompleteListener(task1 -> {
                    String ExamPassedCount = task1.getResult().get("ExamsPassedCount").toString();
                    Log.d("Counter",ExamPassedCount);
                    ExamPassedCount = String.valueOf(Integer.parseInt(ExamPassedCount)+1);
                    Log.d("Counter",ExamPassedCount);
                    Map<String,String> data = new HashMap<>();
                    data.put("ExamsPassedCount",ExamPassedCount);
                    Log.d("Counter",data.get("ExamsPassedCount"));
                    database.collection("PddData")
                            .document("examSuccessPercentage")
                            .collection("Email")
                            .document(email)
                            .set(data, SetOptions.merge());
                });
    }

}