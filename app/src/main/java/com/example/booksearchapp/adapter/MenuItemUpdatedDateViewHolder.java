/*
 * Copyright (c) 2019. Youngbin Ha youngbin567@gmail.com
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. Last modified 10/15/19 2:01 PM
 */

package com.example.booksearchapp.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.booksearchapp.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import static android.provider.Settings.System.getString;

public class MenuItemUpdatedDateViewHolder extends ChildViewHolder {
    private TextView textView;
    private Context context;
    public MenuItemUpdatedDateViewHolder(View itemView, Context context) {
        super(itemView);
        textView = itemView.findViewById(R.id.menuinfo_updated_date);
        this.context = context;

    }

    public void setTextView(String str){
        Resources res = context.getResources();
        String text;
        if (str.length() == 0) {
            text = String.format(res.getString(R.string.current_version), "9/19/2019");
        } else {
            text = String.format(res.getString(R.string.current_version), str);
        }
        textView.setText(text);
    }
}
