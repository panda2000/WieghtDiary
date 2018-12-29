package ru.pandaprg.wieghtdiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB {
    private static final String LOG_TAG = "LOG SQLite";

    private static final String DB_NAME = "wieghtDiaryDB";
    private static final int DB_VERSION = 3;

    private static final String MEASURING_TABLE = "Measuring";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "DateMeas";
    public static final String COLUMN_BREAST = "Breast";
    public static final String COLUMN_UBREAST = "UnderBreast";
    public static final String COLUMN_WAIST = "Waist";

    public static final String COLUMN_BELLY = "Belly";
    public static final String COLUMN_THIGH = "Thigh";
    public static final String COLUMN_LEG = "Leg";
    public static final String COLUMN_WEIGHT = "Weight";

    private static final String DB_CREATE =
            "create table " + MEASURING_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement not null, " +
                    COLUMN_DATE + " datetime not null, " +
                    COLUMN_BREAST + " integer not null, " +
                    COLUMN_UBREAST + " integer not null, " +
                    COLUMN_WAIST + " integer not null, " +
                    COLUMN_BELLY + " integer not null, " +
                    COLUMN_THIGH + " integer not null, " +
                    COLUMN_LEG + " integer not null, " +
                    COLUMN_WEIGHT + " integer not null" +
                    ");";


    private static final String DB_DELETE = "DROP TABLE IF EXISTS " + MEASURING_TABLE + ";";

    // имя таблицы категории еды, поля и запрос создания
    public static final String FOOD_TYPE_TABLE = "foodType";
    public static final String FOOD_TYPE_COLUMN_ID = "_id";
    public static final String FOOD_TYPE_COLUMN_NAME = "name";
    private static final String FOOD_TYPE_TABLE_CREATE = "create table " + FOOD_TYPE_TABLE
            + "(" + FOOD_TYPE_COLUMN_ID + " integer primary key, "
            + FOOD_TYPE_COLUMN_NAME + " text" + ");";

    // запрос удаления таблицы еды
    private static final String FOOD_TYPE_TABLE_DELETE = "DROP TABLE IF EXISTS " + FOOD_TYPE_TABLE + ";";


    private final Context mCtx;


    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        Log.d(LOG_TAG,"mCtx = " + mCtx);
        if (mCtx == null) {
            Log.d(LOG_TAG,"mCtx is NULL !!! ");
        }
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() {
        return mDB.query(MEASURING_TABLE, null, null, null, null, null, null);
    }

    public Cursor getAllDataLast() {
        String orderBy = COLUMN_DATE + " DESC";
        return mDB.query(MEASURING_TABLE, null, null, null, null, null, orderBy);
    }

    public Cursor getAllMesurementDateLast() {
        // // TODO: 26.12.18 оставить только поля дата и id
        String columns[] = { COLUMN_DATE , COLUMN_ID};
        String orderBy = COLUMN_DATE + " DESC";
        return mDB.query(MEASURING_TABLE, columns, null, null, null, null, orderBy);
    }

    // данные по измерениям конкретной группы
    public Cursor getMeasurementData(long groupID) {
        return mDB.query(MEASURING_TABLE, null, COLUMN_ID + " = "
                + groupID, null, null, null, null);
    }

    public Cursor getTopData() {
        String where = "";//"LIMIT 1";
        String orderBy = "_id DESC LIMIT 1";

        Cursor cursor = mDB.query(MEASURING_TABLE, null, where, null, null, null, orderBy);

        debugCursor(cursor);
        return cursor;
    }

    public Cursor getDataByID(int id) {
        String where = COLUMN_ID + " = " + id;

        Cursor cursor = mDB.query(MEASURING_TABLE, null, where, null, null, null, null);

        debugCursor(cursor);
        return cursor;
    }

/*
    // получить все данные из таблицы MEASURING_TABLE
    public Cursor getData(long startDate, long stopDate) {

        String table = MEALTIME_TABLE + " inner join " + MEALTIME_FOOD_TABLE +
                " on " + MEALTIME_TABLE + "." + MEALTIME_COLUMN_ID+ " = " + MEALTIME_FOOD_TABLE + "." + MEALTIME_FOOD_COLUMN_MEALTIME_ID +
                " inner join " + FOOD_TABLE + " on " + FOOD_TABLE + "." + FOOD_COLUMN_ID+ " = " + MEALTIME_FOOD_TABLE + "." + MEALTIME_FOOD_COLUMN_FOOD_ID;

        String columns[] = { MEALTIME_TABLE + "." + MEALTIME_COLUMN_NAME + " as name",
                            MEALTIME_FOOD_TABLE + "." + MEALTIME_FOOD_COLUMN_MEALTIME_ID  + " as _id" ,
                            "group_concat(" + FOOD_TABLE + "." + FOOD_COLUMN_NAME  + ") as food "  };

        String where = MEALTIME_COLUMN_DATE_TIME + " >= " + startDate + " and " + MEALTIME_COLUMN_DATE_TIME + " <= " + stopDate;
        String groupBy = MEALTIME_FOOD_TABLE + "." + MEALTIME_FOOD_COLUMN_MEALTIME_ID;
        Cursor cursor = mDB.query(table, columns, where, null, groupBy, null, null);

        debugCursor(cursor);

        return  cursor;
    }


    public int deleteMealtimeAllFood (long id){
        int count = mDB.delete(MEALTIME_FOOD_TABLE, MEALTIME_FOOD_COLUMN_MEALTIME_ID + " = "+ id, null);
        Log.d(LOG_TAG," delete from " + MEALTIME_FOOD_TABLE + " deleted = "+ count);
        return count;
    }

*/
    // добавить запись в DB_TABLE
    public void addRec(String time, double breast, double ubreast, double waist, double belly, double thigh, double leg, double weight) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DATE, time);
        cv.put(COLUMN_BREAST, breast);
        cv.put(COLUMN_UBREAST, ubreast);
        cv.put(COLUMN_WAIST, waist);
        cv.put(COLUMN_BELLY, belly);
        cv.put(COLUMN_THIGH, thigh);
        cv.put(COLUMN_LEG, leg);
        cv.put(COLUMN_WEIGHT, weight);
        mDB.insert(MEASURING_TABLE, null, cv);
    }

    // удалить запись из DB_TABLE
    public void delRec(long id) {
        mDB.delete(MEASURING_TABLE, COLUMN_ID + " = " + id, null);
    }

    // вывод для Debug
    public int debugCursor (Cursor cursor){
        String str="";
        for (int i=0; i< cursor.getColumnCount(); i++) {
            str += cursor.getColumnName(i) + " | ";
        }


        if (cursor.getCount() == 0) {
            Log.d("LOG SQL", "cursor.getCount() = " + cursor.getCount());
            return 1;
        }

        Log.d("LOG SQL", str);
        cursor.moveToFirst();



        do {
            str="";
            for (int i=0; i< cursor.getColumnCount(); i++) {
                str += cursor.getString(i) + " | ";
            }

            Log.d("LOG SQL", str);
        }
        while (cursor.moveToNext());

        return 0;
    }

    // класс по созданию и управлению БД
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        // создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d (LOG_TAG, "onCreate");
            db.execSQL(DB_CREATE);
            ContentValues cv = new ContentValues();

                cv.put(COLUMN_DATE, "1541980800000");
                cv.put(COLUMN_BREAST, 86);
                cv.put(COLUMN_UBREAST, 75);
                cv.put(COLUMN_WAIST, 72);
                cv.put(COLUMN_BELLY, 89);
                cv.put(COLUMN_THIGH, 93);
                cv.put(COLUMN_LEG, 51);
                cv.put(COLUMN_WEIGHT, 54.2);
                db.insert(MEASURING_TABLE, null, cv);
            cv.clear();

            cv.put(COLUMN_DATE, "1542499200000");
            cv.put(COLUMN_BREAST, 86);
            cv.put(COLUMN_UBREAST, 74);
            cv.put(COLUMN_WAIST, 71);
            cv.put(COLUMN_BELLY, 87);
            cv.put(COLUMN_THIGH, 92);
            cv.put(COLUMN_LEG, 51);
            cv.put(COLUMN_WEIGHT, 52.4);
            db.insert(MEASURING_TABLE, null, cv);
            cv.clear();

            cv.put(COLUMN_DATE, "1543104000000");
            cv.put(COLUMN_BREAST, 85);
            cv.put(COLUMN_UBREAST, 73);
            cv.put(COLUMN_WAIST, 68);
            cv.put(COLUMN_BELLY, 83);
            cv.put(COLUMN_THIGH, 91);
            cv.put(COLUMN_LEG, 51);
            cv.put(COLUMN_WEIGHT, 51.1);
            db.insert(MEASURING_TABLE, null, cv);
            cv.clear();

            cv.put(COLUMN_DATE, "1543708800000");
            cv.put(COLUMN_BREAST, 83);
            cv.put(COLUMN_UBREAST, 72);
            cv.put(COLUMN_WAIST, 65);
            cv.put(COLUMN_BELLY, 81);
            cv.put(COLUMN_THIGH, 90);
            cv.put(COLUMN_LEG, 49);
            cv.put(COLUMN_WEIGHT, 50.7);
            db.insert(MEASURING_TABLE, null, cv);
            cv.clear();

            cv.put(COLUMN_DATE, "1544313600000");
            cv.put(COLUMN_BREAST, 81);
            cv.put(COLUMN_UBREAST, 70);
            cv.put(COLUMN_WAIST, 62);
            cv.put(COLUMN_BELLY, 73);
            cv.put(COLUMN_THIGH, 86);
            cv.put(COLUMN_LEG, 48);
            cv.put(COLUMN_WEIGHT, 49.9);
            db.insert(MEASURING_TABLE, null, cv);
            cv.clear();

            cv.put(COLUMN_DATE, "1545177600000");
            cv.put(COLUMN_BREAST, 81);
            cv.put(COLUMN_UBREAST, 71);
            cv.put(COLUMN_WAIST, 65);
            cv.put(COLUMN_BELLY, 81);
            cv.put(COLUMN_THIGH, 88);
            cv.put(COLUMN_LEG, 48);
            cv.put(COLUMN_WEIGHT, 51.0);
            db.insert(MEASURING_TABLE, null, cv);
            cv.clear();

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d (LOG_TAG, "onUpdate");
            db.execSQL(DB_DELETE);
            Log.d (LOG_TAG, "myTab delete");


            onCreate(db);
            Log.d (LOG_TAG, "Food Type create");


        }
    }
}
