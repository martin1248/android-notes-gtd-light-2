package io.github.martin1248.gtdlight2.viewmodel;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

/*    public String exportAllNotes() {
        List<NoteEntity> notes = mRepository.mNotes.getValue();

        String message = "Uppsss!";

        try {
            File sdCardDir = Environment.getExternalStorageDirectory();
            String filename = "MyGtdBackUp.csv";
            // the name of the file to export with
            File saveFile = new File(sdCardDir, filename);
            FileWriter fw = new FileWriter(saveFile);

            BufferedWriter bw = new BufferedWriter(fw);

            if (notes.size() > 0) {
                bw.write("ID,DATE,TEXT,STATE,CONTEXT,LISTORDER");
                bw.newLine();

                for (NoteEntity note: notes ) {
                    bw.write(note.getId() + ",");
                    bw.write(note.getDate() + ",");
                    bw.write(note.getText() + ",");
                    bw.write(note.getState() + ",");
                    bw.write(note.getContext() + ",");
                    bw.write(note.getListOrder());
                    bw.newLine();
                }
                bw.flush();
                message = "Exported Successfully to " + saveFile.getAbsolutePath();
            }
            message = "Nothing to export";
        } catch (Exception ex) {
            message = ex.getMessage().toString();
        }

        return message;
    }*/

    public String exportAllNotes() {
        List<NoteEntity> notes = mRepository.mNotes.getValue();

        if (notes.isEmpty()) {
            return "No notes found";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("ID,DATE,TEXT,STATE,CONTEXT,LISTORDER\n");

        for (NoteEntity note: notes ) {
            builder.append(note.getId() + ",");
            builder.append(note.getDate() + ",");
            builder.append(note.getText() + ",");
            builder.append(note.getState() + ",");
            builder.append(note.getContext() + ",");
            builder.append(note.getListOrder() + "\n");
        }

        return builder.toString();
    }


}
