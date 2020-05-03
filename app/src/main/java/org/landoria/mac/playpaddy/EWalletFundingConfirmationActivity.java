package org.landoria.mac.playpaddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EWalletFundingConfirmationActivity extends AppCompatActivity {




    TextView textViewEWalletFundingConfirmation;
    Button buttonEWalletFundingConfirmationDone;

    String amount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ewallet_funding_confirmation);


        Intent intent = getIntent();
        amount = intent.getStringExtra("amount");


        showBackButton();


        //showing activity name and app icon on action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logomainwhite);
//        getSupportActionBar().setTitle(sessionDb.getSessionBySessionId("1").get("NAME").toString());
        getSupportActionBar().setDisplayUseLogoEnabled(true);


//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#800000")));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006400")));



        textViewEWalletFundingConfirmation = (TextView)findViewById(R.id.textViewEWalletFundingConfirmation);
        textViewEWalletFundingConfirmation.setText(amount+ "has been credited into your EWallet. You can now enrol and pay for games from your EWallet and make some money. Best wishes!");


        buttonEWalletFundingConfirmationDone = (Button)findViewById(R.id.buttonEWalletFundingConfirmationDone);

        buttonEWalletFundingConfirmationDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(EWalletFundingConfirmationActivity.this, UserHomeMainActivity.class);
                startActivity(intent);
                finish();

            }
        });

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
