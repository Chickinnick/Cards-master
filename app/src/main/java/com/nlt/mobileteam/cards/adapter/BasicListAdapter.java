package com.nlt.mobileteam.cards.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.model.Folder;
import com.nlt.mobileteam.cards.widget.ItemTouchHelperClass;

import java.util.ArrayList;
import java.util.Collections;

public class BasicListAdapter extends RecyclerView.Adapter<BasicListAdapter.ViewHolder> implements ItemTouchHelperClass.ItemTouchHelperAdapter {
    private final Context context;
    private final RecyclerView rootView;
    private ArrayList<Folder> items;
    private OnItemClickListener onItemClickListener;
    private Folder mJustDeletedItem;
    private int mIndexOfDeletedItem;
    private LinearLayout layout;

    public BasicListAdapter(Context context, ArrayList<Folder> items, RecyclerView recyclerView) {
        this.context = context;
        this.items = items;
        this.rootView = recyclerView;
    }


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
            //Remove this line if not using Google Analytics
//            app.send(this, "Action", "Swiped Todo Away");

            mJustDeletedItem = items.remove(position);
            mIndexOfDeletedItem = position;
            notifyItemRemoved(position);
            String toShow = (mJustDeletedItem.getName().length() > 20) ? mJustDeletedItem.getName().substring(0, 20) + "..." : mJustDeletedItem.getName();
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
        public BasicListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_circle_try, parent, false);

            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final BasicListAdapter.ViewHolder holder, final int position) {
            Folder item = items.get(position);
//            if(item.getToDoDate()!=null && item.getToDoDate().before(new Date())){
//                item.setToDoDate(null);
//            }
         //   SharedPreferences sharedPreferences = getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE);
            //Background color for each to-do item. Necessary for night/day mode
            int bgColor=  Color.WHITE;
            //color of title text in our to-do item. White for night mode, dark gray for day mode
            int todoTextColor  = context.getResources().getColor(R.color.secondary_text);
        //  if (sharedPreferences.getString(THEME_SAVED, LIGHTTHEME).equals(LIGHTTHEME)) {
        //      bgColor = Color.WHITE;
        //      todoTextColor = getResources().getColor(R.color.secondary_text);
        //  } else {
        //      bgColor = Color.DKGRAY;
        //      todoTextColor = Color.WHITE;
        //  }
            holder.linearLayout.setBackgroundColor(bgColor);
            layout = holder.linearLayout;
        /*    if (item.hasReminder() && item.getToDoDate() != null) {
                holder.textView.setMaxLines(1);
                holder.countTextView.setVisibility(View.VISIBLE);
//                holder.textView.setVisibility(View.GONE);
            } else {
                holder.countTextView.setVisibility(View.GONE);
                holder.textView.setMaxLines(2);
            }
        */
            holder.mToDoTextview.setText(item.getName());
            holder.countTextView.setText(item.getCards().size() + " cards");
            holder.mToDoTextview.setTextColor(todoTextColor);
//            holder.mColorTextView.setBackgroundColor(Color.parseColor(item.getTodoColor()));

//            TextDrawable myDrawable = TextDrawable.builder().buildRoundRect(item.getToDoText().substring(0,1),Color.RED, 10);
            //We check if holder.color is set or not
            if (item.getColor() == 0) {
                ColorGenerator generator = ColorGenerator.MATERIAL;
                int color = generator.getRandomColor();
                item.setColor(color);
            }

            TextDrawable myDrawable = TextDrawable.builder().beginConfig()
                    .textColor(Color.WHITE)
                    .useFont(Typeface.DEFAULT)
                    .toUpperCase()
                    .endConfig()
                    .buildRound(item.getName().substring(0, 1), item.getColor());

            holder.mColorImageView.setImageDrawable(myDrawable);
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


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Folder folder);
    }


    @SuppressWarnings("deprecation")
    public class ViewHolder extends RecyclerView.ViewHolder {

            View mView;
            LinearLayout linearLayout;
            TextView mToDoTextview;
            //            TextView mColorTextView;
            ImageView mColorImageView;
            TextView countTextView;
        private String LOG_TAG = "HolderView";
//            int color = -1;

            public ViewHolder(View v) {
                super(v);
                mView = v;
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Folder item = items.get(ViewHolder.this.getAdapterPosition());
                        Intent i = null;/* new Intent(FoldersActivity.this, AddToDoActivity.class)*/
                        onItemClickListener.onItemClick(item);
                        Log.d(LOG_TAG, "on item click" + item.getIdentifier());
                        //i.putExtra(TODOITEM, item);
                        //startActivityForResult(i, REQUEST_ID_TODO_ITEM);
                    }
                });
                mToDoTextview = (TextView) v.findViewById(R.id.toDoListItemTextview);
                countTextView = (TextView) v.findViewById(R.id.countTextView);
//                mColorTextView = (TextView)v.findViewById(R.id.toDoColorTextView);
                mColorImageView = (ImageView) v.findViewById(R.id.toDoListItemColorImageView);
                linearLayout = (LinearLayout) v.findViewById(R.id.listItemLinearLayout);

            }

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }
    }


}