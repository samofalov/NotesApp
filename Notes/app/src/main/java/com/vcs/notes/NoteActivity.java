package com.vcs.notes;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.*;
import android.widget.*;

import java.util.List;

public class NoteActivity extends AppCompatActivity {

    public static final String NOTE_INFO = "com.vcs.notes.NoteActivity.note_info";
    private NoteInfo note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadNoteInfo();
        setSpinnerInfo();
        setTextFields();
    }

    private void loadNoteInfo() {
        Intent intent = getIntent();
        note = intent.getParcelableExtra(NOTE_INFO);
    }

    private void setSpinnerInfo() {
        Spinner spinner = findViewById(R.id.spinner_course);
        List<CourseInfo> courses = DataManager.getInstance().getCourses();

        ArrayAdapter<CourseInfo> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                courses);

        spinner.setAdapter(adapter);

        int index = courses.indexOf(note.getCourse());
        spinner.setSelection(index);
    }

    private void setTextFields() {
        EditText title = findViewById(R.id.note_title);
        EditText details = findViewById(R.id.note_detail);


        title.setText(note.getTitle());
        details.setText(note.getText());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
