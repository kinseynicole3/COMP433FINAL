package com.example.test2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddMedsActivity extends AppCompatActivity {

    DBHelper mDBHelper;
    SQLiteDatabase mDatabase;
    Cursor c;
    EditText nameET;
    EditText dosageET;
    EditText freqET;
    EditText notesET;
    EditText refillET;
    CheckBox currMedCB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meds);

        mDBHelper = new DBHelper(this, "MyDatabase", null, 1);
        mDatabase = mDBHelper.getWritableDatabase();
        c = mDatabase.rawQuery("SELECT * from Meds", null);


        nameET = findViewById(R.id.nameText);
        dosageET = findViewById(R.id.dosageText);
        freqET = findViewById(R.id.frequencyText);
        notesET = findViewById(R.id.notesText);
        refillET = findViewById(R.id.refillText);
        currMedCB = findViewById(R.id.currentMedCheckBox);

    }

    /* Save is used to save new medication info into database */
    public void save(View view) {
        // if any of the fields are left empty, print toast to screen
        if(nameET.getText().toString().length() < 1 ||
                dosageET.getText().toString().length() < 1 ||
                freqET.getText().toString().length() < 1 ||
                notesET.getText().toString().length() < 1||
                refillET.getText().toString().length() < 1) {
            Toast.makeText(this, "Must fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            // create content values to insert into database
            c = mDatabase.rawQuery("SELECT * from Meds", null);
            int id = c.getCount() + 1;
            ContentValues cv = new ContentValues();
            cv.put("ID", id);
            cv.put("Name", nameET.getText().toString().trim());
            cv.put("Dosage", dosageET.getText().toString().trim());
            cv.put("Frequency", freqET.getText().toString().trim());
            cv.put("Notes", notesET.getText().toString().trim());
            cv.put("Refill", refillET.getText().toString().trim());
            if(currMedCB.isChecked()) {
                cv.put("Current", "true");
            } else {
                cv.put("Current", "false");
            }

            //insert content values into databse
            mDatabase.insert("Meds", null, cv);

            c = mDatabase.rawQuery("SELECT * from Meds", null);
            Log.v("MYTAG", "Med Count is " + c.getCount());

            // clear EditTexts
            nameET.getText().clear();
            dosageET.getText().clear();
            freqET.getText().clear();
            notesET.getText().clear();
            refillET.getText().clear();
            currMedCB.setChecked(true);

        }
    }
}
