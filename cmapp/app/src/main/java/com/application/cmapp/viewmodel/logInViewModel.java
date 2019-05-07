package com.application.cmapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.application.cmapp.model.Reading;
import com.application.cmapp.repository.Repository;

public class logInViewModel extends ViewModel {

    private final Repository repository = Repository.getInstance();


    @NonNull
    public MutableLiveData<String> getLoginLiveData(String userEmail, String userPass)
    {
        return repository.getAdminLogin(userEmail,userPass);
    }

    public MutableLiveData<String> getAdminIsLoggedInCheck()
    {
        return repository.AdminIsLoggedInCheck();
    }

    public MutableLiveData<String>  AdminSignOut()
    {
        return repository.AdminSignOut();
    }

}
