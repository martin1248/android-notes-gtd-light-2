package io.github.martin1248.gtdlight2.a_ui_controller.Main;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.martin1248.gtdlight2.R;
import io.github.martin1248.gtdlight2.a_ui_controller.Editor.EditorActivity;
import io.github.martin1248.gtdlight2.a_ui_controller.Main.NotesAdapter;
import io.github.martin1248.gtdlight2.b_viewmodel_livedata.MainViewModel;
import io.github.martin1248.gtdlight2.c_database.internal.NoteEntity;
import io.github.martin1248.gtdlight2.utilities.GtdContext;
import io.github.martin1248.gtdlight2.utilities.GtdState;

public class MainTabbedActivity extends AppCompatActivity {

    /**
     * The {@link androidx.viewpager.widget.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * androidx.fragment.app.FragmentStatePagerAdapter.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @OnClick(R.id.fab)
    void fabClickHandler() {
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tabbed);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        // Necessary for ViewPager with Visible Adjacent Pages
        mViewPager.setClipToPadding(false);
        mViewPager.setPageMargin(12);
        mViewPager.setBackgroundColor(Color.parseColor("#e0e0e0"));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        setTitle(GtdState.NEXT_ACTIONS.toString());

        for (GtdContext gtdContext: GtdContext.contexts) {
            tabLayout.addTab(tabLayout.newTab().setText(gtdContext.toString()));
        }

        ButterKnife.bind(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements NotesAdapter.ICheckButtonListener{
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_POSITION = "section_number";

        @BindView(R.id.recycler_view)
        RecyclerView mRecyclerView;

        private List<NoteEntity> notesData = new ArrayList<>();
        private NotesAdapter mAdapter;
        private MainViewModel mViewModel;
        private int mGtdContext;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_POSITION, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_tabbed, container, false);

            ButterKnife.bind(this, rootView);
            initRecyclerView();
            initViewModel();

            rootView.setBackgroundColor(Color.parseColor("#fafafa"));

            mGtdContext = getArguments().getInt(ARG_SECTION_POSITION);
            reloadData();

            return rootView;
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
                        mAdapter.setCheckButtonListener(PlaceholderFragment.this);
                        mRecyclerView.setAdapter(mAdapter);
                    } else {
                        mAdapter.notifyDataSetChanged();
                    }
                }
            };

            mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

            mViewModel.getNotes().observe(this, notesObserver);
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
            mViewModel.loadData(GtdState.states.indexOf(GtdState.NEXT_ACTIONS), mGtdContext);
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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        // Note: It would also be possible to create and REUSE fragments. See: https://medium.com/@kyroschow/how-to-use-viewpager-for-navigating-between-fragments-with-tablayout-a28b4cf92c42
        // private Fragment[] childFragments;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return GtdContext.contexts.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return GtdContext.contexts.get(position).toString();
        }

        // ViewPager with Visible Adjacent Pages
        // See: https://github.com/codepath/android_guides/wiki/ViewPager-with-FragmentPagerAdapter#dynamic-viewpager-fragments
        @Override
        public float getPageWidth (int position) {
            return 0.93f;
        }
    }
}
