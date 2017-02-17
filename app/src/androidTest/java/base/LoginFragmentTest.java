package base;

import junit.framework.TestCase;

import java.net.MalformedURLException;
import java.net.URL;

import model.Login;

/**
 * Created by athom909 on 4/12/16.
 */
public class LoginFragmentTest extends TestCase {

    public void loginTest() {

        LoginFragment loginFragment = new LoginFragment();

        try {
            LoginFragment.LoginTask task = loginFragment.newLoginTask();
            assertEquals("Adam Thompson logged in successfully",
                    task.execute(new URL("http://192.168.2.154/8080/user/login")));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}