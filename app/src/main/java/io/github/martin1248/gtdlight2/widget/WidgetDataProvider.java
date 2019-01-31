package io.github.martin1248.gtdlight2.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.MutableLiveData;
import io.github.martin1248.gtdlight2.R;
import io.github.martin1248.gtdlight2.database.AppRepository;
import io.github.martin1248.gtdlight2.database.internal.NoteEntity;
import io.github.martin1248.gtdlight2.utilities.GtdContext;
import io.github.martin1248.gtdlight2.utilities.GtdState;

/**
 * WidgetDataProvider acts as the adapter for the collection view widget,
 * providing RemoteViews to the widget in the getViewAt method.
 */
public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "GtdLight";

    List<String> mCollection = new ArrayList<>();
    Context mContext = null;
    AppRepository mRepository;

    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
        mRepository = AppRepository.getInstance(context);
    }

    @Override
    public void onCreate() {
        mCollection.clear();
        mCollection.add("Loading notes ...");
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mCollection.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        //RemoteViews view = new RemoteViews(mContext.getPackageName(),
        //        android.R.layout.simple_list_item_1);
        //view.setTextViewText(android.R.id.text1, mCollection.get(position));

        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                R.layout.list_item_widget);
        view.setTextViewText(R.id.widgetItemTaskNameLabel, mCollection.get(position));

        return view;
    }

    //Note: Following link describes how to impl. a click listener
    // https://www.sitepoint.com/killer-way-to-show-a-list-of-items-in-android-collection-widget/

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void initData() {
        mCollection.clear();

        List<NoteEntity> notes = mRepository.getWidgetNotes(
                GtdState.states.indexOf(GtdState.NEXT_ACTIONS),
                GtdContext.contexts.indexOf(GtdContext.IMPORTANT));
        for (NoteEntity note : notes) {
            mCollection.add(note.getText());
        }

        /*
        mCollection.add(mRepository.getNoteById(1).getText());
        for (int i = 1; i <= 10; i++) {
            mCollection.add("ListView item++ " + i);
        }
        */
    }
}
