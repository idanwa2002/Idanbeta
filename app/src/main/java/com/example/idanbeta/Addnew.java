package com.example.idanbeta;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.idanbeta.FBref.refExinfo;
import static com.example.idanbeta.FBref.refPhyios;
import static com.example.idanbeta.FBref.refTasks;

public class Addnew extends AppCompatActivity {
    Button b1, b2,idd;
    TextView tv,tvtime;
    EditText et,et2;
    Spinner sp,sp2;
    String advice, tName,url="tbd",c,p;
    String time,days;
    //private Date date;
    int num;

    Date date = Calendar.getInstance().getTime();
    String thisdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

    @RequiresApi(api = Build.VERSION_CODES.O)
    static public String addOneDay(String date) {
        return LocalDate.parse(date).plusDays(1).toString();
    }

    ArrayList<String> tList = new ArrayList<>(), mList = new ArrayList<>(), hList = new ArrayList<>();
    ArrayList<String> dList = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.O) ///////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);
        b1= findViewById(R.id.button);
        b2=findViewById(R.id.button2);
        tv=findViewById(R.id.textView);
        sp2=findViewById(R.id.spinner2);
        sp=findViewById(R.id.spinner);
        idd=findViewById(R.id.idd);
        et2=findViewById(R.id.et2);
        tvtime=findViewById(R.id.textView5);


        String cName=getIntent().getStringExtra("name");
        String pName=getIntent().getStringExtra("pname");
        c=cName;
        p=pName;

        /*b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });*/

        dList.clear();
        int j = 9;
        while (j>0){
            //String s = String.valueOf(j);
            dList.add(j + " days");
            j--;
        }

        ArrayAdapter aa = new ArrayAdapter<String>(Addnew.this,R.layout.support_simple_spinner_dropdown_item, dList);
        sp2.setAdapter(aa);

        refExinfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Information information = data.getValue(Information.class);
                    //waitValues.add(user);
                    tList.add(information.gettName());

                }
                ArrayAdapter a = new ArrayAdapter<String>(Addnew.this,R.layout.support_simple_spinner_dropdown_item, tList);
                sp.setAdapter(a);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        tv.setText("Set a new task for " + cName);
        //tv.setText(thisdate+"        " + addOneDay(thisdate) + "   " +date );
    }


    public void chooseTime(View view) {
        TimePickerDialog picker = new TimePickerDialog(Addnew.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        time=sHour + ":" + sMinute;
                        tvtime.setText(time);
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
        picker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        picker.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void enter(View view) throws ParseException {
        days=String.valueOf((String) sp2.getSelectedItem());
        num =  Character.getNumericValue(days.charAt(0));
        //SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        //String formattedDate = sdf.format(date);
        //String dt = "2008-01-01";  // Start date
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //Calendar ca = Calendar.getInstance();
        //ca.setTime(sdf.parse(thisdate));
        //ca.add(Calendar.DATE, 1);  // number of days to add
        //thisdate = sdf.format(ca.getTime());  // dt is now the new date
        tName=String.valueOf(sp.getSelectedItem());
        advice=et2.getText().toString();
        Exercises ex;// = new Exercises(p,c,time,advice,tName,thisdate,true);
        int j=0,ddd=num;
        while (j<(ddd)){
            thisdate=addOneDay(thisdate);
            ex = new Exercises(p,c,time,advice,tName,thisdate,true);
            refTasks.child( thisdate+ " " + tName + " " + c).setValue(ex);
            j++;
        }

   //     Intent in = new Intent(Addnew.this, Clientpage.class);
     //   in.putExtra("name",c);
       // in.putExtra("pname",p);
        //startActivity(in);
        finish();
    }



    /*@Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        tv.setText(i + ":" + i1);
    }*/

}
