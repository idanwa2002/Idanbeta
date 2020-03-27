package com.example.idanbeta;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FBref {
    //public static FirebaseDatabase FAB = FirebaseDatabase.getInstance();
    //public static DatabaseReference myRef= FAB.getReference("message");

    public static FirebaseAuth refAuth=FirebaseAuth.getInstance();

    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();
    public static DatabaseReference refUser=FBDB.getReference("User");
    public static DatabaseReference refTest=FBDB.getReference("Test");



}
