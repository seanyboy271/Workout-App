package com.example.a355appgroupc7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        sp = getSharedPreferences("login", MODE_PRIVATE);
        Log.i("sp value", Boolean.toString(sp.getBoolean("loggedIn", false)));
        if(sp.getBoolean("loggedIn", false)){
            try {
                if (CurrentUser.getInstance().getIsCoach().equals("1")) {
                    startActivity(new Intent(getApplicationContext(), CoachHomepage.class));
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), AthleteHomepage.class));
                    finish();
                }
            }
            catch (NullPointerException e){
                e.printStackTrace();
                //Its def a bad idea to store a username and password this way but i will fix it later
                Log.i("Stored username and password", sp.getString("Username", null) + " " + sp.getString("Password", null));
                Login(sp.getString("Username", null), sp.getString("Password", null));
                Toast.makeText(getApplicationContext(), "Welcome back " + sp.getString("Username", null) + "! Please wait while we log you back in", Toast.LENGTH_LONG).show();

            }
        }


        //button + EditText
        Button logIn = findViewById(R.id.LoginButton);
        final EditText username = findViewById(R.id.Username);
        final EditText password = findViewById(R.id.Password);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                //Check empty fields
                if (!user.isEmpty() || !pass.isEmpty()){
                    CurrentUser.getInstance().resetUser();
                    Login(user,pass);
                }
                else{
                    username.setError("Please Enter Username");
                    password.setError("Please Enter Password");
                }
            }
        });

    }



    public void Login(final String username, final String password){
        APIConnection conn = new APIConnection();

        conn.loginUser(getApplicationContext(), username, password, new APIConnection.ResponseHandler() {
            @Override
            public void onSuccess() {
                //Choosing which homepage to go to
                if (CurrentUser.getInstance().getIsCoach().equals("1")) {
                    startActivity(new Intent(getApplicationContext(), CoachHomepage.class));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            sp.edit().putBoolean("loggedIn", true).apply();
                            sp.edit().putString("Username", username).apply();
                            sp.edit().putString("Password", password).apply();
                        }
                    }).start();
                    finish();
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(getApplicationContext(), AthleteHomepage.class));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            sp.edit().putBoolean("loggedIn", true).apply();
                            sp.edit().putString("Username", username).apply();
                            sp.edit().putString("Password", password).apply();
                        }
                    }).start();
                    finish();
                    Log.i("Current user id after login", "  " + CurrentUser.getInstance().getUserId());
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(getApplicationContext(), "Invalid login information. Try again", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void signUpButton(View view){
        Intent toSignUp = new Intent(this, SignUpPage.class);
        startActivity(toSignUp);

    }

    public void forgotPassword(View view){
        Intent forgotInfo = new Intent(this, ForgotInformation.class);
        startActivity(forgotInfo);

    }





}
