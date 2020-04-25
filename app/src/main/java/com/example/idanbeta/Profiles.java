package com.example.idanbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.idanbeta.FBref.refClients;
import static com.example.idanbeta.FBref.refPhyios;

public class Profiles extends AppCompatActivity {

    String name, email, uid,p,c;
    TextView tVnameview, tVemailview, tVuidview,tv;
    CheckBox cBconnectview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        tVnameview=(TextView)findViewById(R.id.tVnameview);
        tVemailview=(TextView)findViewById(R.id.tVemailview);
        tVuidview=(TextView)findViewById(R.id.tVuidview);
        tv=findViewById(R.id.title);
        String cName=getIntent().getStringExtra("name");
        c=cName;



    }

    @Override
    public void onStart() {
        super.onStart();

        refClients.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User u = ds.getValue(User.class);
                    //assert u != null;
                    if (c.equals(u.getName())) {
                        tVemailview.setText(u.getEmail());
                        tVnameview.setText(u.getName());
                        tVuidview.setText(u.getPhone());
                        tv.setText("Your Profile");


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        refPhyios.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User u = ds.getValue(User.class);
                    //assert u != null;
                    if (c.equals(u.getName())) {
                        tVemailview.setText(u.getEmail());
                        tVnameview.setText(u.getName());
                        tVuidview.setText(u.getPhone());
                        tv.setText("Trainer's profile");

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void update(View view) {

        finish();
    }
}
