package web_access;

import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import model.Login;

/**
 * Created by athom909 on 3/18/16.
 */
public class HttpClient {

    public String postUrl(URL url) {
        String postData = "{ username:\"" + Login.getUsername() + "\", " +
            "password:\"" + Login.getPassword() + "\" }";

        String str;

        try {
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.connect();

            OutputStream requestBody = connection.getOutputStream();
            requestBody.write(postData.getBytes());
            requestBody.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream responseBody = connection.getInputStream();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                str = baos.toString();
            }
            else {
                // SERVER RETURNED AN HTTP ERROR
                str = "HTTP ERROR";
            }
        }
        catch (IOException e) {
            Log.e("IOException error: ", "figure out how this happened!");
            e.printStackTrace();
            str = "ERROR";
            // IO ERROR
        }
        catch (Exception e) {
            Log.e("Error: ", "figure out how this happened!");
            e.printStackTrace();
            str = "ERROR";
        }

        return str;
    }

    public String getUrl(URL url, String authorizationToken) {

        String str;

        try {
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Authorization", authorizationToken);
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream responseBody = connection.getInputStream();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                str = baos.toString();
            }
            else {
                str = "HTTP ERROR";
            }
        }
        catch (IOException e) {
            str = "IOException error";
            e.printStackTrace();
        }

        return str;
    }
}
