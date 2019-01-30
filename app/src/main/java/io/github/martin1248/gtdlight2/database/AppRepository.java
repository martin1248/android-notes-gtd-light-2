package io.github.martin1248.gtdlight2.database;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import io.github.martin1248.gtdlight2.database.internal.AppDatabase;
import io.github.martin1248.gtdlight2.database.internal.NoteEntity;
import io.github.martin1248.gtdlight2.utilities.GtdState;
import io.github.martin1248.gtdlight2.utilities.SampleData;

public class AppRepository {
    private static AppRepository ourInstance;

    public List<LiveData<List<NoteEntity>>> mNotesByStates;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);

        mNotesByStates = new ArrayList<>();
        for (GtdState state : GtdState.states) {
            mNotesByStates.add(getAllNotesWithState(GtdState.states.indexOf(state)));
        }
    }

    public void addSampleData() {
        executor.execute(() -> mDb.noteDao().insertAll(SampleData.getNotes()));
    }

    private LiveData<List<NoteEntity>> getAllNotes() {
        return mDb.noteDao().getAll();
    }

    private LiveData<List<NoteEntity>> getAllNotesWithState(int state) {
        return mDb.noteDao().getAllWithState(state);
    }

    public void deleteAllNotes() {
        executor.execute(() -> mDb.noteDao().deleteAll());
    }

    public NoteEntity getNoteById(int noteId) {
        return mDb.noteDao().getNoteById(noteId);
    }

    public void insertNote(NoteEntity note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDao().insertNote(note);
            }
        });
    }

    public void deleteNote(NoteEntity note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDao().deleteNote(note);
            }
        });
    }

    public Executor getExecutor() {
        return executor;
    }

}
