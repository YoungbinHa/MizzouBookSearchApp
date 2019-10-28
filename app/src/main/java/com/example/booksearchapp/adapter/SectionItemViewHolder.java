package com.example.booksearchapp.adapter;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksearchapp.R;


class SectionItemViewHolder extends RecyclerView.ViewHolder {
    TextView mSectionHistoryBookIcon;
    TextView mSectionItemBookCode;
    TextView mSectionDateAndTime;
    ImageView mSectionBookMarkImage;

    SectionItemViewHolder(@NonNull View itemView, final Context context) {
        super(itemView);
        mSectionHistoryBookIcon = itemView.findViewById(R.id.history_bookcodeitem_icon);
        mSectionItemBookCode = itemView.findViewById(R.id.history_recycler_view_bookcode_text);
        mSectionDateAndTime = itemView.findViewById(R.id.history_recycler_view_bookcode_time);
        mSectionBookMarkImage = itemView.findViewById(R.id.history_bookmarkimage);
    }
}
