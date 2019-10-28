package com.example.booksearchapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.booksearchapp.R;
import com.example.booksearchapp.data.Menu;
import com.example.booksearchapp.data.MenuItem;
import com.thoughtbot.expandablerecyclerview.MultiTypeExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MenuItemAdapter extends MultiTypeExpandableRecyclerViewAdapter<MenuViewHolder, ChildViewHolder> {
    public static final int MENUITEM_HOUR = 3;
    public static final int MENUITEM_LOCATION = 4;
    public static final int MENUITEM_CONTACTUS = 5;
    public static final int MENUITEM_UPDATEDDATE = 6;

    public MenuItemAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

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

    @Override
    public boolean isChild(int viewType) {
        return viewType == MENUITEM_HOUR || viewType == MENUITEM_LOCATION ||
                viewType == MENUITEM_CONTACTUS || viewType == MENUITEM_UPDATEDDATE;
    }

    @Override
    public MenuViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expandable_recyclerview_menu, parent, false);
        return new MenuViewHolder(v);
    }

    @Override
    public void onBindGroupViewHolder(MenuViewHolder holder, int flatPosition, ExpandableGroup group) {
        final Menu menu = (Menu) group;
        holder.bind(menu);
    }

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

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        int viewType = getItemViewType(flatPosition);
        MenuItem menuItem = ((Menu) group).getItems().get(childIndex);
        switch (viewType) {
            case MENUITEM_HOUR:
                ((MenuItemHourViewHolder) holder).bind(menuItem);
                break;
            case MENUITEM_LOCATION:
                ((MenuItemLocationViewHolder) holder).setmImageView();
                break;
            case MENUITEM_CONTACTUS:
                ((MenuItemContactUsViewHolder) holder).setImageView();
                break;
            case MENUITEM_UPDATEDDATE:
                ((MenuItemUpdatedDateViewHolder) holder).setTextView("");
                break;
            default:
                throw new IllegalArgumentException("Invalid viewType");
        }
    }


}
