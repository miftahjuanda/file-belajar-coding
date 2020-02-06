package com.miftahjuanda.mymediaplayer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private MediaPlayer mMediaPlayer = null;
    private boolean isReady;
    private Button btnPlay;
    private Button btnStop;
    private final String TAG = MainActivity.class.getSimpleName();
    private Messenger mService = null;
    private Intent mBoundServiceIntent;
    private boolean mServiceBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = findViewById(R.id.btn_play);
        btnStop = findViewById(R.id.btn_stop);
        btnPlay.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        mBoundServiceIntent = new Intent(MainActivity.this, MediaService.class);
        mBoundServiceIntent.setAction(MediaService.ACTION_CREATE);
        startService(mBoundServiceIntent);
        bindService(mBoundServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        init();
    }

        private void init() {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            AssetFileDescriptor afd = getApplicationContext().getResources().openRawResourceFd(R.raw.guitar_background);
            try {
                mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            } catch (IOException e) {
                e.printStackTrace();
            }
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    isReady = true;
                    mMediaPlayer.start();
                }
            });
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    return false;
                }
            });
        }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_play:
                if (!mServiceBound) return;
                try {
                    mService.send(Message.obtain(null, MediaService.PLAY, 0, 0));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_stop:
                if (!mServiceBound) return;
                try {
                    mService.send(Message.obtain(null, MediaService.STOP, 0, 0));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
        @Override
        protected void onDestroy() {
            super.onDestroy();
            Log.d(TAG, "onDestroy: ");
            unbindService(mServiceConnection);
            mBoundServiceIntent.setAction(MediaService.ACTION_DESTROY);
            startService(mBoundServiceIntent);
        }


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mServiceBound = false;
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            mServiceBound = true;
        }
    };
}
