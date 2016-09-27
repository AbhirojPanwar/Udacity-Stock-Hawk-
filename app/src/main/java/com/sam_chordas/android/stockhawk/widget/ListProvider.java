package com.sam_chordas.android.stockhawk.widget;

import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.data.WidgetDataHolder;

import java.util.ArrayList;

/**
 * Created by Abhiroj on 9/23/2016.
 */
public class ListProvider implements RemoteViewsService.RemoteViewsFactory {

    Context context=null;
    Uri uri= QuoteProvider.Quotes.CONTENT_URI;
    String[] projection = new String[]{QuoteColumns.SYMBOL,QuoteColumns.CHANGE};
    ContentResolver contentResolver;
    Cursor cursor;
    ArrayList<WidgetDataHolder> widgetDataHolders;
    int appWidgetId;

    ListProvider(Context context, Intent intent)
    {
        this.context=context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        contentResolver=context.getContentResolver();
        cursor=contentResolver.query(uri,projection,null,null,null);
       widgetDataHolders=new ArrayList<>();
    }


    @Override
    public void onCreate() {
        if(cursor!=null) {
            int i=0;
            int total=cursor.getCount();
         cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                try {
                    cursor.moveToNext();
                    WidgetDataHolder item=new WidgetDataHolder();
                    item.symbol=cursor.getString(cursor.getColumnIndex(QuoteColumns.SYMBOL));
                    item.current=cursor.getString(cursor.getColumnIndex(QuoteColumns.CHANGE));
                    widgetDataHolders.add(i++,item);
                } catch (Exception e) {
                    Log.e("DB Source Exception", "Google it!! It is ListProvider");
                }
            }
        }

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return widgetDataHolders.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        WidgetDataHolder holder=widgetDataHolders.get(i);
        RemoteViews remoteViews=new RemoteViews(context.getPackageName(), R.layout.simple_layout);
        remoteViews.setTextViewText(R.id.stock_symbol,holder.symbol);
        remoteViews.setTextViewText(R.id.bid_price,holder.current);

        cursor.close();

  return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
