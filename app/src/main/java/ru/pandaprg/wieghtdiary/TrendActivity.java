package ru.pandaprg.wieghtdiary;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TrendActivity extends AppCompatActivity {

    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        db = new DB(this);
        db.open();
        Cursor cursor = db.getAllData();

        ArrayList <DataPoint> dataBreast = new ArrayList<DataPoint>();
        ArrayList <DataPoint> dataUBreast = new ArrayList<DataPoint>();
        ArrayList <DataPoint> dataWaist = new ArrayList<DataPoint>();
        ArrayList <DataPoint> dataBelly = new ArrayList<DataPoint>();
        ArrayList <DataPoint> dataThight = new ArrayList<DataPoint>();
        ArrayList <DataPoint> dataLeg = new ArrayList<DataPoint>();
        ArrayList <DataPoint> dataWeight = new ArrayList<DataPoint>();

        if (cursor.getCount() != 0) {

            cursor.moveToFirst();
            for (double i =0 ; cursor.moveToNext(); i ++){
                Log.d("LOG Trend", i + "  " + cursor.getDouble(8));

                Date resultdate = new Date(cursor.getLong(1)); // дата

                dataBreast.add( new DataPoint(resultdate, cursor.getDouble(2)));
                dataUBreast.add( new DataPoint(resultdate, cursor.getDouble(3)));
                dataWaist.add( new DataPoint(resultdate, cursor.getDouble(4)));
                dataBelly.add( new DataPoint(resultdate, cursor.getDouble(5)));
                dataThight.add( new DataPoint(resultdate, cursor.getDouble(6)));
                dataLeg.add( new DataPoint(resultdate, cursor.getDouble(7)));
                dataWeight.add( new DataPoint(resultdate, cursor.getDouble(8)));

            }

            GraphView graph = (GraphView) findViewById(R.id.gvTrend);

            DataPoint[] dataPoints;
            LineGraphSeries<DataPoint> series;

            Resources res = getResources();

            dataPoints = dataBreast.toArray(new DataPoint [0]);
            series = new LineGraphSeries<>(dataPoints);
            series.setTitle(res.getString(R.string.breast_label));  //"Грудь"
            series.setColor(Color.RED);
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(5);
            graph.addSeries(series);

            dataPoints = dataUBreast.toArray(new DataPoint [0]);
            series = new LineGraphSeries<>(dataPoints);
            series.setTitle(res.getString(R.string.under_breast_label));  //"Под грудью"
            series.setColor(Color.YELLOW);
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(5);
            graph.addSeries(series);


            dataPoints = dataWaist.toArray(new DataPoint [0]);
            series = new LineGraphSeries<>(dataPoints);
            series.setTitle(res.getString(R.string.waist_label));   //"Талия"
            series.setColor(Color.BLACK);
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(5);
            graph.addSeries(series);

            dataPoints = dataBelly.toArray(new DataPoint [0]);
            series = new LineGraphSeries<>(dataPoints);
            series.setTitle(res.getString(R.string.belly_label));   //"Живот"
            series.setColor(Color.CYAN);
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(5);
            graph.addSeries(series);

            dataPoints = dataThight.toArray(new DataPoint [0]);
            series = new LineGraphSeries<>(dataPoints);
            series.setTitle(res.getString(R.string.thigh_label));   //"Бедро"
            series.setColor(Color.DKGRAY);
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(5);
            graph.addSeries(series);

            dataPoints = dataLeg.toArray(new DataPoint [0]);
            series = new LineGraphSeries<>(dataPoints);
            series.setTitle(res.getString(R.string.leg_label)); //"Нога"
            series.setColor(Color.GREEN);
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(5);
            graph.addSeries(series);

            dataPoints = dataWeight.toArray(new DataPoint [0]);
            series = new LineGraphSeries<>(dataPoints);
            series.setTitle(res.getString(R.string.weight_label));  //"Вес"
            series.setColor(Color.BLUE);
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(5);
            graph.addSeries(series);

            // set date label formatter
            //graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

            // legend
            graph.getLegendRenderer().setVisible(true);
            graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);


            // set date label formatter
            // graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(HRVViewer.this));
            final DateFormat dateTimeFormatter = DateFormat.getDateInstance();
            graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                @Override
                public String formatLabel(double value, boolean isValueX)
                {
                    if( isValueX ) // dateTimeFormatter.format(new Date((long) value));
                        return dateTimeFormatter.format(new Date((long) value)); // super.formatLabel(value, isValueX);
                    else
                        return super.formatLabel(value, isValueX);
                }
            });

            graph.getGridLabelRenderer().setHumanRounding(true);

            // graph.getGridLabelRenderer().setNumHorizontalLabels(Math.min(8, points.length)); // only 4 because of the space
            //graph.getGridLabelRenderer().setNumHorizontalLabels(dataPoints.length); // only 4 because of the space
            graph.getGridLabelRenderer().setHorizontalLabelsAngle(90);
            //graph.getGridLabelRenderer().setTextSize(12);

            // set manual x bounds to have nice steps
            //graph.getViewport().setMinX(min_d);
            //graph.getViewport().setMaxX(max_d+1);
            graph.getViewport().setXAxisBoundsManual(true);

        }



    }

}
