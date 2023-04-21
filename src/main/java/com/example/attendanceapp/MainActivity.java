package com.example.attendanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton btnFab;
    RecyclerView recyclerView;
    ClassAdapter classAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ClassItem> classItems = new ArrayList<>();
    Toolbar toolbar;
    DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);

        btnFab = findViewById(R.id.btnFab);
        btnFab.setOnClickListener(v -> showDialog());

        loadData();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        classAdapter = new ClassAdapter(this, classItems);
        recyclerView.setAdapter(classAdapter);
        classAdapter.setOnItemClickListener(position -> gotoItemActivity(position));

        setToolBar();

    }

    private void loadData() {
        Cursor cursor = dbHelper.getClassTable();

        classItems.clear();
        while (cursor.moveToNext()){
            /*@SuppressLint("Range") */int id = cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.C_ID));
            /*@SuppressLint("Range") */String className = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.CLASS_NAME_KEY));
            /*@SuppressLint("Range") */String subjectName = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.SUBJECT_NAME_KEY));

            classItems.add(new ClassItem(id, className, subjectName));
        }
    }

    private void setToolBar() {
        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        TextView subtitle = toolbar.findViewById(R.id.subtitle_toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);
        ImageButton save = toolbar.findViewById(R.id.save);

        title.setText("Attendance App");
        subtitle.setVisibility(View.GONE);
        back.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
    }

    private void gotoItemActivity(int position) {
        Intent intent = new Intent(this, StudentActivity.class);
        intent.putExtra("className", classItems.get(position).getClassName());
        intent.putExtra("subjectName", classItems.get(position).getSubjectName());
        intent.putExtra("position", position);
        intent.putExtra("cid", classItems.get(position).getCid());
        startActivity(intent);
    }

    private void showDialog(){
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(), MyDialog.CLASS_ADD_DIALOG);
        dialog.setListener((className, subjectName) -> addClass(className, subjectName));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void addClass(String className, String subjectName) {
        long cid = dbHelper.addClass(className, subjectName);
        ClassItem classItem = new ClassItem(cid, className, subjectName);
        classItems.add(classItem);
        /*It notify us when new class added*/
        classAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 0:
                showUpdateDialog(item.getGroupId());
                break;
            case 1:
                deleteClass(item.getGroupId());
        }
        return super.onContextItemSelected(item);
    }

    private void showUpdateDialog(int position) {
        MyDialog myDialog = new MyDialog();
        myDialog.show(getSupportFragmentManager(), MyDialog.CLASS_UPDATE_DIALOG);
        myDialog.setListener((className, subjectName) -> updateClass(position, className, subjectName));
    }

    private void updateClass(int position, String className, String subjectName) {
        if (-1 == dbHelper.updateClass(classItems.get(position).getCid(), className, subjectName))  //This if else is added because what if the update button not working.
            /*Log.i("Update", "Error in Update");*/
            Toast.makeText(this, "There is an error in Update Button", Toast.LENGTH_LONG).show();
        else
            dbHelper.updateClass(classItems.get(position).getCid(), className, subjectName);

        classItems.get(position).setClassName(className);
        classItems.get(position).setSubjectName(subjectName);
        classAdapter.notifyItemChanged(position);
    }

    private void deleteClass(int position) {
        if (-1 == dbHelper.deleteClass(classItems.get(position).getCid()))
            Toast.makeText(this, "There is an error in Delete Button", Toast.LENGTH_LONG).show();
        else
            dbHelper.deleteClass(classItems.get(position).getCid());

        classItems.remove(position);
        classAdapter.notifyItemRemoved(position);
    }
}