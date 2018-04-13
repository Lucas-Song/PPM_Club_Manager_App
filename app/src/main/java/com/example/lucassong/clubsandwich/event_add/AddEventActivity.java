package com.example.lucassong.clubsandwich.event_add;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lucassong.clubsandwich.CalendarContractHandler;
import com.example.lucassong.clubsandwich.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import be.billington.calendar.recurrencepicker.RecurrencePickerDialog;

/**
 * Created by Lucas Song on 21/2/2018.
 */

public class AddEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String TAG = "AddClubActivity";

    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("h:mm a, EEEE, d MMMM yyyy");

    private TextView startDateTimeText;
    private TextView endDateTimeText;
    private TextView recurText;

    private enum dateOption {DATE_START, DATE_END};
    private enum timeOption {TIME_START, TIME_END};
    private dateOption chosenDateOption;
    private timeOption chosenTimeOption;

    private Date eventStartDate;
    private Date eventEndDate;
    private Date eventStartTime;
    private Date eventEndTime;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private String recurrenceRule;

    private EditText clubName;
    private EditText eventName;
    private EditText eventDetails;
    private EditText eventLocation;

    private Button setStartDateButton;
    private Button setEndDateButton;
    private Button setStartTimeButton;
    private Button setEndTimeButton;
    private Button setRecurButton;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);

        recurText = findViewById(R.id.recur_text);

        clubName = findViewById(R.id.club_name);
        eventName = findViewById(R.id.event_name);
        eventDetails = findViewById(R.id.event_details);
        eventLocation = findViewById(R.id.event_location);

        startDateTimeText = findViewById(R.id.start_date_time_text);
        endDateTimeText = findViewById(R.id.end_date_time_text);

        setStartDateButton = findViewById(R.id.set_start_date_button);
        setEndDateButton = findViewById(R.id.set_end_date_button);
        setStartTimeButton = findViewById(R.id.set_start_time_button);
        setEndTimeButton = findViewById(R.id.set_end_time_button);
        setRecurButton = findViewById(R.id.set_recur_button);
        saveButton = findViewById(R.id.save_button);

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);  //default 9:00am
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        datePickerDialog = new DatePickerDialog(this, AddEventActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        timePickerDialog = new TimePickerDialog(this, AddEventActivity.this, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false);

        setStartDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenDateOption = dateOption.DATE_START;
                datePickerDialog.show();
            }
        });

        setEndDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenDateOption = dateOption.DATE_END;
                datePickerDialog.show();
            }
        });

        setStartTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenTimeOption = timeOption.TIME_START;
                timePickerDialog.show();
            }
        });

        setEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenTimeOption = timeOption.TIME_END;
                timePickerDialog.show();
            }
        });

        setRecurButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecurrencePickerDialog recurrencePickerDialog = new RecurrencePickerDialog();

                if (recurrenceRule != null && recurrenceRule.length() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString(RecurrencePickerDialog.BUNDLE_RRULE, recurrenceRule);
                    recurrencePickerDialog.setArguments(bundle);
                }

                recurrencePickerDialog.setOnRecurrenceSetListener(new RecurrencePickerDialog.OnRecurrenceSetListener() {
                    @Override
                    public void onRecurrenceSet(String rrule) {
                        recurrenceRule = rrule;

                        if (recurrenceRule != null && recurrenceRule.length() > 0) {
                            String readableRule = CalendarContractHandler.formatRecurrenceRule(rrule, AddEventActivity.this);
                            recurText.setText(readableRule);
                        } else {
                            recurText.setText("No recurrence");
                        }

                        Log.d(TAG, "RECUR RULE IS " + recurrenceRule);
                    }
                });

                recurrencePickerDialog.show(getSupportFragmentManager(), "recurrencePicker");
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(TAG, "onClick: firstName: " + firstName.getText().toString());
                if (clubName.getText() == null
                        || eventName.getText() == null
                        || eventDetails.getText() == null
                        || eventStartDate == null
                        || eventEndDate == null
                        || eventStartTime == null
                        || eventEndTime == null
                        || eventLocation.getText() == null) {
                    Toast.makeText(AddEventActivity.this, "Missing fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (CalendarContractHandler.hasCalendarPermissions(AddEventActivity.this, AddEventActivity.this)) {
                        CalendarContractHandler.addEventToCalendar(
                                AddEventActivity.this,
                                AddEventActivity.this,
                                clubName.getText().toString(),
                                eventName.getText().toString(),
                                eventDetails.getText().toString(),
                                eventLocation.getText().toString(),
                                CalendarContractHandler.combine(eventStartDate, eventStartTime),
                                CalendarContractHandler.combine(eventEndDate, eventEndTime),
                                recurrenceRule
                        );
                    }

                    CalendarContractHandler.updateCalendarView(AddEventActivity.this, AddEventActivity.this, AddEventActivity.this);

                    /* OUTDATED BLOCK, ERRORS
                    addEventViewModel.addEvent(new Event(
                            clubName.getText().toString(),
                            eventName.getText().toString(),
                            eventDetails.getText().toString(),
                            eventLocation.getText().toString(),
                            CalendarContractHandler.combine(eventStartDate, eventStartTime),
                            CalendarContractHandler.combine(eventEndDate, eventEndTime),
                            recurrenceRule
                    ));
                    finish();
                    */

                    finish();
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        if (chosenDateOption == dateOption.DATE_START) {
            eventStartDate = calendar.getTime();
            eventStartTime = calendar.getTime();
            startDateTimeText.setText(
                    dateTimeFormat.format(
                            CalendarContractHandler.combine(eventStartDate, eventStartTime)));
        }
        else {
            Log.d(TAG, "END OPTION");
            eventEndDate = calendar.getTime();
            eventEndTime = calendar.getTime();
            endDateTimeText.setText(
                    dateTimeFormat.format(
                            CalendarContractHandler.combine(eventEndDate, eventEndTime)));
        }

    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        if (chosenTimeOption == timeOption.TIME_START) {
            eventStartDate = calendar.getTime();
            eventStartTime = calendar.getTime();
            startDateTimeText.setText(
                    dateTimeFormat.format(
                            CalendarContractHandler.combine(eventStartDate, eventStartTime)));
        }
        else {
            eventEndDate = calendar.getTime();
            eventEndTime = calendar.getTime();
            endDateTimeText.setText(
                    dateTimeFormat.format(
                            CalendarContractHandler.combine(eventEndDate, eventEndTime)));
        }
    }
}
