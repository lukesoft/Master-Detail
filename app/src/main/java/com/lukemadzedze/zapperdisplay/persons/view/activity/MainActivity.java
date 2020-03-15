package com.lukemadzedze.zapperdisplay.persons.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.lukemadzedze.zapperdisplay.R;
import com.lukemadzedze.zapperdisplay.persons.Status;
import com.lukemadzedze.zapperdisplay.persons.data.Resource;
import com.lukemadzedze.zapperdisplay.persons.data.model.Person;
import com.lukemadzedze.zapperdisplay.persons.view.fragment.PersonDetailFragment;
import com.lukemadzedze.zapperdisplay.persons.view.fragment.PersonListFragment;
import com.lukemadzedze.zapperdisplay.persons.view.listener.PersonListClickListener;
import com.lukemadzedze.zapperdisplay.persons.viewmodel.MainViewModel;
import com.lukemadzedze.zapperdisplay.persons.viewmodel.MainViewModelFactory;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements PersonListClickListener {

    private boolean mTwoPane;
    private MainViewModel viewModel;

    @Inject
    MainViewModelFactory mainViewModelFactory;

    ProgressBar progressBar;
    View container;
    private ShimmerFrameLayout shimmerProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initViewModel();
        initObservers();

        viewModel.fetchPersons();
    }

    private void initObservers() {
        viewModel.personsLiveData().observe(this, new Observer<Resource<List<Person>>>() {
            @Override
            public void onChanged(Resource<List<Person>> listResource) {
                if (listResource.status == Status.LOADING) {
                    if (listResource.data == null) {
                        showShimmerProgress(true);
                    } else {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                } else {
                    showShimmerProgress(false);
                    progressBar.setVisibility(View.GONE);
                }

                if (listResource.status == Status.ERROR) {
                    Toast.makeText(MainActivity.this, "Error loading persons list from network", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel.class);
    }

    private void initViews() {
        progressBar = findViewById(R.id.progress);
        shimmerProgress = findViewById(R.id.progress_shimmer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        PersonListFragment fragment = new PersonListFragment();
        fragment.setListener(this);

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_list_container, fragment)
                    .commit();
            container = findViewById(R.id.two_pane_container);
        } else {
            mTwoPane = false;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_single_container, fragment)
                    .commit();

            container = findViewById(R.id.item_single_container);
        }
    }

    private void showShimmerProgress(Boolean show) {
        if (show) {
            container.setVisibility(View.GONE);
            shimmerProgress.setVisibility(View.VISIBLE);
            shimmerProgress.startShimmer();
        } else {
            container.setVisibility(View.VISIBLE);
            shimmerProgress.setVisibility(View.GONE);
            shimmerProgress.stopShimmer();
        }
    }

    @Override
    public void onPersonItemClicked(Person person) {
        Bundle arguments = new Bundle();
        arguments.putInt(PersonDetailFragment.ARG_ITEM_ID, person.getId());

        PersonDetailFragment fragment = new PersonDetailFragment();
        fragment.setArguments(arguments);

        if (mTwoPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_single_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }


    public MainViewModel getViewModel() {
        return viewModel;
    }
}
