package org.landoria.mac.playpaddy;

import HttpAdapter.GameHttpServiceAdapter;
import HttpAdapter.UserGameEnrolmentHttpServiceAdapter;
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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class UserEnroledGamesActivity extends AppCompatActivity {


    UserGameEnrolmentHttpServiceAdapter userGameEnrolmentHttpServiceAdapter = new UserGameEnrolmentHttpServiceAdapter();

    GameHttpServiceAdapter gameHttpServiceAdapter = new GameHttpServiceAdapter();

    ListView listViewUserEnrolledGames;

    String userId = "";
    String userName = "";


    long gameIdToPlay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_enroled_games);





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




        if(!isNetworkAvailable()) {

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(UserEnroledGamesActivity.this);
            builder.setTitle("PlayPaddy");
            builder.setMessage("Your device is not connected to the internet. Please connect to internet.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // You don't have to do anything here if you just
                    // want it dismissed when clicked
                }
            });
            builder.show();

            return;
        }



        listViewUserEnrolledGames = (ListView)findViewById(R.id.listViewUserEnrolledGames);


        loadUserEnroledGames();

    }



    ArrayList<JSONObject> userEnroledGameJsonList = new ArrayList<>();
    ArrayList<String> userEnroledGameNamesList = new ArrayList<>();

    ArrayList<JSONObject> gameJsonList = new ArrayList<>();

    public void loadUserEnroledGames() {


        //        //setting up the error dialog
        AlertDialog.Builder alert = new AlertDialog.Builder(UserEnroledGamesActivity.this, R.style.AppTheme);
        alert.setTitle("Error!!!");


        try {

            //running the time consuming tasks in a seperate thread
            AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
                ProgressDialog dialog = new ProgressDialog(UserEnroledGamesActivity.this);


                int errorCode = 0;
                String errorMessage = "";


                @Override
                protected void onPreExecute() {
                    // what to do before background task
                    dialog.setTitle("Loading Enrolled Games...");
                    dialog.setMessage("Please wait...");
                    dialog.setIndeterminate(true);
                    dialog.setCancelable(false);
                    dialog.show();
                }

                @Override
                protected Void doInBackground(Void... params) {


                    try {

                        //game
                        if (userGameEnrolmentHttpServiceAdapter != null) {
                            if (userGameEnrolmentHttpServiceAdapter.GetUserGameEnrolmentByUserIdAndStatus(userId, "unplayed").size() > 0) {

                                userEnroledGameJsonList = userGameEnrolmentHttpServiceAdapter.GetUserGameEnrolmentByUserIdAndStatus(userId, "unplayed");

                                for (int i = 0; i < userEnroledGameJsonList.size(); i++) {


                                    String gameId = userEnroledGameJsonList.get(i).getString("GameId");

                                    // Toast.makeText(HomeActivity.this, i + "", Toast.LENGTH_LONG).show();
                                    userEnroledGameNamesList.add(gameId);


                                    //get the games jsonobj
                                    if(!gameHttpServiceAdapter.GetGameByGameId(gameId).isNull("GameId")){

                                        gameJsonList.add(gameHttpServiceAdapter.GetGameByGameId(gameId));
                                    }

                                }
                            } else {



                                errorCode = 1;
                                errorMessage = "There are no Enrolled Games to load!";
                                // Toast.makeText(HomeActivity.this, "Nothing to backup", Toast.LENGTH_LONG).show();

                            }
                        } else {

                            errorCode = 1;
                            errorMessage = "No Enrolled Games to load!";
                        }


                    } catch (Exception ex) {


                        errorCode = 1;
                        errorMessage = ex.getMessage();

                    }

                    return null;


                }

                @Override
                protected void onPostExecute(Void result) {
                    // what to do when background task is completed
                    dialog.dismiss();
                    if (errorCode == 1) {
//                            alert.setMessage("Error has occured");
//                            alert.show();

                        Toast.makeText(UserEnroledGamesActivity.this, "Error has occurred" + errorMessage, Toast.LENGTH_LONG).show();
                        return;
                    } else {



                        if (userEnroledGameNamesList.size() > 0) {

                            populateListView();//load the populate list view method


                            Toast.makeText(UserEnroledGamesActivity.this, "Games Loaded", Toast.LENGTH_LONG).show();

                        }
                        else{

                            Toast.makeText(UserEnroledGamesActivity.this, "No Games found", Toast.LENGTH_LONG).show();


                        }


                        return;
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
            Toast.makeText(UserEnroledGamesActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();

        }


    }


    public void populateListView() {

        ArrayAdapter<String> adapter = new MyListAdapter();
        listViewUserEnrolledGames.setAdapter(adapter);

    }

    private class MyListAdapter extends ArrayAdapter<String> {

        public MyListAdapter() {
            super(UserEnroledGamesActivity.this, R.layout.game_layout, userEnroledGameNamesList);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View itemView = null;

            try {

                if(convertView == null){
                    itemView = getLayoutInflater().inflate(R.layout.user_enrolled_game_layout, parent, false);


                    //get the search text views
                    final TextView textViewUserEnrolledGameIdList = (TextView) itemView.findViewById(R.id.textViewUserEnrolledGameIdList);
                    TextView textViewcGameLayoutDateAndTimeToPlay = (TextView) itemView.findViewById(R.id.textViewcGameLayoutDateAndTimeToPlay);
                    TextView textViewUserEnrolledGameLayoutAmountToWin = (TextView)itemView.findViewById(R.id.textViewUserEnrolledGameLayoutAmountToWin);
                    TextView textViewUserEnrolledGameLayoutAmountToPay = (TextView)itemView.findViewById(R.id.textViewUserEnrolledGameLayoutAmountToPay);
                    TextView textViewUserEnrolledGameLayoutNoOfQuestion = (TextView) itemView.findViewById(R.id.textViewUserEnrolledGameLayoutNoOfQuestion);

                    Button buttonUserEnrolledGamePlayGame = (Button)itemView.findViewById(R.id.buttonUserEnrolledGamePlayGame);

                    if (gameJsonList.size() > 0) {//if there are game category to load

                        final String gameId = "" + gameJsonList.get(position).get("GameId");
                        String dateAndTimeToPlay = ""+ gameJsonList.get(position).get("DateToPlay")+" "+gameJsonList.get(position).get("TimeToPlay");
                        String amountToWin = ""+ gameJsonList.get(position).get("AmountToWin");
                        String amountToPlay = ""+ gameJsonList.get(position).get("PlayPrice");




                            gameIdToPlay = Long.parseLong("" + gameJsonList.get(position).get("GameId"));
//                        gameAmountToPlay = Double.parseDouble( ""+ rewardGameJsonList.get(position).get("PlayPrice"));


                            textViewUserEnrolledGameIdList.setText(gameId);
                            textViewcGameLayoutDateAndTimeToPlay.setText(dateAndTimeToPlay);
                            textViewUserEnrolledGameLayoutAmountToWin.setText("N" + String.format("%,.2f", Double.parseDouble(amountToWin)));
                            textViewUserEnrolledGameLayoutAmountToPay.setText("N" + String.format("%,.2f", Double.parseDouble(amountToPlay)));

                            buttonUserEnrolledGamePlayGame.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Use the Builder class for convenient dialog construction
                                    AlertDialog.Builder builder = new AlertDialog.Builder(UserEnroledGamesActivity.this);
                                    builder.setTitle("PlayPaddy");
                                    builder.setMessage("Are you sure you want to Play this Game now?");
                                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            String userGameEnrolmentId = "";


                                            try {

                                                String dateToPlay = ""+ gameJsonList.get(position).get("DateToPlay");
                                                String timeToPlay = ""+ gameJsonList.get(position).get("TimeToPlay");

                                                String patternDate = "dd/MM/yyyy";
                                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patternDate, new Locale("fr", "FR"));
                                                String date = simpleDateFormat.format(new Date());
                                                String todaysDate = date;

                                                String patternTime = "HH:mm";
                                                SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat(patternTime, new Locale("fr", "FR"));
                                                String time = simpleDateFormatTime.format(new Date());
                                                String timeNow =  time;


                                                if(Date.parse(dateToPlay) < Date.parse(date)){

                                                    // Use the Builder class for convenient dialog construction
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(UserEnroledGamesActivity.this);
                                                    builder.setTitle("PlayPaddy");
                                                    builder.setMessage("You cannot play this game until the designated date and time");
                                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            // You don't have to do anything here if you just
                                                            // want it dismissed when clicked
                                                        }
                                                    });
                                                    builder.show();

                                                }
                                                else if(Date.parse(dateToPlay) == Date.parse(date)) {

                                                    String userGameId = userEnroledGameJsonList.get(position).getString("GameId");

                                                    if (userGameId.equals(gameId)) {

                                                        userGameEnrolmentId = userEnroledGameJsonList.get(position).getString("UserGameEnrolmentId");

                                                        Intent intent = new Intent(UserEnroledGamesActivity.this, GamePanelActivity.class);
                                                        intent.putExtra("gameid", gameId);
                                                        intent.putExtra("userid", userId);
                                                        intent.putExtra("userenrolledgameid", userGameEnrolmentId);
                                                        startActivity(intent);
                                                        finish();
                                                    }

                                                }

                                            } catch (Exception ex) {

                                            }

                                        }
                                    });
                                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    });
                                    builder.show();
                                    return;

                                }
                            });


                    } else {

                        Toast.makeText(UserEnroledGamesActivity.this, "Your Enrolled Games could not be loaded. Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
                    }



                }
                else{

                    itemView = convertView;


                    //get the search text views
                    final TextView textViewUserEnrolledGameIdList = (TextView) itemView.findViewById(R.id.textViewUserEnrolledGameIdList);
                    TextView textViewcGameLayoutDateAndTimeToPlay = (TextView) itemView.findViewById(R.id.textViewcGameLayoutDateAndTimeToPlay);
                    TextView textViewUserEnrolledGameLayoutAmountToWin = (TextView)itemView.findViewById(R.id.textViewUserEnrolledGameLayoutAmountToWin);
                    TextView textViewUserEnrolledGameLayoutAmountToPay = (TextView)itemView.findViewById(R.id.textViewUserEnrolledGameLayoutAmountToPay);
                    TextView textViewUserEnrolledGameLayoutNoOfQuestion = (TextView) itemView.findViewById(R.id.textViewUserEnrolledGameLayoutNoOfQuestion);

                    Button buttonUserEnrolledGamePlayGame = (Button)itemView.findViewById(R.id.buttonUserEnrolledGamePlayGame);

                    if (gameJsonList.size() > 0) {//if there are grades to load

                        final String gameId = "" + gameJsonList.get(position).get("GameId");
                        String dateAndTimeToPlay = ""+ gameJsonList.get(position).get("DateToPlay")+" "+gameJsonList.get(position).get("TimeToPlay");
                        String amountToWin = ""+ gameJsonList.get(position).get("AmountToWin");
                        String amountToPlay = ""+ gameJsonList.get(position).get("PlayPrice");





                                gameIdToPlay = Long.parseLong("" + gameJsonList.get(position).get("GameId"));
//                        gameAmountToPlay = Double.parseDouble( ""+ rewardGameJsonList.get(position).get("PlayPrice"));


                                textViewUserEnrolledGameIdList.setText(gameId);
                                textViewcGameLayoutDateAndTimeToPlay.setText(dateAndTimeToPlay);
                                textViewUserEnrolledGameLayoutAmountToWin.setText("N" + String.format("%,.2f", Double.parseDouble(amountToWin)));
                                textViewUserEnrolledGameLayoutAmountToPay.setText("N" + String.format("%,.2f", Double.parseDouble(amountToPlay)));


                                buttonUserEnrolledGamePlayGame.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Use the Builder class for convenient dialog construction
                                        AlertDialog.Builder builder = new AlertDialog.Builder(UserEnroledGamesActivity.this);
                                        builder.setTitle("PlayPaddy");
                                        builder.setMessage("Are you sure you want to Play this Game now?");
                                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                String userGameEnrolmentId = "";

                                                try {


                                                    String dateToPlay = ""+ gameJsonList.get(position).get("DateToPlay");
                                                    String timeToPlay = ""+ gameJsonList.get(position).get("TimeToPlay");

                                                    String patternDate = "dd/MM/yyyy";
                                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patternDate, new Locale("fr", "FR"));
                                                    String date = simpleDateFormat.format(new Date());
                                                    String todaysDate = date;

                                                    String patternTime = "HH:mm";
                                                    SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat(patternTime, new Locale("fr", "FR"));
                                                    String time = simpleDateFormatTime.format(new Date());
                                                    String timeNow =  time;


                                                    if(Date.parse(dateToPlay) < Date.parse(date) || Date.parse(dateToPlay) > Date.parse(date) ){//date of play has passed.

                                                        // Use the Builder class for convenient dialog construction
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(UserEnroledGamesActivity.this);
                                                        builder.setTitle("PlayPaddy");
                                                        builder.setMessage("You cannot play this game. Today is not the designated date");
                                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                // You don't have to do anything here if you just
                                                                // want it dismissed when clicked
                                                            }
                                                        });
                                                        builder.show();

                                                    }
                                                    else if(Date.parse(dateToPlay) == Date.parse(date)) {

                                                        String userGameId = userEnroledGameJsonList.get(position).getString("GameId");

                                                        if (userGameId.equals(gameId)) {

                                                            userGameEnrolmentId = userEnroledGameJsonList.get(position).getString("UserGameEnrolmentId");

                                                            Intent intent = new Intent(UserEnroledGamesActivity.this, GamePanelActivity.class);
                                                            intent.putExtra("gameid", gameId);
                                                            intent.putExtra("userid", userId);
                                                            intent.putExtra("userenrolledgameid", userGameEnrolmentId);
                                                            startActivity(intent);
                                                            finish();
                                                        }

                                                    }

                                                } catch (Exception ex) {

                                                }
                                            }
                                        });
                                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                            }
                                        });
                                        builder.show();
                                        return;

                                    }
                                });




                    } else {

                        Toast.makeText(UserEnroledGamesActivity.this, "Your Enrolled Games could not be loaded. Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
                    }


                }

            } catch (Exception ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            }

            return itemView;

        }


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
