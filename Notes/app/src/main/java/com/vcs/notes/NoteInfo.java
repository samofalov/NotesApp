package com.vcs.notes;

import android.os.Parcel;
import android.os.Parcelable;

public final class NoteInfo implements Parcelable {
    private int id;
    private CourseInfo course;
    private String title;
    private String text;

    public int getId() {
        return id;
    }

    public CourseInfo getCourse() {
        return course;
    }

    public void setCourse(CourseInfo course) {
        this.course = course;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public NoteInfo(int id, CourseInfo course, String title, String text) {
        this.id = id;
        this.course = course;
        this.title = title;
        this.text = text;
    }

    public NoteInfo() {
        // Default
    }


    protected NoteInfo(Parcel parcel) {
        id = parcel.readInt();
        title = parcel.readString();
        text = parcel.readString();
        course = parcel.readParcelable(CourseInfo.class.getClassLoader());
    }

    public static final Creator<NoteInfo> CREATOR = new Creator<NoteInfo>() {
        @Override
        public NoteInfo createFromParcel(Parcel parcel) {
            return new NoteInfo(parcel);
        }

        @Override
        public NoteInfo[] newArray(int size) {
            return new NoteInfo[size];
        }
    };

    private String getCompareKey() {
        return course.getCourseId() + "|" + title + "|" + text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoteInfo that = (NoteInfo) o;

        return getCompareKey().equals(that.getCompareKey());
    }

    @Override
    public int hashCode() {
        return getCompareKey().hashCode();
    }

    @Override
    public String toString() {
        return getCompareKey();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        // Writing information into parcel
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(text);
        parcel.writeParcelable(course, 0);
    }
}












