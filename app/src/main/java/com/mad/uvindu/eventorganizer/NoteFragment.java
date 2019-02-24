package com.mad.uvindu.eventorganizer;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


public class NoteFragment extends Fragment {

    private MainActivity.NoteAdapter noteAdapter;
    private ListView listView;

    public void setNoteAdapter(MainActivity.NoteAdapter noteAdapter) {
        this.noteAdapter = noteAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab = view.findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NoteActivity.class);
                startActivity(intent);
            }
        });
        listView = view.findViewById(R.id.noteList);
        listView.setDivider(new ColorDrawable(getResources().getColor(R.color.colorTab2)));
        listView.setDividerHeight(1);
        listView.setAdapter(noteAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), NoteActivity.class);
                Note note = (Note) listView.getItemAtPosition(i);
                intent.putExtra("noteTitle", note.getNoteTitle());
                intent.putExtra("noteContent", note.getNoteContent());
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note note = (Note) listView.getItemAtPosition(i);
                Snackbar.make(view, "Delete Note \"" + note.getNoteTitle() + "\" ?", Snackbar.LENGTH_INDEFINITE).setAction("DELETE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
                Vibrator vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(20);
                return true;
            }
        });
    }
}
