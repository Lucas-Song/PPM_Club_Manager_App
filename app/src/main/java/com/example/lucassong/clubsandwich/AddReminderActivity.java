package com.example.lucassong.clubsandwich;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import be.billington.calendar.recurrencepicker.RecurrencePickerDialog;

/**
 * Created by Lucas Song on 21/2/2018.
 */

public class AddReminderActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String TAG = "AddReminderActivity";

    private int reminderID;
    private int eventID;
    private boolean existingReminder;

    private TextView title;
    private Button deleteButton;

    private EditText number;

    private enum reminderOption {MINUTES, HOURS, DAYS, WEEKS};
    private reminderOption chosenReminderOption;

    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_reminder);

        reminderID = getIntent().getIntExtra("reminderID", 0);
        eventID = getIntent().getIntExtra("eventID", 0);
        existingReminder = getIntent().getBooleanExtra("existingReminder", false);

        Log.d("A_R_A", "eventID from intent is " + eventID);

        title = findViewById(R.id.title);

        number = findViewById(R.id.number);

        saveButton = findViewById(R.id.save_button);
        deleteButton = findViewById(R.id.delete_button);

        if (existingReminder) {
            title.setText("Edit reminder:");
            deleteButton.setText("Delete Reminder");
        } else {
            title.setText("Add reminder:");
            deleteButton.setText("Cancel Reminder");
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(TAG, "onClick: firstName: " + firstName.getText().toString());
                if (number.getText() == null
                        || (chosenReminderOption != reminderOption.MINUTES
                            && chosenReminderOption != reminderOption.HOURS
                            && chosenReminderOption != reminderOption.DAYS
                            && chosenReminderOption != reminderOption.WEEKS)) {
                    Toast.makeText(AddReminderActivity.this, "Missing fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (CalendarContractHandler.hasCalendarPermissions(AddReminderActivity.this, AddReminderActivity.this)) {

                        long minutes = Long.parseLong(number.getText().toString());

                        if (chosenReminderOption == reminderOption.HOURS) {
                            minutes *= 60;
                        } else if (chosenReminderOption == reminderOption.DAYS) {
                            minutes *= (60*24);
                        } else if (chosenReminderOption == reminderOption.WEEKS) {
                            minutes *= (60*24*7);
                        }

                        if (existingReminder) {
                            CalendarContractHandler.editReminderInCalendar(
                                    AddReminderActivity.this,
                                    AddReminderActivity.this,
                                    reminderID,
                                    minutes
                            );
                        } else {
                            CalendarContractHandler.addReminderToCalendar(
                                    AddReminderActivity.this,
                                    AddReminderActivity.this,
                                    eventID,
                                    minutes
                            );
                        }

                    }

                    Log.d("A_R_A", "eventID passed is " + eventID);
                    CalendarContractHandler.updateRemindersView(AddReminderActivity.this, AddReminderActivity.this, AddReminderActivity.this, eventID);

                    finish();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CalendarContractHandler.hasCalendarPermissions(AddReminderActivity.this, AddReminderActivity.this)) {

                    if (existingReminder) {
                        CalendarContractHandler.deleteReminderInCalendar(AddReminderActivity.this, AddReminderActivity.this, reminderID);

                        CalendarContractHandler.updateRemindersView(AddReminderActivity.this, AddReminderActivity.this, AddReminderActivity.this, eventID);
                    }

                    finish();
                }
            }
        });
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radio_minutes:
                if (checked)
                    chosenReminderOption = reminderOption.MINUTES;
                    break;
            case R.id.radio_hours:
                if (checked)
                    chosenReminderOption = reminderOption.HOURS;
                    break;
            case R.id.radio_days:
                if (checked)
                    chosenReminderOption = reminderOption.DAYS;
                    break;
            case R.id.radio_weeks:
                if (checked)
                    chosenReminderOption = reminderOption.WEEKS;
                    break;
        }
    }
}
