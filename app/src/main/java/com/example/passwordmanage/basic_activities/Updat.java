package com.example.passwordmanage.basic_activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.passwordmanage.CustomHelper;
import com.example.passwordmanage.R;
import com.example.passwordmanage.models.Entries_Model;
import com.google.android.material.snackbar.Snackbar;

public class Updat extends AppCompatActivity {
    EditText id, name, word, tag;
    Button update, delete;
    CustomHelper ch;
    int idi;
    Entries_Model entries_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updat);
        String Sname,Stag;
        id = findViewById(R.id.editTextTextPersonName4);
        name = findViewById(R.id.editTextTextPersonName5);
        word = findViewById(R.id.editTextTextPersonName6);
        tag = findViewById(R.id.editTextTextPersonName8);
        update = findViewById(R.id.button7);
        delete = findViewById(R.id.delete);
        entries_model = new Entries_Model();
        Intent intent = getIntent();
        idi = intent.getExtras().getInt("id");
        Sname = intent.getExtras().getString("name");
        name.setText(Sname);
        Stag = intent.getExtras().getString("tag");
        tag.setText(Stag);
        id.setText(String.valueOf(idi));
        id.setEnabled(false);
        ch = new CustomHelper(this);
        Context context = this.getApplicationContext();
        delete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.custom_alert);
            builder.setMessage("Confirm delete ?");
            builder.setIcon(R.drawable.ic_baseline_delete);
            builder.setTitle("Delete \""+Stag+"\"");

            builder.setCancelable(false);
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    ch.delete(idi);
                    finish();

                }
            });
            builder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        update.setOnClickListener(v -> {
            String i, n, p, t;
            i = id.getText().toString();
            entries_model.setId(Integer.parseInt(i));
            n = name.getText().toString();
            entries_model.setName(n);
            p = word.getText().toString();
            entries_model.setWord(p);
            t = tag.getText().toString();
            entries_model.setTag(t);
            if (i.length() != 0 && n.length() != 0 && p.length() != 0 && t.length() != 0) {
                int ret = ch.update(entries_model);
                if (ret == 1) {
                    View parentLayout;
                    parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "Successful" + ret, Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else {
                    View parentLayout;
                    parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "Unsuccessful" + ret, Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            } else {
                View parentLayout;
                parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "Enter Something", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
    }
}