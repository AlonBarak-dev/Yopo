package com.example.yopo.layout_classes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;
import com.example.yopo.data_classes.AddToFirestoreTask;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    // field variables
    private Button register;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = findViewById(R.id.start_register);
        login = findViewById(R.id.start_login);

//        HashMap<String, Object> data = new HashMap<>();
//        data.put("field1", "value1");
//        data.put("field2", "value2");
//
//        AddToFirestoreTask task = new AddToFirestoreTask(data, "Test");
//        task.execute();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}