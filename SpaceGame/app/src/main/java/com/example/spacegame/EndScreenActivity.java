package com.example.spacegame;

import static java.lang.System.exit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Objects;

public class EndScreenActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_end_screen);

        intent = getIntent();
        setUpScreen(intent.getStringExtra("Status"));
    }

    private void setUpScreen(String status)
    {
        TextView statusText = findViewById(R.id.status_textView);
        TextView scoreText = findViewById(R.id.score_textView);
        TextView healsText = findViewById(R.id.heals_textView);
        TextView bombsText = findViewById(R.id.bombs_textView);

        if(Objects.equals(status, "Victory"))
        {
            statusText.setText("Victory!");
        }else
        {
            statusText.setText("Defeat!");
        }

        scoreText.setText("Score: "+ intent.getStringExtra("Score"));
        healsText.setText("Total Heals Consumed: "+intent.getStringExtra("Heals"));
        bombsText.setText("Total Bombs Detonated: "+intent.getStringExtra("Bombs"));

        // Set-Up Button On Click Listeners
        Button replayButton = findViewById(R.id.replay_Button);
        Button quitButton = findViewById(R.id.quit_Button);

        replayButton.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        });

        quitButton.setOnClickListener(v -> {
            finish();
            exit(0);
        });
    }

}