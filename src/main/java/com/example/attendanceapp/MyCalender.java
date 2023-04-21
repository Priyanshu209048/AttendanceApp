package com.example.attendanceapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class MyCalender extends DialogFragment {
    Calendar calendar = Calendar.getInstance();
    /*private int year = Calendar.YEAR;
    private int month = Calendar.MONTH;
    private int day = Calendar.DAY_OF_MONTH;*/
    public interface OnCalendarOkClickListener{
        void onClick(int year, int month, int day);
    }

    public OnCalendarOkClickListener onCalendarOkClickListener;

    public void setOnCalendarOkClickListener(OnCalendarOkClickListener onCalendarOkClickListener) {
        this.onCalendarOkClickListener = onCalendarOkClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), ((view, year, month, dayOfMonth) -> {

            onCalendarOkClickListener.onClick(year, month, dayOfMonth);

        }), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    void setDate(int year, int month, int day){
        /*this.year = year;
        this.month = month;
        this.day = day;*/

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
    }

    String getDate(){
        return DateFormat.format("dd.MM.yyyy", calendar).toString();
    }

}
