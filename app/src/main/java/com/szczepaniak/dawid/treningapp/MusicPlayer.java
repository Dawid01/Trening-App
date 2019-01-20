package com.szczepaniak.dawid.treningapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.media.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;


public class MusicPlayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ThemeListner(this, false);
        setContentView(R.layout.activity_music_player);

        setNotification("Thunder", "Imagine Dragons");


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }


    public void setNotification(String songName, String author){

        RemoteViews notificationView = new RemoteViews(getPackageName(), R.layout.music_player);
        notificationView.setImageViewResource(R.id.MusicIcon, R.mipmap.thunder);
        notificationView.setImageViewResource(R.id.next, R.drawable.next);
        notificationView.setImageViewResource(R.id.back, R.drawable.next);
        notificationView.setImageViewResource(R.id.PauseBtm, R.drawable.pause_icon);
        notificationView.setTextViewText(R.id.MusicTitle, songName);
        notificationView.setTextViewText(R.id.MusicAuthor, author);

        RemoteViews notificationViewMini = new RemoteViews(getPackageName(), R.layout.music_player_mini);
        notificationViewMini.setImageViewResource(R.id.MusicIcon, R.mipmap.thunder);
        notificationViewMini.setImageViewResource(R.id.next, R.drawable.next);
        notificationViewMini.setImageViewResource(R.id.back, R.drawable.next);
        notificationViewMini.setImageViewResource(R.id.PauseBtm, R.drawable.pause_icon);
        notificationViewMini.setTextViewText(R.id.MusicTitle, songName);
        notificationViewMini.setTextViewText(R.id.MusicAuthor, author);


        Bitmap musicIcon = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.thunder);

        Notification newMessageNotification =  new android.support.v4.app.NotificationCompat.Builder(MusicPlayer.this, "Music")
                .setSmallIcon(R.drawable.musicicon)
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MusicPlayer.class), 0))
                .setCustomBigContentView(notificationView)
                .setCustomContentView(notificationViewMini)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setOngoing(false)
                .setAutoCancel(true)
                .setContentTitle(songName)
                .setContentText(author)
                .setLargeIcon(musicIcon)
                .build();


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, newMessageNotification);

        //WallpaperManager.getInstance(this).setBitmap(musicIcon, null, true, WallpaperManager.FLAG_LOCK);


    }

}
