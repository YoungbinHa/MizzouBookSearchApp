package com.example.booksearchapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksearchapp.R;
import com.example.booksearchapp.adapter.MenuItemAdapter;
import com.example.booksearchapp.data.Menu;
import com.example.booksearchapp.data.MenuItem;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentThree_More.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentThree_More#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentThree_More extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    // RecyclerView variables
    private RecyclerView recyclerView;
    private MenuItemAdapter menuItemAdapter;
    private ArrayList<Menu> menu = new ArrayList<>();

    //Web scarping variables
    private static final String URL = "http://library.missouri.edu/";
    private MenuItem hourItem = new MenuItem();

    private SharedPreferences sharedPreferences;

    public FragmentThree_More() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentThree_More.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentThree_More newInstance(String param1, String param2) {
        FragmentThree_More fragment = new FragmentThree_More();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_three__more, container, false);

        // Web scraping
//        new WebScarping().execute();

        if (getArguments() != null) {
            hourItem = (MenuItem) getArguments().getSerializable("Hour");
        }

        // Set up the menu List
        setMenuItemList();

        // Set up the recycler view
        recyclerView = view.findViewById(R.id.fragmentthree_recyclerview);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                1));

        menuItemAdapter = new MenuItemAdapter(menu);
        recyclerView.setAdapter(menuItemAdapter);
        return view;
    }

    private void setMenuItemList() {
        ArrayList<MenuItem> items = new ArrayList<>();
        hourItem.setHours(true);
        items.add(hourItem);
        Menu library_Hours = new Menu("Library Hours", items);

        ArrayList<MenuItem> items2 = new ArrayList<>();
        MenuItem menuItem2 = new MenuItem();
        menuItem2.setLocation(true);
        items2.add(menuItem2);
        Menu location = new Menu("Location", items2);

        ArrayList<MenuItem> items3 = new ArrayList<>();
        MenuItem menuItem3 = new MenuItem();
        menuItem3.setContactUs(true);
        items3.add(menuItem3);
        Menu contact_Us = new Menu("Contact Us", items3);

        ArrayList<MenuItem> items4 = new ArrayList<>();
        MenuItem menuItem4 = new MenuItem();
        menuItem4.setUpdateddate(true);
        items4.add(menuItem4);
        Menu version = new Menu("About", items4);

        menu.add(library_Hours);
        menu.add(location);
        menu.add(contact_Us);
        menu.add(version);
    }

//    public String getLastModifiedDate() {
//        File file = new File(getContext().getFilesDir().getAbsolutePath() + "/file1.jpg");
//        if (file.exists()) {
//            Date lastModified = new Date(file.lastModified());
//            return lastModified.toString();
//        } else {
//            return "";
//        }
//    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
//
//    // web scraping class
//    public class WebScarping extends AsyncTask<Void, Void, Void> {
//        String key = "hours/";
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            try {
//                // Scarping web resources by using Jsoup
//                Document doc = Jsoup.connect(URL + key).get();
//
//                // Set hour table title from web scarping data
//                Elements hourTitle = doc.select("#hours .hours-content .hours-name");
//                hourItem.setHourTitle(hourTitle.text());
//
//                // Set hours for a week from web scarping data
//                Elements hours = doc.select("#hours .hours-content ul .hours-time");
//                Elements hoursMessage = doc.select("#hours .hours-content ul .hours-time .dailymessage");
//                hourItem.setHourMonday(hours.eq(0).text().split("Open|Close")[0] + "\n" + hoursMessage.eq(0).text().split(", ")[0]);
//                hourItem.setHourTuesday(hours.eq(1).text().split("Open|Close")[0] + "\n" + hoursMessage.eq(1).text().split(", ")[0]);
//                hourItem.setHourWednesday(hours.eq(2).text().split("Open|Close")[0] + "\n" + hoursMessage.eq(2).text().split(", ")[0]);
//                hourItem.setHourThursday(hours.eq(3).text().split("Open|Close")[0] + "\n" + hoursMessage.eq(3).text().split(", ")[0]);
//                hourItem.setHourFriday(hours.eq(4).text().split("Open|Close")[0] + "\n" + hoursMessage.eq(4).text().split(", ")[0]);
//                hourItem.setHourSaturday(hours.eq(5).text().split("Open|Close")[0] + "\n" + hoursMessage.eq(5).text().split(", ")[0]);
//                hourItem.setHourSunday(hours.eq(6).text().split("Open|Close")[0] + "\n" + hoursMessage.eq(6).text().split(", ")[0]);
//
//                // Set Details from web scarping data
//                Elements hourDetail = doc.select("#hours #hours-break");
//                hourItem.setHourDetail(hours.eq(6).text().split(", ")[1]+ "\n" +hourDetail.text());
//            } catch (IOException e) {
//                e.getStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//        }
//
//
//    }
}
