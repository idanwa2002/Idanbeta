package com.example.idanbeta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import static com.example.idanbeta.FBref.refAuth;
import static com.example.idanbeta.FBref.refClients;
import static com.example.idanbeta.FBref.refPhyios;
//import static com.example.idanbeta.FBref.refUser;

public class Lognreg extends AppCompatActivity {
    TextView tVtitle, tVregister, tVnameview,inv;
    EditText eTname, eTphone, eTemail, eTpass;
    CheckBox cBstayconnect;
    Button btn;
    CheckBox cb;
    Spinner sp;
    String str1, str2;
    String name, phone, email, password, uid, phy = "none";
    User userdb;
    Boolean stayConnect, registered, firstrun, cbphy, isPhysio, perm;
    public FirebaseAuth mAuth;
    String[] Parray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lognreg);

        tVtitle=(TextView) findViewById(R.id.tVtitle);
        eTname=(EditText)findViewById(R.id.eTname);
        eTemail=(EditText)findViewById(R.id.eTemail);
        eTpass=(EditText)findViewById(R.id.eTpass);
        eTphone=(EditText)findViewById(R.id.eTphone);
        cBstayconnect=(CheckBox)findViewById(R.id.cBstayconnect);
        tVregister=(TextView) findViewById(R.id.tVregister);
        btn=(Button)findViewById(R.id.btn);
        tVnameview=(TextView) findViewById(R.id.tVname);
        inv=(TextView) findViewById(R.id.inv);
        cb=(CheckBox) findViewById(R.id.cbx);
        sp=(Spinner) findViewById(R.id.spn);
        //sp.setOnItemSelectedListener(this);
        //addItemsOnSpinner(); //************

        mAuth = FirebaseAuth.getInstance();

        stayConnect=false;
        registered=true;

        final List<String> pList = new ArrayList<>();

        //DatabaseReference r = FirebaseDatabase.getInstance().getReference("Game");
        //r.child("Teams").addListenerForSingleValueEvent(new ValueEventListener()
        refPhyios.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User u = ds.getValue(User.class);
                    pList.add(u.getName());
                }
                ArrayAdapter<String> Adp = new ArrayAdapter<>(Lognreg.this, R.layout.support_simple_spinner_dropdown_item, pList);
                sp.setAdapter(Adp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        firstrun=settings.getBoolean("firstRun",false);
        //Toast.makeText(this, ""+firstrun, Toast.LENGTH_SHORT).show();
        if (firstrun) {
            tVtitle.setText("Register");
            eTname.setVisibility(View.VISIBLE);
            eTphone.setVisibility(View.VISIBLE);
            inv.setVisibility(View.VISIBLE);
            btn.setText("Register");
            registered=false;
            logoption();
        }
        else regoption();
    }




    /**
     * On activity start - Checking if user already logged in.
     * If logged in & asked to be remembered - pass on.
     * <p>
     */
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        //Boolean isChecked=false;
        Boolean isChecked=settings.getBoolean("stayConnect",false);
        isPhysio=settings.getBoolean("is physio",false);
        //isChecked = false;
        isPhysio = false;
        if (refAuth.getCurrentUser()!=null && isChecked ) {
            stayConnect=true;
            Intent si = new Intent(Lognreg.this,Passing.class);
            startActivity(si);
        }
        //if (refAuth.getCurrentUser()!=null && isChecked && !isPhysio ) {
         //   stayConnect=true;
         //   Intent si = new Intent(Lognreg.this,Passing.class);
         //   startActivity(si);
        //}
    }

    /**
     * On activity pause - If logged in & asked to be remembered - kill activity.
     * <p>
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (stayConnect) finish();
    }

    private void regoption() {
        SpannableString ss = new SpannableString("Don't have an account?  Register here!");
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                tVtitle.setText("Register");
                eTname.setVisibility(View.VISIBLE);
                eTphone.setVisibility(View.VISIBLE);
                sp.setVisibility(View.VISIBLE);
                cb.setVisibility(View.VISIBLE);
                inv.setVisibility(View.VISIBLE);
                btn.setText("Register");
                registered=false;
                logoption();
            }
        };
        ss.setSpan(span, 24, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tVregister.setText(ss);
        tVregister.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void logoption() {
        SpannableString ss = new SpannableString("Already have an account?  Login here!");
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                tVtitle.setText("Login");
                eTname.setVisibility(View.INVISIBLE);
                eTphone.setVisibility(View.INVISIBLE);
                sp.setVisibility(View.INVISIBLE);
                cb.setVisibility(View.INVISIBLE);
                inv.setVisibility(View.INVISIBLE);
                btn.setText("Login");
                registered=true;
                regoption();
            }
        };
        ss.setSpan(span, 26, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tVregister.setText(ss);
        tVregister.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * Logging in or Registering to the application
     * Using:   Firebase Auth with email & password
     *          Firebase Realtime database with the object User to the branch Users
     * If login or register process is Ok saving stay connect status & pass to next activity
     * <p>
     */
    public void logorreg(View view) {
        if ((eTemail.getText().toString().equals(""))||(eTpass.getText().toString().equals(""))) {
            Toast.makeText(Lognreg.this, "You need to fill everything to continue!", Toast.LENGTH_LONG).show();
        }
        else {
        email=eTemail.getText().toString();
        password=eTpass.getText().toString();
        if (registered) {
            final ProgressDialog pd=ProgressDialog.show(this,"Login","Connecting...",true);
            refAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();
                            if (task.isSuccessful()) {
                                SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
                                SharedPreferences.Editor editor=settings.edit();
                                editor.putBoolean("stayConnect",cBstayconnect.isChecked());
                                editor.apply(); //changed from commit
                                isPhysio=settings.getBoolean("is physio",false);
                                isPhysio=false;
                                Log.d("MainActivity", "signinUserWithEmail:success");
                                Toast.makeText(Lognreg.this, "Login Success", Toast.LENGTH_LONG).show();
                                //if (isPhysio ) {
                                    //Intent si = new Intent(Lognreg.this,Physiolists.class);
                                    //startActivity(si);
                                //}
                                //if (!isPhysio ) {
                                    Intent si = new Intent(Lognreg.this,Passing.class);
                                    startActivity(si);
                                //}
                            } else {
                                Log.d("MainActivity", "signinUserWithEmail:fail");
                                Toast.makeText(Lognreg.this, "e-mail or password are wrong!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            if ((eTemail.getText().toString().equals(""))||(eTpass.getText().toString().equals(""))||(eTname.getText().toString().equals(""))||(eTphone.getText().toString().equals(""))) {
                Toast.makeText(Lognreg.this, "You need to fill everything to continue!", Toast.LENGTH_LONG).show();
            }
            else{
            name=eTname.getText().toString();
            phone=eTphone.getText().toString();
            cbphy=cb.isChecked();
            //phy=String.valueOf(sp.getSelectedItem());
            if (cb.isChecked()){
                phy="none"; perm = true;
            }
            else{
                phy=String.valueOf(sp.getSelectedItem()); perm = false;
            }
            final ProgressDialog pd=ProgressDialog.show(this,"Register","Registering...",true);
            refAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();
                            if (task.isSuccessful()) {
                                SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
                                SharedPreferences.Editor editor=settings.edit();
                                editor.putBoolean("stayConnect",cBstayconnect.isChecked());
                                editor.putBoolean("firstRun",false);
                                editor.putBoolean("is physio",cb.isChecked());
                                editor.apply(); //changed from commit
                                Log.d("MainActivity", "createUserWithEmail:success");
                                FirebaseUser user = refAuth.getCurrentUser();
                                uid = user.getUid();
                                userdb=new User(name,email,phone,uid,phy,perm);
                                //refUser.child(name).setValue(userdb);
                                Toast.makeText(Lognreg.this, "Successful registration", Toast.LENGTH_LONG).show();
                                if (cbphy){
                                    refPhyios.child(name).setValue(userdb);

                                    Intent si = new Intent(Lognreg.this,Passing.class);
                                    startActivity(si);
                                }
                                else {
                                    refClients.child(name).setValue(userdb);
                                    Intent si = new Intent(Lognreg.this,Passing.class);
                                    startActivity(si);
                                }
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                    Toast.makeText(Lognreg.this, "User with e-mail already exist!", Toast.LENGTH_LONG).show();
                                else {
                                    Log.w("MainActivity", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(Lognreg.this, "User create failed.",Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
        }}
    }}
}