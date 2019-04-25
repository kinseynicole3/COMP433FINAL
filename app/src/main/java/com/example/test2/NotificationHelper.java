package com.example.test2;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {

    DBHelper mDBHelper = new DBHelper(this, "MyDatabase", null, 1);
    SQLiteDatabase mDatabase = mDBHelper.getWritableDatabase();
    Cursor c = mDatabase.rawQuery("SELECT * from Meds", null);
    String title = null;
    String message = null;

    public static final String channelID = "channelID";
    public static final String channelName= "User";
    private NotificationManager mManager;
    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    public void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_LOW);
        channel.setVibrationPattern(new long[]{ 0 });
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(R.color.colorPrimary);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle(getTitle())
                .setContentText(getMessage())
                .setSmallIcon(R.drawable.ic_one);
    }

    private String getTitle() {
        c = mDatabase.rawQuery("SELECT * from Meds", null);
        int counter = 0;
        if (c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                if (c.getString(6).trim().equals("true")) {
                    counter++;
                }
                c.moveToNext();
            }
            if (counter < 1) {
                title = "Med Control Reminder: No medication to take!";
                return title;
            } else {
                String title = "Med Control Reminder: Take your medication!";
                return title;
            }
        } else {
            title = "Med Control Reminder: No medication to take!";
            return title;
        }


    }

    private String getMessage() {
        message = "Meds: ";
        c = mDatabase.rawQuery("SELECT * from Meds", null);
        int counter = 0;
        if (c.getCount() > 0) {
            c.moveToFirst();
            for(int i = 0; i < c.getCount(); i++) {
                if(c.getString(6).trim().equals("true")) {
                    counter++;
                }
                c.moveToNext();
            }
            if (counter < 1) {
                message = "You don't have any medication to take!";
                return message;
            }
            int counter2 = 0;
            c.moveToFirst();
            for(int i = 0; i < c.getCount(); i++) {
                if(c.getString(6).trim().equals("true")) {
                    counter2++;
                    message += c.getString(1);
                    if(counter2 < counter) {
                        message += ", ";
                    } else if(counter2 >= counter){
                        message += ".";
                        break;
                    }
                }
                c.moveToNext();
            }
            return message;
        } else {
            message = "You don't have any medication to take!";
            return message;
        }


    }

}
