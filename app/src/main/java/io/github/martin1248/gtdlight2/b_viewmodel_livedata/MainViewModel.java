package io.github.martin1248.gtdlight2.b_viewmodel_livedata;

import android.app.Application;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.github.martin1248.gtdlight2.c_database.AppRepository;
import io.github.martin1248.gtdlight2.c_database.internal.NoteEntity;

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<List<NoteEntity>> mNotes;
    private AppRepository mRepository;
    private Executor executor;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());
        executor = mRepository.getExecutor();
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllNotes() {
        mRepository.deleteAllNotes();
    }

    // Note: Only return immutable data to ui controller
    public LiveData<List<NoteEntity>> getNotes() {
        if (mNotes == null) {
            mNotes = new MutableLiveData<List<NoteEntity>>();
        }
        return mNotes;
    }

    public void loadData(final int gtdState) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<NoteEntity> notes = mRepository.getNotesByGtdState(gtdState);
                mNotes.postValue(notes); // Note: 'postValue' will cause its observer to trigger the 'onChanged' method and then the result is displayed
            }
        });
    }

}
