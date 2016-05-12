package com.nlt.mobileteam.cards.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * Created by Nick on 07.05.2016.
 */
public class CardFragment extends Fragment {
    protected TextView textView;
    protected EditText editText;

    public void editText() {
        CharSequence text = textView.getText();
        textView.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        editText.setText(text);
    }

    public void saveText() {
        CharSequence text = editText.getText();
        editText.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        textView.setText(text);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EasyImage.configuration(getActivity())
                .setImagesFolderName("CardImages")
                .saveInAppExternalFilesDir()
                .setCopyExistingPicturesToPublicLocation(true);
    }
}
