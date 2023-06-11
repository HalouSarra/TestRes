package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class AjouterParking extends AppCompatActivity {

    String[] item={ "Adrar", "Chlef", "Laghouat", "Oum El Bouaghi","Batna","Béjaïa","Biskra","Béchar","Blida", "Bouira", "Tamanrasset", "Tébessa", "Tlemcen",
            "Tiaret", "Tizi Ouzou", "Alger", "Djelfa", "Jijel", "Sétif", "Saïda", "Skikda", "Sidi Bel Abbès", "Annaba", "Guelma", "Constantine", "Médéa",
            "Mostaganem", "M'Sila", "Mascara", "Ouargla", "Oran", "El Bayadh", "Illizi", "Bordj Bou Arreridj", "Boumerdès", "El Tarf", "Tindouf","Tissemsilt",
            "El Oued", "Khenchela", "Souk Ahras", "Tipaza", "Mila", "Aïn Defla", "Naâma", "Aïn Témouchent", "Ghardaïa", "Relizane","Timimoun","Bordj Badji Mokhtar",
            "Ouled Djellal", "Béni Abbès", "In Salah", "Guezzam", "Touggourt","Djanet","El M'Ghair ","El Meniaa" };
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    Button conf;
    EditText timeOpen;
    EditText timeClose;
    EditText namePark;
    EditText nbrPlace;
    EditText wilaya;
    EditText tarif;
    EditText latitude;
    EditText longitude;
    TextInputLayout ed;
    private int mHour,mMinute;
    private static final int REQUEST_PROFILE_PARKING = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_parking);
        timeOpen=findViewById(R.id.heurOverture);
        timeClose=findViewById(R.id.heurFermiture);
        ed=findViewById(R.id.autoCTextCase);
        conf=findViewById(R.id.btnOuvrir);
        namePark=findViewById(R.id.nomParking);
        nbrPlace = findViewById(R.id.nbrPlace);
        tarif=findViewById(R.id.tarifHeure);
        latitude=findViewById(R.id.latitude);
        longitude=findViewById(R.id.longitude);


        autoCompleteTextView=findViewById(R.id.autoCText);
        wilaya=autoCompleteTextView;

        adapterItems= new ArrayAdapter<String>(this, R.layout.list_wilaya, item);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String sItem=adapterView.getItemAtPosition(i).toString();
                ed.setHint(sItem);
            }
        });


        timeOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar=Calendar.getInstance();
                mHour=calendar.get(Calendar.HOUR);
                mMinute=calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog=new TimePickerDialog(AjouterParking.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int h, int m) {
                        timeOpen.setText(h+":"+m);
                    }
                },mHour,mMinute,true);
                timePickerDialog.show();

            }
        });

        timeClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar=Calendar.getInstance();
                mHour=calendar.get(Calendar.HOUR);
                mMinute=calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog=new TimePickerDialog(AjouterParking.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int h, int m) {
                        timeClose.setText(h+":"+m);
                    }
                },mHour,mMinute,true);
                timePickerDialog.show();

            }
        });

        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timeOpenText = timeOpen.getText().toString();
                String timeCloseText = timeClose.getText().toString();
                String nameParkText = namePark.getText().toString();
                String nbrPlaceText = nbrPlace.getText().toString();
                String wilayaText = wilaya.getText().toString();
                String tarifText = tarif.getText().toString();
                double latitudeValue = Double.parseDouble(latitude.getText().toString());
                double longitudeValue = Double.parseDouble(longitude.getText().toString());

                Intent intent = new Intent(AjouterParking.this, ProfileReservationParking.class);
                intent.putExtra("timeOpen", timeOpenText);
                intent.putExtra("timeClose", timeCloseText);
                intent.putExtra("namePark", nameParkText);
                intent.putExtra("nbrPlace", nbrPlaceText);
                intent.putExtra("wilaya", wilayaText);
                intent.putExtra("tarif", tarifText);
                intent.putExtra("latitude", latitudeValue);
                intent.putExtra("longitude", longitudeValue);

                startActivity(intent);
            }
        });

    }
}

