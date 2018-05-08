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
import java.util.List;

/**
 * Created by kraken on 4/3/18.
 */

public class FormAdapter extends ArrayAdapter<Forms> {
    public FormAdapter(Context context, List<Forms> forms) {
        super(context, 0, forms);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Forms form = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.form_adapter, parent, false);
        }
        // Lookup view for data population
        TextView name_h = (TextView) convertView.findViewById(R.id.name_h);
        TextView comment_h = (TextView) convertView.findViewById(R.id.comment_h);
        TextView date_h = (TextView) convertView.findViewById(R.id.date_h);
        TextView questions_number = (TextView) convertView.findViewById(R.id.question_number);
        TextView form_id = (TextView) convertView.findViewById(R.id.form_id);
        // Populate the data into the template view using the data object
        name_h.setText(form.getFormName());
        comment_h.setText(form.getFormComment());
        date_h.setText(form.getFormDate());
        questions_number.setText(form.getQuestions().toString());
        form_id.setText(Integer.toString(form.getFormId()));
        // Return the completed view to render on screen
        return convertView;
    }
}