package com.example.test2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class CurrentMedsActivity extends AppCompatActivity {

    DBHelper mDBHelper;
    SQLiteDatabase mDatabase;
    Cursor c;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_meds);

        mDBHelper = new DBHelper(this, "MyDatabase", null, 1);
        mDatabase = mDBHelper.getWritableDatabase();
        c = mDatabase.rawQuery("SELECT * from Meds", null);

        tv = findViewById(R.id.current_meds_tv);
        Log.v("MYTAG", "Current DB Count: " + c.getCount());
        Log.v("MYTAG", "Current Column Count: " + c.getColumnCount());

        setTV();
    }

    private void setTV() {
        int counter = 0;
        if(c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                Log.v("MYTAG", "Current?:" + c.getString(6));
                if(c.getString(6).trim().equals("true")) {
                    counter++;
                    tv.append("" + counter + ".  Name:   " + c.getString(1) + "\n" +
                            "     Dosage:   " + c.getString(2) + "\n" +
                            "     Frequency:   " + c.getString(3) + "\n" +
                            "     Special Notes:   " + c.getString(4) + "\n" +
                            "     Refill After:   " + c.getString(5) + "\n\n");
                }
                c.moveToNext();
            }
        }
        if(counter < 1) {
            tv.setText("No current medications" +
                    "");
        }

    }
}
