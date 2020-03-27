package com.example.idanbeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.idanbeta.FBref.refTest;

public class MainActivity extends AppCompatActivity {
Button but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        but = (Button) findViewById(R.id.but);
    }





    public void n(View view) {

        Intent si = new Intent(MainActivity.this,Lognreg.class);
        startActivity(si);

    }






}
