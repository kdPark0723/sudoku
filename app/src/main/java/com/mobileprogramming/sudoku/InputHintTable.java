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

    private List<ToggleButton> toggleButtons;

    private CustomButton clickedButton;

    InputHintTable(SudokuTable sudokuTable, TableLayout selectHintLayout, FrameLayout backGround) {
        this.sudokuTable = sudokuTable;

        this.selectHintLayout = selectHintLayout;
        this.backGround = backGround;

        toggleButtons = new ArrayList<>();

        toggleButtons.clear();

        toggleButtons.add(selectHintLayout.findViewById(R.id.hint_1));
        toggleButtons.add(selectHintLayout.findViewById(R.id.hint_2));
        toggleButtons.add(selectHintLayout.findViewById(R.id.hint_3));
        toggleButtons.add(selectHintLayout.findViewById(R.id.hint_4));
        toggleButtons.add(selectHintLayout.findViewById(R.id.hint_5));
        toggleButtons.add(selectHintLayout.findViewById(R.id.hint_6));
        toggleButtons.add(selectHintLayout.findViewById(R.id.hint_7));
        toggleButtons.add(selectHintLayout.findViewById(R.id.hint_8));
        toggleButtons.add(selectHintLayout.findViewById(R.id.hint_9));

        selectHintLayout.findViewById(R.id.hint_ok_button)
                .setOnClickListener(this::selectHintLayoutOnClickOkButtonListener);
        selectHintLayout.findViewById(R.id.hint_cancel_button)
                .setOnClickListener(this::selectHintLayoutOnClickCancelButtonListener);
        selectHintLayout.findViewById(R.id.hint_delete_button)
                .setOnClickListener(this::selectHintLayoutOnClickDeleteButtonListener);
    }

    void init() {
        initSelect();
    }

    private void selectHintLayoutOnClickOkButtonListener(View view) {
        for (int i = 0; i < 9; i++)
            clickedButton.setHint(i + 1, toggleButtons.get(i).isChecked());

        initSelect();
    }

    private void selectHintLayoutOnClickDeleteButtonListener(View view) {
        clickedButton.unsetHints();

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

        this.clickedButton = clickedButton;

        setCheckedButtons(clickedButton);
    }

    private void setCheckedButtons(CustomButton clickedButton) {
        for (int i = 0; i < 9; i++)
            toggleButtons.get(i).setChecked(clickedButton.isHintChecked(i + 1));
    }
}
