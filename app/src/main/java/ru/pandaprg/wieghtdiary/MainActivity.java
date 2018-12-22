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
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private DB db;
    TextView tvBreast;
    TextView tvUBreast;
    TextView tvWaist;
    TextView tvBelly;
    TextView tvThigh;
    TextView tvLeg;
    TextView tvWeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, measurement.class);
                startActivity(intent);
            }
        });

        db = new DB(this);
        db.open();

        tvBreast = (TextView) findViewById(R.id.tvBreast);
        tvUBreast =(TextView) findViewById(R.id.tvUBreast);
        tvWaist = (TextView) findViewById(R.id.tvWaist);
        tvBelly = (TextView) findViewById(R.id.tvBelly);
        tvThigh = (TextView) findViewById(R.id.tvThight);
        tvLeg = (TextView) findViewById(R.id.tvLeg);
        tvWeight = (TextView) findViewById(R.id.tvWeight);

        // запрашиваем последние значения
        Cursor cursor = db.getTopData();
        // выводим значения из курсора
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            tvBreast.setText(cursor.getString(2));
            tvUBreast.setText(cursor.getString(3));
            tvWaist.setText(cursor.getString(4));
            tvBelly.setText(cursor.getString(5));
            tvThigh.setText(cursor.getString(6));
            tvLeg.setText(cursor.getString(7));
            tvWeight.setText(cursor.getString(8));
        }
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
