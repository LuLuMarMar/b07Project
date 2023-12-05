package com.example.b07_project;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventActivity extends AppCompatActivity {
    private static final String TAG = "EventActivity";
    private FirebaseAuth userAuth;
    private FirebaseAuth.AuthStateListener userAuthListener;
    private DatabaseReference userDataReference;
    private DatabaseReference eventReference;
    private boolean isListClickable;
    private AlertDialog alertDialog;
    private ListView listViewEvent;
    private List<String> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        isListClickable = true;
        eventReference = FirebaseDatabase.getInstance().getReference("events");
        userDataReference = FirebaseDatabase.getInstance().getReference("user_data");
        userAuth = FirebaseAuth.getInstance();
        userAuthListener = (FirebaseAuth.AuthStateListener) (firebaseAuth) -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if(user != null) {
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                Log.d(TAG, "onAuthStateChanged:signed_out");
            }
        };
        listViewEvent = findViewById(R.id.listViewEvent);
        eventList = new ArrayList<>();
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setBackgroundColor(Color.parseColor("#007FA3"));
        btnBack.setOnClickListener(v -> finish());
        displayEvent();
        listViewEvent.setOnItemClickListener((parent, view, position, id) -> {
            if(isListClickable) {
                getEvent(position, new EventCallback() {
                    @Override
                    public void onEventRetrieved(String eventKey) {
                        DatabaseReference eventSelectedReference =
                                eventReference.child(eventKey);
                        eventSelectedReference.addListenerForSingleValueEvent(
                                new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot eventSnapshot) {
                                if (eventSnapshot.exists()) {
                                    showAlertDialog(R.layout.layout_event, eventSnapshot);
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
        });
    }

    private void displayEvent() {
        eventReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, eventList);
        listViewEvent.setAdapter(adapter);
    }

    private interface EventCallback {
        void onEventRetrieved(String eventKey);
        void onCancelled(DatabaseError databaseError);
    }

    private void getEvent(int position, final EventCallback callback) {
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
    private void showAlertDialog(int layout, DataSnapshot eventSnapshot) {
        String name = eventSnapshot.child("name").getValue(String.class);
        long limit = eventSnapshot.child("limit").getValue(Long.class);
        long day = eventSnapshot.child("day").getValue(Long.class);
        long month = eventSnapshot.child("month").getValue(Long.class);
        long year = eventSnapshot.child("year").getValue(Long.class);

        FirebaseUser user = userAuth.getCurrentUser();
        assert user != null;
        String userID = user.getUid();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View layoutView = getLayoutInflater().inflate(layout, null);
        View dimmingLayout = LayoutInflater.from(this)
                .inflate(R.layout.dimming_layout, null);
        Button btnRSVP = layoutView.findViewById(R.id.btnRSVP);
        Button btnAddFB = layoutView.findViewById(R.id.btnAddFB);

        userDataReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                if (userSnapshot.child(userID).hasChild(Objects
                        .requireNonNull(eventSnapshot.getKey()))) {
                    btnAddFB.setBackgroundColor(Color.parseColor("#F2F4F7"));
                    btnRSVP.setBackgroundColor(Color.parseColor("#8F9196"));
                } else {
                    btnAddFB.setEnabled(false);
                    btnAddFB.setBackgroundColor(Color.parseColor("#8F9196"));
                    btnRSVP.setBackgroundColor(Color.parseColor("#F2F4F7"));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        ImageButton exitEventDetails = layoutView.findViewById(R.id.exitEventDetails);
        TextView eventName = layoutView.findViewById(R.id.eventName);
        TextView eventDate = layoutView.findViewById(R.id.eventDate);
        TextView eventSpace = layoutView.findViewById(R.id.eventSpace);
        eventName.setText(name);
        eventDate.setText(month + "/" + day + "/" + year);
        eventSpace.setText("Remaining Seats: " + limit);

        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        addContentView(dimmingLayout, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        isListClickable = false;
        Objects.requireNonNull(alertDialog.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().addFlags(WindowManager.LayoutParams.DIM_AMOUNT_CHANGED);
        alertDialog.show();

        btnRSVP.setOnClickListener(view -> {
            if(limit > 0) {
                userDataReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                        if (userSnapshot.child(userID)
                                .hasChild(Objects.requireNonNull(eventSnapshot.getKey()))) {
                            eventReference.child(eventSnapshot.getKey())
                                    .child("limit").setValue(limit + 1);
                            Toast.makeText(EventActivity.this,
                                    "Cancelled Acceptance", Toast.LENGTH_LONG).show();
                            btnAddFB.setEnabled(false);
                            btnAddFB.setBackgroundColor(Color.parseColor("#8F9196"));
                            btnRSVP.setBackgroundColor(Color.parseColor("#F2F4F7"));
                            userDataReference.child(userID)
                                    .child(eventSnapshot.getKey()).removeValue();
                        } else {
                            eventReference.child(eventSnapshot.getKey())
                                    .child("limit").setValue(limit - 1);
                            Toast.makeText(EventActivity.this,
                                    "Accepted Invitation", Toast.LENGTH_LONG).show();
                            btnAddFB.setEnabled(true);
                            btnAddFB.setBackgroundColor(Color.parseColor("#F2F4F7"));
                            btnRSVP.setBackgroundColor(Color.parseColor("#8F9196"));
                            userDataReference.child(userID)
                                    .child(eventSnapshot.getKey()).setValue(true);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            } else {
                Toast.makeText(EventActivity.this,
                        "The event is full!", Toast.LENGTH_LONG).show();
            }
        });
        btnAddFB.setOnClickListener(view -> {
            Intent intent = new Intent(EventActivity.this,
                    AddFeedbackActivity.class);
            intent.putExtra("eventName", name);
            startActivity(intent);
            isListClickable = true;
            if (dimmingLayout != null && dimmingLayout.getParent() != null) {
                ((ViewGroup) dimmingLayout.getParent()).removeView(dimmingLayout);
            }
            alertDialog.dismiss();
        });

        exitEventDetails.setOnClickListener(view -> {
            isListClickable = true;
            if (dimmingLayout != null && dimmingLayout.getParent() != null) {
                ((ViewGroup) dimmingLayout.getParent()).removeView(dimmingLayout);
            }
            alertDialog.dismiss();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        userAuth.addAuthStateListener(userAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(userAuthListener != null) {
            userAuth.removeAuthStateListener(userAuthListener);
        }
    }
}