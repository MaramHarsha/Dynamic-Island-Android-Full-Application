package com.lock.Controllers;

import android.content.Context;
import android.content.Intent;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;
import com.dynamic.island.harsha.notification.R;

public class KeyBoardController extends ButtonState {
    private Context context;

    public Intent getIntent() {
        return null;
    }

    public boolean getState() {
        return false;
    }

    public boolean hasSystemFeature() {
        return true;
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
    }

    public KeyBoardController(Context context2) {
        super(context2);
        this.context = context2;
    }

    public void showKeyboardPicker() {
        this.context.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
        InputMethodManager inputMethodManager = (InputMethodManager) this.context.getApplicationContext().getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.showInputMethodPicker();
        }
    }

    public String getName() {
        return this.context.getResources().getString(R.string.keyboard_picker);
    }
}
