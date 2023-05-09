package com.example.dip_project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

import com.example.dip_project.R;

public class ModSelection extends AppCompatActivity {

    CardView dictionary;
    CardView exam;
    CardView tickets;
    CardView statistic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_selection);
        dictionary = findViewById(R.id.dictionary);
        exam = findViewById(R.id.exam);
        tickets = findViewById(R.id.tickets);
        statistic = findViewById(R.id.statistic);
        Intent goToDictionary = new Intent(this, ChooseDictionaryTheam.class);
        Intent goToExam = new Intent(this, TestingActivity.class);
        Intent goToTickets = new Intent(this, TicketChoice.class);
        Intent goToStatistic = new Intent(this, StatisticActivity.class);
        dictionary.setOnClickListener(view -> startActivity(goToDictionary));
        exam.setOnClickListener(view -> startActivity(goToExam));
        tickets.setOnClickListener(view -> startActivity(goToTickets));
        statistic.setOnClickListener(view -> startActivity(goToStatistic));
    }
}