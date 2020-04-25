package com.example.idanbeta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.idanbeta.FBref.refExinfo;

public class NewEx extends AppCompatActivity {
    Button b1;
    //TextView tv,tvtime;
    EditText et,et2,et3;
    String n,i,u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ex);
        b1= findViewById(R.id.b);
        et=findViewById(R.id.editText);
        et2=findViewById(R.id.editText2);
        et3=findViewById(R.id.editText3);
    }

    public void sub(View view) {
        n= et.getText().toString();
        i= et2.getText().toString();
        u= et3.getText().toString();
        Information information = new Information(n,u,i);
        refExinfo.child(n).setValue(information);
        Toast.makeText(NewEx.this, "Exercise added!", Toast.LENGTH_LONG).show();
        finish();
    }
}
