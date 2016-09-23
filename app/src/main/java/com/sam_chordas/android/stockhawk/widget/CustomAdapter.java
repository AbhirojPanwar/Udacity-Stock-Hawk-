package com.sam_chordas.android.stockhawk.widget;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.sam_chordas.android.stockhawk.R;

/**
 * Created by Abhiroj on 9/20/2016.
 */
public class CustomAdapter extends CursorAdapter {

    public CustomAdapter(Context context,Cursor cursor){
        super(context,cursor,0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.simple_layout, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView symbol=(TextView) view.findViewById(R.id.stock_symbol);
        TextView change=(TextView) view.findViewById(R.id.change);
    }
}
