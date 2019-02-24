package com.satturi.android.activitytracker;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    String gender="Female";
    DatabaseHelper DB;
    EditText Name, Age, Gender, Height, Bweight, Gweight, GDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB = new DatabaseHelper(this);
        Spinner sgender=(Spinner)findViewById(R.id.egender);
        sgender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(
        ) {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender=parent.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gender="Female";
            }
        });
        Name = (EditText) findViewById(R.id.Name);
        Age = (EditText) findViewById(R.id.Age);
        Height = (EditText) findViewById(R.id.Height);
        Bweight = (EditText) findViewById(R.id.Weight);
        Gweight = (EditText) findViewById(R.id.GoalWeight);
        GDate = (EditText) findViewById(R.id.GoalDate);
        Cursor res = DB.getProfile();
        if (res.getCount() == 0) {

        } else {
            Intent intent1 = new Intent(this, Home.class);
            startActivity(intent1);
        }
    }
    public void Submit(View view) {
        Boolean valid;
        if (GDate.getText().toString() != null) {
            SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
            f.setLenient(false);
            try {
                f.parse(GDate.getText().toString());
                valid = true;
            } catch (ParseException e) {
                valid = false;
                Toast.makeText(MainActivity.this, "Date is not in valid format mm/DD/yyyy", Toast.LENGTH_LONG).show();
            }
            if (valid == true) {

                boolean isInserted = DB.insertProfile(Name.getText().toString(), Age.getText().toString(), gender,
                        Height.getText().toString(), Bweight.getText().toString(), Gweight.getText().toString(), GDate.getText().toString());
                if (isInserted == true) {
                    Toast.makeText(MainActivity.this, "Profile Created", Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(this, Home.class);
                    startActivity(intent1);
                } else
                    Toast.makeText(MainActivity.this, "Error Creating Profile, Please re-try", Toast.LENGTH_LONG).show();

            }
        }
    }
}

