package com.example.a355appgroupc7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateChallenge extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Create Challenge");

        final Spinner spinner = (Spinner) findViewById(R.id.ChallengeActivity);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activity_selection, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        Button moveToPart2 = findViewById(R.id.moveToPart2);



        moveToPart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView challenge_name = findViewById(R.id.ChallengeName);
                final String challengeName = challenge_name.getText().toString();

                if (challengeName.equals("")){
                    challenge_name.setError("Required Field");
                }

                TextView challenge_desc = findViewById(R.id.ChallengeDesc);
                final String description = challenge_desc.getText().toString();
                if (description.equals("")){
                    challenge_desc.setError("Required Field");
                }

                TextView failCond = findViewById(R.id.FailCondition);
                final String failCondition = failCond.getText().toString();

                if (failCondition.trim().equals("")){
                    failCond.setError("Required Field");
                    return;
                }

                final String activity = spinner.getSelectedItem().toString();

                Intent cont = new Intent(getApplicationContext(), CreateChallengePt2.class);
                cont.putExtra("DESC", description);
                cont.putExtra("NAME", challengeName);
                cont.putExtra("FAIL", failCondition);
                cont.putExtra("ACTIVITY", activity);
                startActivity(cont);
                finish();
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        EditText min = findViewById(R.id.FailCondition);
        String item = parent.getItemAtPosition(position).toString();

        if (item.equals("Reps")){
            min.setHint("Minimum Reps");
            min.setVisibility(View.VISIBLE);
        }
        else if(item.equals("Time")){
            min.setHint("Minimum Time (in minutes)");
            min.setVisibility(View.VISIBLE);
        }
        else if (item.equals("Weight")) {
            min.setHint("Minimum Weight (in pounds)");
            min.setVisibility(View.VISIBLE);
        }
        else if (item.equals("Distance")){
            min.setHint("Minimum Distance (in miles)");
            min.setVisibility(View.VISIBLE);
        }
        else{
            min.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}