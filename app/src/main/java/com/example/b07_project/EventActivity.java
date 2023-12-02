package com.example.b07_project;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.app.AlertDialog;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {
    private boolean isListClickable;
    private boolean[] flagEventRSVP;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;
    private ImageButton exitEventDetails;
    private TextView eventName;
    private TextView eventDate;
    private TextView eventSpace;
    private DatabaseReference eventReference;
    private ListView listViewEvent;
    private List<String> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        isListClickable = true;
        eventReference = FirebaseDatabase.getInstance().getReference("events");
        listViewEvent = findViewById(R.id.listViewEvent);
        eventList = new ArrayList<>();
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setBackgroundColor(Color.parseColor("#007FA3"));
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        displayFeedback();
        listViewEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isListClickable) {
                    getEvent(position, new EventCallback() {
                        @Override
                        public void onEventRetrieved(String eventKey) {
                            DatabaseReference eventSelectedReference =  eventReference.child(eventKey);
                            eventSelectedReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        showAlertDialog(R.layout.layout_event, position, dataSnapshot);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Handle error
                                }
                            });
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle cancellation or error
                            Log.e("FirebaseError", "Error: " + databaseError.getMessage());
                        }
                    });
                }
            }
        });
    }

    private void displayFeedback() {
        eventReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    long numEvents = dataSnapshot.getChildrenCount();
                    if (flagEventRSVP == null) {
                        flagEventRSVP = new boolean[(int) numEvents];
                    }
                    eventList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        eventList.add(snapshot.child("name").getValue(String.class));
                    }
                    updateFeedbackListView();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors, if any
            }
        });
    }

    private void updateFeedbackListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventList);
        listViewEvent.setAdapter(adapter);
    }

    public interface EventCallback {
        void onEventRetrieved(String eventKey);
        void onCancelled(DatabaseError databaseError);
    }

    public void getEvent(int position, final EventCallback callback) {
        eventReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int current = 0;
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    if (current == position) {
                        String eventKey = eventSnapshot.getKey();
                        callback.onEventRetrieved(eventKey);
                        return;
                    }
                    current++;
                }
                // If the position is out of bounds, you might want to handle this case
                callback.onCancelled(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCancelled(databaseError);
            }
        });
    }
    private void showAlertDialog(int layout, int position, DataSnapshot dataSnapshot) {
        String name = dataSnapshot.child("name").getValue(String.class);
        long limit = dataSnapshot.child("limit").getValue(Long.class);
        long day = dataSnapshot.child("day").getValue(Long.class);
        long month = dataSnapshot.child("month").getValue(Long.class);
        long year = dataSnapshot.child("year").getValue(Long.class);

        dialogBuilder = new AlertDialog.Builder(this);
        View layoutView = getLayoutInflater().inflate(layout, null);
        View dimmingLayout = LayoutInflater.from(this).inflate(R.layout.dimming_layout, null);
        Button btnRSVP = layoutView.findViewById(R.id.btnRSVP);
        Button btnAddFB = layoutView.findViewById(R.id.btnAddFB);

        if(flagEventRSVP[position]) {
            btnAddFB.setBackgroundColor(Color.parseColor("#F2F4F7"));
            btnRSVP.setBackgroundColor(Color.parseColor("#8F9196"));
        } else {
            btnAddFB.setEnabled(false);
            btnAddFB.setBackgroundColor(Color.parseColor("#8F9196"));
            btnRSVP.setBackgroundColor(Color.parseColor("#F2F4F7"));
        }

        exitEventDetails = layoutView.findViewById(R.id.exitEventDetails);
        eventName = layoutView.findViewById(R.id.eventName);
        eventDate = layoutView.findViewById(R.id.eventDate);
        eventSpace = layoutView.findViewById(R.id.eventSpace);
        eventName.setText(name);
        eventDate.setText(month + "/" + day + "/" + year);
        eventSpace.setText("Remaining Seats: " + limit);

        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        addContentView(dimmingLayout, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        isListClickable = false;
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().addFlags(WindowManager.LayoutParams.DIM_AMOUNT_CHANGED);
        alertDialog.show();

        btnRSVP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(limit > 0) {
                    if(!flagEventRSVP[position]) {
                        eventReference.child(dataSnapshot.getKey())
                                .child("limit").setValue(limit - 1);
                        Toast.makeText(EventActivity.this,
                                "Successful RSVP", Toast.LENGTH_LONG).show();
                        btnAddFB.setEnabled(true);
                        btnRSVP.setEnabled(false);
                        btnAddFB.setBackgroundColor(Color.parseColor("#F2F4F7"));
                        btnRSVP.setBackgroundColor(Color.parseColor("#8F9196"));
                        flagEventRSVP[position] = true;
                    }
                } else {
                    Toast.makeText(EventActivity.this,
                            "The event is full!", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnAddFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventActivity.this, AddFeedbackActivity.class);
                intent.putExtra("eventName", name);
                startActivity(intent);
                isListClickable = true;
                if (dimmingLayout != null && dimmingLayout.getParent() != null) {
                    ((ViewGroup) dimmingLayout.getParent()).removeView(dimmingLayout);
                }
                alertDialog.dismiss();
            }
        });

        exitEventDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isListClickable = true;
                if (dimmingLayout != null && dimmingLayout.getParent() != null) {
                    ((ViewGroup) dimmingLayout.getParent()).removeView(dimmingLayout);
                }
                alertDialog.dismiss();
            }
        });
    }
}