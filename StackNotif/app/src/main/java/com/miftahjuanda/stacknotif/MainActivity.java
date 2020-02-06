package com.miftahjuanda.stacknotif;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private EditText edtSender;
    private EditText edtMessage;

    private int idNotification = 0;
    private final List<NotificationItem> stackNotif = new ArrayList<>();

    private static final CharSequence CHANNEL_NAME = "dicoding channel";
    private final static String GROUP_KEY_EMAILS = "group_key_emails";
    private final static int NOTIFICATION_REQUEST_CODE = 200;
    private static final int MAX_NOTIFICATION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtSender = findViewById(R.id.edtSender);
        edtMessage = findViewById(R.id.edtMessage);
        Button btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sender = edtSender.getText().toString();
                String message = edtMessage.getText().toString();
                if (sender.isEmpty() || message.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Data Harus Diisi", Toast.LENGTH_SHORT).show();
                }else {
                    stackNotif.add(new NotificationItem(idNotification, sender, message));
                    sendNotif();
                    idNotification++;
                    edtSender.setText("");
                    edtMessage.setText("");
                    InputMethodManager methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    methodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        stackNotif.clear();
        idNotification = 0;
    }

    private void sendNotif() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.round_notifications_white_24dp);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder;

        String CHANNEL_ID = "channel_01";
        if (idNotification < MAX_NOTIFICATION) {
            mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("New Email from" + stackNotif.get(idNotification).getSender())
                    .setContentText(stackNotif.get(idNotification).getMessage())
                    .setSmallIcon(R.drawable.round_mail_white_48dp)
                    .setLargeIcon(largeIcon)
                    .setGroup(GROUP_KEY_EMAILS)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        }else {
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                    .addLine("New Email from " + stackNotif.get(idNotification).getSender())
                    .setBigContentTitle(idNotification + " new emails")
                    .setSummaryText("mail@dicoding");
            mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle(idNotification + " new emails")
                    .setContentText("mail@dicoding.com")
                    .setSmallIcon(R.drawable.round_mail_white_48dp)
                    .setGroup(GROUP_KEY_EMAILS)
                    .setGroupSummary(true)
                    .setContentIntent(pendingIntent)
                    .setStyle(inboxStyle)
                    .setAutoCancel(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

            mBuilder.setChannelId(CHANNEL_ID);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = mBuilder.build();

        if (mNotificationManager != null) {
            mNotificationManager.notify(idNotification, notification);
        }
    }
}
