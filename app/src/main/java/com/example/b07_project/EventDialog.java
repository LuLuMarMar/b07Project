package com.example.b07_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

public class EventDialog extends AppCompatDialogFragment {
    private String name;
    private String date;
    private long limit;
    private TextView eventName;
    private TextView eventDate;
    private TextView eventSpace;

    public EventDialog() {

    }
    public EventDialog(String name, String date, long limit) {
        this.name = name;
        this.date = date;
        this.limit = limit;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_event, null);

        eventName = view.findViewById(R.id.eventName);
        eventDate = view.findViewById(R.id.eventDate);
        eventSpace = view.findViewById(R.id.eventSpace);
        eventName.setText(name);
        eventDate.setText("Date: " + date);
        eventSpace.setText("Remaining Seats: " + limit);

        builder.setView(view);
        return builder.create();
    }
}
