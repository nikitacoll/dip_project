package com.example.dip_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.RecoverySystem;
import android.util.Log;
import android.widget.RadioButton;

import com.example.dip_project.Adapter.TicketAdapter;
import com.example.dip_project.Class.TicketStatus;
import com.example.dip_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TicketChoice extends AppCompatActivity {
    FirebaseFirestore database;
    FirebaseAuth userEmail;
    List<TicketStatus> ticketStatuses;
    RecyclerView rv;
    RadioButton rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_choice);
        ticketStatuses = new ArrayList<>();
        database = FirebaseFirestore.getInstance();
        userEmail = FirebaseAuth.getInstance();
        rv = findViewById(R.id.ticket_recycler);
        for(int i = 0; i < 10;i++){
            int[] pos = new int[1];
            pos[0] = i;
            database.collection("PddData")
                    .document("ticketStatisik")
                    .collection("Email")
                    .document(userEmail.getCurrentUser().getEmail())
                    .get().addOnCompleteListener(task -> {
                        ticketStatuses.add(new TicketStatus("билет "+ (pos[0]+1),
                                task.getResult().get("ticket"+(pos[0]+1)).toString().trim()));
                        Log.d("status",ticketStatuses.get(pos[0])
                                .getTicketName()
                                + "   " + ticketStatuses.get(pos[0]).getStatus());
                        TicketAdapter adapter = new TicketAdapter(ticketStatuses, ticket -> {
                            Intent intent = new Intent(getBaseContext(),TicketTestingActivity.class);
                            intent.putExtra("ticketNum",ticket.replace("билет","").trim());
                            startActivity(intent);
                            Log.d("ticketnum",ticket.replace("билет","").trim());
                        });
                        rv.setAdapter(adapter);
                    });
        }
    }
}