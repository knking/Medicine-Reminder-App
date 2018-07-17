package com.krishna.medicinereminder;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Main2Activity extends AppCompatActivity {
    EditText name;
    TextView date,time;
    Button datePick,timepick;
    ImageView setTime,setDate;

    private int mYear, mMonth, mDay, mHour, mMinute;
    ImageView cal,clk;

    private int userYear,userMonth,userDay,userHour,userMinute;

    SimpleDateFormat format;
    Button save,setalarm;
    java.sql.Time timeValue;

    Context context;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        name = findViewById(R.id.editText);
        date = findViewById(R.id.in_date);
        time = findViewById(R.id.in_time);

        setTime=findViewById(R.id.imageView2);
        setDate=findViewById(R.id.imageView3);

        save = findViewById(R.id.button);
        datePick= findViewById(R.id.btn_date);
        timepick = findViewById(R.id.btn_time);
        cal=findViewById(R.id.imageView3);
        clk=findViewById(R.id.imageView2);


        context = Main2Activity.this;
        calendar  = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        setDate.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dd = new DatePickerDialog(Main2Activity.this, new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        try {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            String dateInString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

                            Date dtdate = formatter.parse(dateInString);

                            date.setText(formatter.format(dtdate).toString());

                            formatter = new SimpleDateFormat("dd/MMM/yyyy");


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },userYear,userMonth,userDay);
                dd.show();
            }
        });



        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog tt = new TimePickerDialog(Main2Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        try
                        {
                            String dtStart = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
                            format = new SimpleDateFormat("HH:mm");

                            timeValue = new java.sql.Time(format.parse(dtStart).getTime());
                            time.setText(String.valueOf(timeValue));

                            //calender code
                            calendar.set(Calendar.HOUR_OF_DAY , hourOfDay);
                            calendar.set(Calendar.MINUTE , minute);
                            calendar.set(Calendar.SECOND,0);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, userHour,userMinute, DateFormat.is24HourFormat(Main2Activity.this));
                tt.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(name.getText().toString())) {
                    name.setError("Required ");
                } else {


                    AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                    Intent intent = new Intent(Main2Activity.this, Alarm.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                    am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                 Rminder rminder = new Rminder(Main2Activity.this);
                   rminder.addData(name.getText().toString().trim(), date.getText().toString().trim(), time.getText().toString().trim());
                    Intent intent1 = new Intent(Main2Activity.this, MainActivity.class);
                    startActivity(intent1);
                  //  Toast.makeText(Main2Activity.this, "add successfully", Toast.LENGTH_LONG).show();

                    Toast.makeText(Main2Activity.this, "Reminder add successfully", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}


