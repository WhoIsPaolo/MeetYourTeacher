package com.example.meetyourteacher;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoTrailer extends AppCompatActivity
{
    private VideoView videoView;

    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_trailer);

        //REPRODUCCION DE TRAILER
        videoView = findViewById(R.id.videoView);
        Uri path = Uri.parse("android.resource://com.example.meetyourteacher/" + R.raw.trailer);
        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);
        videoView.setVideoURI(path);
        videoView.start();
        //FIN REPRODUCCION TRAILER
    }


}
