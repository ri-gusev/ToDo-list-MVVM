package com.example.todo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewActivity extends AndroidViewModel {

    private NotesDatabase notesDatabase;

    public MainViewActivity(@NonNull Application application) {
        super(application);
        notesDatabase = NotesDatabase.getInstance(application);
    }

    public LiveData<List<Note>> getNotes(){
        return notesDatabase.notesDao().getNotes();
    }

    public void remove(Note note){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                notesDatabase.notesDao().removeNote(note.getId());
            }
        });
        thread.start();
    }

}
