package com.example.booksearchapp.data;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Menu extends ExpandableGroup<MenuItem> {

    public Menu(String title, List<MenuItem> items) {
        super(title, items);
    }
}
