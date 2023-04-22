package com.example.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SheetListActivity extends AppCompatActivity {
    Toolbar toolbar;
    private TextView title;
    private TextView subtitle;
    private String className;
    private String subjectName;
    private ArrayAdapter adapter;
    private ArrayList<String> listItems = new ArrayList<>();
    private long cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_list);

        Intent intent = getIntent();
        className = getIntent().getStringExtra("className");
        subjectName = getIntent().getStringExtra("subjectName");
        cid = getIntent().getLongExtra("cid", -1);

        setToolBar();
        loadListItems();

        ListView sheetList = findViewById(R.id.sheetList);
        adapter = new ArrayAdapter(this, R.layout.sheet_list, R.id.date_list_item, listItems);
        sheetList.setAdapter(adapter);

        sheetList.setOnItemClickListener((parent, view, position, id) -> openSheetActivity(position));

    }

    private void setToolBar() {
        toolbar = findViewById(R.id.toolbar);
        title = toolbar.findViewById(R.id.title_toolbar);
        subtitle = toolbar.findViewById(R.id.subtitle_toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);
        ImageButton save = toolbar.findViewById(R.id.save);

        //save.setOnClickListener( v -> saveStatus());

        title.setText("Attendance Sheet List");
        subtitle.setText(className + " - " + subjectName);
        save.setVisibility(View.GONE);


        back.setOnClickListener( v -> onBackPressed());

        //toolbar.inflateMenu(R.menu.student_menu);
        //toolbar.setOnMenuItemClickListener(menuItem -> onMenuItemClick(menuItem));

    }

    private void openSheetActivity(int position) {
        long[] idArray = getIntent().getLongArrayExtra("idArray");
        int[] rollArray = getIntent().getIntArrayExtra("rollArray");
        String[] nameArray = getIntent().getStringArrayExtra("nameArray");

        Intent intent = new Intent(this, SheetActivity.class);
        intent.putExtra("idArray", idArray);
        intent.putExtra("rollArray", rollArray);
        intent.putExtra("nameArray", nameArray);
        intent.putExtra("className", className);
        intent.putExtra("subjectName", subjectName);
        intent.putExtra("month", listItems.get(position));

        startActivity(intent);
    }

    private void loadListItems() {
        Cursor cursor = new DbHelper(this).getDistinctMonths(cid);

        while (cursor.moveToNext()){
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.DATE_KEY));    //21.04.2023 -- it only adds 04.2023
            listItems.add(date.substring(3));
        }
    }
}