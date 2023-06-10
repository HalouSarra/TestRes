package com.example.test;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText date;
    EditText hour;
    DatePickerDialog.OnDateSetListener setListener;

    EditText timeBooking;
    EditText nom,matricule,nbrJours,nbrHeurs;
    private int mHour,mMinute;
    Button conf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                date=findViewById(R.id.dateDebutRes);
                hour=findViewById(R.id.heurDebutRes);
                nom=findViewById(R.id.nomRes);
                matricule=findViewById(R.id.matriculRes);
                conf=findViewById(R.id.btnConfirmationReservation);
                nbrJours=findViewById(R.id.nbrJours);
                nbrHeurs=findViewById(R.id.nbrHours);
                timeBooking=findViewById(R.id.heurDebutRes);

                Calendar calendar =Calendar.getInstance();
                final int year =calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day =calendar.get(Calendar.DAY_OF_MONTH);
                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog datePickerDialog=new DatePickerDialog(
                                MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                                ,setListener,year, month,day);
                        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        datePickerDialog.show();
                    }
                });

                setListener =new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month =month+ 1;
                        String datee=day+"/"+ month +"/"+year;
                    }
                };

                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog datePickerDialog=new DatePickerDialog(
                                MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                                m=month+1;
                                String datte=d+"/"+m+"/"+y;
                                date.setText(datte);
                            }
                        },year,month,day);
                        datePickerDialog.show();
                    }
                });


                timeBooking.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar calendar=Calendar.getInstance();
                        mHour=calendar.get(Calendar.HOUR);
                        mMinute=calendar.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog=new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int h, int m) {
                                timeBooking.setText(h+":"+m);
                            }
                        },mHour,mMinute,true);
                        timePickerDialog.show();

                    }
                });

        Intent data = getIntent();
        String tarif = data.getStringExtra("PTARIF");
        String parkingName = data.getStringExtra("PNAME");
        String parkingWilaya = data.getStringExtra("PWILAYA");




                conf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, PaymentOnline.class);
                        int nbrJ = Integer.parseInt(nbrJours.getText().toString());
                        int nbrH = Integer.parseInt(nbrHeurs.getText().toString());
                        String tarifT =String.valueOf((Double.parseDouble(tarif)* nbrJ * 24 + (Double.parseDouble(tarif)) * nbrH)) ;
                        String name = nom.getText().toString();
                        String matric = matricule.getText().toString();
                        String tarifTotal = tarifT;
                        String selectedDate = date.getText().toString();
                        String selectedHour = timeBooking.getText().toString();
                        String selectedNbrJours = nbrJours.getText().toString();
                        String selectedNbrHeurs = nbrHeurs.getText().toString();

                        intent.putExtra("NAME", name);
                        intent.putExtra("ParkingName", parkingName);
                        intent.putExtra("ParkingWilaya", parkingWilaya);
                        intent.putExtra("MATRIC", matric);
                        intent.putExtra("TARIF_TOTAL", tarifTotal);
                        intent.putExtra("DATE_DebutRes", selectedDate);
                        intent.putExtra("HOUR_DebutRes", selectedHour);
                        intent.putExtra("NBR_JOURS", selectedNbrJours);
                        intent.putExtra("NBR_HEURS", selectedNbrHeurs);

                        startActivity(intent);

                    }
                });
    }

}

