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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import static com.example.idanbeta.FBref.refAuth;
import static com.example.idanbeta.FBref.refFeedback;
import static com.example.idanbeta.FBref.refMsg;
import static com.example.idanbeta.FBref.refTasks;

public class ClientMain extends AppCompatActivity implements AdapterView.OnItemClickListener {

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
    Messages messages;
    /**
     * on activity create - checks if user permitted on not and fills first listview with messages
     * <p>
     *
     */
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
            tv.setText("Welcome back " + c + "!, you can see above messages and exercises that your trainer has ordered you to do and information about them including videos, good luck! (please check your trainer's advice before doing an exercise)");
        if (bbb.equals("no"))
            tv.setText("Waiting for your trainer to accept you!");

        finlv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        finlv.setOnItemClickListener(this);

        refMsg.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                exList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Messages m = data.getValue(Messages.class);
                    if (m.getClient().equals(c))
                    exList.add(m.getMsg());

                }
                ArrayAdapter adp = new ArrayAdapter<String>(ClientMain.this,R.layout.support_simple_spinner_dropdown_item, exList);
                tslv.setAdapter(adp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    /**
     * on activity start - fills second list view with tasks
     * <p>
     *
     */

    @Override
    public void onStart() {
        super.onStart();

        fList.clear();
        Query query = refTasks
                .orderByChild("cName")
                .equalTo(c)
                ;
        query.addListenerForSingleValueEvent(VEL);
    }
    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override

        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if (dataSnapshot.exists()){

                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Exercises ex = data.getValue(Exercises.class);
                    Boolean smth= ex.getActive();
                    fList.add(ex.getDate() + " " + ex.gettName());

                }
                Collections.sort(fList);

                ArrayAdapter a = new ArrayAdapter<String>(ClientMain.this,R.layout.support_simple_spinner_dropdown_item, fList);
                finlv.setAdapter(a);

            }}

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    /**
     * on either listview item click - 1: alertdialog with option to delete. 2: alertdialog with info and feedbacksheet
     * <p>
     *
     */

    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        switch(parent.getId()){
            case R.id.taskslv:

                dialog = (LinearLayout) getLayoutInflater().inflate(R.layout.dialogx, null);

                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(this);

                builder.setView(dialog);

                str= exList.get(position);
                builder.setTitle("Message from your trainer");
                builder.setMessage(str);

                builder.setPositiveButton("Delete Message", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {
                        refMsg.child(c + " " + str).removeValue();
                        recreate();
                        dialogInterface.cancel();
                    }
                });

                builder.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterfac, int i) {
                        dialogInterfac.cancel();
                    }
                });

                AlertDialog adb = builder.create();
                adb.show();

                break;

            case R.id.finishedlv:
                final android.app.AlertDialog.Builder builder2 = new android.app.AlertDialog.Builder(this);


                final View customLayout2 = getLayoutInflater().inflate(R.layout.costum_dialog_layout, null);
                builder2.setView(customLayout2);
                final String str= fList.get(position);

                first = str.substring(0,10 );
                second = str.substring(11);

                refTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data : dataSnapshot.getChildren()){
                            Exercises ex = data.getValue(Exercises.class);
                            first = fList.get(position).substring(0,10 );
                            second = fList.get(position).substring(11);
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
                        String feedb = etad.getText().toString();
                        if (feedb.equals(""))
                            feedb = "No feedback returned";
                        Fback fback=new Fback(c,feedb,time,date,cbab.isChecked(),sbad.getProgress(),second);

                        refFeedback.child(date + " " + second).setValue(fback);
                        refTasks.child(fList.get(position) + " " + c).removeValue();
                        Toast.makeText(ClientMain.this, "Feedback sent!" , Toast.LENGTH_LONG).show();
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


                break;

        }

    }

    /**
     * creates options menu
     * <p>
     *
     */

    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.add(0,0,100,"Disconnect");
        menu.add(0,0,200,"My Profile");
        menu.add(0,0,300,"Trainer's Profile");
        menu.add(0,0,400,"Exercise List");
        menu.add(0,0,500,"Credits");
        return true;
    }

    /**
     *on click on options menu - transfers to your selected activity if permitted
     * <p>
     *
     */

    public boolean onOptionsItemSelected (MenuItem item) {
        String st=item.getTitle().toString();
        if (st.equals("Disconnect")){
            refAuth.signOut();
            SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
            SharedPreferences.Editor editor=settings.edit();
            editor.putBoolean("stayConnect",false);
            editor.apply(); //changed from commit
            //finish();

            Intent in = new Intent(ClientMain.this, MainActivity.class);

            startActivity(in);
        }
        if (st.equals("My Profile")){


            Intent in = new Intent(ClientMain.this, Profiles.class);
            in.putExtra("name",c);
            startActivity(in);
        }
        if (st.equals("Trainer's Profile")){
            String bbb=getIntent().getStringExtra("perm");
            if (bbb.equals("yes")){
                Intent in = new Intent(ClientMain.this, Profiles.class);
                in.putExtra("name",p);
                startActivity(in);
            }

            if (bbb.equals("no"))
                Toast.makeText(ClientMain.this, "You need to be accepted to go there" , Toast.LENGTH_LONG).show();

        }
        if (st.equals("Exercise List")){
            String bbb=getIntent().getStringExtra("perm");
            if (bbb.equals("yes")){
                Intent in = new Intent(ClientMain.this, ExList.class);
                startActivity(in);
            }

            if (bbb.equals("no"))
                Toast.makeText(ClientMain.this, "You need to be accepted to go there" , Toast.LENGTH_LONG).show();




        }
        if (st.equals("Credits")){
            Intent in = new Intent(ClientMain.this, Credits.class);
            //in.putExtra("name",phyName);
            startActivity(in);
        }
        return true;
    }


}
