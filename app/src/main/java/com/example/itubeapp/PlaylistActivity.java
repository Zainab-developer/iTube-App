package com.example.itubeapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    private ListView lvPlaylists;
    private DatabaseHelper databaseHelper;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        databaseHelper = new DatabaseHelper(this);
        lvPlaylists = findViewById(R.id.lvPlaylists);


        userId = getIntent().getLongExtra("USER_ID", -1);

        loadPlaylists();
    }

    private void loadPlaylists() {
        Cursor cursor = databaseHelper.getAllPlaylists(userId);
        List<String> playlists = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex("name");
            if (nameColumnIndex != -1) {
                do {
                    String name = cursor.getString(nameColumnIndex);
                    playlists.add(name);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playlists);
        lvPlaylists.setAdapter(adapter);
    }


}