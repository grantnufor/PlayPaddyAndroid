package org.landoria.mac.playpaddy;

import DBLayer.GameOptionDB;
import DBLayer.GameQuestionDB;
import DBLayer.GameUserAnswerDB;
import HttpAdapter.GameHttpServiceAdapter;
import HttpAdapter.GameOptionHttpServiceAdapter;
import HttpAdapter.GameQuestionHttpServiceAdapter;
import HttpAdapter.GameUserAnswerHttpServiceAdapter;
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
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

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
    String gameId = "";


    ArrayList<JSONObject> gameOptionJsonListObj = new ArrayList<>();
    ArrayList<JSONObject> gameQuestionJsonListObj = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_panel);


        Intent intent = getIntent();
        userId = intent.getStringExtra("userid");
        userName = intent.getStringExtra("username");
        gameId = intent.getStringExtra("gameid");


        showBackButton();


        //showing activity name and app icon on action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logomainwhite);
//        getSupportActionBar().setTitle(sessionDb.getSessionBySessionId("1").get("NAME").toString());
        getSupportActionBar().setDisplayUseLogoEnabled(true);


//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#800000")));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006400")));


        pullDownGameQuestions();

    }



    String numInserted = "";



    private void pullDownGameQuestions(){


        if(!gameId.equals("") && !userId.equals("")){//if there is a game id available


            //delete existing games
            if (gameQuestionDB.getAllGameQuestions().size() > 0) {//if any game exists already on user's phone delete it

                for(int i = 0; i < gameQuestionDB.getAllGameQuestions().size(); i++){

                    String gameQuestionIdDel = gameQuestionDB.getAllGameQuestions().get(i).get("GAME_QUESTION_ID");

                    gameQuestionDB.deleteGameQuestion(gameQuestionIdDel);

                }

            }



                //check if this user actually has this game
            //        //setting up the error dialog
            AlertDialog.Builder alert = new AlertDialog.Builder(GamePanelActivity.this, R.style.AppTheme);
            alert.setTitle("Error!!!");


            try {

                //running the time consuming tasks in a seperate thread
                AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
                    ProgressDialog dialog = new ProgressDialog(GamePanelActivity.this);


                    int errorCode = 0;
                    String errorMessage = "";


                    @Override
                    protected void onPreExecute() {
                        // what to do before background task
                        dialog.setTitle("Loading...");
                        dialog.setMessage("Please wait...");
                        dialog.setIndeterminate(true);
                        dialog.setCancelable(false);
                        dialog.show();
                    }

                    @Override
                    protected Void doInBackground(Void... params) {


                        try {

                            //pulling down game question for this game
                            if (gameQuestionHttpServiceAdapter != null) {
                                if (gameQuestionHttpServiceAdapter.GetGameQuestionByGameId(gameId).size() > 0) {

                                    gameQuestionJsonListObj = gameQuestionHttpServiceAdapter.GetGameQuestionByGameId(gameId);

                                    for (int i = 0; i < gameQuestionJsonListObj.size(); i++) {

                                        String gameQuestionId = gameQuestionJsonListObj.get(i).getString("GameQuestionId");
                                        String instruction = gameQuestionJsonListObj.get(i).getString("Instruction");
                                        String detail = gameQuestionJsonListObj.get(i).getString("Detail");
                                        String image = gameQuestionJsonListObj.get(i).getString("Image");
                                        String answer = gameQuestionJsonListObj.get(i).getString("Answer");
                                        String explanation = gameQuestionJsonListObj.get(i).getString("Explanation");
                                        String timeAlloted = gameQuestionJsonListObj.get(i).getString("TimeAlloted");
                                        String gameIdNew = gameQuestionJsonListObj.get(i).getString("GameId");


                                            HashMap<String, String> gameQuestionMap = new HashMap<>();
                                            gameQuestionMap.put("GAME_QUESTION_ID", gameQuestionId);
                                            gameQuestionMap.put("INSTRUCTION", instruction);
                                            gameQuestionMap.put("DETAIL", detail);
                                            gameQuestionMap.put("IMAGE", image);
                                            gameQuestionMap.put("ANSWER", answer);
                                            gameQuestionMap.put("EXPLANATION", explanation);
                                            gameQuestionMap.put("TIME_ALLOTED", timeAlloted);
                                            gameQuestionMap.put("GAME_ID", gameIdNew);

                                            gameQuestionDB.insertGameQuestion(gameQuestionMap);




                                       numInserted = gameQuestionDB.getAllGameQuestions().size()+"";


                                    }
                                } else {



                                    errorCode = 1;
                                    errorMessage = "There are no Game Questions to load!";
                                    // Toast.makeText(HomeActivity.this, "Nothing to backup", Toast.LENGTH_LONG).show();

                                }
                            } else {

                                errorCode = 1;
                                errorMessage = "No Game Questions to load!";
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

                            Toast.makeText(GamePanelActivity.this, "Error has occurred" + errorMessage, Toast.LENGTH_LONG).show();
                            return;


                        } else {


                            pullDownGameOptions();


//                            // Use the Builder class for convenient dialog construction
//                            AlertDialog.Builder builder = new AlertDialog.Builder(GamePanelActivity.this);
//                            builder.setTitle("PlayPaddy");
//                            builder.setMessage(numInserted);
//                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    // You don't have to do anything here if you just
//                                    // want it dismissed when clicked
//                                }
//                            });
//                            builder.show();

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
                Toast.makeText(GamePanelActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();

            }

        }


    }



    String numInsertedGameOptions = "";



    private void pullDownGameOptions(){


        //check if this user actually has this game
        if(!gameId.equals("") && !userId.equals("")){//if there is a game id available


            //delete existing games
            if (gameOptionDB.getAllGameOptions().size() > 0) {//if any game exists already on user's phone delete it

                for(int i = 0; i < gameOptionDB.getAllGameOptions().size(); i++){

                    String gameOptionIdDel = gameOptionDB.getAllGameOptions().get(i).get("GAME_OPTION_ID");

                    gameOptionDB.deleteGameOption(gameOptionIdDel);

                }

            }




            //        //setting up the error dialog
            AlertDialog.Builder alert = new AlertDialog.Builder(GamePanelActivity.this, R.style.AppTheme);
            alert.setTitle("Error!!!");


            try {

                //running the time consuming tasks in a seperate thread
                AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
                    ProgressDialog dialog = new ProgressDialog(GamePanelActivity.this);


                    int errorCode = 0;
                    String errorMessage = "";


                    @Override
                    protected void onPreExecute() {
                        // what to do before background task
                        dialog.setTitle("Loading...");
                        dialog.setMessage("Please wait...");
                        dialog.setIndeterminate(true);
                        dialog.setCancelable(false);
                        dialog.show();
                    }

                    @Override
                    protected Void doInBackground(Void... params) {


                        try {

                            //pulling down game option for this game
                            if (gameQuestionHttpServiceAdapter != null) {
                                if (gameQuestionHttpServiceAdapter.GetGameQuestionByGameId(gameId).size() > 0) {

                                    gameQuestionJsonListObj = gameQuestionHttpServiceAdapter.GetGameQuestionByGameId(gameId);

                                    for (int i = 0; i < gameQuestionJsonListObj.size(); i++) {



                                        String gameQuestionId = gameQuestionJsonListObj.get(i).getString("GameQuestionId");

                                        if(gameOptionHttpServiceAdapter.GetGameOptionByGameQuestionId(gameQuestionId).size() > 0) {

                                            gameOptionJsonListObj = gameOptionHttpServiceAdapter.GetGameOptionByGameQuestionId(gameQuestionId);

                                            for (int j = 0; j < gameOptionJsonListObj.size(); j++) {

                                                String gameOptionId = gameOptionJsonListObj.get(j).getString("GameOptionId");
                                                String optionName = gameOptionJsonListObj.get(j).getString("OptionName");
                                                String optionDetail = gameOptionJsonListObj.get(j).getString("OptionDetail");
                                                String image = gameOptionJsonListObj.get(j).getString("Image");
                                                String gameQuestionIdNew = gameOptionJsonListObj.get(j).getString("GameQuestionId");


                                                HashMap<String, String> gameOptionMap = new HashMap<>();
                                                gameOptionMap.put("GAME_OPTION_ID", gameOptionId);
                                                gameOptionMap.put("OPTION_NAME", optionName);
                                                gameOptionMap.put("OPTION_DETAIL", optionDetail);
                                                gameOptionMap.put("IMAGE", image);
                                                gameOptionMap.put("GAME_QUESTION_ID", gameQuestionIdNew);


                                                gameOptionDB.insertGameOption(gameOptionMap);

                                            }



                                        }





                                    }
                                } else {



                                    errorCode = 1;
                                    errorMessage = "There are no Game Options to load!";
                                    // Toast.makeText(HomeActivity.this, "Nothing to backup", Toast.LENGTH_LONG).show();

                                }
                            } else {

                                errorCode = 1;
                                errorMessage = "No Game Options to load!";
                            }


                        } catch (Exception ex) {


                            errorCode = 1;
                            errorMessage = ex.getMessage();

                        }

                        numInsertedGameOptions = gameOptionDB.getAllGameOptions().size() + "";


                        return null;


                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        // what to do when background task is completed
                        dialog.dismiss();
                        if (errorCode == 1) {
//                            alert.setMessage("Error has occured");
//                            alert.show();

                            Toast.makeText(GamePanelActivity.this, "Error has occurred" + errorMessage, Toast.LENGTH_LONG).show();
                            return;


                        } else {



                            // Use the Builder class for convenient dialog construction
                            AlertDialog.Builder builder = new AlertDialog.Builder(GamePanelActivity.this);
                            builder.setTitle("PlayPaddy");
                            builder.setMessage(numInsertedGameOptions);
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

                    ;

                    @Override
                    protected void onCancelled() {
                        dialog.dismiss();
                        super.onCancelled();
                    }
                };
                updateTask.execute((Void[]) null);


            } catch (Exception ex) {
                Toast.makeText(GamePanelActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();

            }

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
