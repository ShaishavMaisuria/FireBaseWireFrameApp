package com.example.firebasewireframeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener , CreateNewAccount.NewAccountListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.rootView,new LoginFragment())
                .commit();


    }

    @Override
    public void OnSuccesfulLogin() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView,new Forums())
                .commit();
    }

    @Override
    public void createNewAccountToLogin() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView,new LoginFragment())
                .commit();
    }

    @Override
    public void loginToCreateNewAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView,new CreateNewAccount())
                .commit();
    }
}