package com.example.news.ui.home;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "news";
    public final static String TABLE_NAME = "favorite";
    public final static String ID = "id";
    public final static String IMAGE_URL = "image_url";
    public final static String TITLE = "title";
    public final static String DESCRIPTION = "description";
    public final static String LINK = "link";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY  AUTOINCREMENT, TITLE TEXT," +
                "LINK TEXT, DESCRIPTION TEXT, IMAGE_URL TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME);
    onCreate(db);
    }

    public boolean insertData(String title, String link, String description, String image_url){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITLE, title);
        cv.put(LINK, link);
        cv.put(DESCRIPTION, description);
        cv.put(IMAGE_URL, image_url);


        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) return false;
        return true;
    }

    public Cursor getData(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FORM " + TABLE_NAME + " WHERE ID='" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public  Cursor getAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME , null);
        return cursor;
    }


    public boolean deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, "ID = ?", new String[]{id});
        if (result == -1){
            return false;
        }
        return true;
    }

}
