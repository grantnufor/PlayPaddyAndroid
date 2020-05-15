package org.landoria.mac.playpaddy;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import HttpAdapter.EWalletHttpServiceAdapter;
import HttpAdapter.UserHttpServiceAdapter;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import co.paystack.android.PaystackSdk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserHomeFragment extends Fragment {



    UserHttpServiceAdapter userHttpServiceAdapter = new UserHttpServiceAdapter();
    EWalletHttpServiceAdapter eWalletHttpServiceAdapter = new EWalletHttpServiceAdapter();

    TextView textViewUserHomeDateSignedUp;
    TextView textViewUserHomeEWalletBalance;
    TextView textViewUserHomeWelcome;
    Button buttonUserHomePlayEnrolledGames;
    Button buttonUserHomeViewGameResults;
    Button buttonUserHomeEnrolToPlayAGame;


    Button buttonUserHomeFundEwallet;

    Button buttonUserHomeEwalletTransactions;

    JSONObject jsonUserObj = new JSONObject();
    JSONObject jsonEwalletMainObj = new JSONObject();



    String userId = "";
    String userName = "";


    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_user_home, container, false);



        Intent intent = getActivity().getIntent();
        userId = intent.getStringExtra("userid");
        userName = intent.getStringExtra("username");


        jsonUserObj = new JSONObject();


        textViewUserHomeDateSignedUp = (TextView)view.findViewById(R.id.textViewUserHomeDateSignedUp);
        textViewUserHomeEWalletBalance =(TextView)view.findViewById(R.id.textViewUserHomeEWalletBalance);

        textViewUserHomeWelcome = (TextView)view.findViewById(R.id.textViewUserHomeWelcome);

        buttonUserHomePlayEnrolledGames = (Button)view.findViewById(R.id.buttonUserHomePlayEnrolledGames);
        buttonUserHomePlayEnrolledGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getActivity(), UserEnroledGamesActivity.class);
                intent.putExtra("userid", userId);
                intent.putExtra("username", userName);
                startActivity(intent);


            }
        });


        buttonUserHomeViewGameResults =(Button)view.findViewById(R.id.buttonUserHomeViewGameResults);
        buttonUserHomeViewGameResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Use the Builder class for convenient dialog construction
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("PlayPaddy");
                builder.setMessage("You do not have any game results at the moment");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // You don't have to do anything here if you just
                        // want it dismissed when clicked
                    }
                });
                builder.show();
                return;


            }
        });


        buttonUserHomeEnrolToPlayAGame = (Button)view.findViewById(R.id.buttonUserHomeEnrolToPlayAGame);
        buttonUserHomeEnrolToPlayAGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), RewardGameHomeActivity.class);
                intent.putExtra("userid", userId);
                intent.putExtra("username", userName);
                startActivity(intent);

            }
        });


        buttonUserHomeFundEwallet = (Button) view.findViewById(R.id.buttonUserHomeFundEwallet);
        buttonUserHomeFundEwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getActivity(), FundEWalletActivity.class);
                intent.putExtra("userid", userId);
                intent.putExtra("username", userName);
                startActivity(intent);

            }
        });


        buttonUserHomeEwalletTransactions = (Button)view.findViewById(R.id.buttonUserHomeEwalletTransactions);
        buttonUserHomeEwalletTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        loadStats();

         return  view;
    }



    private void loadStats(){

        loadUserInfo();
        loadUserEwalletInfo();


        textViewUserHomeWelcome.setText("Welcome "+userName);

    }


    private  void loadUserInfo(){


        try {

            //setting up the error dialog
            final AlertDialog.Builder alert = new AlertDialog.Builder( getActivity(), R.style.AppTheme);
            alert.setTitle("Error!!!");

            //running the time consuming tasks in a seperate thread
            AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
                ProgressDialog dialog = new ProgressDialog(getActivity());


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

//                        String userIdNew = jsonUserObj.getString("UserId");
//
//                        if(!eWalletHttpServiceAdapter.GetEwalletByUserId(userIdNew).isNull("EWalletId")){
//
//                            jsonEwalletMainObj = eWalletHttpServiceAdapter.GetEwalletByUserId(userIdNew);
//                        }



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

                        if(!jsonUserObj.isNull("UserName")) {

                            try {

                                textViewUserHomeDateSignedUp.setText(jsonUserObj.getString("DateRegistered"));

                             //   textViewUserHomeEWalletBalance.setText("N"+jsonEwalletMainObj.getString("CurrentBalance"));


                            }
                            catch(Exception ex){

                            }

                        }
                        else if(jsonUserObj.isNull("UserName")){

                            // Use the Builder class for convenient dialog construction
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("PlayPaddy");
                            builder.setMessage("User Info not found. Please check your internet connection and try again.");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // You don't have to do anything here if you just
                                    // want it dismissed when clicked

                                    getActivity().finish();

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
            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
            //ex.printStackTrace();
        }
    }




    private void loadUserEwalletInfo(){


        try {

            //setting up the error dialog
            final AlertDialog.Builder alert = new AlertDialog.Builder( getActivity(), R.style.AppTheme);
            alert.setTitle("Error!!!");

            //running the time consuming tasks in a seperate thread
            AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
                ProgressDialog dialog = new ProgressDialog(getActivity());


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


                        if(!jsonUserObj.isNull("UserId")) {

                            String userIdNewNow = jsonUserObj.getString("UserId");

                            jsonEwalletMainObj = eWalletHttpServiceAdapter.GetEwalletByUserId(userIdNewNow);
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



                        try{

                            double currentBalance = Double.parseDouble(jsonEwalletMainObj.getString("CurrentBalance"));

                            textViewUserHomeEWalletBalance.setText("N"+String.format("%,.2f", currentBalance));

                        }
                        catch(Exception ex){

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
            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
            //ex.printStackTrace();
        }

    }

}
