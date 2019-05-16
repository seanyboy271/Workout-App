package com.example.a355appgroupc7;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JoinTeam extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_team);



        final ListView listView = findViewById(R.id.JoinTeamListView);
        APIConnection conn = new APIConnection();
        //Need to search for teams based on challenge ID, and return a list of teams for the given challenge
        final Challenges challenges = (Challenges) getIntent().getSerializableExtra("CHALLENGE");

        Log.i("Challenge Activity", challenges.getActivity());

        // TODO Need to get the textviews and set the text in them to the corresponding team info + add the php method to handle getting the teams by challenge id

        conn.getTeamsByChallengeId(getApplicationContext(), challenges.getId(), new APIConnection.ResponseHandlerGetTeam() {
            @Override
            public void onSuccess(List<Team> response) {
                JoinTeamListAdapter adapter = new JoinTeamListAdapter();
                adapter.teams = response;
                Log.i("getTeamsByChallengeId response", response.toString());
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure() {

            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                //This is what will happen when you click a thing in the list
                AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(view.getContext());

                builder.setMessage("Join Team?");

                final Team selectedTeam = ((Team) parent.getAdapter().getItem(position));

                builder.setPositiveButton("Join", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked Join button
                        APIConnection conn = new APIConnection();
                        Log.i("Selected Team info rgiht before the thing", Integer.toString(selectedTeam.getTeamId()));
                        conn.joinTeam(view.getContext(), selectedTeam, challenges, new APIConnection.ResponseHandler() {
                            @Override
                            public void onSuccess() {
                                //Create the daily notification
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

                                //Setting the notification for when the challenge starts
                                Intent challengeStart = new Intent(getApplicationContext(), AlarmReceiverChallengeStart.class);
                                challengeStart.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                PendingIntent challengeStartPending = PendingIntent.getBroadcast(getApplicationContext(), 1, challengeStart, PendingIntent.FLAG_CANCEL_CURRENT);
                                String start = challenges.getStart();
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
                                manager.set(AlarmManager.RTC_WAKEUP, endTime.getTimeInMillis(), challengeStartPending);


                                Toast.makeText(getApplicationContext(), "Joined Team", Toast.LENGTH_SHORT).show();
                                Intent intent2 = new Intent(getApplicationContext(), AthleteHomepage.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intent2);
                                finish();
                            }

                            @Override
                            public void onFailure() {
                                Toast.makeText(getApplicationContext(), "Error joining team", Toast.LENGTH_SHORT);
                            }
                        });
                    }

                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog do nothing
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });



    }

    class JoinTeamListAdapter extends BaseAdapter {
        List<Team> teams;

        @Override
        public int getCount() {
            return teams.size();
        }

        @Override
        public Team getItem(int position) {
            return teams.get(position);
        }

        @Override
        public long getItemId(int position) {
            return teams.get(position).getTeamId();
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.challenge_list, null);

            TextView teamName = (TextView) view.findViewById(R.id.ListElementTop);
            TextView teamCreator = (TextView) view.findViewById(R.id.ListElementBotttom);

            teamName.setText("Team Name: " + teams.get(position).getTeamName());
            teamCreator.setText("Team Creator: " + teams.get(position).getOrganizer());

            return view;
        }
    }



}

