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

    private int eventID;

    private EditText number;

    private enum reminderOption {MINUTES, HOURS, DAYS, WEEKS};
    private reminderOption chosenReminderOption;

    private Button saveButton;

    private AddReminderViewModel addReminderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_reminder);

        eventID = getIntent().getIntExtra("eventID", 0);

        number = findViewById(R.id.number);

        saveButton = findViewById(R.id.save_button);

        addReminderViewModel = ViewModelProviders.of(this).get(AddReminderViewModel.class);

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

                        CalendarContractHandler.addReminderToCalendar(
                                AddReminderActivity.this,
                                AddReminderActivity.this,
                                eventID,
                                minutes
                        );
                    }

                    CalendarContractHandler.updateRemindersView(AddReminderActivity.this, AddReminderActivity.this, AddReminderActivity.this, eventID);

                    startActivity(new Intent(AddReminderActivity.this, RemindersActivity.class));
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
