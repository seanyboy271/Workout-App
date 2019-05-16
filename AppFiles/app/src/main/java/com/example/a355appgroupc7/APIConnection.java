package com.example.a355appgroupc7;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIConnection extends AppCompatActivity {

    public interface ResponseHandler {
        void onSuccess();
        void onFailure();
    }
    public interface ResponseHandlerCreateTeam {
        void onSuccess(Team team);
        void onFailure();
    }
    public interface ResponseHandlerGetUserById {
        void onSuccess(String username);
        void onFailure();
    }
    public interface ResponseHandlerGetChallenge {
        void onSuccess(List<Challenges> response);
        void onFailure();
    }
    public interface ResponseHandlerGetTeam {
        void onSuccess(List<Team> response);
        void onFailure();
    }
    public interface  ResponseHandlerHistory {
        void onSuccess(List<LogObject> response);
        void onFailure();
    }
    public interface ResponseHandlerTeamLeader{
        void onSuccess(TeamLeader teamleader);
        void onFailure();
    }



    /**
     * Sends a get request to the database, which requests the user-input username
     * If data is returned, this means that there is already a user with the selected username value
     * @param context
     * @param username
     * @param responseHandler
     */
    public void checkUsernameCredential(Context context, String username,  final ResponseHandler responseHandler){
        String url = String.format("http://ec2-34-230-82-126.compute-1.amazonaws.com/api/users/users.php?username=%s", username);

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //This is where we should parse through "response" to find the info we requested
                        Log.i("Response: " , response.toString());

                        String in = response.toString();

                        /*
                        Parse through the response and determine if any data was returned
                         */
                        try {
                            JSONObject reader = new JSONObject(in);

                            boolean validUsername = !reader.has("Id");

                            if(!validUsername){
                                responseHandler.onFailure();
                            }
                            else {
                                responseHandler.onSuccess();
                            }
                        }

                        catch(JSONException e){
                            Log.e("JSON EXCEPTION", e.toString());
                            e.printStackTrace();
                            responseHandler.onFailure();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("Volley response error", error.toString());
                    }
                });
        queue.add(jsonObjectRequest);
    }


    /**
     * Sends a get request to the database, which requests the input Email
     * If data is returned, this means that there is already a user with the selected Email value
     * @param context
     * @param email
     * @param responseHandler
     */

    public void checkEmailCredential(final Context context, String email, final ResponseHandler responseHandler){
        String url = String.format("http://ec2-34-230-82-126.compute-1.amazonaws.com/api/users/users.php?email=%s", email);

        Log.i("INSIDE CHECK EMAIL", "  ");

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //This is where we should parse through "response" to find the info we requested
                        Log.i("Response: " , response.toString());

                        String in = response.toString();

                        /*
                        Parse through the response and determine if any data was returned
                         */
                        try {
                            JSONObject reader = new JSONObject(in);

                            boolean validUsername = !reader.has("Id");

                            if(!validUsername){
                                responseHandler.onFailure();
                            }
                            else {
                                responseHandler.onSuccess();
                            }
                        }

                        catch(JSONException e){
                            Log.e("JSON EXCEPTION", e.toString());
                            e.printStackTrace();
                            responseHandler.onFailure();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("Volley response error", error.toString());
                        Toast.makeText(context, "Unexpected error occurred", Toast.LENGTH_SHORT);
                    }
                });

        queue.add(jsonObjectRequest);
    }





    public void loginUser(final Context context, String username, String password, final ResponseHandler responseHandler) {
        String url = String.format("http://ec2-34-230-82-126.compute-1.amazonaws.com/api/users/users.php?username=%s&password=%s", username, password);

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest;
        jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //This is where we should parse through "response" to find the info we requested
                        Log.i("Response: " , response.toString());

                        try {
                            String in = response.toString();

                            JSONObject reader = new JSONObject(in);

                            boolean hasId = reader.has("Id");

                            if (hasId) {
                                //Setting all of the values for the current user
                                String userid = reader.getString("Id");
                                CurrentUser.getInstance().setUserId(userid);

                                String isCoach = reader.getString("IsCoach");
                                Log.i("isCoach value", isCoach);
                                CurrentUser.getInstance().setIsCoach(isCoach);

                                String username = reader.getString("Username");
                                CurrentUser.getInstance().setUsername(username);

                                String email = reader.getString("Email");
                                CurrentUser.getInstance().setEmail(email);

                                String bio = reader.getString("Bio");
                                CurrentUser.getInstance().setBio(bio);

                                responseHandler.onSuccess();
                            }
                            else {
                                CurrentUser.getInstance().setUserId("-1");
                                responseHandler.onFailure();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("Volley response error", error.toString());
                        Toast.makeText(context, "Unexpected error occured", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public void getUserById(final Context context, int Id, final ResponseHandlerGetUserById responseHandler) {
        String url = String.format("http://ec2-34-230-82-126.compute-1.amazonaws.com/api/users/users.php?id=%s", Integer.toString(Id));

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //This is where we should parse through "response" to find the info we requested
                        Log.i("Response of get user by id: " , response.toString());

                        try {
                            String in = response.toString();

                            JSONObject reader = new JSONObject(in);

                            boolean hasUsername = reader.has("Username");

                            if (hasUsername) {
                                String coach = reader.getString("Username");
                                responseHandler.onSuccess(coach);
                            }
                            else {
                                CurrentUser.getInstance().setUserId("-1");
                                responseHandler.onFailure();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("Volley response error", error.toString());
                        Toast.makeText(context, "Unexpected error occured", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonObjectRequest);
    }



    public void signupUser(Context context, final Users user, final ResponseHandler responseHandler) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://ec2-34-230-82-126.compute-1.amazonaws.com/api/users/users.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response", response);

                try {
                    String in = response;
                    JSONObject reader = new JSONObject(in);

                    boolean hasId = reader.has("Id");

                    if (hasId) {
                        String userid = reader.getString("Id");
                        CurrentUser.getInstance().setUserId(userid);
                        responseHandler.onSuccess();
                    }
                    else {
                        CurrentUser.getInstance().setUserId("-1");
                        responseHandler.onFailure();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                responseHandler.onSuccess();
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Log.e("VOLLEY ERROR", error.toString());
                responseHandler.onFailure();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();

                /*
                 * This is where the information for each account would go to add to the database
                 */
                MyData.put("email", user.getEmail());
                MyData.put("username", user.getUsername()); //Add the data you'd like to send to the server.
                MyData.put("password", user.getPassword());
                MyData.put("passwordHash", user.getHash());
                MyData.put("isCoach", user.getIsCoach());
                MyData.put("isPrivate", "1");

                return MyData;
            }
        };

        requestQueue.add(MyStringRequest);
    }



    public void changePassword() {
        
    }

    public void joinTeam(Context context, final Team team, final Challenges challenge, final ResponseHandler responseHandler){
        Log.i("TEAM INFO IN JOIN  TEAM", Integer.toString(team.getTeamId()));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://ec2-34-230-82-126.compute-1.amazonaws.com/api/teams/jointeam.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response", response);

                try {
                    String in = response;
                    Log.i("Reponse from JoinTeam", response);
                    JSONObject reader = new JSONObject(in);

                    boolean hasId = reader.has("Id");

                    if (hasId) {
                        responseHandler.onSuccess();
                    }
                    else {
                        responseHandler.onFailure();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                responseHandler.onSuccess();
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Log.e("VOLLEY ERROR", error.toString());
                responseHandler.onFailure();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();

                /*
                 * This is where the information for each account would go to add to the database
                 * PUTS EVERYTHING INTO LOG TABLE.
                 */
                Log.i("USER ID IN in getParams", "  " + CurrentUser.getInstance().getUserId());
                MyData.put("userId", CurrentUser.getInstance().getUserId());
                //MyData.put("userName", CurrentUser.getInstance().getUsername());
                MyData.put("teamId", Integer.toString(team.getTeamId()));
                //MyData.put("teamName", team.getTeamName());
                //MyData.put("chaId", Integer.toString(challenge.getId()));
                MyData.put("chaId", Integer.toString(challenge.getId()));
                //MyData.put("chaName", challenge.getChallengeName());
                //MyData.put("desc", challenge.getDesc());
                //MyData.put("start", challenge.getStart());
                //MyData.put("end", challenge.getEnd());
                //MyData.put("fail", challenge.getFail());
                //MyData.put("creator", team.getOrganizer());
                //MyData.put("activity", challenge.getActivity());
                   //Add the data you'd like to send to the server.

                return MyData;
            }
        };

        requestQueue.add(MyStringRequest);
    }


    //POST TO TEAM METHOD
    public void createTeam(Context context,final Team team, final ResponseHandlerCreateTeam responseHandler) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://ec2-34-230-82-126.compute-1.amazonaws.com/api/teams/teams.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.i("Response from Create Team", response);

                try {
                    String in = response;
                    JSONObject reader = new JSONObject(response);

                    boolean hasId = reader.has("Id");

                    if (hasId) {
                        Log.i("Team id?????", reader.getString("Id"));
                        team.setTeamId(Integer.parseInt(reader.getString("Id")));
                        Log.i("Team Id after Set", Integer.toString(team.getTeamId()));
                        responseHandler.onSuccess(team);
                    }
                    else {
                        Log.e("Create team failure in create team api method " ,  "  ");
                        responseHandler.onFailure();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Log.e("VOLLEY ERROR", error.toString());
                responseHandler.onFailure();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();

                /*
                 * This is where the information for each account would go to add to the database
                 */
                MyData.put("organizer", team.getOrganizer());
                MyData.put("teamName", team.getTeamName()); //Add the data you'd like to send to the server.
                MyData.put("teamSize", team.getSize());
                MyData.put("chalId", team.getChallengeId());

                return MyData;
            }
        };

        requestQueue.add(MyStringRequest);


    }


    public void challengeCreate(Context context, final Challenges challenge, final ResponseHandler responseHandler) {
        Log.i("Challenge info " , challenge.getChallengeName() + " " + challenge.getDesc() + " " + challenge.getActivity() + " " + challenge.getFail());
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://ec2-34-230-82-126.compute-1.amazonaws.com/api/challenges/challenges.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response", response);


                responseHandler.onSuccess();
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Log.e("VOLLEY ERROR", error.toString());
                responseHandler.onFailure();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();

                /*
                 * This is where the information for each account would go to add to the database
                 */

                MyData.put("chalName", challenge.getChallengeName());
                MyData.put("chalDesc", challenge.getDesc()); //Add the data you'd like to send to the server.
                MyData.put("chalCreator", CurrentUser.getInstance().getUserId());
                MyData.put("chalStart", challenge.getStart());
                MyData.put("chalActivity", challenge.getActivity());
                MyData.put("chalEnd", challenge.getEnd());
                MyData.put("chalFail", challenge.getFail());

                return MyData;
            }
        };

        requestQueue.add(MyStringRequest);
    }
    //GET ALL CHALLENGES BEFORE START DATE (OPEN CHALLENGES)
    public void getAllChallenges(final Context context, final ResponseHandlerGetChallenge responseHandler){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://ec2-34-230-82-126.compute-1.amazonaws.com/api/challenges/challenges.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Get raw response as string
                            String in = response.toString();
                            Log.i("getChallenges Response", in);

                            // convert to json array
                            JSONArray reader = new JSONArray(in);

                            boolean hasResults = reader.length() > 0;

                            // If there are results...
                            if (hasResults) {
                                // Create a list of teams to hold the results
                                List<Challenges> challenges = new ArrayList<Challenges>();

                                // Iterate through the json array of teams
                                for (int i = 0; i < reader.length(); i++) {
                                    final Challenges challenge = new Challenges(
                                            reader.getJSONObject(i).getString("Name"),
                                            reader.getJSONObject(i).getString("Description"),
                                            reader.getJSONObject(i).getString("Start"),
                                            reader.getJSONObject(i).getString("Activity"),
                                            reader.getJSONObject(i).getString("End"),
                                            reader.getJSONObject(i).getString("Fail"));
                                    APIConnection conn = new APIConnection();
                                    conn.getUserById(context, Integer.parseInt(reader.getJSONObject(i).getString("Creator")), new ResponseHandlerGetUserById() {
                                        @Override
                                        public void onSuccess(String username) {
                                            Log.i("Username", username);
                                            challenge.setCoach(username);
                                        }

                                        @Override
                                        public void onFailure() {
                                            Log.e("Could not retrieve coach ID", "");
                                        }
                                    });
                                    challenge.setId(Integer.parseInt(reader.getJSONObject(i).getString("Id")));
                                    challenges.add(challenge);
                                }

                                // Call the responsehandler and pass it the list of teams
                                responseHandler.onSuccess(challenges);
                            }
                            else {
                                CurrentUser.getInstance().setUserId("-1");
                                responseHandler.onFailure();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("Volley response error", error.toString());
                        try{
                            Toast.makeText(getApplicationContext(), "Error getting challenges.", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
        queue.add(jsonArrayRequest);
    }
    //GET ACTIVE CHALLENGES (LOG TABLE)
    // Updated by Ian to use to new SQL
    public void getActiveChallenges(final Context context, final int userId, final ResponseHandlerGetChallenge responseHandler){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://ec2-34-230-82-126.compute-1.amazonaws.com/api/challenges/activechallenges.php?userId=" + userId;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Get raw response as string
                            String in = response.toString();
                            Log.i("getChallenges Response", in);

                            // convert to json array
                            JSONArray reader = new JSONArray(in);

                            boolean hasResults = reader.length() > 0;

                            // If there are results...
                            if (hasResults) {
                                // Create a list of teams to hold the results
                                List<Challenges> challenges = new ArrayList<Challenges>();

                                // Iterate through the json array of teams
                                for (int i = 0; i < reader.length(); i++) {
                                    final Challenges challenge = new Challenges(
                                            reader.getJSONObject(i).getString("Name"),
                                            reader.getJSONObject(i).getString("Description"),
                                            reader.getJSONObject(i).getString("Start"),
                                            reader.getJSONObject(i).getString("Activity"),
                                            reader.getJSONObject(i).getString("End"),
                                            reader.getJSONObject(i).getString("Fail"));
                                    APIConnection conn = new APIConnection();
                                    conn.getUserById(context, Integer.parseInt(reader.getJSONObject(i).getString("Creator")), new ResponseHandlerGetUserById() {
                                        @Override
                                        public void onSuccess(String username) {
                                            Log.i("Username", username);
                                            challenge.setCoach(username);
                                        }

                                        @Override
                                        public void onFailure() {
                                            Log.e("Could not retrieve coach ID", "");
                                        }
                                    });
                                    challenge.setId(Integer.parseInt(reader.getJSONObject(i).getString("ChaId")));
                                    challenges.add(challenge);
                                }

                                // Call the responsehandler and pass it the list of challenges
                                responseHandler.onSuccess(challenges);
                            }
                            else {
                                CurrentUser.getInstance().setUserId("-1");
                                responseHandler.onFailure();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("Volley response error", error.toString());
                        try{
                            Toast.makeText(getApplicationContext(), "Error getting challenges.", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
        queue.add(jsonArrayRequest);
    }
    //GET ALL UPCOMING REGISTERED CHALLENGES (LOG TABLE)
    public void getUpcomingChallenges(final Context context, final int userId, final ResponseHandlerGetChallenge responseHandler){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://ec2-34-230-82-126.compute-1.amazonaws.com/api/challenges/upcomingchallenges.php?userId=" + userId;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Get raw response as string
                            String in = response.toString();
                            Log.i("getChallenges Response", in);

                            // convert to json array
                            JSONArray reader = new JSONArray(in);

                            boolean hasResults = reader.length() > 0;

                            // If there are results...
                            if (hasResults) {
                                // Create a list of teams to hold the results
                                List<Challenges> challenges = new ArrayList<Challenges>();

                                // Iterate through the json array of teams
                                for (int i = 0; i < reader.length(); i++) {
                                    final Challenges challenge = new Challenges(
                                            reader.getJSONObject(i).getString("Name"),
                                            reader.getJSONObject(i).getString("Description"),
                                            reader.getJSONObject(i).getString("Start"),
                                            reader.getJSONObject(i).getString("Activity"),
                                            reader.getJSONObject(i).getString("End"),
                                            reader.getJSONObject(i).getString("Fail"));
                                    APIConnection conn = new APIConnection();
                                    conn.getUserById(context, Integer.parseInt(reader.getJSONObject(i).getString("Creator")), new ResponseHandlerGetUserById() {
                                        @Override
                                        public void onSuccess(String username) {
                                            Log.i("Username", username);
                                            challenge.setCoach(username);
                                        }

                                        @Override
                                        public void onFailure() {
                                            Log.e("Could not retrieve coach ID", "");
                                        }
                                    });
                                    challenge.setId(Integer.parseInt(reader.getJSONObject(i).getString("ChaId")));
                                    challenges.add(challenge);
                                }

                                // Call the responsehandler and pass it the list of teams
                                responseHandler.onSuccess(challenges);
                            }
                            else {
                                CurrentUser.getInstance().setUserId("-1");
                                responseHandler.onFailure();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("Volley response error", error.toString());
                        try{
                            Toast.makeText(getApplicationContext(), "Error getting challenges.", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
        queue.add(jsonArrayRequest);
    }

    public void getPastChallenges(final Context context, final ResponseHandlerGetChallenge responseHandler){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://ec2-34-230-82-126.compute-1.amazonaws.com/api/challenges/pastchallenges.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Get raw response as string
                            String in = response.toString();
                            Log.i("getChallenges Response", in);

                            // convert to json array
                            JSONArray reader = new JSONArray(in);

                            boolean hasResults = reader.length() > 0;

                            // If there are results...
                            if (hasResults) {
                                // Create a list of teams to hold the results
                                List<Challenges> challenges = new ArrayList<Challenges>();

                                // Iterate through the json array of teams
                                for (int i = 0; i < reader.length(); i++) {
                                    final Challenges challenge = new Challenges(
                                            reader.getJSONObject(i).getString("Name"),
                                            reader.getJSONObject(i).getString("Description"),
                                            reader.getJSONObject(i).getString("Start"),
                                            reader.getJSONObject(i).getString("Activity"),
                                            reader.getJSONObject(i).getString("End"),
                                            reader.getJSONObject(i).getString("Fail"));
                                    APIConnection conn = new APIConnection();
                                    conn.getUserById(context, Integer.parseInt(reader.getJSONObject(i).getString("Creator")), new ResponseHandlerGetUserById() {
                                        @Override
                                        public void onSuccess(String username) {
                                            Log.i("Username", username);
                                            challenge.setCoach(username);
                                        }

                                        @Override
                                        public void onFailure() {
                                            Log.e("Could not retrieve coach ID", "");
                                        }
                                    });
                                    challenge.setId(Integer.parseInt(reader.getJSONObject(i).getString("Id")));
                                    challenges.add(challenge);
                                }

                                // Call the responsehandler and pass it the list of teams
                                responseHandler.onSuccess(challenges);
                            }
                            else {
                                CurrentUser.getInstance().setUserId("-1");
                                responseHandler.onFailure();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("Volley response error", error.toString());
                        try{
                            Toast.makeText(getApplicationContext(), "Error getting challenges.", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
        queue.add(jsonArrayRequest);
    }



    /**
     *
     * @param userId
     */
    public void getTeams(Context context, final int userId, final ResponseHandlerGetTeam responseHandler) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://ec2-34-230-82-126.compute-1.amazonaws.com/api/teams/teams.php?userId=" + userId;
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // convert to json array
                            JSONArray reader = response;
                            Log.i("Response from getTeams", response.toString());

                            boolean hasResults = reader.length() > 0;

                            // If there are results...
                            if (hasResults) {
                                // Create a list of teams to hold the results
                                List<Team> teams = new ArrayList<Team>();

                                // Iterate through the json array of teams
                                for (int i = 0; i < reader.length(); i++) {
                                    Team team = new Team(
                                            reader.getJSONObject(i).getString("Name"),
                                            reader.getJSONObject(i).getString("Organizer"),
                                            reader.getJSONObject(i).getString("Size"));

                                    team.setTeamId(Integer.parseInt(reader.getJSONObject(i).getString("Id")));
                                    teams.add(team);
                                }

                                // Call the responsehandler and pass it the list of teams
                                responseHandler.onSuccess(teams);
                            }
                            else {
                                CurrentUser.getInstance().setUserId("-1");
                                responseHandler.onFailure();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("Volley response error", error.toString());
                        try {
                            Toast.makeText(getApplicationContext(), "Error getting teams.", Toast.LENGTH_SHORT).show();
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
        queue.add(jsonObjectRequest);
    }


    public void getTeamsByChallengeId(Context context, int challengeId, final ResponseHandlerGetTeam responseHandler){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = String.format("http://ec2-34-230-82-126.compute-1.amazonaws.com/api/teams/teams.php?chaID=%s", Integer.toString(challengeId));
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Get raw response as string
                            String in = response.toString();

                            // convert to json array
                            JSONArray reader = new JSONArray(in);

                            boolean hasResults = reader.length() > 0;

                            // If there are results...
                            if (hasResults) {
                                // Create a list of teams to hold the results
                                List<Team> teams = new ArrayList<Team>();

                                // Iterate through the json array of teams
                                for (int i = 0; i < reader.length(); i++) {
                                    Team team = new Team(
                                            reader.getJSONObject(i).getString("Name"),
                                            reader.getJSONObject(i).getString("Organizer"),
                                            reader.getJSONObject(i).getString("Size"));
                                    team.setTeamId(Integer.parseInt(reader.getJSONObject(i).getString("Id")));
                                    teams.add(team);
                                }

                                // Call the responsehandler and pass it the list of teams
                                responseHandler.onSuccess(teams);
                            }
                            else {
                                responseHandler.onFailure();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("Volley response error", error.toString());
                        try {
                            Toast.makeText(getApplicationContext(), "Error getting teams.", Toast.LENGTH_SHORT).show();
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
        queue.add(jsonArrayRequest);
    }
    //LOG , UPDATES LOG TABLE WITH RESULT + LOGDATE
    public void logResult(Context context, final LogObject logresult, final ResponseHandler responseHandler) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://ec2-34-230-82-126.compute-1.amazonaws.com/api/log/log.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response", response);


                responseHandler.onSuccess();
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Log.e("VOLLEY ERROR", error.toString());
                responseHandler.onFailure();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();

                /*
                 * This is where the information for each account would go to add to the database
                 */

                MyData.put("userId", CurrentUser.getInstance().getUserId());
                MyData.put("chaId", logresult.getChallengeId());
                MyData.put("result", logresult.getResult());

                return MyData;
            }
        };

        requestQueue.add(MyStringRequest);
    }

    //HISTORY , PULL FROM LOG TABLE WHERE RESULT AND LOGDATE ARE FILLED
    public void getHistory(final Context context, String userId, final ResponseHandlerHistory responseHandler){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = String.format("http://ec2-34-230-82-126.compute-1.amazonaws.com/api/history/athleteHistory.php?userId" +
                "="+userId);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Get raw response as string
                            String in = response.toString();

                            // convert to json array
                            JSONArray reader = new JSONArray(in);

                            boolean hasResults = reader.length() > 0;

                            // If there are results...
                            if (hasResults) {
                                // Create a list of teams to hold the results
                                List<LogObject> logList = new ArrayList<LogObject>();

                                // Iterate through the json array of teams
                                for (int i = 0; i < reader.length(); i++) {
                                    final LogObject logHistory  = new LogObject(
                                            reader.getJSONObject(i).getString("UserId"),
                                            reader.getJSONObject(i).getString("ChaId"),
                                            reader.getJSONObject(i).getString("Result"));
                                    logHistory.setChallengeName(reader.getJSONObject(i).getString("Name"));
                                    logHistory.setLogDate(reader.getJSONObject(i).getString("LogDate"));
                                    //logHistory.setTeamName(reader.getJSONObject(i).getString("TeamName"));
                                    logHistory.setActivity(reader.getJSONObject(i).getString("Activity"));
                                    logHistory.setDescrip(reader.getJSONObject(i).getString("Description"));
                                    logHistory.setChallengeStart(reader.getJSONObject(i).getString("Start"));
                                    logHistory.setChallengeEnd(reader.getJSONObject(i).getString("End"));
                                    APIConnection conn = new APIConnection();
                                    conn.getUserById(context, Integer.parseInt(reader.getJSONObject(i).getString("Creator")), new ResponseHandlerGetUserById() {
                                        @Override
                                        public void onSuccess(String username) {
                                            Log.i("Username", username);
                                            logHistory.setCoach(username);
                                        }

                                        @Override
                                        public void onFailure() {
                                            Log.e("Could not retrieve coach ID", "");
                                        }
                                    });
                                    logList.add(logHistory);
                                }

                                // Call the responsehandler and pass it the list of teams
                                responseHandler.onSuccess(logList);
                            }
                            else {
                                responseHandler.onFailure();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("Volley response error", error.toString());
                        try {
                            Toast.makeText(getApplicationContext(), "Error getting history.", Toast.LENGTH_SHORT).show();
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
        queue.add(jsonArrayRequest);
    }

    //INDIVIDUAL LEADER BOARDS
    public void getIndividualLeader(final Context context, int chaId, final ResponseHandlerHistory responseHandler){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = String.format("http://ec2-34-230-82-126.compute-1.amazonaws.com/api/leaderboard/individual.php?chaId" +
                "="+chaId);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Get raw response as string
                            String in = response.toString();

                            // convert to json array
                            JSONArray reader = new JSONArray(in);

                            boolean hasResults = reader.length() > 0;

                            // If there are results...
                            if (hasResults) {
                                // Create a list of teams to hold the results
                                List<LogObject> logList = new ArrayList<LogObject>();

                                // Iterate through the json array of teams
                                for (int i = 0; i < reader.length(); i++) {
                                    final LogObject logHistory  = new LogObject(
                                            reader.getJSONObject(i).getString("UserId"),
                                            reader.getJSONObject(i).getString("ChaId"),
                                            reader.getJSONObject(i).getString("Result"));
                                    //logHistory.setChallengeName(reader.getJSONObject(i).getString("ChaName"));
                                    logHistory.setLogDate(reader.getJSONObject(i).getString("LogDate"));
                                    //logHistory.setTeamName(reader.getJSONObject(i).getString("TeamName"));
                                    //logHistory.setActivity(reader.getJSONObject(i).getString("Activity"));
                                    //logHistory.setDescrip(reader.getJSONObject(i).getString("Descrip"));
                                    //logHistory.setChallengeStart(reader.getJSONObject(i).getString("StartDate"));
                                    //logHistory.setChallengeEnd(reader.getJSONObject(i).getString("EndDate"));
                                    logHistory.setUsername(reader.getJSONObject(i).getString("Username"));
                                    logList.add(logHistory);
                                }

                                // Call the responsehandler and pass it the list of teams
                                responseHandler.onSuccess(logList);
                            }
                            else {
                                responseHandler.onFailure();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("Volley response error", error.toString());
                        try {
                            Toast.makeText(getApplicationContext(), "Error getting history.", Toast.LENGTH_SHORT).show();
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
        queue.add(jsonArrayRequest);
    }




    public void editBio(Context context, final String userId, final String bio, final ResponseHandler responseHandler){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://ec2-34-230-82-126.compute-1.amazonaws.com/api/bios/bios.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response", response);



                responseHandler.onSuccess();
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Log.e("VOLLEY ERROR", error.toString());
                responseHandler.onFailure();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();

                /*
                 * This is where the information for each account would go to add to the database
                 */
                MyData.put("bio", bio);
                MyData.put("id", userId);


                return MyData;
            }
        };

        requestQueue.add(MyStringRequest);
    }

    public void getTeamLeader(final Context context, int chaId, int teamId, final TeamLeader teamLeader, final ResponseHandlerTeamLeader responseHandler){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = String.format("http://ec2-34-230-82-126.compute-1.amazonaws.com/api/leaderboard/team.php?chaId=%s&teamId=%s", chaId, teamId);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Get raw response as string
                            String in = response.toString();

                            // convert to json array
                            JSONArray reader = new JSONArray(in);

                            boolean hasResults = reader.length() > 0;

                            // If there are results...
                            if (hasResults) {
                                // Create a list of teams to hold the results
                                double total = 0;
                                teamLeader.setTeamSize(reader.length());
                                // Iterate through the json array of teams
                                for (int i = 0; i < reader.length(); i++) {
                                    total += Double.parseDouble(reader.getJSONObject(i).getString("Result"));
                                }

                                teamLeader.setTeamAvg(total/teamLeader.getTeamSize());

                                // Call the responsehandler and pass it the list of teams
                                responseHandler.onSuccess(teamLeader);
                            }
                            else {
                                responseHandler.onFailure();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("Volley response error", error.toString());
                        try {
                            Toast.makeText(getApplicationContext(), "Error getting history.", Toast.LENGTH_SHORT).show();
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
        queue.add(jsonArrayRequest);
    }


    //GET challenge by Coach ID
    public void getChallengesCoach(final Context context, final int userId, final ResponseHandlerGetChallenge responseHandler){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://ec2-34-230-82-126.compute-1.amazonaws.com/api/challenges/challengesCoach.php?coachId=" + userId;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Get raw response as string
                            String in = response.toString();
                            Log.i("getChallenges Response", in);

                            // convert to json array
                            JSONArray reader = new JSONArray(in);

                            boolean hasResults = reader.length() > 0;

                            // If there are results...
                            if (hasResults) {
                                // Create a list of teams to hold the results
                                List<Challenges> challenges = new ArrayList<Challenges>();

                                // Iterate through the json array of teams
                                for (int i = 0; i < reader.length(); i++) {
                                    final Challenges challenge = new Challenges(
                                            reader.getJSONObject(i).getString("Name"),
                                            reader.getJSONObject(i).getString("Description"),
                                            reader.getJSONObject(i).getString("Start"),
                                            reader.getJSONObject(i).getString("Activity"),
                                            reader.getJSONObject(i).getString("End"),
                                            reader.getJSONObject(i).getString("Fail"));
                                    APIConnection conn = new APIConnection();
                                    conn.getUserById(context, Integer.parseInt(reader.getJSONObject(i).getString("Creator")), new ResponseHandlerGetUserById() {
                                        @Override
                                        public void onSuccess(String username) {
                                            Log.i("Username", username);
                                            challenge.setCoach(username);
                                        }

                                        @Override
                                        public void onFailure() {
                                            Log.e("Could not retrieve coach ID", "");
                                        }
                                    });
                                    challenge.setId(Integer.parseInt(reader.getJSONObject(i).getString("Id")));
                                    challenges.add(challenge);
                                }

                                // Call the responsehandler and pass it the list of teams
                                responseHandler.onSuccess(challenges);
                            }
                            else {
                                CurrentUser.getInstance().setUserId("-1");
                                responseHandler.onFailure();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("Volley response error", error.toString());
                        try{
                            Toast.makeText(getApplicationContext(), "Error getting challenges.", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
        queue.add(jsonArrayRequest);
    }
}





