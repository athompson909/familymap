package base;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.bignerdranch.android.familymap3.R;

import model.SettingsInfo;
import utils.NavUtils;

/**
 * Created by athom909 on 3/30/16.
 */
public class MapActivity extends AppCompatActivity {

    private MyMapFragment mMyMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mMyMapFragment = new MyMapFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frameLayout, mMyMapFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(SettingsInfo.getInstance().returnToMainActivity())
            finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.double_up_menu, menu);
        MenuItem doubleUpIcon = menu.findItem(R.id.upArrow);
        doubleUpIcon.setIcon(NavUtils.getDoubleUpIcon(this));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.upArrow:
                SettingsInfo.getInstance().setReturnToMainActivity(true);
                finish();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default: assert false;
        }

        return false;
    }
}