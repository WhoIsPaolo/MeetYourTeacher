package com.example.meetyourteacher;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtc.video.VideoEncoderConfiguration;

import static io.agora.rtc.video.VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15;
import static io.agora.rtc.video.VideoEncoderConfiguration.STANDARD_BITRATE;

public class VideoCall extends AppCompatActivity
{
    private RtcEngine mRtcEngine;
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videocall);

        mRtcEventHandler = new IRtcEngineEventHandler()
        {
            @Override
            public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed)
            {
                Toast.makeText(VideoCall.this, "RtcEventHandler Activated", Toast.LENGTH_SHORT).show();
                Log.i("uid video",uid+"");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupRemoteVideo(uid);
                    }
                });
            }

        };


        initializeAgoraEngine();
        joinChannel();
        mRtcEngine.enableVideo();
        setupVideoProfile();
        setupLocalVideo();

    }

    private IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler()
    {

        @Override
        public void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed)
        {
            setupRemoteVideo(uid);
            //super.onFirstRemoteVideoDecoded(uid, width, height, elapsed);
        }

        @Override
        public void onUserJoined(int uid, int elapsed)
        {
            super.onUserJoined(uid, elapsed);
        }
    };

    private void initializeAgoraEngine()
    {
        Toast.makeText(this, "1. RtcEngine Initialized", Toast.LENGTH_SHORT).show();
        try
        {
            mRtcEngine = RtcEngine.create(getBaseContext(), getString(R.string.private_app_id), mRtcEventHandler);
            mRtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_COMMUNICATION);
        }

        catch (Exception e)
        {
            Log.e("E. Initialize Rtc", Log.getStackTraceString(e));
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }

    int uid = new Random().nextInt(10000000)+1;
    private void joinChannel() {

        String channelName = "MYT_Channel";
        mRtcEngine.joinChannel("token", channelName, "Extra Optional Data",
               uid); // if you do not specify the uid, Agora will assign one.
        Toast.makeText(this, "2. Channel Allowed: " + channelName, Toast.LENGTH_SHORT).show();
    }

    private void setupLocalVideo()
    {
        FrameLayout container = findViewById(R.id.local_video_view_container);
        SurfaceView surfaceView = RtcEngine.CreateRendererView(getBaseContext());
        surfaceView.setZOrderMediaOverlay(true);
        container.addView(surfaceView);
        mRtcEngine.setupLocalVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, uid));
    }

    private void setupVideoProfile()
    {
        mRtcEngine.enableAudio();
        //mRtcEngine.setVideoProfile(Constants.VIDEO_PROFILE_360P, false);

        VideoEncoderConfiguration.ORIENTATION_MODE
                orientationMode = VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT;
        VideoEncoderConfiguration.VideoDimensions dimensions = new VideoEncoderConfiguration.VideoDimensions(360, 640);
        VideoEncoderConfiguration videoEncoderConfiguration = new VideoEncoderConfiguration(dimensions, FRAME_RATE_FPS_15, STANDARD_BITRATE, orientationMode);

        mRtcEngine.setVideoEncoderConfiguration(videoEncoderConfiguration);
    }

    private void setupRemoteVideo(int uid) {
        FrameLayout container = findViewById(R.id.remote_video_view_container);

        Toast.makeText(this, "Remote Video Allowed", Toast.LENGTH_SHORT).show();

        if (container.getChildCount() >= 1) {
            return;
        }

        SurfaceView surfaceView = RtcEngine.CreateRendererView(getBaseContext());
        container.addView(surfaceView);
        mRtcEngine.setupRemoteVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, uid));
        surfaceView.setTag(uid);
    }

    private void leaveChannel() {
        mRtcEngine.leaveChannel();
    }

    public void Profile(View view)
    {
        Intent intProfile = new Intent(getApplicationContext(), Profile.class);
        startActivity(intProfile);
    }

    public void GoogleMaps(View view) {
        Intent intMapsActvity= new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(intMapsActvity);
    }
}

