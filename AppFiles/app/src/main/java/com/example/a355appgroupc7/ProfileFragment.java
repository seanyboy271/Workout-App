package com.example.a355appgroupc7;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.profiles_fragment, null);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Profile");
        //set username + email
        TextView profileUsername = view.findViewById(R.id.profileUsername);
        profileUsername.setText(CurrentUser.getInstance().getUsername());
        TextView email = view.findViewById(R.id.profileEmail);
        email.setText(CurrentUser.getInstance().getEmail());
        TextView bio = view.findViewById(R.id.profileBioView);
        bio.setText(CurrentUser.getInstance().getBio());
        //Edit profile button
        Button edit = view.findViewById(R.id.edit_profile);

        edit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent onEdit = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(onEdit);
            }
        });

    }

}

