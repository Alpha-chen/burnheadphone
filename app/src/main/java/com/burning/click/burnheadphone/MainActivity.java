package com.burning.click.burnheadphone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.burning.click.burnheadphone.fragment.MainFragment;
import com.burning.click.burnheadphone.fragment.SnsFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        showMainFragment();
    }

    private void showMainFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, MainFragment.newInstance("0","0")).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, MainFragment.newInstance("0","0")).commit();
            getSupportFragmentManager().beginTransaction().hide(SnsFragment.newInstance("0","0")).commit();
            toolbar.setTitle(getResources().getString(R.string.main_navi_main));
        } else if (id == R.id.nav_gallery) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, SnsFragment.newInstance("0","0")).commit();
            getSupportFragmentManager().beginTransaction().hide(MainFragment.newInstance("0","0")).commit();
            toolbar.setTitle(getResources().getString(R.string.main_navi_tip));
        } else if (id == R.id.nav_slideshow) {
            toolbar.setTitle(getResources().getString(R.string.main_navi_recommend_head_phone));

        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(MainActivity.this, TestGreenDaoActivity.class));
            toolbar.setTitle(getResources().getString(R.string.main_navi_setting));
        } else if (id == R.id.nav_share) {

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
