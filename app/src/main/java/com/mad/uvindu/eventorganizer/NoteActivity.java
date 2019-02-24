package com.mad.uvindu.eventorganizer;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        final ActionBar actionBar = getSupportActionBar();
        final Bundle bundle = getIntent().getExtras();

        final EditText title = findViewById(R.id.noteTitleId);
        final EditText content = findViewById(R.id.noteContentId);
        final Button save = findViewById(R.id.note_save_button);

        if (bundle != null) {
            actionBar.setTitle("View Note");

            String titleText = bundle.getString("noteTitle");
            String contentText = bundle.getString("noteContent");

            title.setText(titleText);
            title.setEnabled(false);

            content.setText(contentText);
            content.setEnabled(false);

            save.setText("Edit Note");
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actionBar.setTitle("Edit Note");
                    title.setEnabled(true);
                    content.setEnabled(true);
                    save.setText("Save");
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (title.getText().toString().trim().equals(""))
                                title.setError("Please Enter a Title");
                            if (content.getText().toString().trim().equals(""))
                                content.setError("Please Add Content");
                            if (!title.getText().toString().trim().equals("") && !content.getText().toString().trim().equals("") ) {
                                Toast.makeText(NoteActivity.this, "Note Saved", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(NoteActivity.this, MainActivity.class));
                            }
                        }
                    });
                }
            });
        } else {
            actionBar.setTitle("Add Note");
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (title.getText().toString().trim().equals(""))
                        title.setError("Please Enter a Title");
                    if (content.getText().toString().trim().equals(""))
                        content.setError("Please Add Content");
                    if (!title.getText().toString().trim().equals("") && !content.getText().toString().trim().equals("") ) {
                        Toast.makeText(NoteActivity.this, "Note Saved", Toast.LENGTH_SHORT).show();
                        DBHelper db = new DBHelper(NoteActivity.this);
                        Note note = new Note(title.getText().toString().trim(), content.getText().toString().trim());
                        db.addNote(note);
                        startActivity(new Intent(NoteActivity.this, MainActivity.class));
                    }
                }
            });
        }

    }

}
