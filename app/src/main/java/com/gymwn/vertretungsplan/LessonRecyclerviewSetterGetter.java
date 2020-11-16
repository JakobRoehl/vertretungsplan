package com.gymwn.vertretungsplan;

public class LessonRecyclerviewSetterGetter {
    String subject = "";
    String room = "";
    String info = "";
    boolean exercises = false;
    boolean free = false;
    boolean replace = false;
    String elso = "";
    String lesson = "";

    public String getLesson() {
        return lesson;
    }

    public void setLessom(String lesson) {
        this.lesson = lesson;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isExercises() {
        return exercises;
    }

    public void setExercises(boolean exercises) {
        this.exercises = exercises;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public boolean isReplace() {
        return replace;
    }

    public void setReplace(boolean replace) {
        this.replace = replace;
    }

    public String getElso() {
        return elso;
    }

    public void setElso(String elso) {
        this.elso = elso;
    }
}
