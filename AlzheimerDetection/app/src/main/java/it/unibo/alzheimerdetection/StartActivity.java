package it.unibo.alzheimerdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    ImageView Main, About, Progetto, Contacts;
    TextView textMain, textAbout, textProgetto, textContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setTheme(R.style.Theme_AlzheimerDetection);

        Main = findViewById(R.id.Main);
        About = findViewById(R.id.About);
        Progetto = findViewById(R.id.Progetto);
        Contacts = findViewById(R.id.Contacts);
        textMain = findViewById(R.id.textMain);
        textAbout = findViewById(R.id.textAbout);
        textProgetto = findViewById(R.id.textProgetto);
        textContacts = findViewById(R.id.textContacts);

        Main.setOnClickListener(v -> {
            openActivity(MainActivity.class);
            showToast("Main Acitivty Clicked");
        });

        About.setOnClickListener(v -> {
            openActivity(InfoActivity.class);
            showToast("Info Acitivty Clicked");
        });

        Progetto.setOnClickListener(v -> {
            openActivity(ProjectActivity.class);
            showToast("Project Acitivty Clicked");
        });

        Contacts.setOnClickListener(v -> {
            openActivity(EmailActivity.class);
            showToast("Email Activity Clicked");
        });

        textMain.setOnClickListener(v -> {
            openActivity(MainActivity.class);
            showToast("Main Acitivty Clicked");
        });

        textAbout.setOnClickListener(v -> {
            openActivity(InfoActivity.class);
            showToast("Info Acitivty Clicked");
        });

        textProgetto.setOnClickListener(v -> {
            openActivity(ProjectActivity.class);
            showToast("Project Acitivty Clicked");
        });

        textContacts.setOnClickListener(v -> {
            openActivity(EmailActivity.class);
            showToast("Email Acitivty Clicked");
        });

    }

    public void openActivity(Class s) {
        Intent intent = new Intent(this, s);
        startActivity(intent);
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}