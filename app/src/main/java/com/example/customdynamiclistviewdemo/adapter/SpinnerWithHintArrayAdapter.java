package com.example.customdynamiclistviewdemo.adapter;

/**
 * Created by Promlert on 2017-01-21.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerWithHintArrayAdapter extends ArrayAdapter<String> {

    public SpinnerWithHintArrayAdapter(Context context, int resource, String[] items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);

        if (position == getCount()) {
            ((TextView) v.findViewById(android.R.id.text1)).setText("");
            ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount()));
        }
        return v;
    }

    @Override
    public int getCount() {
        return super.getCount() - 1;
    }
}