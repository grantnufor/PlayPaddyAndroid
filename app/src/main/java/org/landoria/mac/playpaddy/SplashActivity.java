package org.landoria.mac.playpaddy;

import DBLayer.SessionDB;
import HttpAdapter.UserHttpServiceAdapter;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONObject;

import javax.xml.parsers.SAXParser;

public class SplashActivity extends AppCompatActivity {


    UserHttpServiceAdapter userHttpServiceAdapter = new UserHttpServiceAdapter();

    SessionDB sessionDb;

    String userName = "";
    String userId = "";


    JSONObject jsonUserObj = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);





        //Getting local sessionicon db for login and log out. If user is logged in then proceed to home
        sessionDb = new SessionDB(this);

        if(sessionDb.getAllSessions().size() > 0){

            if(sessionDb.getSessionBySessionId("1").size() > 0){


                String userServerId = sessionDb.getSessionBySessionId("1").get("USER_SERVER_ID");
                String status = sessionDb.getSessionBySessionId("1").get("STATUS");
                userName = sessionDb.getSessionBySessionId("1").get("USER_NAME");

                if(status.equals("logged in")) {


                    loadUserInfo();

                    if(jsonUserObj.isNull("UserId")) {

                        try {

                            userId = jsonUserObj.getString("UserId");

                            Intent intent = new Intent(SplashActivity.this, UserHomeMainActivity.class);
                            intent.putExtra("userid", userId);
                            intent.putExtra("username", userName);
                            startActivity(intent);
                            finish();
                            return;

                        }catch(Exception ex){


                            // Use the Builder class for convenient dialog construction
                            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                            builder.setTitle("PlayPaddy");
                            builder.setMessage("An error has occurred. Please try again.");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // You don't have to do anything here if you just
                                    // want it dismissed when clicked
                                }
                            });
                            builder.show();


                        }


                    }
                    else{

                        // Use the Builder class for convenient dialog construction
                        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                        builder.setTitle("PlayPaddy");
                        builder.setMessage("An error has occurred. Please try again.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // You don't have to do anything here if you just
                                // want it dismissed when clicked
                            }
                        });
                        builder.show();


                    }
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




    private  void loadUserInfo(){


        try {

            //setting up the error dialog
            final AlertDialog.Builder alert = new AlertDialog.Builder( SplashActivity.this, R.style.AppTheme);
            alert.setTitle("Error!!!");

            //running the time consuming tasks in a seperate thread
            AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
                ProgressDialog dialog = new ProgressDialog(SplashActivity.this);


                int errorCode = 0;
                String errorMessage = "";


                @Override
                protected void onPreExecute() {
                    // what to do before background task
                    dialog.setTitle("Loading...");
                    dialog.setMessage("Please wait.");
                    dialog.setIndeterminate(true);
                    dialog.setCancelable(false);
                    dialog.show();
                }

                @Override
                protected Void doInBackground(Void... params) {


                    try {


                        jsonUserObj = userHttpServiceAdapter.GetUserByUserName(userName);


                    } catch (Exception ex) {
                        //Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                        errorMessage = ex.getMessage();
                    }

                    return null;


                }

                @Override
                protected void onPostExecute(Void result) {
                    // what to do when background task is completed
                    dialog.dismiss();
                    if (errorCode == 1) {
                        alert.setMessage("Error has occurred");
                        alert.show();
                    } else {


                    }


                }

                ;

                @Override
                protected void onCancelled() {
                    dialog.dismiss();
                    super.onCancelled();
                }
            };
            updateTask.execute((Void[]) null);


        } catch (Exception ex) {
            Toast.makeText(SplashActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            //ex.printStackTrace();
        }
    }

}
