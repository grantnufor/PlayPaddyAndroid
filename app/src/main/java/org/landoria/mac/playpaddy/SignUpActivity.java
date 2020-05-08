package org.landoria.mac.playpaddy;

import DBLayer.SessionDB;
import HttpAdapter.UserHttpServiceAdapter;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {


    UserHttpServiceAdapter userHttpServiceAdapter = new UserHttpServiceAdapter();


    EditText editTextSignUpUserName;
    EditText editTextSignUpPassword;

    Button buttonSignUpSignUp;
    Button buttonSignUpBackToHome;

    SessionDB sessionDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


       // showBackButton();


//        //showing activity name and app icon on action bar
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.drawable.logomainwhite);
////        getSupportActionBar().setTitle(sessionDb.getSessionBySessionId("1").get("NAME").toString());
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//
//
////        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#800000")));
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006400")));


        sessionDb = new SessionDB(this);

        editTextSignUpUserName = (EditText) findViewById(R.id.editTextSignUpUserName);
        editTextSignUpPassword = (EditText) findViewById(R.id.editTextSignUpPassword);

        buttonSignUpSignUp = (Button) findViewById(R.id.buttonSignUpSignUp);
        buttonSignUpSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        buttonSignUpBackToHome = (Button) findViewById(R.id.buttonSignUpBackToHome);
        buttonSignUpBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


        //showBackButton();

    }


    String userName = "";
    String password = "";

    String returnVal = "";

    String pattern = "MM/dd/yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("fr", "FR"));
    String date = simpleDateFormat.format(new Date());

    JSONObject jsonUserObj = new JSONObject();

    public void signUp() {


        //user name
        if (!editTextSignUpUserName.getText().equals("")) {

            userName = editTextSignUpUserName.getText().toString();

        } else {

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
            builder.setTitle("PlayPaddy");
            builder.setMessage("You need to enter your UserName.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // You don't have to do anything here if you just
                    // want it dismissed when clicked
                }
            });
            builder.show();

        }


        //password
        if (!editTextSignUpPassword.getText().equals("")) {

            password = editTextSignUpPassword.getText().toString();
        } else {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
            builder.setTitle("PlayPaddy");
            builder.setMessage("You need to enter your Password.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // You don't have to do anything here if you just
                    // want it dismissed when clicked
                }
            });
            builder.show();
        }


        try {


//saving the data

//setting up the error dialog
            final AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this, R.style.AppTheme);
            alert.setTitle("Error!!!");

            //running the time consuming tasks in a seperate thread
            AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
                ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);


                int errorCode = 0;
                String errorMessage = "";


                @Override
                protected void onPreExecute() {
                    // what to do before background task
                    dialog.setTitle("Signing Up...");
                    dialog.setMessage("Please wait.");
                    dialog.setIndeterminate(true);
                    dialog.setCancelable(false);
                    dialog.show();
                }

                @Override
                protected Void doInBackground(Void... params) {


                    try {


                        //checking if this username already exists
                        jsonUserObj = userHttpServiceAdapter.GetUserByUserName(userName);


                        if (jsonUserObj.isNull("UserName")) {

                            //if the username does not exist, then register this user
                            returnVal = userHttpServiceAdapter.RegisterUser(userName, "", "", password, date, date, "");

                        } else {
                            returnVal = "2";//the user already exists.  represents existence of the user
                        }


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

                        if (returnVal.equals("1")) {//if the registration was successful


                            try {

                                String userServerId = jsonUserObj.getString("UserId");
                                String userName = jsonUserObj.getString("UserName");
                                String password = jsonUserObj.getString("Password");

                                if (sessionDb.getAllSessions().size() > 0) {//if a record is saved already

                                    HashMap<String, String> sessionMap = new HashMap<>();
                                    sessionMap.put("SESSION_ID", "1");
                                    sessionMap.put("USER_NAME", userName);
                                    sessionMap.put("PASSWORD", password);
                                    sessionMap.put("STATUS", "logged in");
                                    sessionMap.put("USER_SERVER_ID", userServerId);
                                    sessionDb.updateSession(sessionMap);


                                } else {

                                    HashMap<String, String> sessionMap = new HashMap<>();
                                    sessionMap.put("SESSION_ID", "1");
                                    sessionMap.put("USER_NAME", userName);
                                    sessionMap.put("PASSWORD", password);
                                    sessionMap.put("STATUS", "logged in");
                                    sessionMap.put("USER_SERVER_ID", userServerId);
                                    sessionDb.insertSession(sessionMap);


                                }


                                Intent intent = new Intent(SignUpActivity.this, UserHomeMainActivity.class);
                                intent.putExtra("userid", userServerId);
                                intent.putExtra("username", userName);
                                startActivity(intent);
                                finishAffinity();
                            } catch (Exception ex) {

                                // Use the Builder class for convenient dialog construction
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                builder.setTitle("PlayPaddy");
                                builder.setMessage("An error has occurred. " + errorMessage);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // You don't have to do anything here if you just
                                        // want it dismissed when clicked
                                    }
                                });
                                builder.show();
                                return;
                            }

                        } else if (returnVal.equals("0")) {//if the registration was not successful

                            // Use the Builder class for convenient dialog construction
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                            builder.setTitle("PlayPaddy");
                            builder.setMessage("Registration was not successful. " + errorMessage);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // You don't have to do anything here if you just
                                    // want it dismissed when clicked
                                }
                            });
                            builder.show();
                            return;

                        } else if (returnVal.equals("2")) {

                            // Use the Builder class for convenient dialog construction
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                            builder.setTitle("PlayPaddy");
                            builder.setMessage("A user with this username aleady exists. " + errorMessage);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // You don't have to do anything here if you just
                                    // want it dismissed when clicked
                                }
                            });
                            builder.show();
                            return;
                        }


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
            Toast.makeText(SignUpActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            //ex.printStackTrace();
        }


    }


//    public void showBackButton() {
//
//        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem menuItem) {
//        if (menuItem.getItemId() == android.R.id.home) {
//            finish();
//        }
//        return super.onOptionsItemSelected(menuItem);
//    }
//
//
//    public boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }

}