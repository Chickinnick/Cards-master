package com.nlt.mobileteam.cards;

import android.content.res.Resources;
import android.util.TypedValue;

public class Util {

	public static final int PICK_FOLDER_REQUEST = 1;
	public static final String SELECTED_FOLDER_EXTRA = "selected_folder_extra";

	public static int dpToPx(Resources res, int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
	}

}
