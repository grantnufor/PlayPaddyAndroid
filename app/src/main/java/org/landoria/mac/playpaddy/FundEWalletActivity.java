package org.landoria.mac.playpaddy;

import HttpAdapter.CompanyAccountHttpServiceAdapter;
import HttpAdapter.EWalletHttpServiceAdapter;
import HttpAdapter.EWalletTransactionHttpServiceAdapter;
import HttpAdapter.UserHttpServiceAdapter;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;
import pl.droidsonroids.gif.GifImageView;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FundEWalletActivity extends AppCompatActivity {


    EWalletHttpServiceAdapter eWalletHttpServiceAdapter = new EWalletHttpServiceAdapter();
    EWalletTransactionHttpServiceAdapter eWalletTransactionHttpServiceAdapter = new EWalletTransactionHttpServiceAdapter();
    UserHttpServiceAdapter userHttpServiceAdapter = new UserHttpServiceAdapter();
    CompanyAccountHttpServiceAdapter companyAccountHttpServiceAdapter = new CompanyAccountHttpServiceAdapter();



    EditText editTextFundEwalletEmailAddress;
    EditText editTextFundEwalletAmount;
    EditText editTextFundEwalletCardNumber;
    EditText editTextFundEwalletCVC;

    LinearLayout linearLayoutLoadingPayment;
    TextView textViewLoadingPayment;
    GifImageView imageViewLoadingPayment;



   // Spinner spinnerFundEwalletCardExpirationDateDay;
    Spinner spinnerFundEwalletCardExpirationDateMonth;
    Spinner spinnerFundEwalletCardExpirationDateYear;

    Button  buttonFundEWalletPay;

    ArrayAdapter<CharSequence> adapterCardExpirationMonth;
    ArrayAdapter<CharSequence> adapterCardExpirationYear;


    String userId = "";
    String userName = "";

    Double actualAmount = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_ewallet);




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
            AlertDialog.Builder builder = new AlertDialog.Builder(FundEWalletActivity.this);
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



        PaystackSdk.initialize(getApplicationContext());



        editTextFundEwalletEmailAddress = (EditText)findViewById(R.id.editTextFundEwalletEmailAddress);
        editTextFundEwalletAmount = (EditText) findViewById(R.id.editTextFundEwalletAmount);
        editTextFundEwalletCardNumber = (EditText) findViewById(R.id.editTextFundEwalletCardNumber);
        editTextFundEwalletCVC = (EditText) findViewById(R.id.editTextFundEwalletCVC);

        linearLayoutLoadingPayment = (LinearLayout)findViewById(R.id.linearLayoutLoadingPayment);
        imageViewLoadingPayment = (GifImageView)findViewById(R.id.imageViewLoadingPayment);
        textViewLoadingPayment = (TextView)findViewById(R.id.textViewLoadingPayment);

        //spinnerFundEwalletCardExpirationDateDay = (Spinner)findViewById(R.id.spinnerFundEwalletCardExpirationDateDay);
        spinnerFundEwalletCardExpirationDateMonth = (Spinner)findViewById(R.id.spinnerFundEwalletCardExpirationDateMonth);
        adapterCardExpirationMonth = ArrayAdapter.createFromResource(FundEWalletActivity.this, R.array.month_array, android.R.layout.simple_spinner_item);
        adapterCardExpirationMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFundEwalletCardExpirationDateMonth.setAdapter(adapterCardExpirationMonth);

        spinnerFundEwalletCardExpirationDateYear = (Spinner)findViewById(R.id.spinnerFundEwalletCardExpirationDateYear);
        adapterCardExpirationYear = ArrayAdapter.createFromResource(FundEWalletActivity.this, R.array.year_array, android.R.layout.simple_spinner_item);
        adapterCardExpirationYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFundEwalletCardExpirationDateYear.setAdapter(adapterCardExpirationYear);


        buttonFundEWalletPay = (Button)findViewById(R.id.buttonFundEWalletPay);
        buttonFundEWalletPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                pay();

            }
        });

    }


    Card card;
    int amount = 0;
    String emailAddress= "";

    public  void pay(){


        // This sets up the card and check for validity
        // This is a test card from paystack

        String cardNumber = "0";
        int expiryMonth = 0; //any month in the future
        int expiryYear = 0; // any year in the future. '2018' would work also!
        String cvv = "0";  // cvv of the test card





        if(editTextFundEwalletEmailAddress.getText().equals("")){

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("PlayPaddy");
            builder.setMessage("You need to enter your Email Address (Your Receipt will be sent to your Email Address.)");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // You don't have to do anything here if you just
                    // want it dismissed when clicked
                }
            });
            builder.show();
            return;
        }
        else{
            emailAddress = editTextFundEwalletEmailAddress.getText()+"";
        }


        if(editTextFundEwalletAmount.getText().equals("")){
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("PlayPaddy");
            builder.setMessage("You need to enter an amount");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // You don't have to do anything here if you just
                    // want it dismissed when clicked
                }
            });
            builder.show();
            return;
        }
        else{
            amount = Integer.parseInt( editTextFundEwalletAmount.getText()+"00");
            actualAmount = Double.parseDouble( editTextFundEwalletAmount.getText().toString());
        }


        if(editTextFundEwalletCardNumber.getText().equals("")){
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("PlayPaddy");
            builder.setMessage("You need to enter your card number");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // You don't have to do anything here if you just
                    // want it dismissed when clicked
                }
            });
            builder.show();
            return;
        }
        else {
            cardNumber = editTextFundEwalletCardNumber.getText()+"";
        }


        //month
        if (spinnerFundEwalletCardExpirationDateMonth.getSelectedItem().toString().equals("Select Month")) {

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("PlayPaddy");
            builder.setMessage("You need to select Card Expiration Month!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // You don't have to do anything here if you just
                    // want it dismissed when clicked
                }
            });
            builder.show();

            Toast.makeText(this, "You need to select Card Expiration Month!" , Toast.LENGTH_LONG).show();
            return;
        }
        else {
            expiryMonth = Integer.parseInt(spinnerFundEwalletCardExpirationDateMonth.getSelectedItem()+"");

        }
        //year
        if (spinnerFundEwalletCardExpirationDateYear.getSelectedItem().toString().equals("Select Year")) {

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(FundEWalletActivity.this);
            builder.setTitle("EduJunior");
            builder.setMessage("You need to select Card Expiration Year!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // You don't have to do anything here if you just
                    // want it dismissed when clicked
                }
            });
            builder.show();

            Toast.makeText(this, "You need to select Card Expiration Year!" , Toast.LENGTH_LONG).show();
            return;
        }
        else {
            expiryYear = Integer.parseInt(spinnerFundEwalletCardExpirationDateYear.getSelectedItem()+"");

        }



        if(editTextFundEwalletCVC.getText().equals("")){
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(FundEWalletActivity.this);
            builder.setTitle("PlayPaddy");
            builder.setMessage("You need to enter your card cvc");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // You don't have to do anything here if you just
                    // want it dismissed when clicked
                }
            });
            builder.show();
            return;
        }
        else{
            cvv =  editTextFundEwalletCVC.getText()+"";
        }


        card = new Card(cardNumber, expiryMonth, expiryYear, cvv);
        if (card.isValid()) {
            // charge card
            performCharge();

            linearLayoutLoadingPayment.setVisibility(View.VISIBLE);
            textViewLoadingPayment.setVisibility(View.VISIBLE);
            imageViewLoadingPayment.setVisibility(View.VISIBLE);

        } else {
            //show error message
            AlertDialog.Builder builder = new AlertDialog.Builder(FundEWalletActivity.this);
            builder.setTitle("PlayPaddy");
            builder.setMessage("Card is invalid. Please use another card or contact your bank.");
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



    //double confimedAmount = 0.00;

    // This is the subroutine you will call after creating the charge
    // adding a card and setting the access_code
    public void performCharge(){
        //create a Charge object
        Charge charge = new Charge();
        charge.setCard(card); //sets the card to charge
        charge.setAmount(amount);//sets the amount to charge
        charge.setEmail(emailAddress);

        PaystackSdk.chargeCard(FundEWalletActivity.this, charge, new Paystack.TransactionCallback() {
            @Override
            public void onSuccess(Transaction transaction) {
                // This is called only after transaction is deemed successful.
                // Retrieve the transaction, and send its reference to your server
                // for verification.
                //fund the users ewallet


                creditEwallet();

                linearLayoutLoadingPayment.setVisibility(View.INVISIBLE);
                textViewLoadingPayment.setVisibility(View.INVISIBLE);
                imageViewLoadingPayment.setVisibility(View.INVISIBLE);





            }

            @Override
            public void beforeValidate(Transaction transaction) {
                // This is called only before requesting OTP.
                // Save reference so you may send to server. If
                // error occurs with OTP, you should still verify on server.
            }

            @Override
            public void onError(Throwable error, Transaction transaction) {
                //handle error here
                AlertDialog.Builder builder = new AlertDialog.Builder(FundEWalletActivity.this);
                builder.setTitle("PlayPaddy");
                builder.setMessage(error.getMessage());
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // You don't have to do anything here if you just
                        // want it dismissed when clicked
                    }
                });
                builder.show();


                linearLayoutLoadingPayment.setVisibility(View.INVISIBLE);
                textViewLoadingPayment.setVisibility(View.INVISIBLE);
                imageViewLoadingPayment.setVisibility(View.INVISIBLE);
            }

        });
    }


    JSONObject jsonEwalletObj = new JSONObject();
    JSONObject jsonEwalletTransactionObj = new JSONObject();
    JSONObject jsonCompanyObj = new JSONObject();

    private void creditEwallet(){

        try {

            //setting up the error dialog
            final AlertDialog.Builder alert = new AlertDialog.Builder( FundEWalletActivity.this, R.style.AppTheme);
            alert.setTitle("Error!!!");

            //running the time consuming tasks in a seperate thread
            AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
                ProgressDialog dialog = new ProgressDialog(FundEWalletActivity.this);


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

                        //getting the ewallet for this user
                        jsonEwalletObj = eWalletHttpServiceAdapter.GetEwalletByUserId(userId);

                        if(!jsonEwalletObj.isNull("EwalletId")) {
                            //getting the balance and crediting the user with the new balance
                            String ewalletId = jsonEwalletObj.getString("EwalletId");

                            String pattern = "MM/dd/yyyy HH:mm:ss";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("fr", "FR"));
                            String date = simpleDateFormat.format(new Date());

                            double ewalletBalance = jsonEwalletObj.getDouble("CurrentBalance");

                            double newEwalletBalance = ewalletBalance + actualAmount;

                            if(newEwalletBalance > 0){

                                eWalletHttpServiceAdapter.UpdateEwallet(ewalletId, userId, "0.00", "0.00", newEwalletBalance+"", "0000", date, date);

                                eWalletTransactionHttpServiceAdapter.AddEwalletTransaction(ewalletId, actualAmount+"", "0.00", "EWallet Funding", newEwalletBalance+"", userId, "0", "Success", date);
                            }
                        }
                        else{// if user does not have ewallet, create one for him

                           //

                            String pattern = "MM/dd/yyyy";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("fr", "FR"));
                            String date = simpleDateFormat.format(new Date());

                            double ewalletBalance = jsonEwalletObj.getDouble("CurrentBalance");

                            double newEwalletBalance = ewalletBalance + actualAmount;

                            if(newEwalletBalance > 0){

                                eWalletHttpServiceAdapter.AddEwallet( userId, "0.00", "0.00", newEwalletBalance+"", "0000", date, date);


                                JSONObject jsonEwalletObjForNew = eWalletHttpServiceAdapter.GetEwalletByUserId(userId);

                                if(jsonEwalletObjForNew.isNull("EwalletId")) {

                                    String ewalletIdForNew = jsonEwalletObjForNew.getString("EwalletId");

                                    eWalletTransactionHttpServiceAdapter.AddEwalletTransaction(ewalletIdForNew, actualAmount + "", "0.00", "EWallet Funding", newEwalletBalance + "", userId, "0", "Success", date);
                                }
                            }
                        }



                        //also check company balance and credit the company account
                        jsonCompanyObj = companyAccountHttpServiceAdapter.GetCompanyAccountByCompanyAccountId("1");

                       // if(!jsonCompanyObj.isNull("CompanyAccountId")){//if company account already exists

                            String pattern = "MM/dd/yyyy";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("fr", "FR"));
                            String date = simpleDateFormat.format(new Date());

                            String companyAccountId = jsonCompanyObj.isNull("CompanyAccountId")+"";

                            double companyBalance = Double.parseDouble( jsonCompanyObj.isNull("CompanyBalance")+"");
                            double newCompanyBalance = companyBalance + actualAmount;
                            companyAccountHttpServiceAdapter.AddCompanyAccount(userId, "EWallet Funding", actualAmount+"", "0.00", newCompanyBalance+"", date);

//
//                        }
//                        else{
//
//                            String pattern = "MM/dd/yyyy";
//                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("fr", "FR"));
//                            String date = simpleDateFormat.format(new Date());
//
//                           // String companyAccountId = jsonCompanyObj.isNull("CompanyAccountId")+"";
//
//                            double companyBalance = Double.parseDouble( jsonCompanyObj.isNull("CompanyBalance")+"");
//                            double newCompanyBalance = companyBalance + confimedAmount;
//                            companyAccountHttpServiceAdapter.AddCompanyAccount(userId, "EWallet Funding", confimedAmount+"", "0.00", newCompanyBalance+"", date);
//
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


//                            // Use the Builder class for convenient dialog construction
//                            AlertDialog.Builder builder = new AlertDialog.Builder(FundEWalletActivity.this);
//                            builder.setTitle("PlayPaddy");
//                            builder.setMessage("Your EWallet was funded successfuly.");
//                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    // You don't have to do anything here if you just
//                                    // want it dismissed when clicked
//                                }
//                            });
//                            builder.show();
//
//                            return;

                        Intent intent = new Intent(FundEWalletActivity.this, EWalletFundingConfirmationActivity.class);
                        intent.putExtra("userid", userId);
                        intent.putExtra("actualamount", actualAmount.toString());
                        intent.putExtra("username", userName);
                        startActivity(intent);
                        finishAffinity();


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
            Toast.makeText(FundEWalletActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
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
