package com.example.fabian.tinf15b4_lsmf.activities;

import android.content.Intent;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.app.ProgressDialog;

import com.example.fabian.tinf15b4_lsmf.R;
import com.example.fabian.tinf15b4_lsmf.Ssapi;
import com.example.fabian.tinf15b4_lsmf.User;

public class register extends AppCompatActivity {

    EditText txt_username;
    EditText txt_email;
    EditText txt_password;
    EditText txt_passwordconfirm;
    AppCompatButton btn_register;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txt_username = (EditText) findViewById(R.id.input_name);
        txt_email = (EditText) findViewById(R.id.input_email);
        txt_password = (EditText) findViewById(R.id.input_password);
        txt_passwordconfirm = (EditText) findViewById(R.id.input_passwordconfirm);
        btn_register = (AppCompatButton) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnregisterclick();
            }
        });

    }

    public void navToLogin(View view) {
        Intent i = new Intent(getApplicationContext(), login.class);
        startActivity(i);
        finish();
    }

    public void btnregisterclick() {
        Ssapi ssAPI = new Ssapi();
        if (!isValidData()) {
            Toast.makeText(getBaseContext(), getResources().getString(R.string.registerfailed), Toast.LENGTH_LONG).show();
            return;
        }

        btn_register.setEnabled(false);

        final ProgressDialog ringProgressDialog = ProgressDialog.show(register.this, this.getResources().getString(R.string.pleasewait), this.getResources().getString(R.string.sendregister), true);

        String name = txt_username.getText().toString();
        String email = txt_email.getText().toString();
        String password = txt_password.getText().toString();

        User newUser = new User(name, login.encryptPasswordSHA512(password), email);
        ssAPI.registerUser(newUser);

        Toast.makeText(this, ssAPI.statusMessage, Toast.LENGTH_LONG).show();

        btn_register.setEnabled(true);
        ringProgressDialog.dismiss();

    }



    public boolean isValidData() {
        String name = txt_username.getText().toString();
        String email = txt_email.getText().toString();
        String password = txt_password.getText().toString();
        String passwordconfirm = txt_passwordconfirm.getText().toString();

        if (name.isEmpty() || name.length() < 6) {
            txt_username.setError(getResources().getString(R.string.notenoughchars));
            return false;
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txt_email.setError(getResources().getString(R.string.invalidemail));
            return false;
        }
        if (password.isEmpty() || password.length() < 6) {
            txt_password.setError(getResources().getString(R.string.notenoughchars));
            return false;
        }

        if (passwordconfirm.isEmpty() || !password.equals(passwordconfirm)) {
            txt_password.setError(getResources().getString(R.string.invalidconfirm));
            return false;
        }

        return true;
    }


}
