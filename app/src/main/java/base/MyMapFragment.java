package base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.AmazonMap.OnMarkerClickListener;
import com.amazon.geo.mapsv2.CameraUpdateFactory;
import com.amazon.geo.mapsv2.OnMapReadyCallback;
import com.amazon.geo.mapsv2.SupportMapFragment;
import com.amazon.geo.mapsv2.model.BitmapDescriptorFactory;
import com.amazon.geo.mapsv2.model.LatLng;
import com.amazon.geo.mapsv2.model.Marker;
import com.amazon.geo.mapsv2.model.MarkerOptions;
import com.amazon.geo.mapsv2.model.PolylineOptions;
import com.bignerdranch.android.familymap3.R;

import java.util.List;
import java.util.TreeMap;

import model.Event;
import model.Filters;
import model.Person;
import model.SettingsInfo;
import model.UserInfo;

/**
 * Created by athom909 on 3/23/16.
 */
public class MyMapFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private View v;
    private SupportMapFragment mMapFragment;
    private AmazonMap mMap;

    private Toolbar mBottomToolbar;

    private boolean mapReady = false;

    private static final float CENTER_ZOOM = 4.0f;

    /**
     * inflates the amazon map to the frameLayout in the fragment's corresponding Activity
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_map, container, false);

        mBottomToolbar = (Toolbar) v.findViewById(R.id.bottomToolbar);
        if(UserInfo.getCurrentPerson() == null) {
            mBottomToolbar.setTitle("Click on a marker to see event details");
        }

        mMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(AmazonMap amazonMap) {
                amazonMap.setMapType(SettingsInfo.getInstance().getMapTypeAsInt());
                mMap = amazonMap;


                setMarkers();
                setOnMarkerClickListener();
                if(UserInfo.getCurrentPerson() != null) {
                    displayMarkerInfo();
                    drawPolyLines();
                    moveCenterToMarker();
                }
                mapReady = true;
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(UserInfo.getCurrentPerson() == null) {
            mBottomToolbar.setOnClickListener(null);
            mBottomToolbar.setTitle("Click on a marker to see event details");
            mBottomToolbar.setSubtitle(null);
        }
        if(mapReady) {
            mMap.clear();
            mMap.setMapType(SettingsInfo.getInstance().getMapTypeAsInt());

            setMarkers();
            setOnMarkerClickListener();
            if (UserInfo.getCurrentPerson() != null) {
                displayMarkerInfo();
                drawPolyLines();
                moveCenterToMarker();
            }
        }
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
                SettingsInfo.getInstance().setSpouseLinesColor(item); break;
            case R.id.slSpinner:
                SettingsInfo.getInstance().setSpouseLinesColor(item); break;
            case R.id.mapTypeSpinner:
                SettingsInfo.getInstance().setMapType(item); break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setMarkers() {

        Person.Gender g = UserInfo.getInstance().getUser().getGender();
        if (Filters.getInstance().isGenderOn(g))
            setEventMarkers(UserInfo.getInstance().getUser(), BitmapDescriptorFactory.HUE_BLUE);
        if (UserInfo.getInstance().getUser().getSpouse() != null && Filters.getInstance()
                .isGenderOn(UserInfo.oppositeGender(g)))
            setEventMarkers(UserInfo.getInstance().getPerson(UserInfo.getInstance().getUser()
                    .getSpouse()), BitmapDescriptorFactory.HUE_YELLOW);

        if(UserInfo.getInstance().isUserSpouse()) return;

        if (Filters.getInstance().isFathersSideOn()) {
            List<Person> paternalAncestors = UserInfo.getInstance().getFamilyTree()
                    .getPaternalAncestors();
            for (Person p : paternalAncestors) {
                if (Filters.getInstance().isGenderOn(p.getGender()))
                    setEventMarkers(p, BitmapDescriptorFactory.HUE_GREEN);
            }
        }

        if (Filters.getInstance().isMothersSideOn()) {
            List<Person> maternalAncestors = UserInfo.getInstance().getFamilyTree()
                    .getMaternalAncestors();
            for (Person p : maternalAncestors) {
                if (Filters.getInstance().isGenderOn(p.getGender()))
                    setEventMarkers(p, BitmapDescriptorFactory.HUE_MAGENTA);
            }
        }
    }

    private void setEventMarkers(Person p, float hue) {

        for (TreeMap.Entry<Integer, Event> pEntry : p.getPersonEvents().entrySet()) {
            Event e = pEntry.getValue();
            if (Filters.getInstance().isEventOn(e.getDescription())) {
                LatLng coord = e.getCoordinates();
                MarkerOptions opt = new MarkerOptions()
                        .position(coord)
                        .title(pEntry.getValue().getEventId())
                        .snippet(coord.toString())
                        .icon(BitmapDescriptorFactory.defaultMarker(hue));
                mMap.addMarker(opt);
            }
        }
    }


    private void setOnMarkerClickListener() {

        mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                UserInfo.setSelectedEventId(marker.getTitle());
                displayMarkerInfo();
                drawPolyLines();
                return false;
            }
        });
    }

    private void displayMarkerInfo() {
        Event e = UserInfo.getInstance().getEvents().get(UserInfo.getSelectedEventId());
        Person p = UserInfo.getInstance().getPerson(e.getPersonId());
        UserInfo.setCurrentPerson(p);
        mBottomToolbar.setTitle(p.getFullName());
        mBottomToolbar.setSubtitle(e.getSubtitle());
        setBottomToolbarListener();
    }

    public void setBottomToolbarListener() {
        mBottomToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBottomToolbarButtonClicked();
            }

        });
    }

    private void onBottomToolbarButtonClicked() {
        Intent intent = new Intent(getActivity(), PersonActivity.class);
        startActivity(intent);
    }

    private void drawPolyLines() {
        mMap.clear();
        setMarkers();

        if(SettingsInfo.getInstance().isLifeStoryLinesOn())
            drawPersonEventsPolyLine();
        if(SettingsInfo.getInstance().isSpouseLinesOn())
            drawSpousePolyLine();
        if(SettingsInfo.getInstance().isFamilyTreeLinesOn()) {
            if(UserInfo.getInstance().isUserSpouse()) return;
            drawPaternalAncestorsPolyLine(UserInfo.getSelectedEventId());
            drawMaternalAncestorsPolyLine(UserInfo.getSelectedEventId());
        }

    }

    private void drawPersonEventsPolyLine() {

        PolylineOptions opt = new PolylineOptions()
                .addAll(UserInfo.getCurrentPerson().getPersonEventCoordinates())
                .color(SettingsInfo.getInstance().getLifeStoryLinesColor());
        mMap.addPolyline(opt).setWidth(opt.getWidth() * 0.1f);
    }

    private void drawPaternalAncestorsPolyLine(String eventId) {
        PolylineOptions opt = new PolylineOptions()
                .addAll(UserInfo.getInstance().getFamilyTree().getFatherLine(eventId, UserInfo
                        .getCurrentPerson()))
                .color(SettingsInfo.getInstance().getFamilyTreeLinesColor());
        mMap.addPolyline(opt).setWidth(opt.getWidth() * 0.25f);

        Person p = UserInfo.getCurrentPerson();
        while (p.getFather() != null) {
            if (Filters.getInstance().isGenderOn(Person.Gender.F)) break;
            p = UserInfo.getInstance().getPerson(p.getFather());
            PolylineOptions opt1 = new PolylineOptions()
                    .addAll(UserInfo.getInstance().getFamilyTree().getMotherLine(p
                            .getFirstEventId(), p))
                    .color(SettingsInfo.getInstance().getFamilyTreeLinesColor());
            mMap.addPolyline(opt1).setWidth(opt1.getWidth() * 0.25f);
        }
    }

    private void drawMaternalAncestorsPolyLine(String eventId) {
        PolylineOptions opt = new PolylineOptions()
                .addAll(UserInfo.getInstance().getFamilyTree().getMotherLine(eventId, UserInfo
                        .getCurrentPerson()))
                .color(SettingsInfo.getInstance().getFamilyTreeLinesColor());
        mMap.addPolyline(opt).setWidth(opt.getWidth() * 0.25f);

        Person p = UserInfo.getCurrentPerson();
        while (p.getMother() != null) {
            if(Filters.getInstance().isGenderOn(Person.Gender.M)) break;
            p = UserInfo.getInstance().getPerson(p.getMother());
            PolylineOptions opt1 = new PolylineOptions()
                    .addAll(UserInfo.getInstance().getFamilyTree().getFatherLine(p
                            .getFirstEventId(), p))
                    .color(SettingsInfo.getInstance().getFamilyTreeLinesColor());
            mMap.addPolyline(opt1).setWidth(opt.getWidth() * 0.25f);
        }
    }

    private void drawSpousePolyLine() {
        if(UserInfo.getCurrentPerson().getSpouse() != null &&
                Filters.getInstance().isGenderOn(UserInfo.getInstance()
                        .getPerson(UserInfo.getCurrentPerson().getSpouse()).getGender())) {

            PolylineOptions opt = new PolylineOptions()
                    .add(UserInfo.getInstance().getEvent(UserInfo.getSelectedEventId())
                            .getCoordinates())
                    .add(UserInfo.getInstance().getPerson(UserInfo.getCurrentPerson().getSpouse())
                            .getFirstEventCoordinates())
                    .color(SettingsInfo.getInstance().getSpouseLinesColor());
            mMap.addPolyline(opt).setWidth(opt.getWidth() * 0.25f);
        }
    }


    public void moveCenterToMarker() {
        Event e = UserInfo.getInstance().getEvent(UserInfo.getSelectedEventId());
        LatLng center = e.getCoordinates();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center, CENTER_ZOOM));
    }
}