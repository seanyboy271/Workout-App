package com.example.a355appgroupc7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HistoryDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_details);

        TextView challengeName = findViewById(R.id.ChallengeDetailNameHistory);
        TextView challengeCreator = findViewById(R.id.CoachNameHistory);
        TextView challengeStart = findViewById(R.id.startDateHistory);
        TextView challengeEnd = findViewById(R.id.endDateHistory);
        TextView challengeDescription = findViewById(R.id.descriptionHistory);
        //TextView teamName = findViewById(R.id.historyTeamName);
        TextView result = findViewById(R.id.historyResult);
        TextView logdate = findViewById(R.id.historyLogDate);



        final LogObject logObject = (LogObject) getIntent().getSerializableExtra("CHALLENGE");

        challengeName.setText(logObject.getChallengeName());
        challengeCreator.setText("Coach: " + logObject.getCoach());
        challengeStart.setText(logObject.getChallengeStart());
        challengeDescription.setText(logObject.getDescrip());
        challengeEnd.setText(logObject.getChallengeEnd());
        //teamName.setText(logObject.getTeamName());
        result.setText(logObject.getResult());
        logdate.setText(logObject.getLogDate());


    }



}
