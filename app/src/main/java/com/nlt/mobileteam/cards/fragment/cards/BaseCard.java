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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.activity.MainActivityTabbed;
import com.nlt.mobileteam.cards.controller.BroadcastManager;
import com.nlt.mobileteam.cards.model.Action;
import com.nlt.mobileteam.cards.model.Card;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.SavableView;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.StickerPropertyModel;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.TextPropertyModel;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.BaseTextView;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.BaseView;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.EditStateListener;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.StickerView;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.ResizableTextView;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.ResizableView;
import com.nlt.mobileteam.cards.widget.OperationListener;
import com.thebluealliance.spectrum.SpectrumPalette;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public abstract class BaseCard extends Fragment implements EditStateListener {

    public static final int FRONT = 1;
    public static final int BACK = 2;
    protected static final String CARD_INSTANCE = "card_parcelable_key_ss";
    private static final String LOG_TAG = "BaseCard";


    public BaseView mCurrentEditTextView;

    public ArrayList<View> mViews;

    public RelativeLayout mContentRootView;
    private OperationListener mOperationListener = new OperationListener() {
        @Override
        public void onDeleteClick() {
            mViews.remove(mCurrentEditTextView);
            checkTextViesState();
            mContentRootView.removeView((View) mCurrentEditTextView);
        }

        @Override
        public void onEdit(BaseView view) {

            mCurrentEditTextView.setInEdit(false);
            mCurrentEditTextView = view;
            mCurrentEditTextView.setInEdit(true);
            initTextEditDialog();
            ((MainActivityTabbed) getActivity()).setIsDragMode(true);
            setTextEditDialogVisibility(VISIBLE);

        }

        @Override
        public void onClick(BaseView view) {
            ((MainActivityTabbed) getActivity()).setIsDragMode(true);

        }

        @Override
        public void onTop(BaseView bubbleTextView) {
            int position = mViews.indexOf(bubbleTextView);
            if (position == mViews.size() - 1) {
                return;
            }
            BaseView textView = (BaseView) mViews.remove(position);
            mViews.add(mViews.size(), (View) textView);
        }

        @Override
        public void onDoubleTap(String mStr) {
            Activity activity = getActivity();
            setTextEditDialogVisibility(VISIBLE);
            //editTextWithDialog();

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

    private TextView hintTextView;
    private Activity mActivity;


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
        MainActivityTabbed activity = (MainActivityTabbed) getActivity();
        Card cardTosave = null;
        if (null == activity) {
            cardTosave = activity.getCurrentCard();
        } else if (null != mActivity) {
            cardTosave = ((MainActivityTabbed) mActivity).getCurrentCard();
        }
        if (this instanceof BackCard) {
            cardTosave.setBackSavedViewArray(getViewArray());

        } else if (this instanceof FrontCard) {
            cardTosave.setFrontSavedViewArray(getViewArray());


        }
        Log.d(LOG_TAG, "calculatePropertiesAndSave; " + cardTosave.toString());


        BroadcastManager.getInstance().sendBroadcastWithParcelable(Action.SAVE_STATE.name(), cardTosave);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    private ArrayList<SavableView> getViewArray() {
        ArrayList<SavableView> resultVList = new ArrayList<>();

        for (View view : mViews) {
            if (view instanceof StickerView) {
                StickerPropertyModel stickerPropertyModel = new StickerPropertyModel();
                stickerPropertyModel = ((StickerView) view).calculate(stickerPropertyModel);
                resultVList.add(stickerPropertyModel);
            } else if (view instanceof ResizableTextView) {
                TextPropertyModel textPropertyModel = ((ResizableView) view).saveViewState();
                resultVList.add(textPropertyModel);
            }
        }
        checkTextViesState();

        return resultVList;
    }


    void checkTextViesState() {
        if (mViews != null && !mViews.isEmpty()) {
            hintTextView.setVisibility(GONE);
        } else {
            hintTextView.setVisibility(VISIBLE);
        }
    }

    public void addStickerView(String path) {
        final StickerView stickerView = new StickerView(getActivity());
        stickerView.setImageURI(Uri.parse(path));
        stickerView.setIsInEditListener(this);
        stickerView.setOperationListener(mOperationListener);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mContentRootView.addView(stickerView, lp);
        mViews.add(stickerView);
        setCurrentEdit(stickerView, true);
        checkTextViesState();

    }


    public void addStickerView(StickerPropertyModel stickerPropertyModel) {
        final StickerView stickerView = new StickerView(getActivity());
        stickerView.setImageURI(Uri.parse(stickerPropertyModel.getStickerURL()));
        stickerView.setIsInEditListener(this);
        stickerView.setOperationListener(mOperationListener);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mContentRootView.addView(stickerView, lp);
        mViews.add(stickerView);
        stickerView.restoreViewState(stickerPropertyModel);
        setCurrentEdit(stickerView, false);
        checkTextViesState();

    }


    private void setCurrentEdit(BaseView stickerView, boolean isInEdit) {

        if (mCurrentEditTextView != null) {
            mCurrentEditTextView.setInEdit(false);
        }
        mCurrentEditTextView = stickerView;
        stickerView.setInEdit(isInEdit);
    }
/*
    private void setCurrentEdit(BaseTextView textView, boolean isEdit) {
        if (mCurrentView != null) {
            mCurrentView.setInEdit(false);
        }
        if (mCurrentEditTextView != null) {
            mCurrentEditTextView.setInEdit(false);
        }
        mCurrentEditTextView = textView;
        mCurrentEditTextView.setInEdit(isEdit);
    }*/


    @Override
    public void editStateChanged(boolean isInEdit) {
        ((MainActivityTabbed) getActivity()).setIsDragMode(isInEdit);

    }


    public void addTextView(TextPropertyModel bubblePropertyModel) {
        ResizableTextView txt = new ResizableTextView(getActivity());
        txt.setText(bubblePropertyModel.getText() + " restored");
        txt.setOperationListener(mOperationListener);
        mContentRootView.addView(txt);
        mViews.add(txt);
        txt.restoreViewState(bubblePropertyModel);
        setCurrentEdit(txt, false);
        checkTextViesState();
    }

    public void addNewTextView() {
        ResizableTextView txt = new ResizableTextView(getActivity());
        mCurrentEditTextView = txt;
        txt.setOperationListener(mOperationListener);

        mContentRootView.addView(txt);
        mViews.add(txt);
        txt.setText("Type your text");

        setCurrentEdit(txt, true);
        checkTextViesState();

        float centerY = mContentRootView.getHeight() / 3;
        float centerX = mContentRootView.getWidth() / 8;
        txt.setX(centerX);
        txt.setY(centerY);
    }

    public void editTextWithDialog() {
        String previousText = "";
        int prevColor = 0;
        if (null != mCurrentEditTextView) {
//            previousText = mCurrentEditTextView.getText();
//            prevColor = mCurrentEditTextView.getmBgColor();
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
//                mCurrentEditTextView.setmBgColor(selectedColor[0]);
//                mCurrentEditTextView.setTempColor(selectedColor[0]);
//                mCurrentEditTextView.setText(text);
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

        if (null != mCurrentEditTextView) {
            mCurrentEditTextView.setInEdit(false);
        }
    }

    private void initTextEditDialog() {
        if (mCurrentEditTextView instanceof ResizableTextView) {

            setTextEditDialogVisibility(VISIBLE);
            ((MainActivityTabbed) getActivity()).doneEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setTextEditDialogVisibility(GONE);
                    ((MainActivityTabbed) getActivity()).idleCard();
                }
            });
            ((MainActivityTabbed) getActivity()).addCardEditTxt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (mCurrentEditTextView instanceof ResizableTextView) {
                        ((ResizableTextView) mCurrentEditTextView).setText(s.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } else {
            setTextEditDialogVisibility(GONE);

        }
    }

    public void setTextEditDialogVisibility(int visible) {
        ((MainActivityTabbed) getActivity()).addCardEditTxt.setVisibility(visible);
        ((MainActivityTabbed) getActivity()).doneEditBtn.setVisibility(visible);

        if (mCurrentEditTextView instanceof ResizableTextView) {
            ((MainActivityTabbed) getActivity()).addCardEditTxt.setText(((ResizableTextView) mCurrentEditTextView).getText());
        }
    }
}
