package org.landoria.mac.playpaddy;

import DBLayer.GameOptionDB;
import DBLayer.GameQuestionDB;
import DBLayer.GameUserAnswerDB;
import HttpAdapter.GameHttpServiceAdapter;
import HttpAdapter.GameOptionHttpServiceAdapter;
import HttpAdapter.GameQuestionHttpServiceAdapter;
import HttpAdapter.GameUserAnswerHttpServiceAdapter;
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
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class GamePanelActivity extends AppCompatActivity {


    GameHttpServiceAdapter gameHttpServiceAdapter = new GameHttpServiceAdapter();
    GameQuestionHttpServiceAdapter gameQuestionHttpServiceAdapter  = new GameQuestionHttpServiceAdapter();
    GameOptionHttpServiceAdapter gameOptionHttpServiceAdapter = new GameOptionHttpServiceAdapter();
    GameUserAnswerHttpServiceAdapter gameUserAnswerHttpServiceAdapter;

    UserGameEnrolmentHttpServiceAdapter userGameEnrolmentHttpServiceAdapter = new UserGameEnrolmentHttpServiceAdapter();

    //local db objects
    GameQuestionDB gameQuestionDB = new GameQuestionDB(this);
    GameOptionDB gameOptionDB = new GameOptionDB(this);
    GameUserAnswerDB gameUserAnswerDB = new GameUserAnswerDB(this);



    TextView textViewGamePanelQuestionInstruction;
    TextView textViewGamePanelQuestionStatus;

    TextView textViewGamePanelTime;

    EditText editTextGamePanelQuestion;
    RadioButton radioButtonGamePanelOptionA;
    RadioButton radioButtonGamePanelOptionB;
    RadioButton radioButtonGamePanelOptionC;
    RadioButton radioButtonGamePanelOptionD;
    RadioButton radioButtonGamePanelOptionE;

    Button buttonGamePanelNext;

    Button buttonGamePanelSubmit;



    String userId = "";
    String userName = "";
    String gameId = "";
    String userEnrolledGameId = "";

    String numInsertedGameOptions = "";
    String numInserted = "";



    ArrayList<JSONObject> gameOptionJsonListObj = new ArrayList<>();
    ArrayList<JSONObject> gameQuestionJsonListObj = new ArrayList<>();

    JSONObject userEnrolledGameJson = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_panel);


        Intent intent = getIntent();
        userId = intent.getStringExtra("userid");
        userName = intent.getStringExtra("username");
        gameId = intent.getStringExtra("gameid");
        userEnrolledGameId = intent.getStringExtra("userenrolledgameid");


        gameUserAnswerHttpServiceAdapter = new GameUserAnswerHttpServiceAdapter();

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
            AlertDialog.Builder builder = new AlertDialog.Builder(GamePanelActivity.this);
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


        pullDownGameQuestions();



        textViewGamePanelTime = (TextView)findViewById(R.id.textViewGamePanelTime);

        textViewGamePanelQuestionInstruction = (TextView)findViewById(R.id.textViewGamePanelQuestionInstruction);

        textViewGamePanelQuestionStatus = (TextView)findViewById(R.id.textViewGamePanelQuestionStatus);

        editTextGamePanelQuestion = (EditText)findViewById(R.id.editTextGamePanelQuestion);

        radioButtonGamePanelOptionA = (RadioButton)findViewById(R.id.radioButtonGamePanelOptionA);
        radioButtonGamePanelOptionB = (RadioButton)findViewById(R.id.radioButtonGamePanelOptionB);
        radioButtonGamePanelOptionC = (RadioButton)findViewById(R.id.radioButtonGamePanelOptionC);
        radioButtonGamePanelOptionD = (RadioButton)findViewById(R.id.radioButtonGamePanelOptionD);
        radioButtonGamePanelOptionE = (RadioButton)findViewById(R.id.radioButtonGamePanelOptionE);

        radioButtonGamePanelOptionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonGamePanelOptionB.setChecked(false);
                radioButtonGamePanelOptionC.setChecked(false);
                radioButtonGamePanelOptionD.setChecked(false);
                radioButtonGamePanelOptionE.setChecked(false);
//                radioButtonOptionA.setChecked(true);
            }
        });


        radioButtonGamePanelOptionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonGamePanelOptionA.setChecked(false);
                radioButtonGamePanelOptionC.setChecked(false);
                radioButtonGamePanelOptionD.setChecked(false);
                radioButtonGamePanelOptionE.setChecked(false);
//                radioButtonOptionA.setChecked(true);
            }
        });


        radioButtonGamePanelOptionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonGamePanelOptionA.setChecked(false);
                radioButtonGamePanelOptionB.setChecked(false);
                radioButtonGamePanelOptionD.setChecked(false);
                radioButtonGamePanelOptionE.setChecked(false);
//                radioButtonOptionA.setChecked(true);
            }
        });


        radioButtonGamePanelOptionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonGamePanelOptionA.setChecked(false);
                radioButtonGamePanelOptionB.setChecked(false);
                radioButtonGamePanelOptionC.setChecked(false);
                radioButtonGamePanelOptionE.setChecked(false);
//                radioButtonOptionA.setChecked(true);
            }
        });



        radioButtonGamePanelOptionE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonGamePanelOptionA.setChecked(false);
                radioButtonGamePanelOptionB.setChecked(false);
                radioButtonGamePanelOptionC.setChecked(false);
                radioButtonGamePanelOptionD.setChecked(false);
//                radioButtonOptionA.setChecked(true);
            }
        });



        buttonGamePanelNext = (Button)findViewById(R.id.buttonGamePanelNext);
        buttonGamePanelNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadNextGameQuestion();
            }
        });



        buttonGamePanelSubmit = (Button)findViewById(R.id.buttonGamePanelSubmit);
        buttonGamePanelSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                saveGameUserAnswerToServer();

            }
        });



    }


    private void deleteAllGameUserAnswerDB(){

        if(gameUserAnswerDB.getAllGameUserAnswers().size() > 0){


                gameUserAnswerDB.deleteAllGameUserAnswer();


        }
    }


    String timeCount = "";

    private void startCountDown(){


        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
               timeCount =  new SimpleDateFormat("ss").format(new Date( millisUntilFinished))+"";
                textViewGamePanelTime.setText(timeCount+" Seconds Left");
            }

            public void onFinish() {


                if(totalNumberOfQuestions  >= (currentQuestion + 1) ){//if there are still questions to ask

                    loadNextGameQuestion();

                }
                else{//if there are no more questions
                    submitGame();
                }

            }


        }.start();

    }



    private void submitGame(){



        if(currentQuestion > 0) {

            saveGameUserAnswerToLocalDb();//saving users response to db

        }


       saveGameUserAnswerToServer();

//        // Use the Builder class for convenient dialog construction
//        AlertDialog.Builder builder = new AlertDialog.Builder(GamePanelActivity.this);
//        builder.setTitle("PlayPaddy");
//        builder.setMessage("Game Completed");
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                // You don't have to do anything here if you just
//                // want it dismissed when clicked
//            }
//        });
//        builder.show();
//        return;
    }


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
                        dialog.setTitle("Loading your game...");
                        dialog.setMessage("Please wait...");
                        dialog.setIndeterminate(true);
                        dialog.setCancelable(false);
                        dialog.show();
                    }

                    @Override
                    protected Void doInBackground(Void... params) {


                        try {


                            //loading the user enrolled game json
                            if(userGameEnrolmentHttpServiceAdapter != null){

                                if(!userGameEnrolmentHttpServiceAdapter.GetUserGameEnrolmentByUserGameEnrolmentId(userEnrolledGameId).isNull("UserGameEnrolmentId")){

                                    userEnrolledGameJson = userGameEnrolmentHttpServiceAdapter.GetUserGameEnrolmentByUserGameEnrolmentId(userEnrolledGameId);

                                }

                            }
                            else {

                                errorCode = 1;
                                errorMessage = "No User Enrolment to load!";
                            }



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
                                    errorMessage += " There are no Game Questions to load!";
                                    // Toast.makeText(HomeActivity.this, "Nothing to backup", Toast.LENGTH_LONG).show();

                                }
                            } else {

                                errorCode = 1;
                                errorMessage += " No Game Questions to load!";
                            }


                        } catch (Exception ex) {


                            errorCode = 1;
                            errorMessage += ex.getMessage();

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


                            // Use the Builder class for convenient dialog construction
                            AlertDialog.Builder builder = new AlertDialog.Builder(GamePanelActivity.this);
                            builder.setTitle("PlayPaddy");
                            builder.setMessage("Error has occurred" + errorMessage + ". Please try again or contact PlayPaddy.");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    finish();

                                }
                            });
                            builder.show();

                            return;




                        } else {


                            pullDownGameOptions();


                            deleteAllGameUserAnswerDB();



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

                // Use the Builder class for convenient dialog construction
                AlertDialog.Builder builder = new AlertDialog.Builder(GamePanelActivity.this);
                builder.setTitle("PlayPaddy");
                builder.setMessage("Error has occurred" + ex.getMessage()+ ". Please try again or contact PlayPaddy.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        finish();

                    }
                });
                builder.show();

                return;

            }

        }


    }



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
                        dialog.setTitle("Loading your game...");
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
                                                gameOptionMap.put("IMAGE", "");
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

                            Toast.makeText(GamePanelActivity.this, "Error has occurred " + errorMessage, Toast.LENGTH_LONG).show();


                            // Use the Builder class for convenient dialog construction
                            AlertDialog.Builder builder = new AlertDialog.Builder(GamePanelActivity.this);
                            builder.setTitle("PlayPaddy");
                            builder.setMessage("Error has occurred " + errorMessage+ ". Please try again or contact PlayPaddy.");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    finish();

                                }
                            });
                            builder.show();

                            return;


                        } else {




                            loadNextGameQuestion();

//                            // Use the Builder class for convenient dialog construction
//                            AlertDialog.Builder builder = new AlertDialog.Builder(GamePanelActivity.this);
//                            builder.setTitle("PlayPaddy");
//                            builder.setMessage(numInsertedGameOptions);
//                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    // You don't have to do anything here if you just
//                                    // want it dismissed when clicked
//                                }
//                            });
//                            builder.show();
//
//                            return;

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


                // Use the Builder class for convenient dialog construction
                AlertDialog.Builder builder = new AlertDialog.Builder(GamePanelActivity.this);
                builder.setTitle("PlayPaddy");
                builder.setMessage(ex.getMessage()+ ". Please try again or contact PlayPaddy.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        finish();

                    }
                });
                builder.show();

                return;

            }

        }


    }


    int currentQuestion = 0;
    int totalNumberOfQuestions = 0;

    String gameQuestionId = "";
    String answer = "";
    private void loadNextGameQuestion() {


        startCountDown();


        if (gameQuestionDB.getAllGameQuestions().size() > 0) {


            if(currentQuestion > 0) {

                saveGameUserAnswerToLocalDb();//saving users response to db

            }



            radioButtonGamePanelOptionA.setChecked(false);
            radioButtonGamePanelOptionB.setChecked(false);
            radioButtonGamePanelOptionC.setChecked(false);
            radioButtonGamePanelOptionD.setChecked(false);
            radioButtonGamePanelOptionE.setChecked(false);


            totalNumberOfQuestions = gameQuestionDB.getAllGameQuestions().size();


            if(totalNumberOfQuestions >= (currentQuestion + 1) ){

                HashMap<String, String> gameQuestionObj = gameQuestionDB.getAllGameQuestions().get(currentQuestion);


                gameQuestionId = gameQuestionObj.get("GAME_QUESTION_ID");

                answer = gameQuestionObj.get("ANSWER");

                textViewGamePanelQuestionInstruction.setText(gameQuestionObj.get("INSTRUCTION"));
                textViewGamePanelQuestionStatus.setText("Question " + (currentQuestion + 1) + " of " + totalNumberOfQuestions);
                editTextGamePanelQuestion.setText(gameQuestionObj.get("DETAIL"));


                //loading the game options
                radioButtonGamePanelOptionA.setText("A. " + gameOptionDB.getGameOptionByGameQuestionIdAndOptionName(gameQuestionId, "A").get("OPTION_DETAIL") + "");
                radioButtonGamePanelOptionB.setText("B. " + gameOptionDB.getGameOptionByGameQuestionIdAndOptionName(gameQuestionId, "B").get("OPTION_DETAIL") + "");
                radioButtonGamePanelOptionC.setText("C. " + gameOptionDB.getGameOptionByGameQuestionIdAndOptionName(gameQuestionId, "C").get("OPTION_DETAIL") + "");
                radioButtonGamePanelOptionD.setText("D. " + gameOptionDB.getGameOptionByGameQuestionIdAndOptionName(gameQuestionId, "D").get("OPTION_DETAIL") + "");
                radioButtonGamePanelOptionE.setText("E. " + gameOptionDB.getGameOptionByGameQuestionIdAndOptionName(gameQuestionId, "E").get("OPTION_DETAIL") + "");


                currentQuestion++;


            }
            else{
                submitGame();
            }





        }
    }


    private void saveGameUserAnswerToLocalDb() {

        String userAnswer1 = "";

        //getting the user answer
        if (radioButtonGamePanelOptionA.isChecked()) {
            userAnswer1 = "A";
        }

        else if (radioButtonGamePanelOptionB.isChecked()) {
            userAnswer1 = "B";
        }

        else if (radioButtonGamePanelOptionC.isChecked()) {
            userAnswer1 = "C";
        }

        else if (radioButtonGamePanelOptionD.isChecked()) {
            userAnswer1 = "D";
        }

        else if (radioButtonGamePanelOptionE.isChecked()) {
            userAnswer1 = "E";
        }


        String pattern = "MM/dd/yyyy HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("fr", "FR"));
        String date = simpleDateFormat.format(new Date());

        int  gameUserAnswerId = currentQuestion+1;

        HashMap<String, String> gameUserAnswerMap = new HashMap<>();
        gameUserAnswerMap.put("GAME_USER_ANSWER_ID", gameUserAnswerId+"");
        gameUserAnswerMap.put("GAME_QUESTION_ID", gameQuestionId);
        gameUserAnswerMap.put("USER_ID", userId);
        gameUserAnswerMap.put("USER_ANSWER", userAnswer1);
        gameUserAnswerMap.put("CORRECT", answer);
        gameUserAnswerMap.put("POSITION", "0");
        gameUserAnswerMap.put("TIME_SUBMITTED", timeCount);
        gameUserAnswerMap.put("DATE_SUBMITTED", date);

        gameUserAnswerDB.insertGameUserAnswer(gameUserAnswerMap);


//        String gameUserAnswerCount = gameUserAnswerDB.getAllGameUserAnswers().size()+"";
//
//        // Use the Builder class for convenient dialog construction
//        AlertDialog.Builder builder = new AlertDialog.Builder(GamePanelActivity.this);
//        builder.setTitle("PlayPaddy");
//        builder.setMessage("Game User Answer Count: "+gameUserAnswerCount);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                // You don't have to do anything here if you just
//                // want it dismissed when clicked
//            }
//        });
//        builder.show();
//        return;



    }



    int numLocalSaved = 0;
    int numServerSaved = 0;

    private void saveGameUserAnswerToServer(){

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
                    dialog.setTitle("Submitting your game result...");
                    dialog.setMessage("Please wait...");
                    dialog.setIndeterminate(true);
                    dialog.setCancelable(false);
                    dialog.show();
                }

                @Override
                protected Void doInBackground(Void... params) {


                    try {


                        numLocalSaved = gameUserAnswerDB.getAllGameUserAnswers().size();
                        numServerSaved = 0;

                        if (gameUserAnswerDB.getAllGameUserAnswers().size() > 0) {

                            int gameUserAnswerCount = gameUserAnswerDB.getAllGameUserAnswers().size();

                            for (int i = 0; i < gameUserAnswerCount; i++) {

                                String gameQuestionIdSave = gameUserAnswerDB.getAllGameUserAnswers().get(i).get("GAME_QUESTION_ID");
                                String userIdSave = gameUserAnswerDB.getAllGameUserAnswers().get(i).get("USER_ID");
                                String userAnswerSave = gameUserAnswerDB.getAllGameUserAnswers().get(i).get("USER_ANSWER");
                                String correctSave = gameUserAnswerDB.getAllGameUserAnswers().get(i).get("CORRECT");
                                String positionSave = gameUserAnswerDB.getAllGameUserAnswers().get(i).get("POSITION");
                                String timeSubmittedSave = gameUserAnswerDB.getAllGameUserAnswers().get(i).get("TIME_SUBMITTED");
                                String dateSubmittedSave = gameUserAnswerDB.getAllGameUserAnswers().get(i).get("DATE_SUBMITTED");


                                String returnVal = gameUserAnswerHttpServiceAdapter.AddGameUserAnswer(gameQuestionIdSave, userIdSave, userAnswerSave,
                                        correctSave, positionSave, timeSubmittedSave, dateSubmittedSave);

                                if (returnVal.equals("1")) {
                                    numServerSaved++;
                                }

                            }

                        }


                        userGameEnrolmentHttpServiceAdapter.UpdateUserGameEnrolment(userEnrolledGameId, userEnrolledGameJson.getString("UserId"), userEnrolledGameJson.getString("GameId"), userEnrolledGameJson.getString("AmountPaid"), userEnrolledGameJson.getString("DateOfPayment"), "played");



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



                        if (numLocalSaved == numServerSaved) {

                            Intent intent = new Intent(GamePanelActivity.this, GameCompletedActivity.class);
                            startActivity(intent);

                            finish();

                        } else {

                            // Use the Builder class for convenient dialog construction
                            AlertDialog.Builder builder = new AlertDialog.Builder(GamePanelActivity.this);
                            builder.setTitle("PlayPaddy");
                            builder.setMessage("An error occurred. Please contact PlayPaddy.");
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
            Toast.makeText(GamePanelActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();

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
