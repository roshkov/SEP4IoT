package com.application.cmapp.firebase;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseClient {


    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = mAuth.getCurrentUser(); // may be null if admin is not logged in
    public String loginReply = "Anonymous user0";



    public String AdminLogin (final String userEmail, String userPass)  //changes LD
    {

        mAuth.signInWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            loginReply = userEmail;
                            //return userEmail;  //if user+pass = correct then just put userEmail entered to live data

                        } else {
                            loginReply = "Anonymous user";
                        }
                    }
                });
                Log.i("loginReply====",loginReply+"");
                return loginReply;
    }





    @SuppressLint("LongLogTag")
    public String AdminIsLoggedInCheck()
    {
        if(firebaseUser != null){
            return (firebaseUser.getEmail());
        }
        else{
            return "Anonymous user";

        }

    }

    public void AdminSignOut()
    {
        mAuth.signOut();
    }


}
