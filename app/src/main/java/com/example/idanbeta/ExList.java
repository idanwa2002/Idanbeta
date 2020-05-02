package com.example.idanbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.idanbeta.FBref.refExinfo;
import static com.example.idanbeta.FBref.refFeedback;

public class ExList extends AppCompatActivity implements AdapterView.OnItemClickListener {
ListView exl;
String str;
    ArrayList<String> xList = new ArrayList<>();
    Information n;
    TextView tvex;
    /**
     * on activity created - fills listview with all exercises
     * <p>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_list);
        exl = (ListView) findViewById(R.id.exll);
        tvex = findViewById(R.id.textView4);

        exl.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        exl.setOnItemClickListener(this);

        refExinfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Information information = data.getValue(Information.class);
                    //waitValues.add(user);
                    xList.add(information.gettName());

                }
                ArrayAdapter adp = new ArrayAdapter<String>(ExList.this,R.layout.support_simple_spinner_dropdown_item, xList);
                exl.setAdapter(adp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    /**
     * on listview clicked - opens dialog with info and link
     * <p>
     *
     */
    //@Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
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
        str= xList.get(position);

        builder.setTitle(str);
        refExinfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    n = data.getValue(Information.class);
                    //Toast.makeText(Patientlists.this, exList.get(position), Toast.LENGTH_LONG).show();
                    if (xList.get(position).equals(n.gettName())){
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
                                //Uri uri = Uri.parse("http://www." + n.getUrl()); // missing 'http://' will cause crashed
                                Uri uri = Uri.parse(n.getUrl());
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
    }
    /**
     * on bottom button clicked - finishes activity
     * <p>
     *
     */
    public void ex(View view) {
        finish();
    }

    /**
     * on (type) button clicked - fills listview with only exercises of that type
     * <p>
     *
     */
    public void cardio(View view) {
        tvex.setText("Cardio Exercises:");
        refExinfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Information information = data.getValue(Information.class);
                    //waitValues.add(user);
                    if (information.getType().equals("Cardio"))
                    xList.add(information.gettName());

                }
                ArrayAdapter adp = new ArrayAdapter<String>(ExList.this,R.layout.support_simple_spinner_dropdown_item, xList);
                exl.setAdapter(adp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void legs(View view) {
        tvex.setText("Legs Exercises:");
        refExinfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Information information = data.getValue(Information.class);
                    //waitValues.add(user);
                    if (information.getType().equals("Legs"))
                        xList.add(information.gettName());

                }
                ArrayAdapter adp = new ArrayAdapter<String>(ExList.this,R.layout.support_simple_spinner_dropdown_item, xList);
                exl.setAdapter(adp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    /**
     * on cardio button clicked - fills listview with only exercises of that type
     * <p>
     *
     */
    public void arms(View view) {
        tvex.setText("Arms Exercises:");
        refExinfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Information information = data.getValue(Information.class);
                    //waitValues.add(user);
                    if (information.getType().equals("Arms"))
                        xList.add(information.gettName());

                }
                ArrayAdapter adp = new ArrayAdapter<String>(ExList.this,R.layout.support_simple_spinner_dropdown_item, xList);
                exl.setAdapter(adp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void abs(View view) {
        tvex.setText("Abs Exercises:");
        refExinfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Information information = data.getValue(Information.class);
                    //waitValues.add(user);
                    if (information.getType().equals("Abs"))
                        xList.add(information.gettName());

                }
                ArrayAdapter adp = new ArrayAdapter<String>(ExList.this,R.layout.support_simple_spinner_dropdown_item, xList);
                exl.setAdapter(adp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void chest(View view) {
        tvex.setText("Cardio Exercises:");
        refExinfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Information information = data.getValue(Information.class);
                    //waitValues.add(user);
                    if (information.getType().equals("Chest"))
                        xList.add(information.gettName());

                }
                ArrayAdapter adp = new ArrayAdapter<String>(ExList.this,R.layout.support_simple_spinner_dropdown_item, xList);
                exl.setAdapter(adp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void back(View view) {
        tvex.setText("Back Exercises:");
        refExinfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Information information = data.getValue(Information.class);
                    //waitValues.add(user);
                    if (information.getType().equals("Back"))
                        xList.add(information.gettName());

                }
                ArrayAdapter adp = new ArrayAdapter<String>(ExList.this,R.layout.support_simple_spinner_dropdown_item, xList);
                exl.setAdapter(adp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void shoulders(View view) {
        tvex.setText("Shoulders Exercises:");
        refExinfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Information information = data.getValue(Information.class);
                    //waitValues.add(user);
                    if (information.getType().equals("Shoulders"))
                        xList.add(information.gettName());

                }
                ArrayAdapter adp = new ArrayAdapter<String>(ExList.this,R.layout.support_simple_spinner_dropdown_item, xList);
                exl.setAdapter(adp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
