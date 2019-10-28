package com.example.booksearchapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;

import com.example.booksearchapp.data.LibData;

import androidx.appcompat.app.AppCompatActivity;

import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class DataClickedActivity extends AppCompatActivity {
    private static final String LIBDATA_KEY = "l01";

    TextView bookCodeTextView;
    TextView floorTextView;
    TextView rangeTextView;
    TextView pathTextView;
    TextView bookCodeRangeTextView;
    ZoomableImageView pathImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_clicked);

        // Set up the views
        bookCodeTextView = findViewById(R.id.BookCodeTextView);
        floorTextView = findViewById(R.id.FloorTextView);
        rangeTextView = findViewById(R.id.RangeTextView);
        pathTextView = findViewById(R.id.PathTextView);
        pathImage = findViewById(R.id.PathImage);
        bookCodeRangeTextView = findViewById(R.id.BookCodeRange_TextView);

        pathTextView.setMovementMethod(new ScrollingMovementMethod());
        pathTextView.setVerticalFadingEdgeEnabled(true);
        pathTextView.setVerticalFadingEdgeEnabled(false);

        // Get the parcel data
        LibData libData = getIntent().getParcelableExtra(LIBDATA_KEY);

        // Show data on the screen
        if (libData.getSearchText() == null){
            bookCodeTextView.setText("-");
        } else {
            bookCodeTextView.setText(libData.getSearchText());
        }

        String range = libData.getBeginning() + " - " + libData.getEnding();
        bookCodeRangeTextView.setText(range);


        floorTextView.setText(libData.getFloor());
        rangeTextView.setText(libData.getRange());
        pathTextView.setText(libData.getText());

        // Get Image
        int resId = getResources().getIdentifier(libData.getMap(), "drawable", getApplicationContext().getPackageName());
        Bitmap bm = BitmapFactory.decodeResource(getResources(), resId);
        pathImage.setImageBitmap(bm);
    }
}
