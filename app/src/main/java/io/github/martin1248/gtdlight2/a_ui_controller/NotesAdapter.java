package io.github.martin1248.gtdlight2.a_ui_controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.martin1248.gtdlight2.R;
import io.github.martin1248.gtdlight2.c_database.AppRepository;
import io.github.martin1248.gtdlight2.c_database.internal.NoteEntity;
import io.github.martin1248.gtdlight2.utilities.GtdState;

import static io.github.martin1248.gtdlight2.utilities.Constants.NOTE_ID_KEY;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private final List<NoteEntity> mNotes;
    private final Context mContext;
    private AppRepository mRepository;

    public NotesAdapter(List<NoteEntity> mNotes, Context mContext) {
        this.mNotes = mNotes;
        this.mContext = mContext;
        this.mRepository = AppRepository.getInstance(mContext);
    }

    @NonNull
    @Override
    // Note: Is called each time when a view holder needs to be created and this will create an instance of the custom view holder from below
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.note_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    // Is called each time when I want to update the display of a list item
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NoteEntity note = mNotes.get(position);
        holder.mTextView.setText(note.getText());

        holder.mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note.setState(GtdState.states.indexOf(GtdState.DONE));
                mRepository.insertNote(note);
                // I'm not sure yet if it is a good practice to expose Activity.reloadData to Adapter
                ((MainActivity)mContext).reloadData();
            }
        });

        // Note: From https://stackoverflow.com/questions/24885223/why-doesnt-recyclerview-have-onitemclicklistener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditorActivity.class);
                intent.putExtra(NOTE_ID_KEY, note.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.note_text)
        TextView mTextView;
        @BindView(R.id.fab)
        FloatingActionButton mFab;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
