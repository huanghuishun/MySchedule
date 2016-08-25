package com.example.huanghuishun.myschedule.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by huanghuishun on 2016/8/22.
 */
public class MyCitiesDatabaseHelper extends SQLiteOpenHelper {

    final private static String CREATE_CITY = "CREATE TABLE IF NOT EXISTS city (" +
            "id integer primary key autoincrement," +
            "name text," +
            "adcode integer unique," +
            "location text," +
            "isprimary integer default 0)";

    public MyCitiesDatabaseHelper(Context context) {
        super(context, "city", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
