package io.github.martin1248.gtdlight2.ui.Start;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import io.github.martin1248.gtdlight2.ui.Main.AbstractMainFragment;
import io.github.martin1248.gtdlight2.ui.Main.MainFragActivity;
import io.github.martin1248.gtdlight2.ui.Main.MainTabbedActivity;
import io.github.martin1248.gtdlight2.utilities.GtdState;

import static io.github.martin1248.gtdlight2.utilities.Constants.GTD_STATE_ID_KEY;

public class GtdStatesAdapter extends RecyclerView.Adapter<GtdStatesAdapter.ViewHolder> {

    private final List<GtdState> mGtdStates;
    private final Context mContext;

    public GtdStatesAdapter(List<GtdState> mNotes, Context mContext) {
        this.mGtdStates = mNotes;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public GtdStatesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_gtd_state, parent, false);
        return new GtdStatesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GtdStatesAdapter.ViewHolder holder, int position) {
        if (position == mGtdStates.size()) {
            holder.mTextView.setText("All");

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MainFragActivity.class);
                    intent.putExtra(GTD_STATE_ID_KEY, AbstractMainFragment.VIEW_ALL_STATES);
                    mContext.startActivity(intent);
                }
            });
        } else {
            final GtdState gtdState = mGtdStates.get(position);
            holder.mTextView.setText(gtdState.toString());
            holder.mFab.setBackgroundColor(Color.parseColor(gtdState.getColor()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    if (GtdState.NEXT_ACTIONS == gtdState) {
                        intent = new Intent(mContext, MainTabbedActivity.class);
                    } else {
                        intent = new Intent(mContext, MainFragActivity.class);
                    }
                    intent.putExtra(GTD_STATE_ID_KEY, GtdState.states.indexOf(gtdState));
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mGtdStates.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.gtd_state_text)
        TextView mTextView;
        @BindView(R.id.fab_color)
        FloatingActionButton mFab;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
