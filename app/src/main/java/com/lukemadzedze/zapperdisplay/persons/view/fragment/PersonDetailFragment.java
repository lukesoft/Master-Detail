package com.lukemadzedze.zapperdisplay.persons.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.lukemadzedze.zapperdisplay.R;
import com.lukemadzedze.zapperdisplay.persons.view.activity.MainActivity;
import com.lukemadzedze.zapperdisplay.persons.viewmodel.MainViewModel;
import com.lukemadzedze.zapperdisplay.utils.Status;

import java.util.Objects;

import dagger.android.support.DaggerFragment;

public class PersonDetailFragment extends DaggerFragment {
    private MainViewModel viewModel;
    private TextView txtId;
    private TextView txtName;
    private TextView txtTeam;
    private ShimmerFrameLayout teamProgressShimmer;
    private CardView detailsCcontainer;

    public PersonDetailFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ((MainActivity) Objects.requireNonNull(getActivity())).getViewModel();
        initObservers();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        txtId = rootView.findViewById(R.id.id);
        txtName = rootView.findViewById(R.id.name);
        txtTeam = rootView.findViewById(R.id.team);
        teamProgressShimmer = rootView.findViewById(R.id.team_progress_shimmer);
        detailsCcontainer = rootView.findViewById(R.id.container);

        return rootView;
    }

    private void initObservers() {
        viewModel.getTeamByPersonId().observe(this, teamResource -> {
            showShimmerProgress(false);
            if (teamResource.status == Status.LOADING) {
                showShimmerProgress(true);
            } else {
                showShimmerProgress(false);

                if (((teamResource.status == Status.ERROR) && (teamResource.message != null)) && (viewModel.getSelectedPersonId() > 0)) {
                    Toast.makeText(this.getActivity(), teamResource.message, Toast.LENGTH_LONG).show();
                }
            }

            if (teamResource.data != null) {
                txtId.setText(String.valueOf(teamResource.data.getId()));
                txtName.setText(teamResource.data.getPerson());
                txtTeam.setText(teamResource.data.getTeam());
            }
        });
    }

    private void showShimmerProgress(Boolean show) {
        if (show) {

            if ((teamProgressShimmer.getVisibility() != View.VISIBLE)) {
                detailsCcontainer.setVisibility(View.GONE);
                teamProgressShimmer.setVisibility(View.VISIBLE);
                teamProgressShimmer.startShimmer();
            }

        } else {

            if ((teamProgressShimmer.getVisibility() != View.GONE)) {
                detailsCcontainer.setVisibility(View.VISIBLE);
                teamProgressShimmer.setVisibility(View.GONE);
                teamProgressShimmer.stopShimmer();
            }
        }
    }

}
