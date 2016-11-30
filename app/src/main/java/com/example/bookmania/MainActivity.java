package com.example.bookmania;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    public ActionBarDrawerToggle drawerToggle;
    UserSessionManager sessionManager;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new UserSessionManager(getApplicationContext());

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);

        nvDrawer = (NavigationView)findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        if(savedInstanceState == null){
            MenuItem item = nvDrawer.getMenu().getItem(0);
            selectDrawerItem(item);
        }
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this,mDrawer,toolbar,R.string.drawer_open,R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView nvDrawer) {
        nvDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });
    }

    private void selectDrawerItem(MenuItem item) {
        Fragment fragment = null;

        switch(item.getItemId()){
            case R.id.nav_browse_books:
                fragment = new BrowseBooks();
                setTitle("Browse Books");
                break;
            case R.id.nav_sell_books:
                if(sessionManager.checkLogin()){
                    Toast.makeText(MainActivity.this,"Please Log in!",Toast.LENGTH_LONG).show();
                    fragment = new LoginActivity();
                    setTitle("Login");
                }
                else {
                    fragment = new SellBooks();
                    setTitle("Sell Books");
                }
                break;
            case R.id.nav_login:
               fragment = new LoginActivity();
                setTitle("Login");
                break;
            case R.id.nav_register:
                fragment = new RegisterActivity();
                setTitle("Register");
                break;
            case R.id.nav_logout:
                if(!sessionManager.checkLogin()) {
                    sessionManager.logoutUser();
                    Toast.makeText(MainActivity.this,"Logged out!",Toast.LENGTH_LONG).show();
                    fragment = new BrowseBooks();
                    setTitle("Browse Books");
                }
                else {
                    Toast.makeText(MainActivity.this,"Please Log in!",Toast.LENGTH_LONG).show();
                    fragment = new LoginActivity();
                    setTitle("Login");
                }
                break;
            default:
                fragment = new BrowseBooks();
                setTitle("Browse Books");
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flContent,fragment);
        ft.commit();

        item.setChecked(true);
      //  setTitle(item.getTitle());
        mDrawer.closeDrawers();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       if(drawerToggle.onOptionsItemSelected(item))
       {
           return true;
       }

        return super.onOptionsItemSelected(item);
    }
}
