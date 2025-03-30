package com.example.todo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes")
    Single<List<Note>> getNotes();

    @Insert
    Completable addNote(Note note);

    @Query("DELETE FROM notes WHERE id = :id")
    Completable removeNote(int id);
}
