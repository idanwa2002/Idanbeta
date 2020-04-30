package com.example.idanbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.idanbeta.FBref.refAuth;
import static com.example.idanbeta.FBref.refClients;
import static com.example.idanbeta.FBref.refFeedback;
import static com.example.idanbeta.FBref.refMsg;
import static com.example.idanbeta.FBref.refTasks;

public class Clientpage extends AppCompatActivity implements AdapterView.OnItemClickListener {
    TextView tasks, fdk;
    ListView fblv, ptasklv;
    Button b1,b2;
    String c,p,first,second,third;
    ArrayList<String> tList = new ArrayList<>();
    ArrayList<String> fbList = new ArrayList<>();
    ArrayAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientpage);
        fblv=(ListView) findViewById(R.id.fblv);
        ptasklv=(ListView) findViewById(R.id.ptasklv);
        tasks=(TextView) findViewById(R.id.tasks);
        fdk=(TextView) findViewById(R.id.fbk);
        b1= findViewById(R.id.button);
        b2=findViewById(R.id.button2);
        String cName=getIntent().getStringExtra("name");
         String pName=getIntent().getStringExtra("pname");
         c=cName;
         p=pName;
        fblv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        fblv.setOnItemClickListener(this);
        //Toast.makeText(Clientpage.this, c+p, Toast.LENGTH_SHORT).show();
        refTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Exercises ex = data.getValue(Exercises.class);Boolean b;
                    if ((c.equals(ex.getcName())&&(p.equals(ex.getpName())))){
                        tList.add(ex.getDate() + " " +ex.gettName());
                        b=true;
                       // Toast.makeText(Clientpage.this, ex.gettName()+ex.getDate(), Toast.LENGTH_LONG).show();

                    }

                }
                if (tList==null){
                    tList.add("no active tasks");
                }
                aa = new ArrayAdapter<String>(Clientpage.this,R.layout.support_simple_spinner_dropdown_item, tList);
                ptasklv.setAdapter(aa);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        refFeedback.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fbList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Fback fb = data.getValue(Fback.class);Boolean b;
                    if (c.equals(fb.getcName())){
                        fbList.add(fb.getDate() + " " +fb.getTask() + " " + fb.getTime());
                        // Toast.makeText(Clientpage.this, ex.gettName()+ex.getDate(), Toast.LENGTH_LONG).show();

                    }

                }
                if (fbList==null){
                    fbList.add("no new feedback");
                }
                aa = new ArrayAdapter<String>(Clientpage.this,R.layout.support_simple_spinner_dropdown_item, fbList);
                fblv.setAdapter(aa);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        tasks.setText(cName + "'s acive tasks");
        fdk.setText(cName + "'s feedback");

    }



    public void b(View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);


        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        builder.setView(customLayout);
        TextView tv=customLayout.findViewById(R.id.tv);
        TextView tv2=customLayout.findViewById(R.id.tv2);
        builder.setTitle(c);
        //builder.setMessage(second +third + first);
        refClients.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User u = ds.getValue(User.class);
                    //assert u != null;
                    if (c.equals(u.getName())) {
                        TextView tv=customLayout.findViewById(R.id.tv);
                        TextView tv2=customLayout.findViewById(R.id.tv2);
                        tv.setText("email: " +u.getEmail());
                        tv2.setText("phone num: " +u.getPhone());


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        builder.setNeutralButton("back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity

                dialog.cancel();
            }
        });
        builder.setPositiveButton("delete client", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                refClients.child(c).removeValue();
                Toast.makeText(Clientpage.this, "Client removed", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void bb(View view){
        Intent in = new Intent(Clientpage.this, Addnew.class);
        in.putExtra("name",c);
        in.putExtra("pname",p);
        startActivity(in);
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
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);


        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        builder.setView(customLayout);
        TextView tv=customLayout.findViewById(R.id.tv);
        TextView tv2=customLayout.findViewById(R.id.tv2);
        final String str= fbList.get(position);
        builder.setTitle(str);
        first = str.substring(0,10 );
        second = str.substring(11,str.length() - 6);
        third = str.substring(str.length() - 5);
        //Toast.makeText(Clientpage.this, second +third + first , Toast.LENGTH_LONG).show();
        //builder.setMessage(second +third + first);
        refFeedback.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //gList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Fback fb = data.getValue(Fback.class);
                    String d=fbList.get(position);
                    third = d.substring(d.length() - 5);
                    first = d.substring(0,10 );
                    //if (str!=null)
                    if (fb.getTime().equals(third)&&fb.getDate().equals(first)){
                        TextView tv=customLayout.findViewById(R.id.tv);
                        TextView tv2=customLayout.findViewById(R.id.tv2);
                        if (fb.getDone()) tv.setText("this is his feedback: " + fb.getFb());
                        if (!fb.getDone()) tv.setText("he didnt do it because: " + fb.getFb());
                        tv2.setText("he rated his performence: " + fb.getRate()+"/10");
                        //Toast.makeText(Clientpage.this, fb.getFb(),Toast.LENGTH_LONG).show();
                        builder.setMessage(fb.getcName());

                    }}
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        builder.setNeutralButton("back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity

                dialog.cancel();
            }
        });
        builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity
                refFeedback.child(first + " " + second).removeValue();
                recreate();
                dialog.dismiss();
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void ex1(View view) { finish(); }

    public void bbb(View view) {
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
        builder.setTitle("New Message To " + c);

        /**/
        builder.setPositiveButton("Send Message", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, int i) {
                final EditText edmsg2 = customLayout.findViewById(R.id.edmsg);
                final String msg = edmsg2.getText().toString();
                Messages m = new Messages(p,c,msg);
                refMsg.child(c+" "+msg).setValue(m);
                Toast.makeText(Clientpage.this, "Message Sent!" , Toast.LENGTH_LONG).show();
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
