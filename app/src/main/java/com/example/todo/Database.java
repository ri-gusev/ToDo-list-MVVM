package com.example.todo;

import java.util.ArrayList;
import java.util.Random;

public class Database {
    ArrayList<Note> notes = new ArrayList<>();

    private static Database instance = null;
    //This is singleton
    //Now we don't create new database when we add new note
    public static Database getInstance(){
        if (instance == null){
            instance = new Database();
        }
        return instance;
    }

    public void add(Note note){
        notes.add(note);
    }

    public void remove(int id){
        for (Note note : notes){
            if (note.getId() == id){
                notes.remove(note);
            }
        }
    }

    public ArrayList<Note> getNotes(){
        return new ArrayList<Note>(notes);
    }
}
