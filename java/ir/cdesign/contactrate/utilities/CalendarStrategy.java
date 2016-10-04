package ir.cdesign.contactrate.utilities;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import ir.cdesign.contactrate.persianmaterialdatetimepicker.date.DatePickerDialog;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.utils.PersianCalendar;

/**
 * Created by Sasan on 2016-09-30.
 */
public class CalendarStrategy {

    protected  boolean shamsi;
    public PersianCalendar calendar;

    public String delimiter = "-";

    public CalendarStrategy(PersianCalendar calendar) {
        calendar.setTimeZone(TimeZone.getDefault());
        this.calendar = calendar;
        shamsi = Settings.calendarType == 1;
    }

    public static DialogFragment getDatePicker(DatePickerDialog.OnDateSetListener callback) {
        PersianCalendar calendar = new PersianCalendar();
        calendar.setTimeZone(TimeZone.getDefault());
        if (Settings.calendarType == Settings.SHAMSI) {

            return DatePickerDialog.newInstance(callback,
                    calendar.getPersianYear(),calendar.getPersianMonth(),calendar.getPersianDay());
        }
        else
            return DatePickerGregDialog.newInstance(callback,
                    calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
    }

    public CalendarStrategy set(int year,int month,int day) {

        if (shamsi) {
            calendar.setPersianDate(year,month,day);
        } else {
            calendar.set(year,month,day);
        }

        return this;
    }

    public String getDate() {
        String dateStr;
        if (Settings.calendarType == 1) {
            dateStr = calendar.getPersianLongDate();
        } else {
            dateStr = calendar.get(Calendar.YEAR) + delimiter
                    + (calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH)) + delimiter + calendar.get(Calendar.DAY_OF_MONTH)
                    + "  " + calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH);
        }
        return dateStr;
    }

    public void setTimeInMillis(long time) {
        calendar.setTimeInMillis(time);
    }

    public int get(int field) {
        return calendar.get(field);
    }

    public void set(int field, int value) {
        calendar.set(field, value);
    }

    public long getTimeInMillis() {
        return calendar.getTimeInMillis();
    }
}
