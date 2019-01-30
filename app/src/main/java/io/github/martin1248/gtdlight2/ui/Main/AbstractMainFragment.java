package io.github.martin1248.gtdlight2.ui.Main;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.martin1248.gtdlight2.R;
import io.github.martin1248.gtdlight2.database.internal.NoteEntity;
import io.github.martin1248.gtdlight2.ui.Main.ItemTouchHelpers.SimpleItemTouchHelperCallback;
import io.github.martin1248.gtdlight2.utilities.GtdContext;
import io.github.martin1248.gtdlight2.utilities.GtdState;
import io.github.martin1248.gtdlight2.viewmodel.MainViewModel;

import static io.github.martin1248.gtdlight2.utilities.Constants.GTD_STATE_ID_KEY;

public class AbstractMainFragment extends Fragment implements NotesAdapter.INotesAdapterDelegate {

    public static final int VIEW_ALL_STATES = Integer.MAX_VALUE;
    private static final int VIEW_SCOPE_ALL = 0;
    private static final int VIEW_SCOPE_STATE = 1;
    private static final int VIEW_SCOPE_CONTEXT = 2;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private List<NoteEntity> notesData = new ArrayList<>();
    private NotesAdapter mAdapter;
    private MainViewModel mViewModel;
    private GtdState mGtdState;
    private int mViewScope;
    private GtdContext mGtdContext;
    private int mLayoutResource;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(mLayoutResource, container, false);

        ButterKnife.bind(this, rootView);
        initRecyclerView();

        Bundle extras = getActivity().getIntent().getExtras();
        int gtdState = extras.getInt(GTD_STATE_ID_KEY);
        if (gtdState == VIEW_ALL_STATES) {
            mViewScope = VIEW_SCOPE_ALL;
        } else {
            mViewScope = VIEW_SCOPE_STATE;
            mGtdState = GtdState.states.get(gtdState);
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModel();
    }

    private void initViewModel() {
        final Observer<List<NoteEntity>> notesObserver = new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {
                notesData.clear();
                notesData.addAll(noteEntities);

                Log.i("GtdLight", "onChanged: observed notes changed. state=" + mGtdState + " returned=" + noteEntities.size());

                if (mAdapter == null) {
                    // Note: Also getActivity() is instead possible. See https://stackoverflow.com/questions/32227146/what-is-different-between-getcontext-and-getactivity-from-fragment-in-support-li/32227421
                    mAdapter = new NotesAdapter(notesData, getContext());
                    mAdapter.setNotesAdapterDelegate(AbstractMainFragment.this);
                    mRecyclerView.setAdapter(mAdapter);
                    // For ItemTouchHelper
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                } else {
                    mAdapter.notifyDataSetChanged();
                }

                ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
                mItemTouchHelper = new ItemTouchHelper(callback);
                mItemTouchHelper.attachToRecyclerView(mRecyclerView);
            }
        };

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        if (mViewScope == VIEW_SCOPE_CONTEXT) {
            mViewModel.mNotesByContextForNextA.get(GtdContext.contexts.indexOf(mGtdContext)).observe(this, notesObserver);
        } else if (mViewScope == VIEW_SCOPE_STATE) {
            mViewModel.mNotesByStates.get(GtdState.states.indexOf(mGtdState)).observe(this, notesObserver);
        } else {
            mViewModel.mNotes.observe(this, notesObserver);
        }
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(
                mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
    }

    @Override
    public void setNoteToStateDone(int position) {
        NoteEntity note = notesData.get(position);
        note.setState(GtdState.states.indexOf(GtdState.DONE));
        mViewModel.saveNote(note);
    }

    public void setGtdContext(GtdContext mGtdContext) {
        mViewScope = VIEW_SCOPE_CONTEXT;
        this.mGtdContext = mGtdContext;
    }

    public void setLayoutResource(int layoutResource) {
        this.mLayoutResource = layoutResource;
    }
}
