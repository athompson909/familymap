package base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

import com.bignerdranch.android.familymap3.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import adapters.ExpandableListAdapter;
import model.Event;
import model.Person;
import model.SettingsInfo;
import model.UserInfo;
import utils.NavUtils;

/**
 * Created by athom909 on 3/31/16.
 */
public class PersonActivity extends AppCompatActivity {

    private Person person;

    TextView firstNameTextView;
    TextView lastNameTextView;
    TextView genderTextView;

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<String> headers;
    Map<String, List<String>> headerLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Family Map: Person Details");

        person = UserInfo.getInstance().getCurrentPerson();

        firstNameTextView = (TextView) findViewById(R.id.firstNameTextView);
        lastNameTextView = (TextView) findViewById(R.id.lastNameTextView);
        genderTextView = (TextView) findViewById(R.id.genderTextView);

        firstNameTextView.setText(person.getFirstName());
        lastNameTextView.setText(person.getLastName());
        genderTextView.setText(person.getGenderAsString());

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        setListData();
        expandableListAdapter = new ExpandableListAdapter(this, headers, headerLists);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                expandableListAdapter.onChildClick(groupPosition, childPosition);

                return false;
            }
        });
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

    private void setListData() {
        headers = new ArrayList<>();
        headerLists = new HashMap<>();

        headers.add("LIFE EVENTS");
        headers.add("FAMILY");

        UserInfo inst = UserInfo.getInstance();

        headerLists.put("LIFE EVENTS", new ArrayList<String>());
        TreeMap<Integer, String> eventsInOrder = new TreeMap();
        for(Event e : inst.getPersonEvents().get(person.getPersonId())) {
            eventsInOrder.put(e.getYear(), e.getEventId());
        }
        for(TreeMap.Entry<Integer, String> entry : eventsInOrder.entrySet()) {
            headerLists.get("LIFE EVENTS").add(entry.getValue());
        }

        headerLists.put("FAMILY", new ArrayList<String>());
        if(person.getSpouse() != null)
            headerLists.get("FAMILY").add(person.getSpouse());
        if(person.getFather() != null)
            headerLists.get("FAMILY").add(person.getFather());
        if(person.getMother() != null)
            headerLists.get("FAMILY").add(person.getMother());
        if(person.getChild() != null)
            headerLists.get("FAMILY").add(person.getChild());


    }



    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
