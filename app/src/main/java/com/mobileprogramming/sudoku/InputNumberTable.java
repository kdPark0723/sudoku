package com.mobileprogramming.sudoku;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TableLayout;

class InputNumberTable {
    private SudokuTable sudokuTable;
    private TableLayout selectNumberLayout;
    private FrameLayout backGround;

    private CustomButton onClickedButton = null;

    InputNumberTable(SudokuTable sudokuTable, TableLayout selectNumberLayout, FrameLayout backGround) {
        this.sudokuTable = sudokuTable;

        this.selectNumberLayout = selectNumberLayout;
        this.backGround = backGround;
    }

    void init() {
        selectNumberLayout.findViewById(R.id.number_1)
                .setOnClickListener(this::selectNumberLayoutOnClickNumberButtonListener);
        selectNumberLayout.findViewById(R.id.number_2)
                .setOnClickListener(this::selectNumberLayoutOnClickNumberButtonListener);
        selectNumberLayout.findViewById(R.id.number_3)
                .setOnClickListener(this::selectNumberLayoutOnClickNumberButtonListener);
        selectNumberLayout.findViewById(R.id.number_4)
                .setOnClickListener(this::selectNumberLayoutOnClickNumberButtonListener);
        selectNumberLayout.findViewById(R.id.number_5)
                .setOnClickListener(this::selectNumberLayoutOnClickNumberButtonListener);
        selectNumberLayout.findViewById(R.id.number_6)
                .setOnClickListener(this::selectNumberLayoutOnClickNumberButtonListener);
        selectNumberLayout.findViewById(R.id.number_7)
                .setOnClickListener(this::selectNumberLayoutOnClickNumberButtonListener);
        selectNumberLayout.findViewById(R.id.number_8)
                .setOnClickListener(this::selectNumberLayoutOnClickNumberButtonListener);
        selectNumberLayout.findViewById(R.id.number_9)
                .setOnClickListener(this::selectNumberLayoutOnClickNumberButtonListener);

        selectNumberLayout.findViewById(R.id.delete_button)
                .setOnClickListener(this::selectNumberLayoutOnClickDeleteButtonListener);
        selectNumberLayout.findViewById(R.id.cancel_button)
                .setOnClickListener(this::selectNumberLayoutOnClickCancelButtonListener);

        initSelect();
    }

    private void selectNumberLayoutOnClickNumberButtonListener(View view) {
        Button button = (Button) view;
        int preNumber = onClickedButton.get();
        int newNumber = Integer.parseInt((String) button.getText());
        if (preNumber == newNumber) {
            selectNumberLayoutOnClickCancelButtonListener(view);
            return;
        }

        onClickedButton.set(newNumber);
        onClickedButton.initConflict();
        sudokuTable.updateNumber(onClickedButton, preNumber);

        initSelect();
    }

    private void selectNumberLayoutOnClickDeleteButtonListener(View view) {
        int preNumber = onClickedButton.get();
        onClickedButton.set(0);
        sudokuTable.updateNumber(onClickedButton, preNumber);
        onClickedButton.initConflict();

        initSelect();
    }

    private void selectNumberLayoutOnClickCancelButtonListener(View view) {
        initSelect();
    }

    private void initSelect() {
        backGround.setVisibility(View.INVISIBLE);
        selectNumberLayout.setVisibility(View.INVISIBLE);
        onClickedButton = null;
    }

    void setVisibly(CustomButton clickedButton) {
        onClickedButton = clickedButton;
        backGround.setVisibility(View.VISIBLE);
        selectNumberLayout.setVisibility(View.VISIBLE);
    }
}
