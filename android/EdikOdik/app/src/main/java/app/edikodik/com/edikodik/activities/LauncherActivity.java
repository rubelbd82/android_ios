package app.edikodik.com.edikodik.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import app.edikodik.com.edikodik.R;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
    }

    public void actionHome(View v) {
        startActivity(new Intent(this, HomeActivity.class));
    }

    public void actionSignup(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void actionSignin(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
