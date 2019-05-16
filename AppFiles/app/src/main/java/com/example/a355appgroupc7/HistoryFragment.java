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

public class HistoryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.history_fragment, null);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("History");

        APIConnection conn = new APIConnection();
        //Challenge list
        final ListView challengeList = view.findViewById(R.id.historyList);

        conn.getHistory(this.getContext(), CurrentUser.getInstance().getUserId(),new APIConnection.ResponseHandlerHistory() {
            @Override
            public void onSuccess(List<LogObject> response) {
                ChallengeListAdapter adapter = new ChallengeListAdapter();
                adapter.history = response;
                challengeList.setAdapter(adapter);
            }

            @Override
            public void onFailure() {

            }
        });





        challengeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //This is what will happen when you click a thing in the list
                LogObject selectedHistory = (LogObject) parent.getItemAtPosition(position);
                Log.i("CHALLENGE SELECTED = ", selectedHistory.getChallengeName());

                Intent intent = new Intent(getContext(), HistoryDetails.class);
                intent.putExtra("CHALLENGE", selectedHistory);
                startActivity(intent);
            }
        });
    }

    class ChallengeListAdapter extends BaseAdapter {
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
            view = getLayoutInflater().inflate(R.layout.challenge_list, null);

            TextView chalName = (TextView) view.findViewById(R.id.ListElementTop);
            TextView chalActivity = (TextView) view.findViewById(R.id.ListElementBotttom);

            chalName.setText("Name:   "+history.get(position).getChallengeName());
            chalActivity.setText("Type:   "+history.get(position).getActivity());

            return view;
        }
    }
}
