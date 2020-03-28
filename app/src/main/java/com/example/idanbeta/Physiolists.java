package com.example.idanbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.idanbeta.FBref.refAuth;
import static com.example.idanbeta.FBref.refClients;
import static com.example.idanbeta.FBref.refPhyios;

public class Physiolists extends AppCompatActivity {
    ListView patlv, penlv;
    String phyName;//,uid;
    ArrayList<String> waitList = new ArrayList<>();
    ArrayList<String> ClientList = new ArrayList<>();
    ArrayList<User> waitValues = new ArrayList<>();
    FirebaseUser user = refAuth.getCurrentUser();
    String uid = user.getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physiolists);
        patlv=(ListView) findViewById(R.id.clientslv);
        penlv=(ListView) findViewById(R.id.pendinglv);

        refPhyios.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User u = ds.getValue(User.class);
                    if (uid.equals(u.getUid()))
                    phyName=u.getName();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Toast.makeText(Physiolists.this, phyName, Toast.LENGTH_LONG).show();

        refClients.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                waitList.clear();
                //waitValues.clear();
                ClientList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    User user = data.getValue(User.class);
                    //waitValues.add(user);
                    if (user.getPermission()&&(phyName.equals(user.getPhy())))
                        ClientList.add(user.getName());
                    if (!(user.getPermission())&&(phyName.equals(user.getPhy())))
                        waitList.add(user.getName());

                }
                ArrayAdapter adp = new ArrayAdapter<String>(Physiolists.this,R.layout.support_simple_spinner_dropdown_item, waitList);
                penlv.setAdapter(adp);
                ArrayAdapter a = new ArrayAdapter<String>(Physiolists.this,R.layout.support_simple_spinner_dropdown_item, ClientList);
                patlv.setAdapter(a);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.add(0,0,100,"Disconnect");
        return true;
    }
    //SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
    //Boolean isChecked=settings.getBoolean("stayConnect",false);
    public boolean onOptionsItemSelected (MenuItem item) {
        String st=item.getTitle().toString();
        if (st.equals("Disconnect")){
            refAuth.signOut();
            SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
            SharedPreferences.Editor editor=settings.edit();
            editor.putBoolean("stayConnect",false);
            editor.apply(); //changed from commit
            finish();
        }
        return true;
    }}

