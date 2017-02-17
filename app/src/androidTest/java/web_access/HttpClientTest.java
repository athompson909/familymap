package web_access;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import model.Login;

/**
 * Created by athom909 on 4/12/16.
 */
public class HttpClientTest extends TestCase {


    public void test() {

        HttpClient httpClient = new HttpClient();

        Login.setUsername("adam");
        Login.setPassword("adam");

        try {
            String response =  httpClient.postUrl(new URL("http://192.168.2.154/8080/user/login"));
            assertNotSame("{\n\t\"message\":\"User name or password is wrong\"\n}", response);

            JSONObject jsonObject = new JSONObject(response);
            assertNotSame("{\n\t\"message\":\"Not authenticated access token\"\n}",
                    httpClient.getUrl(new URL("http://192.168.2.154/8080/user/login"),
                            jsonObject.getString("Authorization")));

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}