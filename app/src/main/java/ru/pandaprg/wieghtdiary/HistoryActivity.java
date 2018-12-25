package ru.pandaprg.wieghtdiary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class HistoryActivity extends AppCompatActivity {

    private DB db;
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
        Cursor cursor = db.getAllData();

        startManagingCursor(cursor);

        //// TODO: 25.12.18 добавить обработку даты

        // формируем столбцы сопоставления
        String [] from = new String[]{
               // DB.COLUMN_DATE,       //
                DB.COLUMN_BREAST,
                DB.COLUMN_UBREAST,
                DB.COLUMN_WAIST,
                DB.COLUMN_BELLY,
                DB.COLUMN_THIGH,
                DB.COLUMN_LEG,
                DB.COLUMN_WEIGHT};

        int [] to = new int [] {
              //  R.id.tvTime,
                R.id.tvBreast,
                R.id.tvUBreast,
                R.id.tvWaist,
                R.id.tvBelly,
                R.id.tvThight,
                R.id.tvLeg,
                R.id.tvWeight};

        // создаем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(this,R.layout.item, cursor,from,to);
        ListView lvHistory = (ListView) findViewById(R.id.lvHistory);
        lvHistory.setAdapter(scAdapter);
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

}
