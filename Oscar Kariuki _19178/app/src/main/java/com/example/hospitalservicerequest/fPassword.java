package com.example.hospitalservicerequest;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class fPassword extends Activity {

    EditText Search;
    TextView Output;
    Button searchBtn, BackLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        Search = findViewById(R.id.search);
        Output = findViewById(R.id.output);
        searchBtn = findViewById(R.id.searchBtn);
        BackLogin = findViewById(R.id.backLogin);

        searchBtn.setOnClickListener(v -> {
            String searchText = Search.getText().toString().trim();

            if(searchText.isEmpty())
            {
                Output.setText(getString(R.string.empty_fields));
                Output.setVisibility(View.VISIBLE);
                return;
            }

            if(searchText.contains("@"))
            {
                Output.setText(getString(R.string.emailForgot));
                Output.setVisibility(View.VISIBLE);
                BackLogin.setVisibility(View.VISIBLE);

                BackLogin.setOnClickListener(i -> {
                    Intent intent = new Intent(fPassword.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                });
                return;
            }

            if(TextUtils.isDigitsOnly(searchText))
            {
                Output.setText(getString(R.string.phoneForgot));
                Output.setVisibility(View.VISIBLE);
                BackLogin.setVisibility(View.VISIBLE);

                BackLogin.setOnClickListener(i -> {
                    Intent intent = new Intent(fPassword.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                });
            }
        });

    }
}
