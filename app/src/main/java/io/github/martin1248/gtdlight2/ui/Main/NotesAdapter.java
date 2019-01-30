package io.github.martin1248.gtdlight2.ui.Main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.martin1248.gtdlight2.R;
import io.github.martin1248.gtdlight2.ui.Editor.EditorActivity;
import io.github.martin1248.gtdlight2.database.internal.NoteEntity;
import io.github.martin1248.gtdlight2.ui.Main.ItemTouchHelpers.ItemTouchHelperAdapter;
import io.github.martin1248.gtdlight2.ui.Main.ItemTouchHelpers.ItemTouchHelperViewHolder;

import static io.github.martin1248.gtdlight2.utilities.Constants.NOTE_ID_KEY;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private final List<NoteEntity> mNotes;
    private final Context mContext;
    private INotesAdapterDelegate notesAdapterDelegate;

    //TODO
    private static final String[] STRINGS = new String[]{
            "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"
    };

    private final List<String> mItems = new ArrayList<>();

    public NotesAdapter(List<NoteEntity> mNotes, Context mContext) {
        this.mNotes = mNotes;
        this.mContext = mContext;
        mItems.addAll(Arrays.asList(STRINGS));
    }

    @NonNull
    @Override
    // Note: Is called each time when a view holder needs to be created and this will create an instance of the custom view holder from below
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_note, parent, false);
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
                notesAdapterDelegate.setNoteToStateDone(position);
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

    // See: https://github.com/iPaulPro/Android-ItemTouchHelper-Demo/tree/d8d85c32d579f19718b9bbb97f7a1bda0e616f1f/app/src/main/java/co/paulburke/android/itemtouchhelperdemo
    @Override
    public void onItemDismiss(int position) {
        notesAdapterDelegate.setNoteToStateTrash(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //String prev = mItems.remove(fromPosition);
        //mItems.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        //notifyItemMoved(fromPosition, toPosition);
        Log.i("GtdLight", "onItemMove: Nothing is done");
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        @BindView(R.id.note_text)
        TextView mTextView;
        @BindView(R.id.fab)
        FloatingActionButton mFab;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    public interface INotesAdapterDelegate
    {
        void setNoteToStateDone(int position);
        void setNoteToStateTrash(int position);
    }

    public void setNotesAdapterDelegate(INotesAdapterDelegate listener)
    {
        this.notesAdapterDelegate = listener;
    }
}
