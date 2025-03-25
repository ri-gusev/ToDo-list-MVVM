package com.example.todo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextEnterNote;

    private RadioGroup radioGroupPriority;
    private RadioButton radioButtonPriority0;
    private RadioButton radioButtonPriority1;
    private RadioButton radioButtonPriority2;

    private Button buttonAddNote;

    private AddNoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);

        viewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);

        viewModel.getShouldCloseActivity().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldClose) {
                if (shouldClose) {
                    finish();
                }
            }
        });

        initViews();

        buttonAddNote.setOnClickListener(view -> {
            saveNote();
        });
    }

    //In this method we create a note and add it to out database
    private void saveNote() {
        String text = editTextEnterNote.getText().toString().trim();

        if (text.isEmpty() || radioGroupPriority.getCheckedRadioButtonId() == -1) {
            Toast.makeText(
                    this,
                    "Please fill all details",
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            int priority = getPriority();
            Note note = new Note(text, priority);
            viewModel.AddNote(note);
        }
    }

    private int getPriority() {
        int priority;

        if (radioButtonPriority0.isChecked()) {
            priority = 0;
        } else if (radioButtonPriority1.isChecked()) {
            priority = 1;
        } else {
            priority = 2;
        }

        return priority;
    }

    private void initViews() {
        editTextEnterNote = findViewById(R.id.EditTextEnterNote);

        radioButtonPriority0 = findViewById(R.id.RadioButtonPriority0);
        radioButtonPriority1 = findViewById(R.id.RadioButtonPriority1);
        radioButtonPriority2 = findViewById(R.id.RadioButtonPriority2);
        radioGroupPriority = findViewById(R.id.RadioGroupPriority);

        buttonAddNote = findViewById(R.id.ButtonAddNote);

    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddNoteActivity.class);
    }

}