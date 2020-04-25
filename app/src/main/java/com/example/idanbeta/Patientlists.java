package com.example.idanbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import static com.example.idanbeta.FBref.refAuth;
import static com.example.idanbeta.FBref.refClients;
import static com.example.idanbeta.FBref.refExinfo;
import static com.example.idanbeta.FBref.refFeedback;
import static com.example.idanbeta.FBref.refPhyios;
import static com.example.idanbeta.FBref.refTasks;

public class Patientlists extends AppCompatActivity implements MyDialog.MyDialogListener,AdapterView.OnItemClickListener {
    //String p="physio",dt,s="client2";//
    //public User b;
    public String c,p, first,second,fdb2;
    ListView tslv, finlv;
    ArrayList<String> exList = new ArrayList<>();
    ArrayList<String> fList = new ArrayList<>();
    ArrayList<User> waitValues = new ArrayList<>();
    FirebaseUser user = refAuth.getCurrentUser();
    String uid = user.getUid(),email = user.getEmail();
    AlertDialog.Builder ad;
    LinearLayout dialog;
    int d; Boolean b;
    TextView tv,tvad;
    EditText etad;
    CheckBox cbab;
    SeekBar sbad;
    Information n; String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientlists);
        tslv=(ListView) findViewById(R.id.taskslv);
        finlv=(ListView) findViewById(R.id.finishedlv);
        tv=findViewById(R.id.tvtv);
        tslv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        tslv.setOnItemClickListener(this);

        String cName=getIntent().getStringExtra("name");
        String pName=getIntent().getStringExtra("pname");
        c=cName;
        p=pName;
        String bbb=getIntent().getStringExtra("perm");
        if (bbb.equals("yes"))
            tv.setText("Welcome back " + c + "!, you can see above exercises that your trainer has ordered you to do and information about them including videos, good luck! (please check your trainer's advice before doing an exercise)");
        if (bbb.equals("no"))
            tv.setText("Waiting for your physio to accept you!");

        finlv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        finlv.setOnItemClickListener(this);

        refExinfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                exList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Information information = data.getValue(Information.class);
                    //waitValues.add(user);
                    exList.add(information.gettName());

                }
                ArrayAdapter adp = new ArrayAdapter<String>(Patientlists.this,R.layout.support_simple_spinner_dropdown_item, exList);
                tslv.setAdapter(adp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*refClients.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User u = ds.getValue(User.class);
                    //assert u != null;
                    if (uid.equals(u.getUid())){
                         pp=u.getPhy();
                         c=u.getName();
                        //tv.setText(cName);
                        //s = c;
                        tv.setText(c);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //c=(String) tv.getText();Toast.makeText(Patientlists.this, c + (String)tv.getText(), Toast.LENGTH_LONG).show();
        //tv.setText(tv.getText().toString()+"!");

        //tv.setText(pp + s +c + "!");


        /*refTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Exercises ex = ds.getValue(Exercises.class);
                    //Toast.makeText(Patientlists.this, ex.getDate(), Toast.LENGTH_LONG).show();
                    assert ex != null;
                    if (ex.getActive()!=null)
                    if ((ex.getcName().equals(c))&&ex.getActive()){
                        fList.add(ex.gettName() + ex.getDate());
                        //refTasks.child(ex.getcName() + " " + ex.gettName()).child("active").setValue(true);
                    }
                }
                ArrayAdapter a = new ArrayAdapter<String>(Patientlists.this,R.layout.support_simple_spinner_dropdown_item, fList);
                finlv.setAdapter(a);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

    }

    @Override
    public void onStart() {
        super.onStart();
/*
        refClients.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User u = ds.getValue(User.class);
                    //assert u != null;
                    if (uid.equals(u.getUid())){
                        pp =u.getPhy();
                        c =u.getName();
                        User b = u;
                        //tv.setText(cName);
                        //s = c;
                        //tv.setText(c);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        //Toast.makeText(Patientlists.this, c,Toast.LENGTH_LONG).show();
        fList.clear();
        //tv.setText( "!");
        //tv.setText(c  + "!!!");
        Query query = refTasks
                .orderByChild("cName")
                .equalTo(c)
                //.orderByChild("date")
                ;
        query.addListenerForSingleValueEvent(VEL);
    }
    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override

        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if (dataSnapshot.exists()){

                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Exercises ex = data.getValue(Exercises.class);
                    //Toast.makeText(Patientlists.this, ex.getDate(), Toast.LENGTH_LONG).show();
                    //assert ex != null;
                    //if (ex.getActive()!=null)
                    //assert ex != null;
                    Boolean smth= ex.getActive();
                    //if (smth!=null)
                    //if (smth)
                            fList.add(ex.getDate() + " " + ex.gettName());
                            //refTasks.child(ex.getcName() + " " + ex.gettName()).child("active").setValue(true);
                        //}
                }
                Collections.sort(fList);

                ArrayAdapter a = new ArrayAdapter<String>(Patientlists.this,R.layout.support_simple_spinner_dropdown_item, fList);
                finlv.setAdapter(a);

            }}

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        switch(parent.getId()){
            case R.id.taskslv:
                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);


                final View customLayout = getLayoutInflater().inflate(R.layout.dialog_layout, null);
                builder.setView(customLayout);
                TextView tv=customLayout.findViewById(R.id.tv);
                TextView tv2=customLayout.findViewById(R.id.tv2);

                //dialog = (LinearLayout) getLayoutInflater().inflate(R.layout.dialogx, null);
                //ad = new AlertDialog.Builder(this);
                //.setCancelable(false);
                //ad.setView(dialog);
                //final String
                str= exList.get(position);

                builder.setTitle(str);
                refExinfo.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data : dataSnapshot.getChildren()){
                            n = data.getValue(Information.class);
                            //Toast.makeText(Patientlists.this, exList.get(position), Toast.LENGTH_LONG).show();
                            if (exList.get(position).equals(n.gettName())){
                                TextView tv2=customLayout.findViewById(R.id.tv2);
                                tv2.setText(n.getInfo());
                            }}
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                builder.setPositiveButton("go to video", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {
                        refExinfo.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot data : dataSnapshot.getChildren()){
                                    n = data.getValue(Information.class);
                                    if (str.equals(n.gettName())){
                                        //ad.setMessage(n.getInfo()); // לשנות לקריאה רציפה*********
                                        //Toast.makeText(Patientlists.this, n.getUrl(), Toast.LENGTH_LONG).show();
                                        Uri uri = Uri.parse("http://www." + n.getUrl()); // missing 'http://' will cause crashed
                                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                        startActivity(intent);
                                        dialogInterface.dismiss();
                                    }}

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
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

                break;

            case R.id.finishedlv:
                /*LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.costum_dialog_layout, null);
                etad = alertLayout.findViewById(R.id.ett);
                tvad = alertLayout.findViewById(R.id.ttvv);
                sbad = alertLayout.findViewById(R.id.sb);
                cbab = alertLayout.findViewById(R.id.cbab);//ad.setView(alertLayout);
                dialog = (LinearLayout) getLayoutInflater().inflate(R.layout.dialogx, null);
                ad = new AlertDialog.Builder(this);
                ad.setCancelable(false);
                ad.setView(dialog);
                //etad.setHint("If you did it, share how did it go, and if not, explain why?");

                ad = new AlertDialog.Builder(this);ad.setCancelable(false);*/
                final android.app.AlertDialog.Builder builder2 = new android.app.AlertDialog.Builder(this);


                final View customLayout2 = getLayoutInflater().inflate(R.layout.costum_dialog_layout, null);
                builder2.setView(customLayout2);
                //TextView tv=customLayout2.findViewById(R.id.tv);
                //TextView tv2=customLayout2.findViewById(R.id.tv2);
                final String str= fList.get(position);

                first = str.substring(0,10 );
                second = str.substring(11);
                //Toast.makeText(Patientlists.this, second +str + first , Toast.LENGTH_LONG).show();

                refTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //gList.clear();
                        for(DataSnapshot data : dataSnapshot.getChildren()){
                            Exercises ex = data.getValue(Exercises.class);
                            first = fList.get(position).substring(0,10 );
                            second = fList.get(position).substring(11);
                            //if (str!=null)
                            if (ex.gettName().equals(second)&&ex.getDate().equals(first)){
                                TextView tv=customLayout2.findViewById(R.id.tv);
                                TextView tv2=customLayout2.findViewById(R.id.tv2);
                                tv.setText("your doctor's advice is: " + ex.getAdvice());
                                tv2.setText("exercise time: " + ex.getDate() + " " + ex.getTime());
                            }}
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                builder2.setPositiveButton("enter feedback", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //refTasks.child("1 " + c + " "+ second).child("active").setValue(false);
                        /*String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                        Calendar cal = Calendar.getInstance();
                        Date time1=cal.getTime();
                        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                        String time=dateFormat.format(time1);
                        Fback fback=new Fback(etad.getText().toString(),time,date,cbab.isChecked(),sbad.getProgress(),second);
                        refFeedback.child(str).setValue(fback);*/
                        //Intent di= new Intent(Patientlists.this, Sheetfeedback.class);
                        //Intent in = new Intent(Physiolists.this, Addnew.class);


                        //di.putExtra("name",str);
                        //di.putExtra("cname",c);
                        //startActivity(di);
                        //Toast.makeText(Patientlists.this, "Feedback sent!" , Toast.LENGTH_LONG).show();
                        first = fList.get(position).substring(0,10 );
                        second = fList.get(position).substring(11);
                        etad = customLayout2.findViewById(R.id.ett);
                        tvad = customLayout2.findViewById(R.id.ttvv);
                        sbad = customLayout2.findViewById(R.id.sb);
                        cbab = customLayout2.findViewById(R.id.cbab);//ad.setView(alertLayout);
                        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                        Calendar cal = Calendar.getInstance();
                        Date time1=cal.getTime();
                        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                        String time=dateFormat.format(time1);
                        Fback fback=new Fback(c,etad.getText().toString(),time,date,cbab.isChecked(),sbad.getProgress(),second);
                        //Fback fback1=new Fback(etad.getText().toString(),)

                        refFeedback.child(date + " " + second).setValue(fback);
                        refTasks.child(fList.get(position) + " " + c).removeValue();
                        Toast.makeText(Patientlists.this, "Feedback sent!" , Toast.LENGTH_LONG).show();
                        recreate();
                        dialogInterface.dismiss();
                    }
                });
                builder2.setNeutralButton("exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                android.app.AlertDialog ab = builder2.create();
                ab.show();


                //openDialog();
                //Toast.makeText(Patientlists.this, fdb2 , Toast.LENGTH_LONG).show();

                break;

        }
        //refTasks.child("0 " + c + " "+ second).removeValue();
        //recreate();
    }
    public void openDialog() {
        MyDialog exampleDialog = new MyDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String fdb, Boolean did, String date, String time,int rating) {
        fdb2=fdb;
        Fback fback = new Fback(c,fdb,time,date,did,rating,"Task 1");
        refFeedback.child(date+time).setValue(fback);
        refTasks.child("0 " + c + " "+ "Task 3").removeValue();
        //Toast.makeText(Patientlists.this, "Feedback sent!" , Toast.LENGTH_LONG).show();
        //refTasks.child("0 " + c + " "+ second).child("active").setValue(false);
        //fList.remove(str);
    }

    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.add(0,0,100,"Disconnect");
        menu.add(0,0,200,"My Profile");
        menu.add(0,0,300,"Doctor's Profile");
        menu.add(0,0,300,"Exercise List");
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

            Intent in = new Intent(Patientlists.this, MainActivity.class);

            startActivity(in);
        }
        if (st.equals("My Profile")){


            Intent in = new Intent(Patientlists.this, Profiles.class);
            in.putExtra("name",c);
            startActivity(in);
        }
        if (st.equals("Doctor's Profile")){


            Intent in = new Intent(Patientlists.this, Profiles.class);
            in.putExtra("name",p);
            startActivity(in);
        }
        if (st.equals("Exercise List")){


            Intent in = new Intent(Patientlists.this, ExList.class);
            //in.putExtra("name",phyName);
            startActivity(in);
        }
        return true;
    }


}
