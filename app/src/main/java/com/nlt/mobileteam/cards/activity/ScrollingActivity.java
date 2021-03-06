package com.nlt.mobileteam.cards.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.ThemeManager;
import com.nlt.mobileteam.cards.Util;
import com.nlt.mobileteam.cards.adapter.BasicListAdapter;
import com.nlt.mobileteam.cards.fragment.GridFragment;
import com.nlt.mobileteam.cards.fragment.ListFragment;
import com.nlt.mobileteam.cards.model.Action;
import com.nlt.mobileteam.cards.model.Card;
import com.nlt.mobileteam.cards.model.Folder;

public class ScrollingActivity extends AppCompatActivity implements BasicListAdapter.OnItemClickListener,
        ListFragment.OnFragmentInteractionListener, GridFragment.OnGridFragmentInteractionListener {

    private FloatingActionButton fab;

    private ListFragment listFragment;
    private GridFragment gridFragment;
    private CollapsingToolbarLayout collapsingToolbar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        listFragment = ListFragment.newInstance("", " ");
        setupFragment();

        Intent intent = getIntent();
        boolean isViewFolder = intent.getBooleanExtra(Action.VIEW_FOLDER_INTENT_EXTRA, false);
        Folder folderToView = intent.getParcelableExtra(Action.VIEW_FOLDER_INTENT_EXTRA_DATA);
        if (isViewFolder) {
            startGridFragment(folderToView);
            String title = folderToView.getName().toString();
            if (!TextUtils.isEmpty(title)) {
                collapsingToolbar.setTitle(title);
            }
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listFragment.isVisible()) {
                    listFragment.onFabClicked(view);
                } else if (gridFragment.isVisible()) {
                    gridFragment.onFabClicked(view);
                }
            }
        });

    }

    private void setupFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.scrolling_activity_content, listFragment);
        fragmentTransaction.commit();
    }

    private void startGridFragment(Folder folder) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        gridFragment = GridFragment.newInstance(folder);
        fragmentTransaction.hide(listFragment);
        fragmentTransaction.replace(R.id.scrolling_activity_content, gridFragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onItemClick(Folder folder) {
        ThemeManager.getInstance().changeThemeColor(this, folder.getColor());
        fab.setImageResource(R.drawable.ic_search);
        startGridFragment(folder);
        String title = folder.getName().toString();
        if (!TextUtils.isEmpty(title)) {
            collapsingToolbar.setTitle(title);
        }
        // setSupportActionBar(toolbar);
//        Intent intent = new Intent();
//        intent.putExtra(Util.SELECTED_FOLDER_EXTRA, folder);
//        setResult(Activity.RESULT_OK, intent);
//        // CardDataController.getInstance().clear();
//        finish();


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public CollapsingToolbarLayout getCollapsingToolbar() {
        return collapsingToolbar;
    }

    @Override
    public void onFragmentInteraction(Folder folder, Card card, int adapterPosition) {
        Intent intent = new Intent();
        intent.putExtra(Util.SELECTED_FOLDER_EXTRA, folder);
        intent.putExtra(Util.SELECTED_CARD_POS_EXTRA, adapterPosition);
        setResult(Activity.RESULT_OK, intent);
        //CardDataController.getInstance().clear();
        finish();
    }
}
