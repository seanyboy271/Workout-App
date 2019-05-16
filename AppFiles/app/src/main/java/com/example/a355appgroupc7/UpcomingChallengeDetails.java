package com.example.a355appgroupc7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UpcomingChallengeDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_challenge_details);

        TextView challengeName = findViewById(R.id.ChallengeDetailNameUpcoming);
        TextView challengeCreator = findViewById(R.id.CoachNameUpcoming);
        TextView challengeStart = findViewById(R.id.startDateUpcoming);
        TextView challengeDescription = findViewById(R.id.descriptionUpcoming);
        TextView challengeEnd = findViewById(R.id.endDateUpcoming);


        final Challenges challenge = (Challenges) getIntent().getSerializableExtra("CHALLENGE");

        challengeName.setText(challenge.getChallengeName());
        challengeCreator.setText("Coach: " + challenge.getCoach());
        challengeStart.setText(challenge.getStart());
        challengeDescription.setText(challenge.getDesc());
        challengeEnd.setText(challenge.getEnd());
    }
}
