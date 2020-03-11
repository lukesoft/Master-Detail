package com.lukemadzedze.zapperdisplay.persons.view.activity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.lukemadzedze.zapperdisplay.R;
import com.lukemadzedze.zapperdisplay.persons.view.fragment.PersonListFragment;

public class MainActivity extends AppCompatActivity {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        PersonListFragment fragment = new PersonListFragment();

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_list_container, fragment)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_single_container, fragment)
                    .commit();
        }
    }

    public boolean isTwoPane() {
        return mTwoPane;
    }
}
