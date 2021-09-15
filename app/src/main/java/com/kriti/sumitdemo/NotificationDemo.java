package com.kriti.sumitdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NotificationDemo extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_demo);
        button = findViewById(R.id.notificationButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder;

                if (Build.VERSION.SDK_INT >= 26)
                {
                    NotificationChannel channel = new NotificationChannel("1", "myChannel",
                            NotificationManager.IMPORTANCE_DEFAULT);
                    manager.createNotificationChannel(channel);
                    builder = new NotificationCompat.Builder(NotificationDemo.this, "1");
                }
                else
                {
                    builder = new NotificationCompat.Builder(NotificationDemo.this);
                }

                builder.setSmallIcon(R.drawable.ic_launcher_foreground);
                builder.setContentTitle("Alert!");
                builder.setContentText("This is a demo notification");

                Notification notification = builder.build();
                manager.notify(1, notification);
            }
        });
    }
}