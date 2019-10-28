package com.example.booksearchapp.adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.booksearchapp.R;


class LibDataViewHolder extends RecyclerView.ViewHolder {
    TextView mIcon;
    TextView mBookcode;
    ImageButton bookcodeitem_button;
    TextView mFloor;
    TextView mRange;
    TextView mHowtoGo;

    LibDataViewHolder(View itemView) {
        super(itemView);
        mIcon = itemView.findViewById(R.id.bookcodeitem_icon);
        mBookcode = itemView.findViewById(R.id.bookcodeitem_bookcode);
        mFloor = itemView.findViewById(R.id.bookcodeitem_floor);
        mRange = itemView.findViewById(R.id.bookcodeitem_range);
        mHowtoGo = itemView.findViewById(R.id.bookcodeitem_howtogo);
        bookcodeitem_button = itemView.findViewById(R.id.bookcodeitem_starImage);

    }
}
