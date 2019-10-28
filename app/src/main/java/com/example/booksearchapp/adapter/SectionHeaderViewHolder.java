package com.example.booksearchapp.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksearchapp.R;

class SectionHeaderViewHolder extends RecyclerView.ViewHolder {

    TextView mHeaderTitle;


    SectionHeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        this.mHeaderTitle = itemView.findViewById(R.id.section_text);
    }
}
