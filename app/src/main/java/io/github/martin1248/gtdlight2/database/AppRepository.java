package io.github.martin1248.gtdlight2.database;

import java.util.List;

import io.github.martin1248.gtdlight2.utilities.SampleData;

public class AppRepository {
    private static final AppRepository ourInstance = new AppRepository();

    public List<NoteEntity> mNotes;

    public static AppRepository getInstance() {
        return ourInstance;
    }

    private AppRepository() {
        mNotes  = SampleData.getNotes();
    }
}
