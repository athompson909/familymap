package base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.bignerdranch.android.familymap3.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import adapters.PersonRVAdapter;
import model.Event;
import model.Person;
import model.SearchObject;
import model.SettingsInfo;
import model.UserInfo;

/**
 * Created by athom909 on 4/7/16.
 */
public class SearchActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<SearchObject> peopleQueryResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Family Map: Search");


        EditText editText = (EditText) findViewById(R.id.query);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString();
                search(query);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.personRV);

        mLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(SettingsInfo.getInstance().returnToMainActivity())
            finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.double_up_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            SettingsInfo.getInstance().setReturnToMainActivity(true);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void search(String query) {
        peopleQueryResults.clear();
        Map<String, Person> peopleMap = UserInfo.getInstance().getPeople();
        for (Map.Entry<String, Person> entry : peopleMap.entrySet()) {
            if (entry.getValue().getFullName().toLowerCase().contains(query.toLowerCase()))
                peopleQueryResults.add(entry.getValue());
        }

        Map<String, Event> eventMap = UserInfo.getInstance().getEvents();
        for (Map.Entry<String, Event> entry : eventMap.entrySet()) {
            if (entry.getValue().getSubtitle().toLowerCase().contains(query.toLowerCase()))
                peopleQueryResults.add(entry.getValue());
        }

        mAdapter = new PersonRVAdapter(this, peopleQueryResults);
        mRecyclerView.setAdapter(mAdapter);
    }
}
