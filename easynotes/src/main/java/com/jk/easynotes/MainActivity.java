package com.jk.easynotes;

import com.jk.easynotes.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class MainActivity extends Activity {

    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final boolean TOGGLE_ON_CLICK = true;
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;
    private SystemUiHider mSystemUiHider;
    private ListView listView ;
    private Button btn_add;
    private ArrayList<Note> mNotes;
    private NoteAdapter noteAdapter;
    private NotesDAO notesDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notesDAO=new NotesDAO(this);
        try {
            notesDAO.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        mNotes = notesDAO.getAllNotes();


        listView = (ListView)findViewById(R.id.fullscreen_content);
        btn_add= (Button) findViewById(R.id.btn_addNote);

        noteAdapter = new NoteAdapter(this,mNotes);
        listView.setAdapter(noteAdapter);




    }

    public void deleteAll(View v) throws SQLException {
        notesDAO.deleteAll();
        noteAdapter.deleteAll();
        Toast.makeText(this,"Deleted all",Toast.LENGTH_LONG);
    }

    public void addNote(View view){
        LayoutInflater li= LayoutInflater.from(this);
        View dialogView=li.inflate(R.layout.dialog_add_note,null);
        AlertDialog.Builder alerDialogBuilder = new AlertDialog.Builder(this);

        alerDialogBuilder.setView(dialogView);
        final EditText noteName =(EditText)dialogView.findViewById(R.id.editNoteName);
        final EditText noteDecribe =(EditText)dialogView.findViewById(R.id.editDescribe);

        alerDialogBuilder.setCancelable(false).setPositiveButton("Add",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Note noteNew=notesDAO.createNote(new Note(noteName.getText().toString(),"",noteDecribe.getText().toString()));
                noteAdapter.updateNotes(noteNew);
            }
        }).setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog ad = alerDialogBuilder.create();
        ad.show();

        Toast.makeText(this,"Added",Toast.LENGTH_LONG);

    }


    @Override
    protected void onResume() {
        try {
            notesDAO.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        notesDAO.close();
        super.onPause();
    }



}
