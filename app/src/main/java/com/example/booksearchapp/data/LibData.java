package com.example.booksearchapp.data;


import android.os.Parcel;
import android.os.Parcelable;

public class LibData implements Parcelable {
    private int id;
    private String floor;
    private String range;
    private String beginning;
    private String ending;
    private String map;
    private String text;
    private int favorite = 0;
    private String dateAndTime;
    private boolean isSection = false;
    private String searchText;

    public LibData() { }

    private LibData(Parcel in) {
        this.id = in.readInt();
        this.floor = in.readString();
        this.range = in.readString();
        this.beginning = in.readString();
        this.ending = in.readString();
        this.map = in.readString();
        this.text = in.readString();
        this.favorite = in.readInt();
        this.dateAndTime = in.readString();
        this.searchText = in.readString();
    }

    public LibData(String dateAndTime, boolean isSection) {
        this.dateAndTime = dateAndTime;
        this.isSection = isSection;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getBeginning() {
        return beginning;
    }

    public void setBeginning(String beginning) {
        this.beginning = beginning;
    }

    public String getEnding() {
        return ending;
    }

    public void setEnding(String ending) {
        this.ending = ending;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public boolean isSection() {
        return isSection;
    }

    public void setSection(boolean section) {
        isSection = section;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    // Parcel part

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.floor);
        dest.writeString(this.range);
        dest.writeString(this.beginning);
        dest.writeString(this.ending);
        dest.writeString(this.map);
        dest.writeString(this.text);
        dest.writeInt(this.favorite);
        dest.writeString(this.dateAndTime);
        dest.writeString(this.searchText);
    }

    public static final Parcelable.Creator<LibData> CREATOR = new Creator<LibData>(){
        @Override
        public LibData createFromParcel(Parcel source) {
            return new LibData(source);
        }

        @Override
        public LibData[] newArray(int size) {
            return new LibData[size];
        }
    };
}
