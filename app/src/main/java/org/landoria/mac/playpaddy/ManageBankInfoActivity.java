package org.landoria.mac.playpaddy;

import HttpAdapter.UserBankHttpServiceAdapter;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ManageBankInfoActivity extends AppCompatActivity {



    UserBankHttpServiceAdapter userBankHttpServiceAdapter = new UserBankHttpServiceAdapter();


    Spinner spinnerManageBankAccountInfoBank;
    EditText editTextManageBankInfoAccountName;
    EditText editTextManageBankInfoAccountNumber;
    Button buttonManageBankInfoSave;
    Button buttonManageBankInfoCancel;


    String userId = "";
    String userName = "";


    ArrayAdapter<CharSequence> adapterBankName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bank_info);


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



         spinnerManageBankAccountInfoBank = (Spinner) findViewById(R.id.spinnerManageBankAccountInfoBank);
         editTextManageBankInfoAccountName = (EditText) findViewById(R.id.editTextManageBankInfoAccountName);
         editTextManageBankInfoAccountNumber = (EditText)findViewById(R.id.editTextManageBankInfoAccountNumber);
         buttonManageBankInfoSave = (Button)findViewById(R.id.buttonManageBankInfoSave);
         buttonManageBankInfoSave.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 saveUserBankInfo();
             }
         });


         buttonManageBankInfoCancel = (Button)findViewById(R.id.buttonManageBankInfoCancel);
         buttonManageBankInfoCancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 finish();

             }
         });




        spinnerManageBankAccountInfoBank = (Spinner) findViewById(R.id.spinnerManageBankAccountInfoBank);
        adapterBankName = ArrayAdapter.createFromResource(ManageBankInfoActivity.this, R.array.banks_array, android.R.layout.simple_spinner_item);
        adapterBankName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerManageBankAccountInfoBank.setAdapter(adapterBankName);



        loadUserBankInfo();


    }




    JSONObject userBankJsonObj = new JSONObject();

    private void loadUserBankInfo(){

        try {

            //setting up the error dialog
            final AlertDialog.Builder alert = new AlertDialog.Builder( ManageBankInfoActivity.this, R.style.AppTheme);
            alert.setTitle("Error!!!");

            //running the time consuming tasks in a seperate thread
            AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
                ProgressDialog dialog = new ProgressDialog(ManageBankInfoActivity.this);


                int errorCode = 0;
                String errorMessage = "";


                @Override
                protected void onPreExecute() {
                    // what to do before background task
                    dialog.setTitle("Loading your bank information...");
                    dialog.setMessage("Please wait.");
                    dialog.setIndeterminate(true);
                    dialog.setCancelable(false);
                    dialog.show();
                }

                @Override
                protected Void doInBackground(Void... params) {


                    try {

                        //getting user bank information
                        userBankJsonObj = userBankHttpServiceAdapter.GetUserBankByUserId(userId);

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

                        if(!userBankJsonObj.isNull("UserBankId")) {

                            try {

                                String userBankId = userBankJsonObj.getString("UserBankId");

                                String pattern = "MM/dd/yyyy";
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("fr", "FR"));
                                String date = simpleDateFormat.format(new Date());

                                String bankName = userBankJsonObj.getString("BankName");
                                String accountName = userBankJsonObj.getString("AccountName");
                                String accountNo = userBankJsonObj.getString("AccountNo");


                                int spinnerBankPosition = adapterBankName.getPosition(bankName);

                                spinnerManageBankAccountInfoBank.setSelection(spinnerBankPosition);
                                editTextManageBankInfoAccountName.setText(accountName);
                                editTextManageBankInfoAccountNumber.setText(accountNo);

                            }
                            catch(Exception ex){

                                // Use the Builder class for convenient dialog construction
                                AlertDialog.Builder builder = new AlertDialog.Builder(ManageBankInfoActivity.this);
                                builder.setTitle("PlayPaddy");
                                builder.setMessage("You do not have any bank account information on our system yet. Please enter your bank details so we can pay you when you win.");
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
                        else{// if user does not have any bank information on this system

                            // Use the Builder class for convenient dialog construction
                            AlertDialog.Builder builder = new AlertDialog.Builder(ManageBankInfoActivity.this);
                            builder.setTitle("PlayPaddy");
                            builder.setMessage("You do not have any bank account information on our system yet. Please enter your bank details so we can pay you when you win.");
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
            Toast.makeText(ManageBankInfoActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            //ex.printStackTrace();
        }

    }



    JSONObject userBankJsonObjSave = new JSONObject();

    String pattern = "MM/dd/yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("fr", "FR"));
    String dateSave = simpleDateFormat.format(new Date());

    String bankNameSave = "";
    String accountNameSave = "";
    String accountNoSave = "";


    String returnValue = "";

    private void saveUserBankInfo(){

        try {


            if(!spinnerManageBankAccountInfoBank.getSelectedItem().toString().equals("Select a Bank")){

                bankNameSave = spinnerManageBankAccountInfoBank.getSelectedItem().toString();

            }
            else{

                // Use the Builder class for convenient dialog construction
                AlertDialog.Builder builder = new AlertDialog.Builder(ManageBankInfoActivity.this);
                builder.setTitle("PlayPaddy");
                builder.setMessage("You need to select a bank");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // You don't have to do anything here if you just
                        // want it dismissed when clicked
                    }
                });
                builder.show();
                return;
            }


            if(!editTextManageBankInfoAccountName.getText().equals("")){


                accountNameSave = editTextManageBankInfoAccountName.getText().toString();

            }
            else{

                // Use the Builder class for convenient dialog construction
                AlertDialog.Builder builder = new AlertDialog.Builder(ManageBankInfoActivity.this);
                builder.setTitle("PlayPaddy");
                builder.setMessage("You need to enter your Account Name.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // You don't have to do anything here if you just
                        // want it dismissed when clicked
                    }
                });
                builder.show();
                return;

            }



            if(!editTextManageBankInfoAccountNumber.getText().equals("")){

                accountNoSave = editTextManageBankInfoAccountNumber.getText().toString();

            }
            else{
                // Use the Builder class for convenient dialog construction
                AlertDialog.Builder builder = new AlertDialog.Builder(ManageBankInfoActivity.this);
                builder.setTitle("PlayPaddy");
                builder.setMessage("You need to enter your Account Number.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // You don't have to do anything here if you just
                        // want it dismissed when clicked
                    }
                });
                builder.show();
                return;
            }





            //setting up the error dialog
            final AlertDialog.Builder alert = new AlertDialog.Builder( ManageBankInfoActivity.this, R.style.AppTheme);
            alert.setTitle("Error!!!");

            //running the time consuming tasks in a seperate thread
            AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
                ProgressDialog dialog = new ProgressDialog(ManageBankInfoActivity.this);


                int errorCode = 0;
                String errorMessage = "";


                @Override
                protected void onPreExecute() {
                    // what to do before background task
                    dialog.setTitle("Saving...");
                    dialog.setMessage("Please wait.");
                    dialog.setIndeterminate(true);
                    dialog.setCancelable(false);
                    dialog.show();
                }

                @Override
                protected Void doInBackground(Void... params) {


                    try {

                        //checking if user bank information is available
                        userBankJsonObjSave = userBankHttpServiceAdapter.GetUserBankByUserId(userId);


                        if(!userBankJsonObjSave.isNull("UserBankId")){//if the user has a bank aacount info, then update

                            String userBankId = userBankJsonObjSave.getString("UserBankId");

                            //updating user bank information
                            returnValue = userBankHttpServiceAdapter.UpdateUserBank(userBankId, bankNameSave, accountNameSave, accountNoSave,
                                    "", dateSave, dateSave, userId);

                        }
                        else{

                            //adding user bank information
                            returnValue = userBankHttpServiceAdapter.AddUserBank( bankNameSave, accountNameSave, accountNoSave,
                                    "", dateSave, dateSave, userId);

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


                            try {


                                if(returnValue.equals("1")){

                                    // Use the Builder class for convenient dialog construction
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ManageBankInfoActivity.this);
                                    builder.setTitle("PlayPaddy");
                                    builder.setMessage("Save was successful.");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // You don't have to do anything here if you just
                                            // want it dismissed when clicked
                                        }
                                    });
                                    builder.show();


                                    finish();

                                }
                                else{

                                    // Use the Builder class for convenient dialog construction
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ManageBankInfoActivity.this);
                                    builder.setTitle("PlayPaddy");
                                    builder.setMessage("Save was not successful. Please try again.");
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
                            catch(Exception ex){

                                // Use the Builder class for convenient dialog construction
                                AlertDialog.Builder builder = new AlertDialog.Builder(ManageBankInfoActivity.this);
                                builder.setTitle("PlayPaddy");
                                builder.setMessage("You do not have any bank account information on our system yet. Please enter your bank details so we can pay you when you win.");
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
            Toast.makeText(ManageBankInfoActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            //ex.printStackTrace();
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
