package com.example.projectnotes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    TextView noteTitle, noteContent;
    View view;
    CardView mCardView;

    public NoteViewHolder(@NonNull View itemView) {

        super(itemView);

        noteTitle = itemView.findViewById(R.id.titles);
        noteContent = itemView.findViewById(R.id.content);
        mCardView = itemView.findViewById(R.id.noteCard);
        view = itemView;
    }
}
