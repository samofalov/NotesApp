package com.vcs.notes;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notes";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        createTables(db);
    }

    private void createTables(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE note (id INTEGER PRIMARY KEY, courseId TEXT, title TEXT, text TEXT)";
        String CREATE_COURSES_TABLE = "CREATE TABLE course (id TEXT PRIMARY KEY, title TEXT)";
        String CREATE_MODULES_TABLE = "CREATE TABLE module (id TEXT PRIMARY KEY, courseId TEXT, title TEXT, isComplete BOOLEAN)";

        db.execSQL(CREATE_NOTES_TABLE);
        db.execSQL(CREATE_COURSES_TABLE);
        db.execSQL(CREATE_MODULES_TABLE);
    }

    public void addNote(NoteInfo noteInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("courseId", noteInfo.getCourse().getCourseId());
        values.put("title", noteInfo.getTitle());
        values.put("text", noteInfo.getText());

        db.insert("note", null, values);
        db.close();
    }

    public void addNotes(List<NoteInfo> notes) {
        for (NoteInfo note : notes) {
            addNote(note);
        }
    }

    public void addModule(ModuleInfo moduleInfo, String courseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", moduleInfo.getModuleId());

        if (courseId != null) {
            values.put("courseId", courseId);
        }

        values.put("title", moduleInfo.getTitle());
        values.put("isComplete", moduleInfo.isComplete());

        db.insert("module", null, values);
        db.close();
    }

    public void addCourse(CourseInfo courseInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", courseInfo.getCourseId());
        values.put("title", courseInfo.getTitle());

        db.insert("course", null, values);
        db.close();

        for (ModuleInfo module : courseInfo.getModules()) {
            addModule(module, courseInfo.getCourseId());
        }
    }

    public void addCourses(List<CourseInfo> courses) {

        for (CourseInfo course : courses) {
            addCourse(course);
        }
    }

    public List<CourseInfo> getCourses() {

        List<CourseInfo> courses = new ArrayList<>();

        String selectQuery = "SELECT * FROM course";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                String id = cursor.getString(0);
                String title = cursor.getString(1);

                List<ModuleInfo> courseModules = getModulesByCourseId(id);

                courses.add(new CourseInfo(id, title, courseModules));
            } while (cursor.moveToNext());
        }

        return courses;
    }

    private List<ModuleInfo> getModulesByCourseId(String courseId) {
        List<ModuleInfo> modules = new ArrayList<>();

        // TODO: USE methods instead of a string
        String selectQuery = "SELECT id, title, isComplete FROM module WHERE courseId = '" + courseId + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                String id = cursor.getString(0);
                String title = cursor.getString(1);
                boolean isComplete = (cursor.getInt(2) == 1);

                modules.add(new ModuleInfo(id, title, isComplete));
            } while (cursor.moveToNext());
        }

        return modules;
    }

    public void updateNote(NoteInfo note){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("courseId", note.getCourse().getCourseId());
        values.put("title", note.getTitle());
        values.put("text", note.getText());

        db.update("note", values, "id = ?", new String[] { String.valueOf(note.getId()) });
    }

    public List<NoteInfo> getNotes(){
        List<NoteInfo> notes = new ArrayList<>();

        // TODO: USE methods instead of a string
        String selectQuery = "SELECT * FROM note";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                int id = cursor.getInt(0);
                String courseId = cursor.getString(1);
                String title = cursor.getString(2);
                String text = cursor.getString(3);

                CourseInfo courseInfo = getCourseById(courseId);

                notes.add(new NoteInfo(id, courseInfo, title, text));
            } while (cursor.moveToNext());
        }

        return notes;
    }

    private CourseInfo getCourseById(String courseId) {
        String selectQuery = "SELECT * FROM course WHERE id = '" + courseId + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

                String id = cursor.getString(0);
                String title = cursor.getString(1);

                List<ModuleInfo> modules = getModulesByCourseId(id);

                return new CourseInfo(id, title, modules);
        }

        return null;
    }

}
