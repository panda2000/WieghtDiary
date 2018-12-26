package ru.pandaprg.wieghtdiary;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorTreeAdapter;

public class HistoryActivity extends AppCompatActivity {

    private DB db;
    ExpandableListView elvMain;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this, measurement.class);
                startActivityForResult(intent,1);
            }
        });

        db = new DB(this);
        db.open();
        Cursor cursor = db.getAllMesurementDateLast();

        startManagingCursor(cursor);

        //// TODO: 25.12.18 добавить обработку даты

        // формируем столбцы сопоставления для групп
        String [] groupFrom = new String[]{
                DB.COLUMN_DATE,
                DB.COLUMN_BREAST,
                //DB.COLUMN_UBREAST,
                //DB.COLUMN_WAIST,
                DB.COLUMN_BELLY,
                DB.COLUMN_THIGH,
                //DB.COLUMN_LEG,
                DB.COLUMN_WEIGHT};

        int [] groupTo = new int [] {
                R.id.tvTime,
                R.id.tvBreast,
                //R.id.tvUBreast,
                //R.id.tvWaist,
                R.id.tvBelly,
                R.id.tvThight,
                //R.id.tvLeg,
                R.id.tvWeight};

        // формируем столбцы сопоставления для элементов
        String [] childFrom = new String[]{
                //DB.COLUMN_DATE,
                DB.COLUMN_BREAST,
                DB.COLUMN_UBREAST,
                DB.COLUMN_WAIST,
                DB.COLUMN_BELLY,
                DB.COLUMN_THIGH,
                DB.COLUMN_LEG,
                DB.COLUMN_WEIGHT};

        int [] childTo = new int [] {
                //R.id.tvTime,
                R.id.tvBreast,
                R.id.tvUBreast,
                R.id.tvWaist,
                R.id.tvBelly,
                R.id.tvThight,
                R.id.tvLeg,
                R.id.tvWeight};

        // создаем адаптер и настраиваем список
        /*
        scAdapter = new SimpleCursorAdapter(this,R.layout.item, cursor,from,to);
        ListView lvHistory = (ListView) findViewById(R.id.lvHistory);
        lvHistory.setAdapter(scAdapter);*/



        // создаем адаптер и настраиваем список
        SimpleCursorTreeAdapter sctAdapter = new MyAdapter(this, cursor,
                R.layout.item_group, groupFrom,
                groupTo, R.layout.item_child, childFrom,
                childTo);
        elvMain = (ExpandableListView) findViewById(R.id.elvMain);
        elvMain.setAdapter(sctAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_main) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_trend) {

            Intent intent = new Intent(this, TrendActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_history) {

            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
            return true;
        }
        /* else if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_achive){
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    class MyAdapter extends SimpleCursorTreeAdapter {

        public MyAdapter(Context context, Cursor cursor, int groupLayout,
                         String[] groupFrom, int[] groupTo, int childLayout,
                         String[] childFrom, int[] childTo) {
            super(context, cursor, groupLayout, groupFrom, groupTo,
                    childLayout, childFrom, childTo);
        }

        protected Cursor getChildrenCursor(Cursor groupCursor) {
            // получаем курсор по элементам для конкретной группы
            int idColumn = groupCursor.getColumnIndex(DB.COLUMN_ID);
            return db.getMeasurementData(groupCursor.getInt(idColumn));
        }
    }

}
