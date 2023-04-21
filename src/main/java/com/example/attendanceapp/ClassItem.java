package com.example.attendanceapp;

public class ClassItem {

    private String className;
    private String subjectName;

    public long getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    private long cid;

    public ClassItem(long cid, String className, String subjectName) {
        this.className = className;
        this.subjectName = subjectName;
        this.cid = cid;
    }

    public ClassItem(String className, String subjectName) {
        this.className = className;
        this.subjectName = subjectName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
