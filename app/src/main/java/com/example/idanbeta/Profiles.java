package com.example.idanbeta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

import static com.example.idanbeta.FBref.refClients;
import static com.example.idanbeta.FBref.refImages;
import static com.example.idanbeta.FBref.refPhyios;

public class Profiles extends AppCompatActivity {

    String name, email, uid,p,c;
    TextView tVnameview, tVemailview, tVuidview,tv;
    CheckBox cBconnectview;
    Button ib2;
    ImageView ib;
    int Gallery=1;

    /**
     * on activity created - pulls user name from intent
     * <p>
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        tVnameview=(TextView)findViewById(R.id.tVnameview);
        tVemailview=(TextView)findViewById(R.id.tVemailview);
        tVuidview=(TextView)findViewById(R.id.tVuidview);
        tv=findViewById(R.id.title);
        ib=findViewById(R.id.ib);
        String cName=getIntent().getStringExtra("name");
        c=cName;
        StorageReference refImg = refImages.child(c + ".jpg");
        refImages.child(c+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    download();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //Toast.makeText(Profiles.this, "Feel free to show off your progress for your trainer" , Toast.LENGTH_LONG).show();

            }
        });



    }
    /**
     * on activity start - fills textviews with values from firebase
     * <p>
     *
     */
    @Override
    public void onStart() {
        super.onStart();

        refClients.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User u = ds.getValue(User.class);
                    //assert u != null;
                    if (c.equals(u.getName())) {
                        tVemailview.setText(u.getEmail());
                        tVnameview.setText(u.getName());
                        tVuidview.setText(u.getPhone());
                        tv.setText("Your Profile");
                        ib.setVisibility(View.VISIBLE);


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        refPhyios.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User u = ds.getValue(User.class);
                    //assert u != null;
                    if (c.equals(u.getName())) {
                        tVemailview.setText(u.getEmail());
                        tVnameview.setText(u.getName());
                        tVuidview.setText(u.getPhone());
                        tv.setText("Trainer's profile");

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    /**
     * on exit button clicked - closes activity
     * <p>
     *
     */
    public void update(View view) {

        finish();
    }
    /**
     * on picture click - choosing a profile picture
     * <p>
     *
     */
    public void ibb(View view) {
        Intent si = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(si, Gallery);
    }
    /**
     * Uploading selected image file to Firebase Storage
     * <p>
     *
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)  {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Gallery) {
                Uri file = data.getData();
                if (file != null) {
                    final ProgressDialog pd=ProgressDialog.show(this,"Upload image","Uploading...",true);
                    StorageReference refImg = refImages.child(c+".jpg");
                    refImg.putFile(file)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    pd.dismiss();
                                    Toast.makeText(Profiles.this, "Image Uploaded", Toast.LENGTH_LONG).show();
                                    try {
                                        download();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    pd.dismiss();
                                    Toast.makeText(Profiles.this, "Upload failed", Toast.LENGTH_LONG).show();
                                }
                            });
                } else {
                    Toast.makeText(this, "No Image was selected", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    /**
     * puts client picture on image view from firebase
     * <p>
     *
     */
    public void download() throws IOException {


        StorageReference refImg = refImages.child(c + ".jpg");

        final File localFile = File.createTempFile(c,"jpg");
        refImg.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                //pd.dismiss();
                Toast.makeText(Profiles.this, "Image download success", Toast.LENGTH_LONG).show();
                String filePath = localFile.getPath();
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                ib.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //pd.dismiss();
                Toast.makeText(Profiles.this, "Image download failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
