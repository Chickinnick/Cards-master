package com.nlt.mobileteam.cards.activity;

import android.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nlt.mobileteam.cards.model.Card;

import java.util.ArrayList;

/**
 * Created by Nick on 07.05.2016.
 */
public class CardFragment extends Fragment {
    protected TextView textView;
    protected EditText editText;
    protected ImageView imageView;

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


    public ImageView getImageView() {

        return imageView;
    }

    public void update(ArrayList<Card> cards) {

    }
}
