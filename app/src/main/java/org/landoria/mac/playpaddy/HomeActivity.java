package org.landoria.mac.playpaddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {




    Button buttonSignUp;
    Button buttonSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        buttonSignIn = (Button)findViewById(R.id.buttonSignIn);
        buttonSignUp = (Button)findViewById(R.id.buttonSignUp);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //opening the sign in activity
                Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
                startActivity(intent);


            }
        });



        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //opening the sign up activity
                Intent intent = new Intent(HomeActivity.this, SignUpActivity.class);
                startActivity(intent);


            }
        });



    }
}
