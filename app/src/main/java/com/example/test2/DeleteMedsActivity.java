package com.example.test2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

public class DeleteMedsActivity extends AppCompatActivity {

    Cursor c;
    SQLiteDatabase mDatabase;
    DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDBHelper = new DBHelper(this, "MyDatabase", null, 1);
        mDatabase = mDBHelper.getWritableDatabase();
        c = mDatabase.rawQuery("SELECT * from Meds", null);
//        c.moveToFirst();
//        LinearLayout lLayout = (LinearLayout) findViewById(R.id.del_meds_layout);
//        for (int i = 0; i < c.getCount(); i++){
//            Button button = new Button(this);
//            button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            button.setTextSize(2,25);
//            button.setGravity(Gravity.CENTER);
//            button.setText("Name: " + c.getString(1));
//            button.setId(i + c.getCount());
//            lLayout.addView(button);
//            Log.v("MYTAG", "Delete: running i = " + i);
//            c.moveToNext();
//        }
//        c.moveToFirst();
        createButtons();
    }

    public void createButtons() {
//        ScrollView layout = (ScrollView) findViewById(R.id.del_meds_layout);
        c = mDatabase.rawQuery("SELECT * from Meds", null);
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++){
            Button button = new Button(this);
            button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setTextSize(2,25);
            button.setGravity(Gravity.CENTER);
            button.setText("" + c.getString(1));
            button.setId(i + c.getCount());
            button.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    Button currentButton = findViewById(id);
                    String name = currentButton.getText().toString().trim();
//                    mDatabase.execSQL("DELETE FROM Meds WHERE NAME='"+name+"'");
//                    createButtons();
                }
            });
            Log.v("MYTAG", "Delete: running i = " + i);
            c.moveToNext();
        }
        c.moveToFirst();
    }
}
