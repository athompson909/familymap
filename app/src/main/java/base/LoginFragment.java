package base;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bignerdranch.android.familymap3.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import model.Event;
import model.Login;
import model.Person;
import model.SettingsInfo;
import model.UserInfo;
import web_access.HttpClient;

/**
 * Created by athom909 on 3/17/16.
 */
public class LoginFragment extends Fragment {

    View v;

    //private TextView titleTextView;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText serverHostEditText;
    private EditText serverPortEditText;
    private Button button;

    boolean loggedIn;
    String authorizationToken;
    String personId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loggedIn = false;
        authorizationToken = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_login, container, false);

        button = (Button)v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked();
            }

        });
        return v;
    }

    public void reSync() {
        startAsyncTasks();
    }

    /**
     * starts the Login Async Task
     */
    private void onButtonClicked() {

        usernameEditText = (EditText)v.findViewById(R.id.usernameEditText);
        passwordEditText = (EditText)v.findViewById(R.id.passwordEditText);
        serverHostEditText = (EditText)v.findViewById(R.id.serverHostEditText);
        serverPortEditText = (EditText)v.findViewById(R.id.serverPortEditText);
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String serverHost = serverHostEditText.getText().toString();
        String serverPort = serverPortEditText.getText().toString();
        Login.setInstance(username, password, serverHost, serverPort);

        startAsyncTasks();
    }

    private void startAsyncTasks() {

        try {

            LoginTask task = new LoginTask();
            task.execute(new URL("http://" + Login.getServerHost() + ":" + Login.getServerPort() +
                    "/user/login"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public LoginTask newLoginTask() {
        return new LoginTask();
    }

    public UserInfoTask newUserInfoTask() {
        return new UserInfoTask();
    }



    public class LoginTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {

            HttpClient httpClient = new HttpClient();
            return parseJSON(null, httpClient.postUrl(urls[0]));
        }

        private String parseJSON(String str, String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.has("message")) {
                    str = jsonObject.getString("message");
                }
                else if(jsonObject.has("Authorization")) {
                    authorizationToken = jsonObject.getString("Authorization");
                    personId = jsonObject.getString("personId");
                    loggedIn = true;
                    SettingsInfo.getInstance().setLoggedOut(false);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return str;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null)
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
            else if(loggedIn) {
                SettingsInfo.getInstance().setLoggedOut(false);
                UserInfoTask userInfoTask = new UserInfoTask();
                try {
                    userInfoTask.execute(new URL("http://" + Login.getServerHost() + ":" +
                            Login.getServerPort() + "/person/"));
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class UserInfoTask extends AsyncTask<URL, Void, String> {

        private URL url;

        @Override
        protected String doInBackground(URL... urls) {

            HttpClient httpClient = new HttpClient();
            url = urls[0];
            String response = httpClient.getUrl(url, authorizationToken);
            return parseJSON(null, response);
        }

        private String parseJSON(String str, String response) {
            try {

                JSONArray jsonArray = new JSONObject(response).getJSONArray("data");
                for(int i = 0; i < jsonArray.length(); ++i) {

                    if(jsonArray.getJSONObject(i).getString("personID").equals(personId)) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Person p = new Person(jsonObject);
                        str = p.getFullName() + " successfully logged in";
                        UserInfo.getInstance().setUser(p);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return str;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(SettingsInfo.getInstance().reSyncData()) {
                Toast.makeText(SettingsInfo.getInstance().getContext(),
                        "Data successfully resynced", Toast.LENGTH_LONG).show();
            }
            else if(s != null)
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();

            // then populate the model singleton
            populateModel();
        }

        private void populateModel() {
            try {
                PopulatorTask populatorTask = new PopulatorTask();
                populatorTask.execute(url, new URL("http://" + Login.getServerHost() + ":" +
                        Login.getServerPort() + "/event/"));
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
    }

    public class PopulatorTask extends AsyncTask<URL, Void, Void> {

        /**
         * this populates the database
         * @param urls
         * @return null (Void)
         */
        @Override
        protected Void doInBackground(URL... urls) {

            HttpClient httpClient = new HttpClient();

            try {

                String response = httpClient.getUrl(urls[0], authorizationToken);
                JSONArray jsonArray = new JSONObject(response).getJSONArray("data");
                for(int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject data = jsonArray.getJSONObject(i);
                    Person p = new Person(data);
                    UserInfo.getInstance().addPeople(p);
                }

                response = httpClient.getUrl(urls[1], authorizationToken);
                jsonArray = new JSONObject(response).getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject data = jsonArray.getJSONObject(i);
                    Event e = new Event(data);
                    UserInfo.getInstance().addEvents(e);
                    UserInfo.getInstance().addPersonEvents(e);
                    UserInfo.getInstance().getPerson(e.getPersonId()).addToPersonEvents(e);
                }
                // if the previous loop was entered in {
                UserInfo.getInstance().setPeoplesEventCoordinateLists();
                UserInfo.getInstance().setFamilyTree();

                UserInfo.getInstance().setUserEvents();



            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            MainActivity ma;
            if(SettingsInfo.getInstance().reSyncData()) {
                ma = (MainActivity) SettingsInfo.getInstance().getContext();
                SettingsInfo.getInstance().setReSync(false);
            }
            else
                ma = (MainActivity) getActivity();
            ma.startMapFragment();
        }
    }

}