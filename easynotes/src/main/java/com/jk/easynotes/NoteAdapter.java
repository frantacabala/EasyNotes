package com.jk.easynotes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by john on 2/6/14.
 */
public class NoteAdapter extends ArrayAdapter<Note> {

    private Context mContext;
    private int mResourceId;
    private ArrayList<Note> mNotes;

    public NoteAdapter(Context context, ArrayList<Note> objects) {
        super(context, R.layout.list_view_single, objects);
        mContext=context;

        mNotes=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_view_single, parent, false);
        TextView firstLine=(TextView)rowView.findViewById(R.id.firstLine);
        TextView secondLine = (TextView) rowView.findViewById(R.id.secondLine);
        if(mNotes.size()>0){
        firstLine.setText(mNotes.get(position).getName());
        secondLine.setText(mNotes.get(position).getNote());
        }else{
            return inflater.inflate(android.R.layout.simple_list_item_1,parent,false);
        }
        if((position%2)==0){
            rowView.setBackgroundColor(Color.parseColor("#EE28A7"));
        }else{
            rowView.setBackgroundColor(Color.parseColor("#EE7691"));
        }
        return rowView;
    }

    public void updateNotes(Note note){
        mNotes.add(note);
        this.notifyDataSetChanged();
    }

    public void deleteAll(){
        mNotes=new ArrayList<Note>();
        this.notifyDataSetChanged();
    }




}
