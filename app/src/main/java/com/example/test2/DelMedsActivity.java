package com.example.test2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DelMedsActivity extends AppCompatActivity {

    Cursor c;
    SQLiteDatabase mDatabase;
    DBHelper mDBHelper;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_meds);

        mDBHelper = new DBHelper(this, "MyDatabase", null, 1);
        mDatabase = mDBHelper.getWritableDatabase();
        c = mDatabase.rawQuery("SELECT * from Meds", null);
        layout = findViewById(R.id.buttonContainer);

        setButtonsInLayout();
    }

    public void setButtonsInLayout() {
        c = mDatabase.rawQuery("SELECT * from Meds", null);
        TextView tv = findViewById(R.id.noMedsText);

        // seeing whats in database
        Log.v("MYTAG", "Current db count is " + c.getCount());
        c.moveToFirst();
        for(int i=0; i<c.getCount(); i++) {
            Log.v("MYTAG", "DB Entry Name: " + c.getString(1));
        }

        if (c.getCount() == 0) {
            tv.setVisibility(View.VISIBLE);
        } else {
            tv.setVisibility(View.GONE);

            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {

                    Button button = new Button(this);
                    button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    button.setTextSize(2, 25);
                    button.setGravity(Gravity.CENTER);
                    button.setText("" + c.getString(1));
                    button.setId(i + c.getCount());
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int id = v.getId();
                            Button currentButton = findViewById(id);
                            String name = currentButton.getText().toString().trim();
                            if(name.equals(null)) {
                                Log.v("MYTAG", "Med doesnt exist");
                            } else {
                                Log.v("MYTAG", "Button clicked and deleted: " + name);
                                mDatabase.execSQL("DELETE FROM Meds WHERE NAME='" + name + "'");
                                currentButton.setVisibility(View.GONE);
                                c = mDatabase.rawQuery("SELECT * from Meds", null);
                                if(c.getCount() == 0) {
                                    TextView tv = findViewById(R.id.noMedsText);
                                    tv.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });
                    layout.addView(button);

                c.moveToNext();
            }
        }
    }
}
