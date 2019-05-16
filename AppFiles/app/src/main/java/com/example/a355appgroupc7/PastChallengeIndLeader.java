package com.example.a355appgroupc7;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class PastChallengeIndLeader extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_past_challenges_ind_leader,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different title

        final Challenges challenge = (Challenges) getActivity().getIntent().getSerializableExtra("CHALLENGE");
        APIConnection conn = new APIConnection();
        //leader list
        final ListView leaderList = view.findViewById(R.id.indivualLeaderList);

        conn.getIndividualLeader(this.getContext(), challenge.getId() ,new APIConnection.ResponseHandlerHistory() {
            @Override
            public void onSuccess(List<LogObject> response) {
                leaderListAdapter adapter = new leaderListAdapter();
                adapter.history = response;
                leaderList.setAdapter(adapter);
            }

            @Override
            public void onFailure() {

            }
        });

    }

    class leaderListAdapter extends BaseAdapter {
        List<LogObject> history;

        @Override
        public int getCount() {
            return history.size();
        }

        @Override
        public Object getItem(int position) {
            return history.get(position);
        }

        @Override
        public long getItemId(int position) {
            return history.get(position).getLogId();
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.individualleader_list_item, null);

            TextView name = (TextView) view.findViewById(R.id.userNameLeader);
            TextView result = (TextView) view.findViewById(R.id.resultLeader);
            TextView logDate = view.findViewById(R.id.logDateLeader);

            name.setText("Name:   "+history.get(position).getUsername());
            result.setText("Result:   "+history.get(position).getResult());
            logDate.setText("Log Date:   "+history.get(position).getLogDate());

            return view;
        }
    }
}
