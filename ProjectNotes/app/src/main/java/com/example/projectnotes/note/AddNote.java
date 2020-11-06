package com.example.projectnotes.note;

import android.os.Bundle;

import com.example.projectnotes.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class AddNote extends AppCompatActivity {
    FirebaseFirestore fStore;
    EditText noteTitle,noteContent;
    ProgressBar progressBarSave;
    FirebaseAuth fAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fStore = FirebaseFirestore.getInstance();
        noteTitle = findViewById(R.id.addNoteTitle);
        noteContent = findViewById(R.id.addNoteContent);
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        progressBarSave = findViewById(R.id.progressBar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nTitle = noteTitle.getText().toString();
                String nContent = noteContent.getText().toString();

                if(nTitle.isEmpty() || nContent.isEmpty()) {
                    Toast.makeText(AddNote.this, "Sorry, Cannot save Empty Note.", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBarSave.setVisibility(View.VISIBLE);

                    DocumentReference docref = fStore.collection("notes").document(user.getUid()).collection("mynotes").document();
                    Map<String,Object> note = new HashMap<> ();
                    note.put("title",nTitle);
                    note.put("content",nContent);

                    docref.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AddNote.this, "Your note is saved", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddNote.this, "Error, Try Again", Toast.LENGTH_SHORT).show();
                            progressBarSave.setVisibility(View.INVISIBLE);
                        }
                    });


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.close_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.close) {
            onBackPressed();
            Toast.makeText(this, "Note is not saved", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}