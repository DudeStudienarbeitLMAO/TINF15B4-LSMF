package com.example.fabian.tinf15b4_lsmf.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fabian.tinf15b4_lsmf.R;
import com.example.fabian.tinf15b4_lsmf.apis.Ssapi;
import com.example.fabian.tinf15b4_lsmf.modells.User;

import java.io.IOException;
import java.security.MessageDigest;

public class LoginActivity extends AppCompatActivity {

  private  EditText txt_name, txt_password;
    private  AppCompatButton btn_login;
    private    TextView lbl_reset, lbl_help;
    private CheckBox cbremember;
    private  int failedLoginAttempts = 0;
    SharedPreferences prefs;
    Ssapi Ssapi = new Ssapi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_login);

        txt_name = (EditText) findViewById(R.id.input_username);
        lbl_help = (TextView) findViewById(R.id.link_help);
        txt_password = (EditText) findViewById(R.id.input_password);

        btn_login = (AppCompatButton) findViewById(R.id.btn_login);
        lbl_reset = (TextView) findViewById(R.id.link_resetpw);
        cbremember = (CheckBox) findViewById(R.id.cb_remember);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    login();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        lbl_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });


        lbl_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(k);
            }
        });

        if(prefs.getString("username", "")!=""){
            User loggedInUser = new User(prefs.getString("username", ""), prefs.getString("userhash", ""), "");
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.putExtra("currentUser", loggedInUser);
            startActivity(i);
            finish();
        }

    }

    public void navToRegister(View view) {
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
        finish();

    }


    public void login() throws IOException {

        String username = txt_name.getText().toString();
        String password = txt_password.getText().toString();

        // implement hashing
        String hash = encryptPasswordSHA512(password);


        User user = new User(username, hash, "");

        if (!Ssapi.testConnection(user)) {
            failedLoginAttempts++;
            Toast.makeText(this, getResources().getString(R.string.loginfail), Toast.LENGTH_LONG).show();

        } else {

            if(cbremember.isChecked()) {

                SharedPreferences.Editor e = prefs.edit();
                e.putString("username", user.getUserName());
                e.putString("userhash", user.getPasswordHash());
                e.commit();
            }

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.putExtra("currentUser", user);
            startActivity(i);
            finish();
            // redirect to main activity
        }


        if (failedLoginAttempts >= 3) {
            lbl_reset.setVisibility(View.VISIBLE);
        }

    }

    public void resetPassword() {
        //redirect to reset password form
    }


    public static String encryptPasswordSHA512(String password) {
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (Exception ex) {

        }
        md.update(password.getBytes());

        byte byteData[] = md.digest();

//Convert "byteData" to hex String:
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
