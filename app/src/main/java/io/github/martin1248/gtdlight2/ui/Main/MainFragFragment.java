package io.github.martin1248.gtdlight2.ui.Main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.martin1248.gtdlight2.R;
import io.github.martin1248.gtdlight2.utilities.GtdContext;
import io.github.martin1248.gtdlight2.viewmodel.MainViewModel;
import io.github.martin1248.gtdlight2.database.internal.NoteEntity;
import io.github.martin1248.gtdlight2.utilities.GtdState;

import static io.github.martin1248.gtdlight2.utilities.Constants.GTD_STATE_ID_KEY;

public class MainFragFragment extends Fragment implements NotesAdapter.ICheckButtonListener {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private List<NoteEntity> notesData = new ArrayList<>();
    private NotesAdapter mAdapter;
    private MainViewModel mViewModel;
    private GtdState mGtdState;

    public static MainFragFragment newInstance() {
        return new MainFragFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_frag, container, false);

        ButterKnife.bind(this, rootView);
        initRecyclerView();
        initViewModel();

        reloadData();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    private void initViewModel() {

        final Observer<List<NoteEntity>> notesObserver = new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {
                notesData.clear();
                notesData.addAll(noteEntities);

                if (mAdapter == null) {
                    // Note: Also getActivity() is instead possible. See https://stackoverflow.com/questions/32227146/what-is-different-between-getcontext-and-getactivity-from-fragment-in-support-li/32227421
                    mAdapter = new NotesAdapter(notesData, getContext());
                    mAdapter.setCheckButtonListener(MainFragFragment.this);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        };

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mViewModel.getNotes().observe(this, notesObserver);

        Bundle extras = getActivity().getIntent().getExtras();
        mGtdState = GtdState.states.get(extras.getInt(GTD_STATE_ID_KEY));
        // reloadData(); is done afterwards by onResume()
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(
                mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
    }

    public void reloadData() {
        mViewModel.loadData(GtdState.states.indexOf(mGtdState));
    }

    @Override
    public void onCheckButtonClickListener(int position) {
        mViewModel.setNoteToDone(position);
        reloadData();
        //mAdapter.notifyDataSetChanged(); // This is a good practice but recyclerview is updated by reloadData already
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        reloadData();
    }

}
