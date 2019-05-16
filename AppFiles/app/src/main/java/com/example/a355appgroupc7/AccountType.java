package com.example.a355appgroupc7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AccountType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_type);

        //Intent to get data from last activity
        Intent getData = getIntent();
        final String user_name=getData.getStringExtra("USER"); //"USER" matches "USER" from last intent
        final String e_mail=getData.getStringExtra("MAIL");
        final String pass_word=getData.getStringExtra("PASS");
        final String pass_hash="NOTYET";

        //Buttons
        Button athlete = findViewById(R.id.AthleteButton);
        Button coach = findViewById(R.id.CoachButton);

        //Athlete click listener
        athlete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Users newUser = new Users(e_mail,user_name,pass_word,pass_hash,"0");
                //set connection
                APIConnection conn = new APIConnection();
                //POST to database
                conn.signupUser(getApplicationContext(), newUser, new APIConnection.ResponseHandler() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(),"Registered! Welcome!",Toast.LENGTH_SHORT).show();
                        CurrentUser.getInstance().setUsername(newUser.getUsername());
                        startActivity(new Intent(getApplicationContext(), AthleteHomepage.class));
                        finish();


                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(getApplicationContext(),"Big oof. Sign Up Failure.",Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        //Coach click listener
        coach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Users newUser = new Users(e_mail,user_name,pass_word,pass_hash,"1");
                APIConnection conn = new APIConnection();
                conn.signupUser(getApplicationContext(), newUser, new APIConnection.ResponseHandler() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(),"Registered! Welcome!",Toast.LENGTH_SHORT).show();
                        Intent onCoachselect = new Intent(getApplicationContext(), CoachHomepage.class);
                        CurrentUser.getInstance().setUsername(newUser.getUsername());
                        startActivity(onCoachselect);
                        finish();
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(getApplicationContext(),"Big oof. Sign Up Failure.",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}



