package com.vcs.notes;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    private ListView notesListView;
    private ArrayAdapter<NoteInfo> adapter;
    private List<NoteInfo> notes;
    DatabaseHandler handler = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.add_new_note);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);
                startActivity(intent);
            }
        });

        notesListView = findViewById(R.id.note_list);

        if(handler.getNotes().size() == 0){
            handler.addCourses(DataManager.getInstance().getCourses());
            handler.addNotes(DataManager.getInstance().getNotes());
        }

        notes = handler.getNotes();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                notes
        );

        notesListView.setAdapter(adapter);

        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);

                // Passing information with intent
                NoteInfo noteInfo = (NoteInfo) notesListView.getItemAtPosition(position);

                intent.putExtra(NoteActivity.NOTE_INFO, noteInfo);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        notes.clear();
        notes.addAll(handler.getNotes());
        adapter.notifyDataSetChanged();
    }
}
