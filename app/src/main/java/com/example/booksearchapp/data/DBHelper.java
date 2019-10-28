package com.example.booksearchapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.booksearchapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static androidx.test.InstrumentationRegistry.getContext;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 21;
    //DB variable names
    private static String dbName = "libdata_db";
    private static String tableName = "tb_libdata";
    private static String tableName2 = "tb_libdata_history";

    private static String idColumn = "_id";
    private static String floorColumn = "floor";
    private static String rangeColumn = "range";
    private static String beginningColumn = "beginning";
    private static String endingColumn = "ending";
    private static String mapColumn = "map";
    private static String textColumn = "text";
    //-------------
    private static String favoriteColumn = "favorite";
    //-------------
    //-------------
    private static String currentDateAndTimeColumn = "date_and_time";
    //-------------

    //-------------
    private static String searchTextColumn = "search_text";
    //-------------
    private static String historyIdColumn = "_history_id";

    private Context context;

    public DBHelper(Context context) {
        super(context, dbName, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //original tablesql
        String tableSql = "create table if not exists " + tableName + "(" +
                idColumn + " integer primary key autoincrement,  " +
                floorColumn + " text, " +
                rangeColumn + " text, " +
                beginningColumn + " text, " +
                endingColumn + " text, " +
                mapColumn + " text, " +
                textColumn + " text " +
                ")";


        String tableSql2 = "create table if not exists " + tableName2 + "(" +
                idColumn + " integer, " +
                floorColumn + " text, " +
                rangeColumn + " text, " +
                beginningColumn + " text, " +
                endingColumn + " text, " +
                mapColumn + " text, " +
                textColumn + " text, " +
                favoriteColumn + " integer default 0, " +
                currentDateAndTimeColumn + " text default '', " +
                searchTextColumn + " text default '', " +
                historyIdColumn + " integer primary key autoincrement " +
                ")";
        db.execSQL(tableSql);
        db.execSQL(tableSql2);
        // Set up Database and get all the data
        setDataBase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + tableName);
        db.execSQL("drop table if exists " + tableName2);
        onCreate(db);
    }

    public List<LibData> findAll() {
        List<LibData> data = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from " + tableName, null);
            if (cursor.moveToFirst()) {
                data = new ArrayList<>();
                do {
                    LibData libData = new LibData();
                    libData.setId(cursor.getInt(0));
                    libData.setFloor(cursor.getString(1));
                    libData.setRange(cursor.getString(2));
                    libData.setBeginning(cursor.getString(3));
                    libData.setEnding(cursor.getString(4));
                    libData.setMap(cursor.getString(5));
                    libData.setText(cursor.getString(6));
                    data.add(libData);
                } while (cursor.moveToNext());
            }
            db.close();
        } catch (Exception e) {
            data = null;
            e.printStackTrace();
        }
        return data;
    }
// findAllFavoriteChecked
//    public List<LibData> findAllFavoritechecked() {
//        List<LibData> data = null;
//        try {
//            SQLiteDatabase db = getReadableDatabase();
//            Cursor cursor = db.rawQuery("select * from " + tableName + " where " + favoriteColumn + " = 1", null);
//            if (cursor.moveToFirst()) {
//                data = new ArrayList<>();
//                do{
//                    LibData libData = new LibData();
//                    libData.setId(cursor.getInt(0));
//                    libData.setFloor(cursor.getString(1));
//                    libData.setRange(cursor.getString(2));
//                    libData.setBeginning(cursor.getString(3));
//                    libData.setEnding(cursor.getString(4));
//                    libData.setMap(cursor.getString(5));
//                    libData.setText(cursor.getString(6));
//                    libData.setFavorite(cursor.getInt(7));
//                    libData.setDateAndTime(cursor.getString(8));
//                    libData.setSearchText(cursor.getString(9));
//                    data.add(libData);
//                }while (cursor.moveToNext());
//            }
//
//
//            db.close();
//        } catch (Exception e) {
//            data = null;
//            e.printStackTrace();
//        }
//        return data;
//    }

    public List<LibData> findAllFavoritechecked2() {
        List<LibData> data = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from " + tableName2 + " where " + favoriteColumn + " = 1", null);
            if (cursor.moveToFirst()) {
                data = new ArrayList<>();
                do{
                    LibData libData = new LibData();
                    libData.setId(cursor.getInt(10));
                    libData.setFloor(cursor.getString(1));
                    libData.setRange(cursor.getString(2));
                    libData.setBeginning(cursor.getString(3));
                    libData.setEnding(cursor.getString(4));
                    libData.setMap(cursor.getString(5));
                    libData.setText(cursor.getString(6));
                    libData.setFavorite(cursor.getInt(7));
                    libData.setDateAndTime(cursor.getString(8));
                    libData.setSearchText(cursor.getString(9));
                    data.add(libData);
                }while (cursor.moveToNext());
            }
            db.close();
        } catch (Exception e) {
            data = null;
            e.printStackTrace();
        }
        return data;
    }

    private void setDataBase(SQLiteDatabase db) {
        InputStream is = context.getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line;
        db.beginTransaction();
        try {
            while ((line = reader.readLine()) != null) {
                String[] colums = line.split(",");
                if (colums.length != 6) {
                    Log.d("CSVParser", "Skipping Bad CSV Row");
                    continue;
                }
                ContentValues cv = new ContentValues(3);
                cv.put(floorColumn, colums[0].trim());
                cv.put(rangeColumn, colums[1].trim());
                cv.put(beginningColumn, colums[2].trim());
                cv.put(endingColumn, colums[3].trim());
                cv.put(mapColumn, colums[4].trim());
                cv.put(textColumn, colums[5].trim());
                db.insert(tableName, null, cv);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();

    }
}
