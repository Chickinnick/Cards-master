package com.nlt.mobileteam.cards.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.view.View;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.Util;
import com.nlt.mobileteam.cards.adapter.BasicListAdapter;
import com.nlt.mobileteam.cards.controller.StorageController;
import com.nlt.mobileteam.cards.model.Card;
import com.nlt.mobileteam.cards.model.Folder;
import com.nlt.mobileteam.cards.widget.ItemTouchHelperClass;

import java.util.ArrayList;

public class ScrollingActivity extends AppCompatActivity implements BasicListAdapter.OnItemClickListener {

    private FloatingActionButton fab;
    private BasicListAdapter adapter;
    public ItemTouchHelper itemTouchHelper;


    private ArrayList<Folder> foldersArrayList;
    private RecyclerView foldersRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        foldersRecyclerView = (RecyclerView) findViewById(R.id.foldersRecyclerView);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ScrollingActivity.this);

                final AppCompatEditText edittext = new AppCompatEditText(ScrollingActivity.this);

                alert.setTitle("Name category");

                alert.setView(edittext);

                alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        Editable YouEditTextValue = edittext.getText();
                        //OR
                        String name = edittext.getText().toString();


                        int color = ColorGenerator.MATERIAL.getRandomColor();
                        Folder item = new Folder(name);
                        item.setColor(color);
                        ArrayList<Card> defaultCards = new ArrayList<Card>();
                        defaultCards.add(new Card("", ""));
                        item.setCards(defaultCards);
                        boolean existed = false;
                        for (int i = 0; i < foldersArrayList.size(); i++) {
                            if (item.getIdentifier().equals(foldersArrayList.get(i).getIdentifier())) {
                                foldersArrayList.set(i, item);
                                existed = true;
                                adapter.notifyDataSetChanged();
                                break;
                            }
                        }
                        if (!existed) {
                            addToDataStore(item);
                        }

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();


            }
        });


        foldersArrayList = StorageController.getInstance().getFolderFromStorage();
        adapter = new BasicListAdapter(this, foldersArrayList);
        adapter.setOnItemClickListener(this);

        //  foldersRecyclerView.setHasFixedSize(true);
        foldersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        foldersRecyclerView.setLayoutManager(new LinearLayoutManager(this));


       /* customRecyclerScrollViewListener = new CustomRecyclerScrollViewListener() {
            @Override
            public void show() {

                mAddToDoItemFAB.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
//                mAddToDoItemFAB.animate().translationY(0).setInterpolator(new AccelerateInterpolator(2.0f)).start();
            }

            @Override
            public void hide() {

                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) mAddToDoItemFAB.getLayoutParams();
                int fabMargin = lp.bottomMargin;
                mAddToDoItemFAB.animate().translationY(mAddToDoItemFAB.getHeight() + fabMargin).setInterpolator(new AccelerateInterpolator(2.0f)).start();
            }
        };*/
        // mRecyclerView.addOnScrollListener(customRecyclerScrollViewListener);


        ItemTouchHelper.Callback callback = new ItemTouchHelperClass(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(foldersRecyclerView);
        foldersRecyclerView.setAdapter(adapter);


//        foldersRecyclerView.setOn
//        setUpTransitions();

    }

    private void addToDataStore(Folder item) {
        foldersArrayList.add(item);
        adapter.notifyItemInserted(foldersArrayList.size() - 1);
        StorageController.getInstance().saveFolders(foldersArrayList);
    }

    @Override
    public void onItemClick(Folder folder) {

        Intent intent = new Intent();
        intent.putExtra(Util.SELECTED_FOLDER_EXTRA, folder);
        setResult(Activity.RESULT_OK, intent);
        // CardDataController.getInstance().clear();
        finish();
    }
}
