package com.example.smsbomber;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TextBomber extends AppCompatActivity {
    private TextView textView;
    private Button sendButton;
    private EditText messageEditText,amountEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.text_bomber);
        this.setTitle("Text Bomber");

        textView = findViewById(R.id.mtView);
        sendButton = findViewById(R.id.mclick);
        messageEditText = findViewById(R.id.mtext1);
        amountEditText = findViewById(R.id.mtext2);
    }
}
