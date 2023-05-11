package it.unibo.alzheimerdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    Button returnBtn;
    ImageButton whatsappButton, telegramButton, gitButton;

    TextView textPier, textOrl;
    ImageView imageLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        returnBtn = findViewById(R.id.returnBtn);
        whatsappButton = findViewById(R.id.whatsappButton);
        telegramButton = findViewById(R.id.telegramButton);
        gitButton = findViewById(R.id.gitButton);
        imageLogo = findViewById(R.id.imageLogo);
        textPier = findViewById(R.id.textPier);
        textOrl = findViewById(R.id.textOrl);

        returnBtn.setOnClickListener(v -> openStartActivity());

        whatsappButton.setOnClickListener(v -> gotoUrl("https://wa.me/+393664218978"));

        telegramButton.setOnClickListener(v -> gotoUrl("https://t.me/+393891503893"));

        gitButton.setOnClickListener(v -> gotoUrl("https://github.com/PierCarrozzini/AlzheimerDetection.git"));

        textPier.setOnClickListener(v -> gotoUrl("https://github.com/PierCarrozzini"));

        textOrl.setOnClickListener(v -> gotoUrl("https://github.com/theorly"));

    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    public void openStartActivity() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}