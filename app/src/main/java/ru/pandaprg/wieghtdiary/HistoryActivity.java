package ru.pandaprg.wieghtdiary;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HistoryActivity extends AppCompatActivity {

    final static public int typeTREND = 1;
    final static public int typeHISTORY = 2;

   // private DB db;

    HistoryFragment historyFragment;
    TrendFragment trendFragment;
    FragmentTransaction fTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        historyFragment = new HistoryFragment();
        trendFragment = new TrendFragment();
        fTrans = getFragmentManager().beginTransaction();
        if (getIntent().getIntExtra("Type", 2) == typeHISTORY) {
            fTrans.replace(R.id.fragment_content, historyFragment);
        } else {
            fTrans.replace(R.id.fragment_content, trendFragment);
        }
        fTrans.commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this, measurement.class);
                startActivityForResult(intent,1);

                //// TODO: 29.12.18 сделать добавление на страничке истории
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_main) {
            finish();
            return true;
        }else if (id == R.id.action_trend) {
            fTrans = getFragmentManager().beginTransaction();
            fTrans.replace(R.id.fragment_content, trendFragment);
            fTrans.commit();
            return true;
        }else if (id == R.id.action_history) {
            fTrans = getFragmentManager().beginTransaction();
            fTrans.replace(R.id.fragment_content, historyFragment);
            fTrans.commit();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }



}
