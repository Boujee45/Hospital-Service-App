package com.example.hospitalservicerequest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends Activity {

    EditText  Email,phoneNo,Password1,Username,Password2;

    Button signUP, Cancel;

    TextView Login, Output;

    databaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Email = findViewById(R.id.email);
        phoneNo = findViewById(R.id.phoneNo);
        Password1 = findViewById(R.id.password1);
        Password2 = findViewById(R.id.password2);
        Username = findViewById(R.id.username);

        signUP = findViewById(R.id.signupBtn);
        Cancel = findViewById(R.id.cancelBtn);

        Login = findViewById(R.id.login);
        Output = findViewById(R.id.output);

        dbHelper = new databaseHelper(this);

        signUP.setOnClickListener(v ->
        {

            String emailText = Email.getText().toString().trim();
            String phoneText = phoneNo.getText().toString().trim();
            String passwordText1 = Password1.getText().toString().trim();
            String passwordText2 = Password2.getText().toString().trim();
            String usernameText = Username.getText().toString().trim();

            if(emailText.isEmpty() || phoneText.isEmpty()
                    || passwordText1.isEmpty() || usernameText.isEmpty()
                    || passwordText2.isEmpty())
            {
                Output.setText(getString(R.string.empty_fields));
                Output.setVisibility(View.VISIBLE);
                return;
            }

            if(!emailText.contains("@"))
            {
                Output.setText(getString(R.string.invalid_email));
                Output.setVisibility(View.VISIBLE);
                return;
            }

            if(passwordText1.length() < 6 )
            {
                Output.setText(getString(R.string.short_password));
                Output.setVisibility(View.VISIBLE);
                return;
            }

            if(passwordText1.equals("123456"))
            {
                Output.setText(getString(R.string.weak_password));
                Output.setVisibility(View.VISIBLE);
                return;
            }

            if(!passwordText1.equals(passwordText2))
            {
                Output.setText(getString(R.string.confirm_password));
                Output.setVisibility(View.VISIBLE);
                return;
            }

            if(dbHelper.checkUser(usernameText,emailText))
            {
                Output.setText(getString(R.string.user));
                Output.setVisibility(View.VISIBLE);
                return;
            }

            if(dbHelper.registerUser(usernameText,emailText,passwordText1))
            {
                Toast.makeText(this, getString(R.string.signup_success),
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(this, getString(R.string.signup_failed),
                        Toast.LENGTH_LONG).show();
            }
        });

        Cancel.setOnClickListener(v -> {
            Email.setText("");
            phoneNo.setText("");
            Username.setText("");
            Password1.setText("");
            Password2.setText("");
        });

        Login.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
}

