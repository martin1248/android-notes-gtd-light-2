package io.github.martin1248.gtdlight2.c_database.internal;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


// Note: To see what Room generates search for "AppDatabase_Impl.java"
@Database(entities = {NoteEntity.class}, version = 3)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "AppDatabase.db";
    private static volatile AppDatabase instance; // Note 'volatile': Will always be stored in main memory
    private static final Object LOCK = new Object();

    public abstract NoteDao noteDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
                }
            }
        }

        return instance;
    }
}
