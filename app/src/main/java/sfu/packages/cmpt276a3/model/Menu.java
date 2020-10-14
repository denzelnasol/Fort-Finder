package sfu.packages.cmpt276a3.model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import sfu.packages.cmpt276a3.R;

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
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = GameBoard.makeIntent(Menu.this);
                startActivity(intent);
            }
        });
    }

    private void options() {
    }

    private void help() {
    }
}