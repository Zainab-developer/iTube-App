package com.example.itubeapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomePageActivity extends AppCompatActivity {

    private EditText etVideoUrl;
    private Button btnPlay, btnShowPlaylist, btnSavePlaylist;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        databaseHelper = new DatabaseHelper(this);

        etVideoUrl = findViewById(R.id.etVideoUrl);
        btnPlay = findViewById(R.id.btnPlay);
        btnShowPlaylist = findViewById(R.id.btnShowPlaylist);
        btnSavePlaylist = findViewById(R.id.btnSavePlaylist);

        btnPlay.setOnClickListener(v -> {
            String videoUrl = etVideoUrl.getText().toString().trim();
            if (TextUtils.isEmpty(videoUrl)) {
                Toast.makeText(HomePageActivity.this, "Please enter a video URL", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(HomePageActivity.this, VideoPlayerActivity.class);
            intent.putExtra("VIDEO_URL", videoUrl);
            startActivity(intent);
        });

        btnShowPlaylist.setOnClickListener(v -> {
            startActivity(new Intent(HomePageActivity.this, PlaylistActivity.class));
        });

        btnSavePlaylist.setOnClickListener(v -> {
            String videoUrl = etVideoUrl.getText().toString().trim();
            if (TextUtils.isEmpty(videoUrl)) {
                Toast.makeText(HomePageActivity.this, "Please enter a video URL", Toast.LENGTH_SHORT).show();
                return;
            }

            long playlistId = 1;
            databaseHelper.addVideoToPlaylist(playlistId, videoUrl);
            Toast.makeText(HomePageActivity.this, "Video saved to playlist", Toast.LENGTH_SHORT).show();
        });
    }
}