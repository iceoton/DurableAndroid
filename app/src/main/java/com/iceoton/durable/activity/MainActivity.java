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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.iceoton.durable.R;
import com.iceoton.durable.fragment.AssetFragment;
import com.iceoton.durable.fragment.BaseFragment;
import com.iceoton.durable.fragment.HomeFragment;
import com.iceoton.durable.fragment.ReportFragment;
import com.iceoton.durable.util.AppPreference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    List<Fragment> fragmentList = new ArrayList<>();
    private NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        setupView();

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment.newInstance());
            navigationView.setCheckedItem(R.id.nav_home);
            setTitle(R.string.title_home);
        }

    }

    private void setupView() {
        // setup user info
        AppPreference appPreference = new AppPreference(MainActivity.this);
        TextView txtName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtName);
        txtName.setText(appPreference.getUserName());
        TextView txtEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtEmail);
        txtEmail.setText(appPreference.getUserEmail());
        //http://139.59.255.225/durable/resource/users/thumbs/c073c720e7e8a53eaf34fc2f203e09c2.jpg
        SimpleDraweeView draweeViewPhoto = (SimpleDraweeView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        String userUrl = "http://139.59.255.225/durable/resource/users/thumbs/" + appPreference.getUserPhoto();
        draweeViewPhoto.setImageURI(userUrl);

    }

    // Get back press work only at second press and notify user to press again to exit.
    private static long back_pressed;

    @Override
    public void onBackPressed() {
        boolean handled = false;
        for(Fragment f : fragmentList) {
            if(f instanceof BaseFragment) {
                handled = ((BaseFragment)f).onBackPressed();
                if(handled) {
                    break;
                }
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            if(!handled) {
                super.onBackPressed();
            }
        } else if(!handled) {
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
        setTitle(item.getTitle());
        if (id == R.id.nav_home) {
            replaceFragment(HomeFragment.newInstance());
        } else if (id == R.id.nav_asset) {
            replaceFragment(AssetFragment.newInstance());
        } else if (id == R.id.nav_report) {
            replaceFragment(ReportFragment.newInstance());
        } /*else if (id == R.id.nav_setting) {

        } */ else if (id == R.id.nav_logout) {
            logout();
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentContainer, fragment)
                .commit();
        fragmentList.clear();
        fragmentList.add(fragment);
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

    public void setBackArrowIndicator() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

    }

    public void setDefaultToolbarHamberger() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            if(actionBarDrawerToggle != null) {
                actionBarDrawerToggle.syncState();
            }
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.openDrawer(GravityCompat.START);
                }
            });
        }

    }

}
