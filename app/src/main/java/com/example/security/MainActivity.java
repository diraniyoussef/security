package com.example.security;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ImageView police;
    TextView welcome;
    Button login;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        police = findViewById(R.id.police);
        welcome = findViewById(R.id.welcome);
        login = findViewById(R.id.login1);
        register = findViewById(R.id.register1);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login1();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register1();
            }
        });
    }

    public void register1() {
        Intent intent1 = new Intent(MainActivity.this, Register.class);
        startActivity(intent1);
        finish();
    }

    public void login1() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish();
    }

}

