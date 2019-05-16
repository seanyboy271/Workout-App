package com.example.a355appgroupc7;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Struct;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Button saveChanges = findViewById(R.id.saveChanges);
        final EditText bio = findViewById(R.id.profileBioEdit);
        final String userId = CurrentUser.getInstance().getUserId().toString();
        final APIConnection conn = new APIConnection();


        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bio.getText().toString().trim().isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "Bio is Empty", Toast.LENGTH_SHORT).show();
                } else {
                    conn.editBio(getApplicationContext(), userId, bio.getText().toString(), new APIConnection.ResponseHandler() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(getApplicationContext(), "Bio Changed", Toast.LENGTH_SHORT).show();
                                    CurrentUser.getInstance().setBio(bio.getText().toString());
                                    startActivity(new Intent(getApplicationContext(), AthleteHomepage.class));

                                }

                                @Override
                                public void onFailure() {
                                    Toast.makeText(getApplicationContext(), "Big oof. Bio Change Failure.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}
