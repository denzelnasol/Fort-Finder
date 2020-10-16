package sfu.packages.cmpt276a3.model.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sfu.packages.cmpt276a3.R;

// Displays menu including button to go to other activities
public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        start();
        options();
        help();

    }


    private void start() {
        Button startButton = (Button) findViewById(R.id.startButton);
        final MediaPlayer slash = MediaPlayer.create(this, R.raw.slash);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slash.start();
                Intent intent = GameUI.makeIntent(Menu.this);
                startActivity(intent);
            }
        });
    }

    private void options() {
        Button optionsButton = (Button) findViewById(R.id.optionsButton);
        final MediaPlayer slash = MediaPlayer.create(this, R.raw.slash);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slash.start();
                Intent intent = Options.makeIntent(Menu.this);
                startActivity(intent);
            }
        });
    }

    private void help() {
        Button helpButton = (Button) findViewById(R.id.helpButton);
        final MediaPlayer slash = MediaPlayer.create(this, R.raw.slash);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slash.start();
                Intent intent = Help.makeIntent(Menu.this);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();
        finish();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, Menu.class);
    }
}