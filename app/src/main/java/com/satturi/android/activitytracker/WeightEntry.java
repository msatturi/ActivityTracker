package com.satturi.android.activitytracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class WeightEntry extends Fragment {
    DatabaseHelper myDb;
    EditText eweight;
    TextView edate;
    Button submit;
    ImageView img;
    Bitmap b;
    Boolean pic=true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_weight_entry, container, false);
        myDb = new DatabaseHelper(getActivity());
        edate = (EditText)view.findViewById(R.id.edate);
        eweight = (EditText)view.findViewById(R.id.eweight);
        img = (ImageView)view.findViewById(R.id.eimage);
        submit=view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean valid;
/*                if(pic) {
                    Toast.makeText(getActivity(), "Picture is mandatory ", Toast.LENGTH_LONG).show();
                }
                else*/
                    if (edate.getText().toString()!=null )
                    {   SimpleDateFormat f=new SimpleDateFormat("MM/dd/yyyy");
                        f.setLenient(false);
                        try
                        {
                            f.parse(edate.getText().toString());
                            valid = true;
                        } catch (ParseException e) {
                            valid = false;
                            Toast.makeText(getActivity(),"Date is not in valid format mm/DD/yyyy", Toast.LENGTH_LONG).show();
                        }
                        if (valid == true)
                        {
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            b.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            boolean isInserted = myDb.insertweight(edate.getText().toString(),
                                    eweight.getText().toString(), byteArray


                            );
                            if (isInserted == true) {
                                Intent intent1 = new Intent(getActivity(), Home.class);
                                startActivity(intent1);
                                Toast.makeText(getActivity(), "Data Inserted", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(getActivity(), "Data not Inserted !!! Please try again", Toast.LENGTH_LONG).show();
                        }

                    }


 /*               else {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    b.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    boolean isInserted = myDb.insertweight(edate.getText().toString(),
                            eweight.getText().toString(), byteArray


                    );
                    if (isInserted == true) {
                        Intent intent1 = new Intent(getActivity(), Home.class);
                        startActivity(intent1);
                        Toast.makeText(getActivity(), "Data Inserted", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getActivity(), "Data not Inserted !!! Please try again", Toast.LENGTH_LONG).show();
                }*/
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraintent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraintent,10);
                pic=false;
            }
        });
                return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            if(requestCode==10)
            {
                b=(Bitmap)data.getExtras().get("data");
                img.setImageBitmap(b);
            }
        }
    }
}
