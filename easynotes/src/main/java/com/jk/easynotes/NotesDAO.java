package com.jk.easynotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by john on 2/6/14.
 */
public class NotesDAO {

    private SQLiteDatabase database;
    private Context contex;
    private SqliteHelper dbHelper;
    private String[] allColumns = {
            SqliteHelper.COLUMN_ID,
            SqliteHelper.COLUMN_NOTE_NAME,
            SqliteHelper.COLUMN_NOTE_CONTENT,
            SqliteHelper.COLUMN_DESCRIPTION};


    public NotesDAO(Context context){
        this.contex=context;
        dbHelper = new SqliteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void deleteAll() throws SQLException {
        dbHelper.dropTable(database);
    }

    public Note createNote(Note note){
        ContentValues values = new ContentValues();
        values.put(SqliteHelper.COLUMN_NOTE_NAME,note.getName());
        values.put(SqliteHelper.COLUMN_NOTE_CONTENT,note.getContent());
        values.put(SqliteHelper.COLUMN_DESCRIPTION,note.getNote());
        long insertId= database.insert(SqliteHelper.TABLE_NOTES,null,values);
        Cursor cursor = database.query(SqliteHelper.TABLE_NOTES,allColumns,SqliteHelper.COLUMN_ID+" = "+insertId,null,null,null,null);

        cursor.moveToFirst();
        Note noteDB = cursorToNote(cursor);
        cursor.close();
        return noteDB;


    }


    public ArrayList<Note> getAllNotes(){
        ArrayList<Note> notes= new ArrayList<Note>();

        Cursor cursor = database.query(SqliteHelper.TABLE_NOTES,allColumns,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Note note = cursorToNote(cursor);
            notes.add(note);
            cursor.moveToNext();
        }
        cursor.close();
        return notes;
    }



    public Note cursorToNote(Cursor cursor){
        Note note = new Note();
        note.setId(cursor.getLong(0));
        note.setName(cursor.getString(1));
        note.setContent(cursor.getString(2));
        note.setNote(cursor.getString(3));

        return note;
    }

}
