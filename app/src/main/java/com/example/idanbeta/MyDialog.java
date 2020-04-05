package com.example.idanbeta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

public class MyDialog extends AppCompatDialogFragment {
    private MyDialogListener listener;
    TextView tv,tvad;
    EditText etad;
    CheckBox cbab;
    SeekBar sbad;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.costum_dialog_layout, null);

        builder.setView(view)
                .setTitle("Login")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("submit feedback", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String username = etad.getText().toString();
                        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                        Calendar cal = Calendar.getInstance();
                        Date time1=cal.getTime();
                        //time1.
                        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                        String time=dateFormat.format(time1);
                        //Fback fback = new Fback(etad.getText().toString(),time,date,cbab.isChecked(),sbad.getProgress(),);
                        listener.applyTexts(username, cbab.isChecked(),date,time, sbad.getProgress());
                        
                    }
                });

        etad = view.findViewById(R.id.ett);
        tvad = view.findViewById(R.id.ttvv);
        sbad = view.findViewById(R.id.sb);
        cbab = view.findViewById(R.id.cbab);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (MyDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface MyDialogListener {
        void applyTexts(String fdb, Boolean did, String date, String time, int rating);
    }
}


        //return builder.create();
        //return super.onCreateDialog(savedInstanceState);

