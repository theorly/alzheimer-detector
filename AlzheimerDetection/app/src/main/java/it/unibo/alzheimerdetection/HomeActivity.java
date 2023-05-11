package it.unibo.alzheimerdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(HomeActivity.this, StartActivity.class);
            startActivity(intent);
            finish();
        },3000);

    }
}