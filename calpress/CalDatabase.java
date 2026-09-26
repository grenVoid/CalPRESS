package com.example.calpress;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class CalDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "calpress.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_HISTORY = "history";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EXPRESSION = "expression";
    private static final String COLUMN_RESULT = "result";
    private static final String COLUMN_CREATED_AT = "created_at";

    public CalDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + TABLE_HISTORY + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EXPRESSION + " TEXT NOT NULL, " +
                COLUMN_RESULT + " TEXT NOT NULL, " +
                COLUMN_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion
    ) {

        db.execSQL(
                "DROP TABLE IF EXISTS " + TABLE_HISTORY
        );

        onCreate(db);
    }

    public long insertHistory(
            String expression,
            String result
    ) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(
                COLUMN_EXPRESSION,
                expression
        );

        values.put(
                COLUMN_RESULT,
                result
        );

        long id = db.insert(
                TABLE_HISTORY,
                null,
                values
        );

        db.close();

        return id;
    }

    public ArrayList<ItemHistory> getAllHistory() {

        ArrayList<ItemHistory> historyList =
                new ArrayList<>();

        SQLiteDatabase db =
                getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_HISTORY,
                null,
                null,
                null,
                null,
                null,
                COLUMN_ID + " DESC"
        );

        if (cursor.moveToFirst()) {

            do {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                COLUMN_ID
                        )
                );

                String expression = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                COLUMN_EXPRESSION
                        )
                );

                String result = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                COLUMN_RESULT
                        )
                );

                String createdAt = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                COLUMN_CREATED_AT
                        )
                );

                historyList.add(
                        new ItemHistory(
                                id,
                                expression,
                                result,
                                createdAt
                        )
                );

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return historyList;
    }

    public void deleteAllHistory() {

        SQLiteDatabase db =
                getWritableDatabase();

        db.delete(
                TABLE_HISTORY,
                null,
                null
        );

        db.close();
    }
}