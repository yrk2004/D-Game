package com.example.appmonitorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    Database logindb;
    Button SignUpButton;
    EditText Age;
    EditText Phone;
    EditText Email;
    EditText Password;

    EditText Username;
    Button Next;
    EditText Name;
    Button save;
    Button tnc;
    private Object Main2Activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        logindb = new Database(this);
        SignUpButton = (Button) findViewById(R.id.SignUpButton);
        Age = (EditText) findViewById(R.id.age_text);
        Phone = (EditText) findViewById(R.id.phonenumber);
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        Username = (EditText) findViewById(R.id.username);
        Name = (EditText) findViewById(R.id.name);
       // Next = (Button) findViewById(R.id.next);
        save = (Button) findViewById(R.id.save);
       // tnc = (Button) findViewById(R.id.tnc);

    }
    /*public void tnc(View view) {
        tnc.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     //   startActivity(new Intent(RegisterActivity.this, Main17Activity.class));
                    }
                }
        );
    }*/

    public void store_info(View view) {

                        boolean isInserted = logindb.insertData(Username.getText().toString(),
                                Password.getText().toString(),Name.getText().toString(),
                                Email.getText().toString(),
                                Phone.getText().toString(),
                                Integer.valueOf(Age.getText().toString())
                                );
                        startActivity(new Intent(RegisterActivity.this, AppUsageListActivity.class));

    }

    public void login(View view) {
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }

   /* public void next(View view) {
                        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                    }*/
}
