package io.github.martin1248.gtdlight2.a_ui_controller.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import io.github.martin1248.gtdlight2.a_ui_controller.widget.WidgetDataProvider;

/**
 * WidgetService is the {@link RemoteViewsService} that will return our RemoteViewsFactory
 */
public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetDataProvider(this, intent);
    }
}
