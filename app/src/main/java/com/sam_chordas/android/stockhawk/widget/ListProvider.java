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

/**
 * Created by Abhiroj on 9/23/2016.
 */
public class ListProvider implements RemoteViewsService.RemoteViewsFactory {

    Context context=null;
    int appWidgetId;

    ListProvider(Context context, Intent intent)
    {
        this.context=context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        String[] projection = new String[]{QuoteColumns._ID, QuoteColumns.SYMBOL,QuoteColumns.ISCURRENT};
        Uri uri= QuoteProvider.Quotes.CONTENT_URI;
        ContentResolver contentResolver=context.getContentResolver();
        Cursor cursor=contentResolver.query(uri,projection,null,null,null);
        try {
            cursor.moveToPosition(i);
        }
        catch (Exception e)
        {
            Log.e("DB Source Excetion","Google it!! It is ListProvider");
        }
        RemoteViews remoteViews=new RemoteViews(context.getPackageName(), R.layout.simple_layout);
        remoteViews.setTextViewText(R.id.stock_symbol,cursor.getString(cursor.getColumnIndex(QuoteColumns.SYMBOL)));
        remoteViews.setTextViewText(R.id.bid_price,cursor.getString(cursor.getColumnIndex(QuoteColumns.ISCURRENT)));

        cursor.close();

  return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
