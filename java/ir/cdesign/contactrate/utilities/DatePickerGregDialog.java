package ir.cdesign.contactrate.utilities;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import ir.cdesign.contactrate.persianmaterialdatetimepicker.date.DatePickerDialog;


/**
 * Created by Sasan on 2016-09-30.
 */
public class DatePickerGregDialog extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {

    DatePickerDialog.OnDateSetListener callback;
    android.app.DatePickerDialog datePickerDialog;

    int year,month,day;

    public DatePickerGregDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        datePickerDialog = new android.app.DatePickerDialog(getActivity(),
                this,year,month,day) {
            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                super.onDateChanged(view, year, month, day);
                callback.onDateSet(DatePickerGregDialog.this,year,month,day);
                DatePickerGregDialog.this.dismiss();
            }
        };
        return datePickerDialog.getDatePicker();
    }

    public static DatePickerGregDialog newInstance(DatePickerDialog.OnDateSetListener callBack, int year,
                                                   int monthOfYear,
                                                   int dayOfMonth) {
        DatePickerGregDialog dialog = new DatePickerGregDialog();
        dialog.setCallback(callBack);
        dialog.setDate(year,monthOfYear, dayOfMonth);
        return dialog;
    }

    public void setCallback(DatePickerDialog.OnDateSetListener callback) {
        this.callback = callback;
    }

    public void setDate(int year,int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        //datePickerDialog.onDateChanged();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        callback.onDateSet(this,year,monthOfYear,dayOfMonth);
        dismiss();
    }
}
