package com.example.autovista.authentication;

import com.example.autovista.GlobalManager;
import com.example.autovista.models.user.User;

public class AuthenticationHandler {
    public AuthenticationHandler()
    {
        GlobalManager.Instance.setAuthenticationHandler(this);
    }

    public void OnLogin(User user)
    {
        GlobalManager.Instance.getFirebaseAuthentication().logIn(user);
    }
    public void OnRegister(User user)
    {
        GlobalManager.Instance.getFirebaseAuthentication().register(user);
    }
}
