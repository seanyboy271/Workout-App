package com.example.a355appgroupc7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CreateChallengePt2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge_pt2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Create Challenge pt2");

    }

    public void createChallenge2(View view){

        String desc = getIntent().getStringExtra("DESC");
        String fail = getIntent().getStringExtra("FAIL");
        String name  = getIntent().getStringExtra("NAME");
        String activity = getIntent().getStringExtra("ACTIVITY");

        TimePicker timePicker = findViewById(R.id.challengeTimePicker);
        DatePicker datePicker = findViewById(R.id.challengeStartDate);
        /*
        startTime contains the date and the time and is formatted as YYYY-MM-DD HH:MM:SS
         */
        String start = buildStart(datePicker, timePicker);


        DatePicker endDatePicker = findViewById(R.id.challengeEndDate);
        String end  = buildEnd(endDatePicker, timePicker);

        Challenges newChallenge = new Challenges(name, desc, start, activity, end, fail);
        Log.i("Challenge info", start + "  " + end);

        APIConnection conn = new APIConnection();
        //POST to database
        conn.challengeCreate(getApplicationContext(), newChallenge, new APIConnection.ResponseHandler() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(),"Challenge Created!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CoachHomepage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();

            }

            @Override
            public void onFailure() {
                Toast.makeText(getApplicationContext(),"Big oof. Challenge Failure.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String buildStart(DatePicker datePicker, TimePicker timePicker){

        String year = Integer.toString(datePicker.getYear());
        String month = Integer.toString(datePicker.getMonth() + 1);
        if (month.length() < 2){
            month = "0" + month;
        }
        String day = Integer.toString(datePicker.getDayOfMonth());
        if (day.length() < 2){
            day = "0" + day;
        }
        String hour = Integer.toString(timePicker.getHour());
        if (hour.length() < 2){
            hour = "0" + hour;
        }
        String minute = Integer.toString(timePicker.getMinute());
        if (minute.length() < 2){
            minute = "0" + minute;
        }

        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + "00";
    }
    public String buildEnd(DatePicker datePicker, TimePicker timePicker){

        String year = Integer.toString(datePicker.getYear());
        String month = Integer.toString(datePicker.getMonth() + 1);
        if (month.length() < 2){
            month = "0" + month;
        }
        String day = Integer.toString(datePicker.getDayOfMonth());
        if (day.length() < 2){
            day = "0" + day;
        }
        String hour = Integer.toString(timePicker.getHour());
        if (hour.length() < 2){
            hour = "0" + hour;
        }
        String minute = Integer.toString(timePicker.getMinute());
        if (minute.length() < 2){
            minute = "0" + minute;
        }

        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + "00";
    }
}
