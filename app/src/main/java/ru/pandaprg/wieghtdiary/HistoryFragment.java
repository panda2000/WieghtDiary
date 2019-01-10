package ru.pandaprg.wieghtdiary;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorAdapter;

import static ru.pandaprg.wieghtdiary.R.id.tvTime;

public class HistoryFragment extends Fragment {

    private DB db;
    ExpandableListView elvMain;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_history, null);

        db = new DB(getActivity().getBaseContext());
        db.open();
        Cursor cursor = db.getAllDataLast();
        db.debugCursor (cursor);

        //   startManagingCursor(cursor);

        //// TODO: 25.12.18 добавить обработку даты

        // формируем столбцы сопоставления для групп
        String [] groupFrom = new String[]{
                DB.COLUMN_DATE,
                DB.COLUMN_BREAST,
                //DB.COLUMN_UBREAST,
                DB.COLUMN_WAIST,
                //DB.COLUMN_BELLY,
                DB.COLUMN_THIGH,
                //DB.COLUMN_LEG,
                //DB.COLUMN_WEIGHT
        };

        int [] groupTo = new int [] {
                tvTime,
                R.id.tvBreast,
                //R.id.tvUBreast,
                R.id.tvWaist,
                //R.id.tvBelly,
                R.id.tvThight,
                //R.id.tvLeg,
                //R.id.tvWeight
        };

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
        myCursorTreeAdapter myAdapter = new MyAdapter(getActivity().getBaseContext(), cursor,
                R.layout.item_group, groupFrom,
                groupTo, R.layout.item_child, childFrom,
                childTo);

        elvMain = (ExpandableListView) view.findViewById(R.id.elvMain);
        elvMain.setAdapter(myAdapter);

        return view;
    }

    class MyAdapter extends myCursorTreeAdapter {

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
