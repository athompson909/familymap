package base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.bignerdranch.android.familymap3.R;

import model.Filters;
import model.SettingsInfo;
import utils.NavUtils;

/**
 * Created by athom909 on 4/7/16.
 */
public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Family Map: Filters");
        setToggleButtons();
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

    private void setToggleButtons() {

        ToggleButton baptismEventsSwitch = (ToggleButton) findViewById(R.id.baptismEventsSwitch);
        baptismEventsSwitch.setChecked(Filters.getInstance().isBaptismEventsOn());
        baptismEventsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) Filters.getInstance().setBaptismEventsOn(true);
                else Filters.getInstance().setBaptismEventsOn(false);
            }
        });

        ToggleButton birthEventsSwitch = (ToggleButton) findViewById(R.id.birthEventsSwitch);
        birthEventsSwitch.setChecked(Filters.getInstance().isBirthEventsOn());
        birthEventsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) Filters.getInstance().setBirthEventsOn(true);
                else Filters.getInstance().setBirthEventsOn(false);
            }
        });

        ToggleButton censusEventsSwitch = (ToggleButton) findViewById(R.id.censusEventsSwitch);
        censusEventsSwitch.setChecked(Filters.getInstance().isCensusEventsOn());
        censusEventsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) Filters.getInstance().setCensusEventsOn(true);
                else Filters.getInstance().setCensusEventsOn(false);
            }
        });

        ToggleButton christeningEventsSwitch = (ToggleButton)
                findViewById(R.id.christeningEventsSwitch);
        christeningEventsSwitch.setChecked(Filters.getInstance().isChristeningEventsOn());
        christeningEventsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) Filters.getInstance().setChristeningEventsOn(true);
                else Filters.getInstance().setChristeningEventsOn(false);
            }
        });

        ToggleButton deathEventsSwitch = (ToggleButton) findViewById(R.id.deathEventsSwitch);
        deathEventsSwitch.setChecked(Filters.getInstance().isDeathEventsOn());
        deathEventsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) Filters.getInstance().setDeathEventsOn(true);
                else Filters.getInstance().setDeathEventsOn(false);
            }
        });

        ToggleButton marriageEventsSwitch = (ToggleButton) findViewById(R.id.marriageEventsSwitch);
        marriageEventsSwitch.setChecked(Filters.getInstance().isMarriageEventsOn());
        marriageEventsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) Filters.getInstance().setMarriageEventsOn(true);
                else Filters.getInstance().setMarriageEventsOn(false);
            }
        });

        ToggleButton fathersSideSwitch = (ToggleButton) findViewById(R.id.fathersSideSwitch);
        fathersSideSwitch.setChecked(Filters.getInstance().isFathersSideOn());
        fathersSideSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) Filters.getInstance().setFathersSideOn(true);
                else Filters.getInstance().setFathersSideOn(false);
            }
        });

        ToggleButton mothersSideSwitch = (ToggleButton) findViewById(R.id.mothersSideSwitch);
        mothersSideSwitch.setChecked(Filters.getInstance().isMothersSideOn());
        mothersSideSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) Filters.getInstance().setMothersSideOn(true);
                else Filters.getInstance().setMothersSideOn(false);
            }
        });

        ToggleButton maleEventsSwitch = (ToggleButton) findViewById(R.id.maleEventsSwitch);
        maleEventsSwitch.setChecked(Filters.getInstance().isMaleEventsOn());
        maleEventsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) Filters.getInstance().setMaleEventsOn(true);
                else Filters.getInstance().setMaleEventsOn(false);
            }
        });

        ToggleButton femaleEventsSwitch = (ToggleButton) findViewById(R.id.femaleEventsSwitch);
        femaleEventsSwitch.setChecked(Filters.getInstance().isFemaleEventsOn());
        femaleEventsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) Filters.getInstance().setFemaleEventsOn(true);
                else Filters.getInstance().setFemaleEventsOn(false);
            }
        });

    }
}
