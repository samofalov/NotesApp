package com.vcs.notes;

import java.util.List;

public final class CourseInfo {
    private final String mCourseId;
    private final String mTitle;
    private final List<ModuleInfo> mModules;

    public CourseInfo(String courseId, String title, List<ModuleInfo> modules) {
        mCourseId = courseId;
        mTitle = title;
        mModules = modules;
    }

    public String getCourseId() {
        return mCourseId;
    }

    public ModuleInfo getModule(String moduleId) {
        for(ModuleInfo moduleInfo: mModules) {
            if(moduleId.equals(moduleInfo.getModuleId()))
                return moduleInfo;
        }
        return null;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseInfo that = (CourseInfo) o;

        return mCourseId.equals(that.mCourseId);

    }

    @Override
    public int hashCode() {
        return mCourseId.hashCode();
    }

}
