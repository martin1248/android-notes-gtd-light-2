package io.github.martin1248.gtdlight2.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import io.github.martin1248.gtdlight2.widget.WidgetDataProvider;

/**
 * WidgetService is the {@link RemoteViewsService} that will return our RemoteViewsFactory
 */
public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetDataProvider(this, intent);
    }
}
