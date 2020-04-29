package org.landoria.mac.playpaddy;

import DBLayer.SessionDB;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {



    SessionDB sessionDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Getting local sessionicon db for login and log out. If user is logged in then proceed to home
        sessionDb = new SessionDB(this);

        if(sessionDb.getAllSessions().size() > 0){

            if(sessionDb.getSessionBySessionId("1").size() > 0){

                String status = sessionDb.getSessionBySessionId("1").get("STATUS");
                String userName = sessionDb.getSessionBySessionId("1").get("USER_NAME");

                if(status.equals("logged in")) {


                    Intent intent = new Intent(SplashActivity.this, UserHomeMainActivity.class);
                    intent.putExtra("username", userName);
                    startActivity(intent);
                    finish();

                    return;

                }

            }
        }



        if (!isTaskRoot()) {
            finish();
            return;
        }

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(4000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{



                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);

                    finish();


                }
            }
        };
        timerThread.start();
    }
}
