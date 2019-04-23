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
import android.widget.TextView;

public class EditStatusActivity extends AppCompatActivity {

    Cursor c;
    SQLiteDatabase mDatabase;
    DBHelper mDBHelper;
    LinearLayout currLayout;
    LinearLayout pastLayout;
    LinearLayout noMedLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_status);

        mDBHelper = new DBHelper(this, "MyDatabase", null, 1);
        mDatabase = mDBHelper.getWritableDatabase();
        c = mDatabase.rawQuery("SELECT * from Meds", null);
        currLayout = findViewById(R.id.currentMedsLayout);
        pastLayout = findViewById(R.id.pastMedsLayout);
        noMedLayout = findViewById(R.id.noMedsLayout);

        setButtonsInLayout();
    }

    public void setButtonsInLayout() {
        c = mDatabase.rawQuery("SELECT * from Meds", null);
        if (c.getCount() == 0) {
            // if db empty, only display no meds
            noMedLayout.setVisibility(View.VISIBLE);
            pastLayout.setVisibility(View.GONE);
            currLayout.setVisibility(View.GONE);
        } else {
            // if anything in db
            noMedLayout.setVisibility(View.GONE);
            pastLayout.setVisibility(View.VISIBLE);
            currLayout.setVisibility(View.VISIBLE);
        }
        int currCount = 0;
        int pastCount = 0;
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            if (c.getString(6).trim().equals("true")) {
                currCount++;
            } else {
                pastCount++;
            }
        }
        TextView noCurrTitle = findViewById(R.id.no_current_meds);
        TextView noPastTitle = findViewById(R.id.no_past_meds);
        TextView currTitle = findViewById(R.id.current_meds_title);
        TextView pastTitle = findViewById(R.id.past_meds_title);

        if (currCount < 1) { // no current meds
            currTitle.setVisibility(View.VISIBLE);
            noCurrTitle.setVisibility(View.VISIBLE);
        } else { // there are current meds
            currTitle.setVisibility(View.VISIBLE);
            noCurrTitle.setVisibility(View.VISIBLE);

            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                if (c.getString(6).trim().equals("true")) {
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
                                Log.v("MYTAG", "Button clicked: " + name);
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
                    currLayout.addView(button);
                    c.moveToNext();
                }
            }
        }

        if (pastCount < 1) { // no past meds
            pastTitle.setVisibility(View.VISIBLE);
            noPastTitle.setVisibility(View.VISIBLE);
        } else { // there are past meds
            pastTitle.setVisibility(View.VISIBLE);
            noPastTitle.setVisibility(View.VISIBLE);

            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                if (c.getString(6).trim().equals("false")) {
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
                                Log.v("MYTAG", "Button clicked: " + name);
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
                    pastLayout.addView(button);
                    c.moveToNext();
                }
            }
        }
    }
}
