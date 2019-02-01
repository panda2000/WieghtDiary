package ru.pandaprg.wieghtdiary.Base.View;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ru.pandaprg.wieghtdiary.Hitsory.HistoryActivity;
import ru.pandaprg.wieghtdiary.Main.MainActivity;
import ru.pandaprg.wieghtdiary.R;


public abstract class BaseActivity extends AppCompatActivity implements BaseViewInterface {

    protected int layoutResID;
    protected int toolbarResID;
    protected int menuResID;
    protected int menuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        toolbarResID = R.id.toolbar;
        menuResID = R.menu.menu_main;

        super.onCreate(savedInstanceState);
        super.setContentView(layoutResID);

        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(toolbarResID);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickFab(view);
            }
        });

        init();
    }


    protected abstract void onClickFab(View view);

    protected abstract void init ();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(menuResID, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == menuId) {
            return super.onOptionsItemSelected(item);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_main) {
            menuId = R.id.action_main;
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_trend) {
            menuId = R.id.action_trend;
            Intent intent = new Intent(this, HistoryActivity.class);
            intent.putExtra("Type", HistoryActivity.typeTREND);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_history) {
            menuId = R.id.action_history;
            Intent intent = new Intent(this, HistoryActivity.class);
            intent.putExtra("Type", HistoryActivity.typeHISTORY);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_share){

        }
        /* else if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_achive){
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
