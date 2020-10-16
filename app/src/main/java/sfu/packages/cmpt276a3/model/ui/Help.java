package sfu.packages.cmpt276a3.model.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import sfu.packages.cmpt276a3.R;

// Displays help activity describing game info and how it works
public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, Help.class);
    }
}