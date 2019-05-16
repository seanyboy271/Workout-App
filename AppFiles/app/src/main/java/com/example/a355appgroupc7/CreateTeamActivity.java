package com.example.a355appgroupc7;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class CreateTeamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);

        Button create = findViewById(R.id.createTeamCreateButton);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText teamNameView = findViewById(R.id.teamNameInput);
                final String teamName = teamNameView.getText().toString();
                EditText teamSizeView = findViewById(R.id.teamSizeInput);
                final String teamSize = teamSizeView.getText().toString();
                //Error checking (empty fields and non-integer for teamSize
                if (teamName.isEmpty() || teamSize.isEmpty()) {
                    teamNameView.setError("Team name required.");
                    teamSizeView.setError("Team size required.");
                }
                else
                {


                    final Challenges challenge = (Challenges) getIntent().getSerializableExtra("CHALLENGE"); //This is the challenge
                    final Team newTeam = new Team(teamName, CurrentUser.getInstance().getUserId(), teamSize);
                    newTeam.setChallengeId(Integer.toString(challenge.getId()));


                    final APIConnection conn = new APIConnection();
                    //POST to database
                    conn.createTeam(getApplicationContext(), newTeam, new APIConnection.ResponseHandlerCreateTeam() {
                        @Override
                        public void onSuccess(Team team) {
                            conn.joinTeam(getApplicationContext(), team, challenge, new APIConnection.ResponseHandler() {
                                @Override
                                public void onSuccess() {

                                    Intent intent = new Intent(getApplicationContext(), AlarmReceiverDailyNotif.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    AlarmManager manager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                                    try {
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTimeInMillis(System.currentTimeMillis());
                                        calendar.set(Calendar.HOUR_OF_DAY, 20);//This is where we put in the hour of the time we want the notification to go off
                                        calendar.set(Calendar.MINUTE, 00); //Minute of the time
                                        // if notification time is before selected time, send notification the next day
                                        if (calendar.before(Calendar.getInstance())) {
                                            //calendar.add(Calendar.DATE, 1);
                                        }
                                        if (manager != null) {
                                            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                                        }
                                    }
                                    catch (Exception e){
                                        e.printStackTrace();
                                    }

                                    Intent challengeStart = new Intent(getApplicationContext(), AlarmReceiverChallengeStart.class);
                                    //challengeStart.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    PendingIntent challengeStartPending = PendingIntent.getBroadcast(getApplicationContext(), 1, challengeStart, PendingIntent.FLAG_CANCEL_CURRENT);
                                    String start = challenge.getStart();
                                    Log.i("Start", start);
                                    int year = Integer.parseInt(start.substring(0, 4));
                                    int month = Integer.parseInt(start.substring(5, 7));
                                    int day = Integer.parseInt(start.substring(8, 10));
                                    int hour = Integer.parseInt(start.substring(11, 13));
                                    int minute = Integer.parseInt(start.substring(14 , 16));
                                    Log.i("Year  + month + day + hour + minute", Integer.toString(year) + Integer.toString(month) + Integer.toString(day) + Integer.toString(hour) + Integer.toString(minute));
                                    Calendar endTime = Calendar.getInstance();
                                    endTime.set(Calendar.YEAR, year);
                                    endTime.set(Calendar.MONTH, month - 1);
                                    endTime.set(Calendar.DAY_OF_MONTH, day);
                                    endTime.set(Calendar.HOUR_OF_DAY, hour);
                                    endTime.set(Calendar.MINUTE, minute);
                                    manager.setExact(AlarmManager.RTC_WAKEUP, endTime.getTimeInMillis(), challengeStartPending);

                                    Toast.makeText(getApplicationContext(), "Team created!", Toast.LENGTH_SHORT).show();
                                    Intent intent2 = new Intent(getApplicationContext(), AthleteHomepage.class);
                                    intent2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                    Log.i("Successfully Joined Team", "  ");
                                    startActivity(intent2);
                                    finish();

                                }

                                @Override
                                public void onFailure() {
                                    Log.e("COULD NOT JOIN TEAM FOR SOME REASON", "  ");
                                }
                            });
                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(getApplicationContext(), "Team Creation failed!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.i("Team Id Before joinTeam", Integer.toString(newTeam.getTeamId()));




                }
            }
        });
    }

}

