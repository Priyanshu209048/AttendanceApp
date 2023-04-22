package com.example.attendanceapp;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

public class MyDialog extends DialogFragment {

    public static final String CLASS_ADD_DIALOG = "addClass";
    public static final String CLASS_UPDATE_DIALOG = "updateClass";
    public static final String STUDENT_ADD_DIALOG = "addStudent";
    public static final String STUDENT_UPDATE_DIALOG = "updateStudent";

    private onClickListener listener;
    private int roll;
    private String name;

    public MyDialog(int roll, String name) {

        this.roll = roll;
        this.name = name;
    }

    public MyDialog() {

    }

    public interface onClickListener{
        void onClick(String text1, String text2);
    }

    public void setListener(onClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = null;
        if (getTag().equals(CLASS_ADD_DIALOG))dialog = getAddClassDialog();
        if (getTag().equals(STUDENT_ADD_DIALOG))dialog = getAddStudentDialog();
        if (getTag().equals(CLASS_UPDATE_DIALOG))dialog = getUpdateClassDialog();
        if (getTag().equals(STUDENT_UPDATE_DIALOG))dialog = getUpdateStudentDialog();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        return dialog;
    }

    private Dialog getUpdateStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);
        /*AlertDialog dialog = builder.create();
        dialog.show();*/
        TextView title = view.findViewById(R.id.titleDialog);
        title.setText("Update Student");

        EditText roll_edt = view.findViewById(R.id.edt01);
        EditText name_edt = view.findViewById(R.id.edt02);
        AppCompatButton cancel = view.findViewById(R.id.btnCancel);
        AppCompatButton add = view.findViewById(R.id.btnAdd);
        add.setText("Update");

        roll_edt.setText(roll + "");     /*If we doesn't use the String.valueOf the it gives an error*/
        //roll_edt.setEnabled(false);
        name_edt.setText(name);

        roll_edt.setHint("Roll");
        name_edt.setHint("Name");

        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {
            String roll = roll_edt.getText().toString();
            String name = name_edt.getText().toString();

            listener.onClick(roll, name);
            dismiss();
        });

        return builder.create();
    }

    private Dialog getUpdateClassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);
        /*AlertDialog dialog = builder.create();
        dialog.show();*/
        TextView title = view.findViewById(R.id.titleDialog);
        title.setText("Update Class");

        EditText edtClass = view.findViewById(R.id.edt01);
        EditText edtSubject = view.findViewById(R.id.edt02);
        AppCompatButton cancel = view.findViewById(R.id.btnCancel);
        AppCompatButton add = view.findViewById(R.id.btnAdd);
        add.setText("Update");

        edtClass.setHint("Class Name");
        edtSubject.setHint("Subject Name");

        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {
            String className = edtClass.getText().toString();
            String subjectName = edtSubject.getText().toString();

            listener.onClick(className, subjectName);
            dismiss();
        });

        return builder.create();
    }

    private Dialog getAddStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);
        /*AlertDialog dialog = builder.create();
        dialog.show();*/
        TextView title = view.findViewById(R.id.titleDialog);
        title.setText("Add New Student");

        EditText roll_edt = view.findViewById(R.id.edt01);
        EditText name_edt = view.findViewById(R.id.edt02);
        AppCompatButton cancel = view.findViewById(R.id.btnCancel);
        AppCompatButton add = view.findViewById(R.id.btnAdd);

        roll_edt.setHint("Roll");
        name_edt.setHint("Name");

        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {
            String roll = roll_edt.getText().toString();
            String name = name_edt.getText().toString();

            roll_edt.setText(String.valueOf(Integer.parseInt(roll)+1));     /*If we doesn't use the String.valueOf the it gives an error*/
            //roll_edt.setText("");
            name_edt.setText("");

            listener.onClick(roll, name);
            //dismiss();
        });

        return builder.create();
    }

    private Dialog getAddClassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);
        /*AlertDialog dialog = builder.create();
        dialog.show();*/
        TextView title = view.findViewById(R.id.titleDialog);
        title.setText("Add New Class");

        EditText edtClass = view.findViewById(R.id.edt01);
        EditText edtSubject = view.findViewById(R.id.edt02);
        AppCompatButton cancel = view.findViewById(R.id.btnCancel);
        AppCompatButton add = view.findViewById(R.id.btnAdd);

        edtClass.setHint("Class Name");
        edtSubject.setHint("Subject Name");

        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {
            String className = edtClass.getText().toString();
            String subjectName = edtSubject.getText().toString();

            listener.onClick(className, subjectName);
            dismiss();
        });

        return builder.create();
    }
}
