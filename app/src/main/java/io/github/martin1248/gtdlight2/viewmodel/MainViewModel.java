package io.github.martin1248.gtdlight2.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.github.martin1248.gtdlight2.database.AppRepository;
import io.github.martin1248.gtdlight2.database.internal.NoteEntity;
import io.github.martin1248.gtdlight2.utilities.GtdState;

public class MainViewModel extends AndroidViewModel {

    public LiveData<List<NoteEntity>> mNotes;
    public List<LiveData<List<NoteEntity>>> mNotesByStates;
    public List<LiveData<List<NoteEntity>>> mNotesByContextForNextA;
    private AppRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mNotes = mRepository.mNotes;
        mNotesByStates = mRepository.mNotesByStates;
        mNotesByContextForNextA = mRepository.mNotesByContextForNextA;
    }

    public void saveNote(NoteEntity note) {
        mRepository.insertNote(note);
    }
}
