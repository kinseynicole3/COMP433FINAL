package com.example.test2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DeleteMedsActivity extends AppCompatActivity {

    Cursor c;
    SQLiteDatabase mDatabase;
    DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_meds);

        mDBHelper = new DBHelper(this, "MyDatabase", null, 1);
        mDatabase = mDBHelper.getWritableDatabase();
        c = mDatabase.rawQuery("SELECT * from Meds", null);
        c.moveToFirst();
        LinearLayout lLayout = (LinearLayout) findViewById(R.id.del_meds_layout);
        for (int i = 0; i < c.getCount(); i++){
            Button tv = new Button(this);
            tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv.setTextSize(2,25);
            tv.setGravity(Gravity.CENTER);
            tv.setText("Name: " + c.getString(1));
            tv.setId(i + c.getCount());
            lLayout.addView(tv);
            Log.v("DELETE", "running i = " + i);
            c.moveToNext();
        }
        c.moveToFirst();

    }
}
