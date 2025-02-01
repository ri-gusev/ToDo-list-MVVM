package com.example.todo;

public class Note {

    private int id;
    private String text;
    private int priority;

    public Note(int id, String text, int priority){
        this.id = id;
        this.text = text;
        this.priority = priority;
    }

    public int getId(){
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public String getText() {
        return text;
    }
}
