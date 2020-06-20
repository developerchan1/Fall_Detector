package com.kelompokmcs.falldetector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FallDetectionActivity extends AppCompatActivity {

    private LinearLayout noDetectedLayout, detectedLayout;
    private Button stopBuzzer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fall_detection);

        noDetectedLayout = findViewById(R.id.no_detected_layout);
        detectedLayout = findViewById(R.id.detected_layout);
        stopBuzzer = findViewById(R.id.btn_stop_buzzer);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference isFall = database.getReference("isFall");
        final DatabaseReference buzzer = database.getReference("buzzer");

        isFall.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long isFallValue = dataSnapshot.getValue(Long.class);

                if(isFallValue == 1){
                    noDetectedLayout.setVisibility(View.GONE);
                    detectedLayout.setVisibility(View.VISIBLE);
                }
                else{
                    noDetectedLayout.setVisibility(View.VISIBLE);
                    detectedLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("IsFall", "Failed to read isFall value.", error.toException());
            }
        });

        stopBuzzer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buzzer.setValue(0);
            }
        });
    }
}
