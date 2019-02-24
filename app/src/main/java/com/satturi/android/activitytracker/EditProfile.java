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

public class EditProfile extends AppCompatActivity {

    String gender="Female";
    DatabaseHelper DB;
    EditText Name, Age, Gender, Height, Bweight, Gweight, GDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
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
        Cursor res=DB.getProfile();
        if(res.getCount()==0){
        }
        else
        {
            while(res.moveToNext())
            {
                Name.setText(res.getString(1));
                Age.setText(res.getString(2));
                Height.setText(res.getString(4));
                Bweight.setText(res.getString(5));
                Gweight.setText(res.getString(6));
                GDate.setText(res.getString(7));
            }
        }
    }
    public void Update(View view) {
        Boolean valid;
        if (GDate.getText().toString() != null) {
            SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
            f.setLenient(false);
            try {
                f.parse(GDate.getText().toString());
                valid = true;
            } catch (ParseException e) {
                valid = false;
                Toast.makeText(EditProfile.this, "Date is not in valid format mm/DD/yyyy", Toast.LENGTH_LONG).show();
            }
            if (valid == true) {

                boolean isInserted = DB.updateProfile(Name.getText().toString(),
                        Age.getText().toString(),
                        gender,
                        Height.getText().toString(),
                        Bweight.getText().toString(),
                        Gweight.getText().toString(),
                        GDate.getText().toString()
                );
                if (isInserted == true) {
                    Intent intent1 = new Intent(this, Home.class);
                    startActivity(intent1);
                    Toast.makeText(EditProfile.this, "Profile updated", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(EditProfile.this, "Update profile Incomplete Error Occured - Please Retry Again", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void Cancel(View view) {
        Intent intent1 = new Intent(this, Home.class);
        startActivity(intent1);
    }
}
