package com.example.idanbeta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.idanbeta.FBref.refExinfo;

public class NewEx extends AppCompatActivity {
    Button b1;
    Spinner s;
    ArrayList<String> typesList = new ArrayList<>();
    //TextView tv,tvtime;
    EditText et,et2,et3;
    String n,i,u,t;
    /**
     * on create activity - fills spinner up with body types
     * <p>
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ex);
        b1= findViewById(R.id.b);
        et=findViewById(R.id.editText);
        et2=findViewById(R.id.editText2);
        et3=findViewById(R.id.editText3);
        s=findViewById(R.id.spin);
        typesList.clear();
        typesList.add("Abs");
        typesList.add("Legs");
        typesList.add("Chest");
        typesList.add("Back");
        typesList.add("Arms");
        typesList.add("Shoulders");
        typesList.add("Cardio");
        ArrayAdapter a = new ArrayAdapter<String>(NewEx.this,R.layout.support_simple_spinner_dropdown_item, typesList);
        s.setAdapter(a);
    }
    /**
     * on bottom button clicked - takes all the info and send it to firebase
     * <p>
     *
     */

    public void sub(View view) {
        if (n.equals("")||u.equals("")||i.equals(""))
            Toast.makeText(NewEx.this, "You need to fill everything for that" , Toast.LENGTH_LONG).show();
        else {
            t = String.valueOf(s.getSelectedItem());
            n = et.getText().toString();
            i = et2.getText().toString();
            u = et3.getText().toString();
            Information information = new Information(n, u, i, t);
            refExinfo.child(n).setValue(information);
            Toast.makeText(NewEx.this, "Exercise added!", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
