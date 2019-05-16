package com.example.a355appgroupc7;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import static java.lang.Integer.parseInt;

public class AthleteHomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.athlete_home_fragment, null);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Athlete Homepage");


        APIConnection conn = new APIConnection();
        //Challenge list
        final ListView activechallengeList = view.findViewById(R.id.ActiveChallengeList);
        final ListView upcomingchallengeList= view.findViewById(R.id.UpcomingChallengesList);

        conn.getActiveChallenges(this.getContext(),Integer.parseInt(CurrentUser.getInstance().getUserId()),new APIConnection.ResponseHandlerGetChallenge() {
            @Override
            public void onSuccess(List<Challenges> response) {
                activeChallengeListAdapter adapter = new activeChallengeListAdapter();
                adapter.challenges = response;
                activechallengeList.setAdapter(adapter);
            }

            @Override
            public void onFailure() {

            }
        });

        conn.getUpcomingChallenges(this.getContext(),Integer.parseInt(CurrentUser.getInstance().getUserId()),new APIConnection.ResponseHandlerGetChallenge() {
            @Override
            public void onSuccess(List<Challenges> response) {
                upcomingChallengeListAdapter adapter = new upcomingChallengeListAdapter();
                adapter.challenges = response;
                upcomingchallengeList.setAdapter(adapter);
            }

            @Override
            public void onFailure() {

            }
        });



        //DETAILS
        activechallengeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //This is what will happen when you click a thing in the list
                Challenges selectedChallenge = (Challenges) parent.getItemAtPosition(position);
                Log.i("CHALLENGE SELECTED = ", selectedChallenge.getChallengeName());

                Intent intent = new Intent(getContext(), LogChallenge.class);
                intent.putExtra("CHALLENGE", selectedChallenge);
                startActivity(intent);
            }
        });

        upcomingchallengeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //This is what will happen when you click a thing in the list
                Challenges selectedChallenge = (Challenges) parent.getItemAtPosition(position);
                Log.i("CHALLENGE SELECTED = ", selectedChallenge.getChallengeName());
                //USE CHALLENGE DETAIL LAYOUT
                Intent intent = new Intent(getContext(), UpcomingChallengeDetails.class);
                intent.putExtra("CHALLENGE", selectedChallenge);
                startActivity(intent);
            }
        });





    }

    class activeChallengeListAdapter extends BaseAdapter {
        List<Challenges> challenges;

        @Override
        public int getCount() {
            return challenges.size();
        }

        @Override
        public Object getItem(int position) {
            return challenges.get(position);
        }

        @Override
        public long getItemId(int position) {
            return challenges.get(position).getId();
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.challenge_list, null);

            TextView chalName = (TextView) view.findViewById(R.id.ListElementTop);
            TextView chalActivity = (TextView) view.findViewById(R.id.ListElementBotttom);

            chalName.setText(""+challenges.get(position).getChallengeName());
            chalActivity.setText("Type:   "+challenges.get(position).getActivity());

            return view;
        }
    }

    class upcomingChallengeListAdapter extends BaseAdapter {
        List<Challenges> challenges;

        @Override
        public int getCount() {
            return challenges.size();
        }

        @Override
        public Object getItem(int position) {
            return challenges.get(position);
        }

        @Override
        public long getItemId(int position) {
            return challenges.get(position).getId();
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.challenge_list, null);

            TextView chalName = (TextView) view.findViewById(R.id.ListElementTop);
            TextView chalActivity = (TextView) view.findViewById(R.id.ListElementBotttom);

            chalName.setText(""+challenges.get(position).getChallengeName());
            chalActivity.setText("Type:   "+challenges.get(position).getActivity());

            return view;
        }
    }

    }

