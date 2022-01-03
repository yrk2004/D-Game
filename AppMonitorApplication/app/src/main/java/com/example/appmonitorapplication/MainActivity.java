package com.example.appmonitorapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    Button loginbutton;
    Button login;
    Database logindb;
    private Object Intent;
    private Object Toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logindb=new Database(this);
        loginbutton = (Button) findViewById(R.id.login);
        login = (Button) findViewById(R.id.login);
    }

    public void register(View view) {
                        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
    }

    public void login(View view) {
        if (view.getId() == R.id.login) {
            EditText a = (EditText) findViewById(R.id.editUserName);
            String userName = a.getText().toString();
            EditText b = (EditText) findViewById(R.id.editPassword);
            String password = b.getText().toString();

            String passwordDb = logindb.searchPass(userName);
            if (passwordDb.equals(password)) {


                Intent i = new Intent(MainActivity.this, AppUsageListActivity.class);
                i.putExtra("Username", userName);
                startActivity(i);
            }

            else {
                // Username or password false, display and an error
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

                dlgAlert.setMessage("wrong password or username");
                dlgAlert.setTitle("Error Message...");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
            }


        }

    }




}