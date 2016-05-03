package com.nlt.mobileteam.cards.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.model.ToDoItem;
import com.nlt.mobileteam.cards.widget.ItemTouchHelperClass;

import java.util.ArrayList;
import java.util.Collections;

public class BasicListAdapter extends RecyclerView.Adapter<BasicListAdapter.ViewHolder> implements ItemTouchHelperClass.ItemTouchHelperAdapter {
    private final Context context;
    private ArrayList<ToDoItem> items;

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

        //    mJustDeletedToDoItem = items.remove(position);
        //    mIndexOfDeletedToDoItem = position;
            Intent i = null;
           // deleteAlarm(i, mJustDeletedToDoItem.getIdentifier().hashCode());
            notifyItemRemoved(position);

//            String toShow = (mJustDeletedToDoItem.getToDoText().length()>20)?mJustDeletedToDoItem.getToDoText().substring(0, 20)+"...":mJustDeletedToDoItem.getToDoText();
            String toShow = "Todo";
          // Snackbar.make(mCoordLayout, "Deleted " + toShow, Snackbar.LENGTH_SHORT)
          //         .setAction("UNDO", new View.OnClickListener() {
          //             @Override
          //             public void onClick(View v) {

          //                 //Comment the line below if not using Google Analytics
          //            /*     app.send(this, "Action", "UNDO Pressed");
          //                 items.add(mIndexOfDeletedToDoItem, mJustDeletedToDoItem);
          //                 if(mJustDeletedToDoItem.getToDoDate()!=null && mJustDeletedToDoItem.hasReminder()){
          //                     Intent i = new Intent(FoldersActivity.this, TodoNotificationService.class);
          //                     i.putExtra(TodoNotificationService.TODOTEXT, mJustDeletedToDoItem.getToDoText());
          //                     i.putExtra(TodoNotificationService.TODOUUID, mJustDeletedToDoItem.getIdentifier());
          //                     createAlarm(i, mJustDeletedToDoItem.getIdentifier().hashCode(), mJustDeletedToDoItem.getToDoDate().getTime());
          //                 }
          //                 notifyItemInserted(mIndexOfDeletedToDoItem);*/
          //             }
          //         }).show();
        }

        @Override
        public BasicListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_circle_try, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final BasicListAdapter.ViewHolder holder, final int position) {
            ToDoItem item = items.get(position);
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

            if (item.hasReminder() && item.getToDoDate() != null) {
                holder.mToDoTextview.setMaxLines(1);
                holder.mTimeTextView.setVisibility(View.VISIBLE);
//                holder.mToDoTextview.setVisibility(View.GONE);
            } else {
                holder.mTimeTextView.setVisibility(View.GONE);
                holder.mToDoTextview.setMaxLines(2);
            }
            holder.mToDoTextview.setText(item.getToDoText());
            holder.mToDoTextview.setTextColor(todoTextColor);
//            holder.mColorTextView.setBackgroundColor(Color.parseColor(item.getTodoColor()));

//            TextDrawable myDrawable = TextDrawable.builder().buildRoundRect(item.getToDoText().substring(0,1),Color.RED, 10);
            //We check if holder.color is set or not
//            if(item.getTodoColor() == null){
//                ColorGenerator generator = ColorGenerator.MATERIAL;
//                int color = generator.getRandomColor();
//                item.setTodoColor(color+"");
//            }
//            Log.d("OskarSchindler", "Color: "+item.getTodoColor());
            TextDrawable myDrawable = TextDrawable.builder().beginConfig()
                    .textColor(Color.WHITE)
                    .useFont(Typeface.DEFAULT)
                    .toUpperCase()
                    .endConfig()
                    .buildRound(item.getToDoText().substring(0, 1), item.getTodoColor());

//            TextDrawable myDrawable = TextDrawable.builder().buildRound(item.getToDoText().substring(0,1),holder.color);
            holder.mColorImageView.setImageDrawable(myDrawable);
            if (item.getToDoDate() != null) {
                String timeToShow;
                //  if(android.text.format.DateFormat.is24HourFormat(FoldersActivity.this)){
                //      timeToShow = AddToDoActivity.formatDate(FoldersActivity.DATE_TIME_FORMAT_24_HOUR, item.getToDoDate());
                //  }
                //  else{
                //      timeToShow = AddToDoActivity.formatDate(FoldersActivity.DATE_TIME_FORMAT_12_HOUR, item.getToDoDate());
                //  }
                // holder.mTimeTextView.setText(timeToShow);
            }


        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public BasicListAdapter(Context context , ArrayList<ToDoItem> items) {
this.context=  context;
            this.items = items;
        }


        @SuppressWarnings("deprecation")
        public class ViewHolder extends RecyclerView.ViewHolder {

            View mView;
            LinearLayout linearLayout;
            TextView mToDoTextview;
            //            TextView mColorTextView;
            ImageView mColorImageView;
            TextView mTimeTextView;
//            int color = -1;

            public ViewHolder(View v) {
                super(v);
                mView = v;
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToDoItem item = items.get(ViewHolder.this.getAdapterPosition());
                        Intent i = null;/* new Intent(FoldersActivity.this, AddToDoActivity.class)*/
                        ;
                        //i.putExtra(TODOITEM, item);
                        //startActivityForResult(i, REQUEST_ID_TODO_ITEM);
                    }
                });
                mToDoTextview = (TextView) v.findViewById(R.id.toDoListItemTextview);
                mTimeTextView = (TextView) v.findViewById(R.id.todoListItemTimeTextView);
//                mColorTextView = (TextView)v.findViewById(R.id.toDoColorTextView);
                mColorImageView = (ImageView) v.findViewById(R.id.toDoListItemColorImageView);
                linearLayout = (LinearLayout) v.findViewById(R.id.listItemLinearLayout);
            }


        }
    }