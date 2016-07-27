package com.nlt.mobileteam.cards.fragment.cards;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.activity.MainActivityTabbed;
import com.nlt.mobileteam.cards.controller.BroadcastManager;
import com.nlt.mobileteam.cards.fragment.PlaceholderFragment;
import com.nlt.mobileteam.cards.model.Action;
import com.nlt.mobileteam.cards.model.Card;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.BubblePropertyModel;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.StickerPropertyModel;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.BubbleInputDialog;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.BubbleTextView;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.EditStateListener;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.StickerView;

import java.util.ArrayList;

public abstract class BaseCard extends Fragment implements View.OnClickListener, EditStateListener {

    public static final int FRONT = 1;
    public static final int BACK = 2;
    protected static final String CARD_INSTANCE = "card_parcelable_key_ss";
    private static final String LOG_TAG = "BaseCard";

    // protected TextView textView;
    // protected EditText editText;

    // protected TextView titleTextView;
    // protected EditText titleEditText;


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

       /* titleTextView = (TextView) rootView.findViewById(R.id.textview_title);
        titleEditText = (EditText) rootView.findViewById(R.id.edittext_title);
        textView = (TextView) rootView.findViewById(R.id.textview);
        editText = (EditText) rootView.findViewById(R.id.edittext);
*/
        //  titleTextView.setOnClickListener(this);
//        String title = ((Card) getArguments().getParcelable(CARD_INSTANCE)).getTitle();
        // titleTextView.setText(title);


        mContentRootView = (RelativeLayout) rootView.findViewById(R.id.card_container);
        return rootView;
    }


    public abstract void onRestoreViews(Card card);


    /*public void editText() {
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
    }*/
    @Deprecated
    public void saveText() {
        CharSequence text = null;
      /*  if (editText != null) {
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
            if (*//*!editText.isActivated() &&*//* !TextUtils.isEmpty(title)) {
                titleTextView.setText(title);
            }


        }*/
        Card cardTosave = ((MainActivityTabbed) getActivity()).getCurrentCard();
        Bundle arguments = getArguments();


        //      cardTosave.setTitle(title.toString());
        if (this instanceof BackCard) {
            //Log.d(LOG_TAG, "class was BackSideFragment");
//            cardTosave.setBackText(text.toString());
//            cardTosave.setFrontText(((Card) arguments.getParcelable(CARD_INSTANCE)).getFrontText());

        } else if (this instanceof FrontCard) {
            // Log.d(LOG_TAG, "class was FrontSideFragment");
//            cardTosave.setBackText(((Card) arguments.getParcelable(CARD_INSTANCE)).getBackText());
//            cardTosave.setFrontText(text.toString());
        }
        //Log.d(LOG_TAG, "card" + cardTosave.toString());

        //  CardDataController.getInstance().saveInStorageAndRemove();
        BroadcastManager.getInstance().sendBroadcastWithParcelable(Action.SAVE_STATE.name(), cardTosave);
    }


    @Override
    public void onPause() {
        //  calculatePropertiesAndSave();
        super.onPause();
    }

    public void calculatePropertiesAndSave() {

        Card cardTosave = ((MainActivityTabbed) getActivity()).getCurrentCard();
        Bundle arguments = getArguments();


        //      cardTosave.setTitle(title.toString());
        if (this instanceof BackCard) {
            //Log.d(LOG_TAG, "class was BackSideFragment");
            cardTosave.setBackTextArray(getTextViewModels());
            cardTosave.setBackImageArray(getImageViewModels());

//args
            // cardTosave.setFrontImageArray(((Card) arguments.getParcelable(CARD_INSTANCE)).getFrontImageArray());
            // cardTosave.setFrontTextArray(((Card) arguments.getParcelable(CARD_INSTANCE)).getFrontTextArray());

        } else if (this instanceof FrontCard) {
            // Log.d(LOG_TAG, "class was FrontSideFragment");
            cardTosave.setFrontTextArray(getTextViewModels());
            cardTosave.setFrontImageArray(getImageViewModels());
//args
            //-  cardTosave.setBackTextArray(((Card) arguments.getParcelable(CARD_INSTANCE)).getBackTextArray());
            //-  cardTosave.setBackImageArray(((Card) arguments.getParcelable(CARD_INSTANCE)).getBackImageArray());

//            cardTosave.setFrontText(text.toString());
        }
        //Log.d(LOG_TAG, "card" + cardTosave.toString());

        BroadcastManager.getInstance().sendBroadcastWithParcelable(Action.SAVE_STATE.name(), cardTosave);

    }

    private ArrayList<StickerPropertyModel> getImageViewModels() {
        ArrayList<StickerPropertyModel> resultVList = new ArrayList<>();
        StickerPropertyModel stickerPropertyModel = new StickerPropertyModel();

        for (View view : mViews) {
            if (view instanceof StickerView) {
                stickerPropertyModel = ((StickerView) view).calculate(stickerPropertyModel);
                resultVList.add(stickerPropertyModel);
            }
        }
        return resultVList;

    }

    private ArrayList<BubblePropertyModel> getTextViewModels() {
        ArrayList<BubblePropertyModel> resultVList = new ArrayList<>();
        BubblePropertyModel bubblePropertyModel = new BubblePropertyModel();

        for (View view : mViews) {
            if (view instanceof BubbleTextView) {
                bubblePropertyModel = ((BubbleTextView) view).calculate(bubblePropertyModel);
                resultVList.add(bubblePropertyModel);
            }
        }
        return resultVList;
    }

    @Override
    public void onClick(View v) {
        ((PlaceholderFragment) getParentFragment()).onFragmentClickListener.onTitleClick(v);
    }


    public void addStickerView(String path) {
        final StickerView stickerView = new StickerView(getActivity());
        stickerView.setImageURI(Uri.parse(path));
        stickerView.setIsInEditListener(this);
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


    public void addStickerView(StickerPropertyModel stickerPropertyModel) {
        final StickerView stickerView = new StickerView(getActivity());

        float scale = stickerPropertyModel.getScaling();
        //    stickerView.setScale(scale);
//        stickerView.setScaleY(scale);


        stickerView.setImageURI(Uri.parse(stickerPropertyModel.getStickerURL()));

        stickerView.setIsInEditListener(this);

        /*stickerView.setX(stickerPropertyModel.getxLocation());
        stickerView.setY(stickerPropertyModel.getyLocation());
*/
        //stickerView.

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
        stickerView.restoreViewState(stickerPropertyModel);

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


    @Deprecated
    public void saveView() {
        if (mCurrentView != null) {
            mCurrentView.setInEdit(false);
        }
    }

    @Override
    public void editStateChanged(boolean isInEdit) {
        MainActivityTabbed.isDragMode = isInEdit;
    }


    public void addTextView() {
        final BubbleTextView bubbleTextView = new BubbleTextView(getActivity(),
                Color.BLACK, 0);
        bubbleTextView.setImageResource(R.mipmap.bubble_7_rb);
        bubbleTextView.setOperationListener(new BubbleTextView.OperationListener() {
            @Override
            public void onDeleteClick() {
                mViews.remove(bubbleTextView);
                mContentRootView.removeView(bubbleTextView);
            }

            @Override
            public void onEdit(BubbleTextView bubbleTextView) {
                if (mCurrentView != null) {
                    mCurrentView.setInEdit(false);
                }
                mCurrentEditTextView.setInEdit(false);
                mCurrentEditTextView = bubbleTextView;
                mCurrentEditTextView.setInEdit(true);
            }

            @Override
            public void onClick(BubbleTextView bubbleTextView) {
                mBubbleInputDialog.setBubbleTextView(bubbleTextView);
                mBubbleInputDialog.show();
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
            public void onEditStart(String currentText) {
                editTextWithDialog(bubbleTextView, currentText);
            }


        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mContentRootView.addView(bubbleTextView, lp);
        mViews.add(bubbleTextView);
        setCurrentEdit(bubbleTextView);

    }

    private void editTextWithDialog(final BubbleTextView bubbleTextView, String currentText) {
        final Context context = getActivity();
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        final AppCompatEditText edittext = new AppCompatEditText(context);
        edittext.setText(currentText);
        alert.setView(edittext);

        alert.setPositiveButton(context.getString(R.string.done), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                Editable YouEditTextValue = edittext.getText();
                //OR
                String text = edittext.getText().toString();


                bubbleTextView.setText(text);


//                for (int i = 0; i < foldersArrayList.size(); i++) {
//                    if (item.getIdentifier().equals(foldersArrayList.get(i).getIdentifier())) {
//                        foldersArrayList.set(i, item);
//                        existed = true;
//                        adapter.notifyDataSetChanged();
//                        break;
//                    }
//                }
//                if (!existed) {
//                    addToDataStore(item);
//                }

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();

    }


}
