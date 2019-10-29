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
All book codes and location image displays on here are made by myself which means it is not real data and also not the images that University provided me when I started to develope this app as a IT student Assistant in Library Tech Service Team. <br> <br>
I got the data of the hours, location, and contacts of ellis library are from [Official Mizzou Library Website](http://library.missouri.edu/), some are by webscraping, some are just screenshots.

## Contents
- [Feature](#feature)
  - [Basic UI](#basic-ui)
  - [More UI](#more-ui)
  - [SQLiteDataBase](#sqlitedatabase)
  - [Search Function](#search-function)
  - [WebScraping](#webscraping)
- [Improvements](#improvements)
- [Support](#support)
- [License](#license)

## Feature
  ### Basic UI<br>
  <img src="screenshots/basicui.gif" width="300" height="500" /> <br>
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
  ### More UI<br>
  Explain Here<br>
  <img src="screenshots/moreui.gif" width="300" height="500" /> <br>

  ### SQLiteDataBase<br>
  Save all the csv file which contains all books' location to local database by using sqlitedatabse<br>
  <br>*example of csv file* <br> <img src="screenshots/csvexample.PNG"/> <br>
  ### Search Function<br>
  Search and Touch Event <br>
  <img src="screenshots/searchandtouchevent.gif" width="300" height="500" /> <br>
  Search and Save history <br>
  <img src="screenshots/searchandsavehistory.gif" width="300" height="500" /> <br>

  ### WebScraping<br>
  Explain Here<br>
  <img src="screenshots/wepscraping.gif" width="300" height="500" /> <br>
### Improvements

### Support

### License
This app is a portfolio app for educational purposes
