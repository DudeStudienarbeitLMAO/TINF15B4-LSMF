package com.example.fabian.tinf15b4_lsmf.apis;

import android.os.StrictMode;

import com.example.fabian.tinf15b4_lsmf.modells.SSLTool;
import com.example.fabian.tinf15b4_lsmf.modells.SsapiResult;
import com.example.fabian.tinf15b4_lsmf.modells.User;
import com.omertron.themoviedbapi.model.movie.MovieInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class Ssapi {

    // TODO: Move network activity to new thread -- in the meantime use this work-around
    static {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    //test
    private String baseaddress = "https://cduc.su/";
    private HashMap<Integer, String> statusCodes = new HashMap<Integer, String>();
    public String statusMessage = "";

    public Ssapi() {
        JSONArray jsonArray = null;
        System.out.println(Locale.getDefault().getDisplayLanguage());
        switch (Locale.getDefault().getDisplayLanguage()) {
            case "Deutsch":

                try {
                    jsonArray = new JSONArray(sendWebRequest(baseaddress + "statuscode.php?language=de"));

                } catch (Exception ex) {
                }
                break;
            case "English":
                try {
                    jsonArray = new JSONArray(sendWebRequest(baseaddress + "statuscode.php?language=en"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    jsonArray = new JSONArray(sendWebRequest(baseaddress + "statuscode.php?language=de"));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
        }

        try {

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currentObject = jsonArray.getJSONObject(i);
                Iterator<?> keys = currentObject.keys();
                while (keys.hasNext()) {
                    int key = Integer.parseInt((String) keys.next());
                    String value = (String) currentObject.get(String.valueOf(key));
                    statusCodes.put(key, value);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean testConnection(User user) {
        String method = String.format("SSAPI.php?method=isAuthorized&user=%s&password=%s", user.getUserName(), user.getPasswordHash());
        String response = "";
        boolean testSuccess = false;
        response = sendWebRequest(baseaddress + method);
        if (response.isEmpty()) {
            return false;
        }
        int statuscode = Integer.parseInt(response.substring(1, 4));
        switch (statuscode) {
            case 200:
                testSuccess = true;
                break;
            case 400:
            case 410:
            case 401:
            case 402:
            default:
                testSuccess = false;
                break;
        }
        statusMessage = statusCodes.get(statuscode);
        return testSuccess;
    }

    public SsapiResult registerUser(User user) {
        String method = "SSAPI.php?method=register&user=" + user.getUserName() + "&password=" + user.getPasswordHash() + "&params=[%22" + user.getEMail() + "%22]";

        SsapiResult result = fetchFromDB(baseaddress + method);
        return result;
    }

    public boolean resetPassword(String email) {

        return false;
    }

    private SsapiResult fetchFromDB(String url) {
        SsapiResult result = null;
        try {
            JSONArray jsa = new JSONArray(sendWebRequest(url));
            if (jsa == null) {
                return result;
            }

            int code = Integer.parseInt(jsa.get(0).toString());
            statusMessage = statusCodes.get(code);

            result = new SsapiResult(jsa, code, statusMessage);

        } catch (Exception ex) {

        }

        return result;
    }

    private String sendWebRequest(String url) {
        // TODO: REMOVE B4 PRODUCTION, DUDE
        SSLTool.disableCertificateValidation();
        URL api;
        BufferedReader in;
        String response = "";
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }

    public SsapiResult getLikedMovies(User user) {
        String method = "SSAPI.php?method=getMovies&user=" + user.getUserName() + "&password=" + user.getPasswordHash();

        SsapiResult result = fetchFromDB(baseaddress + method);
        return result;
    }

    public SsapiResult insertLikedMovie(User user, MovieInfo movie) {
        String method = "SSAPI.php?method=likeMovie&user=" + user.getUserName() + "&password=" + user.getPasswordHash() + "&params=[" + movie.getId() + "]";

        SsapiResult result = fetchFromDB(baseaddress + method);
        return result;

    }

    public SsapiResult removeLikedMovie(User user, MovieInfo movie) {
        String method = "SSAPI.php?method=removeMovie&user=" + user.getUserName() + "&password=" + user.getPasswordHash() + "&params=[" + movie.getId() + "]";

        SsapiResult result = fetchFromDB(baseaddress + method);
        return result;
    }

}