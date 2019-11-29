package com.mobileprogramming.sudoku;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class SudokuTable {
    private static TableLayout.LayoutParams tableLayoutParams = createTableLayoutLayoutParams();
    private static TableLayout.LayoutParams tableLayoutTopBiggerParams = createTableLayoutLayoutParams();
    private static TableLayout.LayoutParams tableLayoutBottomBiggerParams = createTableLayoutLayoutParams();
    private static TableRow.LayoutParams buttonLayoutParams = createTableRowLayoutParams();
    private static TableRow.LayoutParams buttonLayoutLeftBiggerParams = createTableRowLayoutParams();
    private static TableRow.LayoutParams buttonLayoutRightBiggerParams = createTableRowLayoutParams();

    static {
        tableLayoutParams.setMargins(1, 1, 1, 1);
        tableLayoutTopBiggerParams.setMargins(1, 3, 1, 1);
        tableLayoutBottomBiggerParams.setMargins(1, 1, 1, 3);

        buttonLayoutParams.setMargins(1, 1, 1, 1);
        buttonLayoutLeftBiggerParams.setMargins(4, 1, 1, 1);
        buttonLayoutRightBiggerParams.setMargins(1, 1, 4, 1);
    }

    private Context context;
    private TableLayout sudokuTable;
    private List<List<CustomButton>> buttons;

    private InputNumberTable inputNumberTable;

    SudokuTable(Context context, TableLayout sudokuTable, TableLayout selectNumberLayout, FrameLayout backGround) {
        this.context = context;
        this.sudokuTable = sudokuTable;

        inputNumberTable = new InputNumberTable(this, selectNumberLayout, backGround);

        initView();
    }

    private static TableLayout.LayoutParams createTableLayoutLayoutParams() {
        return new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT,
                1.0f
        );
    }

    private static TableRow.LayoutParams createTableRowLayoutParams() {
        return new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f
        );
    }

    private void initView() {
        List<TableRow> tableRows = addTableRows(9);
        buttons = addButtons(tableRows);

        inputNumberTable.init();
    }

    private List<TableRow> addTableRows(int size) {
        List<TableRow> tableRows = new ArrayList<>(size);

        for (int i = 0; i < size; ++i)
            tableRows.add(addTableRow());

        return tableRows;
    }

    private TableRow addTableRow() {
        TableRow tableRow = new TableRow(context);
        sudokuTable.addView(tableRow);

        return tableRow;
    }

    private List<List<CustomButton>> addButtons(List<TableRow> tableRows) {
        final int size = tableRows.size();

        List<List<CustomButton>> buttons = new ArrayList<>(size);
        for (int i = 0; i < size; ++i)
            buttons.add(new ArrayList<>(size));

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                CustomButton button = new CustomButton(context, i, j);

                if (j % 3 == 0)
                    button.setLayoutParams(buttonLayoutLeftBiggerParams);
                else if ((j + 1) % 3 == 0)
                    button.setLayoutParams(buttonLayoutRightBiggerParams);
                else
                    button.setLayoutParams(buttonLayoutParams);

                button.setOnClickListener(this::buttonOnClickListener);

                buttons.get(i).add(button);
                tableRows.get(i).addView(buttons.get(i).get(j));
            }

            if (i % 3 == 0)
                tableRows.get(i).setLayoutParams(tableLayoutTopBiggerParams);
            else if ((i + 1) % 3 == 0)
                tableRows.get(i).setLayoutParams(tableLayoutBottomBiggerParams);
            else
                tableRows.get(i).setLayoutParams(tableLayoutParams);
        }

        return buttons;
    }

    void updateNumber(CustomButton button, int preNumber) {
        updateConflict(button, preNumber);

        if (isSolve()) {
            Toast.makeText(context, "Problem is solved!", Toast.LENGTH_LONG).show();

            init();
        }
    }

    private void updateConflict(CustomButton button, int preNumber) {
        int newNumber = button.get();

        for (int i = 0; i < buttons.size(); ++i) {
            if (i == button.col)
                continue;

            get(i, button.row).increaseConflictCount(newNumber);
            get(i, button.row).decreaseConflictCount(preNumber);

            if (get(i, button.row).get() == button.get())
                button.increaseConflictCount(newNumber);
        }

        for (int j = 0; j < buttons.get(button.col).size(); ++j) {
            if (j == button.row)
                continue;

            get(button.col, j).increaseConflictCount(newNumber);
            get(button.col, j).decreaseConflictCount(preNumber);

            if (get(button.col, j).get() == button.get())
                button.increaseConflictCount(newNumber);
        }

        int startCol = button.col - (button.col % 3);
        int startRow = button.row - (button.row % 3);
        for (int i = 0; (i * i) < buttons.size(); ++i) {
            if (startCol + i == button.col)
                continue;

            for (int j = 0; (j * j) < buttons.get(i).size(); ++j) {
                if (startRow + j == button.row)
                    continue;

                get(startCol + i, startRow + j).increaseConflictCount(newNumber);
                get(startCol + i, startRow + j).decreaseConflictCount(preNumber);

                if (get(startCol + i, startRow + j).get() == button.get())
                    button.increaseConflictCount(newNumber);
            }
        }
    }

    private boolean isSolve() {
        final int size = buttons.size();

        for (int i = 0; i < size; ++i)
            for (int j = 0; j < size; ++j)
                if (get(i, j).isConflict() || get(i, j).get() == 0)
                    return false;

        return true;
    }

    void init() {
        inputNumberTable.init();
        initNumber();
    }

    private void initNumber() {
        final BoardGenerator board = new BoardGenerator();

        final int size = buttons.size();
        final Random random = new Random();

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                int value = board.get(i, j);

                if (random.nextFloat() < 0.8) {
                    get(i, j).set(value);
                    get(i, j).setLock(true);
                } else {
                    get(i, j).set(0);
                    get(i, j).setLock(false);
                }

                get(i, j).initConflict();
            }
        }
    }

    private CustomButton get(int i, int j) {
        return buttons.get(i).get(j);
    }

    private void buttonOnClickListener(View view) {
        CustomButton clickedButton = (CustomButton) view;
        if (clickedButton.isLock())
            return;

        inputNumberTable.setVisibly(clickedButton);
    }
}
