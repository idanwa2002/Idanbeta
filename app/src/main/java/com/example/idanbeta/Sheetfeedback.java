package com.example.idanbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.idanbeta.FBref.refFeedback;
import static com.example.idanbeta.FBref.refTasks;

public class Sheetfeedback extends AppCompatActivity {
    TextView tv,tvad;
    EditText etad;
    CheckBox cbab;
    SeekBar sbad;
    String str,first,second, both,c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheetfeedback);
        etad = findViewById(R.id.ett);
        tvad = findViewById(R.id.ttvv);
        sbad = findViewById(R.id.sb);
        cbab = findViewById(R.id.cbab);
        str=getIntent().getStringExtra("name");
        String dd=getIntent().getStringExtra("cname");
        c=dd;
        both = str;
        first = both.substring(0,10);
        second = str.substring(11);
        tvad.setText("please fill this feedback sheet for " + second + " in " + first);/*
        refTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //gList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Exercises ex = data.getValue(Exercises.class);
                    if (str!=null)
                        if (ex.gettName().equals(second)&&ex.getDate().equals(first)){
                            tvad.setText("Your physio's advice: " + ex.getAdvice());
                        }}
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        refTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //gList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Exercises ex = data.getValue(Exercises.class);
                    if (ex.getDate()!=null&&ex.gettName()!=null&&ex.getAdvice()!=null)
                        if (ex.gettName().equals(second)&&ex.getDate().equals(first)){
                            //tvad.setText("Your physio's advice: " + ex.getAdvice());
                        }}
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void submit(View view) {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Calendar cal = Calendar.getInstance();
        Date time1=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String time=dateFormat.format(time1);
        Fback fback=new Fback(c,etad.getText().toString(),time,date,cbab.isChecked(),sbad.getProgress(),second);
        //Fback fback1=new Fback(etad.getText().toString(),)

        refFeedback.child(both).setValue(fback);
        refTasks.child("1 " + c + " "+ second).removeValue();
        Toast.makeText(Sheetfeedback.this, "Feedback sent!" , Toast.LENGTH_LONG).show();

        //Intent in = new Intent(Physiolists.this, Addnew.class);
        finish();
        //Intent di= new Intent(Sheetfeedback.this, Patientlists.class);
        //di.putExtra("name",str);
        //startActivity(di);

    }
}
