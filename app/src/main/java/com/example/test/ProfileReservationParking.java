package com.example.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProfileReservationParking extends AppCompatActivity {


    TextView timeOpen;
    TextView timeClose;
    TextView namePark;
    TextView nbrPlace;
    TextView wilaya;
    TextView tarif;
    double latitude;
    double longitude;
    Button reserv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_reservation_parking);


        reserv = findViewById(R.id.reservationBtn);
        timeOpen = findViewById(R.id.parkingPTimeOpen);
        timeClose = findViewById(R.id.parkingPTimeClose);
        namePark = findViewById(R.id.parkingName);
        nbrPlace = findViewById(R.id.parkingPNbrPlace);
        wilaya = findViewById(R.id.parkingWilayaPName);
        tarif = findViewById(R.id.parkingPTarifH);

        Intent intent = getIntent();
        String timeOpenText = intent.getStringExtra("timeOpen");
        String timeCloseText = intent.getStringExtra("timeClose");
        String nameParkText = intent.getStringExtra("namePark");
        String nbrPlaceText = intent.getStringExtra("nbrPlace");
        String wilayaText = intent.getStringExtra("wilaya");
        String tarifText = intent.getStringExtra("tarif");
        double latitudeValue = intent.getDoubleExtra("latitude", 0);
        double longitudeValue = intent.getDoubleExtra("longitude", 0);

        timeOpen.setText(timeOpenText);
        timeClose.setText(timeCloseText);
        namePark.setText(nameParkText);
        nbrPlace.setText(nbrPlaceText);
        wilaya.setText(wilayaText);
        tarif.setText(tarifText);

        latitude = latitudeValue;
        longitude = longitudeValue;



        reserv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String parkName = namePark.getText().toString();
                String parkWilaya = wilaya.getText().toString();
                String parkTarif = tarif.getText().toString();

                Intent intent = new Intent(ProfileReservationParking.this, MainActivity.class);
                intent.putExtra("PNAME", parkName);
                intent.putExtra("PWILAYA", parkWilaya);
                intent.putExtra("PTARIF", parkTarif);

                startActivity(intent);
            }
        });


    }
}
