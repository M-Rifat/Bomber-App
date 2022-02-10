package com.example.smsbomber;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AboutUs extends AppCompatActivity {

    private TextView txtMarquee;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        txtMarquee = (TextView) findViewById(R.id.marqueeText);

        txtMarquee.setSelected(true);
    }
}
