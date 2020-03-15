package com.lukemadzedze.zapperdisplay.persons.view.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.lukemadzedze.zapperdisplay.R;
import com.lukemadzedze.zapperdisplay.persons.data.Resource;
import com.lukemadzedze.zapperdisplay.persons.data.model.Team;
import com.lukemadzedze.zapperdisplay.persons.view.activity.MainActivity;
import com.lukemadzedze.zapperdisplay.persons.viewmodel.MainViewModel;

import java.util.Objects;
import dagger.android.support.DaggerFragment;

public class PersonDetailFragment extends DaggerFragment {

    public static final String ARG_ITEM_ID = "id";

    MainViewModel viewModel;
    private int personId;

    private TextView txtId;
    private TextView txtName;
    private TextView txtTeam;

    public PersonDetailFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ((MainActivity) Objects.requireNonNull(getActivity())).getViewModel();
        viewModel.fetchTeamById(personId);
        initObservers();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_ITEM_ID)) {
            personId = getArguments().getInt(ARG_ITEM_ID);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        txtId = rootView.findViewById(R.id.id);
        txtName = rootView.findViewById(R.id.name);
        txtTeam = rootView.findViewById(R.id.team);
        return rootView;
    }

    private void initObservers() {
        viewModel.teamLiveData().observe(this, new Observer<Resource<Team>>() {
            @Override
            public void onChanged(Resource<Team> teamResource) {
                if (teamResource.data != null) {
                    txtId.setText(String.valueOf(teamResource.data.getId()));
                    txtName.setText(teamResource.data.getPerson());
                    txtTeam.setText(teamResource.data.getTeam());
                }

            }
        });
    }
}
