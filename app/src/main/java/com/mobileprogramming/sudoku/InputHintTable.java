package com.mobileprogramming.sudoku;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

class InputHintTable {
    private SudokuTable sudokuTable;
    private TableLayout selectHintLayout;
    private FrameLayout backGround;

    private List<ToggleButton> toggelButtons;

    InputHintTable(SudokuTable sudokuTable, TableLayout selectHintLayout, FrameLayout backGround) {
        this.sudokuTable = sudokuTable;

        this.selectHintLayout = selectHintLayout;
        this.backGround = backGround;

        toggelButtons = new ArrayList<>();

        toggelButtons.clear();

        toggelButtons.add(selectHintLayout.findViewById(R.id.hint_1));
        toggelButtons.add(selectHintLayout.findViewById(R.id.hint_2));
        toggelButtons.add(selectHintLayout.findViewById(R.id.hint_3));
        toggelButtons.add(selectHintLayout.findViewById(R.id.hint_4));
        toggelButtons.add(selectHintLayout.findViewById(R.id.hint_5));
        toggelButtons.add(selectHintLayout.findViewById(R.id.hint_6));
        toggelButtons.add(selectHintLayout.findViewById(R.id.hint_7));
        toggelButtons.add(selectHintLayout.findViewById(R.id.hint_8));
        toggelButtons.add(selectHintLayout.findViewById(R.id.hint_9));

        selectHintLayout.findViewById(R.id.hint_ok_button)
                .setOnClickListener(this::selectHintLayoutOnClickOkButtonListener);
        selectHintLayout.findViewById(R.id.hint_cancel_button)
                .setOnClickListener(this::selectHintLayoutOnClickDeleteButtonListener);
        selectHintLayout.findViewById(R.id.hint_delete_button)
                .setOnClickListener(this::selectHintLayoutOnClickCancelButtonListener);
    }

    void init() {
        initSelect();
    }

    private void selectHintLayoutOnClickOkButtonListener(View view) {
        initSelect();
    }

    private void selectHintLayoutOnClickDeleteButtonListener(View view) {
        initSelect();
    }

    private void selectHintLayoutOnClickCancelButtonListener(View view) {
        initSelect();
    }

    private void initSelect() {
        backGround.setVisibility(View.INVISIBLE);
        selectHintLayout.setVisibility(View.INVISIBLE);


    }

    void setVisibly(CustomButton clickedButton) {
        backGround.setVisibility(View.VISIBLE);
        selectHintLayout.setVisibility(View.VISIBLE);
    }
}
