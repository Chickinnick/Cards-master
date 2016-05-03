package com.nlt.mobileteam.cards.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.nlt.mobileteam.cards.AnalyticsApplication;
import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.adapter.BasicListAdapter;
import com.nlt.mobileteam.cards.model.ToDoItem;
import com.nlt.mobileteam.cards.widget.CustomRecyclerScrollViewListener;
import com.nlt.mobileteam.cards.widget.ItemTouchHelperClass;
import com.nlt.mobileteam.cards.widget.RecyclerViewEmptySupport;
import com.nlt.mobileteam.cards.widget.StoreRetrieveData;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class FoldersActivity extends AppCompatActivity {
    private RecyclerViewEmptySupport mRecyclerView;
    private FloatingActionButton mAddToDoItemFAB;
    private ArrayList<ToDoItem> mToDoItemsArrayList;
    private CoordinatorLayout mCoordLayout;
    public static final String TODOITEM = "com.avjindersinghsekhon.com.avjindersinghsekhon.minimaltodo.MainActivity";
    private BasicListAdapter adapter;
    private static final int REQUEST_ID_TODO_ITEM = 100;
    private ToDoItem mJustDeletedToDoItem;
    private int mIndexOfDeletedToDoItem;
    public static final String DATE_TIME_FORMAT_12_HOUR = "MMM d, yyyy  h:mm a";
    public static final String DATE_TIME_FORMAT_24_HOUR = "MMM d, yyyy  k:mm";
    public static final String FILENAME = "todoitems.json";
    private StoreRetrieveData storeRetrieveData;
    public ItemTouchHelper itemTouchHelper;
    private CustomRecyclerScrollViewListener customRecyclerScrollViewListener;
    public static final String SHARED_PREF_DATA_SET_CHANGED = "com.avjindersekhon.datasetchanged";
    public static final String CHANGE_OCCURED = "com.avjinder.changeoccured";
    private int mTheme = -1;
    private String theme = "name_of_the_theme";
    public static final String THEME_PREFERENCES = "com.avjindersekhon.themepref";
    public static final String RECREATE_ACTIVITY = "com.avjindersekhon.recreateactivity";
    public static final String THEME_SAVED = "com.avjindersekhon.savedtheme";
    public static final String DARKTHEME = "com.avjindersekon.darktheme";
    public static final String LIGHTTHEME = "com.avjindersekon.lighttheme";
    private AnalyticsApplication app;
    private String[] testStrings = {"Clean my room",
            "Water the plants",
            "Get car washed",
            "Get my dry cleaning"
    };


    public static ArrayList<ToDoItem> getLocallyStoredData(StoreRetrieveData storeRetrieveData) {
        ArrayList<ToDoItem> items = null;

        try {
            items = storeRetrieveData.loadFromFile();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        if (items == null) {
            items = new ArrayList<>();
        }
        return items;

    }

    @Override
    protected void onResume() {
        super.onResume();
//        app.send(this);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
      /*  if(sharedPreferences.getBoolean(ReminderActivity.EXIT, false)){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(ReminderActivity.EXIT,false);
            editor.apply();
            finish();
        }*/
        /*
        We need to do this, as this activity's onCreate won't be called when coming back from SettingsActivity,
        thus our changes to dark/light mode won't take place, as the setContentView() is not called again.
        So, inside our SettingsFragment, whenever the checkbox's value is changed, in our shared preferences,
        we mark our recreate_activity key as true.

        Note: the recreate_key's value is changed to false before calling recreate(), or we woudl have ended up in an infinite loop,
        as onResume() will be called on recreation, which will again call recreate() and so on....
        and get an ANR

         */
   /*     if(getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE).getBoolean(RECREATE_ACTIVITY, false)){
            SharedPreferences.Editor editor = getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE).edit();
            editor.putBoolean(RECREATE_ACTIVITY, false);
            editor.apply();
            recreate();
        }*/


    }

    @Override
    protected void onStart() {
//        app = (AnalyticsApplication)getApplication();
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(CHANGE_OCCURED, false)) {

            mToDoItemsArrayList = getLocallyStoredData(storeRetrieveData);
            adapter = new BasicListAdapter(this, mToDoItemsArrayList);
            mRecyclerView.setAdapter(adapter);
            setAlarms();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(CHANGE_OCCURED, false);
//            editor.commit();
            editor.apply();


        }
    }

    private void setAlarms() {
       /* if(mToDoItemsArrayList!=null){
            for(ToDoItem item : mToDoItemsArrayList){
                if(item.hasReminder() && item.getToDoDate()!=null){
                    if(item.getToDoDate().before(new Date())){
                        item.setToDoDate(null);
                        continue;
                    }
                    Intent i = new Intent(this, TodoNotificationService.class);
                    i.putExtra(TodoNotificationService.TODOUUID, item.getIdentifier());
                    i.putExtra(TodoNotificationService.TODOTEXT, item.getToDoText());
                    createAlarm(i, item.getIdentifier().hashCode(), item.getToDoDate().getTime());
                }
            }
        }*/
    }

    protected void onCreate(Bundle savedInstanceState) {
        //app = (AnalyticsApplication)getApplication();
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/Aller_Regular.tff").setFontAttrId(R.attr.fontPath).build());

        //We recover the theme we've set and setTheme accordingly
    /*    theme = getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE).getString(THEME_SAVED, LIGHTTHEME);

        if(theme.equals(LIGHTTHEME)){
            mTheme = R.style.CustomStyle_LightTheme;
        }
        else{
            mTheme = R.style.CustomStyle_DarkTheme;
        }
        this.setTheme(mTheme);*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folders);


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CHANGE_OCCURED, false);
        editor.apply();

        storeRetrieveData = new StoreRetrieveData(this, FILENAME);
        mToDoItemsArrayList = getLocallyStoredData(storeRetrieveData);
        adapter = new BasicListAdapter(this,mToDoItemsArrayList);
        setAlarms();


//        adapter.notifyDataSetChanged();
//        storeRetrieveData = new StoreRetrieveData(this, FILENAME);
//
//        try {
//            mToDoItemsArrayList = storeRetrieveData.loadFromFile();
////            Log.d("OskarSchindler", "Arraylist Length: "+mToDoItemsArrayList.size());
//        } catch (IOException | JSONException e) {
////            Log.d("OskarSchindler", "IOException received");
//            e.printStackTrace();
//        }
//
//        if(mToDoItemsArrayList==null){
//            mToDoItemsArrayList = new ArrayList<>();
//        }
//

//        mToDoItemsArrayList = new ArrayList<>();
//        makeUpItems(mToDoItemsArrayList, testStrings.length);

        final android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);


        mCoordLayout = (CoordinatorLayout) findViewById(R.id.myCoordinatorLayout);
        mAddToDoItemFAB = (FloatingActionButton) findViewById(R.id.addToDoItemFAB);

        mAddToDoItemFAB.setOnClickListener(new View.OnClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                // app.send(this, "Action", "FAB pressed");
                Intent newTodo = null;
                //ToDoItem item = new ToDoItem("", false, null);
               int color = ColorGenerator.MATERIAL.getRandomColor();
//
//                noinspection ResourceType
//                String color = getResources().getString(R.color.primary_ligher);
//                 newTodo.putExtra(TODOITEM, item);
//                View decorView = getWindow().getDecorView();
//                View navView= decorView.findViewById(android.R.id.navigationBarBackground);
//                View statusView = decorView.findViewById(android.R.id.statusBarBackground);
//                Pair<View, String> navBar ;
//                if(navView!=null){
//                    navBar = Pair.create(navView, navView.getTransitionName());
//                }
//                else{
//                    navBar = null;
//                }
//                Pair<View, String> statusBar= Pair.create(statusView, statusView.getTransitionName());
//                ActivityOptions options;
//                if(navBar!=null){
//                    options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, navBar, statusBar);
//                }
//                else{
//                    options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, statusBar);
//                }
//
//                startActivity(new Intent(MainActivity.this, TestLayout.class), options.toBundle());
//                startActivityForResult(newTodo, REQUEST_ID_TODO_ITEM, options.toBundle());
//
//                startActivityForResult(newTodo, REQUEST_ID_TODO_ITEM);


                ToDoItem item = new ToDoItem("asdad", false, null);
                item.setTodoColor(color);
                if (item.getToDoText().length() <= 0) {
                    return;
                }
                boolean existed = false;

        /*    if(item.hasReminder() && item.getToDoDate()!=null){
                Intent i = new Intent(this, TodoNotificationService.class);
                i.putExtra(TodoNotificationService.TODOTEXT, item.getToDoText());
                i.putExtra(TodoNotificationService.TODOUUID, item.getIdentifier());
                createAlarm(i, item.getIdentifier().hashCode(), item.getToDoDate().getTime());
//                Log.d("OskarSchindler", "Alarm Created: "+item.getToDoText()+" at "+item.getToDoDate());
            }
*/
                for (int i = 0; i < mToDoItemsArrayList.size(); i++) {
                    if (item.getIdentifier().equals(mToDoItemsArrayList.get(i).getIdentifier())) {
                        mToDoItemsArrayList.set(i, item);
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


//        mRecyclerView = (RecyclerView)findViewById(R.id.toDoRecyclerView);
        mRecyclerView = (RecyclerViewEmptySupport) findViewById(R.id.toDoRecyclerView);
        if (theme.equals(LIGHTTHEME)) {
            mRecyclerView.setBackgroundColor(getResources().getColor(R.color.primary_lightest));
        }
        mRecyclerView.setEmptyView(findViewById(R.id.toDoEmptyView));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        customRecyclerScrollViewListener = new CustomRecyclerScrollViewListener() {
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
        };
        mRecyclerView.addOnScrollListener(customRecyclerScrollViewListener);


        ItemTouchHelper.Callback callback = new ItemTouchHelperClass(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);


        mRecyclerView.setAdapter(adapter);
//        setUpTransitions();


    }

    public void addThemeToSharedPreferences(String theme) {
        SharedPreferences sharedPreferences = getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(THEME_SAVED, theme);
        editor.apply();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED && requestCode == REQUEST_ID_TODO_ITEM) {


        }
    }

    private AlarmManager getAlarmManager() {
        return (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    private boolean doesPendingIntentExist(Intent i, int requestCode) {
        PendingIntent pi = PendingIntent.getService(this, requestCode, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }

    private void createAlarm(Intent i, int requestCode, long timeInMillis) {
        AlarmManager am = getAlarmManager();
        PendingIntent pi = PendingIntent.getService(this, requestCode, i, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, timeInMillis, pi);
//        Log.d("OskarSchindler", "createAlarm "+requestCode+" time: "+timeInMillis+" PI "+pi.toString());
    }

    private void deleteAlarm(Intent i, int requestCode) {
        if (doesPendingIntentExist(i, requestCode)) {
            PendingIntent pi = PendingIntent.getService(this, requestCode, i, PendingIntent.FLAG_NO_CREATE);
            pi.cancel();
            getAlarmManager().cancel(pi);
            Log.d("OskarSchindler", "PI Cancelled " + doesPendingIntentExist(i, requestCode));
        }
    }

    private void addToDataStore(ToDoItem item) {
        mToDoItemsArrayList.add(item);
        adapter.notifyItemInserted(mToDoItemsArrayList.size() - 1);

    }


    public void makeUpItems(ArrayList<ToDoItem> items, int len) {
        for (String testString : testStrings) {
            ToDoItem item = new ToDoItem(testString, false, new Date());
            //noinspection ResourceType
//            item.setTodoColor(getResources().getString(R.color.red_secondary));
            items.add(item);
        }

    }



    //Used when using custom fonts
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }

    private void saveDate() {
        try {
            storeRetrieveData.saveToFile(mToDoItemsArrayList);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            storeRetrieveData.saveToFile(mToDoItemsArrayList);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        mRecyclerView.removeOnScrollListener(customRecyclerScrollViewListener);
    }


//    public void setUpTransitions(){
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            Transition enterT = new Slide(Gravity.RIGHT);
//            enterT.setDuration(500);
//
//            Transition exitT = new Slide(Gravity.LEFT);
//            exitT.setDuration(300);
//
//            Fade fade = new Fade();
//            fade.setDuration(500);
//
//            getWindow().setExitTransition(fade);
//            getWindow().setReenterTransition(fade);
//
//        }
//    }

}


