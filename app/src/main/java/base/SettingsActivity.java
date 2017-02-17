package base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.bignerdranch.android.familymap3.R;

import model.SettingsInfo;
import model.UserInfo;

/**
 * Created by athom909 on 4/7/16.
 */
public class SettingsActivity extends AppCompatActivity implements OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Family Map: Settings");
        setSpinners();
        setToggleButtons();
        setTextViewListeners();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    /**
     * changes color parameters in settings singleton based on user selections
     * @param adapterView
     * @param view
     * @param i says which spinner it is 1 = lslSpinner, 2 = ftlSpinner, 3 = slSpinner, 4 = mapType
     * @param l same as i
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = (String) adapterView.getItemAtPosition(i);
        switch (adapterView.getId()) {
            case R.id.lslSpinner:
                SettingsInfo.getInstance().setLifeStoryLinesColor(item); break;
            case R.id.ftlSpinner:
                SettingsInfo.getInstance().setFamilyTreeLinesColor(item); break;
            case R.id.slSpinner:
                SettingsInfo.getInstance().setSpouseLinesColor(item); break;
            case R.id.mapTypeSpinner:
                SettingsInfo.getInstance().setMapType(item); break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setSpinners() {

        Spinner lifeStoryLinesSpinner = (Spinner) findViewById(R.id.lslSpinner);
        ArrayAdapter<CharSequence> lslAdapter = ArrayAdapter.createFromResource(this,
                R.array.colors_array, android.R.layout.simple_spinner_dropdown_item);
        lslAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lifeStoryLinesSpinner.setAdapter(lslAdapter);
        lifeStoryLinesSpinner.setOnItemSelectedListener(this);
        lifeStoryLinesSpinner.setSelection(SettingsInfo.getInstance().getLslPosition());

        Spinner familyTreeLinesSpinner = (Spinner) findViewById(R.id.ftlSpinner);
        ArrayAdapter<CharSequence> ftlAdapter = ArrayAdapter.createFromResource(this,
                R.array.colors_array, android.R.layout.simple_spinner_dropdown_item);
        ftlAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        familyTreeLinesSpinner.setAdapter(ftlAdapter);
        familyTreeLinesSpinner.setOnItemSelectedListener(this);
        familyTreeLinesSpinner.setSelection(SettingsInfo.getInstance().getFtlPosition());

        Spinner spouseLinesSpinner = (Spinner) findViewById(R.id.slSpinner);
        ArrayAdapter<CharSequence> slAdapter = ArrayAdapter.createFromResource(this,
                R.array.colors_array, android.R.layout.simple_spinner_dropdown_item);
        slAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spouseLinesSpinner.setAdapter(slAdapter);
        spouseLinesSpinner.setOnItemSelectedListener(this);
        spouseLinesSpinner.setSelection(SettingsInfo.getInstance().getSlPosition());

        Spinner mapTypeSpinner = (Spinner) findViewById(R.id.mapTypeSpinner);
        ArrayAdapter<CharSequence> mtAdapter = ArrayAdapter.createFromResource(this,
                R.array.map_types_array, android.R.layout.simple_spinner_dropdown_item);
        mapTypeSpinner.setAdapter(mtAdapter);
        mapTypeSpinner.setOnItemSelectedListener(this);
        mapTypeSpinner.setSelection(SettingsInfo.getInstance().getMtPosition());
    }

    private void setToggleButtons() {

        ToggleButton lslToggleButton = (ToggleButton) findViewById(R.id.lslSwitch);
        lslToggleButton.setChecked(SettingsInfo.getInstance().isLifeStoryLinesOn());
        lslToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) SettingsInfo.getInstance().setLifeStoryLinesOn(true);
                else SettingsInfo.getInstance().setLifeStoryLinesOn(false);
            }
        });

        ToggleButton ftlToggleButton = (ToggleButton) findViewById(R.id.ftlSwitch);
        ftlToggleButton.setChecked(SettingsInfo.getInstance().isFamilyTreeLinesOn());
        ftlToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) SettingsInfo.getInstance().setFamilyTreeLinesOn(true);
                else SettingsInfo.getInstance().setFamilyTreeLinesOn(false);
            }
        });


        ToggleButton slToggleButton = (ToggleButton) findViewById(R.id.slSwitch);
        slToggleButton.setChecked(SettingsInfo.getInstance().isSpouseLinesOn());
        slToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) SettingsInfo.getInstance().setSpouseLinesOn(true);
                else SettingsInfo.getInstance().setSpouseLinesOn(false);
            }
        });
    }

    private void setTextViewListeners() {

        RelativeLayout rsDataLayout = (RelativeLayout) findViewById(R.id.resyncDataSquare);
        rsDataLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsInfo.getInstance().setReSync(true);
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        RelativeLayout logoutLayout = (RelativeLayout) findViewById(R.id.logoutSquare);
        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsInfo.getInstance().setLoggedOut(true);
                UserInfo.clear();
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
