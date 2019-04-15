package com.example.test2;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE Meds (ID int, Name TEXT, Dosage TEXT, Frequency TEXT, Notes TEXT, Refill TEXT, Current TEXT)");
            Log.v("MYTAG", "TABLE Meds was created");
        } catch(SQLException e) {
            Log.v("MYTAG", "Error during create DB " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("MYTAG", "Upgrading database from version "+ oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS Meds" );
        onCreate(db);
    }
}
