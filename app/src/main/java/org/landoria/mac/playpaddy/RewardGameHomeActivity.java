package org.landoria.mac.playpaddy;

import HttpAdapter.GameCategoryHttpServiceAdapter;
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

import java.util.ArrayList;

public class RewardGameHomeActivity extends AppCompatActivity {



    GameCategoryHttpServiceAdapter gameCategoryHttpServiceAdapter = new GameCategoryHttpServiceAdapter();


    ListView listViewGameCategories;

    String userId = "";
    String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_game_home);


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


        listViewGameCategories = (ListView)findViewById(R.id.listViewGameCategories);


        loadRewardGameCategories();

    }




    ArrayList<JSONObject> rewardGameCategoryJsonList = new ArrayList<>();
    ArrayList<String> rewardGameCategoryNamesList = new ArrayList<>();

    public void loadRewardGameCategories() {


        //        //setting up the error dialog
        AlertDialog.Builder alert = new AlertDialog.Builder(RewardGameHomeActivity.this, R.style.AppTheme);
        alert.setTitle("Error!!!");


        try {

            //running the time consuming tasks in a seperate thread
            AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
                ProgressDialog dialog = new ProgressDialog(RewardGameHomeActivity.this);


                int errorCode = 0;
                String errorMessage = "";


                @Override
                protected void onPreExecute() {
                    // what to do before background task
                    dialog.setTitle("Loading Reward Game Categories...");
                    dialog.setMessage("Please wait...");
                    dialog.setIndeterminate(true);
                    dialog.setCancelable(false);
                    dialog.show();
                }

                @Override
                protected Void doInBackground(Void... params) {


                    try {

                        //topic
                        if (gameCategoryHttpServiceAdapter != null) {
                            if (gameCategoryHttpServiceAdapter.GetAllGameCategories().size() > 0) {

                                rewardGameCategoryJsonList = gameCategoryHttpServiceAdapter.GetAllGameCategories();

                                for (int i = 0; i < rewardGameCategoryJsonList.size(); i++) {

                                    // Toast.makeText(HomeActivity.this, i + "", Toast.LENGTH_LONG).show();
                                    rewardGameCategoryNamesList.add(rewardGameCategoryJsonList.get(i).getString("CategoryName"));

                                }
                            } else {


                                errorMessage = "There are no Reward Game Category to load!";
                                // Toast.makeText(HomeActivity.this, "Nothing to backup", Toast.LENGTH_LONG).show();

                            }
                        } else {
                            errorMessage = "No Game Category to load!";
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

                        Toast.makeText(RewardGameHomeActivity.this, "Error has occurred" + errorMessage, Toast.LENGTH_LONG).show();
                        return;
                    } else {



                        if (rewardGameCategoryNamesList.size() > 0) {

                            populateListView();//load the populate list view method


                            Toast.makeText(RewardGameHomeActivity.this, "Game categories Loaded", Toast.LENGTH_LONG).show();


//                            // Use the Builder class for convenient dialog construction
//                            AlertDialog.Builder builder = new AlertDialog.Builder(RewardGameHomeActivity.this);
//                            builder.setTitle("PlayPaddy");
//                            builder.setMessage("Game categories Loaded");
//                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    // You don't have to do anything here if you just
//                                    // want it dismissed when clicked
//                                }
//                            });
//                            builder.show();


                        }
                        else{

                            Toast.makeText(RewardGameHomeActivity.this, "No Game Categories found", Toast.LENGTH_LONG).show();

                            // Use the Builder class for convenient dialog construction
                            AlertDialog.Builder builder = new AlertDialog.Builder(RewardGameHomeActivity.this);
                            builder.setTitle("PlayPaddy");
                            builder.setMessage("No Game Categories found");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // You don't have to do anything here if you just
                                    // want it dismissed when clicked
                                }
                            });
                            builder.show();



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
            Toast.makeText(RewardGameHomeActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();

        }


    }


    public void populateListView() {

        ArrayAdapter<String> adapter = new MyListAdapter();
        listViewGameCategories.setAdapter(adapter);

    }

    private class MyListAdapter extends ArrayAdapter<String> {

        public MyListAdapter() {
            super(RewardGameHomeActivity.this, R.layout.game_category_layout, rewardGameCategoryNamesList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View itemView = null;

            try {

                if(convertView == null){
                    itemView = getLayoutInflater().inflate(R.layout.game_category_layout, parent, false);


                    //get the search text views
                    final TextView textViewGameCategoryIdList = (TextView) itemView.findViewById(R.id.textViewGameCategoryIdList);
                    TextView textViewCategoryName = (TextView) itemView.findViewById(R.id.textViewCategoryName);
                    Button buttonOpenGames = (Button)itemView.findViewById(R.id.buttonOpenGames);

                    if (rewardGameCategoryJsonList.size() > 0) {//if there are game category to load

                        final String categoryId = "" + rewardGameCategoryJsonList.get(position).get("GameCategoryId");
                        final String categoryName = ""+ rewardGameCategoryJsonList.get(position).get("CategoryName");
                        String categoryHighestWorth = ""+rewardGameCategoryJsonList.get(position).get("CategoryHighestWorth");


                        textViewGameCategoryIdList.setText(categoryId);
                        textViewCategoryName.setText(categoryName + " GAME (Win up to N"+categoryHighestWorth+")");
                        buttonOpenGames.setText("Open "+categoryName+ " Games");
                        buttonOpenGames.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(RewardGameHomeActivity.this, RewardGameViewGamesActivity.class);
                                intent.putExtra("categoryId", categoryId);
                                intent.putExtra("categoryName", categoryName);
                                intent.putExtra("userid", userId);
                                intent.putExtra("username", userName);
                                startActivity(intent);
                            }
                        });



                    } else {

                        Toast.makeText(RewardGameHomeActivity.this, "Reward Game Category could not be loaded. Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
                    }



                }
                else{

                    itemView = convertView;


                    //get the search text views
                    final TextView textViewGameCategoryIdList = (TextView) itemView.findViewById(R.id.textViewGameCategoryIdList);
                    TextView textViewCategoryName = (TextView) itemView.findViewById(R.id.textViewCategoryName);
                    Button buttonOpenGames = (Button)itemView.findViewById(R.id.buttonOpenGames);



                    if (rewardGameCategoryJsonList.size() > 0) {//if there are grades to load

                        final String categoryId = "" + rewardGameCategoryJsonList.get(position).get("GameCategoryId");
                        final String categoryName = ""+ rewardGameCategoryJsonList.get(position).get("CategoryName");
                        String categoryHighestWorth = ""+rewardGameCategoryJsonList.get(position).get("CategoryHighestWorth");


                        textViewGameCategoryIdList.setText(categoryId);
                        textViewCategoryName.setText(categoryName + " GAME (Win up to N"+categoryHighestWorth+")");
                        buttonOpenGames.setText("Open "+categoryName+ " Games");
                        buttonOpenGames.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(RewardGameHomeActivity.this, RewardGameViewGamesActivity.class);
                                intent.putExtra("categoryId", categoryId);
                                intent.putExtra("categoryName", categoryName);
                                intent.putExtra("userid", userId);
                                intent.putExtra("username", userName);
                                startActivity(intent);
                            }
                        });



                    } else {

                        Toast.makeText(RewardGameHomeActivity.this, "Reward Games could not be loaded. Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
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
