package com.example.idanbeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.idanbeta.FBref.refExinfo;
import static com.example.idanbeta.FBref.refTasks;

//import static com.example.idanbeta.FBref.refTest;

public class MainActivity extends AppCompatActivity {
Button but;
String url,tName,info;
Information information = new Information("Task 1","youtube.com","this is a good exercise to strenghten the legs");
    Information information1 = new Information("Task 2","google.com","this is a good exercise to improve speech and tongue movement");
    Information information2 = new Information("Task 3","facebook.com","this is an exercise that helps recover from a knee injury");
    String thisdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        but = (Button) findViewById(R.id.but);
        //refExinfo.child("task 1").setValue(information);
        //refExinfo.child("task 2").setValue(information1);
      //  refExinfo.child("task 3").setValue(information2);
        //Exercises ex = new Exercises("physio","client2","12:00","do this to test","Task 3",3,thisdate,true);
        //refTasks.child("test me").setValue(ex);

    }





    public void n(View view) {

        Intent si = new Intent(MainActivity.this,Lognreg.class);
        startActivity(si);

    }






}
