package com.szczepaniak.dawid.treningapp;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;


public class MusicPlayer extends AppCompatActivity {

    private ImageView musicIcon;
    private TextView musicAuthor;
    private TextView musicTitle;
    private TextView musicDuration;
    private TextView musicProgress;
    private SeekBar progressMusic;
    private ImageView pauseBtm;
    MediaPlayer mediaPlayer;

    final int MY_PERMISSIONS_AUDIO = 9999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ThemeListner(this, false);
        setContentView(R.layout.activity_music_player);

        setNotification("Thunder", "Imagine Dragons");

        musicIcon = findViewById(R.id.musicIcon);
        musicAuthor = findViewById(R.id.Author);
        musicTitle = findViewById(R.id.Title);
        musicDuration = findViewById(R.id.musicTime);
        musicProgress = findViewById(R.id.timer);
        progressMusic = findViewById(R.id.seekBar);
        pauseBtm =  findViewById(R.id.PauseBtm);

        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.seekTo(0);
        musicDuration.setText(createTimeLabel(mediaPlayer.getDuration()));
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        final AssetFileDescriptor afd=getResources().openRawResourceFd(R.raw.music);
        mmr.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
        byte [] data = mmr.getEmbeddedPicture();
        if(data != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            musicIcon.setImageBitmap(bitmap);
        }
        progressMusic.setMax(mediaPlayer.getDuration());
        String artist =  mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        musicAuthor.setText(artist);
        musicTitle.setText(title);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    MY_PERMISSIONS_AUDIO);

        } else {
        }


        VisualizerMusic lineBarVisualizer = findViewById(R.id.visualizer);
        lineBarVisualizer.setColor(ContextCompat.getColor(this, R.color.colorPrimary));

        lineBarVisualizer.setDensity(70);
        lineBarVisualizer.setPlayer(mediaPlayer.getAudioSessionId());

        progressMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                if(b) {
                    mediaPlayer.seekTo(i);
                    musicProgress.setText(createTimeLabel(i));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pauseBtm.setImageResource(R.drawable.ic_media_pause_dark);
        pauseBtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!mediaPlayer.isPlaying()){

                    mediaPlayer.start();
                    pauseBtm.setImageResource(R.drawable.ic_media_pause_dark);
                }else {

                    mediaPlayer.pause();
                    pauseBtm.setImageResource(R.drawable.ic_media_play_dark);

                }
            }
        });

        mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {


                return false;
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {

                while (mediaPlayer != null){

                    try{
                        Message msg =  new Message();
                        msg.what = mediaPlayer.getCurrentPosition();
                        mHandler.sendMessage(msg);
                        Thread.sleep(1000);

                    }catch (InterruptedException e){

                    }
                }
            }
        }).start();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                    this.finish();
                }
                return;
            }

        }
    }


    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            progressMusic.setProgress(msg.what);
            musicProgress.setText(createTimeLabel(msg.what));
        }
    };


    public String createTimeLabel(int time){

        String timeLabel = "";
        int min = time / 1000/ 60;
        int sec =  time / 1000 % 60;

        timeLabel = min + ":";
        if(sec < 10) timeLabel += "0";
        timeLabel += sec;
        return timeLabel;
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
                .setOngoing(true)
                .setAutoCancel(true)
                .setContentTitle(songName)
                .setContentText(author)
                .setLargeIcon(musicIcon)
                .build();


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, newMessageNotification);

        WallpaperManager wallpaperManager =  WallpaperManager.getInstance(this);


    }

}
