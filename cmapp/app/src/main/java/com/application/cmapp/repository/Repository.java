package com.application.cmapp.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.application.cmapp.firebase.FirebaseClient;
import com.application.cmapp.network.Adapter;
import com.application.cmapp.model.Reading;
import com.application.cmapp.network.WebAPIClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;

import java.net.MalformedURLException;

public class Repository {

    private static Repository instance = new Repository();
    private MutableLiveData<Reading> liveReadingData = new MutableLiveData<Reading>();
    private final WebAPIClient client = new WebAPIClient();
    private final Adapter adapter = new Adapter();
    private WebAPIClient.ReadingAsyncTask task;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = mAuth.getCurrentUser(); // may be null if admin is not logged in
    private MutableLiveData<String> liveDataLogin = new MutableLiveData<String>();
    public boolean reply;
    private FirebaseClient fbClient = new FirebaseClient();

    private Repository() {

    }


    public static Repository getInstance() {
        return instance;
    }


    //API communication
    public void getReading(String requestUrl) throws MalformedURLException {
        task = client.new ReadingAsyncTask() {
            @Override
            public void onResponseReceived(String jsonResult) throws JSONException {
                //Successful response posts adapted value inside the live data.
                liveReadingData.postValue(adapter.makeReading(jsonResult));
            }

            @Override
            public void onFailed() {
                //Default values if response failed.
                liveReadingData.postValue(new Reading(999, 999, 999, 999, 999, ""));
            }
        };
        //Execute the task.
        task.execute(requestUrl);
    }

    public MutableLiveData<Reading> getLiveReadingData() {
        return liveReadingData;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////


    public void AdminLogin(final String userEmail, String userPass)  //changes LD
    {

        liveDataLogin.postValue(fbClient.AdminLogin(userEmail, userPass));

    }

//

//    public void AdminLogin (final String userEmail, String userPass)  //changes LD
//    {
//
//        mAuth.signInWithEmailAndPassword(userEmail, userPass)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        //progressBar.setVisibility(View.GONE);
//                        if(task.isSuccessful()){
//                            liveDataLogin.postValue(userEmail);  //if user+pass = correct then just put userEmail entered to live data
//
//                        }
//                        else{
//                            liveDataLogin.postValue("Anonymous user");
//                        }
//                    }
//                });
//
//    }
//
    public MutableLiveData<String> AdminIsLoggedInCheck()
    {

        liveDataLogin.postValue(fbClient.AdminIsLoggedInCheck());

        return liveDataLogin;

    }


    public MutableLiveData<String> AdminSignOut()
    {
        Log.i("repoSignout=====", "reaches");
        fbClient.AdminSignOut();
        liveDataLogin.postValue(fbClient.AdminIsLoggedInCheck());

        return liveDataLogin;

    }


    public MutableLiveData<String> getAdminLogin(String userEmail, String userPass)
    {
        Log.i("Repo_getAdminLogin===", "reaches");
            AdminLogin( userEmail,  userPass);   //calls LD change
            return liveDataLogin;                 //return LD
    }

}
