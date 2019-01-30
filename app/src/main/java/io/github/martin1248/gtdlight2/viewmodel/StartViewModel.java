package io.github.martin1248.gtdlight2.viewmodel;

import android.app.Application;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Executor;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.github.martin1248.gtdlight2.database.AppRepository;
import io.github.martin1248.gtdlight2.database.internal.NoteEntity;
import io.github.martin1248.gtdlight2.utilities.GtdState;

public class StartViewModel extends AndroidViewModel {

    private AppRepository mRepository;

    public StartViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(application.getApplicationContext());
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllNotes() { mRepository.deleteAllNotes(); }


}
