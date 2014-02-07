package com.jk.easynotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by john on 2/6/14.
 */
public class SqliteHelper extends SQLiteOpenHelper {

    public static final String TABLE_NOTES="easynotes";
    public static final String DATABASE_NAME="easynotes.db";
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_NOTE_NAME="note_name";
    public static final String COLUMN_DESCRIPTION="description";
    public static final String COLUMN_NOTE_CONTENT="note_content";
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_CREATE=" create table "
            +TABLE_NOTES +"("
            +COLUMN_ID + " integer primary key autoincrement, "
            +COLUMN_NOTE_NAME + " text not null, "
            +COLUMN_NOTE_CONTENT + " text not null, "
            +COLUMN_DESCRIPTION + " text not null );";



    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SqliteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    public void dropTable(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }
}
