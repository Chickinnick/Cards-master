package com.nlt.mobileteam.cards.activity;

import android.Manifest;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pwittchen.swipe.library.Swipe;
import com.github.pwittchen.swipe.library.SwipeListener;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.Util;
import com.nlt.mobileteam.cards.adapter.MainFragmentPagerAdapter;
import com.nlt.mobileteam.cards.controller.BroadcastManager;
import com.nlt.mobileteam.cards.controller.StorageController;
import com.nlt.mobileteam.cards.fragment.PlaceholderFragment;
import com.nlt.mobileteam.cards.model.Action;
import com.nlt.mobileteam.cards.model.Card;
import com.nlt.mobileteam.cards.model.Folder;
import com.nlt.mobileteam.cards.widget.CardsViewPager;
import com.nlt.mobileteam.cards.widget.Fab;
import com.orhanobut.hawk.Hawk;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class MainActivityTabbed extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeListener,
        ViewPager.OnPageChangeListener, View.OnClickListener,
        PlaceholderFragment.OnFragmentClickListener {


    private static final int TYPE_FAVOURITE = 1;
    private static final int TYPE_FOLDERS = 2;

    public void setIsDragMode(boolean isDragMode) {
       this.isDragMode = isDragMode;
        ((CardsViewPager) mViewPager).setSwipeable(!isDragMode);
    }

    private boolean isDragMode = false;
    private ArrayList<Folder> foldersFromStorage;
    public static boolean isShuffleMode;
    private boolean isFirstSwipe;
    private Fab viewFolderFab;
    private OnCardChangedListener onCardChangedListener;

    public void showEditControls() {
        ((PlaceholderFragment) mSectionsPagerAdapter.getCurrentFragment()).showControls();

    }

    public interface OnCardChangedListener {
        void onCardChanged(Card card);
    }

    @Override
    public void onFragmentClick() {
        idleCard();
    }

    @Override
    public void doubleClick() {
        onAddTextClicked();
        ((PlaceholderFragment) mSectionsPagerAdapter.getCurrentFragment()).editText();
    }


    public Card getCurrentCard() {
        return cards.get(mViewPager.getCurrentItem());
    }

    public class StorageActionReciever extends BroadcastReceiver {

        public StorageActionReciever() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Action.SAVE_STATE.name())) {
                Card changedCard = (Card) intent.getParcelableExtra(BroadcastManager.EXTRA_DATA);
                Log.d("changed", "recieve: " + changedCard.toString());
                Fragment currentFragment = mSectionsPagerAdapter.getCurrentFragment();
                if (currentFragment != null) {
                    ((PlaceholderFragment) currentFragment).updateCard(changedCard);
                }

                cards.set(mViewPager.getCurrentItem(), changedCard);
                currentFolder.setCards(cards);
               /* mSectionsPagerAdapter.notifyDataSetChanged();*/
               foldersFromStorage = StorageController.getInstance().getFolderFromStorage();
                for (int i = 0; i < foldersFromStorage.size(); i++) {

                    Folder folder = foldersFromStorage.get(i);

                    if (currentFolder.getName().equals(folder.getName())) {
                        foldersFromStorage.set(i, currentFolder);
                        break;
                    }
                }
// 
                StorageController.getInstance().saveFolders(foldersFromStorage);

            }
        }
    }


    private static final int MAX_CLICK_DURATION = 200;
    private long startClickTime;

    private static final String LOG_TAG = MainActivityTabbed.class.getSimpleName();

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
    private Folder currentFolder;
    private Toolbar toolbar;
    StorageActionReciever storageActionReciever;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Nammu.init(this);
        storageActionReciever = new StorageActionReciever();

        IntentFilter intentFilter = createIntentFilter();
        this.registerReceiver(storageActionReciever, intentFilter);
        setContentView(R.layout.activity_main_activity_tabbed);
        foldersFromStorage = StorageController.getInstance().getFolderFromStorage();
        currentFolder = foldersFromStorage.get(0);
        cards = currentFolder.getCards();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(currentFolder.getName());
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
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
        //  Picasso.with(this).load(Hawk.get(Util.SELECTED_BG_EXTRA, R.drawable.bg_1)).centerInside().into(backgroundImage);
        backgroundImage.setImageResource(Hawk.get(Util.SELECTED_BG_EXTRA, R.drawable.backgr4));
        // mViewPager.setPageTransformer(false, );
        mSectionsPagerAdapter = new MainFragmentPagerAdapter(getFragmentManager(), cards, this);
        mSectionsPagerAdapter.setSize(cards.size());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(this);

                //  size.setText(String.valueOf(mSectionsPagerAdapter.getCount()));
        int sheetColor = getResources().getColor(R.color.colorAccent);
        int fabColor = getResources().getColor(R.color.colorPrimary);

        Fab fab = (Fab) findViewById(R.id.fab);
        viewFolderFab = (Fab) findViewById(R.id.fab_folder_right);
        viewFolderFab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        startClickTime = Calendar.getInstance().getTimeInMillis();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                        if (clickDuration < MAX_CLICK_DURATION) {
                            Intent intent = new Intent(MainActivityTabbed.this, ScrollingActivity.class);
                            intent.putExtra(Action.VIEW_FOLDER_INTENT_EXTRA, true);
                            intent.putExtra(Action.VIEW_FOLDER_INTENT_EXTRA_DATA, currentFolder);
                            startActivityForResult(intent, Util.PICK_FOLDER_REQUEST);
                        }
                    }
                }
                return true;
            }
        });
        View sheetView = findViewById(R.id.fab_sheet);
        View overlay = findViewById(R.id.overlay);
        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay,
                sheetColor, fabColor);

        findViewById(R.id.fab_sheet_item_photo).setOnClickListener(this);
        findViewById(R.id.fab_sheet_item_add).setOnClickListener(this);
        findViewById(R.id.fab_sheet_item_load).setOnClickListener(this);
        findViewById(R.id.fab_sheet_item_remove).setOnClickListener(this);


        // circleButton.setOnClickListener(this);
        swipe = new Swipe();
        swipe.addListener(this);


        EasyImage.configuration(this)
                .setImagesFolderName("CardImages")
                .saveInAppExternalFilesDir()
                .setCopyExistingPicturesToPublicLocation(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, StorageController.getInstance().getFolderFromStorage() + " ");

        updateTextIndicator();
    }

    @Override
    protected void onPause() {
        doneClick();
        super.onPause();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        swipe.dispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        // Clear any configuration that was done!
        EasyImage.clearConfiguration(this);
        unregisterReceiver(storageActionReciever);
        super.onDestroy();
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


    private IntentFilter createIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action.SAVE_STATE.name());

        return intentFilter;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_view) {
            updateDataset(TYPE_FOLDERS);
            //startActivity(new Intent(MainActivityTabbed.this, FoldersActivity.class));
        } else if (id == R.id.nav_categories) {
            Intent intent = new Intent(MainActivityTabbed.this, ScrollingActivity.class);
            startActivityForResult(intent, Util.PICK_FOLDER_REQUEST);
        } else if (id == R.id.nav_favourite) {
            updateDataset(TYPE_FAVOURITE);


        } else if (id == R.id.nav_backr) {
            Intent intent = new Intent(MainActivityTabbed.this, BgPickerActivity.class);
            startActivityForResult(intent, Util.PICK_BG_REQUEST);
        }
        // } else if (id == R.id.nav_share) {
//    //       backgroundImage.setImageResource(R.drawable.bg_1);
        // } else if (id == R.id.nav_send) {
//    //       backgroundImage.setImageResource(R.drawable.bg_2);
        // }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_tabbed);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    private void updateDataset(int type) {

        if (TYPE_FAVOURITE == type) {
            currentFolder = StorageController.getInstance().getFavourite(this);
        } else if (TYPE_FOLDERS == type) {

            currentFolder = (Folder) foldersFromStorage.get(0);
        }
        toolbar.setTitle(currentFolder.getName());
        mSectionsPagerAdapter.setCards(currentFolder.getCards());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        cards = currentFolder.getCards();
        updateTextIndicator();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Util.PICK_FOLDER_REQUEST:
                    currentFolder = (Folder) data.getExtras().get(Util.SELECTED_FOLDER_EXTRA);
                    int position = (int) data.getExtras().get(Util.SELECTED_CARD_POS_EXTRA);
                    toolbar.setTitle(currentFolder.getName());
                    mSectionsPagerAdapter.setCards(currentFolder.getCards());
                    mViewPager.setAdapter(mSectionsPagerAdapter);
                    cards = currentFolder.getCards();
                    mViewPager.setCurrentItem(position);
                    updateTextIndicator();
                    updateStarByPosition();
                    break;

                case Util.PICK_BG_REQUEST:

                    int selectedResBgID = data.getIntExtra(Util.SELECTED_BG_EXTRA, R.drawable.bg_1);
                    backgroundImage.setImageResource(selectedResBgID);
                    Hawk.put(Util.SELECTED_BG_EXTRA, selectedResBgID);
                    break;

            }

        }
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                //Handle the image
                Log.d("picked", "on image picked" + source + " " + imageFile.getAbsolutePath());

               /* onPhotoReturned(imageFile);*/
                setIsDragMode(true);

                ((PlaceholderFragment) mSectionsPagerAdapter.getCurrentFragment()).savePhotoInModel("file://" + imageFile.getAbsolutePath());

                mSectionsPagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(MainActivityTabbed.this);
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_tabbed, menu);
        this.menu = menu;
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

            if (!isShuffleMode) {
                isShuffleMode = true;
                item.setChecked(true);
                item.setIcon(R.drawable.ic_shuffle_picked);
            } else {
                isShuffleMode = false;
                item.setChecked(false);
                item.setIcon(R.drawable.ic_shuffle);
            }

            return true;
        }
        if (id == R.id.action_favorite) {

            Card card = currentFolder.getCards().get(mViewPager.getCurrentItem());
            if (card.isFavourite()) {
                card.setFavourite(false);
                StorageController.getInstance().removeFromFavourites(card, this);

            } else {
                card.setFavourite(true);
                StorageController.getInstance().saveInFavourites(card, this);
            }
            updateStarByPosition();
            BroadcastManager.getInstance().sendBroadcastWithParcelable(Action.SAVE_STATE.name(), card);
            return true;
        }

        if (id == R.id.action_edit) {
             addCard();

//            idleCard();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void idleCard() {
        //  if (isEditing) {
        isEditing = false;
        setIsDragMode(false);

        ((PlaceholderFragment) mSectionsPagerAdapter.getCurrentFragment()).clearFocus();
            doneClick();
        //  }
    }

    private void doneClick() {
        //   if (isEditing) {
            Fragment currentFragment = mSectionsPagerAdapter.getCurrentFragment();
            if (currentFragment != null) {
                ((PlaceholderFragment) currentFragment).exitEditMode();
            }
            final InputMethodManager imm = (InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mViewPager.getWindowToken(), 0);
            isEditing = false;
        setIsDragMode(false);

        //  }
    }

    private Card getCurrentCard(int currentItemIndex) {
        return mSectionsPagerAdapter.getCard(currentItemIndex);
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
        if (isDragMode) {
            return;
        }
    }

    @Override
    public void onSwipedLeft(MotionEvent event) {
        Log.i(TAG, "swipED left");
        isFirstSwipe = false;
    }

    private void handleRandomCardMode() {
        if (isShuffleMode) {
            mViewPager.setCurrentItem(getRandomUniquePage());
        }
    }

    @Override
    public void onSwipingRight(MotionEvent event) {
        if (isDragMode) {
            return;
        }

    }

    @Override
    public void onSwipedRight(MotionEvent event) {
        Log.i(TAG, "swipED right");
        isFirstSwipe = false;

    }

    @Override
    public void onSwipingUp(MotionEvent event) {
    }

    @Override
    public void onSwipedUp(MotionEvent event) {
        if (isDragMode) {
            return;
        }
        doneClick();

        Log.i(TAG, "swipED up");
        //((PlaceholderFragment) mSectionsPagerAdapter.getCurrentFragment()).flipCardUp();
        Fragment currentFragment = mSectionsPagerAdapter.getCurrentFragment();
        if (currentFragment != null) {
            ((PlaceholderFragment) currentFragment).toggleFragment(PlaceholderFragment.SWIPED_UP);
        }
    }

    @Override
    public void onSwipingDown(MotionEvent event) {
    }

    @Override
    public void onSwipedDown(MotionEvent event) {
        if (isDragMode) {
            return;
        }
        doneClick();


        Log.i(TAG, "swipED down");
        //  ((PlaceholderFragment) mSectionsPagerAdapter.getCurrentFragment()).flipCardDown();
        Fragment currentFragment = mSectionsPagerAdapter.getCurrentFragment();
        if (currentFragment != null) {
            ((PlaceholderFragment) currentFragment).toggleFragment(PlaceholderFragment.SWIPED_DOWN);
        }

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        updateTextIndicator();
        updateStarByPosition();
        if (!isFirstSwipe && isShuffleMode) {
            isFirstSwipe = true;
            handleRandomCardMode();

        }
    }

    private void updateStarByPosition() {
        if (menu == null || cards == null || cards.isEmpty()) {
            return;
        }
        MenuItem item = menu.findItem(R.id.action_favorite);
        Card card = cards.get(mViewPager.getCurrentItem());

        if (card.isFavourite()) {
            item.setChecked(true);
            item.setIcon(R.drawable.ic_star_selected);
        } else {
            item.setChecked(false);
            item.setIcon(R.drawable.ic_star);
        }

    }

    private void updateTextIndicator() {
        positionIndicator.setText(mViewPager.getCurrentItem() + 1 + "/" + cards.size());
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // doneClick();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fab_sheet_item_photo:
                onTakePhotoClicked();
                break;
            case R.id.fab_sheet_item_add:
                addCard();
                break;
            case R.id.fab_sheet_item_load:
                onPickFromDocumentsClicked();
                break;
            case R.id.fab_sheet_item_remove:

                // mSectionsPagerAdapter.removeCard();
                cards.remove(mViewPager.getCurrentItem());
                mSectionsPagerAdapter.setCards(cards);
                mViewPager.setCurrentItem(cards.size() - 1);

                break;
         /*   case R.id.fab_folder_right:
                Intent intent = new Intent(MainActivityTabbed.this, ScrollingActivity.class);
                intent.putExtra(Action.VIEW_FOLDER_INTENT_EXTRA, true);
                intent.putExtra(Action.VIEW_FOLDER_INTENT_EXTRA_DATA, currentFolder);
                startActivityForResult(intent, Util.PICK_FOLDER_REQUEST);
                break;*/
        }
        materialSheetFab.hideSheet();
    }

    private void addCard() {
        cards.add(new Card());
        mSectionsPagerAdapter.setCards(cards);
        mViewPager.setCurrentItem(cards.size() - 1);
    }

    private void onAddTextClicked() {
        ((PlaceholderFragment) mSectionsPagerAdapter.getCurrentFragment()).addText();
        setIsDragMode(true);


    }


    public void onTakePhotoClicked() {

        /**Permission check only required if saving pictures to root of sdcard*/
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            EasyImage.openCamera(this, 0);
        } else {
            Nammu.askForPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
                @Override
                public void permissionGranted() {
                    EasyImage.openCamera(MainActivityTabbed.this, 0);
                }

                @Override
                public void permissionRefused() {

                }
            });
        }
    }

    public void onPickFromDocumentsClicked() {
        /** Some devices such as Samsungs which have their own gallery app require write permission. Testing is advised! */

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            EasyImage.openDocuments(this, 0);
        } else {
            Nammu.askForPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
                @Override
                public void permissionGranted() {
                    EasyImage.openDocuments(MainActivityTabbed.this, 0);
                }

                @Override
                public void permissionRefused() {

                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
