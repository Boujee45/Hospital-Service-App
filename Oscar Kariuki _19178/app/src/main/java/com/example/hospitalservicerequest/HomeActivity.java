package com.example.hospitalservicerequest;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    databaseHelper db;
    Spinner spin;
    EditText etWard, etBed, etNotes;
    Button btnSubmit;
    TextView btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new databaseHelper(this);
        spin = findViewById(R.id.spinnerServices);
        etWard = findViewById(R.id.etWard);
        etBed = findViewById(R.id.etBed);
        etNotes = findViewById(R.id.etNotes);
        btnSubmit = findViewById(R.id.btnSubmit);
	    btnLogout = findViewById(R.id.logout);

        String[] services = {"Cleaning (CL001)", "Equipment assistance (EP002)", "Linen change (LC001)", "Porter services (PS001)"};
        spin.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, services));

        btnSubmit.setOnClickListener(v -> {
            String w = etWard.getText().toString().trim();
            String b = etBed.getText().toString().trim();

            if (w.isEmpty() || b.isEmpty()) {
                Toast.makeText(this, "Please enter Ward and Bed", Toast.LENGTH_SHORT).show();
            } else {
                if (db.insertRequest(spin.getSelectedItem().toString(), w, b, etNotes.getText().toString())) {
                    Toast.makeText(this, "Request Sent!", Toast.LENGTH_SHORT).show();
                    etWard.setText(""); etBed.setText(""); etNotes.setText("");
                }
            }
        });
	
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this,MainActivity.class);
            startActivity(intent);
            finish;
        }
    }
}