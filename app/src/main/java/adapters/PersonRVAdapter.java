package adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.familymap3.R;

import java.util.List;

import base.MapActivity;
import base.PersonActivity;
import model.Event;
import model.Person;
import model.SearchObject;
import model.UserInfo;
import utils.GraphicsUtils;

/**
 * Created by athom909 on 4/9/16.
 */
public class PersonRVAdapter extends RecyclerView.Adapter<PersonRVAdapter.ViewHolder> {

    private Context context;
    private List<SearchObject> lines;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public GridLayout mSRGridLayout;
        public ImageView mSRImageView;
        public TextView mSRTextView;

        public ViewHolder(GridLayout gridLayout) {
            super(gridLayout);
            mSRGridLayout = gridLayout;
            mSRImageView = (ImageView) gridLayout.findViewById(R.id.listItemIcon);
            mSRTextView = (TextView) gridLayout.findViewById(R.id.listItem);
        }
    }

    public PersonRVAdapter(Context context, List<SearchObject> lines) {
        this.context = context;
        this.lines = lines;
    }

    @Override
    public PersonRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        ViewHolder viewHolder =  new ViewHolder((GridLayout) view);
        return viewHolder;
    }

    /**
     * This is to set everything inside each individual GridLayout
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchObject searchObject = lines.get(position);
        switch (searchObject.getType()) {
            case PERSON:
                final Person p = (Person) searchObject;// is this a valid cast?
                GraphicsUtils.drawGenderIcon(p.getGender(), holder.mSRImageView, context);
                holder.mSRTextView.setText(p.getFullName());
                holder.mSRGridLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UserInfo.setCurrentPerson(p);
                        Intent intent = new Intent(context, PersonActivity.class);
                        context.startActivity(intent);
                    }
                });
                break;
            case EVENT:
                final Event e = (Event) searchObject;
                GraphicsUtils.drawMapMarkerIcon(holder.mSRImageView, context);
                holder.mSRTextView.setText(e.getSubtitle() + "\n" +
                        UserInfo.getInstance().getPerson(e.getPersonId()).getFullName());
                holder.mSRGridLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UserInfo.setCurrentPerson(UserInfo.getInstance()
                                .getPerson(e.getPersonId()));
                        UserInfo.setSelectedEventId(e.getEventId());
                        Intent intent = new Intent(context, MapActivity.class);
                        context.startActivity(intent);
                    }
                });

                break;
            default:
                assert false;
        }
    }

    @Override
    public int getItemCount() {
        return lines.size();
    }
        
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void addLine(Person p) {
        lines.add(p);
        notifyDataSetChanged();
    }

    public void removeLine(int lineNumber) {
        lines.remove(lineNumber);
    }


}