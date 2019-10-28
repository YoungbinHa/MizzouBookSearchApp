package com.example.booksearchapp.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booksearchapp.R;
import com.example.booksearchapp.data.MenuItem;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MenuItemHourViewHolder extends ChildViewHolder implements View.OnClickListener{
    private TextView hourTitle;
    private TextView hourMonday;
    private TextView hourTuesday;
    private TextView hourWednesday;
    private TextView hourThursday;
    private TextView hourFriday;
    private TextView hourSaturday;
    private TextView hourSunday;
    private TextView hourDetail;
    private Button button;
    private Context context;
    private static final String URL = "http://library.missouri.edu/";
    private MenuItem hourItem = new MenuItem();

    public MenuItemHourViewHolder(View itemView, Context context) {
        super(itemView);
        hourTitle = itemView.findViewById(R.id.menuinfo_hours_title);
        hourMonday = itemView.findViewById(R.id.menuinfo_hours_monday);
        hourTuesday = itemView.findViewById(R.id.menuinfo_hours_tuesday);
        hourWednesday = itemView.findViewById(R.id.menuinfo_hours_wednesday);
        hourThursday = itemView.findViewById(R.id.menuinfo_hours_thurday);
        hourFriday = itemView.findViewById(R.id.menuinfo_hours_friday);
        hourSaturday = itemView.findViewById(R.id.menuinfo_hours_saturday);
        hourSunday = itemView.findViewById(R.id.menuinfo_hours_sunday);
        hourDetail = itemView.findViewById(R.id.menuinfo_hours_detail);
        button = itemView.findViewById(R.id.menuinfo_hours_button);
        this.context = context;

    }
    public void bind(MenuItem menuItem) {
        hourTitle.setText(menuItem.getHourTitle());
        hourMonday.setText(menuItem.getHourMonday());
        hourTuesday.setText(menuItem.getHourTuesday());
        hourWednesday.setText(menuItem.getHourWednesday());
        hourThursday.setText(menuItem.getHourThursday());
        hourFriday.setText(menuItem.getHourFriday());
        hourSaturday.setText(menuItem.getHourSaturday());
        hourSunday.setText(menuItem.getHourSunday());
        hourDetail.setText(menuItem.getHourDetail());
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (isNetworkAvailable(context)) {
            new WebScarping().execute();
        } else {
            Toast.makeText(context, "There is no internet connection", Toast.LENGTH_SHORT).show();
        }

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
                e.getStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            hourTitle.setText(hourItem.getHourTitle());
            hourMonday.setText(hourItem.getHourMonday());
            hourTuesday.setText(hourItem.getHourTuesday());
            hourWednesday.setText(hourItem.getHourWednesday());
            hourThursday.setText(hourItem.getHourThursday());
            hourFriday.setText(hourItem.getHourFriday());
            hourSaturday.setText(hourItem.getHourSaturday());
            hourSunday.setText(hourItem.getHourSunday());
            hourDetail.setText(hourItem.getHourDetail());
        }


    }
    // Check network connection
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

}

