package com.example.itubeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "iTube.db";
    private static final int DATABASE_VERSION = 1;


    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";


    private static final String TABLE_PLAYLISTS = "playlists";
    private static final String COLUMN_PLAYLIST_ID = "id";
    private static final String COLUMN_PLAYLIST_NAME = "name";
    private static final String COLUMN_USER_ID_FK = "user_id";



    private static final String TABLE_PLAYLIST_VIDEOS = "playlist_videos";
    private static final String COLUMN_VIDEO_ID = "id";
    private static final String COLUMN_PLAYLIST_ID_FK = "playlist_id";
    private static final String COLUMN_VIDEO_URL = "video_url";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_PLAYLISTS_TABLE = "CREATE TABLE " + TABLE_PLAYLISTS + "("
                + COLUMN_PLAYLIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PLAYLIST_NAME + " TEXT,"
                + COLUMN_USER_ID_FK + " INTEGER)";
        db.execSQL(CREATE_PLAYLISTS_TABLE);

        String CREATE_PLAYLIST_VIDEOS_TABLE = "CREATE TABLE " + TABLE_PLAYLIST_VIDEOS + "("
                + COLUMN_VIDEO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PLAYLIST_ID_FK + " INTEGER,"
                + COLUMN_VIDEO_URL + " TEXT)";
        db.execSQL(CREATE_PLAYLIST_VIDEOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYLISTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYLIST_VIDEOS);
        onCreate(db);
    }


    public long addUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        return db.insert(TABLE_USERS, null, values);
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_ID};
        String selection = COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        return cursorCount > 0;
    }


    public long addPlaylist(String name, long userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PLAYLIST_NAME, name);
        values.put(COLUMN_USER_ID_FK, userId);
        return db.insert(TABLE_PLAYLISTS, null, values);
    }

    public Cursor getAllPlaylists(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_PLAYLISTS, null, COLUMN_USER_ID_FK + "=?", new String[]{String.valueOf(userId)}, null, null, null);
    }

    public long addVideoToPlaylist(long playlistId, String videoUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PLAYLIST_ID_FK, playlistId);
        values.put(COLUMN_VIDEO_URL, videoUrl);
        return db.insert(TABLE_PLAYLIST_VIDEOS, null, values);
    }


    public Cursor getVideosInPlaylist(long playlistId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_PLAYLIST_VIDEOS, null, COLUMN_PLAYLIST_ID_FK + "=?", new String[]{String.valueOf(playlistId)}, null, null, null);
    }
}