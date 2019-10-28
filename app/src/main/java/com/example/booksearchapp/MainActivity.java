package com.example.booksearchapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.example.booksearchapp.fragment.FragmentOne_Home;
import com.example.booksearchapp.fragment.FragmentThree_More;
import com.example.booksearchapp.fragment.FragmentTwo_His;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class MainActivity extends AppCompatActivity implements FragmentOne_Home.OnFragmentInteractionListener,
        FragmentTwo_His.OnFragmentInteractionListener,
        FragmentThree_More.OnFragmentInteractionListener {

    // Web Scraping Variables
    private static final String URL = "http://library.missouri.edu/";
    private com.example.booksearchapp.data.MenuItem hourItem = new com.example.booksearchapp.data.MenuItem();


    private FragmentTransaction fragmentTransaction;
    private Fragment fragment;
    //    private ActionBar actionBar;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_search:
                            fragment = new FragmentOne_Home();
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frameLayout, fragment);
                            fragmentTransaction.commit();
                            return true;
                        case R.id.navigation_history:
                            setTitle("History");
                            fragment = new FragmentTwo_His();
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frameLayout, fragment);
                            fragmentTransaction.commit();
                            return true;
                        case R.id.navigation_more:
                            setTitle("More");
                            new WebScarping().execute();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Hour", hourItem);
                            fragment = new FragmentThree_More();
                            fragment.setArguments(bundle);
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frameLayout, fragment);
                            fragmentTransaction.commit();
                            return true;
                    }
                    return false;
                }
            };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SystemClock.sleep(400);
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        fragment = new FragmentOne_Home();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    // web scraping class
    public class WebScarping extends AsyncTask<Void, Void, Void> {
        String key = "hours/";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                // Scarping web resources by using Jsoup
                Document doc = Jsoup.connect(URL + key).get();

                // Set hour table title from web scarping data
                Elements hourTitle = doc.select("#hours .hours-content .hours-name");
                hourItem.setHourTitle(hourTitle.text());

                // Set hours for a week from web scarping data
                Elements hours = doc.select("#hours .hours-content ul .hours-time");
                Elements hoursMessage = doc.select("#hours .hours-content ul .hours-time .dailymessage");
                hourItem.setHourMonday(hours.eq(0).text().split("Open|Close")[0] + "\n" + hoursMessage.eq(0).text().split(", ")[0]);
                hourItem.setHourTuesday(hours.eq(1).text().split("Open|Close")[0] + "\n" + hoursMessage.eq(1).text().split(", ")[0]);
                hourItem.setHourWednesday(hours.eq(2).text().split("Open|Close")[0] + "\n" + hoursMessage.eq(2).text().split(", ")[0]);
                hourItem.setHourThursday(hours.eq(3).text().split("Open|Close")[0] + "\n" + hoursMessage.eq(3).text().split(", ")[0]);
                hourItem.setHourFriday(hours.eq(4).text().split("Open|Close")[0] + "\n" + hoursMessage.eq(4).text().split(", ")[0]);
                hourItem.setHourSaturday(hours.eq(5).text().split("Open|Close")[0] + "\n" + hoursMessage.eq(5).text().split(", ")[0]);
                hourItem.setHourSunday(hours.eq(6).text().split("Open|Close")[0] + "\n" + hoursMessage.eq(6).text().split(", ")[0]);

                // Set Details from web scarping data
                Elements hourDetail = doc.select("#hours #hours-break");
                hourItem.setHourDetail(hours.eq(6).text().split(", ")[1]+ "\n" +hourDetail.text());
            } catch (IOException e) {
                hourItem.setHourMonday("12:00 am - 12:00 am"+ "\n" +"Open 24 Hours");
                hourItem.setHourTuesday("12:00 am - 12:00 am"+ "\n" +"Open 24 Hours");
                hourItem.setHourWednesday("12:00 am - 12:00 am"+ "\n" +"Open 24 Hours");
                hourItem.setHourThursday("12:00 am - 12:00 am"+ "\n" +"Open 24 Hours");
                hourItem.setHourFriday("12:00 am - 12:00 am"+ "\n" +"Closes at Midnight");
                hourItem.setHourSaturday("8:00 am - 12:00 am"+ "\n" +"Closes at Midnight");
                hourItem.setHourSunday("12:00 am - 12:00 am"+ "\n" +"Open 24 Hours");
                hourItem.setHourDetail("There is no internet Connection. The default time value will be shown");
                e.getStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }


    }

}
