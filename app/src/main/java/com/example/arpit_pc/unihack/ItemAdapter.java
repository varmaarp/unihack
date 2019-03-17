package com.example.arpit_pc.unihack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {

    public ItemAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        Item currentItem = getItem(position);

        TextView nameView = (TextView) listItemView.findViewById(R.id.nameId);
        nameView.setText(currentItem.GetName());

        TextView carbonValueView = (TextView) listItemView.findViewById(R.id.carbonValueId);
        carbonValueView.setText("Carbon Footprint - " + Float.toString(currentItem.GetCarbonValue()) + " Kg");

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }
}


