package com.example.dip_project.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dip_project.Activity.ResultActivity;
import com.example.dip_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestingActivity extends AppCompatActivity {
    FirebaseFirestore database;
    FirebaseAuth userMail;
    TextView ticketQuestion;
    RadioButton firstAns;
    RadioButton secondAns;
    RadioButton thirdAns;
    RadioButton forthAns;
    ImageView photoUrl;
    RadioGroup radioGroup;
    final String[] rightAnswer = new String[1];
    final int[] counter = {0};
    final int[] counterOfBadAnswer = {0};
    ArrayList<String> questionNumber;
    int ticketCounter;
    int questionCounter;
    ArrayList<String> ticketNumber;
    TextView timerTextView;
    int millis = 1200000;
    CountDownTimer timer = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        ticketQuestion = findViewById(R.id.question);
        firstAns = findViewById(R.id.first_ans);
        secondAns = findViewById(R.id.second_ans);
        thirdAns = findViewById(R.id.third_ans);
        forthAns = findViewById(R.id.forth_ans);
        radioGroup = findViewById(R.id.answers);
        photoUrl = findViewById(R.id.photo);
        timerTextView = findViewById(R.id.timer_text_view);
        database = FirebaseFirestore.getInstance();
        userMail = FirebaseAuth.getInstance();
        ticketNumber = new ArrayList<>();
        questionNumber = new ArrayList<>();
        ticketCounter = 0;
        questionCounter = 0;
        mTimer(millis);
        Random random = new Random();
        for(int i = 0;i < 20;i++){
            ticketNumber.add(String.valueOf(random.nextInt(10)+1));
        }
        for(int i = 0;i < 20;i++){
            questionNumber.add(String.valueOf(random.nextInt(20)+1));
        }
        Log.d("arrays",ticketNumber.toString());
        Log.d("arrays",questionNumber.toString());

        loadTicket(ticketNumber.get(ticketCounter), questionNumber.get(questionCounter));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                Thread myThread = new Thread(() ->{

                    switch (checkedId){
                        case (R.id.first_ans):
                            questionCounter++;
                            ticketCounter++;
                            if("1".equals(rightAnswer[0].trim())){
                                counter[0] = counter[0] + 1;
                            }
                            else{
                                counterOfBadAnswer[0] = counterOfBadAnswer[0] + 1;
                                runOnUiThread(()->{
                                    timer.cancel();
                                    millis = millis + 300000;
                                    mTimer(millis);
                                });
                            }
                            radioGroup.clearCheck();
                            break;
                        case (R.id.second_ans):
                            questionCounter++;
                            ticketCounter++;
                            if("2".equals(rightAnswer[0].trim())){
                                counter[0] = counter[0] + 1;
                            }
                            else{
                                counterOfBadAnswer[0] = counterOfBadAnswer[0] + 1;
                                runOnUiThread(()->{
                                    timer.cancel();
                                    millis = millis + 300000;
                                    mTimer(millis);
                                });
                            }
                            radioGroup.clearCheck();
                            break;
                        case (R.id.third_ans):
                            questionCounter++;
                            ticketCounter++;
                            if("3".equals(rightAnswer[0].trim())){
                                counter[0] = counter[0] + 1;
                            }
                            else{
                                counterOfBadAnswer[0] = counterOfBadAnswer[0] + 1;
                                runOnUiThread(()->{
                                    timer.cancel();
                                    millis = millis + 300000;
                                    mTimer(millis);
                                });
                            }
                            radioGroup.clearCheck();
                            break;
                        case (R.id.forth_ans):
                            questionCounter++;
                            ticketCounter++;
                            if("4".equals(rightAnswer[0].trim())){
                                counter[0] = counter[0] + 1;
                            }
                            else{
                                counterOfBadAnswer[0] = counterOfBadAnswer[0] + 1;
                                runOnUiThread(()->{
                                    timer.cancel();
                                    millis = millis + 300000;
                                    mTimer(millis);
                                });
                            }
                            radioGroup.clearCheck();
                            break;
                    }
                });
                myThread.start();
                try {
                    myThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(questionCounter == 2){
                    updateExamCount();
                }
                if(questionCounter > 19 || counterOfBadAnswer[0] > 2){
                    timer.onFinish();
                    Intent intent = new Intent(getBaseContext(), ResultActivity.class);
                    intent.putExtra("resultExam",String.valueOf(counter[0]));
                    startActivity(intent);

                }
                else
                    loadTicket(ticketNumber.get(ticketCounter), String.valueOf(questionCounter));
            }
        });
    }

    public void loadTicket(String ticketNumber,String questionNumber) {
        database.collection("PddData")
                .document("TestingData")
                .collection("ticket" + ticketNumber).whereEqualTo("questionNumber", questionNumber)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot snapshot : task.getResult()) {
                            ticketQuestion.setText(snapshot.getData().get("question").toString());
                            firstAns.setText(snapshot.getData().get("answer1").toString());
                            secondAns.setText(snapshot.getData().get("answer2").toString());
                            thirdAns.setText(snapshot.getData().get("answer3").toString());
                            forthAns.setText(snapshot.getData().get("answer4").toString());
                            rightAnswer[0] = snapshot.getData().get("Rightanswer").toString();
                            Log.d("counter", rightAnswer[0]);
                            if (snapshot.getData().get("photoUrl").toString().equals("")) {
                                photoUrl.setImageResource(R.drawable.without_photo);
                            } else {
                                Glide.with(getBaseContext())
                                        .load(snapshot.getData().get("photoUrl").toString())
                                        .into(photoUrl);
                            }


                        }
                    }
                    visible();
                });
    }
    public void visible(){
        if(forthAns.getText().toString().equals("-")){
            forthAns.setVisibility(View.INVISIBLE);
        }
        else {
            forthAns.setVisibility(View.VISIBLE);
        }
        if(firstAns.getText().toString().equals("-")){
            firstAns.setVisibility(View.INVISIBLE);
        }
        else {
            firstAns.setVisibility(View.VISIBLE);
        }
        if(secondAns.getText().toString().equals("-")){
            secondAns.setVisibility(View.INVISIBLE);
        }
        else {
            secondAns.setVisibility(View.VISIBLE);
        }
        if(thirdAns.getText().toString().equals("-")){
            thirdAns.setVisibility(View.INVISIBLE);
        }
        else {
            thirdAns.setVisibility(View.VISIBLE);
        }
    }

    public void mTimer(int timerTimeInMillis){
            timer = new CountDownTimer(timerTimeInMillis,1000){
                @Override
                public void onTick(long l) {
                    millis = millis - 1000;
                    int seconds = (int)(l/1000);
                    int minutes = seconds/60;
                    seconds = seconds%60;
                    timerTextView.setText(String.format(String.format("%d:%02d",minutes,seconds)));
                }

                @Override
                public void onFinish() {
                }
            }.start();


    }
    public void updateExamCount(){
        database.collection("PddData")
                .document("examSuccessPercentage")
                .collection("Email")
                .document(userMail.getCurrentUser().getEmail())
                .get().addOnCompleteListener(task1 -> {
                    String ExamCount = task1.getResult().get("ExamsCount").toString();
                    ExamCount = String.valueOf(Integer.parseInt(ExamCount)+1);
                    Map<String,String> data = new HashMap<>();
                    data.put("ExamsCount",ExamCount);
                    database.collection("PddData")
                            .document("examSuccessPercentage")
                            .collection("Email")
                            .document(userMail.getCurrentUser().getEmail())
                            .set(data, SetOptions.merge());
                });
    }
}