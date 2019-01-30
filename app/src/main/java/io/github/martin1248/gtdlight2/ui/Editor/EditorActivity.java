package io.github.martin1248.gtdlight2.ui.Editor;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import io.github.martin1248.gtdlight2.R;
import io.github.martin1248.gtdlight2.database.internal.NoteEntity;
import io.github.martin1248.gtdlight2.utilities.GtdContext;
import io.github.martin1248.gtdlight2.utilities.GtdState;
import io.github.martin1248.gtdlight2.viewmodel.EditorViewModel;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static io.github.martin1248.gtdlight2.utilities.Constants.EDITING_KEY;
import static io.github.martin1248.gtdlight2.utilities.Constants.NOTE_ID_KEY;


public class EditorActivity extends AppCompatActivity {

    @BindView(R.id.note_text)
    TextView mTextView;
    @BindView(R.id.note_gtd_state)
    Spinner mSpinnerGtdState;
    @BindView(R.id.textViewContext)
    TextView mTextViewGtdContext;
    @BindView(R.id.note_gtd_context)
    Spinner mSpinnerGtdContext;


    private EditorViewModel mViewModel;
    private boolean mNewNote, mEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            mEditing = savedInstanceState.getBoolean(EDITING_KEY);
        }

        initViewModel();

        ArrayAdapter<String> adapterGtdState = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, GtdState.getStatesAsStrings());
        adapterGtdState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerGtdState.setAdapter(adapterGtdState);

        ArrayAdapter<String> adapterGtdContext = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, GtdContext.getContextsAsStrings());
        adapterGtdContext.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerGtdContext.setAdapter(adapterGtdContext);

        mSpinnerGtdState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (GtdState.states.get(position) == GtdState.NEXT_ACTIONS) {
                    mTextViewGtdContext.setVisibility(View.VISIBLE);
                    mSpinnerGtdContext.setVisibility(View.VISIBLE);
                } else {
                    mTextViewGtdContext.setVisibility(View.INVISIBLE);
                    mSpinnerGtdContext.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Nothing to do
            }

        });
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(EditorViewModel.class);

        mViewModel.mLiveNote.observe(this, new Observer<NoteEntity>() {
            @Override
            public void onChanged(@Nullable NoteEntity noteEntity) {
                if (noteEntity != null && !mEditing) {
                    mTextView.setText(noteEntity.getText());
                    mSpinnerGtdState.setSelection(noteEntity.getState());
                    mSpinnerGtdContext.setSelection(noteEntity.getContext());
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle(getString(R.string.new_note));
            mNewNote = true;
        } else {
            setTitle(getString(R.string.edit_note));
            int noteId = extras.getInt(NOTE_ID_KEY);
            mViewModel.loadData(noteId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNewNote) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_editor, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            saveAndReturn();
            return true;
        } else if (item.getItemId() == R.id.action_delete) {
            mViewModel.deleteNote();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        mViewModel.saveNote(mTextView.getText().toString(), mSpinnerGtdState.getSelectedItemPosition(), mSpinnerGtdContext.getSelectedItemPosition());
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }

}
