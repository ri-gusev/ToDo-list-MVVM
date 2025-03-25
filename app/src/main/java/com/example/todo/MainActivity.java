package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private RecyclerView recyclerViewNotes;
    private FloatingActionButton floatingActionButtonAddNote;
    private NotesAdapter notesAdapter; //link on our recyclerView adapter
    private NotesDatabase notesDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesDatabase = NotesDatabase.getInstance(getApplication());

        initViews();

        notesAdapter = new NotesAdapter(); //initialize recyclerView adapter

        notesDatabase.notesDao().getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                notesAdapter.setNotes(notes);
            }
        });

        //so far, we will not delete the note when clicking. Let's leave this method empty.
        notesAdapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
            }
        });

        recyclerViewNotes.setAdapter(notesAdapter); //add our adapter to recyclerView

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Note note = notesAdapter.getNotes().get(position);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        notesDatabase.notesDao().removeNote(note.getId());
                    }
                });
                thread.start();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);
        
        //Go to activity where we can make new note
        floatingActionButtonAddNote.setOnClickListener(v -> {
            Intent intent = AddNoteActivity.newIntent(MainActivity.this);
            startActivity(intent);
        });
    }

    private void initViews(){
        recyclerViewNotes = findViewById(R.id.RecyclerViewNotes);
        floatingActionButtonAddNote = findViewById(R.id.ButtonAddNotes);
    }

}