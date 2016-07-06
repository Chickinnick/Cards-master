package com.nlt.mobileteam.cards.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.activity.MainActivityTabbed;
import com.nlt.mobileteam.cards.model.Card;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.StickerView;
import com.nlt.mobileteam.cards.widget.RearrangeableLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Nick on 28.04.2016.
 */
public class FrontSideFragment extends CardFragment {

    public static final String CARD_KEY = "card";
    public static final String LOG_TAG = FrontSideFragment.class.getSimpleName();
    public static final String CARD_KEY_FRONT_TEXT = "card_front_text";
    public static final String CARD_KEY_FRONT_TEXT_SS = "card_front_text_ss";
    public static final String CARD_KEY_BACK_TEXT = "card_back";
    private static final String CARD_KEY_TITLE = "title";
    private static final String CARD_KEY_TITLE_SS = "title_ss";

    public static final String CARD_IMAGE_LINK_FRONT = "link_front";
    public static final String CARD_IMAGE_LINK_SSFRONT = "link_front_ss";
    private static final String TAG = "Rearrangeable ll";

    public ImageView imageViewFront;
    public String path;
    // private RearrangeableLayout rearrangeableLayout;


    public FrontSideFragment() {
    }

    public static FrontSideFragment newInstance(Card card) {
        position = card.getPosition();
        Log.d(LOG_TAG, "on Front pos " + card.toString());
        FrontSideFragment fragment = new FrontSideFragment();
        Bundle args = new Bundle();
        args.putString(CARD_KEY_TITLE, card.getTitle());
        args.putString(CARD_KEY_FRONT_TEXT, card.getFrontText());
        args.putString(CARD_KEY_BACK_TEXT, card.getBackText());
        args.putString(CARD_IMAGE_LINK_FRONT, card.getLinkToFrontImage());
        fragment.setArguments(args);
        //CardDataController.getInstance().setPosition(position);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.card_front, container, false);
        rootView.requestFocus();
        Bundle args = getArguments();
        mViews = new ArrayList<>();

        String cardText = null;
        if (savedInstanceState == null) {
            cardText = args.getString(CARD_KEY_FRONT_TEXT);
        } else {
            cardText = savedInstanceState.getString(CARD_KEY_FRONT_TEXT_SS);
        }

   //     rearrangeableLayout = (RearrangeableLayout) rootView.findViewById(R.id.rearrangeable_layout);

        titleTextView = (TextView) rootView.findViewById(R.id.textview_title);
        titleEditText = (EditText) rootView.findViewById(R.id.edittext_title);
        textView = (TextView) rootView.findViewById(R.id.textview);
        editText = (EditText) rootView.findViewById(R.id.edittext);
        mContentRootView = (RelativeLayout) rootView.findViewById(R.id.card_container);
        mContentRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStickerView();
            }
        });
        textView.setText(cardText);
        textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        titleTextView.setOnClickListener(super.listener);


        String title = null;
        if (savedInstanceState == null) {
            title = args.getString(CARD_KEY_TITLE);
        } else {
            title = savedInstanceState.getString(CARD_KEY_TITLE_SS);
        }
        titleTextView.setText(title);

        hideKeyboard(textView);
        imageViewFront = (ImageView) rootView.findViewById(R.id.imageview);

        path = null;

        if (savedInstanceState == null) {
            path = args.getString(CARD_IMAGE_LINK_FRONT);
        } else {
            path = savedInstanceState.getString(CARD_IMAGE_LINK_SSFRONT);
        }
        if (!TextUtils.isEmpty(path)) {
            Picasso.with(getActivity().getApplicationContext())
                    .load(path)
                    .resize(300, 200)
                    .centerCrop()
                    .into(imageViewFront);
        }

        // callback method to call childPositionListener() method
     //   childPosiitonListener();

        // callback method to call preDrawListener() method
     //   preDrawListener();
        return rootView;
    }

   /* public void childPosiitonListener() {

        rearrangeableLayout.setChildPositionListener(new RearrangeableLayout.ChildPositionListener() {
            @Override
            public void onChildMoved(View childView, Rect oldPosition, Rect newPosition) {
                MainActivityTabbed.isDragMode = false;

                Log.e(TAG, childView.toString());

                Log.e(TAG, oldPosition.toString() + " -> " + newPosition.toString());
            }

            @Override
            public void onChildStartMoving(View mSelectedChild, Rect mChildStartRect, Rect mChildEndRect) {
                MainActivityTabbed.isDragMode = true;

            }
        });
    }*/

    /**
     * In this method, Added a PreviewListener to the root layout to receive update during
     * child view is dragging
     */
   /* public void preDrawListener() {
        rearrangeableLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                Log.e(TAG, "onPrepreview");
                Log.e(TAG, rearrangeableLayout.toString());
                return true;
            }
        });
    }*/


    private void addStickerView() {
        final StickerView stickerView = new StickerView(getActivity());
        stickerView.setImageResource(R.mipmap.ic_cat);
        stickerView.setOperationListener(new StickerView.OperationListener() {
            @Override
            public void onDeleteClick() {
                mViews.remove(stickerView);
                mContentRootView.removeView(stickerView);
            }

            @Override
            public void onEdit(StickerView stickerView) {
                if (mCurrentEditTextView != null) {
                    mCurrentEditTextView.setInEdit(false);
                }
                mCurrentView.setInEdit(false);
                mCurrentView = stickerView;
                mCurrentView.setInEdit(true);
            }

            @Override
            public void onTop(StickerView stickerView) {
                int position = mViews.indexOf(stickerView);
                if (position == mViews.size() - 1) {
                    return;
                }
                StickerView stickerTemp = (StickerView) mViews.remove(position);
                mViews.add(mViews.size(), stickerTemp);
            }
        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mContentRootView.addView(stickerView, lp);
        mViews.add(stickerView);
        setCurrentEdit(stickerView);
    }

    private void setCurrentEdit(StickerView stickerView) {
        if (mCurrentView != null) {
            mCurrentView.setInEdit(false);
        }
        if (mCurrentEditTextView != null) {
            mCurrentEditTextView.setInEdit(false);
        }
        mCurrentView = stickerView;
        stickerView.setInEdit(true);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        String front_txt = getArguments().getString(CARD_KEY_FRONT_TEXT);
        outState.putString(CARD_KEY_FRONT_TEXT_SS, front_txt);
        String front_img = getArguments().getString(CARD_IMAGE_LINK_FRONT);
        outState.putString(CARD_IMAGE_LINK_SSFRONT, front_img);


        String title = getArguments().getString(CARD_KEY_TITLE);
        outState.putString(CARD_KEY_TITLE_SS, title);
        //Log.d(LOG_TAG, "saving state: back " + front_txt + "img:" + front_img);

        /*if (imageFile != null) {
            outState.putString(CARD_IMAGE_LINK_FRONT, imageFile.getAbsolutePath());
        }
        */
        super.onSaveInstanceState(outState);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showImage(String imageFile, Context applicationContext) {
        Bundle args = super.getArguments();
        args.putString(CARD_IMAGE_LINK_FRONT, imageFile);
//        setArguments(args);
        Picasso.with(applicationContext)
                .load(imageFile)
                .resize(300, 200)
                .centerCrop()
                .into((((FrontSideFragment) this).imageViewFront));
    }
}
