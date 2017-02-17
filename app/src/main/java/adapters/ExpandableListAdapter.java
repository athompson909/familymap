package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.familymap3.R;

import java.util.Map;
import java.util.List;

import base.MapActivity;
import base.PersonActivity;
import model.Event;
import model.Person;
import model.UserInfo;
import utils.GraphicsUtils;

/**
 * Created by athom909 on 4/5/16.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {


    private Context context;
    private List<String> headers;
    private Map<String, List<String>> headerLists;
    private String currentHeader;

    public ExpandableListAdapter(Context context, List<String> headerItems,
                                 Map<String, List<String>> listItems) {
        this.context = context;
        this.headers = headerItems;
        this.headerLists = listItems;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return headerLists.get(headers.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        UserInfo inst = UserInfo.getInstance();
        String childText = null;

        if(currentHeader.equals("LIFE EVENTS")) {
            String eventId = (String) getChild(groupPosition, childPosition);
            Event e = inst.getEvent(eventId);
            childText = new String(e.getEventTitle() + ": " + e.getCity() + ", " + e.getCountry() +
                    " (" + e.getYear() + ")\n" + inst.getPerson(e.getPersonId()).getFullName());
            ImageView eventImageView = (ImageView) convertView.findViewById(R.id.listItemIcon);
            GraphicsUtils.drawMapMarkerIcon(eventImageView, context);
            /*Drawable eventIcon = new IconDrawable(context, Iconify.IconValue.fa_map_marker)
                    .sizeDp(40).color(Color.GRAY);
            eventImageView.setImageDrawable(eventIcon);*/
        }
        else if(currentHeader.equals("FAMILY")) {
            String pId = (String) getChild(groupPosition, childPosition);
            Person p = inst.getPerson(pId);

            ImageView genderImageView = (ImageView) convertView.findViewById(R.id.listItemIcon);

            if(pId.equals(UserInfo.getCurrentPerson().getSpouse())) {
                /*switch (p.getGender()) {
                    case M:
                        Drawable maleIcon = new IconDrawable(context, Iconify.IconValue.fa_male)
                                .color(Color.BLUE).sizeDp(40);
                        genderImageView.setImageDrawable(maleIcon);
                        break;
                    case F:
                        Drawable femaleIcon = new IconDrawable(context, Iconify.IconValue
                                .fa_female).color(Color.MAGENTA).sizeDp(40);
                        genderImageView.setImageDrawable(femaleIcon);
                        break;
                    default: assert false;
                }*/
                GraphicsUtils.drawGenderIcon(p.getGender(), genderImageView, context);
                childText = new String(p.getFullName() + "\nSpouse");

            }
            else if(pId.equals(UserInfo.getCurrentPerson().getFather())) {
                /*Drawable maleIcon = new IconDrawable(context, Iconify.IconValue.fa_male)
                        .color(Color.BLUE).sizeDp(40);
                genderImageView.setImageDrawable(maleIcon);*/
                GraphicsUtils.drawMaleIcon(p.getGender(), genderImageView, context);
                childText = new String(p.getFullName() + "\nFather");

            }
            else if(pId.equals(UserInfo.getCurrentPerson().getMother())) {
                /*Drawable femaleIcon = new IconDrawable(context, Iconify.IconValue.fa_female)
                        .color(Color.MAGENTA).sizeDp(40);
                genderImageView.setImageDrawable(femaleIcon);*/
                GraphicsUtils.drawFemaleIcon(p.getGender(), genderImageView, context);
                childText = new String(p.getFullName() + "\nMother");

            }
            else if(pId.equals(UserInfo.getCurrentPerson().getChild())) {
                /*switch (p.getGender()) {
                    case M:
                        Drawable maleIcon1 = new IconDrawable(context, Iconify.IconValue
                                .fa_male).color(Color.BLUE).sizeDp(40);
                        genderImageView.setImageDrawable(maleIcon1);
                        break;
                    case F:
                        Drawable femaleIcon1 = new IconDrawable(context, Iconify.IconValue
                                .fa_female).color(Color.MAGENTA).sizeDp(40);
                        genderImageView.setImageDrawable(femaleIcon1);
                        break;
                    default: assert false;
                }*/
                GraphicsUtils.drawGenderIcon(p.getGender(), genderImageView, context);
                childText = new String(p.getFullName() + "\nChild");

            }


        }

        TextView listItemText = (TextView) convertView.findViewById(R.id.listItem);
        listItemText.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return headerLists.get(headers.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headers.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return headers.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if(convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView listHeaderText = (TextView) convertView.findViewById(R.id.listHeader);
        listHeaderText.setText(headerTitle);
        currentHeader = headerTitle;

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void onChildClick(int groupPosition, int childPosition) {

        if(headers.get(groupPosition).equals("FAMILY")) {
            String personId = (String) getChild(groupPosition, childPosition);
            UserInfo.setCurrentPerson(UserInfo.getInstance().getPerson(personId));

            Intent intent = new Intent(context, PersonActivity.class);
            context.startActivity(intent);
        }
        else if(headers.get(groupPosition).equals("LIFE EVENTS")) {

            // TODO: determine if the next 4 lines are needed
            String eventId = (String) getChild(groupPosition, childPosition);
            String pId = UserInfo.getInstance().getEvent(eventId).getPersonId();
            Person p = UserInfo.getInstance().getPerson(pId);

            UserInfo.setCurrentPerson(p);
            UserInfo.setSelectedEventId(eventId);

            Intent intent = new Intent(context, MapActivity.class);
            context.startActivity(intent);

        }

    }
}