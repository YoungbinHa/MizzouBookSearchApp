package com.example.booksearchapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksearchapp.R;
import com.example.booksearchapp.data.LibData;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class SectionedRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int SEARCHDATE = 0;
    public static final int LIBDATA = 1;

    List<LibData> mLibdataList;
    WeakReference<Context> mContextWeakReference;

    private LibDataAdapter.OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    private LibDataAdapter.OnImageClicked onImageClick;

    public interface OnImageClicked {
        void onImageClicked(int position);
    }


    public SectionedRecyclerViewAdapter(List<LibData> mLibdataList, Context context) {
        this.mLibdataList = mLibdataList;
        this.mContextWeakReference = new WeakReference<>(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int typeView) {

        Context context = mContextWeakReference.get();
        if (typeView == SEARCHDATE) {
            return new SectionHeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.section, parent, false));
        } else {
            return new SectionItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bookcodeitem_history_recycler_view, parent, false), context);
        }
    }

    @Override
    public int getItemViewType(int position) {
       if (mLibdataList.get(position).isSection()) {
            return SEARCHDATE;
        } else {
            return LIBDATA;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder sectionViewHolder, final int position) {
        final Context context = mContextWeakReference.get();
        String[] dateArr = mLibdataList.get(position).getDateAndTime().trim().split(" ");
        final LibData libData =  mLibdataList.get(position);

        if (context == null || dateArr.length != 4) {
            return;
        }

        // Section HeaderView
        if (libData.isSection()) {
            SectionHeaderViewHolder sectionHeaderViewHolder = (SectionHeaderViewHolder) sectionViewHolder;
            sectionHeaderViewHolder.mHeaderTitle.setText(dateArr[0] + " " + dateArr[1]);
            return;
        }

        // Section ItemView
        final SectionItemViewHolder sectionItemViewHolder = (SectionItemViewHolder) sectionViewHolder;
        if (libData.getSearchText().equals("")) {
            sectionItemViewHolder.mSectionItemBookCode.setText(libData.getBeginning() + " - " + libData.getEnding());
        } else {
            sectionItemViewHolder.mSectionItemBookCode.setText(libData.getSearchText());
        }

        sectionItemViewHolder.mSectionDateAndTime.setText(dateArr[2]+" "+dateArr[3]);


        if (mLibdataList.get(position).getFavorite() == 0) {
            sectionItemViewHolder.mSectionBookMarkImage.clearColorFilter();
        } else {
            sectionItemViewHolder.mSectionBookMarkImage
                    .setColorFilter(ContextCompat.getColor(context, R.color.colorMUgoldYellow2));
        }

        sectionItemViewHolder.mSectionBookMarkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sectionItemViewHolder.mSectionBookMarkImage.getColorFilter() != null) {
                    sectionItemViewHolder.mSectionBookMarkImage.clearColorFilter();
                    libData.setFavorite(0);
                    //---------------
                    onImageClick.onImageClicked(position);
                    //---------------
                    notifyDataSetChanged();
                } else {
                    sectionItemViewHolder.mSectionBookMarkImage
                            .setColorFilter(ContextCompat.getColor(context, R.color.colorMUgoldYellow2));
                    libData.setFavorite(1);
                    //---------------
                    onImageClick.onImageClicked(position);
                    //---------------
                    notifyDataSetChanged();
                }
            }
        });
        if (libData.getSearchText().equals("")){
            sectionItemViewHolder.mSectionHistoryBookIcon.setText(libData.getBeginning().substring(0, 2));
        } else {
            sectionItemViewHolder.mSectionHistoryBookIcon.setText(libData.getSearchText().substring(0, 2));
        }

        int seed = libData.getBeginning().charAt(0) + 7;
        Random mRandom = new Random(seed);

        int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
        ((GradientDrawable) sectionItemViewHolder.mSectionHistoryBookIcon.getBackground()).setColor(color);

        // Set onClickListener
        sectionItemViewHolder.mSectionItemBookCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position);
            }
        });

        sectionItemViewHolder.mSectionHistoryBookIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position);
            }
        });

        sectionItemViewHolder.mSectionDateAndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLibdataList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void setOnClick(LibDataAdapter.OnItemClicked onClick) {
        this.onClick = onClick;
    }

    //-----------
    public void setOnImageClickClick(LibDataAdapter.OnImageClicked onClick) {
        this.onImageClick = onClick;
    }
    //-----------
}
