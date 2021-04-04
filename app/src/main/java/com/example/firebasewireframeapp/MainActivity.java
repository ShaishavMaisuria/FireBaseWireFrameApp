package com.example.firebasewireframeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener , CreateNewAccount.NewAccountListener, Forums.ForumsListener , NewForum.NewForumListener {

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

    @Override
    public void logoutFromForums() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView,new LoginFragment())
                .commit();
    }

    @Override
    public void ForumtoNewForums() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView,new NewForum())
                .commit();
    }

    @Override
    public void ForumToEntireForum() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView,new EntireForumFragment())
                .commit();
    }

    @Override
    public void OnSuccesfulForumCreation() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView,new Forums())
                .commit();
    }
}