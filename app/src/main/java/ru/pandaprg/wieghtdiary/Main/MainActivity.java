package ru.pandaprg.wieghtdiary.Main;

import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import ru.pandaprg.wieghtdiary.Base.Data.Data;
import ru.pandaprg.wieghtdiary.Base.View.BaseActivity;
import ru.pandaprg.wieghtdiary.Measurement.MeasurementActivity;
import ru.pandaprg.wieghtdiary.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    MainPresenter presenter;

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
//    private Toolbar toolbar;
    private int id=0;   // Todo: Перенести управление по ID в Presenter

    private ImageButton ibtnBack;
    private ImageButton ibtnNext;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public MainActivity (){
        layoutResID = R.layout.activity_main;
        menuId = R.id.action_main;

    }


    @Override
    public void onClickFab (View view){
       // Log.d ("onClickFab", "MainActivity.onClickFab");
        presenter.clickAddFab();
    }

    public void startMeasurement (){
        //Log.d ("onClickFab", "MainActivity.startMeasurement");
        Intent intent = new Intent(MainActivity.this, MeasurementActivity.class);
        startActivityForResult(intent,1);
    }

    //@Override
    protected void init()
    {
        presenter = new MainPresenter();
        presenter.attach(this);

        logTag += getLocalClassName();
        Log.d(logTag, "Create");



        dataWieghtDiary = new Data(this);


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
        showMeasurement(cursor);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }

        // TODO : проанализировать структуру данных

        HashMap<String, Double> measurementData = new HashMap<>();

        //Log.d (logTag, "Intent " + data.getComponent().toString());

        id = data.getIntExtra("_id", 0);

        measurementData.put("Date", (double) System.currentTimeMillis());
        measurementData.put("_id", (double) data.getIntExtra("_id", 0));
        measurementData.put("Breast", data.getDoubleExtra("Breast", 0));
        measurementData.put("UBreast", data.getDoubleExtra("UBreast", 0));
        measurementData.put("Waist", data.getDoubleExtra("Waist", 0));
        measurementData.put("Belly", data.getDoubleExtra("Belly", 0));
        measurementData.put("Thight", data.getDoubleExtra("Thight", 0));
        measurementData.put("Leg", data.getDoubleExtra("Leg", 0));
        measurementData.put("Weight", data.getDoubleExtra("Weight", 0));

        presenter.addMeasurement (measurementData);
/*
        String time = System.currentTimeMillis() + "";  //TODO : сделать передов Long -> Double
        id = data.getIntExtra("_id", 0);                //TODO : сделать передов Double -> Int
        double breast = data.getDoubleExtra("Breast", 0);
        double uBreast = data.getDoubleExtra("UBreast",0);
        double waist = data.getDoubleExtra("Waist", 0);
        double belly = data.getDoubleExtra("Belly", 0);
        double thigh = data.getDoubleExtra("Thight", 0);
        double leg = data.getDoubleExtra("Leg", 0);
        double weight = data.getDoubleExtra("Weight", 0);
*/


    }

    public void showMeasurement (HashMap<String, Double> measurementData)
    {
        id = measurementData.get("_id").intValue();

        tvTime.setText( sdf.format( measurementData.get("Date").longValue() ) );
        tvBreast.setText(measurementData.get("Breast") +"");
        tvUBreast.setText(measurementData.get("UBreast")+"");
        tvWaist.setText(measurementData.get("Waist")+"");
        tvBelly.setText(measurementData.get("Belly")+"");
        tvThigh.setText(measurementData.get("Thight")+"");
        tvLeg.setText(measurementData.get("Leg")+"");
        tvWeight.setText(measurementData.get("Weight")+"");

    }


    protected  void showMeasurement (Cursor cursor) {
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
        showMeasurement(cursor);
        //getSupportLoaderManager().restartLoader(0,null,lc);
    }

}
