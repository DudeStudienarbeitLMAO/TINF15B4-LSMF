package com.example.fabian.tinf15b4_lsmf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.content.Intent;
import android.app.Activity;
import android.widget.*;

import java.security.MessageDigest;

public class login extends AppCompatActivity {

    EditText txt_name, txt_password;
    AppCompatButton btn_login;
    TextView lbl_reset;

    int failedLoginAttempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_name = (EditText) findViewById(R.id.input_username);

        txt_password = (EditText) findViewById(R.id.input_password);

        btn_login  = (AppCompatButton) findViewById(R.id.btn_login);
        lbl_reset = (TextView) findViewById(R.id.link_resetpw);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });


        lbl_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

    }

    public void navToRegister(View view)
    {
        Intent i = new Intent(getApplicationContext(), register.class);
        startActivity(i);

    }


    public void login(){

        String username = txt_name.getText().toString();
        String password = txt_password.getText().toString();

        // implement hashing
        String hash = password;

        User user = new User(username, hash, "");

        if(!Ssapi.testConnection(user)){
            failedLoginAttempts++;
            Toast.makeText(this, getResources().getString(R.string.loginfail), Toast.LENGTH_LONG).show();

        }else{

            // redirect to main activity
        }


        if(failedLoginAttempts>=3){
            lbl_reset.setVisibility(View.VISIBLE);
        }

    }

    public void resetPassword(){
        //redirect to reset password form
    }


}
