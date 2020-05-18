package org.landoria.mac.playpaddy;

import HttpAdapter.EWalletHttpServiceAdapter;
import HttpAdapter.EWalletTransactionHttpServiceAdapter;
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

public class ViewEWalletTransactionsActivity extends AppCompatActivity {


    EWalletTransactionHttpServiceAdapter eWalletTransactionHttpServiceAdapter;

    EWalletHttpServiceAdapter eWalletHttpServiceAdapter;


    TextView textViewEWalletTransactionsEWalletBalance;

    ListView listViewEWalletTransaction;



    String userId = "";
    String userName = "";


    Double eWalletCurrentBalance = 0.00;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ewallet_transactions);


        eWalletTransactionHttpServiceAdapter = new EWalletTransactionHttpServiceAdapter();
        eWalletHttpServiceAdapter = new EWalletHttpServiceAdapter();






        Intent intent = getIntent();
        userId = intent.getStringExtra("userid");
        userName = intent.getStringExtra("username");



        showBackButton();


        //showing activity name and app icon on action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.drawable.logomainwhite);
        getSupportActionBar().setTitle("MY EWALLET TRANSACTIONS");
//        getSupportActionBar().setDisplayUseLogoEnabled(true);


//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#800000")));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006400")));



        if(!isNetworkAvailable()) {

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewEWalletTransactionsActivity.this);
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



        textViewEWalletTransactionsEWalletBalance = (TextView)findViewById(R.id.textViewEWalletTransactionsEWalletBalance);
        listViewEWalletTransaction = (ListView) findViewById(R.id.listViewEWalletTransaction);


        loadEwalletTransactions();


    }



    ArrayList<JSONObject> eWalletTransactionJsonList = new ArrayList<>();
    ArrayList<String> eWalletTransactionNamesList = new ArrayList<>();

    public void loadEwalletTransactions() {


        //        //setting up the error dialog
        AlertDialog.Builder alert = new AlertDialog.Builder(ViewEWalletTransactionsActivity.this, R.style.AppTheme);
        alert.setTitle("Error!!!");


        try {

            //running the time consuming tasks in a seperate thread
            AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
                ProgressDialog dialog = new ProgressDialog(ViewEWalletTransactionsActivity.this);


                int errorCode = 0;
                String errorMessage = "";


                @Override
                protected void onPreExecute() {
                    // what to do before background task
                    dialog.setTitle("Loading your EWallet Transactions...");
                    dialog.setMessage("Please wait...");
                    dialog.setIndeterminate(true);
                    dialog.setCancelable(false);
                    dialog.show();
                }

                @Override
                protected Void doInBackground(Void... params) {


                    try {

                        //ewallet
                        //checking if user already has an ewallet
                        if(!eWalletHttpServiceAdapter.GetEwalletByUserId(userId).isNull("EwalletId")) {

                            String eWalletId = eWalletHttpServiceAdapter.GetEwalletByUserId(userId).getString("EwalletId");
                            eWalletCurrentBalance = Double.parseDouble( eWalletHttpServiceAdapter.GetEwalletByUserId(userId).getString("CurrentBalance"));

                            if (eWalletTransactionHttpServiceAdapter != null) {
                                if (eWalletTransactionHttpServiceAdapter.GetEwalletTransactionByEwalletId(eWalletId).size() > 0) {

                                    eWalletTransactionJsonList = eWalletTransactionHttpServiceAdapter.GetEwalletTransactionByEwalletId(eWalletId);

                                    for (int i = 0; i < eWalletTransactionJsonList.size(); i++) {


                                        eWalletTransactionNamesList.add(eWalletTransactionJsonList.get(i).getString("EWalletTransactionId"));

                                    }
                                } else {


                                    errorMessage = "There are no EWallet Transactions to load!";
                                    // Toast.makeText(HomeActivity.this, "Nothing to backup", Toast.LENGTH_LONG).show();

                                }
                            } else {
                                errorMessage = "No EWallet Transactions to load!";
                            }

                        }
                        else{//if no ewallet

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

                        Toast.makeText(ViewEWalletTransactionsActivity.this, "Error has occurred" + errorMessage, Toast.LENGTH_LONG).show();
                        return;
                    } else {



                        if (eWalletTransactionNamesList.size() > 0) {

                            populateListView();//load the populate list view method


                            Toast.makeText(ViewEWalletTransactionsActivity.this, "Transactions Loaded", Toast.LENGTH_LONG).show();

                        }
                        else{

                            Toast.makeText(ViewEWalletTransactionsActivity.this, "No Transactions found", Toast.LENGTH_LONG).show();


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
            Toast.makeText(ViewEWalletTransactionsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();

        }


    }


    public void populateListView() {

        ArrayAdapter<String> adapter = new MyListAdapter();
        listViewEWalletTransaction.setAdapter(adapter);

    }

    private class MyListAdapter extends ArrayAdapter<String> {

        public MyListAdapter() {
            super(ViewEWalletTransactionsActivity.this, R.layout.game_layout, eWalletTransactionNamesList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View itemView = null;

            try {

                if(convertView == null){
                    itemView = getLayoutInflater().inflate(R.layout.ewallet_transactions_layout, parent, false);


                    //get the search text views
                    TextView textViewEWalletTransactionLayoutDate = (TextView) itemView.findViewById(R.id.textViewEWalletTransactionLayoutDate);
                    TextView textViewEWalletTransactionLayoutType = (TextView)itemView.findViewById(R.id.textViewEWalletTransactionLayoutType);
                    final TextView textViewEWalletTransactionLayoutAmount = (TextView)itemView.findViewById(R.id.textViewEWalletTransactionLayoutAmount);
                    TextView textViewEWalletTransactionLayoutDescription = (TextView) itemView.findViewById(R.id.textViewEWalletTransactionLayoutDescription);

                    if (eWalletTransactionJsonList.size() > 0) {//if there are ewallet transactions to load

                        String desciption = ""+ eWalletTransactionJsonList.get(position).get("Narration");
                        String date = ""+ eWalletTransactionJsonList.get(position).get("DateOfTransaction");
                        Double debit = Double.parseDouble(""+ eWalletTransactionJsonList.get(position).get("Debit"));
                        Double credit = Double.parseDouble(""+ eWalletTransactionJsonList.get(position).get("Credit"));
                        String type = "";

                        if(credit > 0){

                            type  = "Credit";

                            textViewEWalletTransactionLayoutAmount.setText("Amount: N"+String.format("%,.2f", credit));

                        }

                        if(debit > 0){

                            type = "Debit";

                            textViewEWalletTransactionLayoutAmount.setText("Amount: N"+String.format("%,.2f", debit));

                        }


                        textViewEWalletTransactionLayoutDate.setText("Date: "+date);
                        textViewEWalletTransactionLayoutDescription.setText("Narration: "+desciption);
                        textViewEWalletTransactionLayoutType.setText("Type: "+type);





                    } else {

                        Toast.makeText(ViewEWalletTransactionsActivity.this, "Reward Game Category could not be loaded. Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
                    }



                }
                else{

                    itemView = convertView;


                    //get the search text views
                    TextView textViewEWalletTransactionLayoutDate = (TextView) itemView.findViewById(R.id.textViewEWalletTransactionLayoutDate);
                    TextView textViewEWalletTransactionLayoutType = (TextView)itemView.findViewById(R.id.textViewEWalletTransactionLayoutType);
                    final TextView textViewEWalletTransactionLayoutAmount = (TextView)itemView.findViewById(R.id.textViewEWalletTransactionLayoutAmount);
                    TextView textViewEWalletTransactionLayoutDescription = (TextView) itemView.findViewById(R.id.textViewEWalletTransactionLayoutDescription);


                    if (eWalletTransactionJsonList.size() > 0) {//if there are ewallet transactions to load

                        String desciption = ""+ eWalletTransactionJsonList.get(position).get("Narration");
                        String date = ""+ eWalletTransactionJsonList.get(position).get("DateOfTransaction");
                        Double debit = Double.parseDouble(""+ eWalletTransactionJsonList.get(position).get("Debit"));
                        Double credit = Double.parseDouble(""+ eWalletTransactionJsonList.get(position).get("Credit"));
                        String type = "";

                        if(credit > 0){

                            type  = "Credit";

                            textViewEWalletTransactionLayoutAmount.setText("Amount: N"+String.format("%,.2f", credit));

                        }

                        if(debit > 0){

                            type = "Debit";

                            textViewEWalletTransactionLayoutAmount.setText("Amount: N"+String.format("%,.2f", debit));

                        }


                        textViewEWalletTransactionLayoutDate.setText("Date: "+date);
                        textViewEWalletTransactionLayoutDescription.setText("Narration: "+desciption);
                        textViewEWalletTransactionLayoutType.setText("Type: "+type);



                    } else {

                        Toast.makeText(ViewEWalletTransactionsActivity.this, "Reward Game Category could not be loaded. Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
                    }

                }


                textViewEWalletTransactionsEWalletBalance.setText("CURRENT EWALLET BALANCE: N"+ String.format("%,.2f", eWalletCurrentBalance));



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
