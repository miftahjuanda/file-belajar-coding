package com.miftahjuanda.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.lang.ref.WeakReference;

public class OriginService extends Service implements DummyAsynCallback {
    public static final String ORIGIN_SERVICE = "OriginService";
    static final String TAG = OriginService.class.getSimpleName();

    public OriginService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        DummyAsync dummyAsync = new DummyAsync(this);
        dummyAsync.execute();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void preAsync() {
        Log.d(TAG, "preAsync: Mulai ....");
    }

    @Override
    public void postAsync() {
        Log.d(TAG, "postAsync: Selesai ....");
        stopSelf();
    }

    private static class DummyAsync extends AsyncTask<Void, Void, Void> {
        WeakReference<DummyAsynCallback> callback;
        DummyAsync(DummyAsynCallback callback) {
            this.callback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: ");
            callback.get().preAsync();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(TAG, "doInBackground: ");
            try {
                Thread.sleep(3000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute (Void avoid) {
            super.onPostExecute(avoid);
            Log.d(TAG, "onPostExecute: ");
            callback.get().postAsync();
        }
    }
}

    interface DummyAsynCallback {
        void preAsync ();
        void postAsync ();
    }

