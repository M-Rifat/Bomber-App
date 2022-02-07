package com.example.smsbomber;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    private EditText text1,text2;
    private TextView bt1;
    private TextView tview;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        text1=findViewById(R.id.t1);
        text2=findViewById(R.id.t2);
        bt1=findViewById(R.id.b1);
        tview=findViewById(R.id.tv);
        bt1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        try {

            if (v.getId()==R.id.b1)
            {

                
            }
        }catch (Exception e) {
            tview.setText("Unsuccessfull");
        }


    }
}