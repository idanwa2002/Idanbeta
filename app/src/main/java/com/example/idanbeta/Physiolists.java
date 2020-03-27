package com.example.idanbeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import static com.example.idanbeta.FBref.refAuth;

public class Physiolists extends AppCompatActivity {
    ListView patlv, penlv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physiolists);
        patlv=(ListView) findViewById(R.id.clientslv);
        penlv=(ListView) findViewById(R.id.pendinglv);
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
}
