package org.landoria.mac.playpaddy;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

import java.util.Date;
import java.util.HashMap;

public class UserHomeMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

    String userId = "";
    String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        userId = intent.getStringExtra("userid");
        userName = intent.getStringExtra("username");


//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
//                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        navigationView.setBackgroundColor(getResources().getColor(R.color.common_google_signin_btn_text_light_default));



        Fragment fragment = fragment = new UserHomeFragment();

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment, fragment);
            ft.commit();
        }



        //showing activity name and app icon on action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logomainwhite);
//        getSupportActionBar().setTitle(sessionDb.getSessionBySessionId("1").get("NAME").toString());
        getSupportActionBar().setDisplayUseLogoEnabled(true);


//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#800000")));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006400")));




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_home_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_user_home) {
            //creating fragment object
            Fragment fragment = fragment = new UserHomeFragment();

            //replacing the fragment
            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, fragment);
                ft.commit();
            }

        }
        if (id == R.id.nav_my_enrolled_games) {


            Intent intent = new Intent(UserHomeMainActivity.this, UserEnroledGamesActivity.class);
            intent.putExtra("userid", userId);
            intent.putExtra("username", userName);
            startActivity(intent);


        }
        else if(id == R.id.nav_sign_out){

//            HashMap<String, String> sessionMap = new HashMap<>();
//            sessionMap.put("SESSION_ID", "1");
//            sessionMap.put("EMAIL_ADDRESS", sessionDb.getSessionBySessionId("1").get("EMAIL_ADDRESS"));
//            sessionMap.put("PASSWORD", sessionDb.getSessionBySessionId("1").get("PASSWORD"));
//            sessionMap.put("STATUS", "logged out");
//            sessionMap.put("NAME", sessionDb.getSessionBySessionId("1").get("NAME"));
//            sessionMap.put("PHONE_NUMBER", sessionDb.getSessionBySessionId("1").get("PHONE_NUMBER"));
//            sessionMap.put("CATEGORY", sessionDb.getSessionBySessionId("1").get("CATEGORY"));
//            sessionMap.put("LAST_MODIFIED", new Date().toString());
//            sessionMap.put("USER_SERVER_ID", sessionDb.getSessionBySessionId("1").get("USER_SERVER_ID") );
//            sessionDb.updateSession(sessionMap);

            Intent intent = new Intent(UserHomeMainActivity.this, SignInActivity.class);
            intent.putExtra("LoggedOut", "You have logged out");
            startActivity(intent);
            finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
