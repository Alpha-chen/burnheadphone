package com.burning.click.burnheadphone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.burning.click.burnheadphone.fragment.BurnFragment;
import com.burning.click.burnheadphone.fragment.MainFragment;
import com.burning.click.burnheadphone.fragment.SnsFragment;
import com.burning.click.burnheadphone.fragment.TipFragment;
import com.burning.click.burnheadphone.node.UserNode;
import com.burning.click.burnheadphone.sp.SpUtils;
import com.burning.click.burnheadphone.util.SpkeyName;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        showMainFragment();
    }

    private void showMainFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, BurnFragment.newInstance("0", "0")).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        TextView userName = (TextView) drawer.findViewById(R.id.main_navi_header_nick_name);
        userName.setText(UserNode.getmUserNode().getEmail());
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
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            UserNode.getmUserNode().setLogin_status(0);
            SpUtils.put(MainActivity.this, SpUtils.BHP_SHARF, SpkeyName.USER_NODE, UserNode.toJson(UserNode.getmUserNode()));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, MainFragment.newInstance("0", "0")).commit();
            getSupportFragmentManager().beginTransaction().hide(SnsFragment.newInstance("0", "0")).commit();
            toolbar.setTitle(getResources().getString(R.string.app_name));
        } else if (id == R.id.nav_gallery) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, TipFragment.newInstance("0", "0")).commit();
            getSupportFragmentManager().beginTransaction().hide(MainFragment.newInstance("0", "0")).commit();
            toolbar.setTitle(getResources().getString(R.string.main_navi_tip));
        } else if (id == R.id.nav_share) {

        }
//        else if (id == R.id.nav_manage) {
//            startActivity(new Intent(MainActivity.this, TestGreenDaoActivity.class));
//            toolbar.setTitle(getResources().getString(R.string.main_navi_setting));
//        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
