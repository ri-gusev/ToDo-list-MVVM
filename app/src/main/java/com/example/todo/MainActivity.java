package com.example.todo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity{

    private RecyclerView recyclerViewNotes;
    private FloatingActionButton floatingActionButtonAddNote;
    private NotesAdapter notesAdapter; //link on our recyclerView adapter

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initViews();

        notesAdapter = new NotesAdapter(); //initialize recyclerView adapter

        viewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                notesAdapter.setNotes(notes);
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
                viewModel.remove(note);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);
        
        //Go to activity where we can make new note-
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