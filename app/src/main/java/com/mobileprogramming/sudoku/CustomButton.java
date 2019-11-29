package com.mobileprogramming.sudoku;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CustomButton extends FrameLayout {
    public int row, col;
    private boolean lock = false;
    private int conflictCount = 0;

    private TextView textView;
    private TableLayout hintLayout;
    private TextView[] hints;

    public CustomButton(@NonNull Context context, int col, int row) {
        super(context);

        this.row = row;
        this.col = col;

        this.textView = new TextView(context);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;

        this.textView.setLayoutParams(params);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.hintLayout = (TableLayout) layoutInflater.inflate(R.layout.hint_layout, null);
        this.addView(hintLayout);

        this.initTextSetHintViews();

        this.addView(textView);
        this.setClickable(true);
        this.setBackgroundResource(R.drawable.button_selector);
    }

    private void initTextSetHintViews() {
        this.hints = new TextView[9];

        for (int i = 0, k = 0; i < 3; i++) {
            TableRow r = (TableRow) this.hintLayout.getChildAt(i);

            for (int j = 0; j < 3; j++, k++) {
                this.hints[k] = (TextView) r.getChildAt(j);
            }
        }

        this.unsetHints();
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

        if (rock)
            this.textView.setTextColor(Color.parseColor("#794044"));
        else
            this.textView.setTextColor(Color.parseColor("#000000"));

    }

    boolean isHintChecked(int hint) {
        return hints[hint - 1].getVisibility() == VISIBLE;
    }

    void setHint(int hint, boolean checked) {
        if (checked)
            setHint(hint);
        else
            unsetHint(hint);
    }

    void setHint(int hint) {
        hints[hint - 1].setVisibility(VISIBLE);
    }

    void unsetHints() {
        for (int i = 0; i < 9; i++)
            unsetHint(i + 1);
    }

    void unsetHint(int hint) {
        hints[hint - 1].setVisibility(INVISIBLE);
    }
}
