package com.vcs.notes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public final class CourseInfo implements Parcelable {
    private final String courseId;
    private final String title;
    private final List<ModuleInfo> modules;

    public CourseInfo(String courseId, String title, List<ModuleInfo> modules) {
        this.courseId = courseId;
        this.title = title;
        this.modules = modules;
    }

    protected CourseInfo(Parcel parcel) {
        courseId = parcel.readString();
        title = parcel.readString();

        modules = new ArrayList<>();

        parcel.readTypedList(modules, ModuleInfo.CREATOR);
    }

    public static final Creator<CourseInfo> CREATOR = new Creator<CourseInfo>() {
        @Override
        public CourseInfo createFromParcel(Parcel in) {
            return new CourseInfo(in);
        }

        @Override
        public CourseInfo[] newArray(int size) {
            return new CourseInfo[size];
        }
    };

    public String getCourseId() {
        return courseId;
    }

    public ModuleInfo getModule(String moduleId) {
        for(ModuleInfo moduleInfo: modules) {
            if(moduleId.equals(moduleInfo.getModuleId()))
                return moduleInfo;
        }
        return null;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseInfo that = (CourseInfo) o;

        return courseId.equals(that.courseId);

    }

    @Override
    public int hashCode() {
        return courseId.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(courseId);
        parcel.writeString(title);

        // Adds whole list of objects into parcel
        parcel.writeTypedList(modules);
    }
}
