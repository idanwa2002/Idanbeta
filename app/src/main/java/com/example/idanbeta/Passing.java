package com.example.idanbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.idanbeta.FBref.refAuth;
import static com.example.idanbeta.FBref.refClients;
import static com.example.idanbeta.FBref.refPhyios;

public class Passing extends AppCompatActivity {
TextView tv;
    public String c,p;
    FirebaseUser user = refAuth.getCurrentUser();
    String uid = user.getUid(),email = user.getEmail();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passing);
        tv=findViewById(R.id.tv);
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
                    if (uid.equals(u.getUid())) {
                        p = u.getPhy();
                        c = u.getName();
                        tv.setText("Welcome back " + c + "!");
                        Intent di= new Intent(Passing.this, Patientlists.class);
                        //Intent in = new Intent(Physiolists.this, Addnew.class);
                        if (u.getPermission())
                        di.putExtra("perm","yes");
                        if (!u.getPermission()) di.putExtra("perm","no");
                        di.putExtra("name",c);
                        di.putExtra("pname",p);
                        startActivity(di);

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
                    if (uid.equals(u.getUid())) {
                        p = u.getName();
                        //c = u.getName();
                        tv.setText("Welcome back " + p + "!");
                        Intent di= new Intent(Passing.this, Physiolists.class);
                        //Intent in = new Intent(Physiolists.this, Addnew.class);
                        //if (u.getPermission())
                          //  di.putExtra("perm","yes");
                        //if (!u.getPermission()) di.putExtra("perm","no");
                        //di.putExtra("name",c);
                        di.putExtra("pname",p);
                        startActivity(di);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }}
