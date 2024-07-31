package com.example.fitness;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.fitness.Fragments.HomeFragment;
import com.example.fitness.Notification.AlarmReceiver;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbaar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        scheduleDailyNotification();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.mainitems, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        Fragment fragment = null;

        if (item.getItemId() == R.id.itemHome) {
            fragment = new HomeFragment();
        } else if (item.getItemId() == R.id.itemReminder) {
            showTimePickerDialog();
        } else if (item.getItemId() == R.id.itemprivacy) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://weightlossprivacy.blogspot.com/2024/07/privacy-policy-for-fitness-tracker.html"));
            startActivity(intent);
        } else if (item.getItemId() == R.id.itemTermsandConditions) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://weightlossprivacy.blogspot.com/2024/07/terms-and-conditions-for-weight-loss.html"));
            startActivity(intent);
        } else if (item.getItemId() == R.id.itemshare) {
            shareContent();
            return true;
        } else if (item.getItemId() == R.id.itemRateus) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

        if (fragment != null) {
            loadFragment(fragment);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showTimePickerDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_timepicker, null);
        TimePicker timePicker = dialogView.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Time")
                .setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hour = timePicker.getHour();
                        int minute = timePicker.getMinute();
                        String amPm = timePicker.getHour() >= 12 ? "PM" : "AM";

                        if (hour > 12) {
                            hour -= 12;
                        } else if (hour == 0) {
                            hour = 12;
                        }
                        String timeString = String.format("%02d:%02d %s", hour, minute, amPm);
                        Log.d(TAG, "Time selected: " + timeString);
                        scheduleNotification(hour, minute, amPm.equals("PM"));
                    }
                })
                .setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @SuppressLint("ScheduleExactAlarm")
    private void scheduleNotification(int hour, int minute, boolean isPm) {
        try {
            if (isPm && hour < 12) {
                hour += 12;
            } else if (!isPm && hour == 12) {
                hour = 0;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);

            Intent intent = new Intent(this, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                Log.d(TAG, "Alarm set for: " + calendar.getTime());
            } else {
                showErrorDialog("AlarmManager is null");
            }
        } catch (Exception e) {
            showErrorDialog(e.getMessage());
            Log.e(TAG, "Error scheduling notification", e);
        }
    }

    private void scheduleDailyNotification() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (Calendar.getInstance().after(calendar)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.d(TAG, "Daily alarm set for: " + calendar.getTime());
        } else {
            showErrorDialog("AlarmManager is null");
        }
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    private void shareContent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String body = "Transform your fitness journey with the Fitness Tracker app! " +
                 "Download " +
                "now for free and start your healthy lifestyle today:\n\n"
                + "https://play.google.com/store/apps/details?id=" + getPackageName();
        String appname = "Fitness Tracker";

        shareIntent.putExtra(Intent.EXTRA_SUBJECT, appname);
        shareIntent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }


    private void loadFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.framelayout, fragment)
                .commit();
    }
}
