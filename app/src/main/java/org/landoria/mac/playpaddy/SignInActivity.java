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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {



    UserHttpServiceAdapter userHttpServiceAdapter = new UserHttpServiceAdapter();


    EditText editTextSignInUserName;
    EditText editTextSignInPassword;


    Button buttonSignInSignIn;
    Button buttonSignInBackToHome;


    SessionDB sessionDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        sessionDb = new SessionDB(this);


        editTextSignInUserName = findViewById(R.id.editTextSignInUserName);
        editTextSignInPassword = findViewById(R.id.editTextSignInPassword);


        buttonSignInSignIn = findViewById(R.id.buttonSignInSignIn);
        buttonSignInSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        buttonSignInBackToHome = findViewById(R.id.buttonSignInBackToHome);
        buttonSignInBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                intent.putExtra("username", userName);
                startActivity(intent);
                finish();
                return;
            }
        });


    }



    String userName = "";
    String password = "";

    String returnVal = "";


    JSONObject jsonUserObj;

    public void signIn() {



        //user name
        if (!editTextSignInUserName.getText().equals("")) {

            userName = editTextSignInUserName.getText().toString();

        } else {

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
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
        if(!editTextSignInPassword.getText().equals("")){

            password = editTextSignInPassword.getText().toString();
        }
        else{
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
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
            final AlertDialog.Builder alert = new AlertDialog.Builder(SignInActivity.this, R.style.AppTheme);
            alert.setTitle("Error!!!");

            //running the time consuming tasks in a seperate thread
            AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
                ProgressDialog dialog = new ProgressDialog(SignInActivity.this);


                int errorCode = 0;
                String errorMessage = "";


                @Override
                protected void onPreExecute() {
                    // what to do before background task
                    dialog.setTitle("Signing in...");
                    dialog.setMessage("Please wait.");
                    dialog.setIndeterminate(true);
                    dialog.setCancelable(false);
                    dialog.show();
                }

                @Override
                protected Void doInBackground(Void... params) {


                    try {


                        //checking if this username already exists
                        jsonUserObj = userHttpServiceAdapter.GetUserByUserNameAndPassword(userName, password);



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



                        if(!jsonUserObj.isNull("UserName")){//user exists


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

                                Intent intent = new Intent(SignInActivity.this, UserHomeMainActivity.class);
                                intent.putExtra("userid", userServerId);
                                intent.putExtra("username", userName);
                                startActivity(intent);
                                finish();
                                return;

                            }
                            catch(Exception ex) {

                                // Use the Builder class for convenient dialog construction
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                                builder.setTitle("PlayPaddy");
                                builder.setMessage("An error has occurred "+ ex.getMessage());
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
                        else{

                            // Use the Builder class for convenient dialog construction
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                            builder.setTitle("PlayPaddy");
                            builder.setMessage("Either your username or password does not exist.");
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

                ;

                @Override
                protected void onCancelled() {
                    dialog.dismiss();
                    super.onCancelled();
                }
            };
            updateTask.execute((Void[]) null);


        } catch (Exception ex) {
            Toast.makeText(SignInActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            //ex.printStackTrace();
        }

    }

}
