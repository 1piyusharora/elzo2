package com.elzo.elzo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.viewpagerindicator.CirclePageIndicator;

public class MainActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    GridView gridView;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    private String[] urls = new String[] {"https://firebasestorage.googleapis.com/v0/b/elzo-test.appspot.com/o/img%2Fa.jpg?alt=media&token=bee47fac-e7c0-41fc-b72b-3ddd029de619",
            "https://firebasestorage.googleapis.com/v0/b/elzo-test.appspot.com/o/img%2Fb.jpg?alt=media&token=c7a0b930-6a2c-4773-a1e9-08014cb51833",
            "https://firebasestorage.googleapis.com/v0/b/elzo-test.appspot.com/o/img%2Fc.jpg?alt=media&token=f14a9f1b-3d14-4398-8fd6-2781e6b816dc",
            };

    String[]list={"Packages","Pet Hostel","Book Appointment","Contact Us","About Us"};
    int []img={R.drawable.ic_heartbox,
            R.drawable.ic_pethouse,
            R.drawable.ic_timer,
            R.drawable.ic_contact,
            R.drawable.ic_aboutus
    };

    Button wellnessPackage,groomingPackage;
    CardView layoutBottomSheet;
    CoordinatorLayout coordinatorLayout;
    BottomSheetBehavior sheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Elzo Pet Care");

        init();

        gridView=findViewById(R.id.gridView);
        gridView.setAdapter(new GridViewAdapter(this, list, img));

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        View listHeaderView=getLayoutInflater().inflate(R.layout.nav_header,null,false);
        expListView.addHeaderView(listHeaderView);

        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);


        // preparing list data
        prepareListData();

        setupDrawer();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

        layoutBottomSheet=(CardView) findViewById(R.id.bottom_sheet);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinator) ;
        coordinatorLayout.setVisibility(View.GONE);
        wellnessPackage=(Button)layoutBottomSheet.findViewById(R.id.wellnessPackage);
        groomingPackage=(Button)layoutBottomSheet.findViewById(R.id.groomingPackage);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        if (coordinatorLayout.getVisibility()==View.GONE) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            coordinatorLayout.setVisibility(View.VISIBLE);
                        } else {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            coordinatorLayout.setVisibility(View.GONE);
                        }


                    default:


                }

            }
        });

        /**
         * bottom sheet state change listener
         * we are changing button text when sheet changed state
         * */
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:{

                    }

                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        coordinatorLayout.setVisibility(View.GONE);
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        wellnessPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coordinatorLayout.setVisibility(View.GONE);
                startActivity(new Intent(MainActivity.this, wellnessPackages.class));
            }
        });

        groomingPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coordinatorLayout.setVisibility(View.GONE);
                startActivity(new Intent(MainActivity.this, groomingPackages.class));
            }
        });


    }

    private void init() {

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(MainActivity.this,urls));

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = urls.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawer() {
        mDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle("Elzo Pet Care");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(mDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }



    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Packages");
        listDataHeader.add("Pet Hostel");
        listDataHeader.add("Book Appointment/Trainer");
        listDataHeader.add("Contact Us");
        listDataHeader.add("About Us");

        // Adding child data
        List<String> packages = new ArrayList<String>();
        packages.add("Wellness Package");
        packages.add("Grooming Package");


        List<String> petHostel = new ArrayList<String>();

        List<String> appointmenttrainer = new ArrayList<String>();

        List<String> contactUs = new ArrayList<String>();

        List<String> aboutUs = new ArrayList<String>();


        listDataChild.put(listDataHeader.get(0), packages); // Header, Child data
        listDataChild.put(listDataHeader.get(1), petHostel);
        listDataChild.put(listDataHeader.get(2), appointmenttrainer);
        listDataChild.put(listDataHeader.get(3), contactUs);
        listDataChild.put(listDataHeader.get(4), aboutUs);

    }

}
