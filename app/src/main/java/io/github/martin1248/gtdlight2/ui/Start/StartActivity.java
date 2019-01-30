package io.github.martin1248.gtdlight2.ui.Start;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.martin1248.gtdlight2.R;
import io.github.martin1248.gtdlight2.ui.Editor.EditorActivity;
import io.github.martin1248.gtdlight2.utilities.GtdState;

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @OnClick(R.id.fab)
    void fabClickHandler() {
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }

    private GtdStatesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(
                mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);

        mAdapter = new GtdStatesAdapter(GtdState.states, StartActivity.this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
