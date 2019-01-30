package io.github.martin1248.gtdlight2.a_ui_controller.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.martin1248.gtdlight2.R;
import io.github.martin1248.gtdlight2.a_ui_controller.Editor.EditorActivity;
import io.github.martin1248.gtdlight2.utilities.GtdContext;
import io.github.martin1248.gtdlight2.utilities.GtdState;

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

        setTitle("Main Frag Activity");
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        else if (id == R.id.action_add_sample_data) {
            addSampleData();
            reloadData();
            return true;
        } else if (id == R.id.action_delete_all) {
            deleteAllNotes();
            reloadData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllNotes() {
        //mViewModel.deleteAllNotes();
    }

    private void addSampleData() {
        //mViewModel.addSampleData();
    }

    public void reloadData() {
        //mViewModel.loadData(GtdState.states.indexOf(mGtdState));
    }
}
