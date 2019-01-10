package ru.pandaprg.wieghtdiary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //private DB db;
    private String logTag = "LOG";

    private Data dataWieghtDiary;
    private TextView tvBreast;
    private TextView tvUBreast;
    private TextView tvWaist;
    private TextView tvBelly;
    private TextView tvThigh;
    private TextView tvLeg;
    private TextView tvWeight;
    private TextView tvTime;
    private int id=0;

    private ImageButton ibtnBack;
    private ImageButton ibtnNext;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    //LoaderCallbacks <Cursor> lc = (LoaderCallbacks<Cursor>) this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logTag += getLocalClassName();
        Log.d(logTag, "Create");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataWieghtDiary = new Data(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, measurement.class);
                //intent.putExtra("Data", (Serializable) dataWieghtDiary);
                startActivityForResult(intent,1);
            }
        });

        //db = new DB(this);
        //db.open();



        tvTime = (TextView) findViewById(R.id.tvTime);
        tvBreast = (TextView) findViewById(R.id.tvBreast);
        tvUBreast =(TextView) findViewById(R.id.tvUBreast);
        tvWaist = (TextView) findViewById(R.id.tvWaist);
        tvBelly = (TextView) findViewById(R.id.tvBelly);
        tvThigh = (TextView) findViewById(R.id.tvThight);
        tvLeg = (TextView) findViewById(R.id.tvLeg);
        tvWeight = (TextView) findViewById(R.id.tvWeight);

        ibtnBack = (ImageButton) findViewById(R.id.btnBack);
        ibtnBack.setOnClickListener(this);
        ibtnNext = (ImageButton) findViewById(R.id.btnNext);
        ibtnNext.setOnClickListener(this);

        // запрашиваем последние значения
        Cursor cursor = dataWieghtDiary.getTopData();
        viewCursor(cursor);
    }

    protected  void viewCursor (Cursor cursor) {
        // выводим значения из курсора
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            id = cursor.getInt(0);
            tvTime.setText(sdf.format(Long.parseLong(cursor.getString(1))));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}

        //Log.d (logTag, "Intent " + data.getComponent().toString());

        String time = System.currentTimeMillis() + "";
        id = data.getIntExtra("_id", 0);
        double breast = data.getDoubleExtra("Breast", 0);
        double uBreast = data.getDoubleExtra("UBreast",0);
        double waist = data.getDoubleExtra("Waist", 0);
        double belly = data.getDoubleExtra("Belly", 0);
        double thigh = data.getDoubleExtra("Thight", 0);
        double leg = data.getDoubleExtra("Leg", 0);
        double weight = data.getDoubleExtra("Weight", 0);

        tvTime.setText(sdf.format(Long.parseLong(time)));
        tvBreast.setText(breast+"");
        tvUBreast.setText(uBreast+"");
        tvWaist.setText(waist+"");
        tvBelly.setText(belly+"");
        tvThigh.setText(thigh+"");
        tvLeg.setText(leg+"");
        tvWeight.setText(weight+"");

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
            /*
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);*/
            return true;
        }else if (id == R.id.action_trend) {

            Intent intent = new Intent(this, HistoryActivity.class);
            intent.putExtra("Type", HistoryActivity.typeTREND);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_history) {

            Intent intent = new Intent(this, HistoryActivity.class);
            intent.putExtra("Type", HistoryActivity.typeHISTORY);
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

    @Override
    public void onClick(View v) {
        int tempID = id;
        switch (v.getId()){
            case R.id.btnBack :
               // tvTime.setText("Нажата кнопка Back");
                if (id > 0) {id--;}
                break;
            case R.id.btnNext :
                //tvTime.setText("Нажата кнопка Next");
                id ++;
                break;
        }
        // получаем новый курсор с данными
        Log.d(logTag,"ID = "+id);
        Cursor cursor = dataWieghtDiary.getDataByID(id);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            id = cursor.getInt(0);

        } else {
            id = tempID;
        }
        viewCursor(cursor);
        //getSupportLoaderManager().restartLoader(0,null,lc);
    }

}
