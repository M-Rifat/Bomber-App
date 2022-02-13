package com.example.smsbomber;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private EditText editText1, editText2;
    private RatingBar ratingBar;
    String ratingg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);

        ratingg = new String("");

        //back home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        button = findViewById(R.id.bt);
        editText1 = findViewById(R.id.et1);
        editText2 = findViewById(R.id.et2);

        button.setOnClickListener(this);
        ratingBar=findViewById(R.id.rtbar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //Toast.makeText(Feedback.this,"value : "+rating,Toast.LENGTH_SHORT).show();
                ratingg = String.valueOf(rating);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        try {
            if(v.getId()==R.id.bt){
                String email = editText1.getText().toString();
                String comment = editText2.getText().toString();
                sendFeedback(email,comment,ratingg);
                editText1.setText("");
                editText2.setText("");
                ratingBar.setRating(0F);
            }


        } catch (Exception e) {
        }


    }
    public void sendFeedback(String email,String comment,String rating){
        DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("feedback");
        //  Map<String ,Object> unique_key = new HashMap<String ,Object>();
        String temp_key = root.push().getKey();
        // root.updateChildren(unique_key);
        Map<String ,Object> userMessage = new HashMap<String ,Object>();
        DatabaseReference userRef = root.child(temp_key);
        userMessage.put("Email",email);
        userMessage.put("Comment",comment);
        userMessage.put("Rating",rating);
        userRef.updateChildren(userMessage);

    }
}