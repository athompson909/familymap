package model;

//import java.util.UUID;

/**
 * Created by athom909 on 3/17/16.
 * this is now a singleton with all static fields (why would you even need getInstance()???)
 */
public class Login {

    public static Login instance = null;

    public static Login getInstance() {
        return instance;
    }

    public static void setInstance(String username, String password, String serverHost,
                                   String serverPort) {
        instance = new Login(username, password, serverHost, serverPort);
    }

    private static String username;
    private static String password;
    private static String serverHost;
    private static String serverPort;

    private static boolean isValidLogin;

    private Login() {
    }

    private Login(String username, String password, String serverHost, String serverPort) {
        this.username = username;
        this.password = password;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public static void setInstance(Login instance) {
        Login.instance = instance;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Login.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Login.password = password;
    }

    public static String getServerHost() {
        return serverHost;
    }

    public static void setServerHost(String serverHost) {
        Login.serverHost = serverHost;
    }

    public static String getServerPort() {
        return serverPort;
    }

    public static void setServerPort(String serverPort) {
        Login.serverPort = serverPort;
    }

    public static boolean isValidLogin() {
        return isValidLogin;
    }

    public static void setIsValidLogin(boolean isValidLogin) {
        Login.isValidLogin = isValidLogin;
    }

    /*    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public boolean isValidLogin() {
        return isValidLogin;
    }

    public void setIsValidLogin(boolean isValidLogin) {
        this.isValidLogin = isValidLogin;
    }*/

}
