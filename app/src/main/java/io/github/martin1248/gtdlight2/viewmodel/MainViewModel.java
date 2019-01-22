package io.github.martin1248.gtdlight2.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import io.github.martin1248.gtdlight2.database.AppRepository;
import io.github.martin1248.gtdlight2.database.NoteEntity;
import io.github.martin1248.gtdlight2.utilities.SampleData;

public class MainViewModel extends AndroidViewModel {

    public List<NoteEntity> mNotes;
    private AppRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mNotes = mRepository.mNotes;
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }
}
