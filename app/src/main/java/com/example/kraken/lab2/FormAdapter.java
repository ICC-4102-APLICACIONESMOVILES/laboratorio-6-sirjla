package com.example.kraken.lab2;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kraken.lab2.models.Forms;

import java.util.ArrayList;

/**
 * Created by kraken on 4/3/18.
 */

public class FormAdapter extends ArrayAdapter<Forms> implements View.OnClickListener{

    private ArrayList<Forms> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView nameH;
        TextView commentH;
        TextView dateH;
    }

    public FormAdapter(ArrayList<Forms> data, Context context) {
        super(context, R.layout.form_adapter, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Forms dataModel=(Forms)object;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Forms dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.form_adapter, parent, false);
            viewHolder.nameH = (TextView) convertView.findViewById(R.id.name_h);
            viewHolder.commentH = (TextView) convertView.findViewById(R.id.comment_h);
            viewHolder.dateH = (TextView) convertView.findViewById(R.id.date_h);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.nameH.setText(dataModel.getFormName());
        viewHolder.commentH.setText(dataModel.getFormComment());
        viewHolder.dateH.setText(dataModel.getFormDate());
        // Return the completed view to render on screen
        return convertView;
    }
}

