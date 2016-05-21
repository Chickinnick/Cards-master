package com.nlt.mobileteam.cards.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.Util;
import com.nlt.mobileteam.cards.adapter.CustomAdapter;

public class BgPickerActivity extends AppCompatActivity implements CustomAdapter.OnItemClickListener {

    private CustomAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bg_picker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.backroundsRecyclerView);

        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        adapter = new CustomAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(int position, int item) {
        Intent intent = new Intent();
        intent.putExtra(Util.SELECTED_BG_EXTRA, item);
        setResult(RESULT_OK, intent);
        finish();
    }
}
