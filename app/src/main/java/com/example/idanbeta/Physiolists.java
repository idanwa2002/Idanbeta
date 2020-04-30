package com.example.idanbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.idanbeta.FBref.refAuth;
import static com.example.idanbeta.FBref.refClients;
import static com.example.idanbeta.FBref.refMsg;
import static com.example.idanbeta.FBref.refPhyios;

public class Physiolists extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView patlv, penlv;
    String phyName;//,uid;
    ArrayList<String> waitList = new ArrayList<>();
    ArrayList<String> ClientList = new ArrayList<>();
    ArrayList<User> waitValues = new ArrayList<>();
    FirebaseUser user = refAuth.getCurrentUser();
    String uid = user.getUid();
    AlertDialog.Builder ad;
    LinearLayout dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physiolists);
        patlv=(ListView) findViewById(R.id.clientslv);
        penlv=(ListView) findViewById(R.id.pendinglv);

        penlv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        penlv.setOnItemClickListener(this);

        patlv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        patlv.setOnItemClickListener(this);

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

        //Toast.makeText(Physiolists.this, phyName, Toast.LENGTH_LONG).show();

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

    //@Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        switch(parent.getId()){
            case R.id.clientslv:
                //String str= waitList.get(position);
                //Toast.makeText(Physiolists.this, str, Toast.LENGTH_LONG).show();
                ///Intent i = new Intent(Physiolists.this, Newtask.class);
                //i.putExtra("key",str);
                //startActivity(i);

                dialog = (LinearLayout) getLayoutInflater().inflate(R.layout.dialogx, null);
                ad = new AlertDialog.Builder(this);
                ad.setCancelable(true);
                ad.setTitle("enter client page?");
                ad.setView(dialog);
                ad.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String str= ClientList.get(position);
                        //Toast.makeText(Physiolists.this, str + phyName, Toast.LENGTH_LONG).show();
                       Intent di= new Intent(Physiolists.this, Clientpage.class);
                        //Intent in = new Intent(Physiolists.this, Addnew.class);

                        di.putExtra("name",str);
                        di.putExtra("pname",phyName);
                        startActivity(di);
                        dialogInterface.dismiss();
                    }
                });
                ad.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog adb = ad.create();
                adb.show();

                break;

            case R.id.pendinglv:
                dialog = (LinearLayout) getLayoutInflater().inflate(R.layout.dialogx, null);
                ad = new AlertDialog.Builder(this);
                ad.setCancelable(true);
                ad.setTitle("accept client?");
                ad.setView(dialog);
                ad.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        refClients.child(waitList.get(position)).child("permission").setValue(true);
                        dialogInterface.dismiss();
                        recreate();
                    }
                });
                ad.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                ad.setNegativeButton("deny", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String str= waitList.get(position);
                        refClients.child(str).removeValue();
                        Toast.makeText(Physiolists.this, "Client denied", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                        recreate();
                    }
                });
                AlertDialog adb2 = ad.create();
                adb2.show();
                break;
        }
    }


    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.add(0,0,100,"Disconnect");
        menu.add(0,0,200,"My Profile");
        menu.add(0,0,400,"Add New Exercise");
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
            //finish();

            Intent in = new Intent(Physiolists.this, MainActivity.class);

            startActivity(in);
        }
        if (st.equals("My Profile")){


            Intent in = new Intent(Physiolists.this, Profiles.class);
            in.putExtra("name",phyName);
            startActivity(in);
        }

        if (st.equals("Add New Exercise")){


            Intent in = new Intent(Physiolists.this, NewEx.class);
            in.putExtra("name",phyName);
            startActivity(in);
        }

        return true;
    }

    public void sqr(View view) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);


        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        builder.setView(customLayout);
        TextView tv=customLayout.findViewById(R.id.tv);
        TextView tv2=customLayout.findViewById(R.id.tv2);
        EditText edmsg = customLayout.findViewById(R.id.edmsg);
        tv.setVisibility(View.INVISIBLE);
        tv2.setVisibility(View.INVISIBLE);
        edmsg.setVisibility(View.VISIBLE);

        //dialog = (LinearLayout) getLayoutInflater().inflate(R.layout.dialogx, null);
        //ad = new AlertDialog.Builder(this);
        //.setCancelable(false);
        //ad.setView(dialog);
        //final String
        builder.setTitle("New Message To All");

                /**/
        builder.setPositiveButton("Send Message", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, int i) {
                final EditText edmsg2 = customLayout.findViewById(R.id.edmsg);
                final String msg = edmsg2.getText().toString();

                refClients.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot data : dataSnapshot.getChildren()){
                        User us = data.getValue(User.class);

                        //Toast.makeText(Patientlists.this, exList.get(position), Toast.LENGTH_LONG).show();
                        if ((phyName.equals(us.getPhy())&&(us.getPermission()))){
                            Messages m = new Messages(phyName,us.getName(),msg);
                            refMsg.child(us.getName()+" "+msg).setValue(m);
                        }}
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
                Toast.makeText(Physiolists.this, "Message Sent!" , Toast.LENGTH_LONG).show();
                dialogInterface.cancel();
            }
        });

        builder.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterfac, int i) {
                dialogInterfac.cancel();
            }
        });

        android.app.AlertDialog adb = builder.create();
        adb.show();
    }
}

