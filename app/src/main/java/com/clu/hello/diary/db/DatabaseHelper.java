package com.clu.hello.diary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.clu.hello.diary.model.DiaryModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DIARY_TABLE = "DIARY_TABLE";
    public static final String COLUMN_WEATHER = "DIARY_WEATHER";
    public static final String COLUMN_FULL_NAME = "DIARY_FULL_NAME";
    public static final String COLUMN_DIARY_DATE = "DIARY_DATE";
    public static final String COLUMN_DIARY_CONTENT = "DIARY_CONTENT";
    public static final String COLUMN_ID = "ID";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "diary.db", null, 1);
    }

    // This is called the first time a database is created,
    // there should be code in here to create a new database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTableStatement = "CREATE TABLE " + DIARY_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_FULL_NAME + " TEXT, " + COLUMN_WEATHER + " TEXT, " + COLUMN_DIARY_DATE + " TEXT, " + COLUMN_DIARY_CONTENT + " TEXT)";

        sqLiteDatabase.execSQL(createTableStatement);

    }

    // This is called if the database version changed.
    // It prevents previous users apps from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(DiaryModel diaryModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        // HashMap
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FULL_NAME, diaryModel.getFullName());
        cv.put(COLUMN_WEATHER, diaryModel.getDiaryWeather());
        cv.put(COLUMN_DIARY_DATE, diaryModel.getDiaryDate());
        cv.put(COLUMN_DIARY_CONTENT, diaryModel.getDiaryContent());

        long l = db.insert(DIARY_TABLE, null, cv);

        if (l == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean deleteOne(int diaryId) {
        // find customerModel in the database, if it found, delete it and return true.
        // if it is not found, return false

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + DIARY_TABLE + " WHERE " + COLUMN_ID + " = " + diaryId;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }

    }

    public boolean updateOne(int diaryId, String fullName, String weather, String notes) {
        // find customerModel in the database, if it found, delete it and return true.
        // if it is not found, return false

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "UPDATE " + DIARY_TABLE + " SET " + COLUMN_FULL_NAME + " = \'" + fullName + "\', " + COLUMN_WEATHER + " = \'" + weather + "\', " + COLUMN_DIARY_CONTENT  +"= \'" + notes + "\'" + " WHERE " + COLUMN_ID + " = " + diaryId;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }

    }

    public boolean updateOne(int diaryId, String weather, String notes) {
        // find customerModel in the database, if it found, delete it and return true.
        // if it is not found, return false

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "UPDATE " + DIARY_TABLE + " SET " + COLUMN_WEATHER + " = \'" + weather + "\', " + COLUMN_DIARY_CONTENT  +"= \'" + notes + "\'" + " WHERE " + COLUMN_ID + " = " + diaryId;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Find
     */
    public List<DiaryModel> findRecsByDate(String dateStr) {

        List<DiaryModel> returnList = new ArrayList<>();

        // get data from database
        String queryString = "SELECT * FROM " + DIARY_TABLE + " WHERE " + COLUMN_DIARY_DATE + " = \'" + dateStr + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            // loop through the cursor (result set) and create new customer objects. Put them into the return list.
            do {
                int diaryID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                String weather = cursor.getString(2);
                String date = cursor.getString(3);
                String content = cursor.getString(4);
                DiaryModel diary = new DiaryModel(diaryID, customerName, date, weather, content);

                returnList.add(diary);

            } while (cursor.moveToNext());

        } else {
            // failure. do not add anything to the list

        }

        // close both the cursor and the db when done.
        cursor.close();
        db.close();

        return returnList;

    }

    public DiaryModel findRecById(String id) {

        DiaryModel diary = null;

        // get data from database
        String queryString = "SELECT * FROM " + DIARY_TABLE + " WHERE " + COLUMN_ID + " = \'" + id + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            // loop through the cursor (result set) and create new customer objects. Put them into the return list.
            do {
                int diaryID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                String weather = cursor.getString(2);
                String date = cursor.getString(3);
                String content = cursor.getString(4);
                diary = new DiaryModel(diaryID, customerName, date, weather, content);
            } while (cursor.moveToNext());

        } else {
            // failure. do not add anything to the list

        }

        // close both the cursor and the db when done.
        cursor.close();
        db.close();

        return diary;
    }

    public List<DiaryModel> getEveryOne() {

        List<DiaryModel> returnList = new ArrayList<>();

        // get data from database
        String queryString = "SELECT * FROM " + DIARY_TABLE + " ORDER BY " + COLUMN_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            // loop through the cursor (result set) and create new customer objects. Put them into the return list.
            do {
                int diaryID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                String weather = cursor.getString(2);
                String date = cursor.getString(3);
                String content = cursor.getString(4);
                DiaryModel diary = new DiaryModel(diaryID, customerName, date, weather, content);

                returnList.add(diary);

            } while (cursor.moveToNext());

        } else {
            // failure. do not add anything to the list

        }

        // close both the cursor and the db when done.
        cursor.close();
        db.close();

        return returnList;

    }




}
