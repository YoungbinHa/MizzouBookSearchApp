package com.example.booksearchapp.fragment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksearchapp.DataClickedActivity;
import com.example.booksearchapp.MainActivity;
import com.example.booksearchapp.R;
import com.example.booksearchapp.adapter.LibDataAdapter;
import com.example.booksearchapp.data.DBHelper;
import com.example.booksearchapp.data.LibData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentOne_Home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentOne_Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentOne_Home extends Fragment implements LibDataAdapter.OnItemClicked, LibDataAdapter.OnImageClicked {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String LIBDATA_KEY = "l01";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //DB variables
    private static String dbName = "libdata_db";
    private static String tableName = "tb_libdata";

    private static String idColumn = "_id";
    private static String floorColumn = "floor";
    private static String rangeColumn = "range";
    private static String beginningColumn = "beginning";
    private static String endingColumn = "ending";
    private static String mapColumn = "map";
    private static String textColumn = "text";
    private static String favoriteColumn = "favorite";
    private static String currentDateAndTimeColumn = "date_and_time";
    private static String searchTextColumn = "search_text";
    private static String tableName2 = "tb_libdata_history";
    private static String historyIdColumn = "_history_id";

    // View with Adapter and Data
    private LibDataAdapter mlibDataAdapter;
    private RecyclerView recyclerView;
    private EditText searchView;
    private List<LibData> libData = new ArrayList<>();
    private List<LibData> libDataFiltered;
    private List<LibData> libDataFavoriteFiltered;

    //    //------------
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    //    //------------
    private final String TAG = "FragmentOne";
    private String mLastQuery = "";


    public FragmentOne_Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentOne_Home.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentOne_Home newInstance(String param1, String param2) {
        FragmentOne_Home fragment = new FragmentOne_Home();
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

        // Db set up
        dbHelper = new DBHelper(getActivity());
        sqLiteDatabase = dbHelper.getWritableDatabase();

        // Inflate the layout for this fragment and find id
        View view = inflater.inflate(R.layout.fragment_fragment_one__home, container, false);

        // FindViewById
        recyclerView = view.findViewById(R.id.fragmentone_recyclerview);
        searchView = view.findViewById(R.id.fragmentone_edittext);


        // Set up recyclerView
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                0));


        DBHelper dbHelper = new DBHelper(getContext());
        libData = dbHelper.findAll();

        // Apply adapter
        if (libData != null) {
            mlibDataAdapter = new LibDataAdapter(getContext(), R.layout.bookcodeitem_recycler_view, libData);

            recyclerView.setAdapter(mlibDataAdapter);
        }

        // Set up addTextChangeListener
        searchView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        // Set onItemClickListener
        mlibDataAdapter.setOnClick(this);

        //-----------
        mlibDataAdapter.setOnImageClickClick(this);
        //-----------

        // Save and reload edittext value between fragments

        return view;
    }

    private void filter(String text) {
        libDataFiltered = new ArrayList<>();

        for (LibData temp : libData) {
            if (temp.getBeginning().contains(text.toUpperCase()) || temp.getEnding().compareTo(text.toUpperCase()) >= 0 && temp.getBeginning().compareTo(text.toUpperCase()) <= 0) {
                libDataFiltered.add(temp);
            }
        }
        mlibDataAdapter.filterList(libDataFiltered);
        // Set onItemClickListener
        mlibDataAdapter.setOnClick(this);
        mlibDataAdapter.setOnImageClickClick(this);
    }


    @Override
    public void onItemClick(int position) {
        if (searchView.getText().length() == 0) {
            Intent intent = new Intent(getContext(), DataClickedActivity.class);
            intent.putExtra(LIBDATA_KEY, libData.get(position));
            startActivity(intent);
        } else if (libDataFiltered != null) {
            Intent intent = new Intent(getContext(), DataClickedActivity.class);
            intent.putExtra(LIBDATA_KEY, libDataFiltered.get(position));
            startActivity(intent);
        }
    }

    @Override
    public void onImageClicked(int position) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy '('EEE')' HH:mm a", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());
        String empty = "";
        if (searchView.getText().length() == 0) {
                sqLiteDatabase.execSQL(
                        "INSERT INTO " + tableName2 + "(" +
                        idColumn + ", " + floorColumn + ", " + rangeColumn + ", " + beginningColumn + ", " +
                        endingColumn + ", " + mapColumn + ", " + textColumn + ", " + favoriteColumn + ", " +
                        currentDateAndTimeColumn + ", " + searchTextColumn + ")" +
                        " SELECT " + idColumn + ", " + floorColumn + ", " + rangeColumn + ", " + beginningColumn + ", " +
                        endingColumn + ", " + mapColumn + ", " + textColumn + ", 1, '" + currentDateAndTime + "', ''" +
                        " FROM " + tableName +
                        " WHERE " + idColumn + " = " + libData.get(position).getId()
                );

        } else if (libDataFiltered != null) {
            String searchText = searchView.getText().toString().toUpperCase().trim();
                if (searchText.length() >= 2) {
                    sqLiteDatabase.execSQL(
                            "INSERT INTO " + tableName2 + "(" +
                                    idColumn + ", " + floorColumn + ", " + rangeColumn + ", " + beginningColumn + ", " +
                                    endingColumn + ", " + mapColumn + ", " + textColumn + ", " + favoriteColumn + ", " +
                                    currentDateAndTimeColumn + ", " + searchTextColumn + ")" +
                                    " SELECT " + idColumn + ", " + floorColumn + ", " + rangeColumn + ", " + beginningColumn + ", " +
                                    endingColumn + ", " + mapColumn + ", " + textColumn + ", 1, '" + currentDateAndTime + "', '" + searchText + "'" +
                                    " FROM " + tableName +
                                    " WHERE " + idColumn + " = " + libDataFiltered.get(position).getId());
                } else {
                    sqLiteDatabase.execSQL(
                            "INSERT INTO " + tableName2 + "(" +
                                    idColumn + ", " + floorColumn + ", " + rangeColumn + ", " + beginningColumn + ", " +
                                    endingColumn + ", " + mapColumn + ", " + textColumn + ", " + favoriteColumn + ", " +
                                    currentDateAndTimeColumn + ", " + searchTextColumn + ")" +
                                    " SELECT " + idColumn + ", " + floorColumn + ", " + rangeColumn + ", " + beginningColumn + ", " +
                                    endingColumn + ", " + mapColumn + ", " + textColumn + ", 1, '" + currentDateAndTime + "', ''" +
                                    " FROM " + tableName + " WHERE " + idColumn + " = " + libDataFiltered.get(position).getId()
                    );

                }
            }

    }





public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    void onFragmentInteraction(Uri uri);

}

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("searchText", searchView.getText().toString());
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String searchText = sharedPreferences.getString("searchText", "");
        searchView.setText(searchText);
    }


}
