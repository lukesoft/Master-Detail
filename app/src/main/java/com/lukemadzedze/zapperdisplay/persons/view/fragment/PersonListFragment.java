package com.lukemadzedze.zapperdisplay.persons.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.lukemadzedze.zapperdisplay.R;
import com.lukemadzedze.zapperdisplay.persons.view.activity.MainActivity;
import com.lukemadzedze.zapperdisplay.persons.view.adapter.PersonsListAdapter;
import com.lukemadzedze.zapperdisplay.persons.view.listener.PersonListClickListener;
import com.lukemadzedze.zapperdisplay.persons.viewmodel.MainViewModel;

import java.util.Objects;

import dagger.android.support.DaggerFragment;


public class PersonListFragment extends DaggerFragment {
    private MainViewModel viewModel;
    private PersonListClickListener listener;
    private PersonsListAdapter adapter;

    public PersonListFragment() {
    }

    public void setListener(PersonListClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ((MainActivity) Objects.requireNonNull(getActivity())).getViewModel();

        initObservers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.item_list);

        adapter = new PersonsListAdapter(listener);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    private void initObservers() {
        viewModel.getPersons().observe(this, listResource -> {
            if (listResource == null) {
                return;
            }
            adapter.submitList(listResource.data);
        });
    }
}
