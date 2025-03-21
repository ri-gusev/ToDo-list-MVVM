package com.example.todo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
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
