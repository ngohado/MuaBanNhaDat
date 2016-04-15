package com.qtd.muabannhadat.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.qtd.muabannhadat.model.District;
import com.qtd.muabannhadat.model.Street;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HaiTran on 4/11/2016.
 */
public class MyDatabase extends SQLiteOpenHelper {

    private Context context;
    public static final String DB_NAME = "hanoi.sqlite";
    public static final String DBLOCATION = "data/data/com.qtd.muabannhadat/databases/";
    private SQLiteDatabase db;


    public MyDatabase(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase() {
        String dbPath = context.getDatabasePath(DB_NAME).getPath();
        if (!checkDBExist()) {
            copyDatabaseFromAssets(context);
        }
        if (db != null && db.isOpen()) {
            return;
        }
        db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private Boolean checkDBExist() {
        File dbDir = new File(MyDatabase.DBLOCATION);
        if (!dbDir.exists())
            dbDir.mkdir();

        File dbFile = new File(MyDatabase.DBLOCATION + MyDatabase.DB_NAME);
        if (dbFile.exists()) {
            return true;
        }
        return false;
    }

    private void copyDatabaseFromAssets(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(MyDatabase.DB_NAME);
            String outFileName = MyDatabase.DBLOCATION + MyDatabase.DB_NAME;
            FileOutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            Log.v("News_Activity", "DB copied");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeDatabase() {
        if (db != null) {
            db.close();
        }
    }

    public List<District> getListDisttrict() {
        District district=null;
        List<District> listDistrict = new ArrayList<>();
        openDatabase();
        String selectQuery = "SELECT * FROM DISTRICT";
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        try {
            while (!cursor.isAfterLast()) {
               district=new District(cursor.getInt(0),cursor.getString(1));
                listDistrict.add(district);
                cursor.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();
        closeDatabase();
        return listDistrict;
    }

    public List<Street> getListStreet() {
        Street street=null;
        List<Street> listStreet = new ArrayList<>();
        openDatabase();
        String selectQuery = "SELECT *FROM STREET";
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        try {
            while (!cursor.isAfterLast()) {
                street=new Street(cursor.getInt(0),cursor.getInt(1),cursor.getString(2));
                listStreet.add(street);
                cursor.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();
        closeDatabase();
        return listStreet;
    }




}
