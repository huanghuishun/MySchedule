package com.example.huanghuishun.myschedule.sqlite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by huanghuishun on 2016/9/19.
 */
public class MyWalletDatabaseHelper extends SQLiteOpenHelper {

    final private static String CREATE_ITEM = "CREATE TABLE IF NOT EXISTS item (" +
            "id integer primary key autoincrement," +
            "uid text," +  //uid
            "title text," +  //账目标题
            "type integer," +  //账目类型：0，收入；1，支出。
            "category text," +  //账目类别：衣，食，住，行
            "createDate timestamp default (datetime('now','localtime'))," +  //记账时间
            "place text," +   //记账地点
            "remark text," +   //备注
            "listId integer)";   //所属账本id
    final private static String CREATE_LIST = "CREATE TABLE IF NOT EXISTS list (" +
            "id integer primary key autoincrement," +
            "uid text,"+  //uid
            "name text," +  //账本名字
            "createDate timestamp default (datetime('now','localtime'))," +  //创建时间
            "remark text)";  //备注


    public MyWalletDatabaseHelper(Context context) {
        super(context, "wallet", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_LIST);
        sqLiteDatabase.execSQL(CREATE_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
