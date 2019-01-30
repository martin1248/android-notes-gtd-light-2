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

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private List<NoteEntity> notesData = new ArrayList<>();
    private NotesAdapter mAdapter;
    private MainViewModel mViewModel;
    private GtdState mGtdState;
    private boolean mIsGtdContextAware;
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
        mGtdState = GtdState.states.get(extras.getInt(GTD_STATE_ID_KEY));

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
        mViewModel.mNotesByStates.get(GtdState.states.indexOf(mGtdState)).observe(this, notesObserver);
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
        Log.i("GtdLight", "setNoteToStateDone: TODO");
        /*
        New
        NoteEntity note =  notesData.get(position);
        mViewModel setToDone(note)

                Current
        mViewModel.setNoteToDone(position);;*/
    }

    public void setIsGtdContextAware(boolean mIsGtdContextAware) {
        this.mIsGtdContextAware = mIsGtdContextAware;
    }

    public void setGtdContext(GtdContext mGtdContext) {
        this.mGtdContext = mGtdContext;
    }

    public void setLayoutResource(int layoutResource) {
        this.mLayoutResource = layoutResource;
    }
}
