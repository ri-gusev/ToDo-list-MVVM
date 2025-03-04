package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity{

    private RecyclerView recyclerViewNotes;
    private FloatingActionButton floatingActionButtonAddNote;
    private NotesAdapter notesAdapter; //link on our recyclerView adapter
    private Database database = Database.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        notesAdapter = new NotesAdapter(); //initialize recyclerView adapter

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
                database.remove(note.getId());
                showNotes();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);
        
        //Go to activity where we can make new note
        floatingActionButtonAddNote.setOnClickListener(v -> {
            Intent intent = AddNoteActivity.newIntent(MainActivity.this);
            startActivity(intent);
        });

    }

    //Replace method showNotes from onCreate to onResume because we should show notes when our
    //activity gets focus.
    //If this method would be in onCreate then when we click on floatingActionButton and then return
    //back to main activity our note would not be added to main activity. This happened because
    //onCreate caused when activity destroyed and then create a new one, but when we click on add
    //button we don't destroy our activity, we just lose focus on main activity
    @Override
    protected void onResume() {
        super.onResume();
        showNotes();
    }

    private void showNotes(){
        notesAdapter.setNotes(database.getNotes());
    }

    private void initViews(){
        recyclerViewNotes = findViewById(R.id.RecyclerViewNotes);
        floatingActionButtonAddNote = findViewById(R.id.ButtonAddNotes);
    }
}