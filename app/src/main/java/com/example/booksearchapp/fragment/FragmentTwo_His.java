package com.example.booksearchapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksearchapp.DataClickedActivity;
import com.example.booksearchapp.R;
import com.example.booksearchapp.adapter.LibDataAdapter;
import com.example.booksearchapp.adapter.SectionedRecyclerViewAdapter;
import com.example.booksearchapp.data.DBHelper;
import com.example.booksearchapp.data.LibData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentTwo_His.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentTwo_His#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTwo_His extends Fragment implements LibDataAdapter.OnItemClicked, LibDataAdapter.OnImageClicked {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String LIBDATA_KEY = "l01";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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
    private SectionedRecyclerViewAdapter sectionedRecyclerViewAdapter;
    private LibDataAdapter mlibDataAdapter;
    private RecyclerView recyclerView;
    private EditText searchView;
    private List<LibData> libData = new ArrayList<>();
    private List<LibData> libDataFiltered;
    private List<LibData> libDataFavoriteFiltered;
    private List<LibData> headerAddedlist = new ArrayList<>();


    // DB related variables
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public FragmentTwo_His() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTwo_His.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTwo_His newInstance(String param1, String param2) {
        FragmentTwo_His fragment = new FragmentTwo_His();
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
        View view = inflater.inflate(R.layout.fragment_fragment_two__his, container, false);

        // Db set up
        dbHelper = new DBHelper(getActivity());
        sqLiteDatabase = dbHelper.getWritableDatabase();

        // RecyclerView set up
        recyclerView = view.findViewById(R.id.fragmentTwo_recyclerview);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                0));
        DBHelper dbHelper = new DBHelper(getContext());
        libData = dbHelper.findAllFavoritechecked2();

        if (libData != null) {

            setComparator(libData);

            // Apply adapter
            sectionedRecyclerViewAdapter = new SectionedRecyclerViewAdapter(headerAddedlist, getContext());

            // Set up header section
            recyclerView.setAdapter(sectionedRecyclerViewAdapter);

            // Set up all the listener
            sectionedRecyclerViewAdapter.setOnClick(this);
            sectionedRecyclerViewAdapter.setOnImageClickClick(this);
        }
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
    }

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

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), DataClickedActivity.class);
        intent.putExtra(LIBDATA_KEY, headerAddedlist.get(position));
        startActivity(intent);
    }

    @Override
    public void onImageClicked(int position) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy '('EEE')' HH:mm a", Locale.getDefault());
        if (headerAddedlist.get(position).getFavorite() == 0) {
            headerAddedlist.get(position).setSection(false);
            sqLiteDatabase.execSQL("UPDATE " + tableName2 + " SET " + favoriteColumn + " = 0" + " WHERE " + historyIdColumn + " = " + headerAddedlist.get(position).getId());
        } else if (headerAddedlist.get(position).getFavorite() == 1) {
            headerAddedlist.get(position).setFavorite(1);
            sqLiteDatabase.execSQL("UPDATE " + tableName2 + " SET " + favoriteColumn + " = 1" + " WHERE " + historyIdColumn + " = " + headerAddedlist.get(position).getId());
        }
    }

    @Override
    public void onResume() {
        sqLiteDatabase.execSQL("DELETE FROM " + tableName2 + " WHERE " + favoriteColumn + " = 0");
        super.onResume();
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

    private void setComparator(List<LibData> list) {
        Collections.sort(list, new Comparator<LibData>() {
            @Override
            public int compare(LibData o1, LibData o2) {
                if (o1 == null || o2 == null) {
                    return 0;
                }
                String s1 = o1.getDateAndTime();
                String s2 = o2.getDateAndTime();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy '('EEE')' hh:mm a");

                try {
                    Date date1 = sdf.parse(s1);
                    Date date2 = sdf.parse(s2);

                    return date2.compareTo(date1);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return 0;

            }
        });

        String checkDate = "";
        String dataDateAndTime;
        String dataDate;


        for (LibData data : libData) {
            dataDateAndTime = data.getDateAndTime();
            if (dataDateAndTime == null) {
                continue;
            }
            dataDate = dataDateAndTime.split(" ")[0];
            if (!dataDate.equals(checkDate)) {
                checkDate = dataDate;
                LibData header = new LibData(dataDateAndTime, true);
                headerAddedlist.add(header);
            }

            headerAddedlist.add(data);
        }
    }
}
