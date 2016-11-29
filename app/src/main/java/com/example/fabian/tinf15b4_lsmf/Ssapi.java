package com.example.fabian.tinf15b4_lsmf;

import android.os.StrictMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
import java.net.*;
import java.io.*;
import org.json.JSONObject;

public class Ssapi {

    // TODO: Move network activity to new thread -- in the meantime use this work-around
    static{
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private  String baseaddress = "https://cduc.su/SSAPI.php?";
    private  HashMap<Integer, String> statusCodes  = new HashMap<Integer, String>();
    public String statusMessage = "";
    public Ssapi(){
        JSONObject jsonObject = null;
        switch (Locale.getDefault().getDisplayLanguage()){
            case "de":

                try {
                    jsonObject = new JSONObject(sendWebRequest(baseaddress + "statuscode.php/language=de"));

                }catch(Exception ex){}
                break;
            case "en":
                break;
            default:
                try {
                    jsonObject = new JSONObject(sendWebRequest(baseaddress + "statuscode.php/language=de"));

                }catch(Exception ex){}
                break;
        }
        try {
            Iterator<?> keys = jsonObject.keys();

            while (keys.hasNext()) {
                int key = Integer.parseInt((String) keys.next());
                String value = (String) jsonObject.get(String.valueOf(key));

                statusCodes.put(key, value);
            }
        }catch(Exception ex){}
    }

    public boolean testConnection(User user){

        return true;
    }

    public boolean addMovie(User user, Movie movie){
        user.addMovie(movie);

        // webrequest an db

        return false;
    }

    public boolean registerUser(User user){
        // TODO
        return false;
    }

    public ArrayList<Movie> fetchMovielist(User user){
        // TODO
        return null;
    }

    public boolean resetPassword(String email){

        return false;
    }

    private String sendWebRequest(String url){
        // TODO: REMOVE B4 PRODUCTION, DUDE
        SSLTool.disableCertificateValidation();
        URL api;
        BufferedReader in;
        String response="";
        try {
            api = new URL(url);

            URLConnection yc = api.openConnection();
            in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null)
                response += inputLine;

                in.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return response;
    }
}