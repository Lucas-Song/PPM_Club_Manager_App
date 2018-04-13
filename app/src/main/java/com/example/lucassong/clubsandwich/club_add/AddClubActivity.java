package com.example.lucassong.clubsandwich.club_add;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
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
import com.example.lucassong.clubsandwich.club.Club;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import be.billington.calendar.recurrencepicker.RecurrencePickerDialog;

/**
 * Created by Lucas Song on 21/2/2018.
 */

public class AddClubActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String TAG = "AddClubActivity";

    private DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
    private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);

    private TextView firstMeetingDateText;
    private TextView meetingTimeText;
    private TextView recurrenceRuleText;

    private Date clubMeetingTime;
    private Date firstMeetingDate;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    
    private String recurrenceRule;

    private EditText clubName;
    private EditText clubBio;
    private EditText clubMeetingLocation;
    private EditText clubContactNumber;
    private EditText clubEmail;

    private Button setDateButton;
    private Button setTimeButton;
    private Button setRecurButton;
    private Button saveButton;

    private AddClubViewModel addClubViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_club);

        firstMeetingDateText = findViewById(R.id.first_meeting_date_text);
        meetingTimeText = findViewById(R.id.meeting_time_text);
        recurrenceRuleText = findViewById(R.id.recurrence_rule_text);

        clubName = findViewById(R.id.club_name);
        clubBio = findViewById(R.id.bio);
        clubMeetingLocation = findViewById(R.id.meeting_location);
        clubContactNumber = findViewById(R.id.club_contact_number);
        clubEmail = findViewById(R.id.club_email);

        meetingTimeText = findViewById(R.id.meeting_time_text);

        setDateButton = findViewById(R.id.set_date_button);
        setTimeButton = findViewById(R.id.set_time_button);
        setRecurButton = findViewById(R.id.set_recur_button);
        saveButton = findViewById(R.id.save_button);

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);  //default 9:00am
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        datePickerDialog = new DatePickerDialog(this, AddClubActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        timePickerDialog = new TimePickerDialog(this, AddClubActivity.this, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false);

        addClubViewModel = ViewModelProviders.of(this).get(AddClubViewModel.class);

        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                            String readableRule = CalendarContractHandler.formatRecurrenceRule(rrule, AddClubActivity.this);
                            recurrenceRuleText.setText(readableRule);
                        } else {
                            recurrenceRuleText.setText("No meeting repetition set");
                        }
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
                        || clubBio.getText() == null
                        || clubMeetingLocation.getText() == null
                        || clubMeetingTime == null
                        || recurrenceRule == null
                        || clubContactNumber.getText() == null
                        || clubEmail.getText() == null) {
                    Toast.makeText(AddClubActivity.this, "Missing fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    addClubViewModel.addClub(new Club(
                            clubName.getText().toString(),
                            clubBio.getText().toString(),
                            clubMeetingLocation.getText().toString(),
                            firstMeetingDate,
                            clubMeetingTime,
                            recurrenceRule,
                            clubContactNumber.getText().toString(),
                            clubEmail.getText().toString()
                    ));

                    if (CalendarContractHandler.hasCalendarPermissions(AddClubActivity.this, AddClubActivity.this)) {
                        CalendarContractHandler.addEventToCalendar(
                                AddClubActivity.this,
                                AddClubActivity.this,
                                clubName.getText().toString(),
                                "Meeting",
                                "",
                                clubMeetingLocation.getText().toString(),
                                CalendarContractHandler.combine(firstMeetingDate, clubMeetingTime),
                                CalendarContractHandler.combine(firstMeetingDate, clubMeetingTime),
                                recurrenceRule
                        );
                    }

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

        firstMeetingDate = calendar.getTime();

        firstMeetingDateText.setText(dateFormat.format(firstMeetingDate));
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        clubMeetingTime = calendar.getTime();

        meetingTimeText.setText(timeFormat.format(clubMeetingTime));
    }
}
