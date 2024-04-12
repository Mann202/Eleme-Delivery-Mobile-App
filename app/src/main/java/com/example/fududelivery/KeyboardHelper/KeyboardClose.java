package com.example.fududelivery.KeyboardHelper;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class KeyboardClose {

    private Context context;

    public KeyboardClose(Context context) {
        this.context = context;
    }

    public void setupCloseKeyboardOnEnter(EditText editText) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if(event.getAction() == KeyEvent.ACTION_UP) {
                        closeKeyboard(v);
                        return true;
                    }
                    return false;
                }
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    closeKeyboard(v);
                    return true;
                }
                return false;
            }
        });
    }

    private void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
