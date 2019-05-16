package com.example.a355appgroupc7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class ForgotInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_information);

        Button sendEmail = findViewById(R.id.SendEmailButton);
        final EditText emailBox = findViewById(R.id.ForgotInfoEmail);

        sendEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (emailBox.getText().toString().trim().isEmpty()){
                    Toast.makeText(ForgotInformation.this,"Please fill in the box with a valid email",Toast.LENGTH_SHORT).show();
                }


                    APIConnection conn = new APIConnection();

                    //Make sure email is in database, make toast if not
                    conn.checkEmailCredential(getApplicationContext(), emailBox.getText().toString().trim(), new APIConnection.ResponseHandler() {

                        @Override
                        public void onSuccess() {
                            Toast.makeText(ForgotInformation.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure() {
                            String email = emailBox.getText().toString().trim();
                            String newPass = createPass();
                            String username = "";

                            sendEmail(email, newPass, username);

                            Toast.makeText(getApplicationContext(), "Recovery Email Sent", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(intent);
                            finish();
                            //make toast email sent, go back to login
                        }
                    });

            }
        });

    }

    private static String createPass() {

        char letters[] = new char[10];
        Random randomNum = new Random();

        for (int i = 0; i < 10; i++) {
            letters[i] = (char) (randomNum.nextInt(93) + 33);
        }

        String pass = new String(letters);
        return pass;

    }

    public static void sendEmail(final String email, String password, String username) {
        Log.e("Email", email);
        final String subject = "Workouts password reset";
        final String body = "Hi, " + username + "! Your password has been reset, please use this new one to sign in " + password;
        final String senderMail = "workoutsappteam@gmail.com";
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        GMailSender sender = new GMailSender("workoutsappteam@gmail.com", "Thisclasssucks420");
                        sender.sendMail(subject, body, senderMail, email);
                    }
                catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
                }
            }).start();
    }

}
