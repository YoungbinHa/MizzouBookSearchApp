package com.example.booksearchapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


public class MenuItem implements Parcelable, Serializable {

    // Hours variables
    private String hourTitle;
    private String hourMonday;
    private String hourTuesday;
    private String hourWednesday;
    private String hourThursday;
    private String hourFriday;
    private String hourSaturday;
    private String hourSunday;
    private String hourDetail;

    // Check view holder
    private boolean hours;
    private boolean location;
    private boolean contactUs;
    private boolean updatedDate;

    // Getter and setter for checking variables
    public boolean isHours() {
        return hours;
    }

    public void setHours(boolean hours) {
        this.hours = hours;
    }

    public boolean isLocation() {
        return location;
    }

    public void setLocation(boolean location) {
        this.location = location;
    }

    public boolean isContactUs() {
        return contactUs;
    }

    public void setContactUs(boolean contactUs) {
        this.contactUs = contactUs;
    }

    public boolean isUpdateddate() {
        return updatedDate;
    }

    public void setUpdateddate(boolean updateddate) {
        this.updatedDate = updateddate;
    }


    public MenuItem() {

    }

    public MenuItem(Parcel in) {
        hourTitle = in.readString();
        hourMonday = in.readString();
        hourTuesday = in.readString();
        hourWednesday = in.readString();
        hourThursday = in.readString();
        hourFriday = in.readString();
        hourSaturday = in.readString();
        hourSunday = in.readString();
        hourDetail = in.readString();
    }

    // Getter and setter
    public String getHourTitle() {
        return hourTitle;
    }

    public void setHourTitle(String hourTitle) {
        this.hourTitle = hourTitle;
    }

    public String getHourMonday() {
        return hourMonday;
    }

    public void setHourMonday(String hourMonday) {
        this.hourMonday = hourMonday;
    }

    public String getHourTuesday() {
        return hourTuesday;
    }

    public void setHourTuesday(String hourTuesday) {
        this.hourTuesday = hourTuesday;
    }

    public String getHourWednesday() {
        return hourWednesday;
    }

    public void setHourWednesday(String hourWednesday) {
        this.hourWednesday = hourWednesday;
    }

    public String getHourThursday() {
        return hourThursday;
    }

    public void setHourThursday(String hourThursday) {
        this.hourThursday = hourThursday;
    }

    public String getHourFriday() {
        return hourFriday;
    }

    public void setHourFriday(String hourFriday) {
        this.hourFriday = hourFriday;
    }

    public String getHourSaturday() {
        return hourSaturday;
    }

    public void setHourSaturday(String hourSaturday) {
        this.hourSaturday = hourSaturday;
    }

    public String getHourSunday() {
        return hourSunday;
    }

    public void setHourSunday(String hourSunday) {
        this.hourSunday = hourSunday;
    }

    public String getHourDetail() {
        return hourDetail;
    }

    public void setHourDetail(String hourDetail) {
        this.hourDetail = hourDetail;
    }

    public static final Creator<MenuItem> CREATOR = new Creator<MenuItem>() {
        @Override
        public MenuItem createFromParcel(Parcel in) {
            return new MenuItem(in);
        }

        @Override
        public MenuItem[] newArray(int size) {
            return new MenuItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hourTitle);
        dest.writeString(hourMonday);
        dest.writeString(hourTuesday);
        dest.writeString(hourWednesday);
        dest.writeString(hourThursday);
        dest.writeString(hourFriday);
        dest.writeString(hourSaturday);
        dest.writeString(hourSunday);
        dest.writeString(hourDetail);
    }

}
