package com.example.a355appgroupc7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

public class SignUpPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Button + EditText
        Button signUp = findViewById(R.id.SignUpPageSignUpButton);
        final EditText username = findViewById(R.id.signUpPageUsername);
        final EditText password = findViewById(R.id.SignUpPagePassword);
        final EditText email = findViewById(R.id.signUpPageEmail);

        //Click listener
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make sure no fields are empty
                if (username.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty() || email.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignUpPage.this,"All fields are required",Toast.LENGTH_SHORT).show();
                }

                //Check for spaces in username
                else if (username.getText().toString().trim().contains(" ")){
                    username.setError("Username may not contain any spaces");
                }

                else if(!email.getText().toString().contains("@")){
                    email.setError("Invalid email");
                }

                else {
                    //Check to make sure email and username are not already taken
                    checkSignUpCredentials(username, email, password);
                }
            }
        });

    }

    /**
     * Checks if the username and email the user has input are already in use
     * If one or both of the credentials is already in use, it sets an error on the appropriate TextViews(s)
     * Otherwise it switches to the AccountType activity
     * @param username
     * @param email
     * @param password
     */
    public void checkSignUpCredentials(final EditText username, final EditText email, final EditText password){
        final APIConnection conn = new APIConnection();

        //Checking email
        conn.checkEmailCredential(getApplicationContext(), email.getText().toString(), new APIConnection.ResponseHandler() {
            @Override
            public void onSuccess() {
                //Check if username is not already taken
                conn.checkUsernameCredential(getApplicationContext(), username.getText().toString(), new APIConnection.ResponseHandler() {
                    @Override
                    public void onSuccess() {
                        //Extract the users data
                        String user = username.getText().toString().trim();
                        String pass = password.getText().toString().trim();
                        String mail = email.getText().toString().trim();

                        //Switch to accountType
                        Intent accountType = new Intent(SignUpPage.this, AccountType.class);

                        //Passing values into next activity
                        accountType.putExtra("USER",user);
                        accountType.putExtra("PASS",pass);
                        accountType.putExtra("MAIL",mail);
                        startActivity(accountType);
                        finish();
                    }

                    @Override
                    public void onFailure() {
                        username.setError("Username already in use");
                    }
                });
            }

            @Override
            public void onFailure() { // The email is already taken
                email.setError("Email already in use");
                return;
            }
        });


    }



}
