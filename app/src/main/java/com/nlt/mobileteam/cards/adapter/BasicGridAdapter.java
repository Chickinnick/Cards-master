package com.nlt.mobileteam.cards.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.controller.BroadcastManager;
import com.nlt.mobileteam.cards.controller.StorageController;
import com.nlt.mobileteam.cards.model.Action;
import com.nlt.mobileteam.cards.model.Card;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.SavableView;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.StickerPropertyModel;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.TextPropertyModel;
import com.nlt.mobileteam.cards.widget.ItemTouchHelperClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Nick on 04.06.2016.
 */


public class BasicGridAdapter extends RecyclerView.Adapter<BasicGridAdapter.ViewHolder> implements ItemTouchHelperClass.ItemTouchHelperAdapter {
    private final Context context;
    private ArrayList<Card> items;
    private OnItemClickListener onItemClickListener;
    private RelativeLayout layout;

    @Override
    public void onItemMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(items, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(items, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemRemoved(final int position) {
        notifyItemRemoved(position);
    }

    @Override
    public BasicGridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_grid, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BasicGridAdapter.ViewHolder holder, final int position) {
        Card item = items.get(position);
        int bgColor = Color.WHITE;
        int todoTextColor = context.getResources().getColor(R.color.secondary_text);
        layout = holder.relativeLayout;
        holder.textView.setText(getFirstTextFromArr(item));

        String firstImgFromArr = getFirstImgFromArr(item);
        if (!TextUtils.isEmpty(firstImgFromArr)) {
            Picasso.with(context).load(firstImgFromArr).resizeDimen(R.dimen.preview_grid_w, R.dimen.preview_grid_h).into(holder.cardImageView);
        }
        if (item.isFavourite()) {
            holder.starImageBtn.setImageResource(R.drawable.ic_star_selected);
        } else {
            holder.starImageBtn.setImageResource(R.drawable.ic_star);

        }
        holder.textView.setTextColor(todoTextColor);

    }

    private String getFirstTextFromArr(Card item) {
        for (SavableView savableView : item.getFrontSavedViewArray()) {
            if (savableView instanceof TextPropertyModel) {
                return ((TextPropertyModel) savableView).getText();
            }
        }

        return "";
    }

    private String getFirstImgFromArr(Card item) {
        for (SavableView savableView : item.getFrontSavedViewArray()) {
            if (savableView instanceof StickerPropertyModel) {
                return ((StickerPropertyModel) savableView).getStickerURL();
            }
        }

        return "";
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public BasicGridAdapter(Context context, ArrayList<Card> items) {
        this.context = context;
        this.items = items;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Card card, int adapterPosition);
    }


    @SuppressWarnings("deprecation")
    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        RelativeLayout relativeLayout;
        TextView textView;
        TextView titleTextView;
        ImageView cardImageView;
        ImageButton starImageBtn;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Card item = items.get(ViewHolder.this.getAdapterPosition());
                    Intent i = null;/* new Intent(FoldersActivity.this, AddToDoActivity.class)*/
                    onItemClickListener.onItemClick(item, ViewHolder.this.getAdapterPosition());
                    //i.putExtra(TODOITEM, item);
                    //startActivityForResult(i, REQUEST_ID_TODO_ITEM);
                }
            });
            textView = (TextView) v.findViewById(R.id.item_textview);
            titleTextView = (TextView) v.findViewById(R.id.title_grid);
            cardImageView = (ImageView) v.findViewById(R.id.item_imageview);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.item_grid_root);
            starImageBtn = (ImageButton) v.findViewById(R.id.favourite_btn);
            starImageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("adapter", " onClick");
                    Card card = items.get(ViewHolder.this.getAdapterPosition());
                    if (!card.isFavourite()) {
                        starImageBtn.setImageResource(R.drawable.ic_star_selected);
                    } else {
                        starImageBtn.setImageResource(R.drawable.ic_star);
                    }
                    if (card.isFavourite()) {
                        card.setFavourite(false);
                        StorageController.getInstance().removeFromFavourites(card, context);

                    } else {
                        card.setFavourite(true);
                        StorageController.getInstance().saveInFavourites(card, context);
                    }
                    BroadcastManager.getInstance().sendBroadcastWithParcelable(Action.SAVE_STATE.name(), card);
                }
            });
        }

        public RelativeLayout getRelativeLayout() {
            return relativeLayout;
        }
    }


}