package io.github.martin1248.gtdlight2;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.martin1248.gtdlight2.database.NoteEntity;
import io.github.martin1248.gtdlight2.viewmodel.EditorViewModel;

import android.view.View;
import android.widget.TextView;

import static io.github.martin1248.gtdlight2.utilities.Constants.NOTE_ID_KEY;

public class EditorActivity extends AppCompatActivity {

    @BindView(R.id.note_text)
    TextView mTextview;

    private EditorViewModel mViewModel;
    private boolean mNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        initViewModel();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);
        mViewModel.mLiveNote.observe(this, new Observer<NoteEntity>() {
            @Override
            public void onChanged(NoteEntity noteEntity) {
                mTextview.setText(noteEntity.getText());
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            // brand new note
            setTitle("New note");
            mNewNote = true;
        } else {
            setTitle("Edit note");
            int noteId = extras.getInt(NOTE_ID_KEY);
            mViewModel.loadData(noteId);
        }
    }
}
