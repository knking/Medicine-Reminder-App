package com.krishna.medicinereminder;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    ImageView plus;
    ListView listView;
    ArrayList<String> totalList;
    ArrayList<String> nameList;



    SQLiteDatabase database;
    Rminder rminder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this, " Click Add button to set reminder", Toast.LENGTH_LONG).show();
        plus = findViewById(R.id.imageView5);
        listView = findViewById(R.id.listview);

        totalList = new ArrayList<>();
        nameList = new ArrayList<>();

        rminder = new Rminder(this);
        Cursor cursor = rminder.getdata();

        database = rminder.getWritableDatabase();

        if (cursor.moveToFirst()) {
            do {
                String name_f = cursor.getString(cursor.getColumnIndex("Name"));
                String date_f = cursor.getString(cursor.getColumnIndex("Date"));
                String time_f = cursor.getString(cursor.getColumnIndex("Time"));

                nameList.add(name_f);
                totalList.add(name_f + " " + date_f + " " + time_f);

            }
            while (cursor.moveToNext());
        }
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, totalList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure want to delete ?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        rminder.ondelete(nameList.get(position));

                        totalList.remove(position);

                        arrayAdapter.notifyDataSetChanged();

                        Toast.makeText(MainActivity.this, "Item Deleted", Toast.LENGTH_LONG).show();
                        //

                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

                return true;
            }

        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
    }
}
