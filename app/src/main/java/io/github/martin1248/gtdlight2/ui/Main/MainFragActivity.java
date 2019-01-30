package io.github.martin1248.gtdlight2.ui.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.martin1248.gtdlight2.R;
import io.github.martin1248.gtdlight2.ui.Editor.EditorActivity;
import io.github.martin1248.gtdlight2.utilities.GtdState;

import static io.github.martin1248.gtdlight2.utilities.Constants.GTD_STATE_ID_KEY;

public class MainFragActivity extends AppCompatActivity {

    @OnClick(R.id.fab)
    void fabClickHandler() {
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_frag);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, MainFragFragment.newInstance())
                    .commitNow();
        }

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        int gtdState = extras.getInt(GTD_STATE_ID_KEY);
        if (gtdState == AbstractMainFragment.VIEW_ALL_STATES) {
            setTitle("ALL");
        } else {
            setTitle(GtdState.states.get(gtdState).toString());
        }
    }
}
