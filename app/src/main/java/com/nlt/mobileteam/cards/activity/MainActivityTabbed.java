package com.nlt.mobileteam.cards.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pwittchen.swipe.library.Swipe;
import com.github.pwittchen.swipe.library.SwipeListener;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.adapter.MainFragmentPagerAdapter;
import com.nlt.mobileteam.cards.controller.StorageController;
import com.nlt.mobileteam.cards.model.Card;
import com.nlt.mobileteam.cards.model.Folder;
import com.nlt.mobileteam.cards.widget.Fab;

import java.util.ArrayList;
import java.util.Random;

public class MainActivityTabbed extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeListener, ViewPager.OnPageChangeListener, View.OnClickListener {

    private static final String TAG = "MainActivity";

    private ViewPager mViewPager;
    private Swipe swipe;
    private MainFragmentPagerAdapter mSectionsPagerAdapter;
    private MaterialSheetFab<Fab> materialSheetFab;
    private TextView positionIndicator;
    private ArrayList<Card> cards;
    private int randomItem;
    private ImageView backgroundImage;
    private boolean isEditing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_tabbed);
        ArrayList<Folder> foldersFromStorage = StorageController.getInstance().getFolderFromStorage();
        cards = foldersFromStorage.get(0).getCards();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_tabbed);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.setDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        positionIndicator = (TextView) findViewById(R.id.position_tv);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        backgroundImage = (ImageView) findViewById(R.id.background);

       // mViewPager.setPageTransformer(false, );
        mSectionsPagerAdapter = new MainFragmentPagerAdapter(getFragmentManager(), cards);
        mSectionsPagerAdapter.setSize(cards.size());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(this);
        //  size.setText(String.valueOf(mSectionsPagerAdapter.getCount()));
        int sheetColor = getResources().getColor(R.color.colorAccent);
        int fabColor = getResources().getColor(R.color.colorPrimary);

        Fab fab = (Fab) findViewById(R.id.fab);
        View sheetView = findViewById(R.id.fab_sheet);
        View overlay = findViewById(R.id.overlay);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay,
                sheetColor, fabColor);

        findViewById(R.id.fab_sheet_item_photo).setOnClickListener(this);
        findViewById(R.id.fab_sheet_item_add).setOnClickListener(this);
        findViewById(R.id.fab_sheet_item_load).setOnClickListener(this);
        findViewById(R.id.fab_sheet_item_remove).setOnClickListener(this);


        // circleButton.setOnClickListener(this);
        swipe = new Swipe();
        swipe.addListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTextIndicator();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        swipe.dispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_tabbed);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_view) {
            //startActivity(new Intent(MainActivityTabbed.this, FoldersActivity.class));
        } else if (id == R.id.nav_categories) {
            startActivity(new Intent(MainActivityTabbed.this, ScrollingActivity.class));
        } else if (id == R.id.nav_favourite) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            backgroundImage.setImageResource(R.drawable.bg_1);
        } else if (id == R.id.nav_send) {
            backgroundImage.setImageResource(R.drawable.bg_2);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_tabbed);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_shuffle) {
            mViewPager.setCurrentItem(getRandomUniquePage());
            return true;
        }
        if (id == R.id.action_favorite) {
            Card card = getCurrentCard(mViewPager.getCurrentItem());
            return true;
        }

        if (id == R.id.action_edit) {
            if (!isEditing) {
                isEditing = true;
                ((PlaceholderFragment) mSectionsPagerAdapter.getCurrentFragment()).enterEditMode();
            } else {
                ((PlaceholderFragment) mSectionsPagerAdapter.getCurrentFragment()).exitEditMode();
                isEditing = false;
            }
         /*   circleButton.setVisibility(View.VISIBLE);
circleButton.setOnClickListener(this);*/
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Card getCurrentCard(int currentItem) {
        PlaceholderFragment item = (PlaceholderFragment) mSectionsPagerAdapter.getItem(currentItem);
        return item.getCard();
    }

    private int getRandomUniquePage() {
        int temp = getRandomItem();
        if (temp != randomItem) {
            randomItem = temp;
            return randomItem;
        } else {
            getRandomUniquePage();
        }
        return randomItem;
    }

    private int getRandomItem() {
        return new Random().nextInt(cards.size() + 1);
    }

    @Override
    public void onSwipingLeft(MotionEvent event) {
        Log.i(TAG, "swiping left");
    }

    @Override
    public void onSwipedLeft(MotionEvent event) {
        Log.i(TAG, "swipED left");


    }

    @Override
    public void onSwipingRight(MotionEvent event) {
        Log.i(TAG, "swiping Rght");

    }

    @Override
    public void onSwipedRight(MotionEvent event) {
        Log.i(TAG, "swipED right");
    }

    @Override
    public void onSwipingUp(MotionEvent event) {
        Log.i(TAG, "swiping up");

    }

    @Override
    public void onSwipedUp(MotionEvent event) {
        Log.i(TAG, "swipED up");
        //((PlaceholderFragment) mSectionsPagerAdapter.getCurrentFragment()).flipCardUp();
        ((PlaceholderFragment) mSectionsPagerAdapter.getCurrentFragment()).toggleFragment(PlaceholderFragment.SWIPED_UP);

    }

    @Override
    public void onSwipingDown(MotionEvent event) {
        Log.i(TAG, "swiping down");

    }

    @Override
    public void onSwipedDown(MotionEvent event) {
        Log.i(TAG, "swipED down");
        //  ((PlaceholderFragment) mSectionsPagerAdapter.getCurrentFragment()).flipCardDown();
        ((PlaceholderFragment) mSectionsPagerAdapter.getCurrentFragment()).toggleFragment(PlaceholderFragment.SWIPED_DOWN);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        updateTextIndicator();

    }

    private void updateTextIndicator() {
        positionIndicator.setText(mViewPager.getCurrentItem() + 1 + "/" + cards.size());
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fab_sheet_item_photo:
                break;
            case R.id.fab_sheet_item_add:
                cards.add(new Card("new card", ""));
                mSectionsPagerAdapter.setSize(cards.size());
                mSectionsPagerAdapter.notifyDataSetChanged();
                mViewPager.setCurrentItem(cards.size());
                break;
            case R.id.fab_sheet_item_load:
                break;
            case R.id.fab_sheet_item_remove:

                mSectionsPagerAdapter.removeCard(mViewPager.getCurrentItem());
                mViewPager.setCurrentItem(cards.size() - 1);

                break;
         /* case R.id.done_editing_btn:

            //  ((PlaceholderFragment) mSectionsPagerAdapter.getCurrentFragment()).exitEditMode();
            // circleButton.setVisibility(View.GONE);
                break;*/
        }

        materialSheetFab.hideSheet();
    }


}
