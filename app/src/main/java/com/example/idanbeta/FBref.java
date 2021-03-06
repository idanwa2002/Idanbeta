package com.example.idanbeta;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FBref {
    //public static FirebaseDatabase FAB = FirebaseDatabase.getInstance();
    //public static DatabaseReference myRef= FAB.getReference("message");

    public static FirebaseAuth refAuth=FirebaseAuth.getInstance();

    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();
    //public static DatabaseReference refUser=FBDB.getReference("User");
    //public static DatabaseReference refTest=FBDB.getReference("Test");
    public static DatabaseReference refPhyios=FBDB.getReference("Physios");
    public static DatabaseReference refClients=FBDB.getReference("Clients");
    public static DatabaseReference refTasks=FBDB.getReference("Tasks");
    public static DatabaseReference refFeedback=FBDB.getReference("Feedback");
    public static DatabaseReference refExinfo=FBDB.getReference("Exinfo");
    public static DatabaseReference refMsg=FBDB.getReference("Messages");

    public static FirebaseStorage FBST = FirebaseStorage.getInstance();
    public static StorageReference refStor=FBST.getReference();
    public static StorageReference refImages=refStor.child("Images");


}
