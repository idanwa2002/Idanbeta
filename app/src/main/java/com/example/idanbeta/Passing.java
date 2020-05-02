package com.example.idanbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.victor.loading.rotate.RotateLoading;

import static com.example.idanbeta.FBref.refAuth;
import static com.example.idanbeta.FBref.refClients;
import static com.example.idanbeta.FBref.refPhyios;

public class Passing extends AppCompatActivity {
TextView tv;
    public String c,p;
    FirebaseUser user = refAuth.getCurrentUser();
    String uid = user.getUid(),email = user.getEmail();
    Boolean bool;
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    /**
     * On activity create - connect textview and rotateloading to xml
     * <p>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passing);
        tv=findViewById(R.id.tv);
        RotateLoading rotateLoading = findViewById(R.id.rotateloading);
        rotateLoading.start();
    }
    /**
     * On activity start - read user name from firebase and move to next activity
     * <p>
     *
     */

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
                        bool = u.getPermission();
                        tv.setText("Welcome back " + c + "!");
                        /*Intent di= new Intent(Passing.this, Patientlists.class);
                        if (u.getPermission())
                        di.putExtra("perm","yes");
                        if (!u.getPermission()) di.putExtra("perm","no");
                        di.putExtra("name",c);
                        di.putExtra("pname",p);
                        startActivity(di);*/
                        new Handler().postDelayed(new Runnable(){
                            @Override
                            public void run() {
                                Intent di= new Intent(Passing.this, ClientMain.class);
                                di.putExtra("perm","yes");
                                if (!bool) di.putExtra("perm","no");
                                di.putExtra("name",c);
                                di.putExtra("pname",p);
                                startActivity(di);
                                Passing.this.finish();
                            }
                        }, SPLASH_DISPLAY_LENGTH);

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
                        new Handler().postDelayed(new Runnable(){
                            @Override
                            public void run() {
                                Intent di= new Intent(Passing.this, TrainerMain.class);
                                di.putExtra("pname",p);
                                startActivity(di);
                                Passing.this.finish();
                            }
                        }, SPLASH_DISPLAY_LENGTH);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    /**
     * On activity pause - If logged in & asked to be remembered - kill activity.
     * <p>
     */
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
