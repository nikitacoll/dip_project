package com.example.dip_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dip_project.Class.UserData;
import com.example.dip_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class Authorization extends AppCompatActivity {

    Button goToRegistration;
    Button logging;
    EditText userLogin;
    EditText userPassword;
    FirebaseFirestore database;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        goToRegistration = findViewById(R.id.registration);
        logging = findViewById(R.id.entry);
        userLogin = findViewById(R.id.user_login);
        userPassword = findViewById(R.id.user_password);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();


        onStart();

        Intent goToReg = new Intent(this, Registration.class);
        goToRegistration.setOnClickListener(view -> startActivity(goToReg));

        Intent loggingInAccount = new Intent(this, ModSelection.class);
        logging.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(userLogin.getText().toString()) && !TextUtils.isEmpty(userPassword.getText().toString())) {
                mAuth.signInWithEmailAndPassword(userLogin.getText().toString().replaceAll(" ",""), userPassword.getText().toString()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Вы вошли", Toast.LENGTH_LONG).show();
                        loggingInAccount.putExtra("Email",userLogin.getText().toString().trim());
                        startActivity(loggingInAccount);
          //              updateUserEntries();
                    } else {
                        try {
                            Log.d("Firebase",task.getException().toString());
                            throw task.getException();
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            Toast.makeText(getApplicationContext(),"неверное имя пользователя или пароль" , Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),"Непредвиденная ошибка" , Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            else{
                Toast.makeText(getApplicationContext(),"Заполните поля",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Toast.makeText(this, "user not null", Toast.LENGTH_LONG);
        } else {
            Toast.makeText(this, "user is null", Toast.LENGTH_LONG);
        }
    }
//    public void updateUserEntries(){
//        database.collection("PddData")
//                .document("entryCountStatistic")
//                .collection("Email")
//                .document(userLogin.getText().toString().trim())
//                .get().addOnCompleteListener(task1 -> {
//                    String entryCount = task1.getResult().get("userEntryCount").toString();
//                    entryCount = String.valueOf(Integer.parseInt(entryCount)+1);
//                    Map<String,String> data = new HashMap<>();
//                    data.put("userEntryCount",entryCount);
//                    database.collection("PddData")
//                            .document("userPersonalStatistick")
//                            .collection("Email")
//                            .document(userLogin.getText().toString().trim())
//                            .set(data, SetOptions.merge());
//                });
//    }

}