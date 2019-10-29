# MizzouBookSearchApp
Since Ellis library is a huge library, in the University of Missouri - Columbia, that holds millions of books with maze-like structure and complicated book code system, sometimes students are having a hard time to find a book what they are looking for.<br> <br>
This is a BookFinder App for Students that can help them find books more easily and able to save their search history. Moreover, it supports library's hours and location with contact info.<br>
<img src="screenshots/screenshots_one.png" width="280" height="400" />
<img src="screenshots/screenshots_two.png" width="280" height="400" />
<img src="screenshots/screenshots_three.png" width="280" height="400" />
<img src="screenshots/screenshots_four.png" width="280" height="400" />
<img src="screenshots/fscreenshots_five.png" width="280" height="400" />
<img src="screenshots/screenshots_six.png" width="280" height="400" />


## Note
All images and data that contains book's location(+ all drawable images) are not added on this repository because of security reasons and copy right.<br> <br> 
All book codes and location image displays on here are made by myself or outdated data which means it could be wrong info and also not the images that University provided me when I started to develope this app as a IT student Assistant in Library Tech Service Team. <br> <br>
I got the data of the hours, location, and contacts of ellis library are from [Official Mizzou Library Website](http://library.missouri.edu/), some are by webscraping, some are just screenshots.

## Contents
- [Feature](#feature)
  - [Basic UI](#basic-ui)
  - [More UI](#more-ui)
  - [SQLiteDataBase](#sqlitedatabase)
  - [Search Function](#search-function)
  - [WebScraping](#webscraping)
- [Improvements](#improvements)
- [References](#references)
- [License](#license)

## Feature
  ### Basic UI<br>
  <img alight = "center" src="screenshots/basicui.gif" width="300" height="500"/>
  
  Basic UI contains with three fragment with BottomNavigationView. I used fragmentTransaction class to easily replace fragment in mobile view. Views consist of recyclerview, and each view contains book's location except in more fragment. <br> <br>
  
  ``` java
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
  ```
  
  For history UI, since it has two parts, header(time) and data, build two viewholder, item and header, and create view depends on a boolean type variable, isSection, in Libdata value class.
 
 ``` java
 public class SectionedRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int SEARCHDATE = 0;
    public static final int LIBDATA = 1;

    ...
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
 ```
  
  
  ### More UI<br>
  <img src="screenshots/moreui.gif" width="300" height="500" /> <br>
  Use [Third party library](https://github.com/thoughtbot/expandable-recycler-view) to implement expandable recyclerview with arrow mark. <br> <br>
  Similar steps with sectioned view in history view. Build four different view holder, hours, location, contact us, about, so that depends on view type what users touch, create different types of viewholder.
  
  ``` java
    public class MenuItemAdapter extends MultiTypeExpandableRecyclerViewAdapter<MenuViewHolder, ChildViewHolder> {
    public static final int MENUITEM_HOUR = 3;
    public static final int MENUITEM_LOCATION = 4;
    public static final int MENUITEM_CONTACTUS = 5;
    public static final int MENUITEM_UPDATEDDATE = 6;
    
    ...

    @Override
    public int getChildViewType(int position, ExpandableGroup group, int childIndex) {
        if (((Menu)group).getItems().get(childIndex).isHours()) {
            return MENUITEM_HOUR;
        } else if (((Menu)group).getItems().get(childIndex).isLocation()) {
            return MENUITEM_LOCATION;
        } else if (((Menu)group).getItems().get(childIndex).isContactUs()) {
            return MENUITEM_CONTACTUS;
        } else {
            return MENUITEM_UPDATEDDATE;
        }
    }
    
  ...

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case MENUITEM_HOUR:
                View hours = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.expandable_recyclerview_menuinfo_hour, parent, false);
                return new MenuItemHourViewHolder(hours, parent.getContext());
            case MENUITEM_LOCATION:
                View location = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.expandable_recyclerview_menuinfo_location, parent, false);
                return new MenuItemLocationViewHolder(location);
            case MENUITEM_CONTACTUS:
                View contactUs = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.expandable_recyclerview_menuinfo_contactus, parent, false);
                return new MenuItemContactUsViewHolder(contactUs);
            case MENUITEM_UPDATEDDATE:
                View updatedDate = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.expandable_recyclerview_menuinfo_updatedate, parent, false);
                return new MenuItemUpdatedDateViewHolder(updatedDate, parent.getContext());
            default:
                throw new IllegalArgumentException("Invalid viewType");
        }
    }
    
  ...
}

  
  ```
  Also, to make image zoom in and out, I use [another library](https://github.com/davemorrissey/subsampling-scale-image-view) to make users able to see the location image more clearly.
  
  ### SQLiteDataBase<br>
  Save all the csv file which contains all books' location to local database by using sqlitedatabse<br>
  <br>*example of csv file* <br> <img src="screenshots/csvexample.PNG"/> <br> <br>
  Build DBhelper class that creates databases that holds all csv file and holds search history. the class also contains search functions with sql query. <br> <br>
  \*creat two databases, one for store all data in csv file, and the other one is for history.
  ``` java
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
  ```
  
  \*Parse csv file and store data into database
  ``` java
  
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
    
  ```
  
  ### Search Function<br>
  #### Search and Touch Event <br>
  <img src="screenshots/searchandtouchevent.gif" width="300" height="500" /> <br> <br>
  Function filter gets a string from edittext and filters data depending on if the string is in range of beginning and ending book code by alphabetical order. <br>
  ``` java
  private void filter(String text) {
        libDataFiltered = new ArrayList<>();

        for (LibData temp : libData) {
            if (temp.getBeginning().contains(text.toUpperCase())
                    || temp.getEnding().compareTo(text.toUpperCase()) >= 0
                    && temp.getBeginning().compareTo(text.toUpperCase()) <= 0) {
                libDataFiltered.add(temp);
            }
        }
        mlibDataAdapter.filterList(libDataFiltered);
        // Set onItemClickListener
        mlibDataAdapter.setOnClick(this);
        mlibDataAdapter.setOnImageClickClick(this);
    }
  ```
  
  Also when users touchs a item, onItemClick Listener operates and switch their views to new activity(view) that contains book information with location image and text. <br>
  ``` java
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

  ```
  
  #### Search and Save history <br>
  <img src="screenshots/searchandsavehistory.gif" width="300" height="500" /> <br>

  When a history image button is touched by users, onImageClick listener operates. <br>
  ``` java
  libDataViewHolder.bookcodeitem_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageClick.onImageClicked(i);
            }
        });
  ```
  
  The listener saves data into history database with favorite column = 1 so that it can be distinguised which is not. <br>
  ```java
  @Override
    public void onImageClicked(int position) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy '('EEE')' HH:mm a", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());
        String empty = "";
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
          
          ...
    }

  ```
  In DBhelper class, findAllFavorite function get all the data that favorite column = 1 so that saved data in history database can be shown in screen. <br>
  ``` java 
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
  ```
  

  ### WebScraping<br>
  <img src="screenshots/wepscraping.gif" width="300" height="500" /> <br>
  For getting latest hours of library, I use [Jsoup](https://jsoup.org/) which is an open souce api for extracting and manipulating data, using the best of DOM, CSS, and jquery-like methods. For location and contact, I simply take screenshots and shows it on the screen. <br> <br>
  Since it is related with network jobs, I build a class that extends AsyncTask so that webscraping works in background not a main thread. I can simply execute it like <br>
  ``` java
  new WebScarping().execute();
  ```
  and the class looks like
  ``` java
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
                hourItem.setHourMonday("12:00 am - 5:00 pm");
                hourItem.setHourTuesday("12:00 am - 5:00 pm");
                hourItem.setHourWednesday("12:00 am - 5:00 pm");
                hourItem.setHourThursday("12:00 am - 5:00 pm");
                hourItem.setHourFriday("12:00 am - 5:00 pm");
                hourItem.setHourSaturday("12:00 am - 5:00 pm");
                hourItem.setHourSunday("12:00 am - 5:00 pm");
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
  ```

## Improvements
**1. Data Normalization** <br>
Since the two databases with main and history are very related and contains duplicates columne a lot, I can try to build database more efficiently by making all the column unique and less dependency with each other so that make the databases more stable and also less error. <br> <br>

**2. Server Database** <br>
The location of the books is not constant, and it changes a lot; therefore, users may be misdirected to the wrong place since the image on the screen could be outdated. By setting up a database in a server, the app could get updated location from the server.

## References
I used serveral thrid party libraries. This app is built only for educational purposes.
- [Jsoup](https://jsoup.org/)
- [Subsampling scale imageview](https://github.com/davemorrissey/subsampling-scale-image-view)
- [Expandable recyclerview](https://github.com/thoughtbot/expandable-recycler-view)
- And many information from [StackOverFlow](https://stackoverflow.com/questions)

## License
MizzouBookFinder is Copyright (c) 2019 Youngbin Ha (youngbin567@gmail.com). It is free software, and may be redistributed under the terms specified in the [LICENSE] file.

[LICENSE]: /LICENSE
