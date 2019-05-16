package com.example.a355appgroupc7;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PastChallengeDetailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_past_challenges_details,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView challengeName = view.findViewById(R.id.ChallengeDetailNamePast);
        TextView challengeCreator = view.findViewById(R.id.CoachNamePast);
        TextView challengeStart = view.findViewById(R.id.startDatePast);
        TextView challengeDescription = view.findViewById(R.id.descriptionPast);
        TextView challengeEnd = view.findViewById(R.id.endDatePast);


        final Challenges challenge = (Challenges) getActivity().getIntent().getSerializableExtra("CHALLENGE");

        challengeName.setText(challenge.getChallengeName());
        challengeCreator.setText("Coach: " + challenge.getCoach());
        challengeStart.setText(challenge.getStart());
        challengeDescription.setText(challenge.getDesc());
        challengeEnd.setText(challenge.getEnd());

    }
}
