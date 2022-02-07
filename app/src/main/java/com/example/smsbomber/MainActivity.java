package com.example.smsbomber;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.smsbomber.Bombing;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    private EditText text1,text2;
    private Button bt1;
    private TextView tview;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        text1=findViewById(R.id.n1);
        text2=findViewById(R.id.n2);
        bt1=findViewById(R.id.b1);
        tview=findViewById(R.id.tv);
        bt1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        try {

            if (v.getId()==R.id.b1)
            {
//  importing another class
                Bombing bombing=new Bombing();
                bombing.Bmb();
                int rpcode = bombing.status;
                tview.setText(String.valueOf(rpcode));

            }
        }catch (Exception e) {
            tview.setText("Error");
        }


    }
}