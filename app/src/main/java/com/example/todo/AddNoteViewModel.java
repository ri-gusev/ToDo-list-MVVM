package com.example.todo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AddNoteViewModel extends AndroidViewModel {

    private NotesDao notesDao;
    private MutableLiveData<Boolean> shouldCloseActivity = new MutableLiveData<>();

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        notesDao = NotesDatabase.getInstance(application).notesDao();
    }

    public LiveData<Boolean> getShouldCloseActivity() {
        return shouldCloseActivity;
    }

    public void AddNote(Note note){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                notesDao.addNote(note);
                shouldCloseActivity.postValue(true);
            }
        });
        thread.start();
    }
}
