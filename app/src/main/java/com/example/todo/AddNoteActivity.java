package com.example.todo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextEnterNote;

    private RadioGroup radioGroupPriority;
    private RadioButton radioButtonPriority0;
    private RadioButton radioButtonPriority1;
    private RadioButton radioButtonPriority2;

    private Button buttonAddNote;

    private Database database = Database.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);
        initViews();

        buttonAddNote.setOnClickListener(view -> {
            saveNote();
        });
    }

    //In this method we create a note and add it to out database
    private void saveNote(){
        String text = editTextEnterNote.getText().toString().trim();

        if (text.isEmpty() || radioGroupPriority.getCheckedRadioButtonId() == -1){
            Toast.makeText(
                    this,
                    "Please fill all details",
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            int priority = getPriority();

            // id = id of last note + 1
            int id = 0;
            if (database.getNotes().isEmpty()){
                id = 1;
            } else {
                id = database.getNotes().get(database.getNotes().size()-1).getId() + 1;
            }
            Note note = new Note(id, text + id, priority);
            database.add(note);

            finish();
        }
    }

    private int getPriority(){
        int priority;

        if (radioButtonPriority0.isChecked()){
            priority = 0;
        } else if (radioButtonPriority1.isChecked()){
            priority = 1;
        } else {
            priority = 2;
        }

        return priority;
    }

    private void initViews(){
        editTextEnterNote = findViewById(R.id.EditTextEnterNote);

        radioButtonPriority0 = findViewById(R.id.RadioButtonPriority0);
        radioButtonPriority1 = findViewById(R.id.RadioButtonPriority1);
        radioButtonPriority2 = findViewById(R.id.RadioButtonPriority2);
        radioGroupPriority = findViewById(R.id.RadioGroupPriority);

        buttonAddNote = findViewById(R.id.ButtonAddNote);

    }

    public static Intent newIntent(Context context){
        return new Intent(context, AddNoteActivity.class);
    }

}