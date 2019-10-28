package com.example.booksearchapp.adapter;

import android.media.Image;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.booksearchapp.R;
import com.example.booksearchapp.data.Menu;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class MenuViewHolder extends GroupViewHolder {
    private TextView mTextView;
    private ImageView mImageView;

    public MenuViewHolder(View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.expandable_recyclerview_menu);
        mImageView = itemView.findViewById(R.id.expandable_recyclerview_menu_image);
    }
    public void bind(Menu menu) {
        mTextView.setText(menu.getTitle());
    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        mImageView.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        mImageView.setAnimation(rotate);
    }
}
