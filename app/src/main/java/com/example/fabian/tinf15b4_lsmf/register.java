package com.example.fabian.tinf15b4_lsmf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void navToLogin(View view)
    {
        Intent i = new Intent(getApplicationContext(), login.class);
        startActivity(i);

    }
}
