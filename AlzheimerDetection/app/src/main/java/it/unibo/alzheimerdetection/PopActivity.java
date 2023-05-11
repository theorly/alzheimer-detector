package it.unibo.alzheimerdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PopActivity extends Activity {

    TextView skip, gotit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        gotit = findViewById(R.id.gotit);

        gotit.setOnClickListener(v -> { finish();
            showToast("Let's go");
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int heigth = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(heigth*.9));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.NO_GRAVITY;
        params.x=0;
        params.y=0;
        params.setColorMode(ActivityInfo.COLOR_MODE_WIDE_COLOR_GAMUT);

        getWindow().setAttributes(params);
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}