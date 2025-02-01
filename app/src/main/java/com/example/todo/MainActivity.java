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

    private ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        Random random = new Random();
        for (int i = 0; i < 20; i++){
            notes.add(new Note(i, "Note" + i, random.nextInt(3)));
        }

        showNotes();

        floatingActionButtonAddNote.setOnClickListener(v -> {
            Intent intent = AddNoteActivity.newIntent(MainActivity.this);
            startActivity(intent);
        });

    }



    private void showNotes(){
        for (Note note : notes){
            View view = getLayoutInflater().inflate(
                    R.layout.note_item,
                    linearLayoutNotes,
                    false);

            TextView textViewNote = view.findViewById(R.id.TextViewNote);
            textViewNote.setText(note.getText());

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