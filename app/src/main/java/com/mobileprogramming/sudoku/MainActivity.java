package com.mobileprogramming.sudoku;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    SudokuTable table;
    FloatingActionButton refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout sudokuTable = findViewById(R.id.sudoku_table);
        TableLayout selectNumberLayout = findViewById(R.id.select_number);
        TableLayout selectHintLayout = findViewById(R.id.select_hint);
        FrameLayout backGround = findViewById(R.id.back_ground);

        table = new SudokuTable(this, sudokuTable, selectNumberLayout, selectHintLayout, backGround);
        table.init();

        refreshButton = findViewById(R.id.refresh);
        refreshButton.setOnClickListener(this::refreshOnClick);

    }

    private void refreshOnClick(View view) {
        table.init();
    }
}
