package com.example.a355appgroupc7;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AthleteHomepage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_homepage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Creating the notification channel needed for the challenge start notification
        new Thread(new Runnable() {
            @Override
            public void run() {
                createChallengeStartNotificationChannel();
            }
        }).start();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_athlete_homepage);
        navigationView.setNavigationItemSelectedListener(this);


        /*
        Setting the email and username values in the sidebar
         */
        View emailView = navigationView.getHeaderView(0);
        TextView email = (TextView) emailView.findViewById(R.id.athleteSidebarEmailTextView);
        email.setText(CurrentUser.getInstance().getEmail());

        View usernameView = navigationView.getHeaderView(0);
        TextView username = (TextView) usernameView.findViewById(R.id.athleteSidebarUsernameTextView);
        username.setText(CurrentUser.getInstance().getUsername());


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame_athlete, new AthleteHomeFragment());
        ft.commit();


    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.athlete_homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sp;
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //Make this the logout button
        if (id == R.id.action_settings) {
            CurrentUser.getInstance().resetUser();
            Intent intent = new Intent(getApplicationContext(), LoginPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            sp = getSharedPreferences("login", MODE_PRIVATE);
            sp.edit().putBoolean("loggedIn", false).apply();
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //Create new fragment instance
        //For every button on nav bar
        //and link to the frame of homepage
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.NavBarPastChallenges) {
            fragment = new AthletePastChallengesFragment();

        }else if (id == R.id.NavBarChallengeAthlete){
            fragment = new AthleteChallengeFragment();

        }else if (id == R.id.NavBarTeams){
            fragment = new AthleteTeamFragment();

        }else if (id == R.id.NavBarHome){
            fragment = new AthleteHomeFragment();

        }else if (id == R.id.NavBarProfile){
            fragment = new ProfileFragment();

        }else if (id == R.id.NavBarHistory){
            fragment = new HistoryFragment();
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.content_frame_athlete, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }
    private void createChallengeStartNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Challenge Start";
            String description = "Challenge starting soon!";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("ChallengeStartNotification", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
