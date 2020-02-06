package com.miftahjuanda.mystackwidget;

import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViewsService;

public class StackWidgetService extends RemoteViewsService {
    public StackWidgetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext());
    }
}
