package com.maxmux.itaxi.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maxmux.itaxi.Helpers.FirebaseUtil;
import com.maxmux.itaxi.Helpers.RideDeal;
import com.maxmux.itaxi.R;

import java.util.Calendar;


public class BookActivity extends AppCompatActivity {
    private EditText noOfPassengers, luggageSize;
    private Button  carType, searchCar;
    private TextView pickStation;
    private TextView dropStation;
    private static TextView dateSelector;
    private TextView startTime;
    RideDeal deal;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        // Write a message to the database
        FirebaseUtil.openFbReference("ridebookings");

        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        noOfPassengers = findViewById(R.id.number_of_passengers_text);
        luggageSize = findViewById(R.id.luggage_size);
        dateSelector = findViewById(R.id.date_selector);
        startTime = findViewById(R.id.start_time);
        carType = findViewById(R.id.car_type_selector);
        searchCar = findViewById(R.id.search_car);
        pickStation = findViewById(R.id.car_stations_spinner_filter);
        dropStation = findViewById(R.id.car_drop_stations_spinner_filter);

        assert dateSelector !=null;
        dateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment mDatePicker = new DatePickerFragment();
                mDatePicker.show(getSupportFragmentManager(),"Select Date");

            }
        });

        searchCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookRide();
                Toast.makeText(BookActivity.this, "Vehicle Booked", Toast.LENGTH_LONG).show();
                clean();
                return;

            }
        });

    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
            dateSelector.setText( String.valueOf(year) + " - " + String.valueOf(month) +"1" + " - " + String.valueOf(day));
        }
    }
    private void clean() {
        pickStation.setText("");
        dropStation.setText("");
        dateSelector.setText("");
        startTime.setText("");
        noOfPassengers.requestFocus();
    }


    private void bookRide() {
        String puckup = pickStation.getText().toString();
        String dropoff = dropStation.getText().toString();
        String date = dateSelector.getText().toString();
        String time = startTime.getText().toString();
        String passegers = noOfPassengers.getText().toString();
        String luggage = luggageSize.getText().toString();
        String car = carType.getText().toString();
        RideDeal rideDeal  = new RideDeal(puckup, dropoff, date,time, passegers,luggage,car);
        mDatabaseReference.push().setValue(rideDeal);
    }
}