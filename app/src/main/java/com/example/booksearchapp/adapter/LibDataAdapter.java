package com.example.booksearchapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksearchapp.R;
import com.example.booksearchapp.data.LibData;

import java.util.List;
import java.util.Random;


public class LibDataAdapter extends RecyclerView.Adapter<LibDataViewHolder> {
    private List<LibData> mLibData;
    private Context mContext;
    private int resId;

    // Listener variables and interface
    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    private OnImageClicked onImageClick;

    public interface OnImageClicked {
        void onImageClicked(int position);
    }


    public LibDataAdapter(Context mContext, int resId, List<LibData> datas) {
        this.mLibData = datas;
        this.mContext = mContext;
        this.resId = resId;
    }

    @NonNull
    @Override
    public LibDataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.bookcodeitem_recycler_view, viewGroup, false);
        return new LibDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LibDataViewHolder libDataViewHolder, final int i) {
        String start = mLibData.get(i).getBeginning();
        final String end = mLibData.get(i).getEnding();


        libDataViewHolder.mBookcode.setText(start + " - " + end);
        libDataViewHolder.mFloor.setText(mLibData.get(i).getFloor());
        libDataViewHolder.mRange.setText(mLibData.get(i).getRange());
        libDataViewHolder.mHowtoGo.setText(mLibData.get(i).getText());


        libDataViewHolder.bookcodeitem_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageClick.onImageClicked(i);
            }
        });


        libDataViewHolder.mIcon.setText(mLibData.get(i).getBeginning().substring(0, 2));

        int seed = mLibData.get(i).getBeginning().charAt(0) + 7;
        Random mRandom = new Random(seed);
        int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
        ((GradientDrawable) libDataViewHolder.mIcon.getBackground()).setColor(color);


        // Set onClickListener
        libDataViewHolder.mBookcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(i);
            }
        });

        libDataViewHolder.mFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(i);
            }
        });

        libDataViewHolder.mHowtoGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(i);
            }
        });

        libDataViewHolder.mIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(i);
            }
        });

        libDataViewHolder.mRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(i);
            }
        });
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }

    public void setOnImageClickClick(OnImageClicked onClick) {
        this.onImageClick = onClick;
    }

    @Override
    public int getItemCount() {
        return mLibData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void filterList(List<LibData> libDataFiltered) {
        this.mLibData = libDataFiltered;
        notifyDataSetChanged();
    }
}