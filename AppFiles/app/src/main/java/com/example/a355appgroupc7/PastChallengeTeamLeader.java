package com.example.a355appgroupc7;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PastChallengeTeamLeader extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_past_challenges_team_leader,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different title

        final Challenges challenge = (Challenges) getActivity().getIntent().getSerializableExtra("CHALLENGE");
        final APIConnection conn = new APIConnection();
        //leader list
        final ListView leaderList = view.findViewById(R.id.teamLeaderList);
        final List<TeamLeader> teamlist = new ArrayList<>();

        conn.getTeamsByChallengeId(this.getContext(), challenge.getId() ,new APIConnection.ResponseHandlerGetTeam() {
            @Override
            public void onSuccess(List<Team> response) {
                for (int i = 0; i < response.size(); i++) {
                    final TeamLeader teamLeader = new TeamLeader(response.get(i).getTeamName(), response.get(i).getTeamId());
                    conn.getTeamLeader(getContext(), challenge.getId(), teamLeader.getTeamId(), teamLeader, new APIConnection.ResponseHandlerTeamLeader() {

                        @Override
                        public void onSuccess(TeamLeader teamLeader1) {
                            Log.i("team size", String.valueOf(teamLeader1.getTeamSize()));
                            Log.i("team avg", String.valueOf(teamLeader1.getTeamAvg()));
                            teamlist.add(teamLeader1);
                            leaderListAdapter adapter = new leaderListAdapter();
                            adapter.teamLeaders = teamlist;
                            leaderList.setAdapter(adapter);

                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(getContext(), "Get Team Leader Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }

            @Override
            public void onFailure() {
                Toast.makeText(getContext(), "Get Teams in challenge failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    class leaderListAdapter extends BaseAdapter {
        List<TeamLeader> teamLeaders;

        @Override
        public int getCount() {
            return teamLeaders.size();
        }

        @Override
        public Object getItem(int position) {
            return teamLeaders.get(position);
        }

        @Override
        public long getItemId(int position) {
            return teamLeaders.get(position).getTeamId();
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.individualleader_list_item, null);

            TextView name = (TextView) view.findViewById(R.id.userNameLeader);
            TextView result = (TextView) view.findViewById(R.id.resultLeader);
            TextView size = view.findViewById(R.id.logDateLeader);

            name.setText("Team Name:   "+teamLeaders.get(position).getTeamName());
            result.setText("Average Result:   "+teamLeaders.get(position).getTeamAvg());
            size.setText("Team Size:   "+teamLeaders.get(position).getTeamSize());

            return view;
        }
    }





}
