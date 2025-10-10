package com.tajim.shonarbangla.others;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(Context context) {
        super(context, CONSTANTS.sqliteName, null, CONSTANTS.sqliteVersion );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(String.format("CREATE TABLE %s (id INTEGER PRIMARY KEY AUTOINCREMENT, k22_price TEXT, k21_price TEXT, date TEXT)", CONSTANTS.sqlitePriceTable));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", CONSTANTS.sqlitePriceTable));
        onCreate(db);

    }

    public Cursor getData (){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM "+CONSTANTS.sqlitePriceTable, null);

    }
    public void insertData (String k22_price, String k21_price, String date){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("k22_price", k22_price);
        contentValues.put("k21_price", k21_price);
        contentValues.put("date", date);

        sqLiteDatabase.insert(CONSTANTS.sqlitePriceTable, null, contentValues);


    }

    public void clear(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + CONSTANTS.sqlitePriceTable);
        sqLiteDatabase.close();
    }
}
