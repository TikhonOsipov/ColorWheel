package com.tixon.colorwheel;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.tixon.colorwheel.fragments.FragmentAltComplementarySelector;
import com.tixon.colorwheel.fragments.FragmentAnalogSelector;
import com.tixon.colorwheel.fragments.FragmentComplementarySelector;
import com.tixon.colorwheel.fragments.FragmentContrastSelector;
import com.tixon.colorwheel.fragments.FragmentDoubleContrastSelector;
import com.tixon.colorwheel.fragments.FragmentQuaternionSelector;
import com.tixon.colorwheel.fragments.FragmentTriangleSelector;
import com.tixon.colorwheel.navigationdrawer.DrawerAdapter;
import com.tixon.colorwheel.navigationdrawer.OnDrawerItemClickListener;

public class ActivityMain extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    DrawerAdapter drawerAdapter;
    RecyclerView drawerRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerRecyclerView = (RecyclerView) findViewById(R.id.drawer_recyclerView);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        drawerAdapter = new DrawerAdapter(getResources().getStringArray(R.array.views_array));
        drawerRecyclerView.setAdapter(drawerAdapter);
        drawerRecyclerView.setHasFixedSize(true);
        drawerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        drawerAdapter.setOnDrawerItemClickListener(new OnDrawerItemClickListener() {
            @Override
            public void onDrawerItemClick(int position) {
                selectView(position);
                Log.d("myLogs", "position clicked = " + position);
            }
        });

        if(savedInstanceState == null) {
            selectView(1);
        }
    }

    public void selectView(int position) {
        Fragment fragment = null;

        switch(position) {
            case 1:
                fragment = FragmentTriangleSelector.newInstance();
                break;
            case 2:
                fragment = FragmentComplementarySelector.newInstance();
                break;
            case 3:
                fragment = FragmentAltComplementarySelector.newInstance();
                break;
            case 4:
                fragment = FragmentContrastSelector.newInstance();
                break;
            case 5:
                fragment = FragmentDoubleContrastSelector.newInstance();
                break;
            case 6:
                fragment = FragmentQuaternionSelector.newInstance();
                break;
            case 7:
                fragment = FragmentAnalogSelector.newInstance();
                break;
            default: break;
        }

        if(fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit();
            drawerLayout.closeDrawers();
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
