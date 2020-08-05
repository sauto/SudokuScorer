package com.example.sudokuscorer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    public void onButtonClick(View view){
        switch (view.getId()) {
            case R.id.button2:
                Toast.makeText(MainActivity.this, "クリックされました！", Toast.LENGTH_LONG).show();
                break;
        }
    }

}