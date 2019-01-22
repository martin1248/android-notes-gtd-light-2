package io.github.martin1248.gtdlight2.database;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.github.martin1248.gtdlight2.utilities.SampleData;

public class AppRepository {
    private static AppRepository ourInstance;

    public List<NoteEntity> mNotes;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {
        mNotes  = SampleData.getNotes();
        mDb = AppDatabase.getInstance(context);
    }

    public void addSampleData() {
        Log.i("GtdLight", "AppRepository.addSampleData");
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDao().insertAll(SampleData.getNotes());
            }
        });
    }
}
