package com.example.smsbomber;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TextBomber extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private EditText numberEditText, amountEditText;
    private Button attackButton;
    private TextView resultTextView;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public String nVersion;
    public String version;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.text_bomber);
        this.setTitle("Text Bomber");

        numberEditText = findViewById(R.id.text1);
        amountEditText = findViewById(R.id.text2);
        attackButton = findViewById(R.id.click);
        resultTextView = findViewById(R.id.tView);
        attackButton.setOnClickListener(this);
        resultTextView.setText("");

        version = "1.6";

        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        // Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //swipe to refresh
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);

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
                    }
                }
        );

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.click) {
            String message = numberEditText.getText().toString();
            String nMsg = new String("");
            int amnt = Integer.parseInt(amountEditText.getText().toString());
            for (int i=0;i<=amnt;i++) {
                nMsg+=message;
                nMsg+="\n";
            }
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String subject = "";
            String body = (nMsg);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(intent, "Share with"));
        }

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
            String subject = "Have a good friend? Send him a bomb !:-]";
            String body = String.valueOf("https://github.com/wizard-carlo/APK/blob/main/bomberV" + version + ".apk");
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(intent, "Share with"));
        }
        if (item.getItemId() == R.id.usid) {
            Intent intent = new Intent(TextBomber.this, AboutUs.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.updateid) {

            DatabaseReference mDatabase;
            nVersion = "";
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("version").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                        //.makeText(MainActivity.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                        updateIntentCall(version);
                    } else {
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        //Toast.makeText(MainActivity.this,version,Toast.LENGTH_LONG).show();
                        nVersion = String.valueOf(task.getResult().getValue());
                        if (version.equals(nVersion)) {
                            Toast.makeText(TextBomber.this, "Your version is up to date", Toast.LENGTH_LONG).show();
                        } else {
                            updateIntentCall(nVersion);
                        }
                    }
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateIntentCall(String vv) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        String url = String.valueOf("https://github.com/wizard-carlo/APK/blob/main/bomberV" + vv + ".apk");
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.textbombid) {
            drawerLayout.closeDrawers();
        }
        if (item.getItemId() == R.id.smsbombid) {
            this.finish();
        }

        return true;
    }
}
