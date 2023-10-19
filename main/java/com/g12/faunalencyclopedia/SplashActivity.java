package com.g12.faunalencyclopedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;
/**
 * @author UID: u7532171 Name: Vaibhav Nohria
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        VideoView videoView = findViewById(R.id.videoView);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro_video);
        videoView.setVideoURI(videoUri);
        videoView.setOnCompletionListener(mp -> {
            // Video playback is complete; transition to the main activity
            Intent intent = new Intent(SplashActivity.this, Homepage.class);
            startActivity(intent);
            finish(); // Finish the splash activity
        });
        videoView.start();
    }
}
