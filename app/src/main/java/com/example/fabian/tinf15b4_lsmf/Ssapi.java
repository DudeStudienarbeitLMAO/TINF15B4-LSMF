package com.example.fabian.tinf15b4_lsmf;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.*;
import java.net.*;
import java.io.*;

import org.json.JSONObject;
public class Ssapi {

    private static String baseaddress = "192.168.2.154/";
    private static HashMap<Integer, String> statusCodes  = new HashMap<Integer, String>();

    static{
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
    public static boolean testConnection(User user){

        return true;
    }

    public static boolean addMovie(User user, Movie movie){
        user.addMovie(movie);

        // webrequest an db

        return false;
    }

    public static boolean registerUser(User user){
        // TODO
        return false;
    }

    public static ArrayList<Movie> fetchMovielist(User user){
        // TODO
        return null;
    }

    public static boolean resetPassword(String email){

        return false;
    }

    private static String sendWebRequest(String url){
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

        in.close();
       }catch(Exception ex){}
        return response;
    }
}
