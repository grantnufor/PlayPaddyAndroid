package org.landoria.mac.playpaddy;

import DBLayer.GameOptionDB;
import DBLayer.GameQuestionDB;
import DBLayer.GameUserAnswerDB;
import HttpAdapter.GameHttpServiceAdapter;
import HttpAdapter.GameOptionHttpServiceAdapter;
import HttpAdapter.GameQuestionHttpServiceAdapter;
import HttpAdapter.GameUserAnswerHttpServiceAdapter;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;

public class GamePanelActivity extends AppCompatActivity {


    GameHttpServiceAdapter gameHttpServiceAdapter = new GameHttpServiceAdapter();
    GameQuestionHttpServiceAdapter gameQuestionHttpServiceAdapter  = new GameQuestionHttpServiceAdapter();
    GameOptionHttpServiceAdapter gameOptionHttpServiceAdapter = new GameOptionHttpServiceAdapter();
    GameUserAnswerHttpServiceAdapter gameUserAnswerHttpServiceAdapter = new GameUserAnswerHttpServiceAdapter();

    //local db objects
    GameQuestionDB gameQuestionDB = new GameQuestionDB(this);
    GameOptionDB gameOptionDB = new GameOptionDB(this);
    GameUserAnswerDB gameUserAnswerDB = new GameUserAnswerDB(this);


    String userId = "";
    String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_panel);


        Intent intent = getIntent();
        userId = intent.getStringExtra("userid");
        userName = intent.getStringExtra("username");

        showBackButton();


        //showing activity name and app icon on action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logomainwhite);
//        getSupportActionBar().setTitle(sessionDb.getSessionBySessionId("1").get("NAME").toString());
        getSupportActionBar().setDisplayUseLogoEnabled(true);


//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#800000")));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006400")));

    }


    private void pullDownGame(){

    }


    public void showBackButton() {

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
