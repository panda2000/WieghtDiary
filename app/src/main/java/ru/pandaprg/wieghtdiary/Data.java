package ru.pandaprg.wieghtdiary;


import android.content.Context;
import android.database.Cursor;

public class Data {
    DB db;
    public Data (Context ctx){
        db = new DB(ctx);
        db.open();
    }

    public Cursor getTopData() {
        Cursor cursor = db.getTopData();
        return cursor;
    }

    public Cursor getDataByID(int id){
        Cursor cursor = db.getDataByID(id);
        return cursor;
    }

    public void addRec(double [] values){
        db.addRec(System.currentTimeMillis()+"", values[0], values[1], values[2], values[3], values[4], values[5], values[6]);
    }

    public Cursor getAllData() {
        return db.getAllData();
    }
}
