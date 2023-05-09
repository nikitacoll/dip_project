package com.example.dip_project.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dip_project.Activity.Authorization;
import com.example.dip_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    Button goToLogin;
    Button regAccount;
    EditText userMail;
    EditText userPassword;
    EditText userConfirmedPassword;
    private FirebaseAuth mAuth;
    FirebaseFirestore database;

    public static final Pattern VALID_EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        goToLogin = findViewById(R.id.go_to_sign_in);
        regAccount = findViewById(R.id.registrated);
        userMail = findViewById(R.id.login);
        userPassword = findViewById(R.id.password);
        userConfirmedPassword = findViewById(R.id.repeated_password);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        Map<String,String> userdata = new HashMap<>();
        Map<String,String> entryCountStatistic = new HashMap<>();
        entryCountStatistic.put("title","Статистика собирается на основе подсчёта количества входов в приложение в конкретную дату");
        Map<String,String> examAverageScore = new HashMap<>();
        examAverageScore.put("title","Статистика собирается на основе подсчета баллов за сдачу экзамена и подсчётом среднего балла за 3 дня");
        Map<String,String> examSuccessPercentage = new HashMap<>();
        examSuccessPercentage.put("title","Статистика собирается на основе подсчета успешно сданных экзаменнов относительно всех попыток сдачи экзаменов");
        examSuccessPercentage.put("ExamsCount","0");
        examSuccessPercentage.put("ExamsPassedCount","0");
        examSuccessPercentage.put("ExamsPercent","0");
        Map<String,String> ticketStatistic = new HashMap<>();
        ticketStatistic.put("title","Статистика собирается на основе первой удачный попытки прохождения билета, записываеться информация о дате и времени и набранных баллах за билет");
        for(int i = 0;i < 10;i++){
            userdata.put("ticket"+(i+1),"не пройден");
            ticketStatistic.put("ticket"+(i+1),"не пройден");
        }

        Intent goToLog = new Intent(this, Authorization.class);

        regAccount.setOnClickListener(view -> {
            if(!TextUtils.isEmpty(userMail.getText().toString()) && !TextUtils.isEmpty(userPassword.getText().toString()) && !TextUtils.isEmpty(userConfirmedPassword.getText().toString())) {
                if(validate(userMail.getText().toString()) == true){
                    if (userPassword.getText().toString().equals(userConfirmedPassword.getText().toString())) {
                        mAuth.createUserWithEmailAndPassword(userMail.getText().toString().trim(),userPassword.getText().toString().trim()).addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Вы зарегистрировались",Toast.LENGTH_LONG).show();
                                goToLog.putExtra("userMail",userMail.getText().toString());
                                goToLog.putExtra("userPass",userPassword.getText().toString());
                                startActivity(goToLog);
                                database.collection("PddData")
                                        .document("ticketStatisik")
                                        .collection("Email")
                                        .document(userMail.getText().toString().trim()).set(userdata);
                                database.collection("PddData")
                                        .document("entryCountStatistic")
                                        .collection("Email")
                                        .document(userMail.getText().toString().trim())
                                        .set(entryCountStatistic);
                                database.collection("PddData")
                                        .document("examAverageScore")
                                        .collection("Email")
                                        .document(userMail.getText().toString().trim())
                                        .set(examAverageScore);
                                database.collection("PddData")
                                        .document("examSuccessPercentage")
                                        .collection("Email")
                                        .document(userMail.getText().toString().trim())
                                        .set(examSuccessPercentage);
                                database.collection("PddData")
                                        .document("ticketStatistic")
                                        .collection("Email")
                                        .document(userMail.getText().toString().trim())
                                        .set(ticketStatistic);

                            }
                            else{
                                try{
                                    throw task.getException();
                                }catch (FirebaseAuthWeakPasswordException e){
                                    Toast.makeText(getApplicationContext(),"пароль должен быть длиннее 6 символов",Toast.LENGTH_LONG).show();
                                }catch (FirebaseAuthUserCollisionException e){
                                    Toast.makeText(getApplicationContext(),"пользователь с такой почтой уже существует",Toast.LENGTH_LONG).show();
                                }catch (Exception e){
                                    Toast.makeText(getApplicationContext(),"непредвиденная ошибка",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                    else{
                        Toast.makeText(this,"Пароли не совпадают",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"неверный формат почты",Toast.LENGTH_LONG).show();
                }

            }
            else{
                Toast.makeText(this,"Заполните поля",Toast.LENGTH_LONG).show();
            }
        });

        goToLogin.setOnClickListener(view -> startActivity(goToLog));
    }
    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_REGEX.matcher(emailStr);
        return matcher.find();
    }
}