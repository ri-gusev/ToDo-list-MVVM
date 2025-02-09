package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity{

    private LinearLayout linearLayoutNotes;
    private FloatingActionButton floatingActionButtonAddNote;

    private Database database = Database.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

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
        linearLayoutNotes.removeAllViews();
        //Remove all notes and then we will add them again but
        //with new one
        for (Note note : database.getNotes()){
            //Convert note_item.xml to view (our note)
            View view = getLayoutInflater().inflate(
                    R.layout.note_item,//What we want to convert
                    linearLayoutNotes, //Where we want to add it
                    false);

            view.setOnClickListener(v -> {
                database.remove(note.getId());
                showNotes();
            });

            //Add text to our note view
            TextView textViewNote = view.findViewById(R.id.TextViewNote);
            textViewNote.setText(note.getText());

            //Set color to our note view
            int colorResId;
            switch (note.getPriority()){
                case 0:
                    colorResId = android.R.color.holo_green_light;
                    break;
                case 1:
                    colorResId = android.R.color.holo_orange_light;
                    break;
                default:
                    colorResId = android.R.color.holo_red_light;
            }
            int color = ContextCompat.getColor(this, colorResId);
            view.setBackgroundColor(color);

            linearLayoutNotes.addView(view);
        }
    }

    private void initViews(){
        linearLayoutNotes = findViewById(R.id.LinearLayoutNotes);
        floatingActionButtonAddNote = findViewById(R.id.ButtonAddNotes);
    }
}