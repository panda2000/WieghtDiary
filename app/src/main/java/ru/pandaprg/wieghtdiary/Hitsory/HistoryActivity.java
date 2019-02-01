package ru.pandaprg.wieghtdiary.Hitsory;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.View;

import ru.pandaprg.wieghtdiary.Base.View.BaseActivity;
import ru.pandaprg.wieghtdiary.Measurement.MeasurementActivity;
import ru.pandaprg.wieghtdiary.R;

public class HistoryActivity extends BaseActivity {

    final static public int typeTREND = 1;
    final static public int typeHISTORY = 2;


    HistoryFragment historyFragment;
    TrendFragment trendFragment;
    FragmentTransaction fTrans;

    public HistoryActivity (){
        layoutResID = R.layout.activity_history;
        //menuId = R.id.action_history;
    }


    @Override
    protected void init() {
        historyFragment = new HistoryFragment();
        trendFragment = new TrendFragment();
        fTrans = getFragmentManager().beginTransaction();
        if (getIntent().getIntExtra("Type", 2) == typeHISTORY) {
            fTrans.replace(R.id.fragment_content, historyFragment);
        } else {
            fTrans.replace(R.id.fragment_content, trendFragment);
        }
        fTrans.commit();

    }

    @Override
    public void onClickFab (View view){
        Intent intent = new Intent(HistoryActivity.this, MeasurementActivity.class);
        startActivityForResult(intent,1);
    }

}
