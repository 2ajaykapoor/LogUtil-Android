package com.squareoneinsights.logutil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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
}

