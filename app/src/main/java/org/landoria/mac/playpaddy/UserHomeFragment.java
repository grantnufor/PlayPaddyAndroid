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
    Button buttonUserHomeTopicGame;
    Button buttonUserHomeViewTopicResponses;
    Button buttonUserHomePlayAndWinGame;

    JSONObject jsonUserObj;
    JSONObject jsonEwalletObj;


    String userName = "";


    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_user_home, container, false);



        Intent intent = getActivity().getIntent();
        userName = intent.getStringExtra("username");


        jsonUserObj = new JSONObject();
        jsonEwalletObj = new JSONObject();

        textViewUserHomeDateSignedUp = (TextView)view.findViewById(R.id.textViewUserHomeDateSignedUp);
        textViewUserHomeEWalletBalance =(TextView)view.findViewById(R.id.textViewUserHomeEWalletBalance);

        textViewUserHomeWelcome = (TextView)view.findViewById(R.id.textViewUserHomeWelcome);

        buttonUserHomeTopicGame = (Button)view.findViewById(R.id.buttonUserHomeTopicGame);
        buttonUserHomeTopicGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), ViewTopicsActivity.class);
                startActivity(intent);

            }
        });


        buttonUserHomeViewTopicResponses =(Button)view.findViewById(R.id.buttonUserHomeViewTopicResponses);
        buttonUserHomeViewTopicResponses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        buttonUserHomePlayAndWinGame = (Button)view.findViewById(R.id.buttonUserHomeRewardGame);
        buttonUserHomePlayAndWinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), RewardGameHomeActivity.class);
                startActivity(intent);

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

                            }
                            catch(Exception ex){

                            }

                        }
                        else if(jsonUserObj.isNull("UserName")){

                            // Use the Builder class for convenient dialog construction
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("PlayPaddy");
                            builder.setMessage("User Info not found. Please try again. ");
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


                        if(!jsonUserObj.isNull("UserName")) {

                            jsonEwalletObj = eWalletHttpServiceAdapter.GetEwalletByUserId(jsonUserObj.getString("UserName"));
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

                            textViewUserHomeEWalletBalance.setText("N"+jsonEwalletObj.getString("CurrentBalance"));

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
