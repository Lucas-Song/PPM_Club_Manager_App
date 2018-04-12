package com.example.lucassong.clubsandwich;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.LoginFilter;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import be.billington.calendar.recurrencepicker.EventRecurrence;
import be.billington.calendar.recurrencepicker.EventRecurrenceFormatter;

/**
 * Created by Lucas Song on 22/3/2018.
 */

public class CalendarContractHandler implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int PERMISSION_REQUEST_CALENDAR = 0;

    public static boolean hasCalendarPermissions(Activity activity, Context context) {
        if ((ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_CALENDAR)
                == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.WRITE_CALENDAR)
                        == PackageManager.PERMISSION_GRANTED)) {
            return true;
        } else {
            String[] permissions = {Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR};

            ActivityCompat.requestPermissions(activity,
                    permissions,
                    PERMISSION_REQUEST_CALENDAR);

            if ((ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_CALENDAR)
                    == PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.WRITE_CALENDAR)
                            == PackageManager.PERMISSION_GRANTED)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static void createCalendar(Activity activity, Context context) {
        ContentValues values = new ContentValues();
        values.put(
                CalendarContract.Calendars.ACCOUNT_NAME,
                "ClubSandwich");
        values.put(
                CalendarContract.Calendars.ACCOUNT_TYPE,
                CalendarContract.ACCOUNT_TYPE_LOCAL);
        values.put(
                CalendarContract.Calendars.NAME,
                "ClubSandwich Calendar");
        values.put(
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                "ClubSandwich Calendar");
        values.put(
                CalendarContract.Calendars.CALENDAR_COLOR,
                0xffff0000);
        values.put(
                CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,
                CalendarContract.Calendars.CAL_ACCESS_OWNER);
        //values.put(
        //        CalendarContract.Calendars.OWNER_ACCOUNT,
        //        "some.account@googlemail.com");
        //values.put(
        //        CalendarContract.Calendars.CALENDAR_TIME_ZONE,
        //        "Europe/Berlin");
        values.put(
                CalendarContract.Calendars.SYNC_EVENTS,
                1);

        Uri.Builder builder =
                CalendarContract.Calendars.CONTENT_URI.buildUpon();
        builder.appendQueryParameter(
                CalendarContract.Calendars.ACCOUNT_NAME,
                "com.clubsandwich");
        builder.appendQueryParameter(
                CalendarContract.Calendars.ACCOUNT_TYPE,
                CalendarContract.ACCOUNT_TYPE_LOCAL);
        builder.appendQueryParameter(
                CalendarContract.CALLER_IS_SYNCADAPTER,
                "true");

        if (hasCalendarPermissions(activity, context)) {
            Uri uri = activity.getContentResolver().insert(builder.build(), values);
        }
    }

    /*
    private boolean hasReadCalendarPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CALENDAR)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;


            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CALENDAR)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CALENDAR},
                        PERMISSION_REQUEST_CALENDAR);

                // PERMISSION_REQUEST_CALENDAR is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

        }
    }
    */


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CALENDAR: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    public static long getCalendarId(Activity activity, Context context) {
        String[] projection = new String[]{CalendarContract.Calendars._ID};
        String selection =
                CalendarContract.Calendars.ACCOUNT_NAME +
                        " = ? AND " +
                        CalendarContract.Calendars.ACCOUNT_TYPE +
                        " = ? ";
        // use the same values as above:
        String[] selArgs =
                new String[]{
                        "ClubSandwich",
                        CalendarContract.ACCOUNT_TYPE_LOCAL};

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_CALENDAR)
                == PackageManager.PERMISSION_GRANTED) {
            Cursor cursor =
                    activity.getContentResolver().
                            query(
                                    CalendarContract.Calendars.CONTENT_URI,
                                    projection,
                                    selection,
                                    selArgs,
                                    null);
            if (cursor.moveToFirst()) {
                return cursor.getLong(0);
            }
        }
        return -1;
    }

    public static Date combine(Date date, Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        return cal.getTime();
    }

    public static String formatRecurrenceRule(String recurrenceRule, Context context) {
        if (recurrenceRule != null) {
            EventRecurrence recurrenceEvent = new EventRecurrence();
            recurrenceEvent.setStartDate(new Time("" + new Date().getTime()));
            recurrenceEvent.parse(recurrenceRule);
            String srt = EventRecurrenceFormatter.getRepeatString(context, context.getResources(), recurrenceEvent, true);

            return srt;
        }
        else {
            return "Does not recur";
        }
    }

    public static void addEventToCalendar(Activity activity, Context context,
                                          String clubName, String eventName,
                                          String eventDetails, String eventLocation,
                                          Date eventStartDateTime, Date eventEndDateTime,
                                          String recurrenceRule) {
        if (hasCalendarPermissions(activity, context)) {

            long calId = getCalendarId(activity, context);
            if (calId == -1) {
                Log.d("OOPS","No Calendar ID");
                createCalendar(activity, context);
                calId = getCalendarId(activity, context);
                Toast.makeText(context, "Calendar not found, please try again", Toast.LENGTH_SHORT).show();
                return;
            }

            ContentValues eventValues = new ContentValues();
            eventValues.put(CalendarContract.Events.CALENDAR_ID, calId);
            eventValues.put(CalendarContract.Events.TITLE, eventName + " - " + clubName);
            eventValues.put(CalendarContract.Events.DESCRIPTION, eventDetails);
            eventValues.put(CalendarContract.Events.EVENT_LOCATION, eventLocation);
            eventValues.put(CalendarContract.Events.DTSTART, eventStartDateTime.getTime());
            eventValues.put(CalendarContract.Events.DTEND, eventEndDateTime.getTime());
            eventValues.put(CalendarContract.Events.RRULE, recurrenceRule);
            eventValues.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

// reasonable defaults exist:
            //eventValues.put(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
            //eventValues.put(CalendarContract.Events.SELF_ATTENDEE_STATUS,
            //        CalendarContract.Events.STATUS_CONFIRMED);
            //eventValues.put(CalendarContract.Events.ALL_DAY, 1);
            //eventValues.put(CalendarContract.Events.ORGANIZER, "some.mail@some.address.com");
            //eventValues.put(CalendarContract.Events.GUESTS_CAN_INVITE_OTHERS, 1);
            //eventValues.put(CalendarContract.Events.GUESTS_CAN_MODIFY, 1);
            //eventValues.put(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

            if ((ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_CALENDAR)
                    == PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.WRITE_CALENDAR)
                            == PackageManager.PERMISSION_GRANTED)) {
                Uri uri =
                        context.getContentResolver().
                                insert(CalendarContract.Events.CONTENT_URI, eventValues);
                long eventId = new Long(uri.getLastPathSegment());

                Log.d("YAY","eventID is " +eventId);
            } else {
                Toast.makeText(context, "Calendar permissions needed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void updateCalendarView(Activity activity, Context context, FragmentActivity fragmentActivity) {
        CalendarContractHandler.clearEventsCache(activity, context, fragmentActivity);
        CalendarContractHandler.getEvents(activity, context, fragmentActivity);
    }

    public static void clearEventsCache(Activity activity, Context context, FragmentActivity fragmentActivity) {
        if (hasCalendarPermissions(activity, context)) {
            EventViewModel eventViewModel = ViewModelProviders.of(fragmentActivity).get(EventViewModel.class);

            eventViewModel.deleteAllEvents();
        }
    }

    public static void getEvents(Activity activity, Context context, FragmentActivity fragmentActivity) {

        if (hasCalendarPermissions(activity, context)) {
            String[] proj = new String[]{
                    CalendarContract.Events._ID,
                    CalendarContract.Events.TITLE,
                    CalendarContract.Events.DESCRIPTION,
                    CalendarContract.Events.EVENT_LOCATION,
                    CalendarContract.Events.DTSTART,
                    CalendarContract.Events.DTEND,
                    CalendarContract.Events.RRULE
            };

            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_CALENDAR)
                    == PackageManager.PERMISSION_GRANTED) {
                Cursor cursor =
                        context.getContentResolver().
                                query(
                                        CalendarContract.Events.CONTENT_URI,
                                        proj,
                                        "((" + CalendarContract.Events.CALENDAR_ID + " = ?))",
                                        new String[]{"" + getCalendarId(activity, context)},
                                        null);

                final int _ID = 0;
                final int TITLE = 1;
                final int DESCRIPTION = 2;
                final int EVENT_LOCATION = 3;
                final int DTSTART = 4;
                final int DTEND = 5;
                final int RRULE = 6;

                AddEventViewModel addEventViewModel = ViewModelProviders.of(fragmentActivity).get(AddEventViewModel.class);

                while (cursor.moveToNext()) {
                    String s = cursor.getString(TITLE);
                    String[] split = s.split(" - ");
                    String eventName = split[0];
                    String clubName;
                    if (split.length >= 2) {
                        clubName = split[1];
                    }
                    else {
                        clubName = "Unnamed Club";
                    }

                    addEventViewModel.addEvent(new Event(
                            cursor.getInt(_ID),
                            clubName,
                            eventName,
                            cursor.getString(DESCRIPTION),
                            cursor.getString(EVENT_LOCATION),
                            new Date(cursor.getLong(DTSTART)),
                            new Date(cursor.getLong(DTEND)),
                            cursor.getString(RRULE)
                    ));
                }

            }
        }
    }

    public static void addReminderToCalendar(Activity activity, Context context, int eventID, long minutes) {
        if (hasCalendarPermissions(activity, context)) {

            long calId = getCalendarId(activity, context);
            if (calId == -1) {
                Log.d("OOPS","No Calendar ID");
                createCalendar(activity, context);
                calId = getCalendarId(activity, context);
                Toast.makeText(context, "Calendar not found, please try again", Toast.LENGTH_SHORT).show();
                return;
            }

            ContentValues reminderValues = new ContentValues();
            reminderValues.put(CalendarContract.Reminders.EVENT_ID, eventID);
            reminderValues.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
            reminderValues.put(CalendarContract.Reminders.MINUTES, minutes);

            if ((ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_CALENDAR)
                    == PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.WRITE_CALENDAR)
                            == PackageManager.PERMISSION_GRANTED)) {

                context.getContentResolver().insert(CalendarContract.Reminders.CONTENT_URI, reminderValues);
            } else {
                Toast.makeText(context, "Calendar permissions needed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void editReminderInCalendar(Activity activity, Context context, int reminderID, long minutes) {
        if (hasCalendarPermissions(activity, context)) {

            long calId = getCalendarId(activity, context);
            if (calId == -1) {
                Log.d("OOPS","No Calendar ID");
                createCalendar(activity, context);
                calId = getCalendarId(activity, context);
                Toast.makeText(context, "Calendar not found, please try again", Toast.LENGTH_SHORT).show();
                return;
            }

            ContentValues updatedValues = new ContentValues();
            updatedValues.put(CalendarContract.Reminders.MINUTES, minutes);
            String[] selArgs =
                    new String[]{Long.toString(reminderID)};

            if ((ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_CALENDAR)
                    == PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.WRITE_CALENDAR)
                            == PackageManager.PERMISSION_GRANTED)) {

                context.getContentResolver().update(
                        CalendarContract.Reminders.CONTENT_URI,
                        updatedValues,
                        CalendarContract.Reminders._ID + " =? ",
                        selArgs);
            } else {
                Toast.makeText(context, "Calendar permissions needed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void deleteReminderInCalendar(Activity activity, Context context, int reminderID) {
        if (hasCalendarPermissions(activity, context)) {

            long calId = getCalendarId(activity, context);
            if (calId == -1) {
                Log.d("OOPS","No Calendar ID");
                createCalendar(activity, context);
                calId = getCalendarId(activity, context);
                Toast.makeText(context, "Calendar not found, please try again", Toast.LENGTH_SHORT).show();
                return;
            }

            String[] selArgs =
                    new String[]{Long.toString(reminderID)};

            if ((ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_CALENDAR)
                    == PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.WRITE_CALENDAR)
                            == PackageManager.PERMISSION_GRANTED)) {

                context.getContentResolver().delete(
                        CalendarContract.Reminders.CONTENT_URI,
                        CalendarContract.Reminders._ID + " =? ",
                        selArgs);
            } else {
                Toast.makeText(context, "Calendar permissions needed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void getReminders(Activity activity, Context context, FragmentActivity fragmentActivity, int eventID) {

        Log.d("C_C_H-G_R", "eventID is " + eventID);

        if (hasCalendarPermissions(activity, context)) {

            String[] proj = new String[]{
                    CalendarContract.Reminders._ID,
                    CalendarContract.Reminders.MINUTES
            };

            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_CALENDAR)
                    == PackageManager.PERMISSION_GRANTED) {
                Cursor cursor =
                        context.getContentResolver().
                                query(
                                        CalendarContract.Reminders.CONTENT_URI,
                                        proj,
                                        CalendarContract.Reminders.EVENT_ID + " = ? ",
                                        new String[]{"" + eventID},
                                        null);

                final int _ID = 0;
                final int MINUTES = 1;

                AddReminderViewModel addReminderViewModel = ViewModelProviders.of(fragmentActivity).get(AddReminderViewModel.class);

                Log.d("C_C_H-G_R","Start getting reminders using cursor");
                int i = 0;

                while (cursor.moveToNext()) {

                    i++;
                    Log.d("C_C_H-G_R","Reminder number " + i);

                    addReminderViewModel.addReminder(new Reminder(
                            cursor.getInt(_ID),
                            eventID,
                            cursor.getLong(MINUTES)
                    ));
                }
            }
        }
    }

    public static void updateRemindersView(Activity activity, Context context, FragmentActivity fragmentActivity, int eventID) {
        CalendarContractHandler.clearRemindersCache(activity, context, fragmentActivity);
        Log.d("C_C_H", "Cleared cache");
        CalendarContractHandler.getReminders(activity, context, fragmentActivity, eventID);
        Log.d("C_C_H", "Repopulated cache");
    }

    public static void clearRemindersCache(Activity activity, Context context, FragmentActivity fragmentActivity) {
        if (hasCalendarPermissions(activity, context)) {
            ReminderViewModel reminderViewModel = ViewModelProviders.of(fragmentActivity).get(ReminderViewModel.class);

            reminderViewModel.deleteAllReminders();
        }
    }
}
