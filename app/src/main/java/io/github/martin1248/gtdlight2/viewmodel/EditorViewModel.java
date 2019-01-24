package io.github.martin1248.gtdlight2.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import io.github.martin1248.gtdlight2.database.AppRepository;
import io.github.martin1248.gtdlight2.database.internal.NoteEntity;

public class EditorViewModel extends AndroidViewModel {

    public MutableLiveData<NoteEntity> mLiveNote = new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public EditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(final int noteId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                NoteEntity note = mRepository.getNoteById(noteId);
                mLiveNote.postValue(note); // Note: 'postValue' will cause its observer to trigger the 'onChanged' method and then the result is displayed
            }
        });
    }

    public void saveNote(String noteText, int noteState) {
        NoteEntity note = mLiveNote.getValue();

        if (note == null) {
            // New note
            if (TextUtils.isEmpty(noteText.trim())) {
                return;
            }
            note = new NoteEntity(new Date(), noteText.trim(), noteState);
        } else {
            // Existing/Editing note
            note.setText(noteText.trim());
            note.setState(noteState);
        }
        mRepository.insertNote(note);
    }

    public void deleteNote() {
        mRepository.deleteNote(mLiveNote.getValue());
    }
}
