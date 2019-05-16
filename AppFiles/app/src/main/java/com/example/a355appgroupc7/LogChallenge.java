package com.example.a355appgroupc7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogChallenge extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_challenge);

        TextView challengeName = findViewById(R.id.ChallengeDetailNameLog);
        TextView challengeCreator = findViewById(R.id.CoachNameLog);
        TextView challengeStart = findViewById(R.id.startDateLog);
        TextView challengeDescription = findViewById(R.id.descriptionLog);
        TextView challengeEnd = findViewById(R.id.endDateLog);


        final Challenges challenge = (Challenges) getIntent().getSerializableExtra("CHALLENGE");

        challengeName.setText(challenge.getChallengeName());
        challengeCreator.setText("Coach: " + challenge.getCoach());
        challengeStart.setText(challenge.getStart());
        challengeDescription.setText(challenge.getDesc());
        challengeEnd.setText(challenge.getEnd());

        Button Log = findViewById(R.id.buttonLog);
        Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText result = findViewById(R.id.resultLog);
                if (result.getText().toString().isEmpty()){
                    result.setError("Please Enter Result");
                }else{
                    final APIConnection conn = new APIConnection();
                    LogObject logresult = new LogObject(CurrentUser.getInstance().getUserId(),Integer.toString(challenge.getId()),result.getText().toString());
                    android.util.Log.i("LogObject OBJ" , logresult.getUserId()+"-"+logresult.getChallengeId()+"-"+logresult.getResult());

                    conn.logResult(getApplicationContext(),logresult, new APIConnection.ResponseHandler() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(getApplicationContext(),"Result Logged!",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), AthleteHomepage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(intent);
                            finish();
                        }
                        @Override
                        public void onFailure() {
                            Toast.makeText(getApplicationContext(),"Big oof. Log Failure.",Toast.LENGTH_SHORT).show();
                        }

                    });
                }
            }
        });
    }
}
