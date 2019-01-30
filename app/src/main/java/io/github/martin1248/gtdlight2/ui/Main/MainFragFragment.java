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

public class MainFragFragment extends AbstractMainFragment {

    public static MainFragFragment newInstance() {
        return new MainFragFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setLayoutResource(R.layout.fragment_main_frag);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
