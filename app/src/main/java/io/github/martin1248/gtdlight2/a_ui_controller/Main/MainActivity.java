package io.github.martin1248.gtdlight2.a_ui_controller.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.martin1248.gtdlight2.R;
import io.github.martin1248.gtdlight2.a_ui_controller.Editor.EditorActivity;
import io.github.martin1248.gtdlight2.c_database.internal.NoteEntity;
import io.github.martin1248.gtdlight2.utilities.GtdState;
import io.github.martin1248.gtdlight2.b_viewmodel_livedata.MainViewModel;

import static io.github.martin1248.gtdlight2.utilities.Constants.GTD_STATE_ID_KEY;

public class MainActivity extends AppCompatActivity implements NotesAdapter.ICheckButtonListener{

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @OnClick(R.id.fab)
    void fabClickHandler() {
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }

    private List<NoteEntity> notesData = new ArrayList<>();
    private NotesAdapter mAdapter;
    private MainViewModel mViewModel;
    private GtdState mGtdState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }

    private void initViewModel() {

        final Observer<List<NoteEntity>> notesObserver = new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {
                notesData.clear();
                notesData.addAll(noteEntities);

                if (mAdapter == null) {
                    mAdapter = new NotesAdapter(notesData, MainActivity.this);
                    mAdapter.setCheckButtonListener(MainActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        };

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mViewModel.getNotes().observe(this, notesObserver);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            throw new IllegalStateException("Missing GtdState in MainActivity!");
        } else {
            int gtdState = extras.getInt(GTD_STATE_ID_KEY);
            mGtdState = GtdState.states.get(gtdState);
            setTitle(mGtdState.toString());
            // reloadData(); is done afterwards by onResume()
        }
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(
                mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
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

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        reloadData();
    }

    private void deleteAllNotes() {
        mViewModel.deleteAllNotes();
    }

    private void addSampleData() {
        mViewModel.addSampleData();
    }

    public void reloadData() {
        mViewModel.loadData(GtdState.states.indexOf(mGtdState));
    }

    @Override
    public void onCheckButtonClickListener(int position) {
        mViewModel.setNoteToDone(position);
        reloadData();
        //mAdapter.notifyDataSetChanged(); // This is a good practice but recyclerview is updated by reloadData already
    }
}
