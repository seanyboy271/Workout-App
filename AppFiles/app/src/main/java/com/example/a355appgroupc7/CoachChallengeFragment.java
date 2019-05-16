package com.example.a355appgroupc7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class CoachChallengeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        View v  = inflater.inflate(R.layout.coach_challenge_fragment,null);


        //change R.layout.yourlayoutfilename for each of your fragments
        return v;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("My Challenges");

        //Create Team button --> CreateTeamFragment
        FloatingActionButton createChallenge = view.findViewById(R.id.CreateChallengeButton);
        createChallenge.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent onCreateChallenge = new Intent(getActivity(), CreateChallenge.class);
                startActivity(onCreateChallenge);
            }
        });

        final ListView challengeList = view.findViewById(R.id.CoachChallengesList);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                APIConnection conn = new APIConnection();
                conn.getChallengesCoach(getContext(), Integer.parseInt(CurrentUser.getInstance().getUserId()),new APIConnection.ResponseHandlerGetChallenge() {
                    @Override
                    public void onSuccess(List<Challenges> response) {
                        CoachChallengeFragment.ChallengeListAdapter adapter = new CoachChallengeFragment.ChallengeListAdapter();
                        adapter.challenges = response;
                        challengeList.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure() {

                    }
                });

            }
        });

    }

    class ChallengeListAdapter extends BaseAdapter {
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

            chalName.setText("" + challenges.get(position).getChallengeName());
            chalActivity.setText("Type:   " + challenges.get(position).getActivity());

            return view;
        }
    }
}

