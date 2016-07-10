package com.nlt.mobileteam.cards.fragment.cards;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.activity.MainActivityTabbed;
import com.nlt.mobileteam.cards.controller.BroadcastManager;
import com.nlt.mobileteam.cards.fragment.PlaceholderFragment;
import com.nlt.mobileteam.cards.model.Action;
import com.nlt.mobileteam.cards.model.Card;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.BubbleInputDialog;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.BubbleTextView;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.StickerView;

import java.util.ArrayList;

public abstract class BaseCard extends Fragment implements View.OnClickListener {

    public static final int FRONT = 1;
    public static final int BACK = 2;
    protected static final String CARD_INSTANCE = "card_parcelable_key_ss";

    protected TextView textView;
    protected EditText editText;

    protected TextView titleTextView;
    protected EditText titleEditText;


    public BubbleInputDialog mBubbleInputDialog;

    public StickerView mCurrentView;

    public BubbleTextView mCurrentEditTextView;

    public ArrayList<View> mViews;

    public RelativeLayout mContentRootView;


    public static BaseCard newInstance(Card card, int flag) {
        Bundle args = new Bundle();
        BaseCard fragment = null;
        if (flag == FRONT) {
            fragment = new FrontCard();
        } else if (flag == BACK) {
            fragment = new BackCard();
        }
        args.putParcelable(CARD_INSTANCE, card);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.card_front, container, false);
        rootView.requestFocus();
        mViews = new ArrayList<>();

        titleTextView = (TextView) rootView.findViewById(R.id.textview_title);
        titleEditText = (EditText) rootView.findViewById(R.id.edittext_title);
        textView = (TextView) rootView.findViewById(R.id.textview);
        editText = (EditText) rootView.findViewById(R.id.edittext);

        titleTextView.setOnClickListener(this);
        String title = ((Card) getArguments().getParcelable(CARD_INSTANCE)).getTitle();
        titleTextView.setText(title);


        mContentRootView = (RelativeLayout) rootView.findViewById(R.id.card_container);
        return rootView;
    }


    public void editText() {
        CharSequence text = textView.getText();
        textView.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        editText.setText(text);
    }

    public void editTitle() {
        CharSequence text = titleTextView.getText();
        titleTextView.setVisibility(View.GONE);
        titleEditText.setVisibility(View.VISIBLE);
        titleEditText.setText(text);
    }

    public void saveText() {
        CharSequence text = null;
        if (editText != null) {
            text = editText.getText();
            editText.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            if (!titleEditText.isActivated() && !TextUtils.isEmpty(text)) {
                textView.setText(text);
            }
        }

        CharSequence title = null;
        if (titleEditText != null) {
            title = titleEditText.getText();
            titleEditText.setVisibility(View.GONE);
            titleTextView.setVisibility(View.VISIBLE);
            if (/*!editText.isActivated() &&*/ !TextUtils.isEmpty(title)) {
                titleTextView.setText(title);
            }


        }
        Card cardTosave = ((MainActivityTabbed) getActivity()).getCurrentCard();
        Bundle arguments = getArguments();


        cardTosave.setTitle(title.toString());
        if (this instanceof BackCard) {
            //Log.d(LOG_TAG, "class was BackSideFragment");
            cardTosave.setBackText(text.toString());
            cardTosave.setFrontText(((Card) arguments.getParcelable(CARD_INSTANCE)).getFrontText());

        } else if (this instanceof FrontCard) {
            // Log.d(LOG_TAG, "class was FrontSideFragment");
            cardTosave.setBackText(((Card) arguments.getParcelable(CARD_INSTANCE)).getBackText());
            cardTosave.setFrontText(text.toString());
        }
        //Log.d(LOG_TAG, "card" + cardTosave.toString());

        //  CardDataController.getInstance().saveInStorageAndRemove();
        BroadcastManager.getInstance().sendBroadcastWithParcelable(Action.SAVE_STATE.name(), cardTosave);
    }


    @Override
    public void onClick(View v) {
        ((PlaceholderFragment) getParentFragment()).onFragmentClickListener.onTitleClick(v);
    }


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


}
