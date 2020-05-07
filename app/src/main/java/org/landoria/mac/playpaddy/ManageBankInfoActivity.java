package org.landoria.mac.playpaddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ManageBankInfoActivity extends AppCompatActivity {




    Spinner spinnerManageBankAccountInfoBank;
    EditText editTextManageBankInfoAccountName;
    EditText editTextManageBankInfoAccountNumber;
    Button buttonManageBankInfoSave;
    Button buttonManageBankInfoCancel;


    ArrayAdapter<CharSequence> adapterBankName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bank_info);


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
         buttonManageBankInfoCancel = (Button)findViewById(R.id.buttonManageBankInfoSave);




        spinnerManageBankAccountInfoBank = (Spinner) findViewById(R.id.spinnerManageBankAccountInfoBank);
        adapterBankName = ArrayAdapter.createFromResource(ManageBankInfoActivity.this, R.array.banks_array, android.R.layout.simple_spinner_item);
        adapterBankName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerManageBankAccountInfoBank.setAdapter(adapterBankName);


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
