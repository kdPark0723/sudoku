package com.mobileprogramming.sudoku;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CustomButton extends FrameLayout {
    public int row, col;
    private boolean lock = false;
    private TextView textView;
    private int conflictCount = 0;

    public CustomButton(@NonNull Context context, int col, int row) {
        super(context);

        this.row = row;
        this.col = col;

        textView = new TextView(context);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;

        textView.setLayoutParams(params);

        this.addView(textView);
        this.setClickable(true);
        this.setBackgroundResource(R.drawable.button_selector);
    }

    public void initConflict() {
        conflictCount = 0;
        updateConflict();
    }

    public void increaseConflictCount(int newValue) {
        if (newValue != 0 && get() == newValue)
            conflictCount++;

        updateConflict();
    }

    public void decreaseConflictCount(int preValue) {
        if (preValue != 0 && get() == preValue)
            conflictCount--;

        updateConflict();
    }

    private void updateConflict() {
        if (isConflict())
            setConflict();
        else
            unsetConflict();
    }

    public boolean isConflict() {
        return conflictCount > 0;
    }

    public void set(int a) {
        if (a == 0)
            textView.setText("");
        else
            textView.setText(String.valueOf(a));
    }

    public int get() {
        try {
            return Integer.parseInt((String) textView.getText());
        } catch (Exception e) {
            return 0;
        }
    }

    public void setConflict() {
        this.setBackgroundColor(Color.RED);
    }

    public void unsetConflict() {
        this.setBackgroundColor(Color.parseColor("#f0f0f0f0"));
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean rock) {
        this.lock = rock;
    }
}
