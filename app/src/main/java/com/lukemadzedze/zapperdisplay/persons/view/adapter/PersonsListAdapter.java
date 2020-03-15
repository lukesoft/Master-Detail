package com.lukemadzedze.zapperdisplay.persons.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.lukemadzedze.zapperdisplay.R;
import com.lukemadzedze.zapperdisplay.persons.data.model.Person;
import com.lukemadzedze.zapperdisplay.persons.view.listener.PersonListClickListener;

public class PersonsListAdapter extends ListAdapter<Person, PersonsListAdapter.PersonViewHolder> {
    private PersonListClickListener listener;

    public PersonsListAdapter(PersonListClickListener listener) {
        super(DIFF_CALLBACK);

        this.listener = listener;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_person, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        holder.bindTo(getItem(position));
    }

    class PersonViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView name;
        private View itemView;

        PersonViewHolder(final View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            this.itemView =itemView;
        }

        void bindTo(final Person person) {
            id.setText(String.valueOf(person.getId()));
            name.setText(person.getName());
            this.itemView.setOnClickListener(v -> listener.onPersonItemClicked(person));
        }
    }

    private static final DiffUtil.ItemCallback<Person> DIFF_CALLBACK = new DiffUtil.ItemCallback<Person>() {
        @Override
        public boolean areItemsTheSame(@NonNull Person oldPerson, @NonNull Person newPerson) {
            return oldPerson.getId() == newPerson.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Person oldUser, @NonNull Person newUser) {
            return oldUser.equals(newUser);
        }
    };
}
