package com.example.huanghuishun.myschedule.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by huanghuishun on 2016/8/18.
 */
public class AllCitiesDatabaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/com.example.huanghuishun.myschedule/databases/";
    private static String DB_NAME = "adcode.db";
    private static String ASSETS_NAME = "adcode.db";

    private Context mContext;

    public AllCitiesDatabaseHelper(Context context) {
        super(context, DB_PATH + DB_NAME, null, 1);
        mContext = context;
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            Log.d("AllCitiesDatabaseHelper", "数据库已经存在。");
        } else {
            try {
                File dir = new File(DB_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File dbf = new File(DB_PATH + DB_NAME);
                if (dbf.exists()) {
                    dbf.delete();
                }
                SQLiteDatabase.openOrCreateDatabase(dbf, null);
                copyDatabase();
            } catch (IOException e) {
                throw new Error("数据库创建失败");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        String myPath = DB_PATH + DB_NAME;
        try {
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            Log.d("AllCitiesDatabaseHelper", "数据库不存在。");
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB == null ? false : true;
    }

    private void copyDatabase() throws IOException {
        InputStream inputStream = mContext.getAssets().open(ASSETS_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream outputStream = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
