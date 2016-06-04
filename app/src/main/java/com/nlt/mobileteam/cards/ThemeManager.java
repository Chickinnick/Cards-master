package com.nlt.mobileteam.cards;

import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.nlt.mobileteam.cards.activity.ScrollingActivity;

public class ThemeManager {
    private static ThemeManager ourInstance = new ThemeManager();

    public static ThemeManager getInstance() {
        return ourInstance;
    }

    private ThemeManager() {
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void changeThemeColor(AppCompatActivity activity, int color) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(color);
        activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        ((ScrollingActivity) activity).getCollapsingToolbar().setContentScrimColor(color);
        ((ScrollingActivity) activity).getCollapsingToolbar().setStatusBarScrimColor(color);
        ((ScrollingActivity) activity).getCollapsingToolbar().setBackgroundColor(color);
    }
}
