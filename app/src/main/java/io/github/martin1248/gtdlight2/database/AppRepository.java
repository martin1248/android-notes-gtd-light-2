package io.github.martin1248.gtdlight2.database;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import io.github.martin1248.gtdlight2.utilities.SampleData;

public class AppRepository {
    private static AppRepository ourInstance;

    public LiveData<List<NoteEntity>> mNotes;
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
        mNotes  = getAllNotes();
    }

    public void addSampleData() {
        executor.execute(() -> mDb.noteDao().insertAll(SampleData.getNotes()));
    }

    private LiveData<List<NoteEntity>> getAllNotes() {
        return mDb.noteDao().getAll();
    }
}
