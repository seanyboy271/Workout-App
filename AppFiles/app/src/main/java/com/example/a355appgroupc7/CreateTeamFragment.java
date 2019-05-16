package com.example.a355appgroupc7;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateTeamFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.create_team_fragment, null);
    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Create Team");

        //CREATE TEAM CLASS
//        Button create = view.findViewById(R.id.createTeamCreateButton);
//        create.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText teamNameView = view.findViewById(R.id.teamNameInput);
//                final String teamName = teamNameView.getText().toString();
//                EditText teamSizeView = view.findViewById(R.id.teamSizeInput);
//                final String teamSize = teamSizeView.getText().toString();
//                //Error checking (empty fields and non-integer for teamSize
//                if (teamName.isEmpty() || teamSize.isEmpty()) {
//                    teamNameView.setError("Team name required.");
//                    teamSizeView.setError("Team size required.");
//                }
//                else
//                {
//
//                    Team newTeam = new Team(teamName, CurrentUser.getInstance().getUserId(), teamSize);
//                    newTeam.setChallengeId("1");// TODO: this is hard coded as a test for now, we need a way to get the context of which challenge it's being made for.
//
//                    APIConnection conn = new APIConnection();
//                    //POST to database
//                    conn.createTeam(getContext(), newTeam, new APIConnection.ResponseHandler() {
//                        @Override
//                        public void onSuccess() {
//                            Toast.makeText(getContext(), "Team created!", Toast.LENGTH_SHORT).show();
//
//                        }
//
//                        @Override
//                        public void onFailure() {
//                            Toast.makeText(getContext(), "Team Creation failed!", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                }
//
//
//            }
//        });
    }
}
