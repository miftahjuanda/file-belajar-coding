package com.miftahjuanda.mywidgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class RandomNumbersWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);

        // Construct the RemoteViews object
        String lastUpdate = "Random: " + NumberGenerator.Generate(100);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.random_numbers_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setOnClickPendingIntent(R.id.btn_click, getPendingSelfIntent(context, appWidgetId, WIDGET_CLICK));
        appWidgetManager.updateAppWidget(appWidgetId, views);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static String WIDGET_CLICK = "widgetsclick";

    @Override
    public void onReceive(Context context, Intent intent){
        super.onReceive(context, intent);
        if (WIDGET_CLICK.equals(intent.getAction())){
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.random_numbers_widget);
            String lastUpdate = "Random: " + NumberGenerator.Generate(100);
            int appWidgetId = intent.getIntExtra(WIDGET_ID_EXTRA, 0);
            views.setTextViewText(R.id.appwidget_text, lastUpdate);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    public static String WIDGET_ID_EXTRA = "widget_id_extra";

    protected static PendingIntent getPendingSelfIntent(Context context, int appWidgetId, String action){
        Intent intent = new Intent(context, RandomNumbersWidget.class);
        intent.setAction(action);
        intent.putExtra(WIDGET_ID_EXTRA, appWidgetId);
        return PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

