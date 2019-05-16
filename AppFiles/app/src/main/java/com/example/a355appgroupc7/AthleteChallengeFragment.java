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

/**
 * Created by Belal on 18/09/16.
 */


public class AthleteChallengeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.athlete_challenge_fragment, null);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Athlete Challenges");

        final ListView challengeList = view.findViewById(R.id.ChallengesList);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                APIConnection conn = new APIConnection();
                conn.getAllChallenges(getContext(), new APIConnection.ResponseHandlerGetChallenge() {
                    @Override
                    public void onSuccess(List<Challenges> response) {
                        ChallengeListAdapter adapter = new ChallengeListAdapter();
                        adapter.challenges = response;
                        challengeList.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure() {

                    }
                });

            }
        });

        //Challenge list




        challengeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //This is what will happen when you click a thing in the list
                Challenges selectedChallenge = (Challenges) parent.getItemAtPosition(position);
                Log.i("CHALLENGE SELECTED = ", selectedChallenge.getChallengeName());

                Intent intent = new Intent(getContext(), ChallengeDetails.class);
                intent.putExtra("CHALLENGE", selectedChallenge);
                startActivity(intent);
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

            chalName.setText(""+challenges.get(position).getChallengeName());
            chalActivity.setText("Type:   "+challenges.get(position).getActivity());

            return view;
        }
    }
}

