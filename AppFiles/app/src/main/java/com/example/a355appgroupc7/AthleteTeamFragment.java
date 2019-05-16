package com.example.a355appgroupc7;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class AthleteTeamFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.athlete_teams_fragment, null);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Athlete Teams");

        APIConnection conn = new APIConnection();
        //TEam list
        final ListView teamList = view.findViewById(R.id.teamsList);

        conn.getTeams(this.getContext(), Integer.parseInt(CurrentUser.getInstance().getUserId()), new APIConnection.ResponseHandlerGetTeam() {
            @Override
            public void onSuccess(List<Team> response) {
                TeamListAdapter adapter = new TeamListAdapter();
                adapter.teams = response;
                teamList.setAdapter(adapter);
            }

            @Override
            public void onFailure() {
                Toast.makeText(getContext(), "getTeams onFailure invoked from AthleteTeamFragment Class", Toast.LENGTH_LONG).show();
            }
        });

    }

    class TeamListAdapter extends BaseAdapter {
        List<Team> teams;

        @Override
        public int getCount() {
            return teams.size();
        }

        @Override
        public Object getItem(int position) {
            return teams.get(position);
        }

        @Override
        public long getItemId(int position) {
            return teams.get(position).getTeamId();
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.team_list_item, null);

            TextView teamName = (TextView) view.findViewById(R.id.teamName);
            TextView teamSize = (TextView) view.findViewById(R.id.teamSize);

            teamName.setText(""+teams.get(position).getTeamName());
            teamSize.setText("Team Size: " + teams.get(position).getSize());

            return view;
        }
    }
}
