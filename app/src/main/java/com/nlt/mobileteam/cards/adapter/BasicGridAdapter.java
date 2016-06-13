package com.nlt.mobileteam.cards.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
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
import com.nlt.mobileteam.cards.widget.ItemTouchHelperClass;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Nick on 04.06.2016.
 */


public class BasicGridAdapter extends RecyclerView.Adapter<BasicGridAdapter.ViewHolder> implements ItemTouchHelperClass.ItemTouchHelperAdapter {
    private final Context context;
    private final RecyclerView rootView;
    private ArrayList<Card> items;
    private OnItemClickListener onItemClickListener;
    private Card mJustDeletedItem;
    private int mIndexOfDeletedItem;
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
        mJustDeletedItem = items.remove(position);
        mIndexOfDeletedItem = position;
        notifyItemRemoved(position);
        String toShow = (mJustDeletedItem.getFrontText().length() > 20) ? mJustDeletedItem.getFrontText().substring(0, 20) + "..." : mJustDeletedItem.getFrontText();
        if (layout != null && layout.getContext() != null) {
            Snackbar.make(rootView, "Deleted " + toShow, Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            items.add(mIndexOfDeletedItem, mJustDeletedItem);
                            notifyItemInserted(mIndexOfDeletedItem);
                        }
                    }).show();
        }
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
//            if(item.getToDoDate()!=null && item.getToDoDate().before(new Date())){
//                item.setToDoDate(null);
//            }
        //   SharedPreferences sharedPreferences = getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE);
        //Background color for each to-do item. Necessary for night/day mode
        int bgColor = Color.WHITE;
        //color of title text in our to-do item. White for night mode, dark gray for day mode
        int todoTextColor = context.getResources().getColor(R.color.secondary_text);
        //  if (sharedPreferences.getString(THEME_SAVED, LIGHTTHEME).equals(LIGHTTHEME)) {
        //      bgColor = Color.WHITE;
        //      todoTextColor = getResources().getColor(R.color.secondary_text);
        //  } else {
        //      bgColor = Color.DKGRAY;
        //      todoTextColor = Color.WHITE;
        //  }
        // holder.relativeLayout.setBackgroundColor(bgColor);
        layout = holder.relativeLayout;
        /*    if (item.hasReminder() && item.getToDoDate() != null) {
                holder.textView.setMaxLines(1);
                holder.countTextView.setVisibility(View.VISIBLE);
//                holder.textView.setVisibility(View.GONE);
            } else {
                holder.countTextView.setVisibility(View.GONE);
                holder.textView.setMaxLines(2);
            }
        */
        holder.textView.setText(item.getFrontText());
        if (item.isFavourite()) {
            holder.starImageBtn.setImageResource(R.drawable.ic_star_selected);
        } else {
            holder.starImageBtn.setImageResource(R.drawable.ic_star);

        }

        String path = item.getLinkToFrontImage();

            Picasso.with(context)
                    .load(path)
                    .resize(300, 200)
                    .centerCrop()
                    .into(holder.cardImageView);


        //holder.countTextView.setText(item.getCards().size() + " cards");
        holder.textView.setTextColor(todoTextColor);
//            holder.mColorTextView.setBackgroundColor(Color.parseColor(item.getTodoColor()));

//            TextDrawable myDrawable = TextDrawable.builder().buildRoundRect(item.getToDoText().substring(0,1),Color.RED, 10);
        //We check if holder.color is set or not
       /* if (item.getColor() == 0) {
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color = generator.getRandomColor();
            item.setColor(color);
        }*/

       /* TextDrawable myDrawable = TextDrawable.builder().beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRound(item.getName().substring(0, 1), item.getColor());
*/
        //      holder.cardImageView.setImageDrawable(myDrawable);
      /*      if (item.getToDoDate() != null) {
                String timeToShow;
                //  if(android.text.format.DateFormat.is24HourFormat(FoldersActivity.this)){
                //      timeToShow = AddToDoActivity.formatDate(FoldersActivity.DATE_TIME_FORMAT_24_HOUR, item.getToDoDate());
                //  }
                //  else{
                //      timeToShow = AddToDoActivity.formatDate(FoldersActivity.DATE_TIME_FORMAT_12_HOUR, item.getToDoDate());
                //  }
                // holder.countTextView.setText(timeToShow);
            }*/


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public BasicGridAdapter(Context context, ArrayList<Card> items, RecyclerView root) {
        this.context = context;
        this.items = items;
        this.rootView = root;

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
        //            TextView mColorTextView;
        ImageView cardImageView;
        ImageButton starImageBtn;
        private String LOG_TAG = "HolderView";
//            int color = -1;

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
//                mColorTextView = (TextView)v.findViewById(R.id.toDoColorTextView);
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
                        StorageController.getInstance().removeFromFavourites(card);

                    } else {
                        card.setFavourite(true);
                        StorageController.getInstance().saveInFavourites(card);
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