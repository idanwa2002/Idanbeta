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
import android.widget.Toast;

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
    int num;

    Date date = Calendar.getInstance().getTime();
    String thisdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    /**
     * gets date and returns the same date plus 1 day
     * <p>
     *
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    static public String addOneDay(String date) {
        return LocalDate.parse(date).plusDays(1).toString();
    }

    ArrayList<String> tList = new ArrayList<>(), mList = new ArrayList<>(), hList = new ArrayList<>();
    ArrayList<String> dList = new ArrayList<>();

    /**
     * on activity create - fills spinner with exercises from firebase
     * <p>
     *
     */

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



        dList.clear();
        int j = 9;
        while (j>0){
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
    }

    /**
     * on click setting time button - opens alertdialog with time picker fragment
     * <p>
     *
     */

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
    /**
     * on submit button clicked - putting the data set by the trainer and sending it to the client using firebase
     * <p>
     *
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void enter(View view) throws ParseException {
        days=String.valueOf((String) sp2.getSelectedItem());
        num =  Character.getNumericValue(days.charAt(0));
        tName=String.valueOf(sp.getSelectedItem());
        advice=et2.getText().toString();
        Exercises ex;
        if (advice.equals("")||time.equals(""))
            Toast.makeText(Addnew.this, "You need to fill everything for that" , Toast.LENGTH_LONG).show();
        else {
            int j = 0, ddd = num;
            while (j < (ddd)) {
                thisdate = addOneDay(thisdate);
                ex = new Exercises(p, c, time, advice, tName, thisdate, true);
                refTasks.child(thisdate + " " + tName + " " + c).setValue(ex);
                j++;
            }
        }
    }


}
