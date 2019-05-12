package com.application.cmapp.activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.application.cmapp.R;
import com.application.cmapp.activities.MainActivity;
import com.application.cmapp.model.Admin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.application.cmapp.activities.MainActivity.loginVM;
import static com.application.cmapp.activities.MainActivity.readingLoginLiveData;

//implements LoaderCallbacks<Cursor>
public class ActivityLoginAdmin extends AppCompatActivity {

    Toolbar toolbar;
    ProgressBar progressBar;
    EditText userEmail;
    EditText userPass;
    Button userLogin;
    Button userSignOut;
 //   MainActivity activityReferenceVarMain;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        //toolbar = findViewById(R.id.toolbar1);
       // progressBar = findViewById(R.id.login_progress1);
        userEmail = findViewById(R.id.username);
        userPass = findViewById(R.id.password);
        userLogin = findViewById(R.id.login);
        userSignOut = findViewById(R.id.signOutBtn);





        userLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                loginMethod();
                finish();

            }

        });


        userSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loginVM.AdminSignOut();
                signOutMethod();
                finish();


            }
        });

        //setUpIfLoggedIn();

    }

//
//    public void setUpIfLoggedIn()
//    {
//        Log.i("________value", loginVM.getAdminIsLoggedInCheck().getValue());
//
//        String mail = loginVM.getAdminIsLoggedInCheck().getValue();
//        if (! (mail.equals("Anonymous user")))
//        {
//            userEmail.setText(mail);
//            userPass.setVisibility(View.INVISIBLE);
//            userLogin.setVisibility(View.INVISIBLE);
//        }
//
//
//    }

    public void loginMethod()
    {
        loginVM.getLoginLiveData(userEmail.getText().toString(), userPass.getText().toString());
    }

    public void signOutMethod()
    {
        loginVM.AdminSignOut();

        loginVM.getAdminIsLoggedInCheck();
        Log.i("__________SignOut",  loginVM.AdminSignOut().getValue());
    }

}



