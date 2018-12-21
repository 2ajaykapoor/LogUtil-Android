package com.squareoneinsights.logutil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class WebUtil {

    public static final String REQUEST_METHOD = "POST";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    //public static final String URL = "https://reqres.in/api/users";

    public static void LogActivity(String strURL, String httpRequest) {

        try {
            //Create a URL object holding our url
            URL myUrl = new URL(strURL);

            //Create a connection
            HttpURLConnection connection = (HttpURLConnection)
                    myUrl.openConnection();

            //Set methods and timeouts
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            try {
                writer.write(httpRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }

            writer.flush();
            writer.close();
            os.close();
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(
                    connection.getInputStream()));

            StringBuffer sb = new StringBuffer("");
            String line = "";

            while ((line = in.readLine()) != null) {

                sb.append(line);
                break;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void LogActivityTest() {

        String strURL = "http://localhost:9000/api/v1/activity-engine/activity";

        try {
            //Create a URL object holding our url
            URL myUrl = new URL(strURL);

            //Create a connection
            HttpURLConnection connection = (HttpURLConnection)
                    myUrl.openConnection();

            JSONObject postDataParams = new JSONObject();
            try {
                postDataParams.put("userId", "morpheus");
                postDataParams.put("activityName", "leader");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            //Set methods and timeouts
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            try {
                writer.write(getPostDataString(postDataParams));
            } catch (Exception e) {
                e.printStackTrace();
            }

            writer.flush();
            writer.close();
            os.close();
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(
                    connection.getInputStream()));

            StringBuffer sb = new StringBuffer("");
            String line = "";

            while ((line = in.readLine()) != null) {

                sb.append(line);
                break;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
}

