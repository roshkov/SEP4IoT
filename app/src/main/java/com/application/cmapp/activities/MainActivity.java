package com.application.cmapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.application.cmapp.R;
import com.application.cmapp.model.Reading;
import com.application.cmapp.viewmodel.ReadingViewModel;
import com.application.cmapp.viewmodel.logInViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    TextView temperature, deviceNo, sound, co2, humidity, datetime;
    //Reading reading;
    Button button;
    Button loginButton;
    TextView email;
    Button adminButton;

    private ReadingViewModel viewModel;
    public static logInViewModel loginVM;   //to be changed later!
    public static MutableLiveData<String> readingLoginLiveData;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Quick connection no Async task for quick testing
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temperature = findViewById(R.id.temperature);
        deviceNo = findViewById(R.id.devicenumber);
        sound = findViewById(R.id.sound);
        humidity = findViewById(R.id.humidity);
        co2 = findViewById(R.id.co2);
        datetime = findViewById(R.id.datetime);
        loginButton = findViewById(R.id.loginBtn);
        email = findViewById(R.id.adminEmail);
        adminButton = findViewById(R.id.ButtonForAdmins);

        button = findViewById(R.id.login2);
        temperature.setText("Temperature: ");

        viewModel = ViewModelProviders.of(this).get(ReadingViewModel.class);
        loginVM = ViewModelProviders.of(this).get(logInViewModel.class);




        //getReadingSensor
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Get reading
                    viewModel.getReading("http://10.152.194.103:8080/readings");
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });


        //Make this activity observe the mutable data.
        MutableLiveData<Reading> readingLiveData = viewModel.getReadingLiveData();

        readingLiveData.observe(this, new Observer<Reading>()
        {
            public void onChanged(@Nullable Reading reading)
            {
                //Update current reading
                updateCurrentReading(reading);
            }
        });










        ///////////////////////////////////////////////////////////////////////test login2


       readingLoginLiveData = loginVM.getAdminIsLoggedInCheck(); //check if logged in





        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                readingLoginLiveData = loginVM.getLoginLiveData("libworker1@gmail.com", "libworker1");

            }
        });






        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, ActivityLoginAdmin.class);
                startActivity(intent);


            }
        });



        readingLoginLiveData.observe(this, new Observer<String>()
        {
            public void onChanged( String s)
            {
                email.setText(s);
                Log.i("__________observer", readingLoginLiveData.getValue()+ " - this is the LD value");

                if (email.getText().toString().equals("Anonymous user"))
                {
                    adminButton.setVisibility(View.GONE);
                }

                else {
                    adminButton.setVisibility(View.VISIBLE);
                }




            }
        });




    }


    protected void onResume(Bundle savedInstanceState) {
        readingLoginLiveData = loginVM.getAdminIsLoggedInCheck();
    }



    public void updateCurrentReading(Reading reading)
    {
        //this.reading = reading;
        temperature.setText(String.valueOf(reading.getTemperature()));
        humidity.setText(String.valueOf(reading.getHumidity()));
        datetime.setText(reading.getDateTime());
        co2.setText(String.valueOf(reading.getCo2()));
        sound.setText(String.valueOf(reading.getSound()));
        deviceNo.setText(String.valueOf(reading.getDeviceNo()));

    }

    //  MutableLiveData<String> readingSignOutLiveData = loginVM.AdminSignOut();
//        readingSignOutLiveData.observe(this, new Observer<String>()
//        {
//            public void onChanged( String s)
//            {
//                email.setText(s);
//            }
//        });


}
