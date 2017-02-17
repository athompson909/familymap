package base;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.bignerdranch.android.familymap3.R;

import model.Person;
import model.SettingsInfo;
import model.UserInfo;
import utils.NavUtils;

/**
 * So the fragment needs to be added to the activity's code (not the layout)
 */
public class MainActivity extends AppCompatActivity {

    private MenuItem settingsIcon;
    private MenuItem searchIcon;
    private MenuItem filterIcon;

    private static LoginFragment mLoginFragment;
    private static MyMapFragment mMyMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(SettingsInfo.getInstance().isLoggedOut()) {
            mLoginFragment = new LoginFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.frameLayout, mLoginFragment).commit();
        }
    }

    private void showLogoutFragment() {
        mLoginFragment = new LoginFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, mLoginFragment).commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        settingsIcon.setVisible(false);
        filterIcon.setVisible(false);
        searchIcon.setVisible(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(SettingsInfo.getInstance().reSyncData()) {
            SettingsInfo.getInstance().setContext(this);
            UserInfo.clear();
            mLoginFragment.reSync();
        }
        else if(SettingsInfo.getInstance().returnToMainActivity()) {
            SettingsInfo.getInstance().setReturnToMainActivity(false);
            UserInfo.setCurrentPerson(null);
            UserInfo.setSelectedEventId(null);
        }
    }

    /**
     * I'm debating whether this is the right place to mess with action bar stuff...
     */
    public void startMapFragment() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        settingsIcon.setVisible(true);
        filterIcon.setVisible(true);
        searchIcon.setVisible(true);

        FragmentManager fm = getSupportFragmentManager();
        mMyMapFragment = new MyMapFragment();
        fm.beginTransaction().replace(R.id.frameLayout, mMyMapFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        settingsIcon = menu.findItem(R.id.settingsIcon);
        settingsIcon.setIcon(NavUtils.getSettingsIcon(this));
        filterIcon = menu.findItem(R.id.filterIcon);
        filterIcon.setIcon(NavUtils.getFilterIcont(this));
        searchIcon = menu.findItem(R.id.searchIcon);
        searchIcon.setIcon(NavUtils.getSearchIcon(this));

        settingsIcon.setVisible(false);
        filterIcon.setVisible(false);
        searchIcon.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.settingsIcon:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.filterIcon:
                Intent filterIntent = new Intent(this, FilterActivity.class);
                startActivity(filterIntent);
                return true;
            case R.id.searchIcon:
                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);
                return true;
            case android.R.id.home:
                SettingsInfo.getInstance().setLoggedOut(true);
                UserInfo.clear();
                showLogoutFragment();
                Person p = UserInfo.getCurrentPerson();
                return true;
            default: assert false;
        }

        return false;
    }
}