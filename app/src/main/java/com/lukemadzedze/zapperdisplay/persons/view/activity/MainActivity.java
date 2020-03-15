package com.lukemadzedze.zapperdisplay.persons.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.lukemadzedze.zapperdisplay.R;
import com.lukemadzedze.zapperdisplay.utils.Resource;
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

    private ProgressBar progressBar;
    private View container;
    private ShimmerFrameLayout shimmerProgress;
    private LinearLayout contentView;
    private LinearLayout feedbackView;
    private TextView feedbackText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initViewModel();
        initObservers();

        viewModel.getPersons();
    }

    private void initObservers() {
        viewModel.getPersons().observe(this, listResource -> {
            if (listResource == null) {
                return;
            }

            resetViews();

            switch (listResource.status) {
                case LOADING:
                    if (listResource.data == null || listResource.data.isEmpty()) {
                        showShimmerProgress(true);
                    } else {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    break;
                case ERROR:
                case SUCCESS:
                    showFeedbackView(listResource);
                    break;
            }
        });
    }

    private void resetViews() {
        showShimmerProgress(false);
        progressBar.setVisibility(View.GONE);
        feedbackView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
    }

    private void showFeedbackView(Resource<List<Person>> listResource) {
        if (listResource.data.isEmpty()) {
            feedbackText.setText(listResource.message != null ? listResource.message : getString(R.string.no_content_found));

            feedbackView.setVisibility(View.VISIBLE);
            contentView.setVisibility(View.GONE);
        } else {
            if (listResource.message != null) {
                Toast.makeText(this, listResource.message, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel.class);
    }

    private void initViews() {
        progressBar = findViewById(R.id.progress);
        shimmerProgress = findViewById(R.id.progress_shimmer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        feedbackView = findViewById(R.id.feedback_view);
        contentView = findViewById(R.id.content_view);
        feedbackText = findViewById(R.id.feedback_text);

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

            if ((shimmerProgress.getVisibility() != View.VISIBLE)) {
                container.setVisibility(View.GONE);
                shimmerProgress.setVisibility(View.VISIBLE);
                shimmerProgress.startShimmer();
            }

        } else {

            if ((shimmerProgress.getVisibility() != View.GONE)) {
                container.setVisibility(View.VISIBLE);
                shimmerProgress.setVisibility(View.GONE);
                shimmerProgress.stopShimmer();
            }

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
