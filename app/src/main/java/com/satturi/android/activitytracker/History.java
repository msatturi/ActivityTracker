package com.satturi.android.activitytracker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link History.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link History#newInstance} factory method to
 * create an instance of this fragment.
 */
public class History extends Fragment {
    DatabaseHelper myDb;
    TableLayout tableLayout;
    TableRow tableRow;
    TextView tweight,tdate;
    ImageView image;
    Button delete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_history, container, false);

        myDb = new DatabaseHelper(getActivity());
        tableLayout =  view.findViewById(R.id.maintable);
        t1Headers();
        t1Data();
        return view;
    }
    public void t1Headers(){
        /* Create a TableRow dynamically */
        tableRow = new TableRow(getActivity());
        /* weight */
        TextView weight = new TextView(getActivity());
        weight.setText("Weight");
        weight.setTextColor(Color.GRAY);
        weight.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        weight.setPadding(3, 3, 3, 0);
        tableRow.addView(weight);	// Adding textView to tablerow.
        /* date*/
        TextView date = new TextView(getActivity());
        date.setText("Date");
        date.setTextColor(Color.GRAY);
        date.setPadding(3, 3, 3, 0);
        date.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tableRow.addView(date); // Adding textView to tablerow.
        /* image*/
        TextView pic = new TextView(getActivity());
        pic.setText("Image");
        pic.setTextColor(Color.GRAY);
        pic.setPadding(3, 3, 3, 0);
        pic.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tableRow.addView(pic); // Adding textView to tablerow.
        /* delete*/
        TextView deletebutton = new TextView(getActivity());
        deletebutton.setText("   ");
        deletebutton.setTextColor(Color.GRAY);
        deletebutton.setPadding(3, 3, 3, 0);
        deletebutton.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tableRow.addView(deletebutton); // Adding textView to tablerow.
        // Add the TableRow to the TableLayout
        tableLayout.addView(tableRow);
        // we are adding two textviews for the divider because we have two columns
        tableRow = new TableRow(getActivity());
        /* Creating another textview */
        TextView divider = new TextView(getActivity());
        divider.setText("-----------------");
        divider.setTextColor(Color.GREEN);
        divider.setPadding(3, 3, 3, 0);
        divider.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tableRow.addView(divider); // Adding textView to tablerow.
        TextView divider2 = new TextView(getActivity());
        divider2.setText("-----------------");
        divider2.setTextColor(Color.GREEN);
        divider2.setPadding(3, 3, 3, 0);
        divider2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tableRow.addView(divider2); // Adding textView to tablerow.
        TextView divider3 = new TextView(getActivity());
        divider3.setText("-----------------");
        divider3.setTextColor(Color.GREEN);
        divider3.setPadding(3, 3, 3, 0);
        divider3.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tableRow.addView(divider3); // Adding textView to tablerow.
        TextView divider4 = new TextView(getActivity());
        divider4.setText("     ");
        divider4.setTextColor(Color.GREEN);
        divider4.setPadding(3, 3, 3, 0);
        divider4.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tableRow.addView(divider4); // Adding textView to tablerow.
        // Add the TableRow to the TableLayout
        tableLayout.addView(tableRow);
    }
    public void t1Data(){
        myDb = new DatabaseHelper(getActivity());
        Cursor res=myDb.getAllWeights();
        if(res.getCount()==0)        {        }
        else
        {
            while(res.moveToNext())
            {
                /** Create a TableRow dynamically **/
                tableRow = new TableRow(getActivity());
                tweight = new TextView(getActivity());
                tweight.setText(res.getString(2));
                tweight.setTextColor(Color.RED);
                tweight.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                tweight.setPadding(3, 3, 3, 0);
                tableRow.addView(tweight);	// Adding textView to tablerow.
                tdate = new TextView(getActivity());
                tdate.setText(res.getString(1));
                tdate.setTextColor(Color.RED);
                tdate.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                tdate.setPadding(3, 3, 3, 0);
                tableRow.addView(tdate);	// Adding textView to tablerow.
                image = new ImageView(getActivity());
                byte[] bimage=res.getBlob(3);
                Bitmap b= BitmapFactory.decodeByteArray(bimage,0,bimage.length);
                image.setImageBitmap(b);
                image.setPadding(3, 3, 3, 0);
                tableRow.addView(image);	// Adding textView to tablerow.
                // ---- Delete Button -----
                delete =new Button(getActivity());
                delete.setText("Delete");
                tableRow.addView(delete);
                final int s=Integer.parseInt(res.getString(0));
                delete.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Boolean del=myDb.deleteWeight(s);
                        if(del)
                        {
                            Intent intent1 = new Intent(getActivity(), Home.class);
                            startActivity(intent1);
                            Toast.makeText(getActivity(), "Record Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                // Add the TableRow to the TableLayout
                tableLayout.addView(tableRow);
            }
        }
    }
}
