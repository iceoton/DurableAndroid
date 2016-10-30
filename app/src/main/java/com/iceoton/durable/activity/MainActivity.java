package com.iceoton.durable.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.iceoton.durable.R;
import com.iceoton.durable.fragment.AssetFragment;
import com.iceoton.durable.fragment.MainFragment;
import com.iceoton.durable.fragment.ReportFragment;
import com.iceoton.durable.util.AppPreference;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        setupView();

        if (savedInstanceState == null) {
            replaceFragment(MainFragment.newInstance());
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }

    private void setupView(){
        AppPreference appPreference = new AppPreference(MainActivity.this);
        TextView txtName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtName);
        txtName.setText(appPreference.getUserName());
        TextView txtEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtEmail);
        txtEmail.setText(appPreference.getUserEmail());
    }

    // Get back press work only at second press and notify user to press again to exit.
    private static long back_pressed;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else {
            if (back_pressed + 2000 > System.currentTimeMillis()) {
                super.onBackPressed(); // Exit
            } else {
                Toast.makeText(getBaseContext(), R.string.press_one_again,
                        Toast.LENGTH_SHORT).show();
            }
            back_pressed = System.currentTimeMillis();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            replaceFragment(MainFragment.newInstance());
        } else if (id == R.id.nav_asset) {
            replaceFragment(AssetFragment.newInstance());
        } else if (id == R.id.nav_report) {
            replaceFragment(ReportFragment.newInstance());
        } else if (id == R.id.nav_member) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentContainer, fragment)
                .commit();
    }

    private void logout() {
        AppPreference appPreference = new AppPreference(MainActivity.this);
        appPreference.saveUserId("");
        appPreference.saveUserName("");
        appPreference.saveUserEmail("");
        appPreference.saveLoginStatus(false);

        Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(logoutIntent);
        finish();
    }
}
