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
    private boolean isNewNote;
    private EditText title;
    private EditText details;
    private Spinner spinner;

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

        isNewNote = (note == null);
    }

    private void setSpinnerInfo() {
        spinner = findViewById(R.id.spinner_course);
        List<CourseInfo> courses = DataManager.getInstance().getCourses();

        ArrayAdapter<CourseInfo> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                courses);

        spinner.setAdapter(adapter);

        if(!isNewNote) {
            int index = courses.indexOf(note.getCourse());
            spinner.setSelection(index);
        }
    }

    private void setTextFields() {
        title = findViewById(R.id.note_title);
        details = findViewById(R.id.note_detail);

        if(!isNewNote) {
            title.setText(note.getTitle());
            details.setText(note.getText());
        }
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
        if (id == R.id.action_send_email) {
            sendEmail();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        saveNote();
    }

    private void saveNote() {
        note.setCourse((CourseInfo) spinner.getSelectedItem());
        note.setTitle(title.getText().toString());
        note.setText(details.getText().toString());

        DataManager.getInstance().updateNote(note);
    }

    private void sendEmail() {
        CourseInfo courseInfo = (CourseInfo) spinner.getSelectedItem();

        String subject = courseInfo.toString() + "|" + title.getText().toString();
        String text = details.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc2822");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);

        startActivity(intent);
    }
}
