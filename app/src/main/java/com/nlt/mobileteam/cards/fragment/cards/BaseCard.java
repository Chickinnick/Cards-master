package com.nlt.mobileteam.cards.fragment.cards;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.activity.MainActivityTabbed;
import com.nlt.mobileteam.cards.controller.BroadcastManager;
import com.nlt.mobileteam.cards.fragment.PlaceholderFragment;
import com.nlt.mobileteam.cards.model.Action;
import com.nlt.mobileteam.cards.model.Card;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.BubblePropertyModel;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.SavableView;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.StickerPropertyModel;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.BubbleTextView;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.EditStateListener;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.StickerView;
import com.thebluealliance.spectrum.SpectrumPalette;

import java.util.ArrayList;


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
            ((MainActivityTabbed) getActivity()).setIsDragMode(true);
            ((MainActivityTabbed) getActivity()).showEditControls();


        }

        @Override
        public void onClick(BubbleTextView bubbleTextView) {
            ((MainActivityTabbed) getActivity()).setIsDragMode(true);

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
        public void onDoubleTap(String mStr) {
            Activity activity = getActivity();

            editTextWithDialog();

         /*   new ChromaDialog.Builder()
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
                    .show(((MainActivityTabbed) getActivity()).getSupportFragmentManager(), "ChromaDialog");*/
        }

        @Override
        public void onEditStart(String currentText) {

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
            ((MainActivityTabbed) getActivity()).setIsDragMode(true);


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

    private ArrayList<SavableView> getViewArray() {
        ArrayList<SavableView> resultVList = new ArrayList<>();

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

    public void addStickerView(String path ) {
        final StickerView stickerView = new StickerView(getActivity());
        stickerView.setImageURI(Uri.parse(path));
        stickerView.setIsInEditListener(this);
        stickerView.setOperationListener(stickerViewOperationListener);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mContentRootView.addView(stickerView, lp);
        mViews.add(stickerView);
        setCurrentEdit(stickerView , true);
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
        setCurrentEdit(stickerView , false);
        checkTextViesState();

    }


    private void setCurrentEdit(StickerView stickerView , boolean isInEdit) {
        if (mCurrentView != null) {
            mCurrentView.setInEdit(false);
        }
        if (mCurrentEditTextView != null) {
            mCurrentEditTextView.setInEdit(false);
        }
        mCurrentView = stickerView;
        stickerView.setInEdit(isInEdit);
    }

    private void setCurrentEdit(BubbleTextView bubbleTextView, boolean isEdit) {
        if (mCurrentView != null) {
            mCurrentView.setInEdit(false);
        }
        if (mCurrentEditTextView != null) {
            mCurrentEditTextView.setInEdit(false);
        }
        mCurrentEditTextView = bubbleTextView;
        mCurrentEditTextView.setInEdit(isEdit);
    }


    @Override
    public void editStateChanged(boolean isInEdit) {
        ((MainActivityTabbed) getActivity()).setIsDragMode(isInEdit);
        ((MainActivityTabbed) getActivity()).showEditControls();

    }


    public void addTextView(BubblePropertyModel bubblePropertyModel) {
        final BubbleTextView bubbleTextView = new BubbleTextView(getActivity(),
                Color.BLACK, 0);
        bubbleTextView.setText(bubblePropertyModel.getText());
        bubbleTextView.setOperationListener(mOperationListener);
        bubbleTextView.setmBgColor(bubblePropertyModel.getBgColor());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mContentRootView.addView(bubbleTextView, lp);
        mViews.add(bubbleTextView);

        bubbleTextView.restoreViewState(bubblePropertyModel);
        setCurrentEdit(bubbleTextView , false);
        checkTextViesState();


    }


    public void addTextView() {
        final BubbleTextView bubbleTextView = new BubbleTextView(getActivity(),
                Color.BLACK, 0);
        bubbleTextView.setOperationListener(mOperationListener);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mContentRootView.addView(bubbleTextView, lp);
        mViews.add(bubbleTextView);
        setCurrentEdit(bubbleTextView , true);
        checkTextViesState();


    }

    public void editTextWithDialog() {
        String previousText = "";
        int prevColor = 0;
        if (null != mCurrentEditTextView) {
            previousText = mCurrentEditTextView.getText();
            prevColor = mCurrentEditTextView.getmBgColor();
        }
        final Context context = getActivity();

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.edit_dialog);

        final EditText editText = (EditText) dialog.findViewById(R.id.edit_text);
        editText.setHint("Input text here");
        editText.setText(previousText);

        final SpectrumPalette spectrumPalette = (SpectrumPalette) dialog.findViewById(R.id.palette);
        final int[] colors = getResources().getIntArray(R.array.demo_colors);
        spectrumPalette.setColors(colors);
        spectrumPalette.setSelectedColor(prevColor);
        final int[] selectedColor = new int[1];

        final SeekBar seekBar = (SeekBar) dialog.findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                adjustAlpha(selectedColor[0], progress / 100f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        spectrumPalette.setOnColorSelectedListener(new SpectrumPalette.OnColorSelectedListener() {
            @Override
            public void onColorSelected(@ColorInt int color) {
                selectedColor[0] = color;
            }
        });


        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                mCurrentEditTextView.setmBgColor(selectedColor[0]);
                mCurrentEditTextView.setTempColor(selectedColor[0]);
                mCurrentEditTextView.setText(text);
                dialog.dismiss();
            }
        });

        dialog.show();

        /*
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
        alert.show();*/
    }

    public int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    public void clearFocus() {
        if (null != mCurrentView) {
            mCurrentView.setInEdit(false);
        }
        if (null != mCurrentEditTextView) {
            mCurrentEditTextView.setInEdit(false);
        }
    }

    public void adjustHeight(int progress) {
        mCurrentEditTextView.adjustHeight(progress);
    }
    public void adjustWidth(int progress) {
        mCurrentEditTextView.adjustWidth(progress);
    }
}
