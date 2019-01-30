package io.github.martin1248.gtdlight2;

import android.content.Context;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import io.github.martin1248.gtdlight2.database.internal.AppDatabase;
import io.github.martin1248.gtdlight2.database.internal.NoteDao;
import io.github.martin1248.gtdlight2.database.internal.NoteEntity;
import io.github.martin1248.gtdlight2.utilities.SampleData;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    public static final String TAG = "Junit";
    private AppDatabase mDb;
    private NoteDao mDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mDao = mDb.noteDao();
        Log.i(TAG, "createDb");
    }

    @After
    public void closeDb() {
        mDb.close();
        Log.i(TAG, "closeDb");
    }

    @Test
    public void createAndRetrieveNotes() {
        mDao.insertAll(SampleData.getNotes());
        int count = mDao.getCount();
        Log.i(TAG, "createAndRetrieveNotes: count = " + count);
        assertEquals(SampleData.getNotes().size(), count);
    }

    @Test
    public void compareStrings() {
        mDao.insertAll(SampleData.getNotes());
        NoteEntity original = SampleData.getNotes().get(0);
        NoteEntity fromDb = mDao.getNoteById(1);
        assertEquals(original.getText(), fromDb.getText());
        assertEquals(1,fromDb.getId());
    }
}
