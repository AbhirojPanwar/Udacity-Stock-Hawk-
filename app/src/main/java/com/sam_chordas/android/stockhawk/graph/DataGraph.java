package com.sam_chordas.android.stockhawk.graph;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Abhiroj on 9/6/2016.
 */
public class DataGraph extends AppCompatActivity {

    static Context c;

    static  BarChart chart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_graph);

        c=getApplicationContext();
        Intent i=getIntent();
        Bundle rec=i.getExtras();
        int position=rec.getInt("POS");
       // Toast.makeText(getBaseContext(),"POS ="+position,Toast.LENGTH_LONG).show();
         chart=(BarChart) findViewById(R.id.chart);
        chart.setNoDataText("Be a Data analyst in 3..2..1...Go");
        String[] projection = new String[]{QuoteColumns._ID, QuoteColumns.SYMBOL};
        Uri uri= QuoteProvider.Quotes.CONTENT_URI;
        ContentResolver resolver=getContentResolver();
        Cursor cursor=resolver.query(uri,projection,null,null,null);
        try{
            if(cursor!=null)
            {
                cursor.moveToPosition(position);
                String symbol=cursor.getString(cursor.getColumnIndex(QuoteColumns.SYMBOL));
                makeRequest(symbol);
            }
        }catch (Exception e)
        {
            Log.e("Foolishness Exception","Google It, Dumbass!");
        }
        cursor.close();

    }

    public static void makeRequest(String symbol)
    {
        Date date = new Date();
        String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
        String startDate=new SimpleDateFormat("yyyy-MM-dd").format(new DateTime(date).minusMonths(3).toDate());

        String URL ="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20%3D%20%22"+symbol+"%22%20and%20startDate%20%3D%20%22"+startDate+"%22%20and%20endDate%20%3D%20%22"+modifiedDate+"%22&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        JsonObjectRequest jsonRequest=new JsonObjectRequest(Request.Method.GET,URL,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response=response.getJSONObject("query");
                    JSONObject result=response.getJSONObject("results");
                    JSONArray quotes=result.getJSONArray("quote");
                    int len=quotes.length();
                    ArrayList<String> xaxis=new ArrayList<>();
                    ArrayList<BarEntry> valueSet=new ArrayList<>();
                    ArrayList<BarDataSet> dataSets = new ArrayList<>();
                    for (int i=0;i<len;i++)
                    {
                        JSONObject ob=quotes.getJSONObject(i);
                        String start=ob.getString("Date");
                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
                        Date date = null;
                        try {
                            date = (Date)formatter.parse(start);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
                        String finalString = newFormat.format(date);
                        xaxis.add(finalString);
                        Float entry=(float)ob.getDouble("High");
                        valueSet.add(new BarEntry(entry,i));

                    }

                    BarDataSet barDataSet = new BarDataSet(valueSet, "Stock Price Over Time");
                    barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    dataSets.add(barDataSet);
                    BarData data=new BarData(xaxis,dataSets);
                    chart.setData(data);

                    chart.setDescription("My Chart");
                    chart.animateXY(2000,2000);
                    chart.invalidate();

                } catch (JSONException e) {
                    Log.e("Come on!!",e.toString());
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        Volley.newRequestQueue(c).add(jsonRequest);
    }


    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
        valueSet1.add(v1e6);

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v2e1 = new BarEntry(150.000f, 0); // Jan
        valueSet2.add(v2e1);
        BarEntry v2e2 = new BarEntry(90.000f, 1); // Feb
        valueSet2.add(v2e2);
        BarEntry v2e3 = new BarEntry(120.000f, 2); // Mar
        valueSet2.add(v2e3);
        BarEntry v2e4 = new BarEntry(60.000f, 3); // Apr
        valueSet2.add(v2e4);
        BarEntry v2e5 = new BarEntry(20.000f, 4); // May
        valueSet2.add(v2e5);
        BarEntry v2e6 = new BarEntry(80.000f, 5); // Jun
        valueSet2.add(v2e6);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        return xAxis;
    }
}
