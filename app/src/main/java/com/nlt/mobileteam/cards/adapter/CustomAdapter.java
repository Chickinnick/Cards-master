package com.nlt.mobileteam.cards.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nlt.mobileteam.cards.R;
import com.squareup.picasso.Picasso;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private final Context context;
    private int[] dataSet = new int[]{

            R.drawable.backgr1,
            R.drawable.backgr2,
            R.drawable.backgr4,
            R.drawable.backgr5,
            R.drawable.backgr6,
            R.drawable.bg_1,
            R.drawable.bg_2,
            R.drawable.bg_3

    };
    ;

    public interface OnItemClickListener {
        void onItemClick(int position, int item);
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.image_view_bg);
        }
    }

    public CustomAdapter(Context context) {
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bg, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        ImageView imageView = holder.imageViewIcon;
        Picasso.with(context).load(dataSet[listPosition])
                .resize(600, 400)/*.transform(new SquareTransformation())*/.centerCrop().into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(listPosition, dataSet[listPosition]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.length;
    }
}