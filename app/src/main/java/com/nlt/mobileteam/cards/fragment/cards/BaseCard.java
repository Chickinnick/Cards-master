package com.nlt.mobileteam.cards.fragment.cards;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.activity.MainActivityTabbed;
import com.nlt.mobileteam.cards.controller.BroadcastManager;
import com.nlt.mobileteam.cards.model.Action;
import com.nlt.mobileteam.cards.model.Card;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.BaseViewModel;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.BubblePropertyModel;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.StickerPropertyModel;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.BubbleTextView;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.EditStateListener;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.StickerView;
import com.pavelsikun.vintagechroma.ChromaDialog;
import com.pavelsikun.vintagechroma.IndicatorMode;
import com.pavelsikun.vintagechroma.OnColorSelectedListener;
import com.pavelsikun.vintagechroma.colormode.ColorMode;

import java.util.ArrayList;

import static com.nlt.mobileteam.cards.activity.MainActivityTabbed.isDragMode;

public abstract class BaseCard extends Fragment implements EditStateListener {

    public static final int FRONT = 1;
    public static final int BACK = 2;
    protected static final String CARD_INSTANCE = "card_parcelable_key_ss";
    private static final String LOG_TAG = "BaseCard";



    public StickerView mCurrentView;

    public BubbleTextView mCurrentEditTextView;

    public ArrayList<View> mViews;

    public RelativeLayout mContentRootView;
    private BubbleTextView.OperationListener mOperationListener = new BubbleTextView.OperationListener() {
        @Override
        public void onDeleteClick() {
            mViews.remove(mCurrentEditTextView);
            checkTextViesState();
            mContentRootView.removeView(mCurrentEditTextView);
        }

        @Override
        public void onEdit(BubbleTextView bubbleTextView) {
            if (mCurrentView != null) {
                mCurrentView.setInEdit(false);
            }
            mCurrentEditTextView.setInEdit(false);
            mCurrentEditTextView = bubbleTextView;
            mCurrentEditTextView.setInEdit(true);
            isDragMode = true;

        }

        @Override
        public void onClick(BubbleTextView bubbleTextView) {

            isDragMode = true;
        }

        @Override
        public void onTop(BubbleTextView bubbleTextView) {
            int position = mViews.indexOf(bubbleTextView);
            if (position == mViews.size() - 1) {
                return;
            }
            BubbleTextView textView = (BubbleTextView) mViews.remove(position);
            mViews.add(mViews.size(), textView);
        }

        @Override
        public void onDoubleTap() {
            Activity activity = getActivity();


            new ChromaDialog.Builder()
                    .initialColor(Color.GREEN)
                    .colorMode(ColorMode.ARGB) // RGB, ARGB, HVS, CMYK, CMYK255, HSL
                    .indicatorMode(IndicatorMode.HEX) //HEX or DECIMAL; Note that (HSV || HSL || CMYK) && IndicatorMode.HEX is a bad idea
                    .onColorSelected(new OnColorSelectedListener() {
                        @Override
                        public void onColorSelected(@ColorInt int color) {
                            mCurrentEditTextView.setmBgColor(color);
                        }
                    })
                    .create()
                    .show(((MainActivityTabbed) getActivity()).getSupportFragmentManager(), "ChromaDialog");
        }

        @Override
        public void onEditStart(String currentText) {
            editTextWithDialog(mCurrentEditTextView, currentText);
        }
    };
    private StickerView.OperationListener stickerViewOperationListener = new StickerView.OperationListener() {
        @Override
        public void onDeleteClick() {
            mViews.remove(mCurrentView);
            checkTextViesState();

            mContentRootView.removeView(mCurrentView);
        }

        @Override
        public void onEdit(StickerView stickerView) {
            if (mCurrentEditTextView != null) {
                mCurrentEditTextView.setInEdit(false);
            }
            mCurrentView.setInEdit(false);
            mCurrentView = stickerView;
            mCurrentView.setInEdit(true);
            isDragMode = true;

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
    };
    private TextView hintTextView;


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
        mContentRootView = (RelativeLayout) rootView.findViewById(R.id.card_container);
        hintTextView = (TextView) rootView.findViewById(R.id.textview);
        return rootView;
    }




    public abstract void onRestoreViews(Card card);


    @Override
    public void onPause() {
        //calculatePropertiesAndSave();
        super.onPause();
    }

    public void calculatePropertiesAndSave() {
        Card cardTosave = ((MainActivityTabbed) getActivity()).getCurrentCard();

        if (this instanceof BackCard) {
            cardTosave.setBackSavedViewArray(getViewArray());

        } else if (this instanceof FrontCard) {
            cardTosave.setFrontSavedViewArray(getViewArray());


        }
        Log.d(LOG_TAG, "calculatePropertiesAndSave; " + cardTosave.toString());


        BroadcastManager.getInstance().sendBroadcastWithParcelable(Action.SAVE_STATE.name(), cardTosave);
    }

    private ArrayList<BaseViewModel> getViewArray() {
        ArrayList<BaseViewModel> resultVList = new ArrayList<>();

        for (View view : mViews) {
            if (view instanceof StickerView) {
                StickerPropertyModel stickerPropertyModel = new StickerPropertyModel();
                stickerPropertyModel = ((StickerView) view).calculate(stickerPropertyModel);
                resultVList.add(stickerPropertyModel);
            } else if (view instanceof BubbleTextView) {
                BubblePropertyModel bubblePropertyModel = new BubblePropertyModel();
                bubblePropertyModel = ((BubbleTextView) view).calculate(bubblePropertyModel);
                resultVList.add(bubblePropertyModel);
            }
        }
        checkTextViesState();

        return resultVList;
    }


    void checkTextViesState(){
        if(mViews!= null && !mViews.isEmpty()){
            hintTextView.setVisibility(View.GONE);
        } else {
            hintTextView.setVisibility(View.VISIBLE);

        }
    }

    public void addStickerView(String path) {
        final StickerView stickerView = new StickerView(getActivity());
        stickerView.setImageURI(Uri.parse(path));
        stickerView.setIsInEditListener(this);
        stickerView.setOperationListener(stickerViewOperationListener);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mContentRootView.addView(stickerView, lp);
        mViews.add(stickerView);
        setCurrentEdit(stickerView);
        checkTextViesState();

    }


    public void addStickerView(StickerPropertyModel stickerPropertyModel) {
        final StickerView stickerView = new StickerView(getActivity());
        stickerView.setImageURI(Uri.parse(stickerPropertyModel.getStickerURL()));
        stickerView.setIsInEditListener(this);
        stickerView.setOperationListener(stickerViewOperationListener);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mContentRootView.addView(stickerView, lp);
        mViews.add(stickerView);
        stickerView.restoreViewState(stickerPropertyModel);
        setCurrentEdit(stickerView);
        checkTextViesState();

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

    private void setCurrentEdit(BubbleTextView bubbleTextView) {
        if (mCurrentView != null) {
            mCurrentView.setInEdit(false);
        }
        if (mCurrentEditTextView != null) {
            mCurrentEditTextView.setInEdit(false);
        }
        mCurrentEditTextView = bubbleTextView;
        mCurrentEditTextView.setInEdit(true);
    }


    @Override
    public void editStateChanged(boolean isInEdit) {
        isDragMode = isInEdit;
    }


    public void addTextView(BubblePropertyModel bubblePropertyModel) {
        final BubbleTextView bubbleTextView = new BubbleTextView(getActivity(),
                Color.BLACK, 0);
        bubbleTextView.setText(bubblePropertyModel.getText());
        bubbleTextView.setImageResource(R.mipmap.bubble_7_rb);
        bubbleTextView.setOperationListener(mOperationListener);
        bubbleTextView.setmBgColor(bubblePropertyModel.getBgColor());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mContentRootView.addView(bubbleTextView, lp);
        mViews.add(bubbleTextView);

        bubbleTextView.restoreViewState(bubblePropertyModel);
        setCurrentEdit(bubbleTextView);
        checkTextViesState();


    }


    public void addTextView() {
        final BubbleTextView bubbleTextView = new BubbleTextView(getActivity(),
                Color.BLACK, 0);
        bubbleTextView.setImageResource(R.mipmap.bubble_7_rb);
        bubbleTextView.setOperationListener(mOperationListener);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mContentRootView.addView(bubbleTextView, lp);
        mViews.add(bubbleTextView);
        setCurrentEdit(bubbleTextView);
        checkTextViesState();


    }

    private void editTextWithDialog(final BubbleTextView bubbleTextView, String currentText) {
        final Context context = getActivity();
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        final AppCompatEditText edittext = new AppCompatEditText(context);
        edittext.setText(currentText);
        alert.setView(edittext);
        alert.setPositiveButton(context.getString(R.string.done), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String text = edittext.getText().toString();
                bubbleTextView.setText(text);
            }
        });
        alert.setNegativeButton("Cancel", null);
        alert.show();
    }


    public void clearFocus() {
        if (null != mCurrentView) {
            mCurrentView.setInEdit(false);
        }
        if (null != mCurrentEditTextView) {
            mCurrentEditTextView.setInEdit(false);
        }
    }
}
